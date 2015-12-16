package com.ism.teacher.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.teacher.R;
import com.ism.teacher.Utility.ControllerTopMenuItem;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.adapters.AssignmentSubmitterAdapter;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.adapters.ControllerTopSpinnerAdapter;
import com.ism.teacher.adapters.MyStudentsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.fragments.AssignmentExamFragment;
import com.ism.teacher.fragments.GetObjectiveAssignmentQuestionsFragment;
import com.ism.teacher.fragments.StudentAttemptedFragment;
import com.ism.teacher.fragments.TeacherChatFragment;
import com.ism.teacher.fragments.TeacherHomeFragment;
import com.ism.teacher.fragments.TeacherOfficeFragment;
import com.ism.teacher.fragments.TeacherTutorialGroupFragment;
import com.ism.teacher.fragments.UpcomingEventsFragment;
import com.ism.teacher.fragments.UserProfileFragment;
import com.ism.teacher.interfaces.FragmentListener;

import java.util.ArrayList;

/**
 * Created by c75 on 16/10/15.
 */

public class TeacherHostActivity extends Activity implements FragmentListener {

    private static final String TAG = TeacherHostActivity.class.getSimpleName();

    private LinearLayout llControllerLeft, llSearch;
    public FrameLayout flFragmentContainerMain, flFragmentContainerRight;
    private RelativeLayout rlControllerTopMenu, rlAddPost;
    private ImageView imgHome, imgTutorial, imgOffice, imgAssessment, imgDesk, imgReportCard, imgLogOut, imgSearch, imgUpcomings, img_teacher_profile, imgChat, imgBack;
    private TextView txtTitle, txtOne, txtTwo, txtThree, txtFour, txtFive, txtSix, txtAction, txtAddPost;
    private EditText etSearch;
    private Spinner spSubmenu;

    private View.OnClickListener onClickMenuItem;
    private ControllerTopSpinnerAdapter adapterControllerTopSpinner;

    private TextView txtsMenu[];
    public ArrayList<ControllerTopMenuItem> controllerTopMenuClassroom, controllerTopMenuAssessment, controllerTopMenuDesk, controllerTopMenuReportCard, currentControllerTopMenu;


    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_UPCOMING_EVENTS = 6;
    public static final int FRAGMENT_TEACHER_CHAT = 7;
    public static final int FRAGMENT_USER_PROFILE = 8;
    public static final int FRAGMENT_TEACHER_HOME = 9;
    public static final int FRAGMENT_TEACHER_TUTORIAL_GROUP = 10;
    public static final int FRAGMENT_TEACHER_OFFICE = 11;
    public static final int FRAGMENT_STUDENT_ATTEMPTED = 12;

    public static int currentMainFragment;
    public static int currentRightFragment;
    private int currentMainFragmentBg;

    private ArrayList<ControllerTopMenuItem> controllerTopMenuOffice, controllerTopMenuQuiz;
    private HostListener listenerHost;
    private AddTopicsListener addTopicsListener;

    private ActionProcessButton progress_bar;
    private ProgressGenerator progressGenerator;

    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    Bundle fragmentBundle = new Bundle();

    public ArrayList<ControllerTopMenuItem> updatedTopMenuItem = new ArrayList<>();

    public interface HostListener {
        public void onControllerMenuItemClicked(int position);
    }

    public interface AddTopicsListener {
        public void addTopic(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_host);

        initGlobal();

    }

