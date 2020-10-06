package com.example.foragentss.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.widget.Toast;

import com.example.foragentss.MainActivity;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager wifiP2pManager;
    private Channel channel;
    private MainActivity mainActivity;
    public WiFiDirectBroadcastReceiver(WifiP2pManager wifiP2pManager,Channel channel,MainActivity mainActivity){
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.mainActivity = mainActivity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
               // mainActivity.wifiEnable();
            } else {
                // Wi-Fi P2P is not enabled
               // mainActivity.wifiIsNotEnable();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (wifiP2pManager!=null){
                //wifiP2pManager.requestPeers(channel,mainActivity.peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            if (wifiP2pManager==null){
                return;
            }

            NetworkInfo info = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (info.isConnected()){
                //wifiP2pManager.requestConnectionInfo(channel,mainActivity.connectionInfoListener);
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //Respond to you phone device change
            WifiP2pDevice myDevice =(WifiP2pDevice)intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            //mainActivity.myDevice(myDevice);
        }
    }
}
