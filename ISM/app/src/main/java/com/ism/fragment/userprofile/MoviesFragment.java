package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.FavoriteMoviesAdapter;
import com.ism.adapter.SuggestedMoviesAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.MovieData;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class MoviesFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostActivity.ManageResourcesListner {

    private static final String TAG = MoviesFragment.class.getSimpleName();
    private View view;
    private HostActivity activityHost;
    FavoriteMoviesAdapter userMoviesAdapter;
    public static TextView txtFavEmpty, txtSuggestedEmpty;
    TextView txtSuggestedBooks, txtFavBooks;
    public static RecyclerView listViewFav, listViewSuggested;
    private ArrayList<MovieData> arrayListFav;
    private FavoriteMoviesAdapter favMovieAdapter;
    private ArrayList<MovieData> arrayListSuggested;
    private SuggestedMoviesAdapter suggestedMovieAdapter;
    private ImageView imgFavSearch, imgSuggestedSearch;
    private EditText etFavSearch, etSuggestedSearch;
    private ArrayList<String> arrayListFavItems = new ArrayList<>();
    private ArrayList<String> arrayListUnFavItems = new ArrayList<>();
    private String strSearch = "";
    private LinearLayoutManager layoutManagerFav;
    private LinearLayoutManager layoutManagerSuggested;
    private ImageView imgNextFav, imgPrevFav;
    private ImageView imgNextSuggested, imgPrevSuggested;

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

        txtFavEmpty = (TextView) view.findViewById(R.id.txt_fav_empty);
        txtSuggestedEmpty = (TextView) view.findViewById(R.id.txt_suggested_empty);
        txtSuggestedBooks = (TextView) view.findViewById(R.id.txt_read_books);
        txtFavBooks = (TextView) view.findViewById(R.id.txt_fav_books);
        imgFavSearch = (ImageView) view.findViewById(R.id.img_search_fav);
        etFavSearch = (EditText) view.findViewById(R.id.et_search_fav);
        imgSuggestedSearch = (ImageView) view.findViewById(R.id.img_search_suggested);
        etSuggestedSearch = (EditText) view.findViewById(R.id.et_search_suggested);
        //set typeface
        txtFavEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFavBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSuggestedBooks.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtSuggestedBooks.setText(R.string.strSuggestedMovies);
        txtFavBooks.setText(R.string.strFavMovies);

        txtFavEmpty.setText(R.string.no_movies_available);
        txtSuggestedEmpty.setText(R.string.no_movies_available);

        listViewFav = (RecyclerView) view.findViewById(R.id.lv_fav_books);
        listViewSuggested = (RecyclerView) view.findViewById(R.id.lv_suggested_books);

        layoutManagerFav = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listViewFav.setLayoutManager(layoutManagerFav);

        layoutManagerSuggested = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listViewSuggested.setLayoutManager(layoutManagerSuggested);

        imgNextFav = (ImageView) view.findViewById(R.id.img_next_fav);
        imgPrevFav = (ImageView) view.findViewById(R.id.img_prev_fav);
        imgNextSuggested = (ImageView) view.findViewById(R.id.img_next_suggested);
        imgPrevSuggested = (ImageView) view.findViewById(R.id.img_prev_suggested);

        callApiGetMoviesForUser();
        onClicks();
    }

    private void onClicks() {
        try {
            imgNextFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Next");
                    listViewFav.getLayoutManager().smoothScrollToPosition(listViewFav, null, layoutManagerFav.findLastCompletelyVisibleItemPosition() + 1);
                }

            });

            imgPrevFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Previous");
                    listViewFav.getLayoutManager().smoothScrollToPosition(listViewFav, null, layoutManagerFav.findFirstCompletelyVisibleItemPosition() > 0 ? layoutManagerFav.findFirstCompletelyVisibleItemPosition() - 1 : 0);
                }
            });

            imgNextSuggested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Next");
                    listViewSuggested.getLayoutManager().smoothScrollToPosition(listViewSuggested, null, layoutManagerSuggested.findLastCompletelyVisibleItemPosition() + 1);

                }
            });

            imgPrevSuggested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Previous");
                    listViewSuggested.getLayoutManager().smoothScrollToPosition(listViewSuggested, null, layoutManagerSuggested.findFirstCompletelyVisibleItemPosition() > 0 ? layoutManagerSuggested.findFirstCompletelyVisibleItemPosition() - 1 : 0);
                }
            });
            imgFavSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etFavSearch.getVisibility() == View.VISIBLE) {
                        etFavSearch.setVisibility(View.GONE);
                        Utility.hideKeyboard(getActivity(), getView());
                        setUpFavList(arrayListFav);
                        etFavSearch.setText("");

                    } else {
                        Utility.startSlideAnimation(etFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                        Utility.startSlideAnimation(imgFavSearch, etFavSearch.getWidth(), 0, 0, 0);
                        etFavSearch.setVisibility(View.VISIBLE);
                        Utility.showSoftKeyboard(etFavSearch, getActivity());
                    }

                }
            });
            imgSuggestedSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (etSuggestedSearch.getVisibility() == View.VISIBLE) {
                        etSuggestedSearch.setVisibility(View.GONE);
                        Utility.hideKeyboard(getActivity(), getView());
                        setUpSuggestedList(arrayListSuggested);
                        etSuggestedSearch.setText("");
                    } else {
                        Utility.startSlideAnimation(etSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                        Utility.startSlideAnimation(imgSuggestedSearch, etSuggestedSearch.getWidth(), 0, 0, 0);
                        etSuggestedSearch.setVisibility(View.VISIBLE);
                        Utility.showSoftKeyboard(etSuggestedSearch, getActivity());
                    }
                }
            });

            etFavSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    strSearch = "";
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    strSearch = strSearch + s;
                    setUpFavList(onSearch(arrayListFav, strSearch));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            etSuggestedSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    strSearch = "";
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    strSearch = strSearch + s;
                    setUpSuggestedList(onSearch(arrayListSuggested, strSearch));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        } catch (Exception e) {
            Debug.i(TAG, "onClicks Exception : " + e.getLocalizedMessage());
        }

    }

    private ArrayList<MovieData> onSearch(ArrayList<MovieData> arrayList, String s) {
        ArrayList<MovieData> list = new ArrayList<>();
        try {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getMovieName().toString().toLowerCase().contains(s.toString().toLowerCase()) || arrayList.get(i).getMovieName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                    list.add(arrayList.get(i));
                    Debug.i(TAG, "i :" + i + " String : " + s);
                }
            }
        } catch (Exception e) {
            Debug.i(TAG, "onSearch: " + e.getLocalizedMessage());
        }
        return list;
    }

    private void setUpFavList(ArrayList<MovieData> arrayList) {
        try {
            favMovieAdapter = new FavoriteMoviesAdapter(getActivity(), arrayList, this);
            listViewFav.setAdapter(favMovieAdapter);
            favMovieAdapter.notifyDataSetChanged();
            setVisibilityFavItems(arrayList.size());
        } catch (Exception e) {
            Debug.e(TAG, "setUpFavList Exceptions :" + e.getLocalizedMessage());
        }
    }

    private void setUpSuggestedList(ArrayList<MovieData> arrayList) {
        try {
            suggestedMovieAdapter = new SuggestedMoviesAdapter(getActivity(), arrayList, this);
            listViewSuggested.setAdapter(suggestedMovieAdapter);
            suggestedMovieAdapter.notifyDataSetChanged();
            setVisibilitySuggestedItems(arrayList.size());
        } catch (Exception e) {
            Debug.e(TAG, "setUpSuggestedList Exceptions :" + e.getLocalizedMessage());
        }
    }


    private void callApiGetMoviesForUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute requestObject = new Attribute();
                requestObject.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_MOVIES_FOR_USER);
            } else {
                Utility.alertOffline(getActivity());
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
                case WebConstants.MANAGE_FAVOURITES:
                    onResponseAddResourceToFavorite(object, error);
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

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (arrayListFavItems.size() != 0 || arrayListUnFavItems.size() != 0)
                callApiAddResourceToFav();
        } catch (ClassCastException e) {
            Log.e(TAG, "onPause Exception : " + e.toString());
        }

    }

    private void callApiAddResourceToFav() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                if (arrayListFavItems == null)
                    attribute.setFavResourceId(new ArrayList<String>());
                else {
                    attribute.setFavResourceId(arrayListFavItems);// get all resource ids from suggested list to add resource ids in user favourites
                }
                if (arrayListUnFavItems == null)
                    attribute.setUnfavoriteResourceId(new ArrayList<String>());// get All the resource ids from favourite list to add resource id in user unfavourites
                else {
                    attribute.setUnfavoriteResourceId(arrayListUnFavItems);// get All the resource ids from favourite list to add resource id in user unfavourites
                }
                attribute.setResourceName(AppConstant.RESOURCE_MOVIES);
                Debug.i(TAG, "Attributes object :" + attribute);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.MANAGE_FAVOURITES);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiAddResourceToFav Exception : " + e.getLocalizedMessage());
        }
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

    private void onResponseAddResourceToFavorite(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                Debug.i(TAG, "Response object :" + object);
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "onResponseAddResourceToFavorite success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseAddResourceToFavorite Failed");
                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseAddResourceToFavorite api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseAddResourceToFavorite Exception : " + e.toString());
        }
    }

    @Override
    public void onAddToFav(int position) {
        Debug.i(TAG, "OnAddToFav" + position);
        try {
            arrayListFavItems.add(arrayListSuggested.get(position).getMovieId());
            arrayListFav.add(arrayListSuggested.get(position));
            arrayListSuggested.remove(position);
            setVisibilityFavItems(arrayListFav.size());
            setVisibilitySuggestedItems(arrayListSuggested.size());
            favMovieAdapter.notifyDataSetChanged();
            suggestedMovieAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Debug.e(TAG, "onAddToFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onRemoveFromFav(int position) {
        Debug.i(TAG, "onRemoveFromFav" + position);
        try {
            arrayListUnFavItems.add(arrayListFav.get(position).getMovieId());
            arrayListSuggested.add(arrayListFav.get(position));
            arrayListFav.remove(position);
            setVisibilityFavItems(arrayListFav.size());
            setVisibilitySuggestedItems(arrayListSuggested.size());
            favMovieAdapter.notifyDataSetChanged();
            suggestedMovieAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Debug.e(TAG, "onRemoveFromFav Exception : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onSearchFav(Object o) {
        setUpFavList((ArrayList<MovieData>) o);
    }

    @Override
    public void onSearchSuggested(Object o) {
        setUpSuggestedList((ArrayList<MovieData>) o);
    }

    public void setVisibilityFavItems(int size) {

        if (size == 0) {
            txtFavEmpty.setVisibility(View.VISIBLE);
            listViewFav.setVisibility(View.GONE);
        } else {
            txtFavEmpty.setVisibility(View.GONE);
            listViewFav.setVisibility(View.VISIBLE);
        }
    }

    public void setVisibilitySuggestedItems(int size) {
        if (size == 0) {
            txtSuggestedEmpty.setVisibility(View.VISIBLE);
            listViewSuggested.setVisibility(View.GONE);
        } else {
            txtSuggestedEmpty.setVisibility(View.GONE);
            listViewSuggested.setVisibility(View.VISIBLE);
        }
    }

}
