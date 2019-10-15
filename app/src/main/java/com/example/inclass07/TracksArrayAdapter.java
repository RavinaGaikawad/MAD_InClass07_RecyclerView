package com.example.inclass07;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TracksArrayAdapter extends ArrayAdapter<TrackDetails> {
    public TracksArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TrackDetails> tracks) {
        super(context, resource, tracks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TrackDetails trackDetails = getItem(position);
        View view = convertView;
        ViewHolder viewHolder;

        if(view == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_list,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_trackname = convertView.findViewById(R.id.tv_trackname);
            viewHolder.tv_albumname = convertView.findViewById(R.id.tv_album);
            viewHolder.tv_artistname = convertView.findViewById(R.id.tv_artist);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_trackname.setText("Track: " + trackDetails.getTrackname());
        viewHolder.tv_date.setText("Date: "+String.format(getDate(trackDetails.getDate())));
        //viewHolder.tv_date.setText("Date: "+trackDetails.getDate());
        viewHolder.tv_albumname.setText("Album: "+trackDetails.getAlbum());
        viewHolder.tv_artistname.setText("Artist: "+trackDetails.getArtist());
        return convertView;
    }

    public class ViewHolder{
        TextView tv_trackname;
        TextView tv_albumname;
        TextView tv_artistname;
        TextView tv_date;
    }

    public String getDate(String inputdate){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");

        Date date = null;
        try
        {
            date = input.parse(inputdate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(date);
        return formatted;
    }
}