    private void initGlobal() {

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        progress_bar = (ActionProcessButton) findViewById(R.id.progress_bar);
        progressGenerator = new ProgressGenerator();

        txtAddPost = (TextView) findViewById(R.id.txt_add_post);
        rlAddPost = (RelativeLayout) findViewById(R.id.rl_add_post);

        llControllerLeft = (LinearLayout) findViewById(R.id.ll_controller_left);
        flFragmentContainerMain = (FrameLayout) findViewById(R.id.fl_fragment_container_main);
        flFragmentContainerRight = (FrameLayout) findViewById(R.id.fl_fragment_container_right);
        rlControllerTopMenu = (RelativeLayout) findViewById(R.id.rl_controller_top_menu);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgHome = (ImageView) findViewById(R.id.img_home);
        imgTutorial = (ImageView) findViewById(R.id.img_tutorial_teacher);
        imgOffice = (ImageView) findViewById(R.id.img_office);
        imgAssessment = (ImageView) findViewById(R.id.img_assessment);
        imgDesk = (ImageView) findViewById(R.id.img_desk);
        imgReportCard = (ImageView) findViewById(R.id.img_reportcard);
        imgLogOut = (ImageView) findViewById(R.id.img_logout);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        imgUpcomings = (ImageView) findViewById(R.id.img_upcomings);
        img_teacher_profile = (ImageView) findViewById(R.id.img_teacher_profile);
        imgChat = (ImageView) findViewById(R.id.img_chat);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtOne = (TextView) findViewById(R.id.txt_one);
        txtTwo = (TextView) findViewById(R.id.txt_two);
        txtThree = (TextView) findViewById(R.id.txt_three);
        txtFour = (TextView) findViewById(R.id.txt_four);
        txtFive = (TextView) findViewById(R.id.txt_five);
        txtSix = (TextView) findViewById(R.id.txt_six);
        txtAction = (TextView) findViewById(R.id.txt_action);
        etSearch = (EditText) findViewById(R.id.et_search);
        spSubmenu = (Spinner) findViewById(R.id.sp_submenu);

        txtsMenu = new TextView[]{txtOne, txtTwo, txtThree, txtFour, txtFive, txtSix};


        controllerTopMenuClassroom = ControllerTopMenuItem.getMenuClassroom(TeacherHostActivity.this);
        controllerTopMenuAssessment = ControllerTopMenuItem.getMenuAssessment(TeacherHostActivity.this);
        controllerTopMenuDesk = ControllerTopMenuItem.getMenuDesk(TeacherHostActivity.this);
        controllerTopMenuReportCard = ControllerTopMenuItem.getMenuReportCard(TeacherHostActivity.this);

        //control for office side bar menu
        controllerTopMenuOffice = ControllerTopMenuItem.getMenuTeacherOffice(TeacherHostActivity.this);
        controllerTopMenuQuiz = ControllerTopMenuItem.getMenuDesk(TeacherHostActivity.this);


        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentInMainContainer(FRAGMENT_TEACHER_HOME, null);
            }
        });
        imgTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentInMainContainer(FRAGMENT_TEACHER_TUTORIAL_GROUP, null);
            }
        });

        imgOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentInMainContainer(FRAGMENT_TEACHER_OFFICE, null);
            }
        });


        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSearch.setActivated(!imgSearch.isActivated());
                if (etSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(etSearch, 0, etSearch.getWidth(), 0, 0);
//		            startSlideAnimation(imgSearch, -imgSearch.getWidth(), 0, 0, 0);
                    etSearch.setVisibility(View.GONE);
                } else {
                    startSlideAnimation(etSearch, etSearch.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgSearch, etSearch.getWidth(), 0, 0, 0);
                    etSearch.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSearch, TeacherHostActivity.this);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.e(TAG, "search clicked");
                }
                return false;
            }
        });

        imgUpcomings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentInRightContainer(FRAGMENT_UPCOMING_EVENTS);
            }
        });

        img_teacher_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentInRightContainer(FRAGMENT_USER_PROFILE);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentInRightContainer(FRAGMENT_TEACHER_CHAT);
            }
        });

        onClickMenuItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClick(v);
            }
        };

        imgBack.setOnClickListener(onClickMenuItem);
        txtOne.setOnClickListener(onClickMenuItem);
        txtTwo.setOnClickListener(onClickMenuItem);
        txtThree.setOnClickListener(onClickMenuItem);
        txtFour.setOnClickListener(onClickMenuItem);
        txtFive.setOnClickListener(onClickMenuItem);
        txtSix.setOnClickListener(onClickMenuItem);
        txtAction.setOnClickListener(onClickMenuItem);


        loadFragmentInMainContainer(FRAGMENT_TEACHER_HOME, null);
        loadFragmentInRightContainer(FRAGMENT_UPCOMING_EVENTS);

    }

    public void loadFragmentInMainContainer(int mainfragment, Bundle fragmentArgument) {
        try {
            removeBundleArguments();
            switch (mainfragment) {
                case FRAGMENT_TEACHER_HOME:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TeacherHomeFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TEACHER_TUTORIAL_GROUP:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TeacherTutorialGroupFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TEACHER_OFFICE:
                    TeacherOfficeFragment teacherOfficeFragment = TeacherOfficeFragment.newInstance(FRAGMENT_TEACHER_OFFICE, fragmentArgument);
                    listenerHost = teacherOfficeFragment;
                    addTopicsListener = teacherOfficeFragment;
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, teacherOfficeFragment, AppConstant.FRAGMENT_TAG_TEACHER_OFFICE).commit();
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragmentInMainContainer Exception : " + e.toString());
        }
    }

    //these is for the load fragment in right container.
    public void loadFragmentInRightContainer(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_UPCOMING_EVENTS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, UpcomingEventsFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TEACHER_CHAT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, TeacherChatFragment.newInstance()).commit();
                    break;
                case FRAGMENT_USER_PROFILE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, UserProfileFragment.newInstance()).commit();
                    break;
                case FRAGMENT_STUDENT_ATTEMPTED:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, StudentAttemptedFragment.newInstance(null)).commit();
                    break;
            }

        } catch (Exception e) {
            Log.i(TAG, "loadFragmentInMainContainer Exception : " + e.toString());

        }

    }


    @Override
    public void onFragmentAttached(int fragment) {

        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    currentMainFragment = fragment;
                    imgHome.setActivated(true);
                    loadControllerTopMenu(null);
                    txtTitle.setVisibility(View.GONE);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    break;

                case FRAGMENT_UPCOMING_EVENTS:
                    currentRightFragment = fragment;
                    imgUpcomings.setActivated(true);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_USER_PROFILE:
                    currentRightFragment = fragment;
                    img_teacher_profile.setActivated(true);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_TEACHER_CHAT:
                    currentRightFragment = fragment;
                    imgChat.setActivated(true);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    break;

                case FRAGMENT_TEACHER_HOME:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_report_card;
                    imgHome.setActivated(true);
                    loadControllerTopMenu(null);
                    txtTitle.setVisibility(View.GONE);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_report_card);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_report_card));
                    rlAddPost.setVisibility(View.GONE);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_TEACHER_TUTORIAL_GROUP:
                    currentMainFragment = fragment;
                    imgTutorial.setActivated(true);
                    loadControllerTopMenu(null);
                    txtTitle.setVisibility(View.GONE);
                    txtAddPost.setText("PAST");
                    rlAddPost.setVisibility(View.VISIBLE);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_TEACHER_OFFICE:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_classroom;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_classroom);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_classroom));
                    loadControllerTopMenu(controllerTopMenuOffice);
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onFragmentAttached Exception : " + e.toString());
        }
    }

    @Override
    public void onFragmentDetached(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    imgHome.setActivated(false);
                    break;

                case FRAGMENT_UPCOMING_EVENTS:
                    imgUpcomings.setActivated(false);
                    break;
                case FRAGMENT_USER_PROFILE:
                    img_teacher_profile.setActivated(false);
                    break;
                case FRAGMENT_TEACHER_CHAT:
                    imgChat.setActivated(false);
                    break;

                case FRAGMENT_TEACHER_HOME:
                    imgHome.setActivated(false);
                    rlAddPost.setVisibility(View.GONE);
                    break;

                case FRAGMENT_TEACHER_TUTORIAL_GROUP:
                    imgTutorial.setActivated(false);
                    rlAddPost.setVisibility(View.GONE);
                    break;
                case FRAGMENT_TEACHER_OFFICE:
                    imgOffice.setActivated(false);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onFragmentDetached Exception : " + e.toString());
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
                for (int i = 0; i < txtsMenu.length; i++) {
                    if (i < menu.size()) {
                        currentControllerTopMenu.get(i).setIsActive(false);
                        txtsMenu[i].setTextColor(Color.WHITE);
                        txtsMenu[i].setText(menu.get(i).getMenuItemTitle());
                        startSlideAnimation(txtsMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
                        txtsMenu[i].setVisibility(View.VISIBLE);
                    } else {
                        txtsMenu[i].setText("");
                        startSlideAnimation(txtsMenu[i], 0, rlControllerTopMenu.getWidth(), 0, 0);
                        txtsMenu[i].setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "loadMenu Exception : " + e.toString());
        }
    }

    private void onMenuItemClick(View view) {
        try {
            if (view == imgBack) {
                hideControllerTopControls();
//                for (int i = 0; i < currentControllerTopMenu.size(); i++) {
//                    txtsMenu[i].setTextColor(Color.WHITE);
//                    currentControllerTopMenu.get(i).setIsActive(false);
//                    startSlideAnimation(txtsMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
//                    txtsMenu[i].setVisibility(View.VISIBLE);
//                }

                onBackClick(currentMainFragment);

            }

            /**
             * For loading the add (exam,and other add features from different frags)
             */
            else if (view == txtAction) {

                switch (currentMainFragment) {

                    case FRAGMENT_TEACHER_OFFICE:

                        switch (TeacherOfficeFragment.getCurrentChildFragment()) {

                            case TeacherOfficeFragment.FRAGMENT_NOTES:
                                if (addTopicsListener != null) {
                                    addTopicsListener.addTopic(TeacherOfficeFragment.FRAGMENT_NOTES);
                                    txtAction.setVisibility(View.GONE);
                                }
                                break;
                            case TeacherOfficeFragment.FRAGMENT_QUIZ:
                                if (addTopicsListener != null) {
                                    addTopicsListener.addTopic(TeacherOfficeFragment.FRAGMENT_QUIZ);
                                    txtAction.setVisibility(View.GONE);
                                }
                                break;
                        }
                        break;
                }


            } else {
                boolean isActive = false;
                for (int i = 0; i < currentControllerTopMenu.size(); i++) {
                    if (view == txtsMenu[i] && currentControllerTopMenu.get(i).isActive()) {
                        isActive = true;
                        break;
                    }
                }
                if (!isActive) {
                    for (int i = 0; i < currentControllerTopMenu.size(); i++) {

                        if (view == txtsMenu[i]) {
                            currentControllerTopMenu.get(i).setIsActive(true);
                            txtsMenu[i].setTextColor(getResources().getColor(currentMainFragmentBg));

                            startSlideAnimation(imgBack, -1000, 0, 0, 0);
                            imgBack.setVisibility(View.VISIBLE);

                            if (currentControllerTopMenu.get(i).getSubMenu() == null) {
                                startSlideAnimation(txtsMenu[i], -imgBack.getWidth(), 0, 0, 0);
                                txtsMenu[i].setVisibility(View.VISIBLE);
                            } else {
                                txtsMenu[i].setVisibility(View.GONE);
                                startSlideAnimation(spSubmenu, -imgBack.getWidth(), 0, 0, 0);
                                spSubmenu.setVisibility(View.VISIBLE);
                                adapterControllerTopSpinner = new ControllerTopSpinnerAdapter(currentControllerTopMenu.get(i).getSubMenu(), TeacherHostActivity.this);
                                spSubmenu.setAdapter(adapterControllerTopSpinner);
                            }

                            if (currentControllerTopMenu.get(i).getMenuItemAction() != null) {
                                startSlideAnimation(txtAction, rlControllerTopMenu.getWidth(), 0, 0, 0);
                                txtAction.setText(currentControllerTopMenu.get(i).getMenuItemAction());
                                txtAction.setVisibility(View.VISIBLE);
                            } else {
                                txtAction.setVisibility(View.GONE);
                            }

                            /**
                             * Menu item click event for top bar text(notes,quiz,classwall....)
                             */
                            if (listenerHost != null) {
                                listenerHost.onControllerMenuItemClicked(i + 1);
                            }


                        } else {
                            currentControllerTopMenu.get(i).setIsActive(false);
                            startSlideAnimation(txtsMenu[i], 0, rlControllerTopMenu.getWidth(), 0, 0);
                            txtsMenu[i].setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onMenuItemClick Exception : " + e.toString());
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


    public void showRightContainerFragment() {
        flFragmentContainerRight.setVisibility(View.VISIBLE);
    }

    public void hideRightContainerFragment() {
        flFragmentContainerRight.setVisibility(View.GONE);
    }

    public void hideTxtAction() {
        txtAction.setVisibility(View.GONE);
    }

    public void showTxtAction() {
        txtAction.setVisibility(View.VISIBLE);
        txtAction.setTextColor(getResources().getColor(R.color.bg_classroom));
    }


    private void onBackClick(int currentMainFragment) {

        if (currentMainFragment == FRAGMENT_TEACHER_OFFICE) {

            //On loading teacher office in main frag,automatically it will call
            //Teacher class wall(as per mockup_),first frag called inside TeacherOffice is classwall in initglobal

            int current_office_fragment;
            current_office_fragment = TeacherOfficeFragment.getCurrentChildFragment();
            TeacherOfficeFragment teacherOfficeFragment = (TeacherOfficeFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_TAG_TEACHER_OFFICE);

            /**
             *  sending old fragment in onBack param(which we need to replace with new one)
             */

            switch (current_office_fragment) {
                case TeacherOfficeFragment.FRAGMENT_QUIZ:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_QUIZ);
                    break;

                case TeacherOfficeFragment.FRAGMENT_ASSIGNMENT_SUBMITTER:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_ASSIGNMENT_SUBMITTER);
                    break;

//                case TeacherOfficeFragment.FRAGMENT_CLASSWALL:
//                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_ASSIGNMENT_SUBMITTER);
//                    break;


                case TeacherOfficeFragment.FRAGMENT_NOTES:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_NOTES);
                    break;

                case TeacherOfficeFragment.FRAGMENT_MARK_SCRIPT:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_MARK_SCRIPT);
                    break;

                case TeacherOfficeFragment.FRAGMENT_RESULTS:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_RESULTS);
                    break;

                case TeacherOfficeFragment.FRAGMENT_PROGRESS_REPORT:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_PROGRESS_REPORT);
                    break;

                case TeacherOfficeFragment.FRAGMENT_GET_OBJECTIVE_QUESTIONS_VIEW:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_GET_OBJECTIVE_QUESTIONS_VIEW);
                    break;

                case TeacherOfficeFragment.FRAGMENT_CREATE_EXAM_CONTAINER:

                    // CreateExamAssignmentContainerFragment createExamContainer = (CreateExamAssignmentContainerFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_TAG_CREATE_EXAM_CONTAINER);
                    //  createExamContainer.onBack(TeacherOfficeFragment.FRAGMENT_CREATE_EXAM_CONTAINER);

                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_GET_OBJECTIVE_QUESTIONS_VIEW);
                    break;

                case TeacherOfficeFragment.FRAGMENT_CREATE_EXAM_CONTAINER_EDIT:
                    teacherOfficeFragment.onBack(TeacherOfficeFragment.FRAGMENT_CREATE_EXAM_CONTAINER_EDIT);

                    break;

            }

        }

    }

    Bundle bundle = new Bundle();

    public Bundle getBundle() {
        return bundle;
    }


    @Override
    public void onBackPressed() {

    }

    public void showControllerTopBackButton() {
        Utility.startSlideAnimation(imgBack, -100, 0, 0, 0);
        imgBack.setVisibility(View.VISIBLE);
    }


    public void showSpinnerWithSubMenu(int index) {

        switch (index) {
            case AppConstant.INDEX_ALL_ASSIGNMENTS:
                hideAllMainMenus();
                showControllerTopBackButton();
                startSlideAnimation(spSubmenu, -imgBack.getWidth(), 0, 0, 0);
                spSubmenu.setVisibility(View.VISIBLE);
                adapterControllerTopSpinner = new ControllerTopSpinnerAdapter(currentControllerTopMenu.get(index).getSubMenu(), TeacherHostActivity.this);
                spSubmenu.setAdapter(adapterControllerTopSpinner);
                break;

        }
    }


    public void showAllMainMenus() {
        for (int i = 0; i < currentControllerTopMenu.size(); i++) {
            txtsMenu[i].setTextColor(Color.WHITE);
            currentControllerTopMenu.get(i).setIsActive(false);
            startSlideAnimation(txtsMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
            txtsMenu[i].setVisibility(View.VISIBLE);
        }
    }

    public void hideAllMainMenus() {
        for (int i = 0; i < currentControllerTopMenu.size(); i++) {
            txtsMenu[i].setVisibility(View.GONE);
        }
    }

    private void removeBundleArguments() {

        getBundle().remove(AssignmentExamFragment.ARG_IS_CREATE_EXAM);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_ID);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_NAME);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_CLASSROOM_ID);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_TOPIC_ID);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_BOOK_ID);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_CATEGORY);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_TYPE);

        getBundle().remove(AssignmentsAdapter.ARG_EXAM_MODE);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_DURATION);
        getBundle().remove(AssignmentsAdapter.ARG_ASSIGNMENT_NO);


        getBundle().remove(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_CREATED_DATE);


        getBundle().remove(AssignmentsAdapter.ARG_EXAM_ATTEMPT_COUNT);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_INSTRUCTIONS);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_IS_RANDOM_QUESTION);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_IS_DECLARE_RESULTS);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_ASSESSOR);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_START_DATE);
        getBundle().remove(AssignmentsAdapter.ARG_EXAM_START_TIME);


        getBundle().remove(AssignmentsAdapter.ARG_FRAGMENT_TYPE);
        getBundle().remove(AssignmentsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION);

        getBundle().remove(AssignmentSubmitterAdapter.ARG_STUDENT_ID);
        getBundle().remove(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION);
        getBundle().remove(AssignmentSubmitterAdapter.ARG_STUDENT_PROFILE_PIC);
        getBundle().remove(AssignmentSubmitterAdapter.ARG_STUDENT_NAME);

        getBundle().remove(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
        getBundle().remove(GetObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY);
        getBundle().remove(MyStudentsAdapter.ARG_ARR_LIST_STUDENTS);

    }
}
