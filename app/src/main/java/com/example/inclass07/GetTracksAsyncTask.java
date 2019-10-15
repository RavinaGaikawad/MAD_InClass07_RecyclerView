package com.example.inclass07;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetTracksAsyncTask extends AsyncTask<String ,Void, ArrayList<TrackDetails>> {
    RequestParams requestParams;
    IGetTracksData getTracksData;
    MainActivity mainActivity;

    public GetTracksAsyncTask(RequestParams requestParams, IGetTracksData getTracksData) {
        this.requestParams = requestParams;
        this.getTracksData = getTracksData;
    }

    @Override
    protected ArrayList<TrackDetails> doInBackground(String... params) {
        HttpURLConnection connection = null;
        ArrayList<TrackDetails> trackDetails = new ArrayList<>();

        URL url = null;
        try {
            url = new URL(requestParams.GetEncodedUrl(params[0]));
            Log.d("bagh", url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("bagh", "inside doin backgrounf");
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                JSONObject root = new JSONObject(json);
                JSONObject messages = root.getJSONObject("message");
                JSONObject body = messages.getJSONObject("body");
                JSONArray track_list = body.getJSONArray("track_list");

               for (int i =0 ; i < track_list.length(); i++){
                   JSONObject trackupper = track_list.getJSONObject(i);
                   JSONObject track = trackupper.getJSONObject("track");
                   TrackDetails trackDetails1 = new TrackDetails();
                   trackDetails1.setTrackname(track.getString("track_name"));
                   trackDetails1.setAlbum(track.getString("album_name"));
                   trackDetails1.setArtist(track.getString("artist_name"));
                   trackDetails1.setDate(track.getString("updated_time"));
                   trackDetails1.setUrl(track.getString("track_share_url"));
                   trackDetails.add(trackDetails1);
               }

               Log.d("bagh", trackDetails.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return trackDetails;
    }

    @Override
    protected void onPostExecute(ArrayList<TrackDetails> trackDetails) {
        super.onPostExecute(trackDetails);
        getTracksData.GetTracksData(trackDetails);
    }

    public interface IGetTracksData{
        public void GetTracksData(ArrayList<TrackDetails> trackDetails);
    }
}
