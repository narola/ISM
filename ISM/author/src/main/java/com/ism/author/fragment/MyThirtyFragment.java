package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.MyThirtyAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Exams;

import java.util.ArrayList;

/**
 * Created by c166 on 11/12/15.
 */
/*This fragment will use for both trial section and my30 section*/
public class MyThirtyFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = MyThirtyFragment.class.getSimpleName();
    private View view;


    public static MyThirtyFragment newInstance() {
        MyThirtyFragment myThirtyFragment = new MyThirtyFragment();
        return myThirtyFragment;
    }

    public MyThirtyFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvMythirtyList;
    private MyThirtyAdapter myThirtyAdapter;
    private ArrayList<Exams> arrListExams = new ArrayList<Exams>();
    private TextView tvNoDataMsg;
    private FragmentListener fragListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mythirty, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        rvMythirtyList = (RecyclerView) view.findViewById(R.id.rv_mythirty_list);
        myThirtyAdapter = new MyThirtyAdapter(getActivity());

        rvMythirtyList.setHasFixedSize(true);
        rvMythirtyList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvMythirtyList.setAdapter(myThirtyAdapter);

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(View.GONE);
        tvNoDataMsg.setText(getString(R.string.no_exams));

        callApiGetAllExams();
    }

    private void callApiGetAllExams() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
                request.setUserId(Global.strUserId);
                request.setRole(Global.role);

                if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_TRIAL) {
                    request.setExamCategory(getString(R.string.strtrial));
                } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_MYTHIRTY) {
                    request.setExamCategory(getString(R.string.strmy30th));
                }
//                request.setExamCategory("");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLASSIGNMENTS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.GETALLASSIGNMENTS:
                    onResponseGetAllExams(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseGetAllExams(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getExams().size() > 0) {
                        arrListExams.addAll(responseHandler.getExams());
                        myThirtyAdapter.addAll(arrListExams);
                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExams api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExams Exception : " + e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_MYTHIRTY);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_MYTHIRTY);
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    public void onBackClick() {
        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_MYTHIRTY);
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }
}
