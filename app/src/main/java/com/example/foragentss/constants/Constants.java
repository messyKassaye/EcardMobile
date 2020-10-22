package com.example.foragentss.constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Constants {
    //10.0.2.2:8000
    //http://dev.tesfabunna.com
    private static final String TOKEN_PREFENCE = "loggedUser";
    private static final String DOWNLOAD_PATH = "http://10.0.2.2:8000/";
    private static final String API_URL="http://10.0.2.2:8000/api/";
    private static final String API_AUTH_URL="http://10.0.2.2:8000/api/auth/";
    private static final String API_AUTH_ADMIN = "http://10.0.2.2:8000/api/admin/";
    private static final String API_RETAILERS_ADMIN = "http://10.0.2.2:8000/api/retailers/";
    private static final String DATA_PATH = "advertData";
    private static final String DB_NAME = "TabletAdsDB";
    private static int realWidth,realHeight;

    public static String getBaseAPiURL(){
        return API_URL;
    }

    public static String getAPIAuthURL(){
        return API_AUTH_URL;
    }


    public  static String getDbName(){
        return DB_NAME;
    }

    public static String getTokenPrefence(){
        return  TOKEN_PREFENCE;
    }

    public static String getApiAuthAdmin(){
        return API_AUTH_ADMIN;
    }

    public static int getRealWidth() {
        return realWidth;
    }

    public static void setRealWidth(int realWidth) {
        Constants.realWidth = realWidth;
    }

    public static int getRealHeight() {
        return realHeight;
    }

    public static void setRealHeight(int realHeight) {
        Constants.realHeight = realHeight;
    }

    public static  void calculateDeviceResolution(AppCompatActivity context) {
        Display display = context.getWindowManager().getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= 17) {
            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            setRealWidth(realMetrics.widthPixels);
            setRealHeight(realMetrics.heightPixels);

        } else {
            //This should be close, as lower API devices should not have window navigation bars
            setRealWidth(display.getWidth());
            setRealHeight(display.getHeight());
        }
    }

    public static String currentDate(){
        String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        return date;
    }

    public static String currentTime(){
        String date = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        return date;
    }

    public static String todaysDate(){
        String date = Constants.currentDate();
        String today = date.substring(0,date.lastIndexOf(" "));
        return  today;
    }

    public static String getFormatedAmount(double amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    public static int getCardTypeValue(int id){
        int result =0;
        if (id==1){
            result=5;
        }else if (id==2){
            result = 10;
        }else if(id==3){
            result =15;
        }else if (id==4){
            result = 25;
        }else if (id==5){
            result = 50;
        }else if (id==6){
            result = 100;
        }

        return result;
    }


    public  static boolean isOnline(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
}
