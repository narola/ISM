package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.DeskBookAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.views.HorizontalListView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 28/10/15.
 */
public class MyDeskBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = MyDeskBooksFragment.class.getSimpleName();
    private View view;
    private HorizontalListView lvBooks;
    private AuthorHostActivity activityHost;
    private ResponseHandler responseHandler;
    private ArrayList<BookData> arrayListFavBooks;
    private ArrayList<BookData> arrayListSuggestedBooks;
    private TextView txtEmpty;
    DeskBookAdapter deskBookAdapter;

    public static MyDeskBooksFragment newInstance() {
        MyDeskBooksFragment fragBooks = new MyDeskBooksFragment();
        return fragBooks;
    }

    public MyDeskBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desk_books, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
         lvBooks=(HorizontalListView)view.findViewById(R.id.lv_books);
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
       // DeskBookAdapter deskBookAdapter=new DeskBookAdapter(getActivity(),null);
        //lvBooks.setAdapter(deskBookAdapter);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        callApiGetBooksForUser();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost=(AuthorHostActivity)activity;
           // fragListener = (FragmentListener) activity;
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {

        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
    }
    public void callApiGetBooksForUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_BOOKS_FOR_USER);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetBooksForUser Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.GET_BOOKS_FOR_USER:
                    onResponseUserBooks(object, error);
                    break;
//                case WebConstants.MANAGE_FAVOURITES:
//                    onResponseAddResourceToFavorite(object, error);
//                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }
    private void onResponseUserBooks(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListFavBooks = responseHandler.getBooks().get(0).getFavorite();
                    arrayListSuggestedBooks = responseHandler.getBooks().get(0).getSuggested();
                    setUpList(arrayListSuggestedBooks,arrayListFavBooks);
                    Debug.i(TAG, "onResponseUserBooks success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.i(TAG, "onResponseUserBooks Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseUserBooks api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUserBooks Exception : " + e.toString());
        }
    }

    private void setUpList(ArrayList<BookData> arrayListSuggestedBooks, ArrayList<BookData> arrayListFavBooks) {
        try {
            if (!arrayListSuggestedBooks.isEmpty()) {
                txtEmpty.setVisibility(View.GONE);
                deskBookAdapter = new DeskBookAdapter(getActivity(), arrayListSuggestedBooks);
                lvBooks.setAdapter(deskBookAdapter);
                deskBookAdapter.notifyDataSetChanged();

            } else {
                txtEmpty.setVisibility(View.VISIBLE);
                lvBooks.setVisibility(View.GONE);
//                listViewFavBooks.setEmptyView(txtFavEmpty);
            }

        } catch (Exception e) {
            Debug.e(TAG, "setUpFavList Exceptions :" + e.getLocalizedMessage());
        }
    }
}
