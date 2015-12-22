
package com.ism.author.activtiy;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utility;
import com.ism.author.adapter.ControllerTopSpinnerAdapter;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.BooksFragment;
import com.ism.author.fragment.HomeFragment;
import com.ism.author.fragment.OfficeFragment;
import com.ism.author.fragment.TrialFragment;
import com.ism.author.fragment.assessment.AssignmentsSubmittorFragment;
import com.ism.author.fragment.assessment.ExamsFragment;
import com.ism.author.fragment.assessment.objectiveassessment.ObjectiveAssignmentQuestionsFragment;
import com.ism.author.fragment.assessment.subjectiveassessment.SubjectiveAssignmentQuestionsContainerFragment;
import com.ism.author.fragment.createexam.CreateExamAssignmentContainerFragment;
import com.ism.author.fragment.createexam.CreateExamFragment;
import com.ism.author.fragment.createquestion.AddQuestionContainerFragment;
import com.ism.author.fragment.gotrending.GoTrendingFragment;
import com.ism.author.fragment.gotrending.PastFragment;
import com.ism.author.fragment.mydesk.AddAssignmentFragment;
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
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.ControllerTopMenuItem;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import realmhelper.AuthorHelper;

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
    private HostListenerAllNotification listenerHostAllNotification;
    private HostListenerAllMessage listenerHostAllMessage;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuTrial, currentControllerTopMenu, controllerTopMenuMyThirty,

    controllerTopMenuAssessment, controllerTopMenuMyDesk, controllerTopMenuBooks, controllerTopMenuGoTrading, controllerTopSubMenuGoTrading;
    /*
    * these are the fragments for the main fragment.
    * */
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_OFFICE = 1;
    public static final int FRAGMENT_BOOKS = 2;
    public static final int FRAGMENT_MY_DESK = 3;
    public static final int FRAGMENT_GOTRENDING = 4;
    public static final int FRAGMENT_TRIAL = 5;
    public static final int FRAGMENT_ASSESSMENT = 6;
    public static final int FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT = 7;
    public static final int FRAGMENT_ADDQUESTION_CONTAINER = 8;
    public static final int FRAGMENT_ASSIGNMENT_SUBMITTOR = 9;
    public static final int FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS = 10;
    public static final int FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER = 11;
    public static final int FRAGMENT_ALL_MESSAGE = 12;
    public static final int FRAGMENT_ALL_NOTIFICATION = 13;
    public static final int FRAGMENT_ALL_STUDYMATE_REQUEST = 14;
    public static final int FRAGMENT_PAST = 15;
    public static final int FRAGMENT_ADD_ASSIGNMENT = 16;


    //these are the right side fragments

    public static final int FRAGMENT_PROFILE_CONTROLLER = 31;
    public static final int FRAGMENT_HIGHSCORE = 32;
    public static final int FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT = 34;

    public static final int FRAGMENT_MY_FEEDS = 35;
    public static final int FRAGMENT_FOLLOWERS = 36;
    //    public static final int FRAGMENT_MY_BOOKS = 38;
    public static final int FRAGMENT_MY_ACTIVITY = 37;
    //    public static final int FRAGMENT_VIEW_PROFILE = 39;
    private InputMethodManager inputMethod;

    public static int currentMainFragment;
    public static int currentRightFragment;
    private int currentMainFragmentBg;
    private ActionProcessButton progress_bar;
    private ProgressGenerator progressGenerator;
    private BooksListner booksListner;
    private HostListenerTrending listenerHostTrending;


    public interface HostListenerProfileController {
        public void onBadgesFetched();

        public void onSubFragmentAttached(int fragmentId);

        public void onSubFragmentDetached(int fragmentId);
        //  public void onControllerMenuItemClicked(int position);
    }

    public interface HostListenerTrending {
        public void onViewSelect(int position);
    }

    public interface BooksListner {
        public void onAddToFav(int position);

        public void onRemoveFromFav(int position);

        public void onAddToLibrary(String id);

        public void onRemoveFromLibrary(String id);
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

    public void setListenerHostTrending(HostListenerTrending listenerHostTrending) {
        this.listenerHostTrending = listenerHostTrending;
    }

    public void setListenerHostAllMessage(HostListenerAllMessage listenerHostAllMessage) {
        this.listenerHostAllMessage = listenerHostAllMessage;
    }

    public void setListenerBook(BooksListner booksListner) {
        this.booksListner = booksListner;
    }

    /**
     * object of realm database.
     */
    private AuthorHelper authorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_host);

        inigGlobal();

    }

    private void inigGlobal() {
//        IOSocketHandler.ConnectSocket();


        Global.myTypeFace = new MyTypeFace(getApplicationContext());
        Global.imageLoader = ImageLoader.getInstance();
        Global.imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        Global.strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, AuthorHostActivity.this);
        Global.strFullName = PreferenceData.getStringPrefs(PreferenceData.USER_FULL_NAME, AuthorHostActivity.this);
        Global.strProfilePic = WebConstants.USER_IMAGES + PreferenceData.getStringPrefs(PreferenceData.USER_PROFILE_PIC, AuthorHostActivity.this);
        Global.authorHelper = new AuthorHelper(getActivity());


