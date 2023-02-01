package com.status.truthanddare.Adapter;

import static android.media.CamcorderProfile.get;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.makeramen.roundedimageview.RoundedImageView;
import com.status.truthanddare.R;
import com.status.truthanddare.modelclass.ImageSlider;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private final List<ImageSlider> sliderItems;
   Handler ImageSlider=new Handler();
    private final ViewPager2 viewPager2;

    public SliderAdapter(List<ImageSlider> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems=sliderItems;
        this.viewPager2=viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.sliderimage, parent, false
                ) );
    }
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if (position == sliderItems.size()- 2){
            viewPager2.post(runnable);
        }
    }
    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
    class SliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
        void setImage(ImageSlider sliderItems){
//use glide or picasso in case you get image from internet
            imageView.setImageResource(sliderItems.getImage());
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}


