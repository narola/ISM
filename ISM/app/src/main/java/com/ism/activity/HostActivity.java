package com.ism.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.fragment.AccordionFragment;
import com.ism.fragment.AllStudymateRequestFragment;
import com.ism.fragment.AssessmentFragment;
import com.ism.fragment.ChatFragment;
import com.ism.fragment.ClassroomFragment;
import com.ism.fragment.DeskFragment;
import com.ism.fragment.MyAuthorFragment;
import com.ism.fragment.desk.JotterScientificSymbolFragment;
import com.ism.fragment.myAuthor.authorDesk.AuthorDeskFragment;
import com.ism.fragment.tutorialGroup.TutorialFragment;
import com.ism.fragment.userProfile.AllMessageFragment;
import com.ism.fragment.userProfile.AllNoticeFragment;
import com.ism.fragment.userProfile.AllNotificationFragment;
import com.ism.fragment.userProfile.EditProfileFragment;
import com.ism.fragment.userProfile.GeneralSettingsFragment;
import com.ism.fragment.userProfile.MyActivityFragment;
import com.ism.fragment.userProfile.MyFeedsFragment;
import com.ism.fragment.userProfile.MyWalletFragment;
import com.ism.fragment.userProfile.ProfileControllerFragment;
import com.ism.fragment.userProfile.StudymatesFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.model.ControllerTopMenuItem;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.NotificationSetting;
import com.ism.ws.model.PrivacySetting;
import com.ism.ws.model.SMSAlert;
import com.ism.ws.model.TutorialGroupProfile;
import com.ism.ws.model.UserPreferences;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import realmhelper.StudentHelper;

/**
 * Created by c161 on --/10/15.
 */
public class HostActivity extends FragmentActivity implements FragmentListener, WebserviceWrapper.WebserviceResponse {

    private static final String TAG = HostActivity.class.getName();
    private LinearLayout llControllerLeft;
    private FrameLayout flFragmentContainerMain, flFragmentContainerRight;

    private RelativeLayout rlControllerTopMenu;
    private LinearLayout llSearch;
    private ImageView imgHome, imgTutorial, imgClassroom, imgAssessment, imgDesk, imgReportCard, imgLogOut,
            imgSearch, imgNotes, imgProfileController, imgChat, imgMenuBack;
    private TextView txtTitle, txtOne, txtTwo, txtThree, txtFour, txtFive, txtAction;
    private EditText etSearch;
    private Spinner spSubmenu;
    private ActionProcessButton progHost;
    private View.OnClickListener onClickMenuItem;
    private ControllerTopSpinnerAdapter adapterControllerTopSpinner;

    private ProgressGenerator progressGenerator;
    private HostListener listenerHost;
    private HostListenerTutorial listenerHostTutorial;

    private HostListenerDesk hostListenerDesk;
    private HostListenerAllNotification listenerHostAllNotification;
    private HostListenerAllMessage listenerHostAllMessage;
    private HostListenerFavourites listenerFavourites;
    private HostListenerProfileController listenerHostProfileController;
    private ProfileControllerPresenceListener listenerProfileControllerPresence;
    private HostListenerStudymates listenerHostStudymates;
    private AddToLibraryListner addToLibraryListner;
    private BooksListner booksListner;
    private HostListenerEditAboutMe listenerEditAboutMe;
    public InsertSymbolListener insertSymbolListener;
    private HostListenerMyAuthor listenerHostMyAuthor;
    private HostListenerQuestionPalette listenerQuestionPalette;
    private HostListenerAuthorDesk listenerAuthorDesk;
    private HostListenerFindMoreAuthors listenerHostFindMoreAuthors;

    private TextView arrTxtMenu[];
    private ArrayList<ControllerTopMenuItem> controllerTopMenuClassroom;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuAssessment;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuDesk;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuMyAuthor;
    private ArrayList<ControllerTopMenuItem> currentControllerTopMenu;
    private GeneralSettingsFragment generalSettingsFragment;
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_TUTORIAL = 1;
    public static final int FRAGMENT_CLASSROOM = 2;
    public static final int FRAGMENT_ASSESSMENT = 3;
    public static final int FRAGMENT_DESK = 4;
    public static final int FRAGMENT_MY_AUTHOR = 5;
    public static final int FRAGMENT_NOTES = 6;
    public static final int FRAGMENT_PROFILE_CONTROLLER = 7;
    public static final int FRAGMENT_CHAT = 8;
    public static final int FRAGMENT_ALL_NOTICE = 9;
    public static final int FRAGMENT_GENERAL_SETTINGS = 10;
    public static final int FRAGMENT_MY_FEEDS = 11;
    public static final int FRAGMENT_STUDYMATES = 12;
    public static final int FRAGMENT_MY_ACTIVITY = 13;
    public static final int FRAGMENT_MY_WALLET = 14;
    public static final int FRAGMENT_ALL_NOTIFICATION = 15;
    public static final int FRAGMENT_ALL_MESSAGE = 16;
    public static final int FRAGMENT_ALL_STUDYMATE_REQUEST = 17;
    public static final int FRAGMENT_EDIT_PROFILE = 18;
    public static final int FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL = 19;
    public static final int FRAGMENT_SUNDAY_EXAM = 20;


