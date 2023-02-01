package com.status.truthanddare;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCalling {
    static  final  String TRUTH="https://api.truthordarebot.xyz/v1/truth";
    static  final  String DARE="https://api.truthordarebot.xyz/v1/dare";
  Context context;
    public static String LANGUAGE="ENG";

    public String getLANGUAGE() {
        return LANGUAGE;
    }

    public void setLANGUAGE(String LANGUAGE) {
        ApiCalling.LANGUAGE = LANGUAGE;
    }


  String ANSWER=null;

    ApiCalling(Context context){

         this.context=context;
        Toast.makeText(context, getLANGUAGE(), Toast.LENGTH_SHORT).show();

    }

    void apiCallingp() {

     LANGUAGE= getLANGUAGE();

        AndroidNetworking.initialize(context);
        AndroidNetworking.get(TRUTH).setPriority(Priority.HIGH).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", "Response working");
                try {

                    if (LANGUAGE.equals("ENG")) {
                        ANSWER = response.getString("question");
                    } else {
                        JSONObject obj = new JSONObject();
                        obj = response.getJSONObject("translations");
                        ANSWER = obj.getString(LANGUAGE);

                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("Response", anError.toString());
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void dareAns(){
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(DARE).setPriority(Priority.HIGH).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", "Response working");
                try {

                    if (LANGUAGE.equals("ENG")) {
                        ANSWER = response.getString("question");
                    } else {
                        JSONObject obj = new JSONObject();
                        obj = response.getJSONObject("translations");
                        ANSWER = obj.getString(LANGUAGE);

                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("Response", anError.toString());
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  String answer(){
        return ANSWER;
    }



}
