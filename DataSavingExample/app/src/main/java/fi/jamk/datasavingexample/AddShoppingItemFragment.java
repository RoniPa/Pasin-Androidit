package fi.jamk.datasavingexample;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddShoppingItemFragment.OnAddShoppingItemListener} interface
 * to handle interaction events.
 * Use the {@link AddShoppingItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddShoppingItemFragment extends DialogFragment
    implements View.OnClickListener
{
    private OnAddShoppingItemListener mListener;
    private View view;

    public AddShoppingItemFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddShoppingItemFragment.
     */
    public static AddShoppingItemFragment newInstance() {
        AddShoppingItemFragment fragment = new AddShoppingItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // ... //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_shopping_item, container, false);
        Button btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddShoppingItemListener) {
            mListener = (OnAddShoppingItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnAddShoppingItemListener) {
            mListener = (OnAddShoppingItemListener) context;
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

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            String[] itemData = new String[]{
                    ((EditText)view.findViewById(R.id.editText5)).getText().toString(),
                    ((EditText)view.findViewById(R.id.editText6)).getText().toString()};
            System.out.println(itemData[0]+itemData[1]);
            mListener.saveShoppingItem(itemData);
        }
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
    public interface OnAddShoppingItemListener {
        void saveShoppingItem(String[] itemData);
    }
}