//        Global.strUserId = "52";
//        Global.strFullName = "Chirag Mistry";
//        Global.strProfilePic = WebConstants.USER_IMAGES + "user_52/_dev_chirag.png";

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

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
        controllerTopMenuMyDesk = ControllerTopMenuItem.getMenuMyDesk(AuthorHostActivity.this);
        controllerTopMenuAssessment = ControllerTopMenuItem.getMenuAssessment(AuthorHostActivity.this);
        controllerTopMenuBooks = ControllerTopMenuItem.getMenuBooks(AuthorHostActivity.this);
        controllerTopMenuMyThirty = ControllerTopMenuItem.getMenuMyThirty(AuthorHostActivity.this);
        controllerTopMenuGoTrading = ControllerTopMenuItem.getMenuGoTrading(AuthorHostActivity.this);
        controllerTopSubMenuGoTrading = ControllerTopMenuItem.getMenuGoTradingSubMenu(AuthorHostActivity.this);

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

        loadFragmentInMainContainer(FRAGMENT_HOME);

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


    /*this bundle used to pass data between fragments and also managing backstack for the fragment*/
    /*remove bundle data on backclcik of fragment as it is not necessory*/

    Bundle bundle = new Bundle();

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    //These is for the load fragmet in main container
    public void loadFragmentInMainContainer(int fragment) {

        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            HomeFragment.newInstance()).commit();
                    break;
                case FRAGMENT_OFFICE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            OfficeFragment.newInstance()).commit();
                    break;
                case FRAGMENT_BOOKS:

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            BooksFragment.newInstance()).commit();
                    break;

                case FRAGMENT_MY_DESK:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_MYDESK);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            MyDeskFragment.newInstance(), AppConstant.FRAGMENT_MYDESK).commit();
                    break;

                case FRAGMENT_ADD_ASSIGNMENT:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_ADD_ASSIGNMENT);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AddAssignmentFragment.newInstance(), AppConstant.FRAGMENT_ADD_ASSIGNMENT).commit();
                    break;

                case FRAGMENT_TRIAL:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_TRIAL);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            TrialFragment.newInstance(), AppConstant.FRAGMENT_TRIAL).commit();
                    break;

                case FRAGMENT_GOTRENDING:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_GOTRENDING);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            GoTrendingFragment.newInstance(), AppConstant.FRAGMENT_GOTRENDING).commit();
                    break;
                case FRAGMENT_PAST:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_PAST);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            PastFragment.newInstance(), AppConstant.FRAGMENT_PAST).commit();
                    break;

                case FRAGMENT_ASSESSMENT:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_ASSESSMENT);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            ExamsFragment.newInstance(), AppConstant.FRAGMENT_ASSESSMENT).commit();
                    break;

                case FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_CREATEEXAMCONTAINER);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            CreateExamAssignmentContainerFragment.newInstance(), AppConstant.FRAGMENT_CREATEEXAMCONTAINER).commit();

                    break;

                case FRAGMENT_ASSIGNMENT_SUBMITTOR:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_ASSIGNMENT_SUBMITTOR);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AssignmentsSubmittorFragment.newInstance(), AppConstant.FRAGMENT_ASSIGNMENT_SUBMITTOR).commit();
                    break;

                case FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            ObjectiveAssignmentQuestionsFragment.newInstance(), AppConstant.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS).commit();
                    break;

                case FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            SubjectiveAssignmentQuestionsContainerFragment.newInstance(),
                            AppConstant.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER).commit();
                    break;

                case FRAGMENT_ADDQUESTION_CONTAINER:

                    setBackStackFragmentKey(AppConstant.FRAGMENT_ADDQUESTION_CONTAINER);
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AddQuestionContainerFragment.newInstance(),
                            AppConstant.FRAGMENT_ADDQUESTION_CONTAINER).commit();
                    break;

                case FRAGMENT_MY_FEEDS:

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            MyFeedsFragment.newInstance()).commit();
                    break;

