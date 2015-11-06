package com.ism.author;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.adapter.ControllerTopSpinnerAdapter;
import com.ism.author.fragment.AddQuestionFragment;
import com.ism.author.fragment.BooksFragment;
import com.ism.author.fragment.CreateAssignmentFragment;
import com.ism.author.fragment.HomeFragment;
import com.ism.author.fragment.OfficeFragment;
import com.ism.author.fragment.StudentAttemptedFragment;
import com.ism.author.fragment.TrialExamDetailFragment;
import com.ism.author.fragment.TrialFragment;
import com.ism.author.helper.ControllerTopMenuItem;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.rightcontainerfragment.AuthorProfileFragment;
import com.ism.author.rightcontainerfragment.HighScoreFragment;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

import java.util.ArrayList;

/*
* these class is for the main screen after login contains the host activity for managing the main and container fragment.
* */
public class AuthorHostActivity extends Activity implements FragmentListener {

    protected static final String TAG = AuthorHostActivity.class.getName();


    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;

    RelativeLayout rlControllerTop, rlControllerTopMenu;
    LinearLayout llSearch, llControllerLeft;
    FrameLayout flFragmentContainerMain, flFragmentContainerRight;

    ImageView imgLogo, imgHome, imgBack, imgSearch, imgOffice, imgBooks, imgEditProfileHome, imgEditProfileTutorial, imgEditProfileClassroom,
            imgEditProfileAssesment, imgAuthorProfile, imgHighScore, imgChat;

    Spinner spSubmenu;

    TextView txtTitle, txtAction;


    EditText etSearch;

    private View.OnClickListener onClickMenuItem;
    private ControllerTopSpinnerAdapter adapterControllerTopSpinner;
    private HostListener listenerHost;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuTrial;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuHome;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuTrialMenu;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuReportCard;
    private TextView txtsMenu[];
    private ArrayList<ControllerTopMenuItem> controllerTopMenuAuthorOffice, currentControllerTopMenu;

    /*
    * these are the fragments for the main fragment.
    * */

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_OFFICE = 1;
    public static final int FRAGMENT_BOOKS = 2;
    public static final int FRAGMENT_MYFEEDS = 3;
    public static final int FRAGMENT_FOLLOWING = 4;
    public static final int FRAGMENT_MYACTIVITY = 5;
    public static final int FRAGMENT_GOTRENDING = 6;
    public static final int FRAGMENT_SETQUIZ = 7;
    public static final int FRAGMENT_PROGRESSREPORT = 8;
    public static final int FRAGMENT_TRIAL = 9;
    public static final int FRAGMENT_ADDNEWTRIAL = 10;
    public static final int FRAGMENT_ADDQUESTION = 11;
    public static final int FRAGMENT_TRIAL_EXAM_DETAILS = 16;

    //these are the right side fragments

    public static final int FRAGMENT_AUTHORPROFILE = 12;
    public static final int FRAGMENT_HIGHSCORE = 13;
    public static final int FRAGMENT_CHAT = 14;
    public static final int FRAGMENT_STUDENT_ATTEMPTED = 15;


    private InputMethodManager inputMethod;
    //these are the fragments for the author edit profile screen.

//    public static final int FRAGMENT_TUTORIAL = 3;
//    public static final int FRAGMENT_CLASSROOM = 4;
//    public static final int FRAGMENT_ASSESSMENT = 5;

    public static int currentMainFragment;
    public static int currentRightFragment;
    private int currentMainFragmentBg;
    private ActionProcessButton progress_bar;
    private ProgressGenerator progressGenerator;


    public interface HostListener {
        public void onControllerMenuItemClicked(int position);
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

