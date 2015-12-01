
package com.ism.author.activtiy;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utility;
import com.ism.author.adapter.ControllerTopSpinnerAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.AddQuestionContainerFragment;
import com.ism.author.fragment.BooksFragment;
import com.ism.author.fragment.CreateExamAssignmentContainerFragment;
import com.ism.author.fragment.ExamsFragment;
import com.ism.author.fragment.GetAssignmentsSubmittorFragment;
import com.ism.author.fragment.GetObjectiveAssignmentQuestionsFragment;
import com.ism.author.fragment.GetSubjectiveAssignmentQuestionsFragment;
import com.ism.author.fragment.HomeFragment;
import com.ism.author.fragment.OfficeFragment;
import com.ism.author.fragment.StudentAttemptedFragment;
import com.ism.author.fragment.TrialExamObjectiveDetailFragment;
import com.ism.author.fragment.TrialExamSujectiveDetailFragment;
import com.ism.author.fragment.TrialFragment;
import com.ism.author.fragment.mydesk.MyDeskFragment;
import com.ism.author.fragment.userprofile.AllMessageFragment;
import com.ism.author.fragment.userprofile.AllNotificationFragment;
import com.ism.author.fragment.userprofile.AllStudymateRequestFragment;
import com.ism.author.fragment.userprofile.AuthorProfileFragment;
import com.ism.author.fragment.userprofile.FollowersFragment;
import com.ism.author.fragment.userprofile.HighScoreFragment;
import com.ism.author.fragment.userprofile.MyActivityFragment;
import com.ism.author.fragment.userprofile.MyFeedsFragment;
import com.ism.author.fragment.userprofile.StudentAttemptedAssignmentFragment;
import com.ism.author.fragment.userprofile.ViewProfileFragment;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.ControllerTopMenuItem;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.BookData;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/*
* these class is for the main screen after login contains the host activity for managing the main and container fragment.
* */
public class AuthorHostActivity extends Activity implements FragmentListener, WebserviceWrapper.WebserviceResponse {

    protected static final String TAG = AuthorHostActivity.class.getName();
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    RelativeLayout rlControllerTop, rlControllerTopMenu;
    LinearLayout llSearch, llControllerLeft;
    FrameLayout flFragmentContainerMain, flFragmentContainerRight;
    ImageView imgLogo, imgHome, imgBack, imgSearch, imgOffice, imgBooks, imgEditProfileHome, imgEditProfileTutorial, imgEditProfileClassroom,
            imgEditProfileAssesment, imgAuthorProfile, imgHighScore;
    Spinner spSubmenu;
    TextView txtTitle, txtAction;

    EditText etSearch;
    private View.OnClickListener onClickMenuItem;

    private ControllerTopSpinnerAdapter adapterControllerTopSpinner;
    private HostListenerProfileController listenerHostProfileController;
    private AddToFavouriteListner addToFavouriteListner;
    private HostListenerAllNotification listenerHostAllNotification;

    private HostListenerAllMessage listenerHostAllMessage;

    private ArrayList<ControllerTopMenuItem> controllerTopMenuTrial, currentControllerTopMenu, controllerTopMenuAssessment, controlTopMenuMyDesk;
    /*
    * these are the fragments for the main fragment.
    * */
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_OFFICE = 1;
    public static final int FRAGMENT_BOOKS = 2;
    public static final int FRAGMENT_GOTRENDING = 6;
    public static final int FRAGMENT_SETQUIZ = 7;
    public static final int FRAGMENT_PROGRESSREPORT = 8;
    public static final int FRAGMENT_TRIAL = 9;
    public static final int FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT = 10;
    public static final int FRAGMENT_ADDQUESTION_CONTAINER = 11;
    public static final int FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS = 12;
    public static final int FRAGMENT_TRIAL_EXAM_SUBJECTIVE_DETAILS = 13;
    public static final int FRAGMENT_ASSESSMENT = 14;
    public static final int FRAGMENT_ASSIGNMENT_SUBMITTOR = 15;
    public static final int FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS = 16;
    public static final int FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS = 17;
    public static final int FRAGMENT_ALL_MESSAGE = 18;
    public static final int FRAGMENT_ALL_NOTIFICATION = 19;
    public static final int FRAGMENT_ALL_STUDYMATE_REQUEST = 20;
    public static final int FRAGMENT_MY_DESK = 21;
    //these are the right side fragments


