package com.ism.fragment.myAuthor.authorDesk;

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

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.AuthorAssignmentsAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.AuthorBookAssignment;

import java.util.ArrayList;

/**
 * Created by c162 on 1/1/2016.
 */
public class AuthorDeskAssignmentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = AuthorDeskAssignmentsFragment.class.getSimpleName();
    private View view;
    private RecyclerView rvAssignmentsList;
    private AuthorAssignmentsAdapter assignmentAdapter;
    private Fragment mFragment;
    private ArrayList<AuthorBookAssignment> arrListAuthorBooksAssignment = new ArrayList<AuthorBookAssignment>();
    private TextView tvNoDataMsg;
    private HostActivity activityHost;
    private FragmentListener fragmentListener;


    public static AuthorDeskAssignmentsFragment newInstance(Fragment fragment) {
        AuthorDeskAssignmentsFragment myDeskAssignmentsFragment = new AuthorDeskAssignmentsFragment();
        myDeskAssignmentsFragment.mFragment = fragment;
        return myDeskAssignmentsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_assignments, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        tvNoDataMsg = (TextView) view.findViewById(R.id.txt_empty_view);
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());

        rvAssignmentsList = (RecyclerView) view.findViewById(R.id.rv_assignments_list);
        assignmentAdapter = new AuthorAssignmentsAdapter(mFragment, getActivity());
        rvAssignmentsList.setHasFixedSize(true);
        rvAssignmentsList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAssignmentsList.setAdapter(assignmentAdapter);
        callApiGetAuthorBookAssignment();


    }


    private void callApiGetAuthorBookAssignment() {

        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setAuthorId(activityHost.getBundle().getString(AppConstant.AUTHOR_ID));

                new WebserviceWrapper(getActivity(), attribute,this).new WebserviceCaller()
                        .execute(WebConstants.GET_AUTHOR_BOOK_ASSIGNMENT);
            } catch (Exception e) {
                Utility.alertOffline(getActivity());
            }
        } else {
            Utility.alertOffline(getActivity());
        }

    }

    private void onResponseGetAuthorBookAssignment(Object object, Exception error) {

        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

                    if (responseHandler.getAuthorBookAssignment().size() > 0) {

                        arrListAuthorBooksAssignment.addAll(responseHandler.getAuthorBookAssignment());
                        assignmentAdapter.addAll(arrListAuthorBooksAssignment);
                        assignmentAdapter.notifyDataSetChanged();

                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Utility.showToast(getActivity(),responseHandler.getMessage());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAuthorBookAssignment api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAuthorBookAssignment Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {
                case WebConstants.GET_AUTHOR_BOOK_ASSIGNMENT:
                    onResponseGetAuthorBookAssignment(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            fragmentListener=(FragmentListener)activity;
            if(fragmentListener!=null){
                fragmentListener.onFragmentAttached(AuthorDeskFragment.FRAGMENT_ASSIGNMENTS);
            }

        } catch (Exception e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach fragment ");
            if (fragmentListener != null) {
                fragmentListener.onFragmentDetached(AuthorDeskFragment.FRAGMENT_ASSIGNMENTS);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragmentListener = null;
    }
}
