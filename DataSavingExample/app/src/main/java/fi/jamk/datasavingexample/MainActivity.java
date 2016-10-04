package fi.jamk.datasavingexample;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
        implements TextEntryDialogFragment.OnFragmentInteractionListener {

    private static final String TEXTVIEW_STATEKEY = "textViewState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView1);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TEXTVIEW_STATEKEY)) {
                String text = savedInstanceState.getString(TEXTVIEW_STATEKEY);
                textView.setText(text);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        TextView textView = (TextView) findViewById(R.id.textView1);
        saveInstanceState.putString(TEXTVIEW_STATEKEY, textView.getText().toString());
    }

    @Override
    public void onPositiveInteraction(DialogFragment frg, String txt) {
        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setText(txt);
    }

    @Override
    public void onNegativeInteraction(DialogFragment frg) {
        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT)
                .show();
    }

    public void buttonClicked(View view) {
        TextEntryDialogFragment eDialog = new TextEntryDialogFragment();
        eDialog.show(getFragmentManager(), "Text Dialog");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
