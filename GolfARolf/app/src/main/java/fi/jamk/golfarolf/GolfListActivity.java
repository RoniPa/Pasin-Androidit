package fi.jamk.golfarolf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GolfListActivity extends AppCompatActivity {
    public final static String COURSE_DATA = "fi.jamk.golfarolf.COURSE_DATA";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private JSONArray mJSONData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context ctx = this;

        setContentView(R.layout.activity_golf_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.golf_courses_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(
            new RecyclerViewOnClickListener(this, mRecyclerView, new RecyclerViewOnClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(ctx, GolfDetailsActivity.class);

                    try {
                        intent.putExtra(COURSE_DATA, mJSONData.getJSONObject(position).toString());
                        startActivity(intent);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {
                    // ... //
                }
            })
        );

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(null);

        fetchCourseData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_golf_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchCourseData() {
        final Context ctx = this;

        FetchDataTask fetchData = new FetchDataTask();
        fetchData.setDataDownloadListener(new FetchDataTask.DataDownloadListener()
        {
            @Override
            public void dataDownloadedSuccessfully(JSONObject data) {
                // specify an adapter (see also next example)
                try {
                    mJSONData = data.getJSONArray("kentat");
                    mAdapter = new GolfCourseListAdapter(mJSONData, ctx);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void dataDownloadFailed() {
                // handler failure (e.g network not available etc.)
                System.out.println("Data download has failed");
            }
        });
        fetchData.execute(getString(R.string.courses_url));
    }
}
