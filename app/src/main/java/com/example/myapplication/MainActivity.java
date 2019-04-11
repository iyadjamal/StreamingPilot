package com.example.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.exoplayer2.ui.PlayerView;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    static YouTubeExtractor youTubeExtractor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        extractYoutubeUrl();
    }
    private void extractYoutubeUrl() {
        @SuppressLint("StaticFieldLeak") YouTubeExtractor mExtractor = new YouTubeExtractor(this) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
                String url="";
                if (sparseArray != null) {
                    Log.d("iyad",sparseArray.toString());
                    for (int i=0;i<sparseArray.size();i++) {
                        int key = sparseArray.keyAt(i);
                        if(sparseArray.get(key).getMeta().getAudioBitrate()!=-1&&sparseArray.get(key).getMeta().getExt().equals("m4a"))
                        {
                            url=sparseArray.get(key).getUrl();
                            Log.d("iyad","url= "+url);
                        }
                    }
                    playVideo(url);
                }
            }
        };
        mExtractor.extract("https://www.youtube.com/watch?v=_3vpiTgG59A", true, true);
    }
    private void playVideo(String downloadUrl) {
        PlayerView mPlayerView = findViewById(R.id.mPlayerView);
        mPlayerView.setPlayer(ExoPlayerManager.getSharedInstance(MainActivity.this).getPlayerView().getPlayer());
        ExoPlayerManager.getSharedInstance(MainActivity.this).playStream(downloadUrl);

    }
}
