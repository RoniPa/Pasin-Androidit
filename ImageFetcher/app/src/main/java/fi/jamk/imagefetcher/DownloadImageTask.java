package fi.jamk.imagefetcher;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    protected Bitmap doInBackground(String... params) {
        return null;
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
