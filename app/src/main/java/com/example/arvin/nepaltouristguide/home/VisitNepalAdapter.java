package com.example.arvin.nepaltouristguide.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arvin.nepaltouristguide.R;
import com.example.arvin.nepaltouristguide.model.ApiResponse;
import com.example.arvin.nepaltouristguide.placeOptions.PlacesOptions;
import com.squareup.picasso.Picasso;

public class VisitNepalAdapter extends RecyclerView.Adapter<VisitNepalAdapter.NepalAdapterViewHolder> {

    ApiResponse mApiResponse;
    Context mContext;

    public VisitNepalAdapter(ApiResponse mApiResponse, Context mContext) {
        this.mApiResponse = mApiResponse;
        this.mContext = mContext;
    }

    @Override
    public NepalAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_layout, parent, false);
        return new NepalAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NepalAdapterViewHolder holder, int position) {

        try {
            String photoReference = mApiResponse.getResults().get(position).getPhotos().get(0).getPhotoReference();
            String imageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=" + photoReference + "&key=AIzaSyBT2bl_XWXG7-fsWtCNyGrTD8wFxaBxbTc";

            Picasso.with(holder.place_image.getContext()).load(imageURL).into(holder.place_image);
            holder.place_name.setText(mApiResponse.getResults().get(position).getName());

        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return mApiResponse.getResults().size();
    }

    class NepalAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView place_image;
        TextView place_name;

        public NepalAdapterViewHolder(View itemView) {
            super(itemView);
            place_image = itemView.findViewById(R.id.place_picture);
            place_name = itemView.findViewById(R.id.place_name);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), PlacesOptions.class);
            intent.putExtra("place_photo", mApiResponse.getResults().get(getAdapterPosition()).getPhotos().get(0).getPhotoReference());
            intent.putExtra("place_name", mApiResponse.getResults().get(getAdapterPosition()).getName());
            v.getContext().startActivity(intent);
            Toast.makeText(mContext, mApiResponse.getResults().get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

        }
    }


}