


package com.cst2335.ghar0035;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ID = "ID";
    public static final String IS_SENT = "isSent";
    public static final String MESSAGE = "message";

    // TODO: Rename and change types of parameters
    private String id;
    private boolean isSent;
    private String message;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = Long.toString(getArguments().getLong(ID));
            isSent = getArguments().getBoolean(IS_SENT);
            message = getArguments().getString(MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View newView = inflater.inflate(R.layout.fragment_details, container,false);

        TextView msgTextView = newView.findViewById(R.id.msgTv1);
        TextView idTextView = newView.findViewById(R.id.idTv2);
        CheckBox isSendChk = newView.findViewById(R.id.isSendChk);
        Button hideButton = (Button) newView.findViewById(R.id.hideBtn);

        isSendChk.setChecked(isSent);
        msgTextView.setText(message);
        idTextView.setText("ID = " + id);

        hideButton.setOnClickListener((view) -> {
               removeFragment();
            });

        // Inflate the layout for this fragment
        return newView;
    }

    public void removeFragment() {
        getParentFragmentManager().beginTransaction()
                .remove(DetailsFragment.this).commit();
    }
}