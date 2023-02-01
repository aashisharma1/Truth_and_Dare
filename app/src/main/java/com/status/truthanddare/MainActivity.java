package com.status.truthanddare;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.status.truthanddare.Adapter.SliderAdapter;
import com.status.truthanddare.modelclass.ImageSlider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    com.google.android.material.floatingactionbutton.FloatingActionButton button;
    BottomSheet bottomSheet;
    SharedPreferences.Editor myedit;
    SharedPreferences sharedPreferences;
    ViewPager2 viewPager2;
    private final Handler sliderHandler = new Handler();
    com.google.android.material.button.MaterialButton whatsapp, shareToApp, copyText,follow;
    TextView question;
    androidx.cardview.widget.CardView b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager2 = findViewById(R.id.viewPagerImageSlider);

        List<ImageSlider> sliderItems = new ArrayList<>();
        sliderItems.add(new ImageSlider(R.drawable.img2));
        sliderItems.add(new ImageSlider(R.drawable.img3));
        sliderItems.add(new ImageSlider(R.drawable.img1));
        sliderItems.add(new ImageSlider(R.drawable.img4));



        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
/*
        NetworkConnectionCheck networkConnectionCheck=new NetworkConnectionCheck(this);

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(ConnectivityManager.class);
        connectivityManager.requestNetwork(networkConnectionCheck.networkRequest,networkConnectionCheck.networkCallback);
*/
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
            }
        });


        sharedPreferences = getSharedPreferences("Dark", MODE_PRIVATE);
        myedit = sharedPreferences.edit();
        boolean mode = sharedPreferences.getBoolean("mode", false);
        if (mode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        b1 = findViewById(R.id.truth);
        whatsapp = findViewById(R.id.whatsappshare);
        shareToApp = findViewById(R.id.shareToApps);
        shareToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToApps(question.getText().toString());
            }
        });

        //follow button
        follow=findViewById(R.id.follow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            instgramMethod();


            }
        });

        // Copy Text button
        copyText = findViewById(R.id.copytext);
        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(question.getText().toString());
            }
        });


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsappShare(question.getText().toString());
            }
        });


        // bottomSheet.spinner.setSelection();


        b2 = findViewById(R.id.dare);
        button = findViewById(R.id.FButoon);

        question = findViewById(R.id.QuestionShow);
        animation(question);
        ApiCalling apiCalling = new ApiCalling(getApplicationContext());
        apiCalling.setLANGUAGE(sharedPreferences.getString("lang", "ENG"));
        apiCalling.apiCallingp();
        apiCalling.dareAns();

                    // Truth Button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  oTruth();

                question.setText(apiCalling.answer());
                apiCalling.apiCallingp();
               // sw.setImageResource(R.drawable.pro);
            }
        });

        //Dare Button

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Next Image",
                        Toast.LENGTH_LONG).show();
                apiCalling.dareAns();
                question.setText(apiCalling.answer());

                // oDare();
               // sw.setImageResource(R.drawable.pro1);
            }
        });

        // Bottom Sheet
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet = new BottomSheet();
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });


    }

    private void instgramMethod() {
        Uri uri = Uri.parse("http://instagram.com/Its_Aashi_pandit");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/Its_Aashi_pandit")));
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    void whatsappShare(String text) {

        //  String toNumber = "+91 80532 75720"; // contains spaces.
        //  toNumber = toNumber.replace("+", "").replace(" ", "");

        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra("jid", "@s.whatsapp.net");
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }

    void shareToApps(String text) {

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

// (Optional) Here we're setting the title of the content
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Truth Dare App");
        // sendIntent.setType("text/plain");

// (Optional) Here we're passing a content URI to an image to be displayed
        //  sendIntent.setData(contentUri);
        sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// Show the Sharesheet
        startActivity(Intent.createChooser(sendIntent, null));
    }

    void copyText(String text) {

// Gets a handle to the clipboard service.
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText(" ", text);
        Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show();
        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip);
    }
    Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }

    void animation(TextView textView){
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(900); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);
    }




}