    private int currentMainFragment = -1;
    private int currentRightFragment;
    private int relaunchRightFragmentId = -1;
    private int currentMainFragmentBg;
    private ArrayList<NotificationSetting> arrayListNotificationSettings = new ArrayList<>();
    private ArrayList<SMSAlert> arrayListSMSAlert = new ArrayList<>();
    private ArrayList<PrivacySetting> arrayListPrivacySetting = new ArrayList<>();
    private InputMethodManager inputMethod;
    private ScrollListener scrollListener;
    private ResizeView resizeListView;
    private StudentHelper studentHelper;
    private boolean isUpdateActionBar = true;
    private int intSubItemSelection;
    private ArrayList<ControllerTopMenuItem> controllerTopMenuAutorDesk;
    private MyAuthorFragment myAuthorFragment;
    private int currentMainChildFragment = -1;
    private int currentChildAuthorDesk = -1;

    public interface ScrollListener {
        public void isLastPosition();

        public void isFirstPosition();
    }

    public interface HostListenerFindMoreAuthors {
        public void onControllerTopBackClick(int position);
    }

    public interface HostListener {
        public void onControllerMenuItemClicked(int position);
    }

    public void setListenerHostFindMoreAuthors(HostListenerFindMoreAuthors listenerHostFindMoreAuthors) {
        this.listenerHostFindMoreAuthors = listenerHostFindMoreAuthors;
    }

    public interface HostListenerAllNotification {
        public void onControllerTopBackClick();
    }

    public interface HostListenerEditAboutMe {
        public void onAmbition();

        public void onAboutMe();
    }

    public interface HostListenerAllMessage {
        public void onControllerTopBackClick();
    }

    public interface HostListenerMyAuthor {
        public void onControllerTopBackClick(int position);

        public void onLoadFragment(int position);
    }

    public interface HostListenerFavourites {
        public void onControllerTopItemChanged(int position);
    }

    public interface HostListenerProfileController {
        public void onBadgesFetched();

        public void onSubFragmentAttached(int fragmentId);

        public void onSubFragmentDetached(int fragmentId);
    }

    public interface HostListenerTutorial {
        public void setNewFragmentArguments(Bundle fragmentArguments);
    }

    public interface HostListenerStudymates {
        public void setNewFragmentArguments(Bundle fragmentArguments);
    }

    public interface ProfileControllerPresenceListener {
        public void onProfileControllerAttached();

        public void onProfileControllerDetached();
    }

    public interface ResizeView {
        public void onUnBlockUser();
    }

    public interface BooksListner {
        public void onAddToFav(int position);

        public void onRemoveFromFav(int position);

        public void onAddToLibrary(String id);

        public void onRemoveFromLibrary(String id);
        // public void onSearchFav(ArrayList<BookData> arrayList);
        // public void onSearchSuggested(ArrayList<BookData> arrayList);
    }

    public interface ManageResourcesListner {
        public void onAddToFav(int position);

        public void onRemoveFromFav(int position);

        public void onSearchFav(Object o);

        public void onSearchSuggested(Object o);
    }

    public interface HostListenerQuestionPalette {
        public void showTutorialGroupData(TutorialGroupProfile tutorialGroupProfile);
    }

    public interface HostListenerDesk {
        public void onBackMenuItemClick();
    }

    public interface HostListenerAuthorDesk {
        public void onTopControllerBackClick(int position);
    }

    public void setListenerHostAuthorDesk(HostListenerAuthorDesk listenerAuthorDesk) {
        this.listenerAuthorDesk = listenerAuthorDesk;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        initGlobal();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent");
        setIntent(intent);
        if (getIntent().getAction() != null && getIntent().getAction().equals(AppConstant.ACTION_FRIDAY_EXAM)) {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.ACTION, AppConstant.ACTION_FRIDAY_EXAM);
            loadFragment(FRAGMENT_TUTORIAL, bundle);
        }
    }

    private void initGlobal() {
        Global.myTypeFace = new MyTypeFace(HostActivity.this);
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
        imgProfileController = (ImageView) findViewById(R.id.img_profile_controller);
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
        inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        studentHelper = new StudentHelper(this);

        arrTxtMenu = new TextView[]{txtOne, txtTwo, txtThree, txtFour, txtFive};
        progressGenerator = new ProgressGenerator();

        progHost = (ActionProcessButton) findViewById(R.id.prog_host);
        Global.strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, HostActivity.this);
        Global.strFullName = PreferenceData.getStringPrefs(PreferenceData.USER_FULL_NAME, HostActivity.this);
        Log.e(TAG, "User Image : " + WebConstants.HOST_IMAGE_USER + PreferenceData.getStringPrefs(PreferenceData.USER_PROFILE_PIC, HostActivity.this));
        Global.strProfilePic = WebConstants.HOST_IMAGE_USER + PreferenceData.getStringPrefs(PreferenceData.USER_PROFILE_PIC, HostActivity.this);