    public static final int FRAGMENT_PROFILE_CONTROLLER = 31;

    public static final int FRAGMENT_HIGHSCORE = 32;
    public static final int FRAGMENT_STUDENT_ATTEMPTED = 33;
    public static final int FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT = 34;
    public static final int FRAGMENT_MY_FEEDS = 35;
    public static final int FRAGMENT_FOLLOWERS = 36;
    public static final int FRAGMENT_MY_ACTIVITY = 37;
    public static final int FRAGMENT_MY_BOOKS = 38;
    public static final int FRAGMENT_VIEW_PROFILE = 39;
    private InputMethodManager inputMethod;


    public static int currentMainFragment;


    public static int currentRightFragment;
    private int currentMainFragmentBg;
    private ActionProcessButton progress_bar;
    private ProgressGenerator progressGenerator;

    public interface HostListenerProfileController {
        public void onBadgesFetched();

        public void onSubFragmentAttached(int fragmentId);

        public void onSubFragmentDetached(int fragmentId);
        //  public void onControllerMenuItemClicked(int position);
    }

    public interface HostListenerAllNotification {
        public void onControllerTopBackClick();
    }

    public interface HostListenerAllMessage {
        public void onControllerTopBackClick();
    }

    public void setListenerHostProfileController(HostListenerProfileController listenerHostProfileController) {
        this.listenerHostProfileController = listenerHostProfileController;
    }

    public void setListenerHostAllNotification(HostListenerAllNotification listenerHostAllNotification) {
        this.listenerHostAllNotification = listenerHostAllNotification;
    }

    public void setListenerHostAllMessage(HostListenerAllMessage listenerHostAllMessage) {
        this.listenerHostAllMessage = listenerHostAllMessage;
    }

    public void setListenerFavourites(AddToFavouriteListner addToFavouriteListner) {
        this.addToFavouriteListner = addToFavouriteListner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_host);

        inigGlobal();

    }


    private void inigGlobal() {

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Global.myTypeFace = new MyTypeFace(getApplicationContext());

        Global.imageLoader = ImageLoader.getInstance();
        Global.imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        Global.strUserId = "52";
        Global.strFullName = "Arti Patel";
        Global.strProfilePic = WebConstants.USER_IMAGES + "user_370/123_test.png";
        rlControllerTop = (RelativeLayout) findViewById(R.id.rl_controller_top);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        rlControllerTopMenu = (RelativeLayout) findViewById(R.id.rl_controller_top_menu);
        llControllerLeft = (LinearLayout) findViewById(R.id.ll_controller_left);
        flFragmentContainerMain = (FrameLayout) findViewById(R.id.fl_fragment_container_main);
        flFragmentContainerRight = (FrameLayout) findViewById(R.id.fl_fragment_container_right);
        progress_bar = (ActionProcessButton) findViewById(R.id.progress_bar);
        progressGenerator = new ProgressGenerator();

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgHome = (ImageView) findViewById(R.id.img_home);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        imgOffice = (ImageView) findViewById(R.id.img_office);
        imgBooks = (ImageView) findViewById(R.id.img_books);
        imgEditProfileHome = (ImageView) findViewById(R.id.img_edit_profile_home);
        imgEditProfileTutorial = (ImageView) findViewById(R.id.img_edit_profile_tutorial);
        imgEditProfileClassroom = (ImageView) findViewById(R.id.img_edit_profile_classroom);
        imgEditProfileAssesment = (ImageView) findViewById(R.id.img_edit_profile_assesment);

        controllerTopMenuTrial = ControllerTopMenuItem.getMenuTrial(AuthorHostActivity.this);
        controlTopMenuMyDesk = ControllerTopMenuItem.getMenuMyDesk(AuthorHostActivity.this);
        controllerTopMenuAssessment = ControllerTopMenuItem.getMenuAssessment(AuthorHostActivity.this);

        spSubmenu = (Spinner) findViewById(R.id.sp_submenu);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtAction = (TextView) findViewById(R.id.txt_action);
        etSearch = (EditText) findViewById(R.id.et_search);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Debug.i(TAG, "search clicked");
                }

                return false;
            }
        });

        imgAuthorProfile = (ImageView) findViewById(R.id.img_author_profile);
        imgHighScore = (ImageView) findViewById(R.id.img_high_score);

        loadFragmentInMainContainer(FRAGMENT_HOME, null);
        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE, null);

        onClickMenuItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClick(v);
            }
        };

        imgBack.setOnClickListener(onClickMenuItem);
        txtAction.setOnClickListener(onClickMenuItem);
        callApiGetAllBadgesCount();

    }

