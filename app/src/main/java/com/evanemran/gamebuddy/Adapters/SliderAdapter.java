package com.evanemran.gamebuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;

import com.evanemran.gamebuddy.Models.GameDetailResponse;
import com.evanemran.gamebuddy.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    Context context;
    List<GameDetailResponse.Screenshot> screenshots;

    public SliderAdapter(Context context, List<GameDetailResponse.Screenshot> screenshots) {
        this.context = context;
        this.screenshots = screenshots;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        Picasso.get().load(screenshots.get(position).image).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return screenshots.size();
    }

    public class Holder extends  SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }
}
