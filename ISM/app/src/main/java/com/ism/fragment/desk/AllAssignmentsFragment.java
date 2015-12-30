package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.deskAdapter.FavAssignmentsAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.ws.helper.WebserviceWrapper;

/**
 * Created by c162 on 28/12/15.
 */
public class AllAssignmentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse{

    private static final String TAG = AllAssignmentsFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private HostActivity activityHost;
    private RecyclerView recyclerView;
    private TextView txtEmptyView;
    private FavAssignmentsAdapter favAssignmentsAdapter;

    public static AllAssignmentsFragment newInstance() {
        AllAssignmentsFragment fragment = new AllAssignmentsFragment();
        return fragment;
    }

    public AllAssignmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        favAssignmentsAdapter = new FavAssignmentsAdapter(getActivity());
        recyclerView.setAdapter(favAssignmentsAdapter);

        txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);
        txtEmptyView.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmptyView.setVisibility(View.GONE);
        txtEmptyView.setText(getString(R.string.no_books_available));

//        callApiGetAllAssignments();
    }


//    private void callApiGetAllAssignments() {
//
//        if (Utility.isConnected(getActivity())) {
//            try {
//               // activityHost.showProgress();
////                new WebserviceWrapper(getActivity(), new Attribute(), this).new WebserviceCaller()
////                        .execute(WebConstants.GET_ALL_BOOKS);
//            } catch (Exception e) {
//                Debug.e(TAG ,"callApiGetAllAssignments Exceptions : "+ e.getLocalizedMessage());
//            }
//        } else {
//            Utility.alertOffline(getActivity());
//        }
//    }
//
//    private void onResponseGetBooksForAuthor(Object object, Exception error) {
//        try {
//            activityHost.hideProgress();
//            if (object != null) {
//                ResponseHandler responseHandler = (ResponseHandler) object;
//                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
//                    Debug.e(TAG, "onResponseGetBooksForAuthor api Success ");
//
//                    if (responseHandler.getAllBooks().size() > 0) {
//                       // arrListBooks = new ArrayList<AllBooks>();
//                       // arrListBooks.addAll(responseHandler.getAllBooks());
//                       // favAssignmentsAdapter.addAll(arrListBooks);
//                       // favAssignmentsAdapter.notifyDataSetChanged();
//                       // txtEmptyView.setVisibility(View.GONE);
//                    } else {
//                        txtEmptyView.setVisibility(View.VISIBLE);
//                    }
//
//                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
//                    Debug.e(TAG, "onResponseGetBooksForAuthor api Failed ");
//                }
//            } else if (error != null) {
//                Debug.e(TAG, "onResponseGetBooksForAuthor api Exception : " + error.toString());
//            }
//        } catch (Exception e) {
//            Debug.e(TAG, "onResponseGetBooksForAuthor Exception : " + e.toString());
//        }
//    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;

        } catch (ClassCastException e) {
            Debug.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {

                case WebConstants.GET_ALL_BOOKS:
                 //   onResponseGetBooksForAuthor(object, error);
                    break;

            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }
}