        rlControllerTop = (RelativeLayout) findViewById(R.id.rl_controller_top);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        rlControllerTopMenu = (RelativeLayout) findViewById(R.id.rl_controller_top_menu);
        llControllerLeft = (LinearLayout) findViewById(R.id.ll_controller_left);
        flFragmentContainerMain = (FrameLayout) findViewById(R.id.fl_fragment_container_main);
        flFragmentContainerRight = (FrameLayout) findViewById(R.id.fl_fragment_container_right);
        progress_bar = (ActionProcessButton) findViewById(R.id.progress_bar);
        progressGenerator=new ProgressGenerator();

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
        controllerTopMenuHome = ControllerTopMenuItem.getMenuHome(AuthorHostActivity.this);
        controllerTopMenuTrialMenu = ControllerTopMenuItem.getMenuTrialSubMenu(AuthorHostActivity.this);
        spSubmenu = (Spinner) findViewById(R.id.sp_submenu);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtAction = (TextView) findViewById(R.id.txt_action);
        etSearch = (EditText) findViewById(R.id.et_search);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i(TAG, "search clicked");
                }
                return false;
            }
        });

        imgAuthorProfile = (ImageView) findViewById(R.id.img_author_profile);
        imgHighScore = (ImageView) findViewById(R.id.img_high_score);
        imgChat = (ImageView) findViewById(R.id.img_chat);

//        txtsMenu = new TextView[]{txtOne, txtTwo, txtThree};

        loadFragmentInMainContainer(FRAGMENT_HOME);
        loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);


        //controllerTopMenuAuthorOffice = ControllerTopMenuItem.getMenuAuthorOffice(getActivity());

        onClickMenuItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClick(v);
            }
        };

        imgBack.setOnClickListener(onClickMenuItem);
//        txtOne.setOnClickListener(onClickMenuItem);
//        txtTwo.setOnClickListener(onClickMenuItem);
//        txtThree.setOnClickListener(onClickMenuItem);
        txtAction.setOnClickListener(onClickMenuItem);


    }


    //These is for the load fragmet in main container

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void loadFragmentInMainContainer(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, HomeFragment.newInstance()).commit();
                    break;

                case FRAGMENT_OFFICE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, OfficeFragment.newInstance()).commit();
//                    mFragmentTransaction = mFragmentManager.beginTransaction();
//                    mFragmentTransaction.add(R.id.fl_fragment_container_main, OfficeFragment.newInstance());
//                    mFragmentTransaction.addToBackStack(String.valueOf(FRAGMENT_OFFICE));
//                    mFragmentTransaction.commit();

                    break;
                case FRAGMENT_BOOKS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, BooksFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TRIAL:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TrialFragment.newInstance()).commit();
//                    mFragmentTransaction = mFragmentManager.beginTransaction();
//                    mFragmentTransaction.add(R.id.fl_fragment_container_main, TrialFragment.newInstance());
//                    mFragmentTransaction.addToBackStack(String.valueOf(FRAGMENT_TRIAL));
//                    mFragmentTransaction.commit();

                    break;
                case FRAGMENT_ADDNEWTRIAL:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, CreateAssignmentFragment.newInstance()).commit();
//                    mFragmentTransaction = mFragmentManager.beginTransaction();
//                    mFragmentTransaction.add(R.id.fl_fragment_container_main, TrialAddNewFragment.newInstance());
//                    mFragmentTransaction.addToBackStack(String.valueOf(FRAGMENT_ADDNEWTRIAL));
//                    mFragmentTransaction.commit();
                    break;

                case FRAGMENT_ADDQUESTION:
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.add(R.id.fl_fragment_container_main, AddQuestionFragment.newInstance());
                    mFragmentTransaction.addToBackStack(String.valueOf(FRAGMENT_ADDQUESTION));
                    mFragmentTransaction.commit();
                    break;

                case FRAGMENT_TRIAL_EXAM_DETAILS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TrialExamDetailFragment.newInstance()).commit();

                    break;
            }
            currentMainFragment = fragment;

        } catch (Exception e) {
            Log.i(TAG, "loadFragment Exception : " + e.toString());
        }

    }


    //these is for the load fragment in right container.
    public void loadFragmentInRightContainer(int fragment) {
        try {
            switch (fragment) {

                case FRAGMENT_AUTHORPROFILE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, AuthorProfileFragment.newInstance()).commit();
                    break;

                case FRAGMENT_HIGHSCORE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, HighScoreFragment.newInstance()).commit();
                    break;
                case FRAGMENT_STUDENT_ATTEMPTED:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, StudentAttemptedFragment.newInstance()).commit();
                    break;
            }

        } catch (Exception e) {
            Log.i(TAG, "loadFragment Exception : " + e.toString());

        }

    }


    @Override
    public void onFragmentAttached(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    imgHome.setActivated(true);
                    currentMainFragment = fragment;
                    loadControllerTopMenu(null);
                    rlControllerTopMenu.setVisibility(View.VISIBLE);
                    txtAction.setVisibility(View.VISIBLE);
                    txtAction.setText(getString(R.string.straddpost));
                    txtAction.setTextColor(getResources().getColor(R.color.color_blue));
                    break;

                case FRAGMENT_OFFICE:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    loadControllerTopMenu(null);
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
                    txtTitle.setText(getString(R.string.strtrial));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_books));
                    break;


                case FRAGMENT_TRIAL:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));

                    //  txtAction.setTextColor(getResources().getColor(R.color.bg_office));
