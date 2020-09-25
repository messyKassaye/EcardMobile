package com.example.foragentss.dashboard.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.InetAddresses;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.receivers.WiFiDirectBroadcastReceiver;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AcceptCardActivity extends AppCompatActivity {
    private IntentIntegrator qrScanner;
    private Button startScanning;

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private WiFiDirectBroadcastReceiver wiFiDirectBroadcastReceiver;
    private IntentFilter intentFilter;
    private WifiManager wifiManager;

    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private String[] deviceNameArray;
    private WifiP2pDevice[] deviceArray;
    private HashMap<String,WifiP2pDevice> deviceHashMap = new HashMap<>();

    //views
    private static final int MESSAGE_READ = 1;
    private ListView listView;
    private TextView messageBox;
    ServerClass serverClass;
    ClientClass clientClass;
    SendReceive sendReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_card);
        getSupportActionBar().setTitle("Accept card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        wifiP2pManager = (WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(this,getMainLooper(),null);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wiFiDirectBroadcastReceiver = new WiFiDirectBroadcastReceiver(wifiP2pManager,channel,this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        qrScanner = new IntentIntegrator(this);
        startScanning = findViewById(R.id.startScanning);
        listView = findViewById(R.id.listView);

        startDiscovering();

        startScanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qrScanner.initiateScan();
                String message = "Hello";
                sendReceive.write(message.getBytes());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WifiP2pDevice selectedDevice = deviceArray[position];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = selectedDevice.deviceAddress;
                wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"Connected successfully",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int reason) {

                    }
                });
            }
        });

        messageBox = findViewById(R.id.messageBox);



    }

   public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peersList) {
            if (!peersList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peersList.getDeviceList());
                deviceNameArray = new String[peersList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peersList.getDeviceList().size()];

                int index =0;
                for (WifiP2pDevice device:peersList.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    deviceHashMap.put(device.deviceName,device);
                    index++;
                }
               ArrayAdapter<String> adapter =new ArrayAdapter<>
                       (getApplicationContext(),R.layout.device_list_layout,R.id.deviceName,deviceNameArray);
                listView.setAdapter(adapter);

            }

            if (peers.size()==0){
                Toast.makeText(getApplicationContext(),"No device is found",Toast.LENGTH_LONG).show();
                return;
            }

        }
    };

    public WifiP2pManager.ConnectionInfoListener connectionInfoListener =new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            final InetAddress inetAddresses = info.groupOwnerAddress;
            startScanning.setVisibility(View.VISIBLE);
            if (info.groupFormed&&info.isGroupOwner){
                Toast.makeText(getApplicationContext(),"Im host",Toast.LENGTH_LONG).show();
                serverClass = new ServerClass();
                serverClass.start();
            }else {
                Toast.makeText(getApplicationContext(),"Im client",Toast.LENGTH_LONG).show();
                clientClass = new ClientClass(inetAddresses);
                clientClass.start();

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                //after finding device address let's send the data
                String qrCodeContent = result.getContents();
                Toast.makeText(getApplicationContext(),qrCodeContent,Toast.LENGTH_LONG).show();
                WifiP2pDevice activeDevice= deviceHashMap.get(qrCodeContent);
                Toast.makeText(getApplicationContext(),"Hash:"+qrCodeContent,Toast.LENGTH_LONG).show();
               /* WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = activeDevice.deviceAddress;
                wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"Connected with "+
                                activeDevice.deviceName,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_LONG).show();
                    }
                });*/

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public  void startDiscovering(){

        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"Discovered",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason) {
                System.out.println("REASON: "+reason);
            }
        });
    }

    public void wifiIsNotEnable(){

    }

    public void wifiEnable(){
        Toast.makeText(this,"Wifi is opened",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wiFiDirectBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wiFiDirectBroadcastReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    public void myDevice(WifiP2pDevice device){

    }


    public class ServerClass extends Thread{
        private ServerSocket serverSocket;
        private Socket socket;
        @Override
        public void run(){

            try {
                serverSocket = new ServerSocket(4626);
                socket = serverSocket.accept();
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class ClientClass extends Thread{
        private Socket socket;
        private String hostAddress;

        public ClientClass(InetAddress inetAddress){
            hostAddress = inetAddress.getHostAddress();
            socket = new Socket();
        }

        @Override
        public void run(){
            try {
                socket.connect(new InetSocketAddress(hostAddress,4626),500);
                sendReceive = new SendReceive(socket);
                sendReceive.start();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tmpMessage = new String(readBuff,0,msg.arg1);
                    messageBox.setText(tmpMessage);
                    break;
            }
            return true;
        }
    });

    public class SendReceive extends Thread{
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt){
            this.socket = skt;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            }catch (Exception e){

            }
        }

        @Override
        public void run(){
            byte[] buffer = new byte[1024];
            int bytes;

            while (socket!=null){
                try {
                    bytes = inputStream.read(buffer);
                    if (bytes>0){
                        handler.obtainMessage(MESSAGE_READ,bytes,-1,buffer).sendToTarget();
                    }
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
