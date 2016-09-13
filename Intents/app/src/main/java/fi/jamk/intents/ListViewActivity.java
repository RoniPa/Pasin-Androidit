package fi.jamk.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listview = (ListView) findViewById(R.id.listView);
        String[] phones = new String[]{
                "Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu",
                "Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu"
        };

        final ArrayList<String> list = new ArrayList<>();

        int l = phones.length - 1;
        for (int i = l; i >= 0; i--) {
            list.add(phones[i]);
        }

        ArrayAdapter adapter = new PhoneArrayAdapter(this, list);
        listview.setAdapter(adapter);

        // item listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get list row data (now String as a phone name)
                String phone = list.get(position);

                // create an explicit intent
                Intent intent = new Intent(ListViewActivity.this, PhoneDetailsActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
    }
}
