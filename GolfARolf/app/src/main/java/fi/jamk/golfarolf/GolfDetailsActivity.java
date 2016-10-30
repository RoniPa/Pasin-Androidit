package fi.jamk.golfarolf;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class GolfDetailsActivity extends AppCompatActivity {
    private JSONObject courseElement;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golf_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();

        try {
            courseElement = new JSONObject(intent.getStringExtra(GolfListActivity.COURSE_DATA));
            toolbar.setTitle(courseElement.getString("Kentta"));
            setDataToViews(courseElement);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setDataToViews(JSONObject data)
            throws JSONException
    {
        Picasso.with(this)
                .load(getString(R.string.data_root_url) + data.getString("Kuva"))
                .into((ImageView)findViewById(R.id.main_imageview));

        ((TextView)findViewById(R.id.address_textview)).setText(data.getString("Osoite"));
        ((TextView)findViewById(R.id.phone_textview)).setText(data.getString("Puhelin"));
        ((TextView)findViewById(R.id.email_textview)).setText(data.getString("Sahkoposti"));
        ((TextView)findViewById(R.id.description_textview)).setText(data.getString("Kuvaus"));
        ((TextView)findViewById(R.id.homepage_textview)).setText(data.getString("Webbi"));

        lat = data.getDouble("lat");
        lng = data.getDouble("lng");

        findViewById(R.id.location_textview)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMap();
                }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMap() {
        Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lng+"?q="+lat+","+lng);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
