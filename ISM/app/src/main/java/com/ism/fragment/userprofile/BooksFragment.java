package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.FavoriteBooksAdapter;
import com.ism.adapter.SuggestedBookAdapter;
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
public class BooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, AdapterView.OnItemClickListener, SuggestedBookAdapter.AddToFavouriteListner {

    private static final String TAG = BooksFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    private HorizontalListView listViewFavBooks;
    FavoriteBooksAdapter favoriteBooksAdapter;
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
    private ImageView imgFavSearch;
    private EditText etFavSearch;
    private ImageView imgSuggestedSearch;
    private EditText etSuggestedSearch;

    public static BooksFragment newInstance() {
        BooksFragment fragment = new BooksFragment();
        return fragment;
    }

    public BooksFragment() {
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
        imgFavSearch = (ImageView) view.findViewById(R.id.img_search_fav);
        etFavSearch = (EditText) view.findViewById(R.id.et_search_fav);
        imgSuggestedSearch = (ImageView) view.findViewById(R.id.img_search_suggested);
        etSuggestedSearch = (EditText) view.findViewById(R.id.et_search_suggested);

        //set typeface
        txtFavEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtFavBooks.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedBooks.setTypeface(myTypeFace.getRalewayRegular());

        listViewFavBooks = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggestedBooks = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        callApiGetBooksForUser();
        imgFavSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inSearch(imgFavSearch, etFavSearch);
            }
        });
        imgSuggestedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inSearch(imgSuggestedSearch,etSuggestedSearch);
            }
        });
        etFavSearch
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            favoriteBooksAdapter.getFilter()
                                    .filter(etFavSearch.getText().toString()
                                            .trim());
                            return true;
                        }
                        return false;
                    }
                });

    }

    private void inSearch(ImageView imgSearch, EditText etSearch) {
        if (etSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
            etSearch.setVisibility(View.GONE);
            View view=getActivity().getCurrentFocus();
            Utility.hideKeyboard(getActivity(), getView());
        } else {
            startSlideAnimation(etSearch, etSearch.getWidth(), 0, 0, 0);
            startSlideAnimation(imgSearch, etSearch.getWidth(), 0, 0, 0);
            etSearch.setVisibility(View.VISIBLE);
            Utility.showSoftKeyboard(etSearch, getActivity());
        }
    }

    private void startSlideAnimation(final View view, int fromX, int toX, int fromY, int toY) {
        TranslateAnimation slideOutAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideOutAnimation.setDuration(500);
        slideOutAnimation.setFillAfter(true);
        view.startAnimation(slideOutAnimation);
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
                favoriteBooksAdapter = new FavoriteBooksAdapter(getActivity(), arrayListFavBooks);
                listViewFavBooks.setAdapter(favoriteBooksAdapter);
                favoriteBooksAdapter.notifyDataSetChanged();

            } else {
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFavBooks.setVisibility(View.GONE);
            }
            if (!arrayListSuggestedBooks.isEmpty()) {
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggestedBooks.setVisibility(View.VISIBLE);
                suggestedBookForUserAdapter = new SuggestedBookAdapter(getActivity(), arrayListSuggestedBooks, this);
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