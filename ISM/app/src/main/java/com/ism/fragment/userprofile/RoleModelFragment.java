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
import com.ism.adapter.FavoriteRoleModelsAdapter;
import com.ism.adapter.SuggestedRoleModelsAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.RolemodelData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class RoleModelFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostActivity.ManageResourcesListner {

    private static final String TAG = RoleModelFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    public static RecyclerView listViewFav;
    FavoriteRoleModelsAdapter favRoleModelsAdapter;
    private ArrayList<RolemodelData> arrayListFav;
    public static RecyclerView listViewSuggested;
    SuggestedRoleModelsAdapter suggestedRoleModelsAdapter;
    private ArrayList<RolemodelData> arrayListSuggested;
    public static TextView txtSuggestedEmpty;
    public static TextView txtFavEmpty;
    private TextView txtFavRoleModels;
    private TextView txtSuggestedRoleModels;
    private ImageView imgFavSearch, imgSuggestedSearch;
    private EditText etFavSearch, etSuggestedSearch;
    private ArrayList<String> arrayListFavItems = new ArrayList<>();
    private ArrayList<String> arrayListUnFavItems = new ArrayList<>();
    private String strSearch = "";
    private LinearLayoutManager layoutManagerFav, layoutManagerSuggested;
    private ImageView imgNextFav, imgPrevFav;
    private ImageView imgNextSuggested, imgPrevSuggested;

    public static RoleModelFragment newInstance() {
        RoleModelFragment fragment = new RoleModelFragment();
        return fragment;
    }

    public RoleModelFragment() {
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
        txtSuggestedRoleModels = (TextView) view.findViewById(R.id.txt_read_books);
        txtFavRoleModels = (TextView) view.findViewById(R.id.txt_fav_books);
        imgFavSearch = (ImageView) view.findViewById(R.id.img_search_fav);
        etFavSearch = (EditText) view.findViewById(R.id.et_search_fav);
        imgSuggestedSearch = (ImageView) view.findViewById(R.id.img_search_suggested);
        etSuggestedSearch = (EditText) view.findViewById(R.id.et_search_suggested);
        //set typeface
        txtFavEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtFavRoleModels.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedRoleModels.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedRoleModels.setText(R.string.strSuggestedRoleModels);
        txtFavRoleModels.setText(R.string.strFavRolemodels);

        txtFavEmpty.setText(R.string.no_rolemodels_available);
        txtSuggestedEmpty.setText(R.string.no_rolemodels_available);

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

        callApiGetRoleModelsForUser();
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
                    listViewFav.getLayoutManager().smoothScrollToPosition(listViewFav, null, layoutManagerFav.findFirstCompletelyVisibleItemPosition() > 0? layoutManagerFav.findFirstCompletelyVisibleItemPosition() - 1:0);
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
                    listViewSuggested.getLayoutManager().smoothScrollToPosition(listViewSuggested, null, layoutManagerSuggested.findFirstCompletelyVisibleItemPosition() > 0 ? layoutManagerSuggested.findFirstCompletelyVisibleItemPosition() - 1:0);
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

    private void callApiGetRoleModelsForUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute requestObject = new Attribute();
                requestObject.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_ROLEMODEL_FOR_USER);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetRoleModelsForUser Exception : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_ROLEMODEL_FOR_USER:
                    onResponseUserRoleModels(object, error);
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

    private void onResponseUserRoleModels(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListFav = responseHandler.getRoleModel().get(0).getFavoriteRolemodel();
                    arrayListSuggested = responseHandler.getRoleModel().get(0).getSuggestedRolemodel();
                    setUpFavList(arrayListFav);
                    setUpSuggestedList(arrayListSuggested);
                    Debug.i(TAG, "onResponseUserRoleModels success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.i(TAG, "onResponseUserRoleModels Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseUserRoleModels api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUserRoleModels Exception : " + e.toString());
        }
    }

    private void setUpFavList(ArrayList<RolemodelData> arrayListFav) {
        try {
            Debug.i(TAG, "setUpFavList :arrayListFav=" + arrayListFav.size());
            favRoleModelsAdapter = new FavoriteRoleModelsAdapter(getActivity(), arrayListFav, this);
            listViewFav.setAdapter(favRoleModelsAdapter);
            setVisibilityFavItems(arrayListFav.size());

        } catch (Exception e) {
            Debug.e(TAG, "setUpFavList Exceptions :" + e.getLocalizedMessage());
        }
    }

    private void setUpSuggestedList(ArrayList<RolemodelData> arrayListSuggested) {
        try {
            Debug.i(TAG, "setUpSuggestedList :arrayListSuggested=" + arrayListSuggested.size());
            suggestedRoleModelsAdapter = new SuggestedRoleModelsAdapter(getActivity(), arrayListSuggested, this);
            listViewSuggested.setAdapter(suggestedRoleModelsAdapter);
            setVisibilitySuggestedItems(arrayListSuggested.size());

        } catch (Exception e) {
            Debug.e(TAG, "setUpSuggestedList Exceptions :" + e.getLocalizedMessage());
        }
    }

    public ArrayList<RolemodelData> onSearch(ArrayList<RolemodelData> arrayList, String s) {
        ArrayList<RolemodelData> list = new ArrayList<>();
        try {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getModelName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                    list.add(arrayList.get(i));
                    Debug.i(TAG, "i :" + i + " String : " + s);
                }
            }
        } catch (Exception e) {
            Debug.i(TAG, "onSearch: " + e.getLocalizedMessage());
        }
        return list;
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
                attribute.setResourceName(AppConstant.RESOURCE_ROLEMODEL);
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

    @Override
    public void onAddToFav(int position) {
        Debug.i(TAG, "OnAddToFav" + position);
        try {
            arrayListFavItems.add(arrayListSuggested.get(position).getRolemodelId());
            arrayListFav.add(arrayListSuggested.get(position));
            arrayListSuggested.remove(position);
            setVisibilityFavItems(arrayListFav.size());
            setVisibilitySuggestedItems(arrayListSuggested.size());
            favRoleModelsAdapter.notifyDataSetChanged();
            suggestedRoleModelsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Debug.e(TAG, "onAddToFav Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onRemoveFromFav(int position) {
        Debug.i(TAG, "onRemoveFromFav" + position);
        try {
            arrayListUnFavItems.add(arrayListFav.get(position).getRolemodelId());
            // arrayListSuggested.add(arrayListFav.get(position));
            arrayListFav.remove(position);
            setVisibilityFavItems(arrayListFav.size());
            setVisibilitySuggestedItems(arrayListSuggested.size());
            favRoleModelsAdapter.notifyDataSetChanged();
            suggestedRoleModelsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Debug.e(TAG, "onRemoveFromFav Exception : " + e.getLocalizedMessage());
        }

    }

    @Override
    public void onSearchFav(Object o) {
        setUpFavList((ArrayList<RolemodelData>) o);
    }

    @Override
    public void onSearchSuggested(Object o) {
        setUpSuggestedList((ArrayList<RolemodelData>) o);
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
