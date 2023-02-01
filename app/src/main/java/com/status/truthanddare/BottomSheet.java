package com.status.truthanddare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheet extends BottomSheetDialogFragment {

    com.google.android.material.switchmaterial.SwitchMaterial aSwitch;
    Spinner spinner;
    String[] aa = {"ENG", "bn", "hi", "de", "fr", "es", "tl", "Eng"};

    MainActivity activity;


    public BottomSheet() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        aSwitch = view.findViewById(R.id.switch1);


        if (activity.sharedPreferences.getBoolean("mode", false)) {
            aSwitch.setChecked(true);
        }
        spinner = view.findViewById(R.id.spinner);
        String p = activity.sharedPreferences.getString("lang", "ENG");

        for (int i = 0; i < aa.length; i++) {
            if (aa[i].equals(p)) {
                spinner.setSelection(i);
                break;
            }
        }


        Button hide = view.findViewById(R.id.hide);

        // spinner.setSelection(2);

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> okk(isChecked));

        ApiCalling apiCalling = new ApiCalling(activity);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lang;
                if (position == 0) {
                    apiCalling.setLANGUAGE("ENG");
                    lang = "ENG";
                } else if (position == 1) {
                    // apiCalling.LANGUAGE="bn";
                    apiCalling.setLANGUAGE("bn");
                    lang = "bn";
                } else if (position == 2) {
                    apiCalling.setLANGUAGE("hi");
                    lang = "hi";

                } else if (position == 3) {
                    apiCalling.setLANGUAGE("de");
                    lang = "de";


                } else if (position == 4) {
                    apiCalling.setLANGUAGE("fr");
                    lang = "fr";

                } else if (position == 5) {
                    apiCalling.setLANGUAGE("es");
                    lang = "es";


                } else if (position == 6) {
                    apiCalling.setLANGUAGE("tl");
                    lang = "tl";

                } else {
                    apiCalling.setLANGUAGE("ENG");
                    lang = "ENG";
                }

                activity.myedit.putString("lang", lang);
                activity.myedit.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activity.bottomSheet.isHidden()) {
                    activity.bottomSheet.dismiss();
                }


            }
        });
        return view;
    }


    @SuppressLint("SetTextI18n")
    public void okk(boolean ischecked) {

        //final boolean isDarkModeOn = false;//sharedPreferences.getBoolean("isDarkModeOn", false);

        if (ischecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            aSwitch.setText("Disable Dark Mode");
            activity.bottomSheet.dismiss();
            activity.myedit.putBoolean("mode", true);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            aSwitch.setText("Enable Dark mode ");
            aSwitch.setChecked(false);
            activity.bottomSheet.dismiss();
            activity.myedit.putBoolean("mode", false);
        }

        activity.myedit.commit();

    }
}