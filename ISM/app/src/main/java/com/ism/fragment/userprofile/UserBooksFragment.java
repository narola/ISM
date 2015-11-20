package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.SuggestedBookForUserAdapter;
import com.ism.adapter.UserFavoriteBooksAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.views.HorizontalListView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Favorite;
import com.ism.ws.model.Suggested;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class UserBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private static final String TAG = UserBooksFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    private HorizontalListView listViewFavBooks;
    UserFavoriteBooksAdapter userFavoriteBooksAdapter;
    private ArrayList<Favorite> arrayListFavBooks;
    private HorizontalListView listViewSuggestedBooks;
    SuggestedBookForUserAdapter suggestedBookForUserAdapter;
    private ArrayList<Suggested> arrayListSuggestedBooks;
    private TextView txtSuggestedEmpty;
    private TextView txtFavEmpty;

    public static UserBooksFragment newInstance() {
        UserBooksFragment fragment = new UserBooksFragment();
        return fragment;
    }

    public UserBooksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile_books, container, false);

        initGlobal();

        return view;
    }


    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        txtFavEmpty=(TextView)view.findViewById(R.id.txt_fav_empty);
        txtSuggestedEmpty=(TextView)view.findViewById(R.id.txt_suggested_empty);
        //set typeface
        txtFavEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(myTypeFace.getRalewayRegular());

        listViewFavBooks = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggestedBooks = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        callApiGetBooksForUser();



    }


    private void callApiGetBooksForUser() {
        try {
            activityHost.showProgress();
            Attribute requestObject = new Attribute();
            requestObject.setUserId("1");
            new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_BOOKS_FOR_USER);

        } catch (Exception e) {
            Debug.i(TAG, "callApiGetBooksForUser Exception : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_BOOKS_FOR_USER:
                    onResponseUserBooks(object, error);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseUserBooks(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler  responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                        arrayListFavBooks=responseHandler.getBooks().get(0).getFavorite();
                        arrayListSuggestedBooks=responseHandler.getBooks().get(0).getSuggested();
                    setUpList(arrayListFavBooks,arrayListSuggestedBooks);
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

    private void setUpList(ArrayList<Favorite> arrayListFavBooks, ArrayList<Suggested> arrayListSuggestedBooks) {
        try {
            if(!arrayListFavBooks.isEmpty()){
                txtFavEmpty.setVisibility(View.GONE);
                listViewFavBooks.setVisibility(View.VISIBLE);
                userFavoriteBooksAdapter=new UserFavoriteBooksAdapter(getActivity(),arrayListFavBooks);
                listViewFavBooks.setAdapter(userFavoriteBooksAdapter);

            }else{
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFavBooks.setVisibility(View.GONE);
            }
            if(!arrayListFavBooks.isEmpty()){
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggestedBooks.setVisibility(View.VISIBLE);
                suggestedBookForUserAdapter=new SuggestedBookForUserAdapter(getActivity(),arrayListSuggestedBooks);
                listViewSuggestedBooks.setAdapter(suggestedBookForUserAdapter);
            }else{
                txtSuggestedEmpty.setVisibility(View.VISIBLE);
                listViewSuggestedBooks.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Debug.e(TAG,"setUpList Exceptions :" +e.getLocalizedMessage());
        }
    }


    @Override
    public void onClick(View v) {


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            //   activityHost.setListenerHostAboutMe(this);
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

}
