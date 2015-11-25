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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.FavoriteMoviesAdapter;
import com.ism.adapter.SuggestedBookAdapter;
import com.ism.adapter.SuggestedMoviesAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.HorizontalListView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.MovieData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class MoviesFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, SuggestedBookAdapter.AddToFavouriteListner {

    private static final String TAG = MoviesFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    FavoriteMoviesAdapter userMoviesAdapter;
    public static TextView txtFavEmpty,txtSuggestedEmpty;
    TextView txtSuggestedBooks,txtFavBooks;
    public static HorizontalListView listViewFav,listViewSuggested;
    private ImageView imgNxtFav,imgPrevFav;
    private ArrayList<MovieData> arrayListFav;
    private FavoriteMoviesAdapter favMovieAdapter;
    private ArrayList<MovieData> arrayListSuggested;
    private SuggestedMoviesAdapter suggestedMovieAdapter;
    private ImageView imgFavSearch,imgSuggestedSearch;
    private EditText etFavSearch,etSuggestedSearch;

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    public MoviesFragment() {
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
        txtSuggestedBooks.setText(R.string.strSuggestedMovies);
        txtFavBooks.setText(R.string.strFavMovies);


        listViewFav = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggested = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        imgNxtFav=(ImageView)view.findViewById(R.id.img_next_fav);
        imgPrevFav=(ImageView)view.findViewById(R.id.img_prev_fav);

        callApiGetMoviesForUser();

        imgFavSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFavSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
                    etFavSearch.setVisibility(View.GONE);
                    View view = getActivity().getCurrentFocus();
                    Utility.hideKeyboard(getActivity(), getView());
                    setUpFavList(arrayListFav);
                    etFavSearch.setText("");

                } else {
                    startSlideAnimation(etFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                    etFavSearch.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etFavSearch, getActivity());
                }

            }
        });
        imgSuggestedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSuggestedSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
                    etSuggestedSearch.setVisibility(View.GONE);
                    View view = getActivity().getCurrentFocus();
                    Utility.hideKeyboard(getActivity(), getView());
                    setUpSuggestedList(arrayListSuggested);
                    etSuggestedSearch.setText("");
                } else {
                    startSlideAnimation(etSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                    etSuggestedSearch.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSuggestedSearch, getActivity());
                }
            }
        });
        etFavSearch
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            favMovieAdapter.getFilter()
                                    .filter(etFavSearch.getText().toString()
                                            .trim());
                            Utility.hideKeyboard(getActivity(), getView());
                            return true;
                        }
                        return false;
                    }
                });
        etSuggestedSearch
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            suggestedMovieAdapter.getFilter()
                                    .filter(etSuggestedSearch.getText().toString()
                                            .trim());
                            Utility.hideKeyboard(getActivity(), getView());
                            return true;
                        }
                        return false;
                    }
                });



    }

    private void setUpFavList(ArrayList<MovieData> arrayList) {
        try {
            if (!arrayList.isEmpty()) {
                txtFavEmpty.setVisibility(View.GONE);
                listViewFav.setVisibility(View.VISIBLE);
                favMovieAdapter = new FavoriteMoviesAdapter(getActivity(), arrayList);
                listViewFav.setAdapter(favMovieAdapter);

            } else {
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFav.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Debug.e(TAG, "setUpFavList Exceptions :" + e.getLocalizedMessage());
        }
    }

    private void setUpSuggestedList(ArrayList<MovieData> arrayList) {
        try {

            if (!arrayList.isEmpty()) {
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggested.setVisibility(View.VISIBLE);
                suggestedMovieAdapter = new SuggestedMoviesAdapter(getActivity(), arrayList,this);
                listViewSuggested.setAdapter(suggestedMovieAdapter);
            } else {
                txtSuggestedEmpty.setVisibility(View.VISIBLE);
                listViewSuggested.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Debug.e(TAG, "setUpSuggestedList Exceptions :" + e.getLocalizedMessage());
        }
    }


    private void callApiGetMoviesForUser() {
        try {
            if(Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute requestObject = new Attribute();
                requestObject.setUserId("1");
                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_MOVIES_FOR_USER);
            }else{
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetMoviesForUser Exception : " + e.getLocalizedMessage());
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
    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_MOVIES_FOR_USER:
                    onResponseUserMovies(object, error);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseUserMovies(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListFav = responseHandler.getMovies().get(0).getFavoriteMovies();
                    arrayListSuggested = responseHandler.getMovies().get(0).getSuggestedMovies();
                    setUpFavList(arrayListFav);
                    setUpSuggestedList(arrayListSuggested);
                    Log.e(TAG, "onResponseUserMovies success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseUserMovies Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseUserMovies api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUserMovies Exception : " + e.toString());
        }
    }

//    private void setUpList(ArrayList<MovieData> arrayListFav, ArrayList<MovieData> arrayListSuggested) {
//        try {
//            if (!arrayListFav.isEmpty()) {
//                txtFavEmpty.setVisibility(View.GONE);
//                listViewFav.setVisibility(View.VISIBLE);
//                favMovieAdapter = new FavoriteMoviesAdapter(getActivity(), arrayListFav);
//                listViewFav.setAdapter(favMovieAdapter);
//
//            } else {
//                txtFavEmpty.setVisibility(View.VISIBLE);
//                listViewFav.setVisibility(View.GONE);
//            }
//            if (!arrayListSuggested.isEmpty()) {
//                txtSuggestedEmpty.setVisibility(View.GONE);
//                listViewSuggested.setVisibility(View.VISIBLE);
//                suggestedMovieAdapter = new SuggestedMoviesAdapter(getActivity(), arrayListSuggested,this);
//                listViewSuggested.setAdapter(suggestedMovieAdapter);
//            } else {
//                txtSuggestedEmpty.setVisibility(View.VISIBLE);
//                listViewSuggested.setVisibility(View.GONE);
//            }
//        } catch (Exception e) {
//            Debug.e(TAG, "setUpList Exceptions :" + e.getLocalizedMessage());
//        }
//    }


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

    @Override
    public void onAddToFav(int position) {
        callApiGetMoviesForUser();
    }
}
