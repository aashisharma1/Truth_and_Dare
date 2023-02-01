package com.status.truthanddare.Network;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.status.truthanddare.MainActivity;

public class NetworkConnectionCheck {

    //Note:
    //This Class Is not Implemented yet.
    //I will Add this is next Version
    // Thanks you
    MainActivity mainActivity;
   public NetworkRequest networkRequest;

    public NetworkConnectionCheck(MainActivity mainActivity){

   networkRequest=new NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build();
       this.mainActivity=mainActivity;
    }
    public  ConnectivityManager.NetworkCallback networkCallback=new ConnectivityManager.NetworkCallback(){
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Toast.makeText(mainActivity, "Connection Lost", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            final boolean unmetered=networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);

        }


    };




}
