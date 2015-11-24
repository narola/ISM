package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.SuggestedBookAdapter;
import com.ism.adapter.FavoriteBooksAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.HorizontalListView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Book;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class UserBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, AdapterView.OnItemClickListener, SuggestedBookAdapter.AddToFavouriteListner {

    private static final String TAG = UserBooksFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    private HorizontalListView listViewFavBooks;
    FavoriteBooksAdapter userFavoriteBooksAdapter;
    private ArrayList<Book> arrayListFavBooks;
    private HorizontalListView listViewSuggestedBooks;
    SuggestedBookAdapter suggestedBookForUserAdapter;
    private ArrayList<Book> arrayListSuggestedBooks;
    private TextView txtSuggestedEmpty;
    private TextView txtFavEmpty;
    private TextView txtSuggestedBooks;
    private TextView txtFavBooks;
    private int addToFavItem;
    private ResponseHandler responseHandler;

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

        txtFavEmpty = (TextView) view.findViewById(R.id.txt_fav_empty);
        txtSuggestedEmpty = (TextView) view.findViewById(R.id.txt_suggested_empty);
        txtSuggestedBooks = (TextView) view.findViewById(R.id.txt_read_books);
        txtFavBooks = (TextView) view.findViewById(R.id.txt_fav_books);
        //set typeface
        txtFavEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtFavBooks.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedBooks.setTypeface(myTypeFace.getRalewayRegular());

        listViewFavBooks = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggestedBooks = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        callApiGetBooksForUser();
//        listViewSuggestedBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Debug.i(TAG, "onItemClick : " + position + " view id : " + view.getId() + " parent id : " + parent.getItemAtPosition(position));
//                try {
//                    Toast.makeText(getActivity(),"Image",Toast.LENGTH_SHORT);
//                    ImageView imgAddFav=(ImageView)view.findViewById(R.id.img_add_fav);
////                    if(view.getId()==){
////                            addToFavItem = position;
////                            callApiAddResourceToFav(position);
////
////
////                    }
//                } catch (Exception e) {
//                    Debug.i(TAG, "listViewSuggestedBooks setOnItemClck Exception : " + e.getLocalizedMessage());
//                }
//            }
//        });


    }


    public void callApiGetBooksForUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId("1");
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_BOOKS_FOR_USER);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetBooksForUser Exception : " + e.toString());
        }
    }

//    private void onResponseAddResourceToFavorite(Object object, Exception error) {
//        try {
//            activityHost.hideProgress();
//            if (object != null) {
//                ResponseHandler responseHandler = (ResponseHandler) object;
//                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
//                    arrayListFavBooks.add(arrayListSuggestedBooks.get(addToFavItem));
//                    arrayListSuggestedBooks.remove(addToFavItem);
//                    Debug.i(TAG, "onResponseAddResourceToFavorite success");
//                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
//                    Debug.i(TAG, "onResponseAddResourceToFavorite Failed");
//                }
//            } else if (error != null) {
//                Debug.i(TAG, "onResponseAddResourceToFavorite api Exception : " + error.toString());
//            }
//        } catch (Exception e) {
//            Debug.e(TAG, "onResponseAddResourceToFavorite Exception : " + e.toString());
//        }
//    }


    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_BOOKS_FOR_USER:
                    onResponseUserBooks(object, error);
                    break;
//                case WebConstants.ADD_RESOURCE_TO_FAVORITE:
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
                    setUpList(arrayListFavBooks, arrayListSuggestedBooks);
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

    private void setUpList(ArrayList<Book> arrayListFavBooks, ArrayList<Book> arrayListSuggestedBooks) {
        try {
            if (!arrayListFavBooks.isEmpty()) {
                txtFavEmpty.setVisibility(View.GONE);
                listViewFavBooks.setVisibility(View.VISIBLE);
                userFavoriteBooksAdapter = new FavoriteBooksAdapter(getActivity(), arrayListFavBooks);
                listViewFavBooks.setAdapter(userFavoriteBooksAdapter);
                userFavoriteBooksAdapter.notifyDataSetChanged();

            } else {
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFavBooks.setVisibility(View.GONE);
            }
            if (!arrayListSuggestedBooks.isEmpty()) {
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggestedBooks.setVisibility(View.VISIBLE);
                suggestedBookForUserAdapter = new SuggestedBookAdapter(getActivity(), arrayListSuggestedBooks,this);
                listViewSuggestedBooks.setAdapter(suggestedBookForUserAdapter);
                suggestedBookForUserAdapter.notifyDataSetChanged();
            } else {
                txtSuggestedEmpty.setVisibility(View.VISIBLE);
                listViewSuggestedBooks.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Debug.e(TAG, "setUpList Exceptions :" + e.getLocalizedMessage());
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
//            case R.id.lv_suggested_books:
//
//                break;
        }

    }

    @Override
    public void onAddToFav(int addToFavItem) {
        Debug.i(TAG, "OnAddToFav" + addToFavItem);
        try {
            callApiGetBooksForUser();

        } catch (Exception e) {
            Debug.e(TAG, "onAddToFav Exception : " + e.getLocalizedMessage());
        }
    }
}