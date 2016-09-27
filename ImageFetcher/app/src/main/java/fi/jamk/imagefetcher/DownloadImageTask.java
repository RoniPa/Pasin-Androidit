package fi.jamk.imagefetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

/**
 * Downloads images and does nice stuff
 */
class DownloadImageTask extends AsyncTask<String,Void,Bitmap>
{
    private ImageView i;
    private TextView t;
    private ProgressBar b;
    private int ind;
    private String[] images;

    DownloadImageTask(ImageView i, TextView t, ProgressBar b, int ind, String[] images)
    {
        super();
        this.i = i;
        this.t = t;
        this.b = b;
        this.ind = ind;
        this.images = images;
    }

    // this is done in UI thread, nothing this time
    @Override
    protected void onPreExecute()
    {
        // show loading progress bar
        b.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        URL imageUrl;
        Bitmap bitmap = null;
        try {
            imageUrl = new URL(params[0]);
            InputStream in = imageUrl.openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception ex) {
            Log.e("<<LOADIMAGE>>", ex.getMessage());
        }
        return bitmap;
    }

    // this is done in UI thread
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        i.setImageBitmap(bitmap);
        t.setText("Image " + (ind + 1) + "/" + images.length);
        // hide loading progress bar
        b.setVisibility(View.INVISIBLE);
    }
}
