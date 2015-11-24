package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ism.ws.model.ResponseObject;
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
    private HorizontalListView listViewMovies;
    FavoriteMoviesAdapter userMoviesAdapter;
    private ArrayList<MovieData> arrayListFavBooks;
    private TextView txtFavEmpty,txtSuggestedEmpty,txtSuggestedBooks,txtFavBooks;
    private HorizontalListView listViewFav,listViewSuggested;
    private ImageView imgNxtFav,imgPrevFav;
    private ArrayList<MovieData> arrayListFav;
    private FavoriteMoviesAdapter favMovieAdapter;
    private ArrayList<MovieData> arrayListSuggested;
    private SuggestedMoviesAdapter suggestedMovieAdapter;

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
        //set typeface
        txtFavEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtFavBooks.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedBooks.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedBooks.setText(R.string.strSuggestedRoleModels);
        txtFavBooks.setText(R.string.strFavRolemodels);


        listViewFav = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggested = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        imgNxtFav=(ImageView)view.findViewById(R.id.img_next_fav);
        imgPrevFav=(ImageView)view.findViewById(R.id.img_prev_fav);

        callApiGetMoviesForUser();

        imgNxtFav.setOnClickListener(this);
        imgPrevFav.setOnClickListener(this);



    }


    private void callApiGetMoviesForUser() {
        try {
            if(Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute requestObject = new Attribute();
                requestObject.setUserId("1");
                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_MOVIES_FOR_USER);
            }else{
                Utility.toastOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetMoviesForUser Exception : " + e.getLocalizedMessage());
        }
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
                if (responseHandler.getStatus().equals(ResponseObject.SUCCESS)) {
                    arrayListFav = responseHandler.getMovies().get(0).getFavoriteMovies();
                    arrayListSuggested = responseHandler.getMovies().get(0).getSuggestedMovies();
                    setUpList(arrayListFav, arrayListSuggested);
                    Log.e(TAG, "onResponseUserMovies success");
                } else if (responseHandler.getStatus().equals(ResponseObject.FAILED)) {
                    Log.e(TAG, "onResponseUserMovies Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseUserMovies api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUserMovies Exception : " + e.toString());
        }
    }

    private void setUpList(ArrayList<MovieData> arrayListFav, ArrayList<MovieData> arrayListSuggested) {
        try {
            if (!arrayListFav.isEmpty()) {
                txtFavEmpty.setVisibility(View.GONE);
                listViewFav.setVisibility(View.VISIBLE);
                favMovieAdapter = new FavoriteMoviesAdapter(getActivity(), arrayListFav);
                listViewFav.setAdapter(favMovieAdapter);

            } else {
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFav.setVisibility(View.GONE);
            }
            if (!arrayListSuggested.isEmpty()) {
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggested.setVisibility(View.VISIBLE);
                suggestedMovieAdapter = new SuggestedMoviesAdapter(getActivity(), arrayListSuggested,this);
                listViewSuggested.setAdapter(suggestedMovieAdapter);
            } else {
                txtSuggestedEmpty.setVisibility(View.VISIBLE);
                listViewSuggested.setVisibility(View.GONE);
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
