package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.SuggestedBookAdapter;
import com.ism.adapter.SuggestedRoleModelsAdapter;
import com.ism.adapter.FavoriteRoleModelsAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.HorizontalListView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.RolemodelData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class RoleModelFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, SuggestedBookAdapter.AddToFavouriteListner {

    private static final String TAG = RoleModelFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    public static HorizontalListView listViewFav;
    FavoriteRoleModelsAdapter favRoleModelsAdapter;
    private ArrayList<RolemodelData> arrayListFav;
    public static HorizontalListView listViewSuggested;
    SuggestedRoleModelsAdapter suggestedRoleModelsAdapter;
    private ArrayList<RolemodelData> arrayListSuggested;
    public static TextView txtSuggestedEmpty;
    public static TextView txtFavEmpty;
    private ImageView imgNxtFav;
    private ImageView imgPrevFav;
    private TextView txtFavRoleModels;
    private TextView txtSuggestedRoleModels;
    private ImageView imgFavSearch, imgSuggestedSearch;
    private EditText etFavSearch, etSuggestedSearch;

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


        listViewFav = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggested = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        imgNxtFav = (ImageView) view.findViewById(R.id.img_next_fav);
        imgPrevFav = (ImageView) view.findViewById(R.id.img_prev_fav);

        callApiGetRoleModelsForUser();

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
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
                    etSuggestedSearch.setVisibility(View.GONE);
                    View view = getActivity().getCurrentFocus();
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
        etFavSearch
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            favRoleModelsAdapter.getFilter()
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
                            suggestedRoleModelsAdapter.getFilter()
                                    .filter(etSuggestedSearch.getText().toString()
                                            .trim());
                            Utility.hideKeyboard(getActivity(), getView());
                            return true;
                        }
                        return false;
                    }
                });
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

            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
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
            if (!arrayListFav.isEmpty()) {
                txtFavEmpty.setVisibility(View.GONE);
                listViewFav.setVisibility(View.VISIBLE);
                favRoleModelsAdapter = new FavoriteRoleModelsAdapter(getActivity(), arrayListFav);
                listViewFav.setAdapter(favRoleModelsAdapter);

            } else {
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFav.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Debug.e(TAG, "setUpFavList Exceptions :" + e.getLocalizedMessage());
        }
    }

    private void setUpSuggestedList(ArrayList<RolemodelData> arrayListSuggested) {
        try {
            Debug.i(TAG, "setUpSuggestedList :arrayListSuggested=" + arrayListSuggested.size());

            if (!arrayListSuggested.isEmpty()) {
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggested.setVisibility(View.VISIBLE);
                suggestedRoleModelsAdapter = new SuggestedRoleModelsAdapter(getActivity(), arrayListSuggested, this);
                listViewSuggested.setAdapter(suggestedRoleModelsAdapter);
            } else {
                txtSuggestedEmpty.setVisibility(View.VISIBLE);
                listViewSuggested.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Debug.e(TAG, "setUpSuggestedList Exceptions :" + e.getLocalizedMessage());
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.img_next_fav:
                    //onNextFavorite();
                    break;
            }


        } catch (Exception e) {
            Debug.e(TAG, "onClick Exception :" + e.getLocalizedMessage());
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
        callApiGetRoleModelsForUser();
    }
}
