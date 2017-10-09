package com.vaijyant.musicsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vaijy on 2017-10-08.
 */

public class TrackAdapter extends ArrayAdapter<Track> {
    List<Track> mData;
    Context mContext;
    int mResource;
String TAG = "vt";
    public TrackAdapter(Context context, int resource, List<Track> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

        }
        Track track = mData.get(position);

        TextView viewName = convertView.findViewById(R.id.viewName);
        viewName.setText(track.getName());

        TextView viewArtist = convertView.findViewById(R.id.viewArtist);
        viewArtist.setText(track.getArtist());

        ImageView imageViewTrack = convertView.findViewById(R.id.imageViewTrack);
        if(track.getSmallImageURL()!= null) {
            new ImageAsyncTask().execute(track.getSmallImageURL(), imageViewTrack);
        }

        final ImageButton imgBtnFavourite  = convertView.findViewById(R.id.imgBtnFavourite);
        if(mContext.toString().contains("MainActivity") && mData != null) {
            imgBtnFavourite.setImageResource(android.R.drawable.btn_star_big_on);
            imgBtnFavourite.setTag("on");
        }

        imgBtnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgBtnFavourite.getTag().equals("off")){
                    imgBtnFavourite.setImageResource(android.R.drawable.btn_star_big_on);
                    imgBtnFavourite.setTag("on");
                    Data.favoriteTrackArrayList.add(mData.get(position));
                    Toast.makeText(mContext, "Track added to favorites.", Toast.LENGTH_SHORT).show();
                }
                else if(imgBtnFavourite.getTag().equals("on")){
                    imgBtnFavourite.setImageResource(android.R.drawable.btn_star_big_off);
                    imgBtnFavourite.setTag("off");
                    Data.favoriteTrackArrayList.remove(mData.get(position));
                    Toast.makeText(mContext, "Track removed form favorites.", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();

            }
        });
        return convertView;
    }
}
