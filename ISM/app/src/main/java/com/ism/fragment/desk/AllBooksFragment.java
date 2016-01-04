package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.deskAdapter.BooksAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.AllBooks;

import java.util.ArrayList;

/**
 * Created by c162 on 28/12/15.
 */
public class AllBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse{

    private static final String TAG = AllBooksFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private HostActivity activityHost;
    private ArrayList<AllBooks> arrListBooks;
    private RecyclerView rvBooksList;
    private BooksAdapter booksAdapter;
    private TextView tvNoDataMsg;

    public static AllBooksFragment newInstance() {
        AllBooksFragment fragment = new AllBooksFragment();
        return fragment;
    }

    public AllBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_books, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        rvBooksList = (RecyclerView) view.findViewById(R.id.recyclerview);
        rvBooksList.setHasFixedSize(true);
        rvBooksList.setLayoutManager(new LinearLayoutManager(getActivity()));
        booksAdapter = new BooksAdapter(getActivity());
        rvBooksList.setAdapter(booksAdapter);

        tvNoDataMsg = (TextView) view.findViewById(R.id.txt_empty_view);
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(View.GONE);
        tvNoDataMsg.setText(getString(R.string.no_books_available));

        callApiGetAllBooks();
    }


    private void callApiGetAllBooks() {

        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                new WebserviceWrapper(getActivity(), new Attribute(), this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_BOOKS);
            } catch (Exception e) {
                Debug.e(TAG ,"callApiGetAllBooks Exceptions : "+ e.getLocalizedMessage());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    private void onResponseGetBooksForAuthor(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.e(TAG, "onResponseGetBooksForAuthor api Success ");

                    if (responseHandler.getAllBooks().size() > 0) {
                        arrListBooks = new ArrayList<AllBooks>();
                        arrListBooks.addAll(responseHandler.getAllBooks());
                        booksAdapter.addAll(arrListBooks);
                        booksAdapter.notifyDataSetChanged();
                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.e(TAG, "onResponseGetBooksForAuthor api Failed ");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetBooksForAuthor api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetBooksForAuthor Exception : " + e.toString());
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            Log.e(TAG, "onAttach Attached fragment ");
            activityHost = (HostActivity) activity;
            activityHost.onChildFragmentAttached(false);
            activityHost.onSetPositionSpinner(2);
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
                    onResponseGetBooksForAuthor(object, error);
                    break;

            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }
}
