package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.BooksAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AllBooks;

import java.util.ArrayList;

/**
 * Created by c162 on 21/10/15.
 */
public class BooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = BooksFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    private RecyclerView rvBooksList;
    private BooksAdapter booksAdapter;
    private ArrayList<AllBooks> arrListAuthorBooks;
    private TextView tvNoDataMsg;

    public static BooksFragment newInstance() {
        BooksFragment fragBooks = new BooksFragment();
        return fragBooks;
    }

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_books, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

        rvBooksList = (RecyclerView) view.findViewById(R.id.rv_books_list);
        rvBooksList.setHasFixedSize(true);
        rvBooksList.setLayoutManager(new LinearLayoutManager(getActivity()));
        booksAdapter = new BooksAdapter(getActivity());
        rvBooksList.setAdapter(booksAdapter);


        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        setEmptyView(false);

        callApiGetAllBooks();
    }


    private void callApiGetAllBooks() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_BOOKS);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, getActivity()), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
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

    private void onResponseGetBooksForAuthor(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {


                    if (responseHandler.getAllBooks().size() > 0) {
                        arrListAuthorBooks = new ArrayList<AllBooks>();
                        arrListAuthorBooks.addAll(responseHandler.getAllBooks());
                        booksAdapter.addAll(arrListAuthorBooks);
                        booksAdapter.notifyDataSetChanged();

                        setEmptyView(false);
                    } else {

                        setEmptyView(true);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
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
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_BOOKS);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_BOOKS);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setText(getString(R.string.no_books_added));
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvBooksList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }
}
