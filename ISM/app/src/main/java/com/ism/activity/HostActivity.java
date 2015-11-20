package com.ism.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.fragment.AllMessageFragment;
import com.ism.fragment.AllNoticeFragment;
import com.ism.fragment.AllNotificationFragment;
import com.ism.fragment.AllStudymateRequestFragment;
import com.ism.fragment.AssessmentFragment;
import com.ism.fragment.ChatFragment;
import com.ism.fragment.ClassroomFragment;
import com.ism.fragment.DeskFragment;
import com.ism.fragment.MyActivityFragment;
import com.ism.fragment.MyFeedsFragment;
import com.ism.fragment.MyWalletFragment;
import com.ism.fragment.AccordionFragment;
import com.ism.fragment.ProfileControllerFragment;
import com.ism.fragment.ReportCardFragment;
import com.ism.fragment.StudymatesFragment;
import com.ism.fragment.TutorialFragment;
import com.ism.fragment.userprofile.EditProfileFragment;
import com.ism.fragment.userprofile.GeneralSettingsFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.model.ControllerTopMenuItem;
import com.ism.model.FragmentArgument;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.model.DataUserPreferences;
import com.ism.ws.model.Notification;
import com.ism.ws.model.PrivacySetting;
import com.ism.ws.model.RequestObject;
import com.ism.ws.model.ResponseGetAllPreferences;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.WebserviceWrapper;
import com.ism.ws.model.ResponseUserPreferences;
import com.ism.ws.model.SMSAlert;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class HostActivity extends Activity implements FragmentListener, WebserviceWrapper.WebserviceResponse {

    private static final String TAG = HostActivity.class.getName();

    private LinearLayout llControllerLeft;
    private FrameLayout flFragmentContainerMain, flFragmentContainerRight;
    private RelativeLayout rlControllerTopMenu;
    private LinearLayout llSearch;
    private ImageView imgHome, imgTutorial, imgClassroom, imgAssessment, imgDesk, imgReportCard, imgLogOut,
            imgSearch, imgNotes, imgStudyMates, imgChat, imgMenuBack;
    private TextView txtTitle, txtOne, txtTwo, txtThree, txtFour, txtFive, txtAction;
    private EditText etSearch;
    private Spinner spSubmenu;
    private ActionProcessButton progHost;

    private View.OnClickListener onClickMenuItem;
    private ControllerTopSpinnerAdapter adapterControllerTopSpinner;
    private ProgressGenerator progressGenerator;

    private HostListener listenerHost;
    private HostListenerAllNotification listenerHostAllNotification;
    private HostListenerAllMessage listenerHostAllMessage;
    private HostListenerProfileController listnerHostProfileController;
    private HostListenerAboutMe hostListenerAboutMe;

    private TextView arrTxtMenu[];
    private ArrayList<ControllerTopMenuItem> controllerTopMenuClassroom;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuAssessment;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuDesk;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuReportCard;
    private ArrayList<ControllerTopMenuItem> currentControllerTopMenu;

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_TUTORIAL = 1;
    public static final int FRAGMENT_CLASSROOM = 2;
    public static final int FRAGMENT_ASSESSMENT = 3;
    public static final int FRAGMENT_DESK = 4;
    public static final int FRAGMENT_REPORT_CARD = 5;
    public static final int FRAGMENT_NOTES = 6;
    public static final int FRAGMENT_PROFILE_CONTROLLER = 7;
    public static final int FRAGMENT_CHAT = 8;
    public static final int FRAGMENT_ALL_NOTES = 9;
    public static final int FRAGMENT_GENERAL_SETTINGS = 10;
    public static final int FRAGMENT_MY_FEEDS = 11;
    public static final int FRAGMENT_STUDYMATES = 12;
    public static final int FRAGMENT_MY_ACTIVITY = 13;
    public static final int FRAGMENT_MY_WALLET = 14;
    public static final int FRAGMENT_ALL_NOTIFICATION = 15;
    public static final int FRAGMENT_ALL_MESSAGE = 16;
    public static final int FRAGMENT_ALL_STUDYMATE_REQUEST = 17;
    public static final int FRAGMENT_EDIT_PROFILE = 18;
    private int currentMainFragment;
    private int currentRightFragment;
    private int currentMainFragmentBg;
    private ArrayList<Notification> arrayListNotification = new ArrayList<>();
    private ArrayList<SMSAlert> arrayListSMSAlert = new ArrayList<>();
    private ArrayList<PrivacySetting> arrayListPrivacySetting = new ArrayList<>();

    public interface HostListenerAboutMe {
        public void onSelectImage(Bitmap bitmap);
    }

    public interface HostListener {
        public void onControllerMenuItemClicked(int position);
    }

    public interface HostListenerAllNotification {
        public void onControllerTopBackClick();
    }

    public interface HostListenerAllMessage {
        public void onControllerTopBackClick();
    }

    public interface HostListenerProfileController {
        public void onBadgesFetched();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        initGlobal();

    }

    private void initGlobal() {
        llControllerLeft = (LinearLayout) findViewById(R.id.ll_controller_left);
        flFragmentContainerMain = (FrameLayout) findViewById(R.id.fl_fragment_container_main);
        flFragmentContainerRight = (FrameLayout) findViewById(R.id.fl_fragment_container_right);
        rlControllerTopMenu = (RelativeLayout) findViewById(R.id.rl_controller_top_menu);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        imgHome = (ImageView) findViewById(R.id.img_home);
        imgTutorial = (ImageView) findViewById(R.id.img_tutorial);
        imgClassroom = (ImageView) findViewById(R.id.img_office);
        imgAssessment = (ImageView) findViewById(R.id.img_assessment);
        imgDesk = (ImageView) findViewById(R.id.img_desk);
        imgReportCard = (ImageView) findViewById(R.id.img_reportcard);
        imgLogOut = (ImageView) findViewById(R.id.img_logout);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        imgNotes = (ImageView) findViewById(R.id.img_notes);
        imgStudyMates = (ImageView) findViewById(R.id.img_author_profile);
        imgChat = (ImageView) findViewById(R.id.img_chat);
        imgMenuBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtOne = (TextView) findViewById(R.id.txt_one);
        txtTwo = (TextView) findViewById(R.id.txt_two);
        txtThree = (TextView) findViewById(R.id.txt_three);
        txtFour = (TextView) findViewById(R.id.txt_four);
        txtFive = (TextView) findViewById(R.id.txt_five);
        txtAction = (TextView) findViewById(R.id.txt_action);
        etSearch = (EditText) findViewById(R.id.et_search);
        spSubmenu = (Spinner) findViewById(R.id.sp_submenu);
        progHost = (ActionProcessButton) findViewById(R.id.prog_host);

        arrTxtMenu = new TextView[]{txtOne, txtTwo, txtThree, txtFour, txtFive};
        progressGenerator = new ProgressGenerator();
        Global.strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, HostActivity.this);
        Global.strFullName = PreferenceData.getStringPrefs(PreferenceData.USER_FULL_NAME, HostActivity.this);
//	    Global.strProfilePic = PreferenceData.getStringPrefs(PreferenceData.USER_PROFILE_PIC, HostActivity.this);
        Global.strProfilePic = "http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png";

        callApiGetAllBadgesCount();

        loadFragment(FRAGMENT_HOME, null);
        loadFragment(FRAGMENT_CHAT, null);

        controllerTopMenuClassroom = ControllerTopMenuItem.getMenuClassroom(HostActivity.this);
        controllerTopMenuAssessment = ControllerTopMenuItem.getMenuAssessment(HostActivity.this);
        controllerTopMenuDesk = ControllerTopMenuItem.getMenuDesk(HostActivity.this);
        controllerTopMenuReportCard = ControllerTopMenuItem.getMenuReportCard(HostActivity.this);

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_HOME, null);
            }
        });
        imgTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_TUTORIAL, null);
            }
        });

        imgClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_CLASSROOM, null);
            }
        });

        imgAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_ASSESSMENT, null);
            }
        });

        imgDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_DESK, null);
            }
        });

        imgReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_REPORT_CARD, null);
            }
        });

        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceData.clearWholePreference(HostActivity.this);
                Intent intentLogin = new Intent(HostActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
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
                    Utility.showSoftKeyboard(etSearch, HostActivity.this);
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
                loadFragment(FRAGMENT_NOTES, null);
            }
        });

        imgStudyMates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_PROFILE_CONTROLLER, null);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_CHAT, null);
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
        txtAction.setOnClickListener(onClickMenuItem);

        callApiGetGeneralSettingPreferences();
        callApiForGetUserPreference();

    }

    private void callApiGetGeneralSettingPreferences() {
        try {
            showProgress();
            new WebserviceWrapper(getApplicationContext(), null, HostActivity.this).new WebserviceCaller().execute(WebConstants.GENERAL_SETTING_PREFERENCES);

        } catch (Exception e) {

            Debug.i(TAG, "General setting Pereference :" + e.getLocalizedMessage());
        }
    }


    private void callApiGetAllBadgesCount() {
        try {
            showProgress();
            RequestObject requestObject = new RequestObject();
            requestObject.setUserId(Global.strUserId);

            new WebserviceWrapper(HostActivity.this, requestObject, this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_BADGES_COUNT);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetAllBadgesCount Exception : " + e.toString());
        }
    }

    public void loadFragment(int fragment, FragmentArgument fragmentArgument) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
