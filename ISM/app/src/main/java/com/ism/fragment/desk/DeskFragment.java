package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.GridAdaptor;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c161 on --/10/15.
 */
public class DeskFragment extends Fragment implements HostActivity.InsertSymbolListener {

    private static final String TAG = DeskFragment.class.getSimpleName();

    private View view;

    private FragmentListener fragListener;
    private EditText etNotes;
    private HostActivity activityHost;
    GridAdaptor gridAdaptor;

    public static DeskFragment newInstance() {
        DeskFragment fragDesk = new DeskFragment();
        return fragDesk;
    }

    public DeskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desk, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        etNotes = (EditText) view.findViewById(R.id.et_notes);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            activityHost.setInsertSymbolListener(this);
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_DESK);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_DESK);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void insertSymbol(String symbol) {
        etNotes.setText(etNotes.getText().toString()+symbol);
        etNotes.setSelection(etNotes.getText().toString().length());
    }
}
