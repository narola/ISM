package com.ism.author.fragment.mydesk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.MyDeskAssignmentsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AuthorBookAssignment;

import java.util.ArrayList;

/**
 * Created by c162 on 28/11/15.
 */
public class MyDeskAssignmentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = MyDeskAssignmentsFragment.class.getSimpleName();
    private View view;
    private RecyclerView rvAssignmentsList;
    private MyDeskAssignmentsAdapter assignmentAdapter;
    private Fragment mFragment;
    private ArrayList<AuthorBookAssignment> arrListAuthorBooksAssignment = new ArrayList<AuthorBookAssignment>();
    private TextView tvNoDataMsg;


    public static MyDeskAssignmentsFragment newInstance(Fragment fragment) {
        MyDeskAssignmentsFragment myDeskAssignmentsFragment = new MyDeskAssignmentsFragment();
        myDeskAssignmentsFragment.mFragment = fragment;
        return myDeskAssignmentsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);


        rvAssignmentsList = (RecyclerView) view.findViewById(R.id.rv_assignments_list);
        assignmentAdapter = new MyDeskAssignmentsAdapter(mFragment, getActivity());
        rvAssignmentsList.setHasFixedSize(true);
        rvAssignmentsList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAssignmentsList.setAdapter(assignmentAdapter);


        setEmptyView(false);
        callApiGetAuthorBookAssignment();


    }


    private void callApiGetAuthorBookAssignment() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setAuthorId(Global.strUserId);

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_AUTHOR_BOOK_ASSIGNMENT);
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
                case WebConstants.GET_AUTHOR_BOOK_ASSIGNMENT:
                    onResponseGetAuthorBookAssignment(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAuthorBookAssignment(Object object, Exception error) {

        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getAuthorBookAssignment().size() > 0) {

                        arrListAuthorBooksAssignment.addAll(responseHandler.getAuthorBookAssignment());
                        assignmentAdapter.addAll(arrListAuthorBooksAssignment);
                        assignmentAdapter.notifyDataSetChanged();

                        setEmptyView(false);
                    } else {
                        setEmptyView(true);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAuthorBookAssignment api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAuthorBookAssignment Exception : " + e.toString());
        }
    }

    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setText(getResources().getString(R.string.no_assignments));
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvAssignmentsList.setVisibility(isEnable ? View.GONE : View.VISIBLE);

    }


}
