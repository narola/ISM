package com.ism.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.ism.fragment.AllMessageFragment;
import com.ism.fragment.AllNoticeFragment;
import com.ism.fragment.AllNotificationFragment;
import com.ism.fragment.AllStudymateRequestFragment;
import com.ism.fragment.AssessmentFragment;
import com.ism.fragment.ChatFragment;
import com.ism.fragment.ClassroomFragment;
import com.ism.fragment.DeskFragment;
import com.ism.fragment.GeneralSettingsFragment;
import com.ism.fragment.MyActivityFragment;
import com.ism.fragment.MyFeedsFragment;
import com.ism.fragment.MyWalletFragment;
import com.ism.fragment.NotesFragment;
import com.ism.fragment.ReportCardFragment;
import com.ism.fragment.ProfileControllerFragment;
import com.ism.fragment.StudymatesFragment;
import com.ism.fragment.TutorialFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.model.ControllerTopMenuItem;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class HostActivity extends Activity implements FragmentListener {

    private static final String TAG = HostActivity.class.getName();

    private LinearLayout llControllerLeft;
    private FrameLayout flFragmentContainerMain;
    private FrameLayout flFragmentContainerRight;
    private RelativeLayout rlControllerTopMenu;
    private LinearLayout llSearch;
    private ImageView imgHome;
    private ImageView imgTutorial;
    private ImageView imgClassroom;
    private ImageView imgAssessment;
    private ImageView imgDesk;
    private ImageView imgReportCard;
    private ImageView imgLogOut;
    private ImageView imgSearch;
    private ImageView imgNotes;
    private ImageView imgStudyMates;
    private ImageView imgChat;
    private ImageView imgMenuBack;
    private TextView txtTitle;
    private TextView txtOne;
    private TextView txtTwo;
    private TextView txtThree;
    private TextView txtFour;
    private TextView txtFive;
    private TextView txtAction;
    private EditText etSearch;
    private Spinner spSubmenu;
	private ActionProcessButton progHost;

    private View.OnClickListener onClickMenuItem;
    private ControllerTopSpinnerAdapter adapterControllerTopSpinner;
    private HostListener listenerHost;
	private ProgressGenerator progressGenerator;

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
	public static int currentMainFragment;
    public static int currentRightFragment;
    private int currentMainFragmentBg;

    public interface HostListener {
        public void onControllerMenuItemClicked(int position);
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

    }

    public void loadFragment(int fragment, Object object) {
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
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, NotesFragment.newInstance()).commit();
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, ProfileControllerFragment.newInstance()).commit();
                    break;
                case FRAGMENT_CHAT:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, ChatFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_NOTES:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AllNoticeFragment.newInstance((ArrayList<Data>) object)).commit();
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
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AllNotificationFragment.newInstance((ArrayList<Data>) object)).commit();
                    break;
                case FRAGMENT_ALL_MESSAGE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AllMessageFragment.newInstance((ArrayList<Data>) object)).commit();
                    break;
                case FRAGMENT_ALL_STUDYMATE_REQUEST:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AllStudymateRequestFragment.newInstance((ArrayList<Data>) object)).commit();
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
                hideControllerTopControls();

                for (int i = 0; i < currentControllerTopMenu.size(); i++) {
                    arrTxtMenu[i].setTextColor(Color.WHITE);
                    currentControllerTopMenu.get(i).setIsActive(false);
                    startSlideAnimation(arrTxtMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
                    arrTxtMenu[i].setVisibility(View.VISIBLE);
                }

            } else if (view == txtAction) {
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

                            startSlideAnimation(imgMenuBack, -1000, 0, 0, 0);
                            imgMenuBack.setVisibility(View.VISIBLE);

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
		    Log.e(TAG, "hideProgress Exception : " + e.toString());
	    }
    }

}