//                case FRAGMENT_MY_BOOKS:
//
//                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
//                            AssignmentsSubmittorFragment.BooksFragment.newInstance()).commit();
//                    break;

                case FRAGMENT_FOLLOWERS:

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            FollowersFragment.newInstance()).commit();
                    break;

                case FRAGMENT_MY_ACTIVITY:

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            MyActivityFragment.newInstance()).commit();
                    break;


                case FRAGMENT_ALL_NOTIFICATION:

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllNotificationFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_MESSAGE:

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllMessageFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_STUDYMATE_REQUEST:

                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllStudymateRequestFragment.newInstance()).commit();
                    break;
            }
            currentMainFragment = fragment;

        } catch (Exception e) {
            Debug.i(TAG, "loadFragment Exception : " + e.toString());
        }

    }

    private void setBackStackFragmentKey(String fragmentName) {

        if (!this.bundle.containsKey(fragmentName)) {
            this.bundle.putInt(fragmentName, currentMainFragment);
        }
    }

    /*This is method will use to laod selected user evaluation data in case of assessment */
//    private ObjectiveAssignmentQuestionsFragment mFragment;

    public void loadStudentEvaluationData() {
        ObjectiveAssignmentQuestionsFragment objectiveAssignmentQuestionsFragment = (ObjectiveAssignmentQuestionsFragment)
                getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
        objectiveAssignmentQuestionsFragment.loadStudentEvaluationData();
    }


    //these is for the load fragment in right container.
    public void loadFragmentInRightContainer(int fragment) {

        try {
            switch (fragment) {

                case FRAGMENT_PROFILE_CONTROLLER:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, AuthorProfileFragment.newInstance()).commit();
                    break;

                case FRAGMENT_HIGHSCORE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, HighScoreFragment.newInstance()).commit();
                    break;

                case FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, StudentAttemptedAssignmentFragment.newInstance()).commit();
                    break;
            }
            currentRightFragment = fragment;
        } catch (Exception e) {
            Debug.i(TAG, "loadFragment Exception : " + e.toString());

        }

    }


    /*topbar values
    * --currentfragmnet
    * --currentfragmentcolor
    * --homeenable
    * --officeenable
    * --booksenable
    * --menu
    * --isactionbutton visible*/
    @Override
    public void onFragmentAttached(int fragment) {
        try {
            switch (fragment) {

                case FRAGMENT_HOME:
                    setTopBarValues(fragment, getResources().getColor(R.color.color_blue), true, false, false, null, false);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                    }
                    break;

                case FRAGMENT_OFFICE:
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, null, false);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
//                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
//                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
//                    }
                    break;

                case FRAGMENT_BOOKS:
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_books), false, false, true, controllerTopMenuBooks, false);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                    }
                    break;

                case FRAGMENT_MY_DESK:

                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopMenuMyDesk, false);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    if (currentRightFragment != FRAGMENT_PROFILE_CONTROLLER) {
                        loadFragmentInRightContainer(FRAGMENT_PROFILE_CONTROLLER);
                    }
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;

                case FRAGMENT_ADD_ASSIGNMENT:
                    // setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopMenuMyDesk, false);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
