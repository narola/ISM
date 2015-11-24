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

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.adapter.TrialExamsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;


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
    private Fragment fragment;

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
        onClicks();
        return view;
    }

    private void onClicks() {
//        gridExams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                arrayList.getItem(position);
////                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS);
//
//            }
//        });
    }

    private void initGlobal() {
        gridExams = (GridView) view.findViewById(R.id.grid_trial);// The number of Columns
        Attribute attribute = new Attribute();
        attribute.setRole("4");
        attribute.setUserId("370");
        ((AuthorHostActivity) getActivity()).startProgress();
        new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                .execute(WebConstants.GETALLEXAM);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_TRIAL);
                Debug.i(TAG, "attach");
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
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        ((AuthorHostActivity) getActivity()).stopProgress();
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (API_METHOD == WebConstants.GETALLEXAM) {
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (responseHandler.getExams().size() != 0) {
                        arrayList = new TrialExamsAdapter(getActivity(), responseHandler, this);
                        gridExams.setAdapter(arrayList);
                    }
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString() + "");
        }


    }

//    public void hadleClick(int position) {
//        ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS);
//    }
}