//        Global.strProfilePic = "http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png";
        Global.strTutorialGroupId = PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, HostActivity.this);
        Global.strTutorialGroupName = PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_GROUP_NAME, HostActivity.this);
        Global.imageLoader = ImageLoader.getInstance();
        Global.imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));

        if (Utility.isConnected(HostActivity.this)) {
            callApiGetAllBadgesCount();
            callApiGetGeneralSettingPreferences();
            callApiForGetUserPreference();
        } else {
            Utility.alertOffline(HostActivity.this);
        }

        if (getIntent().getAction() != null && getIntent().getAction().equals(AppConstant.ACTION_FRIDAY_EXAM)) {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.ACTION, AppConstant.ACTION_FRIDAY_EXAM);
            loadFragment(FRAGMENT_TUTORIAL, bundle);
        } else {
            loadFragment(FRAGMENT_HOME, null);
            loadFragment(FRAGMENT_CHAT, null);
        }

        controllerTopMenuClassroom = ControllerTopMenuItem.getMenuClassroom(HostActivity.this);
        controllerTopMenuAssessment = ControllerTopMenuItem.getMenuAssessment(HostActivity.this);
        controllerTopMenuDesk = ControllerTopMenuItem.getMenuDesk(HostActivity.this);
        controllerTopMenuMyAuthor = ControllerTopMenuItem.getMenuMyAuthor(HostActivity.this);
        controllerTopMenuAutorDesk = ControllerTopMenuItem.getMenuMyAuthorDesk(HostActivity.this);

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
                loadFragment(FRAGMENT_MY_AUTHOR, null);
            }
        });

        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PreferenceData.clearWholePreference(HostActivity.this);
                PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, HostActivity.this, false);
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
                    Utility.hideKeyboard(getApplicationContext(), getCurrentFocus());
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

        imgProfileController.setOnClickListener(new View.OnClickListener() {
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

        spSubmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "spinner position : " + position);
                if (isUpdateActionBar && spSubmenu.isEnabled())
                    listenerFavourites.onControllerTopItemChanged(position);
                //if(position==1)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void callApiGetGeneralSettingPreferences() {
        try {
            showProgress();
            new WebserviceWrapper(this, new Attribute(), this).new WebserviceCaller().execute(WebConstants.GENERAL_SETTING_PREFERENCES);
        } catch (Exception e) {
            Log.e(TAG, "General setting Pereference :" + e.getLocalizedMessage());
        }
    }

    private void callApiGetAllBadgesCount() {
        try {
            showProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(Global.strUserId);

            new WebserviceWrapper(this, attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_BADGES_COUNT);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetAllBadgesCount Exception : " + e.toString());
        }
    }

    public void loadFragment(int fragment, Bundle fragmentArguments) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    if (currentMainFragment != fragment) {
                        ClassroomFragment homeFragment = ClassroomFragment.newInstance(FRAGMENT_HOME);
                        listenerHost = homeFragment;
                        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, homeFragment).commit();
                    }
                    break;
                case FRAGMENT_TUTORIAL:
                    if (currentMainFragment != fragment) {
                        com.ism.fragment.tutorialGroup.QuestionPaletteFragment questionPaletteFragment = com.ism.fragment.tutorialGroup.QuestionPaletteFragment.newInstance(true);
                        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                                TutorialFragment.newInstance(fragmentArguments, questionPaletteFragment)).commit();

                        switch (currentRightFragment) {
                            case FRAGMENT_NOTES:
                            case FRAGMENT_PROFILE_CONTROLLER:
                            case FRAGMENT_CHAT:
                                relaunchRightFragmentId = currentRightFragment;
                                break;
                            default:
                                relaunchRightFragmentId = FRAGMENT_CHAT;
                                break;
                        }
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fl_fragment_container_right, questionPaletteFragment).commit();

                        /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.addToBackStack(QuestionPaletteFragment.class.getSimpleName());
                        fragmentTransaction.replace(R.id.fl_fragment_container_right, questionPaletteFragment).commit();
                        imgNotes.setActivated(false);
                        imgProfileController.setActivated(false);
                        imgChat.setActivated(false);*/
                    } else if (fragmentArguments != null) {
                        if (listenerHostTutorial != null) {
                            listenerHostTutorial.setNewFragmentArguments(fragmentArguments);
                        }
                    }
                    break;
                case FRAGMENT_CLASSROOM:
                    if (currentMainFragment != fragment) {
                        ClassroomFragment classroomFragment = ClassroomFragment.newInstance(FRAGMENT_CLASSROOM);
                        listenerHost = classroomFragment;
                        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, classroomFragment).commit();
                    }
                    break;
                case FRAGMENT_ASSESSMENT:
                    if (currentMainFragment != fragment) {
                        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, AssessmentFragment.newInstance()).commit();
                    }
                    break;
                case FRAGMENT_DESK:
                    if (currentMainFragment != fragment) {
                        DeskFragment deskFragment = DeskFragment.newInstance();
                        listenerHost = deskFragment;
                        hostListenerDesk = deskFragment;
                        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, deskFragment).commit();
