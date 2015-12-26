package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c162 on 24/10/15.
 */
public class BooksFragment extends Fragment    {

    private static final String TAG = BooksFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private HostActivity activityHost;

    public static BooksFragment newInstance() {
        BooksFragment fragDesk = new BooksFragment();
        return fragDesk;
    }

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desk_book, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

//        etNotes = (EditText) view.findViewById(R.id.et_notes);
//        etNotes.setTypeface(Global.myTypeFace.getRalewayRegular());


    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

}
