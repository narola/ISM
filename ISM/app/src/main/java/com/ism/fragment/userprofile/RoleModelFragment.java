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
import com.ism.adapter.SuggestedBookAdapter;
import com.ism.adapter.SuggestedRoleModelsAdapter;
import com.ism.adapter.FavoriteRoleModelsAdapter;
import com.ism.constant.WebConstants;
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
public class RoleModelFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener ,SuggestedBookAdapter.AddToFavouriteListner{

    private static final String TAG = RoleModelFragment.class.getSimpleName();
    ImageLoader imageLoader;
    private View view;
    private MyTypeFace myTypeFace;
    private HostActivity activityHost;
    private HorizontalListView listViewFav;
    FavoriteRoleModelsAdapter favoriteRoleModelsAdapter;
    private ArrayList<RolemodelData> arrayListFav;
    private HorizontalListView listViewSuggested;
    SuggestedRoleModelsAdapter suggestedRoleModelsAdapter;
    private ArrayList<RolemodelData> arrayListSuggested;
    private TextView txtSuggestedEmpty;
    private TextView txtFavEmpty;
    private ImageView imgNxtFav;
    private ImageView imgPrevFav;
    private TextView txtFavRoleModels;
    private TextView txtSuggestedRoleModels;

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
        //set typeface
        txtFavEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtFavRoleModels.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedRoleModels.setTypeface(myTypeFace.getRalewayRegular());
        txtSuggestedRoleModels.setText(R.string.strSuggestedRoleModels);
        txtFavRoleModels.setText(R.string.strFavRolemodels);


        listViewFav = (HorizontalListView) view.findViewById(R.id.lv_fav_books);
        listViewSuggested = (HorizontalListView) view.findViewById(R.id.lv_suggested_books);

        imgNxtFav=(ImageView)view.findViewById(R.id.img_next_fav);
        imgPrevFav=(ImageView)view.findViewById(R.id.img_prev_fav);

        callApiGetRoleModelsForUser();

        imgNxtFav.setOnClickListener(this);
        imgPrevFav.setOnClickListener(this);

    }

    private void callApiGetRoleModelsForUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute requestObject = new Attribute();
                requestObject.setUserId("1");
                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_ROLEMODEL_FOR_USER);
            }
            else{
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
                ResponseHandler  responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                        arrayListFav =responseHandler.getRoleModel().get(0).getFavoriteRolemodel();
                        arrayListSuggested =responseHandler.getRoleModel().get(0).getSuggestedRolemodel();
                    setUpList(arrayListFav, arrayListSuggested);
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

    private void setUpList(ArrayList<RolemodelData> arrayListFav, ArrayList<RolemodelData> arrayListSuggested) {
        try {
            Debug.i(TAG, "setUpList :arrayListFav="+arrayListFav.size());
            if(!arrayListFav.isEmpty()){
                txtFavEmpty.setVisibility(View.GONE);
                listViewFav.setVisibility(View.VISIBLE);
                favoriteRoleModelsAdapter=new FavoriteRoleModelsAdapter(getActivity(),arrayListFav);
                listViewFav.setAdapter(favoriteRoleModelsAdapter);

            }else{
                txtFavEmpty.setVisibility(View.VISIBLE);
                listViewFav.setVisibility(View.GONE);
            }
            if(!arrayListSuggested.isEmpty()){
                txtSuggestedEmpty.setVisibility(View.GONE);
                listViewSuggested.setVisibility(View.VISIBLE);
                suggestedRoleModelsAdapter =new SuggestedRoleModelsAdapter(getActivity(),arrayListSuggested,this);
                listViewSuggested.setAdapter(suggestedRoleModelsAdapter);
            }else{
                txtSuggestedEmpty.setVisibility(View.VISIBLE);
                listViewSuggested.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Debug.e(TAG,"setUpList Exceptions :" +e.getLocalizedMessage());
        }
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.img_next_fav:
                    //onNextFavorite();
                    break;
            }


        }catch (Exception e){
            Debug.e(TAG,"onClick Exception :" +e.getLocalizedMessage());
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