//                    rlControllerTopMenu.setVisibility(View.VISIBLE);
//                    txtAction.setVisibility(View.VISIBLE);
//                    txtAction.setText(getString(R.string.straddnew));
//                    txtAction.setTextColor(getResources().getColor(R.color.color_blue));
//                    txtTitle.setVisibility(View.VISIBLE);
//                    txtTitle.setText(getString(R.string.strtrial));
//                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
//                    imgBack.setVisibility(View.VISIBLE);
//                    spSubmenu.setVisibility(View.GONE);
//                    txtOne.setVisibility(View.GONE);
//                    txtTwo.setVisibility(View.GONE);
//                    txtThree.setVisibility(View.GONE);
                    loadControllerTopMenu(controllerTopMenuTrial);
                    break;


                case FRAGMENT_ADDNEWTRIAL:
                    imgHome.setActivated(false);
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
//                    currentMainFragment = fragment;
//                    currentMainFragmentBg = R.color.bg_office;
//
//                    rlControllerTopMenu.setVisibility(View.VISIBLE);
//                    txtAction.setVisibility(View.VISIBLE);
//                    txtAction.setText(getString(R.string.straddnew));
//                    txtAction.setTextColor(getResources().getColor(R.color.color_blue));
//                    txtTitle.setVisibility(View.VISIBLE);
//                    txtTitle.setText(getString(R.string.strtrial));
//                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
//                    imgBack.setVisibility(View.VISIBLE);
//                    spSubmenu.setVisibility(View.GONE);
//                    txtOne.setVisibility(View.GONE);
//                    txtTwo.setVisibility(View.GONE);
//                    txtThree.setVisibility(View.GONE);
                    break;


                case FRAGMENT_ADDQUESTION:
                    imgHome.setActivated(false);
                    flFragmentContainerRight.setVisibility(View.GONE);

                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;

                    rlControllerTopMenu.setVisibility(View.VISIBLE);
                    txtAction.setVisibility(View.GONE);
                    txtAction.setText(getString(R.string.straddnew));
                    txtAction.setTextColor(getResources().getColor(R.color.color_blue));
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText(getString(R.string.strtrial));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
                    imgBack.setVisibility(View.VISIBLE);
                    spSubmenu.setVisibility(View.GONE);
//                    txtOne.setVisibility(View.GONE);
//                    txtTwo.setVisibility(View.GONE);
//                    txtThree.setVisibility(View.GONE);
                    break;


                case FRAGMENT_AUTHORPROFILE:
                    currentRightFragment = fragment;
                    imgAuthorProfile.setActivated(true);
                    break;

                case FRAGMENT_HIGHSCORE:
                    currentRightFragment = fragment;
                    imgHighScore.setActivated(true);
                    break;
                case FRAGMENT_TRIAL_EXAM_DETAILS:
                    imgHome.setActivated(false);
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
                    //  txtAction.setTextColor(getResources().getColor(R.color.bg_office));
                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));

                    loadControllerTopMenu(controllerTopMenuTrialMenu);
                    break;
                case FRAGMENT_STUDENT_ATTEMPTED:
                    // currentRightFragment = fragment;
                    currentMainFragmentBg = R.color.bg_office;
                    imgOffice.setActivated(true);
