package fi.jamk.golfarolf;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

class GolfCourseListAdapter extends RecyclerView.Adapter<GolfCourseListAdapter.ViewHolder> {
    private JSONArray dataSet;
    private String dataRoot;
    private Context ctx;

    GolfCourseListAdapter(JSONArray dataSet, Context ctx) {
        this.ctx = ctx;
        this.dataRoot = ctx.getResources().getString(R.string.data_root_url);
        this.dataSet = dataSet;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView mCardView;
        ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }


    @Override
    public GolfCourseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.golf_course_item_view, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Picasso.with(ctx)
                    .load(dataRoot + dataSet.getJSONObject(position).getString("Kuva"))
                    .into((ImageView)holder.mCardView.findViewById(R.id.image_view));

            ((TextView)holder.mCardView.findViewById(R.id.name_textfield))
                    .setText(dataSet.getJSONObject(position).getString("Kentta"));
            ((TextView)holder.mCardView.findViewById(R.id.address_textfield))
                    .setText(dataSet.getJSONObject(position).getString("Osoite"));
            ((TextView)holder.mCardView.findViewById(R.id.phone_textfield))
                    .setText(dataSet.getJSONObject(position).getString("Puhelin"));
            ((TextView)holder.mCardView.findViewById(R.id.email_textfield))
                    .setText(dataSet.getJSONObject(position).getString("Sahkoposti"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.length();
    }
}