//                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, HomeFragment.newInstance()).commit();
                    ClassroomFragment homeFragment = ClassroomFragment.newInstance(FRAGMENT_HOME);
                    listenerHost = homeFragment;
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, homeFragment).commit();
                    break;
                case FRAGMENT_TUTORIAL:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, TutorialFragment.newInstance()).commit();
                    break;
                case FRAGMENT_CLASSROOM:
                    ClassroomFragment classroomFragment = ClassroomFragment.newInstance(FRAGMENT_CLASSROOM);
                    listenerHost = classroomFragment;
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, classroomFragment).commit();
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
                case FRAGMENT_NOTES:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, AccordionFragment.newInstance()).commit();
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, ProfileControllerFragment.newInstance()).commit();
                    break;
                case FRAGMENT_CHAT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, ChatFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_NOTES:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AllNoticeFragment.newInstance(fragmentArgument.getArrayListData())).commit();
                    break;
                case FRAGMENT_GENERAL_SETTINGS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, GeneralSettingsFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MY_FEEDS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, MyFeedsFragment.newInstance()).commit();
                    break;
                case FRAGMENT_STUDYMATES:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, StudymatesFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MY_ACTIVITY:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, MyActivityFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MY_WALLET:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, MyWalletFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_NOTIFICATION:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllNotificationFragment.newInstance(fragmentArgument.getArrayListData(), fragmentArgument.getPosition())).commit();
                    break;
                case FRAGMENT_ALL_MESSAGE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllMessageFragment.newInstance(fragmentArgument.getArrayListData(), fragmentArgument.getPosition())).commit();
                    break;
                case FRAGMENT_ALL_STUDYMATE_REQUEST:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllStudymateRequestFragment.newInstance(fragmentArgument.getArrayListData())).commit();
                    break;
                case FRAGMENT_EDIT_PROFILE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, EditProfileFragment.newInstance()).commit();
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
                    break;
                case FRAGMENT_TUTORIAL:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_tutorial;
                    imgTutorial.setActivated(true);
                    loadControllerTopMenu(null);
                    txtTitle.setText(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.group_name) + "</font><font color='#1BBC9B'>Venice Beauty</font>"));
                    txtTitle.setVisibility(View.VISIBLE);
                    break;
                case FRAGMENT_CLASSROOM:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_classroom;
                    imgClassroom.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_classroom);
                    loadControllerTopMenu(controllerTopMenuClassroom);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_classroom));
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
                case FRAGMENT_NOTES:
                    currentRightFragment = fragment;
                    imgNotes.setActivated(true);
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    currentRightFragment = fragment;
                    imgStudyMates.setActivated(true);
                    break;
                case FRAGMENT_CHAT:
                    currentRightFragment = fragment;
                    imgChat.setActivated(true);
                    break;
                case FRAGMENT_ALL_NOTES:
                    currentMainFragment = fragment;
                    txtTitle.setVisibility(View.GONE);
                    break;
                case FRAGMENT_GENERAL_SETTINGS:
                    currentMainFragment = fragment;
                    currentMainFragment = fragment;
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
                case FRAGMENT_EDIT_PROFILE:
                    currentMainFragment = fragment;
                    imgChat.setActivated(false);
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
                    loadControllerTopMenu(null);
                    txtTitle.setVisibility(View.GONE);
                    break;
                case FRAGMENT_CLASSROOM:
                    imgClassroom.setActivated(false);
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_ASSESSMENT:
                    imgAssessment.setActivated(false);
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_DESK:
                    imgDesk.setActivated(false);
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_REPORT_CARD:
                    imgReportCard.setActivated(false);
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_NOTES:
                    imgNotes.setActivated(false);
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    imgStudyMates.setActivated(false);
                    break;
                case FRAGMENT_CHAT:
                    imgChat.setActivated(false);
                    break;
                case FRAGMENT_GENERAL_SETTINGS:
                    // llControllerLeft.setVisibility(View.VISIBLE);
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
                case FRAGMENT_EDIT_PROFILE:
                   // loadControllerTopMenu(null);
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
                for (int i = 0; i < arrTxtMenu.length; i++) {
                    arrTxtMenu[i].setTextColor(Color.WHITE);
                    arrTxtMenu[i].setText("");
                    arrTxtMenu[i].setVisibility(View.GONE);
                }
                rlControllerTopMenu.setBackgroundColor(Color.TRANSPARENT);
                rlControllerTopMenu.setVisibility(View.GONE);
            } else {
                rlControllerTopMenu.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                hideControllerTopControls();
                for (int i = 0; i < arrTxtMenu.length; i++) {
                    if (i < menu.size()) {
                        currentControllerTopMenu.get(i).setIsActive(false);
                        arrTxtMenu[i].setTextColor(Color.WHITE);
                        arrTxtMenu[i].setText(menu.get(i).getMenuItemTitle());
                        startSlideAnimation(arrTxtMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
                        arrTxtMenu[i].setVisibility(View.VISIBLE);
                    } else {
                        arrTxtMenu[i].setText("");
                        startSlideAnimation(arrTxtMenu[i], 0, rlControllerTopMenu.getWidth(), 0, 0);
                        arrTxtMenu[i].setVisibility(View.GONE);
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
                /**
                 * Controller top back button click
                 */

                hideControllerTopControls();

                if (currentControllerTopMenu != null) {
                    for (int i = 0; i < currentControllerTopMenu.size(); i++) {
                        arrTxtMenu[i].setTextColor(Color.WHITE);
                        currentControllerTopMenu.get(i).setIsActive(false);
                        startSlideAnimation(arrTxtMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
                        arrTxtMenu[i].setVisibility(View.VISIBLE);
                    }
                }

                switch (currentMainFragment) {
                    case FRAGMENT_ALL_NOTIFICATION:
                        listenerHostAllNotification.onControllerTopBackClick();
                        break;
                    case FRAGMENT_ALL_MESSAGE:
                        listenerHostAllMessage.onControllerTopBackClick();
                        break;
                }

            } else if (view == txtAction) {
                /**
                 * Controller top action button click
                 */

                Log.e(TAG, "text action");
                /*switch (currentMainFragment) {
                    case FRAGMENT_CLASSROOM:
	                    switch (ClassroomFragment.getCurrentChildFragment()) {
		                    case ClassroomFragment.FRAGMENT_CLASSWALL:
			                    break;
	                    }
	                    break;
                }*/
            } else {
                boolean isActive = false;
                for (int i = 0; i < currentControllerTopMenu.size(); i++) {
                    if (view == arrTxtMenu[i] && currentControllerTopMenu.get(i).isActive()) {
                        isActive = true;
                        break;
                    }
                }
                if (!isActive) {
                    for (int i = 0; i < currentControllerTopMenu.size(); i++) {
                        if (view == arrTxtMenu[i]) {
                            currentControllerTopMenu.get(i).setIsActive(true);
                            arrTxtMenu[i].setTextColor(getResources().getColor(currentMainFragmentBg));

                            showControllerTopBackButton();

                            if (currentControllerTopMenu.get(i).getSubMenu() == null) {
                                startSlideAnimation(arrTxtMenu[i], -imgMenuBack.getWidth(), 0, 0, 0);
                                arrTxtMenu[i].setVisibility(View.VISIBLE);
                            } else {
                                arrTxtMenu[i].setVisibility(View.GONE);
                                startSlideAnimation(spSubmenu, -imgMenuBack.getWidth(), 0, 0, 0);
                                spSubmenu.setVisibility(View.VISIBLE);
                                adapterControllerTopSpinner = new ControllerTopSpinnerAdapter(currentControllerTopMenu.get(i).getSubMenu(), HostActivity.this);
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
                            startSlideAnimation(arrTxtMenu[i], 0, rlControllerTopMenu.getWidth(), 0, 0);
                            arrTxtMenu[i].setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onMenuItemClick Exception : " + e.toString());
        }
    }

    public void showControllerTopBackButton() {
        startSlideAnimation(imgMenuBack, -1000, 0, 0, 0);
        imgMenuBack.setVisibility(View.VISIBLE);
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

    public int getCurrentMainFragment() {
        return currentMainFragment;
    }

    public int getCurrentRightFragment() {
        return currentRightFragment;
    }

    public void showProgress() {
        try {
            Global.intApiCounter++;
            if (progHost != null && progHost.getVisibility() != View.VISIBLE) {
                progHost.setProgress(1);
                progHost.setVisibility(View.VISIBLE);
                progressGenerator.start(progHost);
            }
        } catch (Exception e) {
            Log.e(TAG, "showProgress Exception : " + e.toString());
        }
    }

    public void hideProgress() {
        try {
            if (progHost != null && progHost.getVisibility() == View.VISIBLE && --Global.intApiCounter == 0) {
                progHost.setProgress(100);
                progHost.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Log.e(TAG, "hideProgress Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        hideProgress();
        try {


            if (WebConstants.GENERAL_SETTING_PREFERENCES == apiCode) {
                onResponseGetAllPreference(object, error);

            } else if (WebConstants.GET_USER_PREFERENCES == apiCode) {
                onResponseGetUserPreference(object, error);

            } else if (WebConstants.GET_ALL_BADGES_COUNT == apiCode) {
                onResponseGetAllBadges(object, error);
            }
        } catch (Exception e) {
            Log.e(TAG, "On response Exception : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetAllPreference(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseGetAllPreferences responseObject = (ResponseGetAllPreferences) object;
                if (responseObject.getStatus().toString().equals(WebConstants.SUCCESS)) {
                    if (responseObject.getData().size() > 0) {

                        arrayListSMSAlert = responseObject.getData().get(0).getSMSAlert();
                        for (int j = 0; j < arrayListSMSAlert.size(); j++) {
                            PreferenceData.setStringPrefs(arrayListSMSAlert.get(j).getPreferenceKey().toString(), getApplicationContext(), arrayListSMSAlert.get(j).getId());
                            //  PreferenceData.setStringPrefs(arrayList.get(j).getId(), getApplicationContext(), arrayList.get(j).getDefaultValue());
                        }
                        arrayListNotification = responseObject.getData().get(0).getNotification();
                        for (int j = 0; j < arrayListNotification.size(); j++) {
                            PreferenceData.setStringPrefs(arrayListNotification.get(j).getPreferenceKey().toString(), getApplicationContext(), arrayListNotification.get(j).getId());
                            // PreferenceData.setStringPrefs(arrayList.get(j).getId(), getApplicationContext(), arrayList.get(j).getDefaultValue());
                        }
                        arrayListPrivacySetting = responseObject.getData().get(0).getPrivacySetting();
                        for (int j = 0; j < arrayListPrivacySetting.size(); j++) {
                            PreferenceData.setStringPrefs(arrayListPrivacySetting.get(j).getPreferenceKey().toString(), getApplicationContext(), arrayListPrivacySetting.get(j).getId());
                            // PreferenceData.setStringPrefs(arrayList.get(j).getId(), getApplicationContext(), arrayList.get(j).getDefaultValue());
                        }
                    }

                } else if (responseObject.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "Failed to load general setting preferences");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAllPreference api Exceptiion : " + error.toString());
            }


        } catch (Exception e) {

            Debug.i(TAG, "onResponseGetAllPreference :" + e.getLocalizedMessage());

        }
    }

    private void onResponseGetUserPreference(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseUserPreferences responseObject = (ResponseUserPreferences) object;
                if (responseObject.getStatus().toString().equals(ResponseObject.SUCCESS)) {
                    if (responseObject.getData().size() > 0) {
                        ArrayList<DataUserPreferences> arrayListUserPreferences=new ArrayList<>();
                        arrayListUserPreferences=responseObject.getData();
                        for (int j = 0; j < arrayListUserPreferences.size(); j++) {
                            GeneralSettingsFragment.newInstance().setPreferenceList(arrayListUserPreferences.get(j).getId(), arrayListUserPreferences.get(j).getPreferenceValue(), getApplicationContext());
                        }
                    }

                } else if (responseObject.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "Failed to load user setting preferences");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetUserPreference api Exceptiion : " + error.toString());
            }


        } catch (Exception e) {

            Debug.i(TAG, "onResponseGetUserPreference :" + e.getLocalizedMessage());

        }
    }

    private void callApiForGetUserPreference() {

        try {
            showProgress();
            RequestObject requestObject = new RequestObject();
            requestObject.setUserId("1");
            new WebserviceWrapper(getApplicationContext(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_USER_PREFERENCES);

        } catch (Exception e) {

            Debug.i(TAG, "General setting Pereference :" + e.getLocalizedMessage());

        }

    }

    public void setListenerHostAboutMe(HostListenerAboutMe hostListenerAboutMe) {
        this.hostListenerAboutMe = hostListenerAboutMe;
    }

    public void setListenerHostAllNotification(HostListenerAllNotification listenerHostAllNotification) {
        this.listenerHostAllNotification = listenerHostAllNotification;
    }

    public void setListenerHostAllMessage(HostListenerAllMessage listenerHostAllMessage) {
        this.listenerHostAllMessage = listenerHostAllMessage;
    }

    public void setListnerHostProfileController(HostListenerProfileController listnerHostProfileController) {
        this.listnerHostProfileController = listnerHostProfileController;
    }


    private void onResponseGetAllBadges(Object object, Exception error) {
        try {
            hideProgress();
            if (object != null) {
                ResponseObject responseObject = (ResponseObject) object;
                if (responseObject.getStatus().equals(ResponseObject.SUCCESS)) {

                    String count = responseObject.getData().get(0).getNotificationCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_NOTIFICATION, HostActivity.this, count != null ? Integer.valueOf(count) : 0);

                    count = responseObject.getData().get(0).getMessageCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_MESSAGE, HostActivity.this, count != null ? Integer.valueOf(count) : 0);

                    count = responseObject.getData().get(0).getRequestCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_REQUEST, HostActivity.this, count != null ? Integer.valueOf(count) : 0);
                } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {
                    Log.e(TAG, "Failed to load badges count");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAllBadges api Exceptiion : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAllBadges Exceptiion : " + e.toString());
        }
    }

}
