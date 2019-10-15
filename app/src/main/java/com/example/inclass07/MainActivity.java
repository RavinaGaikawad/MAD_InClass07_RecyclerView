package com.example.inclass07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTracksAsyncTask.IGetTracksData {
    TextView tv_limit;
    EditText editText;
    RadioGroup rg_rating;
    RadioButton rb_track;
    RadioButton rb_artist;
    ProgressBar pb_loading;
    SeekBar sb_limit;
    int progress = 5;
    String radioselected = "s_track_rating";
    ListView lv_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_limit = findViewById(R.id.tv_limit);
        editText = findViewById(R.id.editText);
        rg_rating = findViewById(R.id.rg_rating);
        rb_track = findViewById(R.id.rb_track);
        rb_artist = findViewById(R.id.rb_artist);
        pb_loading = findViewById(R.id.pb_loading);
        sb_limit = findViewById(R.id.sb_limit);
        lv_results = findViewById(R.id.lv_results);

        pb_loading.setVisibility(View.GONE);

        setTitle("MusixMatch Track Search");

        rb_track.setChecked(true);

        sb_limit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                tv_limit.setText("Limit: "+progress);
                Log.d("bagh", "Progress bar: " +progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //radio group
        rg_rating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.rb_track)
                {
                    radioselected = "s_track_rating";
                    rb_track.setChecked(true);

                    if(isConnected()){
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParams("q", editText.getText().toString());
                        requestParams.addParams("page_size", String.valueOf(progress));
                        requestParams.addParams(radioselected, "desc");

                        new GetTracksAsyncTask(requestParams, MainActivity.this).execute("http://api.musixmatch.com/ws/1.1/track.search");

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Not Connected to Internet.", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_artist){
                    radioselected = "s_artist_rating";
                    rb_artist.setChecked(true);

                    if(isConnected()){
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParams("q", editText.getText().toString());
                        requestParams.addParams("page_size", String.valueOf(progress));
                        requestParams.addParams(radioselected, "desc");

                        new GetTracksAsyncTask(requestParams, MainActivity.this).execute("http://api.musixmatch.com/ws/1.1/track.search");

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Not Connected to Internet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        findViewById(R.id.bt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    RequestParams requestParams = new RequestParams();
                    requestParams.addParams("q", editText.getText().toString());
                    requestParams.addParams("page_size", String.valueOf(progress));
                    requestParams.addParams(radioselected, "desc");

                    new GetTracksAsyncTask(requestParams, MainActivity.this).execute("http://api.musixmatch.com/ws/1.1/track.search");

                }
                else {
                    Toast.makeText(MainActivity.this, "Not Connected to Internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void GetTracksData(final ArrayList<TrackDetails> trackDetails) {

        Log.d("bagh", "inside interface");
        TracksArrayAdapter tracksArrayAdapter = new TracksArrayAdapter(MainActivity.this, R.layout.track_list, trackDetails);
        lv_results.setAdapter(tracksArrayAdapter);

        lv_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = trackDetails.get(i).url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