//These is for the load fragmet in main container

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void loadFragmentInMainContainer(int fragment, Bundle bundleArgument) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, HomeFragment.newInstance()).commit();
                    break;

                case FRAGMENT_OFFICE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, OfficeFragment.newInstance()).commit();

                    break;
                case FRAGMENT_BOOKS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, BooksFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TRIAL:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TrialFragment.newInstance()).commit();
                    break;
                case FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            CreateExamAssignmentContainerFragment.newInstance(bundleArgument)).commit();
                    break;
                case FRAGMENT_ADDQUESTION_CONTAINER:
//                    mFragmentTransaction = mFragmentManager.beginTransaction();
//                    mFragmentTransaction.add(R.id.fl_fragment_container_main, AddQuestionContainerFragment.newInstance());
//                    mFragmentTransaction.addToBackStack(String.valueOf(FRAGMENT_ADDQUESTION_CONTAINER));
//                    mFragmentTransaction.commit();

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AddQuestionContainerFragment.newInstance(bundleArgument)).commit();
                    break;

                case FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TrialExamObjectiveDetailFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TRIAL_EXAM_SUBJECTIVE_DETAILS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TrialExamSujectiveDetailFragment.newInstance()).commit();
                    break;

                case FRAGMENT_ASSESSMENT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, ExamsFragment.newInstance()).commit();
                    break;

                case FRAGMENT_ASSIGNMENT_SUBMITTOR:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            GetAssignmentsSubmittorFragment.newInstance(bundleArgument)).commit();
                    break;

                case FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS:
                    mFragment = GetObjectiveAssignmentQuestionsFragment.newInstance(bundleArgument);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            mFragment).commit();
                    break;
                case FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            GetSubjectiveAssignmentQuestionsFragment.newInstance(bundleArgument)).commit();
                    break;

                case FRAGMENT_MY_ACTIVITY:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            MyActivityFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MY_BOOKS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            GetAssignmentsSubmittorFragment.BooksFragment.newInstance()).commit();
                    break;
                case FRAGMENT_FOLLOWERS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            FollowersFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MY_FEEDS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            MyFeedsFragment.newInstance()).commit();
                    break;
                case FRAGMENT_VIEW_PROFILE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            ViewProfileFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_NOTIFICATION:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllNotificationFragment.newInstance(bundleArgument)).commit();
                    break;
                case FRAGMENT_ALL_MESSAGE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllMessageFragment.newInstance(bundleArgument)).commit();
                    break;
                case FRAGMENT_ALL_STUDYMATE_REQUEST:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllStudymateRequestFragment.newInstance(bundleArgument)).commit();
                    break;
                case FRAGMENT_MY_DESK:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            MyDeskFragment.newInstance()).commit();
                    break;
            }
            currentMainFragment = fragment;

        } catch (Exception e) {
            Debug.i(TAG, "loadFragment Exception : " + e.toString());
        }

    }

    private GetObjectiveAssignmentQuestionsFragment mFragment;


    public void loadStudentEvaluationData() {
        if (mFragment != null) {
            ((GetObjectiveAssignmentQuestionsFragment) mFragment).loadStudentEvaluationData();
        }
    }

    //these is for the load fragment in right container.
    public void loadFragmentInRightContainer(int fragment, Bundle bundleArgument) {
        try {
            switch (fragment) {

                case FRAGMENT_PROFILE_CONTROLLER:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, AuthorProfileFragment.newInstance()).commit();
                    break;

                case FRAGMENT_HIGHSCORE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, HighScoreFragment.newInstance()).commit();
                    break;
                case FRAGMENT_STUDENT_ATTEMPTED:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, StudentAttemptedFragment.newInstance()).commit();
                    break;

                case FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, StudentAttemptedAssignmentFragment.newInstance(bundleArgument)).commit();
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "loadFragment Exception : " + e.toString());

        }

    }

    private void callApiGetAllBadgesCount() {
        try {
            if (Utility.isConnected(getApplicationContext())) {
                showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_BADGES_COUNT);
            } else {
                Utility.alertOffline(getApplicationContext());
            }
        } catch (Exception e) {
            Log.e(TAG, "callApiGetAllBadgesCount Exception : " + e.toString());
        }
    }

    @Override
    public void onFragmentAttached(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    imgHome.setActivated(true);
                    currentMainFragment = fragment;
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top);
                    loadControllerTopMenu(null);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
