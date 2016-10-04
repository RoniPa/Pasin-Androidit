package fi.jamk.datasavingexample;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * A simple {@link DialogFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TextEntryDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TextEntryDialogFragment extends DialogFragment {

    private String dialogTitle = "Give a new text";
    OnFragmentInteractionListener mListener;

    public TextEntryDialogFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.textentry_dialog, null);

        builder.setView(dialogView)
                .setTitle(dialogTitle)
                .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mListener == null)
                            throw new RuntimeException("OnFragmentInteractionListener is not implemented!");

                        EditText editText = (EditText) dialogView.findViewById(R.id.editText);

                        String text = editText.getText().toString();
                        mListener.onPositiveInteraction(
                                TextEntryDialogFragment.this,
                                text);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener == null)
                            throw new RuntimeException("OnFragmentInteractionListener is not implemented!");

                        mListener.onNegativeInteraction(TextEntryDialogFragment.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onPositiveInteraction(DialogFragment frg, String txt);
        void onNegativeInteraction(DialogFragment frg);
    }
}
