package com.example.inclass07;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.ocpsoft.prettytime.PrettyTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TracksArrayAdapterRecyclerView extends RecyclerView.Adapter<TracksArrayAdapterRecyclerView.MyViewHolder> {
    ArrayList<TrackDetails> trackDetails;
    String prettyTimeString;
    Date inputDate;
    Date outputDate;
    String formattedDateString;

    public TracksArrayAdapterRecyclerView(ArrayList<TrackDetails> trackDetails) {
        this.trackDetails = trackDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TrackDetails details = trackDetails.get(position);

        holder.tv_albumname.setText("Album: " + details.getAlbum());
        holder.tv_trackname.setText("Track: " + details.getTrackname());
        holder.tv_artistname.setText("Artist: " + details.getArtist());
        holder.tv_date.setText("Date: "+String.format(getDate(details.getDate())) );
        holder.trackDetails = trackDetails.get(position);
    }

    @Override
    public int getItemCount() {
        return trackDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TrackDetails trackDetails;
        TextView tv_trackname;
        TextView tv_albumname;
        TextView tv_artistname;
        TextView tv_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            tv_albumname = itemView.findViewById(R.id.tv_album);
            tv_trackname = itemView.findViewById(R.id.tv_trackname);
            tv_artistname = itemView.findViewById(R.id.tv_artist);
            tv_date = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = trackDetails.url;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
            });
        }
    }

    public String getDate(String inputdate){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");
        try {
            inputDate=input.parse(inputdate);
            formattedDateString=output.format(inputDate);

            Log.d("bagh",formattedDateString);
            outputDate=output.parse(formattedDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrettyTime prettyTime=new PrettyTime();
        prettyTimeString=prettyTime.format(outputDate);
        return formattedDateString;
    }


}
