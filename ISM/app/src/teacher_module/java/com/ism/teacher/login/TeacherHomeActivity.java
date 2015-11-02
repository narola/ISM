package com.ism.teacher.login;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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

import com.ism.R;
import com.ism.adapter.ControllerTopSpinnerAdapter;
import com.ism.fragment.AssessmentFragment;
import com.ism.fragment.ClassroomFragment;
import com.ism.fragment.DeskFragment;
import com.ism.fragment.HomeFragment;
import com.ism.fragment.ReportCardFragment;
import com.ism.fragment.TutorialFragment;
import com.ism.interfaces.FragmentListener;

import com.ism.teacher.Utility.ControllerTopMenuItem;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.fragments.AddQuestionFragmentTeacher;
import com.ism.teacher.fragments.TeacherChatFragment;
import com.ism.teacher.fragments.TeacherHomeFragment;
import com.ism.teacher.fragments.TeacherOfficeFragment;
import com.ism.teacher.fragments.TeacherQuizHomeFragment;
import com.ism.teacher.fragments.TeacherTutorialGroupFragment;
import com.ism.teacher.fragments.UpcomingEventsFragment;
import com.ism.teacher.fragments.UserProfileFragment;
import com.ism.utility.Utility;

import java.util.ArrayList;

/**
 * Created by c75 on 16/10/15.
 */
public class TeacherHomeActivity extends Activity implements FragmentListener {

    private static final String TAG = TeacherHomeActivity.class.getSimpleName();

    private LinearLayout llControllerLeft, llSearch;
    private FrameLayout flFragmentContainerMain, flFragmentContainerRight;
    private RelativeLayout rlControllerTopMenu, rlAddPost;
    private ImageView imgHome, imgTutorial, imgOffice, imgAssessment, imgDesk, imgReportCard, imgLogOut, imgSearch, imgNotes, img_teacher_profile, imgChat, imgMenuBack;
    private TextView txtTitle, txtOne, txtTwo, txtThree, txtFour, txtFive, txtSix, txtAction, txtAddPost;
    private EditText etSearch;
    private Spinner spSubmenu;

    private View.OnClickListener onClickMenuItem;
    private ControllerTopSpinnerAdapter adapterControllerTopSpinner;

    private TextView txtsMenu[];
    private ArrayList<ControllerTopMenuItem> controllerTopMenuClassroom, controllerTopMenuAssessment, controllerTopMenuDesk, controllerTopMenuReportCard, currentControllerTopMenu;

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_TUTORIAL = 1;
    public static final int FRAGMENT_CLASSROOM = 2;
    public static final int FRAGMENT_ASSESSMENT = 3;
    public static final int FRAGMENT_DESK = 4;
    public static final int FRAGMENT_REPORT_CARD = 5;

    public static final int FRAGMENT_UPCOMING_EVENTS = 6;
    public static final int FRAGMENT_TEACHER_CHAT = 7;
    public static final int FRAGMENT_USER_PROFILE = 8;
    public static final int FRAGMENT_TEACHER_HOME = 9;
    public static final int FRAGMENT_TEACHER_TUTORIAL_GROUP = 10;
    public static final int FRAGMENT_TEACHER_OFFICE = 11;

    public static final int FRAGMENT_ADDQUESTION = 12;

    public static int currentMainFragment;
    public static int currentRightFragment;
    private int currentMainFragmentBg;

    //ry
    private ArrayList<ControllerTopMenuItem> controllerTopMenuOffice, controllerTopMenuQuiz;
    private HostListener listenerHost;
    private AddTopicsListener addTopicsListener;


    public interface HostListener {
        public void onControllerMenuItemClicked(int position);
    }

    public interface AddTopicsListener {
        public void addTopic(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        initGlobal();

    }