//                        if (currentRightFragment != FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL) {
//                            loadFragment(FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL, null);
//                        }
                    }
                    break;
                case FRAGMENT_MY_AUTHOR:
                    if (currentMainFragment != fragment) {
                        myAuthorFragment = MyAuthorFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, myAuthorFragment).commit();
                    }
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
                case FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, JotterScientificSymbolFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_NOTICE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllNoticeFragment.newInstance(fragmentArguments)).commit();
                    break;
                case FRAGMENT_GENERAL_SETTINGS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, GeneralSettingsFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MY_FEEDS:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, MyFeedsFragment.newInstance()).commit();
                    break;
                case FRAGMENT_STUDYMATES:
                    if (currentMainFragment == FRAGMENT_STUDYMATES) {
                        listenerHostStudymates.setNewFragmentArguments(fragmentArguments);
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, StudymatesFragment.newInstance(fragmentArguments)).commit();
                    }
                    break;
                case FRAGMENT_MY_ACTIVITY:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, MyActivityFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MY_WALLET:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, MyWalletFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ALL_NOTIFICATION:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllNotificationFragment.newInstance(fragmentArguments)).commit();
                    break;
                case FRAGMENT_ALL_MESSAGE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllMessageFragment.newInstance(fragmentArguments)).commit();
                    break;
                case FRAGMENT_ALL_STUDYMATE_REQUEST:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main,
                            AllStudymateRequestFragment.newInstance(fragmentArguments)).commit();
                    break;
                case FRAGMENT_EDIT_PROFILE:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, EditProfileFragment.newInstance()).commit();
                    break;
                case MyAuthorFragment.FRAGMENT_MY_AUTHORS:
                    listenerHostMyAuthor.onLoadFragment(MyAuthorFragment.FRAGMENT_MY_AUTHORS);
                    break;
                case MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE:
                    listenerHostMyAuthor.onLoadFragment(MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE);
                    break;
                case MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS:
                    listenerHostMyAuthor.onLoadFragment(MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS);
                    break;
                case MyAuthorFragment.FRAGMENT_AUTHOR_DESK:
                    listenerHostMyAuthor.onLoadFragment(MyAuthorFragment.FRAGMENT_AUTHOR_DESK);
                    //activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_DESK);
                    break;
                case MyAuthorFragment.FRAGMENT_GO_TRENDING:
                    listenerHostMyAuthor.onLoadFragment(MyAuthorFragment.FRAGMENT_GO_TRENDING);
                    //activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_DESK);
                    break;
                case MyAuthorFragment.FRAGMENT_AUTHOR_PAST_QUESTIONS:
                    listenerHostMyAuthor.onLoadFragment(MyAuthorFragment.FRAGMENT_AUTHOR_PAST_QUESTIONS);
                    break;
                case MyAuthorFragment.FRAGMENT_PAST_TRENDING_QUESTION_DETAIL:
                    listenerHostMyAuthor.onLoadFragment(MyAuthorFragment.FRAGMENT_PAST_TRENDING_QUESTION_DETAIL);
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
                case FRAGMENT_MY_AUTHOR:
                    currentMainFragment = fragment;
                    currentMainFragmentBg = R.color.bg_my_author;
                    imgReportCard.setActivated(true);
                    rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_report_card);
                    txtAction.setTextColor(getResources().getColor(R.color.bg_my_author));
                    txtOne.setTextColor(getResources().getColor(currentMainFragmentBg));
