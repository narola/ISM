package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * Created by c162 on 21/10/15.
 */
public class AuthorProfileFragment extends Fragment implements AuthorHostActivity.HostListenerProfileController {


    private static final String TAG = AuthorProfileFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private ImageLoader imageLoader;
    private CircleImageView imgUser;
    private TextView txtViewProfile;
    private TextView txtUserName;
    private AuthorHostActivity activityHost;
    private TextView txtMessage, txtNotification, txtFriendRequest;
    private TextView txtMyFeeds, txtMyActivity, txtMyBooks, txtFollowers;

    public static AuthorProfileFragment newInstance() {
        AuthorProfileFragment fragAuthorProfile = new AuthorProfileFragment();
        return fragAuthorProfile;
    }

    public AuthorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_profile, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        imgUser = (CircleImageView) view.findViewById(R.id.img_user);
        txtViewProfile = (TextView) view.findViewById(R.id.txt_view_profile);
        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
        txtNotification = (TextView) view.findViewById(R.id.txt_notification);
        txtMessage = (TextView) view.findViewById(R.id.txt_message);
        txtFriendRequest = (TextView) view.findViewById(R.id.txt_request);
        txtMyActivity = (TextView) view.findViewById(R.id.txt_myactivity);
        txtMyBooks = (TextView) view.findViewById(R.id.txt_mybooks);
        txtFollowers = (TextView) view.findViewById(R.id.txt_followers);
        txtMyFeeds = (TextView) view.findViewById(R.id.txt_myfeeds);


        txtUserName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFriendRequest.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMessage.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtViewProfile.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMyBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMyFeeds.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMyActivity.setTypeface(Global.myTypeFace.getRalewayRegular());

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        //imageLoader.displayImage(Global.strProfilePic, imgUser, ISMAuthor.options);// for change place holder of image =>use Utility getDisplayImageOption method

        View.OnClickListener onClickLabel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.txt_myfeeds:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_FEEDS, null);
                        break;
                    case R.id.txt_mybooks:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_BOOKS, null);
                        break;
                    case R.id.txt_followers:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_FOLLOWERS, null);
                        break;
                    case R.id.txt_view_profile:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_VIEW_PROFILE, null);
                        break;
                    case R.id.txt_myactivity:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_ACTIVITY, null);
                        break;
                }
            }
        };
        txtFollowers.setOnClickListener(onClickLabel);
        txtMyBooks.setOnClickListener(onClickLabel);
        txtMyFeeds.setOnClickListener(onClickLabel);
        txtMyActivity.setOnClickListener(onClickLabel);
        showBadges();


    }

    private void showBadges() {
        int count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_NOTIFICATION, getActivity());
        txtNotification.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtNotification.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_MESSAGE, getActivity());
        txtMessage.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtMessage.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_REQUEST, getActivity());
        txtFriendRequest.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtFriendRequest.setText("" + count);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;
            activityHost.setListenerHostProfileController(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_PROFILE_CONTROLLER);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_PROFILE_CONTROLLER);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


//    public void loadFragmentIntoMainContainer(View view) {
//
//        try {
//            switch (view.getId()) {
//                case R.id.txt_myfeeds:
//                    loadFragment(AuthorHostActivity.FRAGMENT_MYFEEDS);
//                    break;
//
//                case R.id.txt_mybooks:
//                    loadFragment(AuthorHostActivity.FRAGMENT_BOOKS);
//                    break;
//
//                case R.id.txt_myactivity:
//                    loadFragment(AuthorHostActivity.FRAGMENT_MYACTIVITY);
//                    break;
//            }
//
//        } catch (Exception e) {
//
//        }
//
//    }


    private void highlightLabel(int fragmentId, boolean attached) {
        int textColor = attached ? getActivity().getResources().getColor(R.color.color_blue) : Color.WHITE;
        switch (fragmentId) {
            case AuthorHostActivity.FRAGMENT_MY_FEEDS:
                txtMyFeeds.setTextColor(textColor);
                txtMyFeeds.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_MY_BOOKS:
                txtMyBooks.setTextColor(textColor);
                txtMyBooks.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_MY_ACTIVITY:
                txtMyActivity.setTextColor(textColor);
                txtMyActivity.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_FOLLOWERS:
                txtFollowers.setTextColor(textColor);
                txtFollowers.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_VIEW_PROFILE:
                txtViewProfile.setText(attached ? Html.fromHtml("<u>" + activityHost.getString(R.string.strviewprofile) + "</u>") : activityHost.getString(R.string.strviewprofile));
                txtViewProfile.setEnabled(!attached);
                break;
        }
    }

    @Override
    public void onBadgesFetched() {
        showBadges();
    }

    @Override
    public void onSubFragmentAttached(int fragmentId) {
        highlightLabel(fragmentId, true);
    }

    @Override
    public void onSubFragmentDetached(int fragmentId) {
        highlightLabel(fragmentId, false);
    }

}