//                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
//                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
//                    }
                    break;

                case FRAGMENT_TRIAL:
                    ArrayList<ControllerTopMenuItem> menu_trial = null;
                    if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_TRIAL) {
                        menu_trial = controllerTopMenuTrial;
                    } else {
                        menu_trial = controllerTopMenuMyThirty;
                    }
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, menu_trial, true);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                    }
                    break;

                case FRAGMENT_ASSESSMENT:
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopMenuAssessment, true);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                    }
                    break;
                case FRAGMENT_GOTRENDING:
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopMenuGoTrading, true);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
//                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
//                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
//                    }
                    break;

                case FRAGMENT_PAST:
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopSubMenuGoTrading, false);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
//                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
//                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
//                    }
                    break;

                case FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT:
                    ArrayList<ControllerTopMenuItem> menu = null;
                    if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_TRIAL) {
                        menu = controllerTopMenuTrial;
                    } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_MYTHIRTY) {
                        menu = controllerTopMenuMyThirty;
                    } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_ASSESSMENT) {
                        menu = controllerTopMenuAssessment;
                    }
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, menu, false);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                    }
                    break;

                case FRAGMENT_ASSIGNMENT_SUBMITTOR:
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopMenuAssessment, true);

                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                    }
                    break;

                case FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS:

                    ArrayList<ControllerTopMenuItem> menu_view_questions = null;
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);

                    if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_TRIAL) {
                        menu_view_questions = controllerTopMenuTrial;

                        if (currentRightFragment != FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT) {
                            loadFragmentInRightContainer(FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT);
                        }
                    } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_MYTHIRTY) {
                        menu_view_questions = controllerTopMenuMyThirty;

                        if (currentRightFragment != FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT) {
                            loadFragmentInRightContainer(FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT);
                        }
                    } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_ASSESSMENT) {
                        menu_view_questions = controllerTopMenuAssessment;

                        if (getBundle().containsKey(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION)) {
                            if (getBundle().getBoolean(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION)) {
                                if (currentRightFragment != FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT) {
                                    loadFragmentInRightContainer(FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT);
                                }
                            } else {
                                if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                                    loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                                }
                            }
                        } else {
                            if (currentRightFragment != FRAGMENT_HIGHSCORE) {
                                loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                            }
                        }


                    }
                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, menu_view_questions, false);

                    break;

                case FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER:

                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopMenuAssessment, true);
                    llControllerLeft.setVisibility(View.GONE);
                    flFragmentContainerRight.setVisibility(View.GONE);
                    break;


                case FRAGMENT_ADDQUESTION_CONTAINER:

                    ArrayList<ControllerTopMenuItem> menuAddQuestion = null;

                    if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_TRIAL) {

                        menuAddQuestion = controllerTopMenuTrial;

                    } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_MYTHIRTY) {

                        menuAddQuestion = controllerTopMenuMyThirty;

                    } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_ASSESSMENT) {
                        menuAddQuestion = controllerTopMenuAssessment;
                    }

                    setTopBarValues(fragment, getResources().getColor(R.color.bg_office), false, true, false, controllerTopMenuAssessment, false);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.GONE);
                    break;


                case FRAGMENT_PROFILE_CONTROLLER:

                    currentRightFragment = fragment;
                    imgAuthorProfile.setActivated(true);
                    break;

                case FRAGMENT_HIGHSCORE:

                    currentRightFragment = fragment;
                    imgHighScore.setActivated(true);
                    break;


                case FRAGMENT_MY_ACTIVITY:
                    setTopBarValues(fragment, getResources().getColor(R.color.color_blue), false, false, false, null, false);
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;

