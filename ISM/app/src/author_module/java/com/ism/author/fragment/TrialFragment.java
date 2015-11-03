package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.ism.R;
import com.ism.author.AuthorHostActivity;
import com.ism.author.adapter.TrialExamsAdapter;
import com.ism.author.model.Data;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/**
 * Created by c166 on 29/10/15.
 */
public class TrialFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = TrialFragment.class.getSimpleName();
    private View view;
    private TrialExamsAdapter arrayList;
    private FragmentListener fragListener;
    private GridView gridExams;
    private GridLayoutManager mLayoutManager;

    public static TrialFragment newInstance() {
        TrialFragment fragTrial = new TrialFragment();
        return fragTrial;
    }

    public TrialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        gridExams = (GridView) view.findViewById(R.id.grid_trial);// The number of Columns
        Data data = new Data();
        data.setRole("4");
        data.setUserId("370");
        new WebserviceWrapper(getActivity(), data, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                .execute(WebserviceWrapper.GETALLEXAM);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_TRIAL);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_TRIAL);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        try {
            ResponseObject responseObject = (ResponseObject) object;

            if (responseObject.getStatus().equals(ResponseObject.SUCCESS)) {
                if (!responseObject.getData().equals("")) {
                    arrayList = new TrialExamsAdapter(getActivity(), responseObject, this);
                    gridExams.setAdapter(arrayList);


                }
            } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {
                Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString() + "");
        }


    }
}
