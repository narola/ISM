package com.ism.teacher.fragments.tutorial;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.tutorial.PastTutorialAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;


/**
 * Created by c161 on --/10/15.
 */
public class PastTutorialsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse{

    private static final String TAG = PastTutorialsFragment.class.getSimpleName();


    //Views
    private RecyclerView rvPastTutorials;
    private TextView tvNoPastTutorials;

    //Objects
    private FragmentListener fragListener;

    private PastTutorialAdapter pastTutorialAdapter;

    public static PastTutorialsFragment newInstance() {
        PastTutorialsFragment pastTutorialsFragment = new PastTutorialsFragment();
        return pastTutorialsFragment;
    }

    public PastTutorialsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_past_tutorials, container, false);

        initGlobal(rootview);
        return rootview;
    }

    public void initGlobal(View rootview) {
        rvPastTutorials = (RecyclerView) rootview.findViewById(R.id.rv_past_tutorials);
        tvNoPastTutorials =(TextView)rootview.findViewById(R.id.tv_no_past_tutorials);
        rvPastTutorials.setHasFixedSize(true);
        rvPastTutorials.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        pastTutorialAdapter = new PastTutorialAdapter(getActivity());
        rvPastTutorials.setAdapter(pastTutorialAdapter);

        if (Utility.isInternetConnected(getActivity())) {
            callGroupAllocationApi();
        }

    }

    private void callGroupAllocationApi() {
        try {
            ((TeacherHostActivity) getActivity()).showProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(WebConstants.USER_ID_370);
            new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GROUP_ALLOCATION);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_PAST_TUTORIALS);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_PAST_TUTORIALS);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GROUP_ALLOCATION:
                    onResponseGetGroupAllocation(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetGroupAllocation(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getGroup().size() > 0) {

                        //setTutorialGroupDetails(responseHandler.getGroup());
                        pastTutorialAdapter.addAll(responseHandler.getGroup());
                    } else {
                        Utility.hideView(rvPastTutorials);
                        Utility.showView(tvNoPastTutorials);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetGroupAllocation Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetGroupAllocation : " + e.toString());
        }
    }
}