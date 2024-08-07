package com.example.androidfinal;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //CREDIT TO Jomie (https://open.spotify.com/artist/5F4cFdG9NvJMnHXJkmqw9g) for all the music
    Integer[] songImages = {R.drawable.ahoy, R.drawable.aiwytl, R.drawable.arguewithme ,R.drawable.bother, R.drawable.disappointingeyes, R.drawable.empathize,
            R.drawable.heartburn, R.drawable.hitch, R.drawable.lately, R.drawable.littledoyouknow, R.drawable.lostmyvoice, R.drawable.milkinthemorning,
            R.drawable.otherside, R.drawable.senses, R.drawable.sentimentality, R.drawable.soso, R.drawable.spiderman, R.drawable.stuck,
            R.drawable.tangledcables, R.drawable.tpatt, R.drawable.wtp};
    Integer[] songNames = {R.string.ahoy, R.string.aiwytl, R.string.arguewithme ,R.string.bother, R.string.disappointingeyes, R.string.empathize,
            R.string.heartburn, R.string.hitch, R.string.lately, R.string.littledoyouknow, R.string.lostmyvoice, R.string.milkinthemorning,
            R.string.otherside, R.string.senses, R.string.sentimentality, R.string.soso, R.string.spiderman, R.string.stuck,
            R.string.tangledcables, R.string.tpatt, R.string.wtp};
    Integer[] songs = {R.raw.ahoy, R.raw.aiwytl, R.raw.arguewithme ,R.raw.bother, R.raw.disappointingeyes, R.raw.empathize,
            R.raw.heartburn, R.raw.hitch, R.raw.lately, R.raw.littledoyouknow, R.raw.lostmyvoice, R.raw.milkinthemorning,
            R.raw.otherside, R.raw.senses, R.raw.sentimentality, R.raw.soso, R.raw.spiderman, R.raw.stuck,
            R.raw.tangledcables, R.raw.tpatt, R.raw.wtp};
    ImageView pic;
    int selectedSong = -1;
    MediaPlayer mpJomie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button play = (Button) findViewById(R.id.btnPlay);
        GridView grid = (GridView) findViewById(R.id.gridView);
        final ImageView pic = (ImageView) findViewById(R.id.imgLarge);
        pic.setImageResource(R.drawable.jomie);
        grid.setAdapter(new ImageAdapter(this));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSong = position;
                String songName = getString(songNames[position]);
                Toast.makeText(getBaseContext(), "Selected Song is: " + songName, Toast.LENGTH_LONG).show();
                pic.setImageResource(songImages[position]);

                if (mpJomie != null) {
                    mpJomie.stop();
                    mpJomie.release();
                    mpJomie = null;
                }
                mpJomie = MediaPlayer.create(MainActivity.this, songs[selectedSong]);
                play.setText(R.string.btnPlay);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedSong != -1) {
                    if (mpJomie != null) {
                        if (mpJomie.isPlaying()) {
                            mpJomie.pause();
                            play.setText(R.string.btnPlay);
                        } else {
                            mpJomie.start();
                            play.setText(R.string.pause);
                        }
                    } else {
                        mpJomie = MediaPlayer.create(MainActivity.this, songs[selectedSong]);
                        mpJomie.start();
                        play.setText(R.string.pause);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Select a song", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button stop = (Button) findViewById(R.id.btnStop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpJomie != null && mpJomie.isPlaying()) {
                    mpJomie.stop();
                    mpJomie.release();
                    mpJomie = null;
                    play.setText(R.string.btnPlay);
                }
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return songImages.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            pic = new ImageView(context);
            pic.setImageResource(songImages[position]);
            pic.setScaleType(ImageView.ScaleType.FIT_XY);
            pic.setLayoutParams(new ViewGroup.LayoutParams(330, 300));
            return pic;
        }
    }
}