//                case FRAGMENT_MY_BOOKS:
//
//                    currentMainFragment = fragment;
//                    listenerHostProfileController.onSubFragmentAttached(fragment);
//                    break;

                case FRAGMENT_FOLLOWERS:
                    setTopBarValues(fragment, getResources().getColor(R.color.color_blue), false, false, false, null, false);
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;

                case FRAGMENT_MY_FEEDS:
                    setTopBarValues(fragment, getResources().getColor(R.color.color_blue), false, false, false, null, false);
                    // llControllerLeft.setVisibility(View.VISIBLE);
                    //flFragmentContainerRight.setVisibility(View.VISIBLE);
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentAttached(fragment);
                    break;

//                case FRAGMENT_VIEW_PROFILE:
//
//                   // imgOffice.setActivated(true);
//                    currentMainFragment = fragment;
//                    listenerHostProfileController.onSubFragmentAttached(fragment);
//                    break;

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

                case FRAGMENT_BOOKS:
                    imgBooks.setActivated(false);
                    break;

                case FRAGMENT_HOME:
                    imgHome.setActivated(false);
                    break;

                case FRAGMENT_OFFICE:
                    imgOffice.setActivated(false);
                    break;

                case FRAGMENT_MY_DESK:
                    imgOffice.setActivated(true);
                    listenerHostProfileController.onSubFragmentDetached(fragment);

                    break;

                case FRAGMENT_ADD_ASSIGNMENT:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_TRIAL:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_ASSESSMENT:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_GOTRENDING:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_PAST:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_ASSIGNMENT_SUBMITTOR:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS:
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER:
                    imgOffice.setActivated(true);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_HIGHSCORE);
                    break;

                case FRAGMENT_ADDQUESTION_CONTAINER:
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    break;

                case FRAGMENT_MY_FEEDS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;

//                case FRAGMENT_MY_BOOKS:
//                    currentMainFragment = fragment;
//                    listenerHostProfileController.onSubFragmentDetached(fragment);
//                    break;

                case FRAGMENT_FOLLOWERS:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;

                case FRAGMENT_MY_ACTIVITY:
                    currentMainFragment = fragment;
                    listenerHostProfileController.onSubFragmentDetached(fragment);
                    break;