//                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_office);
//                    txtAction.setTextColor(getResources().getColor(R.color.bg_office));
//                    txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
//                    loadControllerTopMenu(controllerTopMenuTrialMenu);
                    imgChat.setActivated(false);
                    imgSearch.setActivated(false);
                    imgAuthorProfile.setActivated(false);
                    break;
            }

        } catch (Exception e) {
            Log.i(TAG, "onFragmentAttached Exception : " + e.toString());
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
                    imgOffice.setActivated(true);
                    break;

                case FRAGMENT_ADDNEWTRIAL:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_ADDQUESTION:
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_AUTHORPROFILE:
                    imgAuthorProfile.setActivated(false);
                    break;
                case FRAGMENT_HIGHSCORE:
                    imgHighScore.setActivated(false);
                    break;
                case FRAGMENT_TRIAL_EXAM_DETAILS:
                    imgOffice.setActivated(true);
                    break;
                case FRAGMENT_STUDENT_ATTEMPTED:
                    imgOffice.setActivated(true);
                    break;


            }
        } catch (Exception e) {
            Log.i(TAG, "onFragmentDetached Exception : " + e.toString());
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
                txtTitle.setVisibility(View.GONE);
                hideControllerTopControls();
                startSlideAnimation(imgBack, -1000, 0, 0, 0);
                imgBack.setVisibility(View.VISIBLE);

                if (menu.get(0).getMenuItemTitle() != null) {
                    currentControllerTopMenu.get(0).setIsActive(true);
                    txtTitle.setText(currentControllerTopMenu.get(0).getMenuItemTitle());
                    startSlideAnimation(txtTitle, rlControllerTopMenu.getWidth(), 0, 0, 0);
                    txtTitle.setVisibility(View.VISIBLE);
                    spSubmenu.setVisibility(View.GONE);

                } else {
                    txtTitle.setText("");
                    startSlideAnimation(txtTitle, 0, rlControllerTopMenu.getWidth(), 0, 0);
                    txtTitle.setVisibility(View.GONE);
                }
                if (currentControllerTopMenu.get(0).getSubMenu() != null) {
                    //txtTitle.setVisibility(View.GONE);
                    spSubmenu.setVisibility(View.VISIBLE);
                    adapterControllerTopSpinner = new ControllerTopSpinnerAdapter(currentControllerTopMenu.get(0).getSubMenu(), AuthorHostActivity.this);
                    spSubmenu.setAdapter(adapterControllerTopSpinner);
                }
                if (currentControllerTopMenu.get(0).getMenuItemAction() != null) {
                    startSlideAnimation(txtAction, rlControllerTopMenu.getWidth(), 0, 0, 0);
                    txtAction.setText(currentControllerTopMenu.get(0).getMenuItemAction());
                    txtAction.setVisibility(View.VISIBLE);
                } else {
                    txtAction.setVisibility(View.GONE);
                }
            }


        } catch (Exception e) {
            Log.i(TAG, "loadMenu Exception : " + e.toString());
        }
    }


    private void onMenuItemClick(View view) {
        try {
            if (view == imgBack) {
                hideControllerTopControls();
                Debug.i(TAG, "current Fragmnet" + currentMainFragment);
                onBackClick(currentMainFragment);

            } else if (view == txtAction) {
                Log.i(TAG, "text action");
                handleTheActionButtonFragmentEvents();
            }
        } catch (Exception e) {
            Log.i(TAG, "onMenuItemClick Exception : " + e.toString());
        }
    }

    private void onBackClick(int currentMainFragment) {
        if (currentMainFragment == FRAGMENT_TRIAL) {
            loadFragmentInMainContainer(FRAGMENT_OFFICE);
        } else if (currentMainFragment == FRAGMENT_TRIAL_EXAM_DETAILS) {
            loadFragmentInMainContainer(FRAGMENT_TRIAL);
            loadFragmentInRightContainer(currentRightFragment);
        } else if (currentMainFragment == FRAGMENT_ADDNEWTRIAL) {
            loadFragmentInMainContainer(FRAGMENT_TRIAL);
        }
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
        startSlideAnimation(imgBack, 0, -1000, 0, 0);
        imgBack.setVisibility(View.GONE);
    }

    private void hideControllerTopAction() {
        startSlideAnimation(txtAction, 0, rlControllerTopMenu.getWidth(), 0, 0);
        txtAction.setText("");
        txtAction.setVisibility(View.GONE);
    }

    private void hideControllerTopSpinner() {
        spSubmenu.setAdapter(null);
        startSlideAnimation(spSubmenu, 0, -1000, 0, 0);
        spSubmenu.setVisibility(View.GONE);
    }

    private void startSlideAnimation(final View view, int fromX, int toX, int fromY, int toY) {
        TranslateAnimation slideOutAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideOutAnimation.setDuration(500);
        slideOutAnimation.setFillAfter(true);
        view.startAnimation(slideOutAnimation);
    }


    public void openRightContainerFragment(View view) {


        switch (view.getId()) {

            case R.id.img_author_profile:
                loadFragmentInRightContainer(FRAGMENT_AUTHORPROFILE);
                break;

            case R.id.img_high_score:
                loadFragmentInRightContainer(FRAGMENT_HIGHSCORE);
                break;

            case R.id.img_chat:

                loadFragmentInRightContainer(FRAGMENT_CHAT);
                break;
        }


    }


    public void openMainContainerFragment(View view) {
        switch (view.getId()) {
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

    public void searchFeeds(View view) {
        imgSearch.setActivated(!imgSearch.isActivated());
        if (etSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
            etSearch.setVisibility(View.GONE);
        } else {
            startSlideAnimation(etSearch, etSearch.getWidth(), 0, 0, 0);
            startSlideAnimation(imgSearch, etSearch.getWidth(), 0, 0, 0);
            etSearch.setVisibility(View.VISIBLE);
            Utility.showSoftKeyboard(etSearch, getActivity());
        }


    }


    public void logOut(View view) {
    }

    private Activity getActivity() {
        return AuthorHostActivity.this;

    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();

//        handleOnBackPressed();

//        Utils.showToast("The backstack count is" + getFragmentManager().getBackStackEntryCount(), this);
    }

    private void handleTheActionButtonFragmentEvents() {


        if (currentMainFragment == FRAGMENT_HOME) {

        } else if (currentMainFragment == FRAGMENT_TRIAL) {

            loadFragmentInMainContainer(FRAGMENT_ADDNEWTRIAL);

        } else if (currentMainFragment == FRAGMENT_ADDNEWTRIAL) {

        } else if (currentMainFragment == FRAGMENT_ADDQUESTION) {

        }


    }

    public void startProgress() {
        progress_bar.setProgress(1);
        progress_bar.setEnabled(false);
        progress_bar.setVisibility(View.VISIBLE);
        progressGenerator.start(progress_bar);
    }

    public void stopProgress() {
        progress_bar.setProgress(100);
        progress_bar.setVisibility(View.INVISIBLE);
    }

//    private void handleOnBackPressed() {
//
//
////        if (mFragmentManager.findFragmentByTag(String.valueOf(FRAGMENT_TRIAL)) instanceof TrialFragment) {
//
//        currentMainFragment = FRAGMENT_TRIAL;
//        currentMainFragmentBg = R.color.bg_office;
//        rlControllerTopMenu.setVisibility(View.VISIBLE);
//        txtAction.setVisibility(View.VISIBLE);
//        txtAction.setText(getString(R.string.straddnew));
//        txtAction.setTextColor(getResources().getColor(R.color.color_blue));
//
//        txtTitle.setVisibility(View.VISIBLE);
//        txtTitle.setText(getString(R.string.strtrial));
//        txtTitle.setTextColor(getResources().getColor(R.color.bg_office));
//        imgBack.setVisibility(View.VISIBLE);
//        spSubmenu.setVisibility(View.GONE);
//
//        txtOne.setVisibility(View.GONE);
//        txtTwo.setVisibility(View.GONE);
//        txtThree.setVisibility(View.GONE);
//
////        }
//
//    }
}
