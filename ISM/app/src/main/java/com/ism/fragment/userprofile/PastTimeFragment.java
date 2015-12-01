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
import com.ism.adapter.FavouritePastTimeAdapter;
import com.ism.adapter.SuggestedPastTimeAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.HorizontalListView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.PastimeData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class PastTimeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener, HostActivity.AddToFavouriteListner {

    private static final String TAG = PastTimeFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    public static HorizontalListView listViewFav;
    FavouritePastTimeAdapter favouritePastTimeAdapter;
    private ArrayList<PastimeData> arrayListFav;
    public static HorizontalListView listViewSuggested;
    SuggestedPastTimeAdapter suggestedPastTimeAdapter;
    private ArrayList<PastimeData> arrayListSuggested;
    public static TextView txtSuggestedEmpty;
    public static TextView txtFavEmpty;
    private TextView txtSuggestedPastimes, txtFavPastimes;
    private ImageView imgFavSearch, imgSuggestedSearch;
    private EditText etFavSearch, etSuggestedSearch;
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

        myTypeFace = new MyTypeFace(getActivity());

        txtFavEmpty=(TextView)view.findViewById(R.id.txt_fav_empty);
        txtSuggestedEmpty=(TextView)view.findViewById(R.id.txt_suggested_empty);
        txtSuggestedPastimes = (TextView) view.findViewById(R.id.txt_read_books);
        txtFavPastimes = (TextView) view.findViewById(R.id.txt_fav_books);
        txtSuggestedPastimes.setText(R.string.strSuggestedPastimes);
        txtFavPastimes.setText(R.string.strFavPastimes);
        imgFavSearch = (ImageView) view.findViewById(R.id.img_search_fav);
        etFavSearch = (EditText) view.findViewById(R.id.et_search_fav);
        imgSuggestedSearch = (ImageView) view.findViewById(R.id.img_search_suggested);
        etSuggestedSearch = (EditText) view.findViewById(R.id.et_search_suggested);
        //set typeface
        txtFavEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(myTypeFace.getRalewayRegular());

        listViewFav = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggested = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        callApiGetPastimeForUser();

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
                            favouritePastTimeAdapter.getFilter()
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
                            suggestedPastTimeAdapter.getFilter()
                                    .filter(etSuggestedSearch.getText().toString()
                                            .trim());
                            Utility.hideKeyboard(getActivity(), getView());
                            return true;
                        }
                        return false;
                    }
                });

    }

    private void setUpSuggestedList(ArrayList<PastimeData> arrayListSuggested) {
        try {

            if(!arrayListSuggested.isEmpty()){
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggested.setVisibility(View.VISIBLE);
                suggestedPastTimeAdapter=new SuggestedPastTimeAdapter(getActivity(),arrayListSuggested,this);
                listViewSuggested.setAdapter(suggestedPastTimeAdapter);
            }else{
                txtSuggestedEmpty.setVisibility(View.VISIBLE);
                listViewSuggested.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Debug.e(TAG,"setUpSuggestedList Exceptions :" +e.getLocalizedMessage());
        }

    }

    private void setUpFavList(ArrayList<PastimeData> arrayListFav) {
        try {
            if(!arrayListFav.isEmpty()){
                txtFavEmpty.setVisibility(View.GONE);
                listViewFav.setVisibility(View.VISIBLE);
                favouritePastTimeAdapter=new FavouritePastTimeAdapter(getActivity(),arrayListFav);
                listViewFav.setAdapter(favouritePastTimeAdapter);

            }else{
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFav.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Debug.e(TAG,"setUpFavList Exceptions :" +e.getLocalizedMessage());
        }
    }


    private void callApiGetPastimeForUser() {
        try {
            if(Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_PASTTIME_FOR_USER);
            }
            else{
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

            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseUserPastTime(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler  responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                        arrayListFav =responseHandler.getPastime().get(0).getFavoritePastime();
                        arrayListSuggested =responseHandler.getPastime().get(0).getSuggestedPastime();
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
    public void onAddToFav(int position) {
        callApiGetPastimeForUser();
    }

    @Override
    public void onRemoveFromFav(int position) {

    }
}