//                 loadControllerTopMenu(controllerTopMenuMyAuthor);
                    break;
                case FRAGMENT_NOTES:
                    currentRightFragment = fragment;
                    imgNotes.setActivated(true);
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    currentRightFragment = fragment;
                    imgProfileController.setActivated(true);
                    if (listenerProfileControllerPresence != null) {
                        listenerProfileControllerPresence.onProfileControllerAttached();
                    }
                    break;
                case FRAGMENT_CHAT:
                    currentRightFragment = fragment;
                    imgChat.setActivated(true);
                    break;
                case FRAGMENT_ALL_NOTICE:
                    currentMainFragment = fragment;
                    txtTitle.setVisibility(View.GONE);
                    break;
                case FRAGMENT_GENERAL_SETTINGS:
                case FRAGMENT_MY_FEEDS:
                case FRAGMENT_STUDYMATES:
                case FRAGMENT_MY_ACTIVITY:
                case FRAGMENT_MY_WALLET:
                case FRAGMENT_EDIT_PROFILE:
                    currentMainFragment = fragment;
                    if (listenerHostProfileController != null) {
                        listenerHostProfileController.onSubFragmentAttached(currentMainFragment);
                    }
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
                case FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL:
                    break;
                case MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE:
                    currentMainChildFragment = fragment;
                    if (imgMenuBack.getVisibility() == View.GONE) {
                        showControllerTopBackButton();
                    }
                    loadControllerTopCustomMenu();
//                    currentMainFragment = fragment;
//                    AuthorOfficeFragment authorOfficeFragment = AuthorOfficeFragment.newInstance();
//                    getFragmentManager().beginTransaction().replace(R.id.fl_my_authors, authorOfficeFragment,AppConstant.FRAGMENT_AUTHOR_OFFICE).commit();
                    break;
                case MyAuthorFragment.FRAGMENT_AUTHOR_DESK:
                    currentMainChildFragment = fragment;
//                    loadControllerTopMenu(controllerTopMenuAutorDesk);
                    loadTopMenuItem(getString(R.string.strMyAuthor_sDesk), true);
                    break;
                case MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS:
                    currentMainChildFragment = fragment;
                    loadTopMenuItem(getResources().getString(R.string.strMyAuthors), true);
