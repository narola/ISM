package com.ism.fragment.userProfile;

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
import com.ism.adapter.FavouritePastTimeAdapter;
import com.ism.adapter.SuggestedPastTimeAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.PastimeData;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class PastTimeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, HostActivity.ManageResourcesListner {

    private static final String TAG = PastTimeFragment.class.getSimpleName();
    private View view;
    private HostActivity activityHost;
    public static RecyclerView listViewFav;
    FavouritePastTimeAdapter favouritePastTimeAdapter;
    private ArrayList<PastimeData> arrayListFav;
    public static RecyclerView listViewSuggested;
    SuggestedPastTimeAdapter suggestedPastTimeAdapter;
    private ArrayList<PastimeData> arrayListSuggested;
    public static TextView txtSuggestedEmpty;
    public static TextView txtFavEmpty;
    private TextView txtSuggestedPastimes, txtFavPastimes;
    private ImageView imgFavSearch, imgSuggestedSearch;
    private EditText etFavSearch, etSuggestedSearch;
    private ArrayList<String> arrayListUnFavItems = new ArrayList<>();
    private ArrayList<String> arrayListFavItems = new ArrayList<>();
    private String strSearch = "";
    private LinearLayoutManager layoutManagerFav, layoutManagerSuggested;
    private ImageView imgNextFav, imgPrevFav;
    private ImageView imgNextSuggested, imgPrevSuggested;

    public static PastTimeFragment newInstance() {
        PastTimeFragment fragment = new PastTimeFragment();
        return fragment;
    }

    public PastTimeFragment() {
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
        txtFavEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFavEmpty.setText(R.string.no_pastimes_available);

        txtSuggestedEmpty = (TextView) view.findViewById(R.id.txt_suggested_empty);
        txtSuggestedEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setText(R.string.no_pastimes_available);

        txtSuggestedPastimes = (TextView) view.findViewById(R.id.txt_read_books);
        txtSuggestedPastimes.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSuggestedPastimes.setText(R.string.strSuggestedPastimes);

        txtFavPastimes = (TextView) view.findViewById(R.id.txt_fav_books);
        txtFavPastimes.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFavPastimes.setText(R.string.strFavPastimes);

        imgFavSearch = (ImageView) view.findViewById(R.id.img_search_fav);

        etFavSearch = (EditText) view.findViewById(R.id.et_search_fav);
        etFavSearch.setTypeface(Global.myTypeFace.getRalewayRegular());

        etSuggestedSearch = (EditText) view.findViewById(R.id.et_search_suggested);
        etSuggestedSearch.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgSuggestedSearch = (ImageView) view.findViewById(R.id.img_search_suggested);
        imgNextFav = (ImageView) view.findViewById(R.id.img_next_fav);
        imgPrevFav = (ImageView) view.findViewById(R.id.img_prev_fav);
        imgNextSuggested = (ImageView) view.findViewById(R.id.img_next_suggested);
        imgPrevSuggested = (ImageView) view.findViewById(R.id.img_prev_suggested);

        listViewFav = (RecyclerView) view.findViewById(R.id.lv_fav_books);
        listViewSuggested = (RecyclerView) view.findViewById(R.id.lv_suggested_books);

        layoutManagerFav = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listViewFav.setLayoutManager(layoutManagerFav);

        layoutManagerSuggested = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listViewSuggested.setLayoutManager(layoutManagerSuggested);
        callApiGetPastimeForUser();
        onClicks();
    }

    private void onClicks() {
        try {
            imgNextFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Next");
                    imgPrevFav.setEnabled(arrayListFav.size() > 4);
                    if (layoutManagerFav.findLastCompletelyVisibleItemPosition() == arrayListFav.size() - 2) {
                        imgNextFav.setEnabled(false);
                    }
                    listViewFav.getLayoutManager().smoothScrollToPosition(listViewFav, null, layoutManagerFav.findLastCompletelyVisibleItemPosition() + 1);
                }

            });

            imgPrevFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Previous");
                    imgNextFav.setEnabled(arrayListFav.size() > 4);
                    if (layoutManagerFav.findFirstCompletelyVisibleItemPosition() == 1) {
                        imgPrevFav.setEnabled(false);
                    }
                    listViewFav.getLayoutManager().smoothScrollToPosition(listViewFav, null, layoutManagerFav.findFirstCompletelyVisibleItemPosition() > 0 ? layoutManagerFav.findFirstCompletelyVisibleItemPosition() - 1 : 0);
                }
            });

            imgNextSuggested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Next");
                    imgPrevSuggested.setEnabled(arrayListSuggested.size() > 4);
                    if (layoutManagerSuggested.findLastCompletelyVisibleItemPosition() == arrayListSuggested.size() - 2) {
                        imgNextSuggested.setEnabled(false);
                    }
                    listViewSuggested.getLayoutManager().smoothScrollToPosition(listViewSuggested, null, layoutManagerSuggested.findLastCompletelyVisibleItemPosition() + 1);

                }
            });

            imgPrevSuggested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(getActivity(), "Previous");
                    imgNextSuggested.setEnabled(arrayListSuggested.size() > 4);
                    if (layoutManagerSuggested.findFirstCompletelyVisibleItemPosition() == 1) {
                        imgPrevSuggested.setEnabled(false);
                    }
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

    private void setUpSuggestedList(ArrayList<PastimeData> arrayListSuggested) {
        try {
            suggestedPastTimeAdapter = new SuggestedPastTimeAdapter(getActivity(), arrayListSuggested, this);
            listViewSuggested.setAdapter(suggestedPastTimeAdapter);
            setVisibilitySuggestedItems(arrayListSuggested.size());
            if (arrayListSuggested.size() > 5) {
                imgNextSuggested.setEnabled(true);
                imgPrevSuggested.setEnabled(false);
            } else {
                imgNextSuggested.setEnabled(false);
                imgPrevSuggested.setEnabled(false);
            };
        } catch (Exception e) {
            Debug.e(TAG, "setUpSuggestedList Exceptions :" + e.getLocalizedMessage());
        }

    }

    private void setUpFavList(ArrayList<PastimeData> arrayListFav) {
        try {
            favouritePastTimeAdapter = new FavouritePastTimeAdapter(getActivity(), arrayListFav, this);
            listViewFav.setAdapter(favouritePastTimeAdapter);
            setVisibilityFavItems(arrayListFav.size());
            if (arrayListFav.size() > 5) {
                imgNextFav.setEnabled(true);
                imgPrevFav.setEnabled(false);
            } else {
                imgNextFav.setEnabled(false);
                imgPrevFav.setEnabled(false);
            }
        } catch (Exception e) {
            Debug.e(TAG, "setUpFavList Exceptions :" + e.getLocalizedMessage());
        }
    }


    private void callApiGetPastimeForUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_PASTTIME_FOR_USER);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetPastimeForUser Exception : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_PASTTIME_FOR_USER:
                    onResponseUserPastTime(object, error);
                    break;
                case WebConstants.MANAGE_FAVOURITES:
                    onResponseAddResourceToFavorite(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
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

    private void onResponseUserPastTime(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListFav = responseHandler.getPastime().get(0).getFavoritePastime();
                    arrayListSuggested = responseHandler.getPastime().get(0).getSuggestedPastime();
                    setUpFavList(arrayListFav);
                    setUpSuggestedList(arrayListSuggested);
                    Debug.i(TAG, "onResponseUserPastTime success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.i(TAG, "onResponseUserPastTime Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseUserPastTime api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUserPastTime Exception : " + e.toString());
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
    public void onPause() {
        super.onPause();
        try {
            if (arrayListFavItems.size() != 0 || arrayListUnFavItems.size() != 0)
                callApiAddResourceToFav();
        } catch (ClassCastException e) {
            Log.e(TAG, "onPause Exception : " + e.toString());
        }


    }

    public ArrayList<PastimeData> onSearch(ArrayList<PastimeData> arrayList, String s) {
        ArrayList<PastimeData> list = new ArrayList<>();
        try {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getPastimeName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                    list.add(arrayList.get(i));
                    Debug.i(TAG, "i :" + i + " String : " + s);
                }
            }
        } catch (Exception e) {
            Debug.i(TAG, "onSearch: " + e.getLocalizedMessage());
        }
        return list;
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
                attribute.setResourceName(AppConstant.RESOURCE_PASTTIMES);
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
    public void onAddToFav(int position) {
        Debug.i(TAG, "OnAddToFav" + position);
        try {
            arrayListFavItems.add(arrayListSuggested.get(position).getPastimeId());
            arrayListFav.add(arrayListSuggested.get(position));
            arrayListSuggested.remove(position);
            setVisibilityFavItems(arrayListFav.size());
            setVisibilitySuggestedItems(arrayListSuggested.size());
            favouritePastTimeAdapter.notifyDataSetChanged();
            suggestedPastTimeAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Debug.e(TAG, "onAddToFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onRemoveFromFav(int position) {
        Debug.i(TAG, "onRemoveFromFav" + position);
        try {
            arrayListUnFavItems.add(arrayListFav.get(position).getPastimeId());
            arrayListSuggested.add(arrayListFav.get(position));
            arrayListFav.remove(position);
            setVisibilityFavItems(arrayListFav.size());
            setVisibilitySuggestedItems(arrayListSuggested.size());
            favouritePastTimeAdapter.notifyDataSetChanged();
            suggestedPastTimeAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Debug.e(TAG, "onRemoveFromFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onSearchFav(Object o) {
        setUpFavList((ArrayList<PastimeData>) o);
    }

    @Override
    public void onSearchSuggested(Object o) {
        setUpSuggestedList((ArrayList<PastimeData>) o);
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
