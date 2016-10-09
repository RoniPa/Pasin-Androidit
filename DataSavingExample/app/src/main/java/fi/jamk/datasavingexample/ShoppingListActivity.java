package fi.jamk.datasavingexample;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingListActivity extends ListActivity
        implements AddShoppingItemFragment.OnAddShoppingItemListener
{
    SimpleCursorAdapter mAdapter;
    ShoppingListDatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbh = new ShoppingListDatabaseHelper(this);
        setContentView(R.layout.activity_shopping_list);

        ListView listView = getListView();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbh.deleteItem(
                        ((TextView)view.findViewById(R.id.idTextView))
                                .getText()
                                .toString());
                Cursor newC = dbh.getData();
                _outputSum(newC);
                Cursor old = mAdapter.swapCursor(newC);
                if (old != null) old.close();

                return false;
            }
        });

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {
                dbh.ID,
                dbh.NAME,
                dbh.COUNT,
                dbh.PRICE};
        int[] toViews = {
                R.id.idTextView,
                R.id.nameTextView,
                R.id.countTextView,
                R.id.priceTextView};

        mAdapter = new SimpleCursorAdapter(this,
                R.layout.shopping_list_item,
                dbh.getData(),
                fromColumns, toViews, 0);

        setListAdapter(mAdapter);
    }

    @Override
    public void saveShoppingItem(String[] itemData) {
        dbh.insertItem(
                itemData[0],
                Integer.parseInt(itemData[1]),
                Float.parseFloat(itemData[2]));
        Cursor newC = dbh.getData();
        _outputSum(newC);
        Cursor old = mAdapter.swapCursor(newC);
        if (old != null) old.close();
    }

    public void addButtonClicked(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(null);

        // Create and show the dialog.
        AddShoppingItemFragment frag = AddShoppingItemFragment.newInstance();
        frag.show(ft, "dialog");
    }

    private void _outputSum(Cursor c) {
        double sum = 0;
        while(c.moveToNext()) {
            sum += (c.getFloat(3) * c.getInt(2));
        }
        c.moveToFirst();
        Toast.makeText(this, "Total price: "+String.format("%.2f",sum+0.005), Toast.LENGTH_SHORT).show();
    }
}