//                    loadControllerTopMenu(controllerTopMenuMyAuthor);
                    break;
                case MyAuthorFragment.FRAGMENT_MY_AUTHORS:
                    currentMainChildFragment = fragment;
                    loadControllerTopMenu(controllerTopMenuMyAuthor);
                    break;
                case MyAuthorFragment.FRAGMENT_TERM_AND_CONDITION:
                    currentMainChildFragment = fragment;
                    loadTopMenuItem(getResources().getString(R.string.strMyAuthors), true);
                    break;
                //case AuthorDeskFragment.FRAGMENT_ABOUT_ME:
                // case AuthorDeskFragment.FRAGMENT_ASSIGNMENTS:
                case AuthorDeskFragment.FRAGMENT_BOOKASSIGNMENT:
                    //  case AuthorDeskFragment.FRAGMENT_BOOKS:
                    currentChildAuthorDesk = fragment;
                    break;
                case MyAuthorFragment.FRAGMENT_GO_TRENDING:
                    loadTopMenuItem(getResources().getString(R.string.strgotrending), true);
                    currentMainChildFragment = fragment;

                    break;
                case MyAuthorFragment.FRAGMENT_AUTHOR_PAST_QUESTIONS:
                    loadTopMenuItem(getResources().getString(R.string.strAuthorsPastQuestions), true);
                    currentMainChildFragment = fragment;

                    break;
                case FRAGMENT_SUNDAY_EXAM:
                    llControllerLeft.setVisibility(View.GONE);
                    flFragmentContainerRight.setVisibility(View.GONE);
                    break;
                case MyAuthorFragment.FRAGMENT_PAST_TRENDING_QUESTION_DETAIL:
                    loadTopMenuItem(getResources().getString(R.string.strAuthorsPastQuestions), true);
                    currentMainChildFragment = fragment;
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onFragmentAttached Exception : " + e.toString());
        }
    }

    private void loadControllerTopCustomMenu() {
        try {
            if (getBundle().containsKey(AppConstant.AUTHOR_NAME)) {
                loadTopMenuItem(getBundle().getString(AppConstant.AUTHOR_NAME), true);
            }
        } catch (Exception e) {
            Log.e(TAG, "loadControllerTopCustomMenu Exception : " + e.toString());
        }
    }

    private void loadTopMenuItem(String text, boolean isBackVisible) {
        hideControllerTopControls();
        if (isBackVisible) {
            showControllerTopBackButton();
        }
        txtOne.setTextColor(getResources().getColor(currentMainFragmentBg));
        txtOne.setText(text);
        startSlideAnimation(txtOne, rlControllerTopMenu.getWidth(), 0, 0, 0);
        Utility.showView(txtOne);
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
//                    getFragmentManager().popBackStack(QuestionPaletteFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    loadFragment(relaunchRightFragmentId > 0 ? relaunchRightFragmentId : FRAGMENT_CHAT, null);
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
                    isUpdateActionBar = true;
                    imgDesk.setActivated(false);
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_MY_AUTHOR:
                    currentMainChildFragment = -1;
                    imgReportCard.setActivated(false);
                    loadControllerTopMenu(null);
                    break;
                case FRAGMENT_NOTES:
                    imgNotes.setActivated(false);
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    imgProfileController.setActivated(false);
                    if (listenerProfileControllerPresence != null) {
                        listenerProfileControllerPresence.onProfileControllerDetached();
                    }
                    break;
                case FRAGMENT_CHAT:
                    imgChat.setActivated(false);
                    break;
                case FRAGMENT_GENERAL_SETTINGS:
                case FRAGMENT_MY_FEEDS:
                case FRAGMENT_STUDYMATES:
                case FRAGMENT_MY_ACTIVITY:
                case FRAGMENT_MY_WALLET:
                case FRAGMENT_EDIT_PROFILE:
                    if (listenerHostProfileController != null) {
                        listenerHostProfileController.onSubFragmentDetached(fragment);
                    }
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
                case FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL:
                    Log.i(TAG, "FRAGMENT_JOTTER_SCIENTIFIC_SYMBOL detached");
                    break;
                case MyAuthorFragment.FRAGMENT_AUTHOR_DESK:
                    // showControllerTopBackButton();
                    // loadControllerTopMenu(controllerTopMenuMyAuthor);
                    break;
//                case FRAGMENT_MY_AUTHORS:
//                    loadTopMenuItem("My Authors's");
//                    break;
//                case AuthorDeskFragment.FRAGMENT_ABOUT_ME:
//                case AuthorDeskFragment.FRAGMENT_ASSIGNMENTS:
                case AuthorDeskFragment.FRAGMENT_BOOKASSIGNMENT:
//                case AuthorDeskFragment.FRAGMENT_BOOKS:
                    currentChildAuthorDesk = -1;
                    break;
                case FRAGMENT_SUNDAY_EXAM:
                    llControllerLeft.setVisibility(View.VISIBLE);
                    flFragmentContainerRight.setVisibility(View.VISIBLE);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onFragmentDetached Exception : " + e.toString());
        }
    }

    public void showTutorialGroupData(TutorialGroupProfile tutorialGroupProfile) {
        txtTitle.setText(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.group_name)
                + "</font><font color='#1BBC9B'>" + tutorialGroupProfile.getGroupName() + "</font>"));
        txtTitle.setVisibility(View.VISIBLE);
        listenerQuestionPalette.showTutorialGroupData(tutorialGroupProfile);
    }

    @Override
    public void onFragmentResumed(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_NOTES:
                    imgNotes.setActivated(true);
                    break;
                case FRAGMENT_PROFILE_CONTROLLER:
                    imgProfileController.setActivated(true);
                    break;
                case FRAGMENT_CHAT:
                    imgChat.setActivated(true);
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

//                if (currentMainChildFragment == MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS || currentMainChildFragment == MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE) {
//                    hideControllerTopBackButton();
//                    listenerHostMyAuthor.onControllerTopBackClick(MyAuthorFragment.FRAGMENT_MY_AUTHORS);
//                } else if (currentMainChildFragment == MyAuthorFragment.FRAGMENT_TERM_AND_CONDITION) {
//                    hideControllerTopBackButton();
//                    listenerHostMyAuthor.onControllerTopBackClick(MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS);
//                } else if (currentMainChildFragment == MyAuthorFragment.FRAGMENT_AUTHOR_DESK) {
//                    hideControllerTopBackButton();
//                    listenerHostMyAuthor.onControllerTopBackClick(MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE);
//                }
                if (currentChildAuthorDesk != -1) {
                    listenerAuthorDesk.onTopControllerBackClick(currentChildAuthorDesk);
                } else if (currentMainChildFragment != -1) {
                    listenerHostMyAuthor.onControllerTopBackClick(currentMainChildFragment);
                } else {
                    if (isUpdateActionBar) {
                        hideControllerTopControls();
                        if (currentControllerTopMenu != null) {
                            for (int i = 0; i < currentControllerTopMenu.size(); i++) {
                                arrTxtMenu[i].setTextColor(Color.WHITE);
                                currentControllerTopMenu.get(i).setIsActive(false);
                                startSlideAnimation(arrTxtMenu[i], rlControllerTopMenu.getWidth(), 0, 0, 0);
                                arrTxtMenu[i].setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    switch (currentMainFragment) {
                        case FRAGMENT_ALL_NOTIFICATION:
                            listenerHostAllNotification.onControllerTopBackClick();
                            break;
                        case FRAGMENT_ALL_MESSAGE:
                            listenerHostAllMessage.onControllerTopBackClick();
                            break;
                        case FRAGMENT_DESK:
//                        switch ()
//                        if(DeskFragment.FRAGMENT_ALL_BOOKS)
                            hostListenerDesk.onBackMenuItemClick();
                            break;
//                        case MyAuthorFragment.FRAGMENT_MY_AUTHORS:
//                        case MyAuthorFragment.FRAGMENT_AUTHOR_DESK:
//                        case MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE:
//                            onControllerTopBack();
//                            break;
                    }
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
                if (currentMainChildFragment == -1) {
//                if (currentMainChildFragment != MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS && currentMainChildFragment != MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE && currentMainChildFragment != MyAuthorFragment.FRAGMENT_MY_AUTHORS) {
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


//                            if(view==spSubmenu){
//                                hostSpinnerListener.onControllerMenuSpinnerItemClicked(spSubmenu.getSelectedItem().toString());
//                            }
                            } else {
                                currentControllerTopMenu.get(i).setIsActive(false);
                                startSlideAnimation(arrTxtMenu[i], 0, rlControllerTopMenu.getWidth(), 0, 0);
                                arrTxtMenu[i].setVisibility(View.GONE);
                            }
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

    public void hideControllerTopBackButton() {
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
            switch (apiCode) {
                case WebConstants.GENERAL_SETTING_PREFERENCES:
                    onResponseGetAllPreference(object, error);
                    break;
                case WebConstants.GET_USER_PREFERENCES:
                    onResponseGetUserPreference(object, error);
                    break;
                case WebConstants.GET_ALL_BADGES_COUNT:
                    onResponseGetAllBadges(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "On response Exception : " + e.getLocalizedMessage());
        }
    }
    /*this bundle used to pass data between fragments and also managing backstack for the fragment*/
    /*remove bundle data on backclcik of fragment as it is not necessory*/

    public Bundle bundle = new Bundle();

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    private void onResponseGetAllPreference(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().toString().equals(WebConstants.SUCCESS)) {
                    if (responseHandler.getPreference().size() > 0) {
                        arrayListSMSAlert = responseHandler.getPreference().get(0).getSMSAlert();
                        for (int j = 0; j < arrayListSMSAlert.size(); j++) {
                            PreferenceData.setStringPrefs(arrayListSMSAlert.get(j).getPreferenceKey().toString(), getApplicationContext(), arrayListSMSAlert.get(j).getId());
                            //  PreferenceData.setStringPrefs(arrayList.get(j).getId(), getApplicationContext(), arrayList.get(j).getDefaultValue());
                        }
                        arrayListNotificationSettings = responseHandler.getPreference().get(0).getNotificationSettings();
                        for (int j = 0; j < arrayListNotificationSettings.size(); j++) {
                            PreferenceData.setStringPrefs(arrayListNotificationSettings.get(j).getPreferenceKey().toString(), getApplicationContext(), arrayListNotificationSettings.get(j).getId());
                            // PreferenceData.setStringPrefs(arrayList.get(j).getId(), getApplicationContext(), arrayList.get(j).getDefaultValue());
                        }

                        arrayListPrivacySetting = responseHandler.getPreference().get(0).getPrivacySetting();
                        for (int j = 0; j < arrayListPrivacySetting.size(); j++) {
                            PreferenceData.setStringPrefs(arrayListPrivacySetting.get(j).getPreferenceKey().toString(), getApplicationContext(), arrayListPrivacySetting.get(j).getId());
                            //PreferenceData.setStringPrefs(arrayList.get(j).getId(), getApplicationContext(), arrayList.get(j).getDefaultValue());
                        }
                    }

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "Failed to load general setting preferences");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAllPreference api Exceptiion : " + error.toString());
            }


        } catch (Exception e) {

            Log.e(TAG, "onResponseGetAllPreference :" + e.getLocalizedMessage());

        }
    }

    private void onResponseGetUserPreference(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseObject = (ResponseHandler) object;
                if (responseObject.getStatus().toString().equals(WebConstants.SUCCESS)) {
                    if (responseObject.getUserPreference() != null) {
                        ArrayList<UserPreferences> arrayListUserPreferences = new ArrayList<>();
                        arrayListUserPreferences = responseObject.getUserPreference();
                        for (int j = 0; j < arrayListUserPreferences.size(); j++) {
                            Log.e(TAG, "j :" + j);
                            //generalSettingsFragment.setPreferenceList(arrayListUserPreferences.get(j).getId(), arrayListUserPreferences.get(j).getPreferenceValue(), getApplicationContext());
                            PreferenceData.setStringPrefs(arrayListUserPreferences.get(j).getId(), this, arrayListUserPreferences.get(j).getPreferenceValue());
                        }
                    }

                } else if (responseObject.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "Failed to load user setting preferences");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetUserPreference api Exceptiion : " + error.toString());
            }


        } catch (Exception e) {

            Log.e(TAG, "onResponseGetUserPreference :" + e.getLocalizedMessage());

        }
    }

    private void callApiForGetUserPreference() {
        try {
            showProgress();
            Attribute requestObject = new Attribute();
            requestObject.setUserId(Global.strUserId);
            new WebserviceWrapper(this, requestObject, this).new WebserviceCaller().execute(WebConstants.GET_USER_PREFERENCES);
        } catch (Exception e) {
            Log.e(TAG, "General setting Pereference :" + e.getLocalizedMessage());
        }
    }

    public void setListenerHostEditAboutMe(HostListenerEditAboutMe listenerHostEditAboutMe) {
        this.listenerEditAboutMe = listenerHostEditAboutMe;
    }

    public void setListenerHostTutorial(HostListenerTutorial listenerHostTutorial) {
        this.listenerHostTutorial = listenerHostTutorial;
    }

    public void setListenerHostScroll(ScrollListener scrollListner) {
        this.scrollListener = scrollListner;
    }

    public void setListenerHostAllNotification(HostListenerAllNotification listenerHostAllNotification) {
        this.listenerHostAllNotification = listenerHostAllNotification;
    }

    public void setListenerHostAllMessage(HostListenerAllMessage listenerHostAllMessage) {
        this.listenerHostAllMessage = listenerHostAllMessage;
    }

    public void setListenerHostMyAuthor(HostListenerMyAuthor listenerHostMyAuthor) {
        this.listenerHostMyAuthor = listenerHostMyAuthor;
    }

    public void setListenerHostProfileController(HostListenerProfileController listenerHostProfileController) {
        this.listenerHostProfileController = listenerHostProfileController;
    }


    public void setListenerProfileControllerPresence(ProfileControllerPresenceListener listenerProfileControllerPresence) {
        this.listenerProfileControllerPresence = listenerProfileControllerPresence;
    }

    public void setListenerHostStudymates(HostListenerStudymates listenerHostStudymates) {
        this.listenerHostStudymates = listenerHostStudymates;
    }

    public void setListenerFavourites(HostListenerFavourites listenerFavourites) {
        this.listenerFavourites = listenerFavourites;
    }

    public interface AddToLibraryListner {
        public void onAddToLibrary(String id);

        public void onRemoveFromLibrary(String id);
    }

    public void setOnControllerTopBackHostListener(HostListenerDesk hostListenerDesk) {
        this.hostListenerDesk = hostListenerDesk;
    }

    public void setHostListener(HostListener listenerHost) {
        this.listenerHost = listenerHost;
    }

    public void setListenerResizeView(ResizeView resizeListView) {
        this.resizeListView = resizeListView;
    }

    public interface InsertSymbolListener {
        public void Scientific(String symbol);
    }

    public void setInsertSymbolListener(InsertSymbolListener insertSymbolListener) {
        this.insertSymbolListener = insertSymbolListener;
    }

    public void setListenerAddToLibrary(AddToLibraryListner addToLibraryListner) {
        this.addToLibraryListner = addToLibraryListner;
    }

    public void setListenerQuestionPalette(HostListenerQuestionPalette listenerQuestionPalette) {
        this.listenerQuestionPalette = listenerQuestionPalette;
    }

    private void onResponseGetAllBadges(Object object, Exception error) {
        try {
            hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

                    String count = responseHandler.getBadges().get(0).getNotificationCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_NOTIFICATION, HostActivity.this, count != null ? Integer.valueOf(count) : 0);

                    count = responseHandler.getBadges().get(0).getMessageCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_MESSAGE, HostActivity.this, count != null ? Integer.valueOf(count) : 0);

                    count = responseHandler.getBadges().get(0).getRequestCount();
                    PreferenceData.setIntPrefs(PreferenceData.BADGE_COUNT_REQUEST, HostActivity.this, count != null ? Integer.valueOf(count) : 0);
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "Failed to load badges count");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAllBadges api Exceptiion : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAllBadges Exceptiion : " + e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onChildFragmentAttached(boolean isUpdateActionBar) {
        this.isUpdateActionBar = isUpdateActionBar;
    }

    public void onSetPositionSpinner(int position) {
        intSubItemSelection = position;
        spSubmenu.setSelection(intSubItemSelection);
        if (position == 0) spSubmenu.setEnabled(true);
        else spSubmenu.setEnabled(false);
    }

    public void updateLayoutForExam(boolean examStart) {
        llControllerLeft.setVisibility(examStart ? View.GONE : View.VISIBLE);
        imgHome.setEnabled(!examStart);
        imgSearch.setEnabled(!examStart);
        etSearch.setEnabled(!examStart);
        imgNotes.setEnabled(!examStart);
        imgProfileController.setEnabled(!examStart);
        imgChat.setEnabled(!examStart);
    }

}
