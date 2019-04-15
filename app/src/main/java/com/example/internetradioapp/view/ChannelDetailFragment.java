package com.example.internetradioapp.view;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.internetradioapp.R;
import com.example.internetradioapp.model.ChannelRepository;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ChannelDetailFragment extends Fragment {

    View view;
    TextView tvTitle, tvDj, tvDescription, tvDjEmail, tvListeners, tvGenre;
    ImageView ivThumbnail;
    Button btnPlay;
    Bundle bundle;

    public ChannelDetailFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_channel_detail, container, false);

        setUpViews();

        Log.d(this.getClass().getSimpleName(), "set up key listener");

        return view;
    }

    private void setUpViews() {
        tvTitle = view.findViewById(R.id.tv_title);
        tvDj = view.findViewById(R.id.tv_dj);
        tvDescription = view.findViewById(R.id.tv_description);
        tvDjEmail = view.findViewById(R.id.tv_dj_email);
        tvListeners = view.findViewById(R.id.tv_listeners);
        tvGenre = view.findViewById(R.id.tv_genre);
        ivThumbnail = view.findViewById(R.id.iv_channel_thumbnail);
        btnPlay = view.findViewById(R.id.btn_play);


        bundle = this.getArguments();

        tvTitle.setText(bundle.getString("title"));
        tvDj.setText(bundle.getString("dj"));
        tvDescription.setText(bundle.getString("description"));
        tvDjEmail.setText(bundle.getString("djEmail"));
        tvListeners.setText(bundle.getString("listeners"));
        tvGenre.setText(bundle.getString("genre"));
        Picasso.get().load(bundle.getString("thumbnailUrlLarge")).into(ivThumbnail);

//        final MediaPlayer mediaPlayer = setUpMediaPlayer();
        btnPlay.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View view) {
////                       Intent intent = new Intent(Intent.ACTION_VIEW);
////                       intent.setDataAndType(Uri.parse(bundle.getString("previewUrl")), "audio/*");
////                       startActivity(intent);
//                       if (mediaPlayer.isPlaying()){
//                           mediaPlayer.stop();
//                       }
//                       else {
//                            startMediaPlayer(mediaPlayer);
//                       }
                       Toast.makeText(getContext(), "couldn't set up opening streaming file", Toast.LENGTH_SHORT).show();
                   }
               });

    }

    private void startMediaPlayer(MediaPlayer mediaPlayer){
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    private MediaPlayer setUpMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            Log.d("media player", bundle.getString("previewUrl"));
            mediaPlayer.setDataSource(getActivity().getApplicationContext()
                    /*getContext().getApplicationContext()*/,
                    Uri.parse(bundle.getString("previewUrl")));
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mediaPlayer;
    }

}