    private void initGlobal() {
        txtAddPost = (TextView) findViewById(R.id.txt_add_post);
        rlAddPost = (RelativeLayout) findViewById(R.id.rl_add_post);

        llControllerLeft = (LinearLayout) findViewById(R.id.ll_controller_left);
        flFragmentContainerMain = (FrameLayout) findViewById(R.id.fl_fragment_container_main);
        flFragmentContainerRight = (FrameLayout) findViewById(R.id.fl_fragment_container_right);
        rlControllerTopMenu = (RelativeLayout) findViewById(R.id.rl_controller_top_menu);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        imgHome = (ImageView) findViewById(R.id.img_home);
        imgTutorial = (ImageView) findViewById(R.id.img_tutorial_teacher);
        imgOffice = (ImageView) findViewById(R.id.img_office);
        imgAssessment = (ImageView) findViewById(R.id.img_assessment);
        imgDesk = (ImageView) findViewById(R.id.img_desk);
        imgReportCard = (ImageView) findViewById(R.id.img_reportcard);
        imgLogOut = (ImageView) findViewById(R.id.img_logout);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        imgNotes = (ImageView) findViewById(R.id.img_notes);
        img_teacher_profile = (ImageView) findViewById(R.id.img_teacher_profile);
        imgChat = (ImageView) findViewById(R.id.img_chat);
        imgMenuBack = (ImageView) findViewById(R.id.img_back);
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


        controllerTopMenuClassroom = ControllerTopMenuItem.getMenuClassroom(TeacherHomeActivity.this);
        controllerTopMenuAssessment = ControllerTopMenuItem.getMenuAssessment(TeacherHomeActivity.this);
        controllerTopMenuDesk = ControllerTopMenuItem.getMenuDesk(TeacherHomeActivity.this);
        controllerTopMenuReportCard = ControllerTopMenuItem.getMenuReportCard(TeacherHomeActivity.this);

        //control for office side bar menu
        controllerTopMenuOffice = ControllerTopMenuItem.getMenuTeacherOffice(TeacherHomeActivity.this);
        controllerTopMenuQuiz = ControllerTopMenuItem.getMenuDesk(TeacherHomeActivity.this);


        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFragment(FRAGMENT_HOME);
                loadFragment(FRAGMENT_TEACHER_HOME);
            }
        });
        imgTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_TEACHER_TUTORIAL_GROUP);
            }
        });

        imgOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFragment(FRAGMENT_CLASSROOM);
                loadFragment(FRAGMENT_TEACHER_OFFICE);
            }
        });

        imgAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_ASSESSMENT);
            }
        });

        imgDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_DESK);
            }
        });

        imgReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_REPORT_CARD);
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
                    Utility.showSoftKeyboard(etSearch, TeacherHomeActivity.this);
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

        imgNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_UPCOMING_EVENTS);
            }
        });

        img_teacher_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_USER_PROFILE);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_TEACHER_CHAT);
            }
        });

        onClickMenuItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClick(v);
            }
        };

        imgMenuBack.setOnClickListener(onClickMenuItem);
        txtOne.setOnClickListener(onClickMenuItem);
        txtTwo.setOnClickListener(onClickMenuItem);
        txtThree.setOnClickListener(onClickMenuItem);
        txtFour.setOnClickListener(onClickMenuItem);
        txtFive.setOnClickListener(onClickMenuItem);
        txtSix.setOnClickListener(onClickMenuItem);
        txtAction.setOnClickListener(onClickMenuItem);


        loadFragment(FRAGMENT_UPCOMING_EVENTS);
        loadFragment(FRAGMENT_TEACHER_HOME);

    }


    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, HomeFragment.newInstance()).commit();
                    break;
                case FRAGMENT_TUTORIAL:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TutorialFragment.newInstance()).commit();
                    break;
                case FRAGMENT_CLASSROOM:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, ClassroomFragment.newInstance(FRAGMENT_CLASSROOM)).commit();
                    break;
                case FRAGMENT_ASSESSMENT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AssessmentFragment.newInstance()).commit();
                    break;
                case FRAGMENT_DESK:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, DeskFragment.newInstance()).commit();
                    break;
                case FRAGMENT_REPORT_CARD:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, ReportCardFragment.newInstance()).commit();
                    break;




                case FRAGMENT_UPCOMING_EVENTS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, UpcomingEventsFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TEACHER_CHAT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, TeacherChatFragment.newInstance()).commit();
                    break;
                case FRAGMENT_USER_PROFILE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, UserProfileFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TEACHER_HOME:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TeacherHomeFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TEACHER_TUTORIAL_GROUP:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TeacherTutorialGroupFragment.newInstance()).commit();
                    break;

                case FRAGMENT_TEACHER_OFFICE:
                    TeacherOfficeFragment teacherOfficeFragment = TeacherOfficeFragment.newInstance(FRAGMENT_TEACHER_OFFICE);
                    listenerHost = teacherOfficeFragment;
                    addTopicsListener = teacherOfficeFragment;
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, teacherOfficeFragment).commit();
                    break;

                case FRAGMENT_ADDQUESTION:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AddQuestionFragmentTeacher.newInstance(FRAGMENT_ADDQUESTION)).commit();
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragment Exception : " + e.toString());
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
                    break;
                case FRAGMENT_TUTORIAL:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_tutorial;
                    imgTutorial.setActivated(true);
                    loadControllerTopMenu(null);
                    txtTitle.setText(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.group_name) + "</font><font color='#1BBC9B'>Venice Beauty</font>"));
                    txtTitle.setVisibility(View.VISIBLE);
                    break;

                case FRAGMENT_ASSESSMENT:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_assessment;
                    imgAssessment.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_assessment);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_assessment));
                    loadControllerTopMenu(controllerTopMenuAssessment);
                    break;
                case FRAGMENT_DESK:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_desk;
                    imgDesk.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_desk);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_desk));
                    loadControllerTopMenu(controllerTopMenuDesk);
                    break;
                case FRAGMENT_REPORT_CARD:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_report_card;
                    imgReportCard.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_report_card);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_report_card));
                    loadControllerTopMenu(controllerTopMenuReportCard);
                    break;


                case FRAGMENT_UPCOMING_EVENTS:
                    currentRightFragment = fragment;
                    imgNotes.setActivated(true);
                    break;
                case FRAGMENT_USER_PROFILE:
                    currentRightFragment = fragment;
                    img_teacher_profile.setActivated(true);
                    break;
                case FRAGMENT_TEACHER_CHAT:
                    currentRightFragment = fragment;
                    imgChat.setActivated(true);
                    break;

                case FRAGMENT_TEACHER_HOME:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_report_card;
                    imgHome.setActivated(true);
                    loadControllerTopMenu(null);
                    txtTitle.setVisibility(View.GONE);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_report_card);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_report_card));
                    rlAddPost.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_TEACHER_TUTORIAL_GROUP:
                    currentMainFragment = fragment;
                    imgTutorial.setActivated(true);
                    loadControllerTopMenu(null);
                    txtTitle.setVisibility(View.GONE);
                    txtAddPost.setText("PAST");
                    rlAddPost.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_TEACHER_OFFICE:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_classroom;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_classroom);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_classroom));
                    loadControllerTopMenu(controllerTopMenuOffice);
                    break;


                case FRAGMENT_ADDQUESTION:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_classroom;
                    imgOffice.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_classroom);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_classroom));
                    loadControllerTopMenu(null);
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
                case FRAGMENT_TUTORIAL:
                    imgTutorial.setActivated(false);
                    break;
                case FRAGMENT_CLASSROOM:
                    imgOffice.setActivated(false);
                    break;
                case FRAGMENT_ASSESSMENT:
                    imgAssessment.setActivated(false);
                    break;
                case FRAGMENT_DESK:
                    imgDesk.setActivated(false);
                    break;
                case FRAGMENT_REPORT_CARD:
                    imgReportCard.setActivated(false);
                    break;


                case FRAGMENT_UPCOMING_EVENTS:
                    imgNotes.setActivated(false);
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

                case FRAGMENT_ADDQUESTION:
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
            if (view == imgMenuBack) {
                hideControllerTopControls();
                for (int i = 0; i < currentControllerTopMenu.size(); i++) {
                    txtsMenu[i].setTextColor(Color.WHITE);
                    currentControllerTopMenu.get(i).setIsActive(false);
                    startSlideAnimation(txtsMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
                    txtsMenu[i].setVisibility(View.VISIBLE);
                }

            } else if (view == txtAction) {

                switch (currentMainFragment) {

                    case FRAGMENT_TEACHER_OFFICE:

                        switch (TeacherOfficeFragment.getCurrentChildFragment()) {

                            case TeacherOfficeFragment.FRAGMENT_NOTES:
                                if (addTopicsListener != null) {
                                    addTopicsListener.addTopic(TeacherOfficeFragment.FRAGMENT_NOTES);
                                }
                                break;
                            case TeacherOfficeFragment.FRAGMENT_QUIZ:
                                if (addTopicsListener != null) {
                                    addTopicsListener.addTopic(TeacherOfficeFragment.FRAGMENT_QUIZ);
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

                            startSlideAnimation(imgMenuBack, -1000, 0, 0, 0);
                            imgMenuBack.setVisibility(View.VISIBLE);

                            if (currentControllerTopMenu.get(i).getSubMenu() == null) {
                                startSlideAnimation(txtsMenu[i], -imgMenuBack.getWidth(), 0, 0, 0);
                                txtsMenu[i].setVisibility(View.VISIBLE);
                            } else {
                                txtsMenu[i].setVisibility(View.GONE);
                                startSlideAnimation(spSubmenu, -imgMenuBack.getWidth(), 0, 0, 0);
                                spSubmenu.setVisibility(View.VISIBLE);
                                adapterControllerTopSpinner = new ControllerTopSpinnerAdapter(currentControllerTopMenu.get(i).getSubMenu(), TeacherHomeActivity.this);
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
                             * Menu item click event
                             */
                            if (listenerHost != null) {
                                listenerHost.onControllerMenuItemClicked(i);
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
        if (imgMenuBack.getVisibility() == View.VISIBLE) {
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
        startSlideAnimation(imgMenuBack, 0, -1000, 0, 0);
        imgMenuBack.setVisibility(View.GONE);
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


}