//                case FRAGMENT_VIEW_PROFILE:
//                    currentMainFragment = fragment;
//                    listenerHostProfileController.onSubFragmentDetached(fragment);
//                    break;

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

                case FRAGMENT_PROFILE_CONTROLLER:
                    imgAuthorProfile.setActivated(false);
                    break;

                case FRAGMENT_HIGHSCORE:
                    imgHighScore.setActivated(false);
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

    @Override
    public void onBackPressed() {
        if (currentMainFragment == FRAGMENT_HOME) {
            super.onBackPressed();
        }
    }

    /*handle top action button clcik events*/
    private void onMenuItemClick(View view) {
        try {
            if (view == imgBack) {
//                hideControllerTopControls();
                onBackClick(currentMainFragment);

            } else if (view == txtAction) {
                handleTheActionButtonFragmentEvents();
            }
        } catch (Exception e) {
            Debug.i(TAG, "onMenuItemClick Exception : " + e.toString());
        }
    }


    /*to handle back click events*/
    private void onBackClick(int currentMainFragment) {

        /*This is the new code for backstack management*/

        Debug.e(TAG, "The current Main fragment is:::" + currentMainFragment);
        switch (currentMainFragment) {

            case FRAGMENT_HOME:
                onBackPressed();
                break;

            case FRAGMENT_BOOKS:
                loadFragmentInMainContainer(FRAGMENT_HOME);
                break;

            case FRAGMENT_MY_DESK:
                MyDeskFragment myDeskFragment = (MyDeskFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_MYDESK);
                myDeskFragment.onBackClick();
                break;

            case FRAGMENT_ADD_ASSIGNMENT:
                AddAssignmentFragment addAssignmentFragment = (AddAssignmentFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_ADD_ASSIGNMENT);
                addAssignmentFragment.onBackClick();
                break;

            case FRAGMENT_GOTRENDING:
                GoTrendingFragment goTrendingFragment = (GoTrendingFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_GOTRENDING);
                goTrendingFragment.onBackClick();
                break;

            case FRAGMENT_PAST:
                PastFragment pastFragment = (PastFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_PAST);
                pastFragment.onBackClick();
                break;

            case FRAGMENT_TRIAL:
                TrialFragment trialFragment = (TrialFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_TRIAL);
                trialFragment.onBackClick();
                break;

            case FRAGMENT_ASSESSMENT:
                ExamsFragment examsFragment = (ExamsFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_ASSESSMENT);
                examsFragment.onBackClick();
                break;

            case FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT:
                CreateExamAssignmentContainerFragment createExamAssignmentContainerFragment = (CreateExamAssignmentContainerFragment)
                        getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_CREATEEXAMCONTAINER);
                createExamAssignmentContainerFragment.onBackClick();
                break;

            case FRAGMENT_ASSIGNMENT_SUBMITTOR:
                AssignmentsSubmittorFragment assignmentsSubmittorFragment = (AssignmentsSubmittorFragment)
                        getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_ASSIGNMENT_SUBMITTOR);
                assignmentsSubmittorFragment.onBackClick();
                break;

            case FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS:
                ObjectiveAssignmentQuestionsFragment objectiveAssignmentQuestionsFragment = (ObjectiveAssignmentQuestionsFragment)
                        getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
                objectiveAssignmentQuestionsFragment.onBackClick();
                break;

            case FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER:
                SubjectiveAssignmentQuestionsContainerFragment subjectiveAssignmentQuestionsContainerFragment = (SubjectiveAssignmentQuestionsContainerFragment)
                        getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER);
                subjectiveAssignmentQuestionsContainerFragment.onBackClick();
                break;

            case FRAGMENT_ADDQUESTION_CONTAINER:
                AddQuestionContainerFragment addQuestionContainerFragment = (AddQuestionContainerFragment)
                        getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_ADDQUESTION_CONTAINER);
                addQuestionContainerFragment.onBackClick();
                break;

        }

    }


    /*for handling clcik of top action button*/
    private void handleTheActionButtonFragmentEvents() {

        if (currentMainFragment == FRAGMENT_TRIAL || currentMainFragment == FRAGMENT_ASSESSMENT
                || currentMainFragment == FRAGMENT_ASSIGNMENT_SUBMITTOR) {

            getBundle().putBoolean(CreateExamFragment.ARG_IS_CREATE_EXAM, true);
            loadFragmentInMainContainer(FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT);

        } else if (currentMainFragment == FRAGMENT_ADDQUESTION_CONTAINER) {

        } else if (currentMainFragment == FRAGMENT_GOTRENDING) {
            loadFragmentInMainContainer(FRAGMENT_PAST);
        }


    }

    /*
    * common method to set the top action bar values*/
    private void setTopBarValues(int currentFragment, Integer currentMainFragmentBgColor, Boolean isHomeEnable, Boolean isOfficeEnable,
                                 Boolean isBooksEnable, ArrayList<ControllerTopMenuItem> menu, Boolean isActionButtonVisible) {

        currentMainFragment = currentFragment;
        currentMainFragmentBg = currentMainFragmentBgColor;
        imgHome.setActivated(isHomeEnable);
        imgOffice.setActivated(isOfficeEnable);
        imgBooks.setActivated(isBooksEnable);
        rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
        txtAction.setTextColor(getResources().getColor(R.color.color_blue));
        txtTitle.setTextColor(currentMainFragmentBgColor);
        loadControllerTopMenu(menu);
        if (isActionButtonVisible) {
            txtAction.setVisibility(View.VISIBLE);
        } else {
            txtAction.setVisibility(View.GONE);
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

    /*these are the onClick Methods to handle click events from xml*/
    public void openRightContainerFragment(View view) {
        switch (view.getId()) {
            case R.id.img_author_profile:
                loadFragmentInRightContainer(FRAGMENT_PROFILE_CONTROLLER);
                break;
            case R.id.img_high_score:
                loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                if(currentMainFragment!=FRAGMENT_HOME)
                    loadFragmentInMainContainer(FRAGMENT_HOME);
                break;
        }
    }

    public void openMainContainerFragment(View view) {
        removeBundleArguments();
        switch (view.getId()) {
            case R.id.img_logo:
                loadFragmentInMainContainer(FRAGMENT_HOME);
                break;
            case R.id.img_home:
                loadFragmentInMainContainer(FRAGMENT_HOME);
                break;
            case R.id.img_office:
                loadFragmentInMainContainer(FRAGMENT_OFFICE);
                break;
            case R.id.img_books:
                loadFragmentInMainContainer(FRAGMENT_BOOKS);
                break;
        }
    }


    /*for search post feeds*/
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

        PreferenceData.clearWholePreference(getActivity());
        Intent intentLogin = new Intent(getActivity(), AuthorLoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    private Activity getActivity() {
        return AuthorHostActivity.this;

    }

    /*methods for handling top progress bar*/
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


    /*This is to handle backstack for particular fragment */

    public void handleBackClick(String fragmentName) {

        if (fragmentName != AppConstant.FRAGMENT_ADDQUESTION_CONTAINER) {
            loadFragmentInMainContainer(getBundle().getInt(fragmentName));
            getBundle().remove(fragmentName);

        } else {
            /*here load main assessment fragment because in copy new exam get created so we cant load previous fragments with previus exam data*/
            if (getBundle().getBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                /*here load assessment fragment in case of copy exam*/
                loadFragmentInMainContainer(FRAGMENT_ASSESSMENT);
                removeBundleArguments();

            } else {

                loadFragmentInMainContainer(getBundle().getInt(fragmentName));
                getBundle().remove(fragmentName);

            }

        }

    }

    private void removeBundleArguments() {

        getBundle().clear();


//        if (getBundle().containsKey(AssignmentSubmittorAdapter.ARG_STUDENT_ID)) {
//            Utils.showToast("VALUE IS THERE", getActivity());
//        } else {
//            Utils.showToast("VALUE IS NOT THERE", getActivity());
//        }

//        getBundle().remove(CreateExamFragment.ARG_IS_CREATE_EXAM);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_ID);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_NAME);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_CLASSROOM_ID);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_BOOK_ID);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_BOOK_NAME);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_CATEGORY);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_MODE);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_PASS_PERCENTAGE);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_DURATION);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_ATTEMPT_COUNT);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_INSTRUCTIONS);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_IS_RANDOM_QUESTION);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_IS_DECLARE_RESULTS);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_ASSESSOR);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_START_DATE);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_START_TIME);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_CREATED_DATE);
//        getBundle().remove(ExamsAdapter.ARG_EXAM_NO);
//        getBundle().remove(ExamsAdapter.ARG_FRAGMENT_TYPE);
//        getBundle().remove(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION);
//        getBundle().remove(AssignmentSubmittorAdapter.ARG_STUDENT_ID);
//        getBundle().remove(AssignmentSubmittorAdapter.ARG_STUDENT_POSITION);
//        getBundle().remove(AssignmentSubmittorAdapter.ARG_STUDENT_PROFILE_PIC);
//        getBundle().remove(AssignmentSubmittorAdapter.ARG_STUDENT_NAME);
//        getBundle().remove(ObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
//        getBundle().remove(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_TYPE);
//        getBundle().remove(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY);
//        getBundle().remove(MyStudentListAdapter.ARG_ARR_LIST_STUDENTS);


    }
}