//                    rlControllerTopMenu.setVisibility(View.VISIBLE);
//                    txtAction.setVisibility(View.VISIBLE);
//                    txtAction.setText(getString(R.string.straddpost));
                    txtAction.setTextColor(getResources().getColor(R.color.color_blue));
                    break;

                case FRAGMENT_OFFICE:
                    imgHome.setActivated(false);
                    imgOffice.setActivated(true);
                    currentMainFragment = fragment;
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_MY_DESK:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(controlTopMenuMyDesk);
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    hideControllerTopAction();
                    break;

                case FRAGMENT_BOOKS:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_books;
                    loadControllerTopMenu(null);
                    rlControllerTopMenu.setVisibility(View.VISIBLE);
                    txtAction.setVisibility(View.VISIBLE);
                    txtAction.setText(getString(R.string.stradd));
                    txtAction.setTextColor(getResources().getColor(R.color.color_blue));
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText(getString(R.string.strTrial));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_books));
                    break;


                //c162
                case FRAGMENT_TRIAL:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(controllerTopMenuTrial);
                    break;


                case FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    break;


                case FRAGMENT_ADDQUESTION_CONTAINER:
                    imgHome.setActivated(false);
                    flFragmentContainerRight.setVisibility(View.GONE);

                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;

                    rlControllerTopMenu.setVisibility(View.VISIBLE);
                    txtAction.setVisibility(View.GONE);
                    txtAction.setText(getString(R.string.straddnew));
                    txtAction.setTextColor(getResources().getColor(R.color.color_blue));
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText(getString(R.string.strTrial));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    imgBack.setVisibility(View.VISIBLE);
                    spSubmenu.setVisibility(View.GONE);
                    break;


                case FRAGMENT_PROFILE_CONTROLLER:
                    currentRightFragment = fragment;
                    imgAuthorProfile.setActivated(true);
                    break;

                case FRAGMENT_HIGHSCORE:
                    currentRightFragment = fragment;
                    imgHighScore.setActivated(true);
                    break;

                case FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS:
                    imgHome.setActivated(false);
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    hideControllerTopAction();
                    txtTitle.setText("");

                    break;

                case FRAGMENT_TRIAL_EXAM_SUBJECTIVE_DETAILS:
                    imgHome.setActivated(false);
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    hideControllerTopAction();
                    txtTitle.setText("");

                    break;

                case FRAGMENT_STUDENT_ATTEMPTED:
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    imgSearch.setActivated(false);
                    imgAuthorProfile.setActivated(false);
                    break;

                case FRAGMENT_ASSESSMENT:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(controllerTopMenuAssessment);
                    break;
                case FRAGMENT_ASSIGNMENT_SUBMITTOR:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(controllerTopMenuAssessment);
                    break;

                case FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(controllerTopMenuAssessment);
                    break;
                case FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(controllerTopMenuAssessment);
                    flFragmentContainerRight.setVisibility(View.GONE);
                    llControllerLeft.setVisibility(View.GONE);
                    break;
                case FRAGMENT_MY_ACTIVITY:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;
                case FRAGMENT_MY_BOOKS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;
                case FRAGMENT_FOLLOWERS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;
                case FRAGMENT_MY_FEEDS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;
                case FRAGMENT_VIEW_PROFILE:
                    imgOffice.setActivated(true);
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;
                case FRAGMENT_ALL_NOTIFICATION:
                    currentMainFragment = fragment;
                    rlControllerTopMenu.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_ALL_MESSAGE:
                    currentMainFragment = fragment;
                    rlControllerTopMenu.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_ALL_STUDYMATE_REQUEST:
                    currentMainFragment = fragment;
                    rlControllerTopMenu.setVisibility(View.VISIBLE);
                    break;

            }

        } catch (Exception e) {
            Debug.i(TAG, "onFragmentAttached Exception : " + e.toString());
        }
    }


    @Override
    public void onFragmentDetached(int fragment) {

        try {
            switch (fragment) {
                case FRAGMENT_OFFICE:
                    imgOffice.setActivated(false);
                    break;
                case FRAGMENT_BOOKS:
                    imgBooks.setActivated(false);
                    break;

                case FRAGMENT_TRIAL:
                    // imgOffice.setActivated(true);
                    break;
                case FRAGMENT_MY_DESK:
                    // imgOffice.setActivated(true);
                    break;

                case FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_ADDQUESTION_CONTAINER:
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    imgAuthorProfile.setActivated(false);
                    break;
                case FRAGMENT_HIGHSCORE:
                    imgHighScore.setActivated(false);
                    break;
                case FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_STUDENT_ATTEMPTED:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_ASSESSMENT:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_ASSIGNMENT_SUBMITTOR:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS:
                    imgOffice.setActivated(true);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_HIGHSCORE, null);
                    break;
                case FRAGMENT_MY_ACTIVITY:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;
                case FRAGMENT_MY_BOOKS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;
                case FRAGMENT_FOLLOWERS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;
                case FRAGMENT_MY_FEEDS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;
                case FRAGMENT_VIEW_PROFILE:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;
                case FRAGMENT_ALL_NOTIFICATION:
                    hideControllerTopBackButton();
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_ALL_MESSAGE:
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_ALL_STUDYMATE_REQUEST:
                    loadControllerTopMenu(null);
                    break;
            }
        } catch (Exception e) {
            Debug.i(TAG, "onFragmentDetached Exception : " + e.toString());
        }


    }


    private void loadControllerTopMenu(ArrayList<ControllerTopMenuItem> menu) {
        try {
            currentControllerTopMenu = menu;
            if (menu == null) {

                hideControllerTopControls();
                rlControllerTopMenu.setVisibility(View.GONE);
            } else {
                rlControllerTopMenu.setVisibility(View.VISIBLE);
                // txtTitle.setVisibility(View.GONE);
                hideControllerTopControls();
                if (currentMainFragment != FRAGMENT_HOME) {
                    Utility.startSlideAnimation(imgBack, -1000, 0, 0, 0);
                    imgBack.setVisibility(View.VISIBLE);
                }

                if (menu.get(0).getMenuItemTitle() != null) {
                    currentControllerTopMenu.get(0).setIsActive(true);
                    txtTitle.setText(currentControllerTopMenu.get(0).getMenuItemTitle());
                    Utility.startSlideAnimation(txtTitle, rlControllerTopMenu.getWidth(), 0, 0, 0);
                    txtTitle.setVisibility(View.VISIBLE);
                    spSubmenu.setVisibility(View.GONE);

                } else {
                    txtTitle.setText("");
                    Utility.startSlideAnimation(txtTitle, 0, rlControllerTopMenu.getWidth(), 0, 0);
                    txtTitle.setVisibility(View.GONE);
                }
                if (currentControllerTopMenu.get(0).getSubMenu() != null) {
                    //txtTitle.setVisibility(View.GONE);
                    spSubmenu.setVisibility(View.VISIBLE);
                    adapterControllerTopSpinner = new ControllerTopSpinnerAdapter(currentControllerTopMenu.get(0).getSubMenu(), AuthorHostActivity.this);
                    spSubmenu.setAdapter(adapterControllerTopSpinner);
                }
                if (currentControllerTopMenu.get(0).getMenuItemAction() != null) {
                    Utility.startSlideAnimation(txtAction, rlControllerTopMenu.getWidth(), 0, 0, 0);
                    txtAction.setText(currentControllerTopMenu.get(0).getMenuItemAction());
                    txtAction.setVisibility(View.VISIBLE);
                } else {
                    txtAction.setVisibility(View.GONE);
                }
            }


        } catch (Exception e) {
            Debug.i(TAG, "loadMenu Exception : " + e.toString());
        }
    }

    public void popupDisplay() {

        PopupWindow popupWindow = new PopupWindow(getActivity());

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.row_trial, null);
        popupWindow = new PopupWindow(view, 400,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchInterceptor(customPopUpTouchListenr);
        popupWindow.showAtLocation(view, Gravity.LEFT, 0, 0);


    }

    View.OnTouchListener customPopUpTouchListenr = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            Debug.d("POPUP", "Touch false");
            return false;
        }

    };

    private void onMenuItemClick(View view) {
        try {
            if (view == imgBack) {
                hideControllerTopControls();
                Debug.i(TAG, "current Fragmnet" + currentMainFragment);
                onBackClick(currentMainFragment);

            } else if (view == txtAction) {
                Debug.i(TAG, "text action");
                handleTheActionButtonFragmentEvents();
            }
        } catch (Exception e) {
            Debug.i(TAG, "onMenuItemClick Exception : " + e.toString());
        }
    }

    private void onBackClick(int currentMainFragment) {

        if (currentMainFragment == FRAGMENT_TRIAL) {

            loadFragmentInMainContainer(FRAGMENT_OFFICE, null);

        } else if (currentMainFragment == FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS) {

            loadFragmentInMainContainer(FRAGMENT_TRIAL, null);
            loadFragmentInRightContainer(currentRightFragment, null);

        } else if (currentMainFragment == FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT) {

            loadFragmentInMainContainer(FRAGMENT_TRIAL, null);

        } else if (currentMainFragment == FRAGMENT_ASSESSMENT) {

            loadFragmentInMainContainer(FRAGMENT_OFFICE, null);

        } else if (currentMainFragment == FRAGMENT_ASSIGNMENT_SUBMITTOR) {

            loadFragmentInMainContainer(FRAGMENT_ASSESSMENT, null);

        } else if (currentMainFragment == FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS) {

            loadFragmentInMainContainer(FRAGMENT_ASSESSMENT, null);
            loadFragmentInRightContainer(FRAGMENT_HIGHSCORE, null);

        } else if (currentMainFragment == FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS) {

            llControllerLeft.setVisibility(View.VISIBLE);
            flFragmentContainerRight.setVisibility(View.VISIBLE);
            loadFragmentInMainContainer(FRAGMENT_ASSESSMENT, null);

        } else if (currentMainFragment == FRAGMENT_ADDQUESTION_CONTAINER) {

            flFragmentContainerRight.setVisibility(View.VISIBLE);
            loadFragmentInMainContainer(FRAGMENT_TRIAL, null);

        } else if (currentMainFragment == FRAGMENT_ALL_NOTIFICATION) {
            listenerHostAllNotification.onControllerTopBackClick();
        } else if (currentMainFragment == FRAGMENT_ALL_MESSAGE) {
            listenerHostAllMessage.onControllerTopBackClick();
        } else if (currentMainFragment == FRAGMENT_MY_DESK) {
            loadFragmentInMainContainer(FRAGMENT_OFFICE, null);
        }


    }

    public void showControllerTopBackButton() {
        Utility.startSlideAnimation(imgBack, -100, 0, 0, 0);
        imgBack.setVisibility(View.VISIBLE);
    }

    private void hideControllerTopControls() {
        if (imgBack.getVisibility() == View.VISIBLE) {
            hideControllerTopBackButton();
        }
        if (txtAction.getVisibility() == View.VISIBLE) {
            hideControllerTopAction();
        }
        if (spSubmenu.getVisibility() == View.VISIBLE) {
            hideControllerTopSpinner();
        }
    }

    private void hideControllerTopBackButton() {
        Utility.startSlideAnimation(imgBack, 0, -100, 0, 0);
        imgBack.setVisibility(View.GONE);
    }

    private void hideControllerTopAction() {
        Utility.startSlideAnimation(txtAction, 0, rlControllerTopMenu.getWidth(), 0, 0);
        txtAction.setText("");
        txtAction.setVisibility(View.GONE);
    }

    private void hideControllerTopSpinner() {
        spSubmenu.setAdapter(null);
        Utility.startSlideAnimation(spSubmenu, 0, -1000, 0, 0);
        spSubmenu.setVisibility(View.GONE);
    }


    public void openRightContainerFragment(View view) {
        switch (view.getId()) {
            case R.id.img_author_profile:
                loadFragmentInRightContainer(FRAGMENT_PROFILE_CONTROLLER, null);
                break;
            case R.id.img_high_score:
                loadFragmentInRightContainer(FRAGMENT_HIGHSCORE, null);
                break;
        }


    }


    public void openMainContainerFragment(View view) {
        switch (view.getId()) {
            case R.id.img_home:
                loadFragmentInMainContainer(FRAGMENT_HOME, null);
                break;
            case R.id.img_office:
                loadFragmentInMainContainer(FRAGMENT_OFFICE, null);
                break;
            case R.id.img_books:
                loadFragmentInMainContainer(FRAGMENT_BOOKS, null);
                break;


        }

    }

    public void searchFeeds(View view) {
        imgSearch.setActivated(!imgSearch.isActivated());
        if (etSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
            etSearch.setVisibility(View.GONE);
            Utility.hideKeyboard(getActivity(), getCurrentFocus());
        } else {
            Utility.startSlideAnimation(etSearch, etSearch.getWidth(), 0, 0, 0);
            Utility.startSlideAnimation(imgSearch, etSearch.getWidth(), 0, 0, 0);
            etSearch.setVisibility(View.VISIBLE);
            Utility.showSoftKeyboard(etSearch, getActivity());
        }


    }

    public void logOut(View view) {
    }

    private Activity getActivity() {
        return AuthorHostActivity.this;

    }


    private void handleTheActionButtonFragmentEvents() {
        if (currentMainFragment == FRAGMENT_HOME) {

        } else if (currentMainFragment == FRAGMENT_TRIAL) {

            loadFragmentInMainContainer(FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT, null);

        } else if (currentMainFragment == FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT) {

        } else if (currentMainFragment == FRAGMENT_ADDQUESTION_CONTAINER) {

        }


    }

    public void showProgress() {
        progress_bar.setProgress(1);
        progress_bar.setEnabled(false);
        progress_bar.setVisibility(View.VISIBLE);
        progressGenerator.start(progress_bar);
    }

    public void hideProgress() {
        progress_bar.setProgress(100);
        progress_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        hideProgress();
        try {
            if (WebConstants.GENERAL_SETTING_PREFERENCES == apiCode) {
                // onResponseGetAllPreference(object, error);

            } else if (WebConstants.GET_USER_PREFERENCES == apiCode) {
                //  onResponseGetUserPreference(object, error);

            } else if (WebConstants.GET_ALL_BADGES_COUNT == apiCode) {
                onResponseGetAllBadges(object, error);
            }
        } catch (Exception e) {
            Debug.i(TAG, "On response Exception : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetAllBadges(Object object, Exception error) {
        try {

            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "Successload badges count");
                    String count = responseHandler.getBadges().get(0).getNotificationCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_NOTIFICATION, this, count != null ? Integer.valueOf(count) : 0);

                    count = responseHandler.getBadges().get(0).getMessageCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_MESSAGE, this, count != null ? Integer.valueOf(count) : 0);

                    count = responseHandler.getBadges().get(0).getRequestCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_REQUEST, this, count != null ? Integer.valueOf(count) : 0);
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "Failed to load badges count");
                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseGetAllBadges api Exceptiion : " + error.toString());
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponseGetAllBadges Exceptiion : " + e.toString());
        }
    }

    public interface AddToFavouriteListner {
        public void onAddToFav(int position);

        public void onRemoveFromFav(int position);

        public void onAddToLibrary(String id);

        public void onRemoveFromLibrary(String id);

        public void onSearchFav(ArrayList<BookData> arrayList);

        public void onSearchSuggested(ArrayList<BookData> arrayList);
    }


}
