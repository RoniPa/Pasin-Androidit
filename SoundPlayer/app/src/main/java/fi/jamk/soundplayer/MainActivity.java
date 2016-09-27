package fi.jamk.soundplayer;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ListView listview;
    private String mediaPath;
    private List<String> songs = new ArrayList<>();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private LoadSongsTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView);
        mediaPath = Environment.getExternalStorageDirectory()
                .getPath() + "/Music/";
        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(songs.get(position));
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException ex) {
                            Log.e("Cannot play audio", ex.toString());
                        }
                    }
                });

        task = new LoadSongsTask();
        task.execute();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (mediaPlayer.isPlaying()) mediaPlayer.reset();
    }

    private class LoadSongsTask extends AsyncTask<Void, String, Void>
    {
        private List<String> loadedSongs = new ArrayList<>();

        @Override
        protected void onPreExecute()
        {
            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            updateSongListRecursive(new File(mediaPath));
            return null;
        }

        @Override
        protected void onPostExecute(Void args)
        {
            ArrayAdapter<String> songList =
                    new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1, loadedSongs);

            listview.setAdapter(songList);
            songs = loadedSongs;

            Toast.makeText(getApplicationContext(),
                    "Songs="+songs.size(), Toast.LENGTH_LONG).show();
        }

        private void updateSongListRecursive(File path)
        {
            if (path.isDirectory()) {
                int l = path.listFiles().length;
                for (int i = 0; i < l; i++) {
                    File file = path.listFiles()[i];
                    updateSongListRecursive(file);
                }
            } else {
                String name = path.getAbsolutePath();
                publishProgress(name);
                if (name.endsWith(".mp3")) {
                    loadedSongs.add(name);
                }
            }
        }

    }
}
