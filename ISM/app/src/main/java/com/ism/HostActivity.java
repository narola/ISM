package com.ism;

import android.app.Activity;
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

import com.ism.adapter.ControllerTopSpinnerAdapter;
import com.ism.fragment.AssessmentFragment;
import com.ism.fragment.ChatFragment;
import com.ism.fragment.ClassroomFragment;
import com.ism.fragment.DeskFragment;
import com.ism.fragment.HomeFragment;
import com.ism.fragment.NotesFragment;
import com.ism.fragment.ReportCardFragment;
import com.ism.fragment.StudyMatesFragment;
import com.ism.fragment.TutorialFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.ControllerTopMenuItem;
import com.ism.utility.Utility;

import java.util.ArrayList;

public class HostActivity extends Activity implements FragmentListener {

    private static final String TAG = HostActivity.class.getSimpleName();

    private LinearLayout mLayoutControllerLeft;
    private FrameLayout mLayoutFragmentContainerMain;
    private FrameLayout mLayoutFragmentContainerRight;
	private RelativeLayout mLayoutControllerTopMenu;
	private LinearLayout mLayoutSearch;
    private ImageView mImageHome;
    private ImageView mImageTutorial;
    private ImageView mImageClassroom;
    private ImageView mImageAssessment;
    private ImageView mImageDesk;
    private ImageView mImageReportCard;
    private ImageView mImageLogOut;
    private ImageView mImageSearch;
    private ImageView mImageNotes;
    private ImageView mImageStudyMates;
    private ImageView mImageChat;
    private ImageView mImageMenuBack;
    private TextView mTextTitle;
    private TextView mTextOne;
    private TextView mTextTwo;
    private TextView mTextThree;
    private TextView mTextFour;
    private TextView mTextFive;
    private TextView mTextAction;
	private EditText mEditSearch;
	private Spinner mSpinnerSubmenu;

	private View.OnClickListener mMenuItemClickListener;
	private ControllerTopSpinnerAdapter mControllerTopSpinnerAdapter;

	private TextView mTextsMenu[];
	private ArrayList<ControllerTopMenuItem> mControllerTopMenuClassroom;
	private ArrayList<ControllerTopMenuItem> mControllerTopMenuAssessment;
	private ArrayList<ControllerTopMenuItem> mControllerTopMenuDesk;
	private ArrayList<ControllerTopMenuItem> mControllerTopMenuReportCard;
	private ArrayList<ControllerTopMenuItem> mCurrentControllerTopMenu;

	public static final int FRAGMENT_HOME = 0;
	public static final int FRAGMENT_TUTORIAL = 1;
	public static final int FRAGMENT_CLASSROOM = 2;
	public static final int FRAGMENT_ASSESSMENT = 3;
	public static final int FRAGMENT_DESK = 4;
	public static final int FRAGMENT_REPORT_CARD = 5;
	public static final int FRAGMENT_NOTES = 6;
	public static final int FRAGMENT_STUDY_MATES = 7;
	public static final int FRAGMENT_CHAT = 8;
	public static int sCurrentMainFragment;
    public static int sCurrentRightFragment;
	private int mCurrentMainFragmentBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        inigGlobal();

    }

    private void inigGlobal() {
        mLayoutControllerLeft = (LinearLayout) findViewById(R.id.layout_controller_left);
        mLayoutFragmentContainerMain = (FrameLayout) findViewById(R.id.layout_fragment_container_main);
        mLayoutFragmentContainerRight = (FrameLayout) findViewById(R.id.layout_fragment_container_right);
	    mLayoutControllerTopMenu = (RelativeLayout) findViewById(R.id.layout_controller_top_menu);
	    mLayoutSearch = (LinearLayout) findViewById(R.id.layout_search);
        mImageHome = (ImageView) findViewById(R.id.image_home);
        mImageTutorial = (ImageView) findViewById(R.id.image_tutorial);
        mImageClassroom = (ImageView) findViewById(R.id.image_classroom);
        mImageAssessment = (ImageView) findViewById(R.id.image_assessment);
        mImageDesk = (ImageView) findViewById(R.id.image_desk);
        mImageReportCard = (ImageView) findViewById(R.id.image_reportcard);
        mImageLogOut = (ImageView) findViewById(R.id.image_logout);
        mImageSearch = (ImageView) findViewById(R.id.image_search);
        mImageNotes = (ImageView) findViewById(R.id.image_notes);
        mImageStudyMates = (ImageView) findViewById(R.id.image_study_mates);
        mImageChat = (ImageView) findViewById(R.id.image_chat);
	    mImageMenuBack = (ImageView) findViewById(R.id.image_back);
	    mTextTitle = (TextView) findViewById(R.id.text_title);
	    mTextOne = (TextView) findViewById(R.id.text_one);
	    mTextTwo = (TextView) findViewById(R.id.text_two);
	    mTextThree = (TextView) findViewById(R.id.text_three);
	    mTextFour = (TextView) findViewById(R.id.text_four);
	    mTextFive = (TextView) findViewById(R.id.text_five);
	    mTextAction = (TextView) findViewById(R.id.text_action);
	    mEditSearch = (EditText) findViewById(R.id.edit_search);
	    mSpinnerSubmenu = (Spinner) findViewById(R.id.spinner_submenu);

	    mTextsMenu = new TextView[]{mTextOne, mTextTwo, mTextThree, mTextFour, mTextFive};

        loadFragment(FRAGMENT_TUTORIAL);
        loadFragment(FRAGMENT_CHAT);

	    mControllerTopMenuClassroom = ControllerTopMenuItem.getMenuClassroom(HostActivity.this);
	    mControllerTopMenuAssessment = ControllerTopMenuItem.getMenuAssessment(HostActivity.this);
	    mControllerTopMenuDesk = ControllerTopMenuItem.getMenuDesk(HostActivity.this);
	    mControllerTopMenuReportCard = ControllerTopMenuItem.getMenuReportCard(HostActivity.this);

        mImageHome.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        loadFragment(FRAGMENT_HOME);
	        }
        });
        mImageTutorial.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        loadFragment(FRAGMENT_TUTORIAL);
	        }
        });

        mImageClassroom.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        loadFragment(FRAGMENT_CLASSROOM);
	        }
        });

        mImageAssessment.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        loadFragment(FRAGMENT_ASSESSMENT);
	        }
        });

        mImageDesk.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        loadFragment(FRAGMENT_DESK);
	        }
        });

        mImageReportCard.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        loadFragment(FRAGMENT_REPORT_CARD);
	        }
        });

        mImageLogOut.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {

	        }
        });

        mImageSearch.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        mImageSearch.setActivated(!mImageSearch.isActivated());
		        if (mEditSearch.getVisibility() == View.VISIBLE) {
//		            startSlideAnimation(mEditSearch, 0, mEditSearch.getWidth(), 0, 0);
//		            startSlideAnimation(mImageSearch, -mImageSearch.getWidth(), 0, 0, 0);
			        mEditSearch.setVisibility(View.GONE);
		        } else {
			        startSlideAnimation(mEditSearch, mEditSearch.getWidth(), 0, 0, 0);
			        startSlideAnimation(mImageSearch, mEditSearch.getWidth(), 0, 0, 0);
			        mEditSearch.setVisibility(View.VISIBLE);
			        Utility.showSoftKeyboard(mEditSearch, HostActivity.this);
		        }
	        }
        });

	    mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				    Log.e(TAG, "search clicked");
			    }
			    return false;
		    }
	    });

        mImageNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_NOTES);
            }
        });

        mImageStudyMates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_STUDY_MATES);
            }
        });

        mImageChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(FRAGMENT_CHAT);
            }
        });

	    mMenuItemClickListener = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    onMenuItemClick(v);
		    }
	    };

	    mImageMenuBack.setOnClickListener(mMenuItemClickListener);
	    mTextOne.setOnClickListener(mMenuItemClickListener);
	    mTextTwo.setOnClickListener(mMenuItemClickListener);
	    mTextThree.setOnClickListener(mMenuItemClickListener);
	    mTextFour.setOnClickListener(mMenuItemClickListener);
	    mTextFive.setOnClickListener(mMenuItemClickListener);
	    mTextAction.setOnClickListener(mMenuItemClickListener);

    }

	private void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_HOME:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_main, HomeFragment.newInstance()).commit();
                    break;
                case FRAGMENT_TUTORIAL:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_main, TutorialFragment.newInstance()).commit();
                    break;
                case FRAGMENT_CLASSROOM:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_main, ClassroomFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ASSESSMENT:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_main, AssessmentFragment.newInstance()).commit();
                    break;
                case FRAGMENT_DESK:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_main, DeskFragment.newInstance()).commit();
                    break;
                case FRAGMENT_REPORT_CARD:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_main, ReportCardFragment.newInstance()).commit();
                    break;
                case FRAGMENT_NOTES:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_right, NotesFragment.newInstance()).commit();
                    break;
                case FRAGMENT_STUDY_MATES:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_right, StudyMatesFragment.newInstance()).commit();
                    break;
                case FRAGMENT_CHAT:
                    getFragmentManager().beginTransaction().replace(R.id.layout_fragment_container_right, ChatFragment.newInstance()).commit();
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
                    sCurrentMainFragment = fragment;
	                mImageHome.setActivated(true);
	                loadControllerTopMenu(null);
	                mTextTitle.setVisibility(View.GONE);
                    break;
	            case FRAGMENT_TUTORIAL:
		            sCurrentMainFragment = fragment;
		            mCurrentMainFragmentBg = R.color.bg_tutorial;
		            mImageTutorial.setActivated(true);
		            loadControllerTopMenu(null);
		            mTextTitle.setText(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.group_name) + "</font><font color='#1BBC9B'>Venice Beauty</font>"));
		            mTextTitle.setVisibility(View.VISIBLE);
		            break;
	            case FRAGMENT_CLASSROOM:
		            sCurrentMainFragment = fragment;
		            mCurrentMainFragmentBg = R.color.bg_classroom;
		            mImageClassroom.setActivated(true);
		            mLayoutControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_classroom);
		            mTextAction.setTextColor(getResources().getColor(R.color.bg_classroom));
		            loadControllerTopMenu(mControllerTopMenuClassroom);
		            break;
                case FRAGMENT_ASSESSMENT:
                    sCurrentMainFragment = fragment;
	                mCurrentMainFragmentBg = R.color.bg_assessment;
                    mImageAssessment.setActivated(true);
	                mLayoutControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_assessment);
	                mTextAction.setTextColor(getResources().getColor(R.color.bg_assessment));
	                loadControllerTopMenu(mControllerTopMenuAssessment);
                    break;
                case FRAGMENT_DESK:
                    sCurrentMainFragment = fragment;
	                mCurrentMainFragmentBg = R.color.bg_desk;
                    mImageDesk.setActivated(true);
	                mLayoutControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_desk);
	                mTextAction.setTextColor(getResources().getColor(R.color.bg_desk));
	                loadControllerTopMenu(mControllerTopMenuDesk);
                    break;
                case FRAGMENT_REPORT_CARD:
                    sCurrentMainFragment = fragment;
	                mCurrentMainFragmentBg = R.color.bg_report_card;
                    mImageReportCard.setActivated(true);
	                mLayoutControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_report_card);
	                mTextAction.setTextColor(getResources().getColor(R.color.bg_report_card));
	                loadControllerTopMenu(mControllerTopMenuReportCard);
                    break;
                case FRAGMENT_NOTES:
                    sCurrentRightFragment = fragment;
                    mImageNotes.setActivated(true);
                    break;
                case FRAGMENT_STUDY_MATES:
                    sCurrentRightFragment = fragment;
                    mImageStudyMates.setActivated(true);
                    break;
                case FRAGMENT_CHAT:
                    sCurrentRightFragment = fragment;
                    mImageChat.setActivated(true);
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
                    mImageHome.setActivated(false);
                    break;
                case FRAGMENT_TUTORIAL:
                    mImageTutorial.setActivated(false);
                    break;
                case FRAGMENT_CLASSROOM:
                    mImageClassroom.setActivated(false);
                    break;
                case FRAGMENT_ASSESSMENT:
                    mImageAssessment.setActivated(false);
                    break;
                case FRAGMENT_DESK:
                    mImageDesk.setActivated(false);
                    break;
                case FRAGMENT_REPORT_CARD:
                    mImageReportCard.setActivated(false);
                    break;
                case FRAGMENT_NOTES:
                    mImageNotes.setActivated(false);
                    break;
                case FRAGMENT_STUDY_MATES:
                    mImageStudyMates.setActivated(false);
                    break;
                case FRAGMENT_CHAT:
                    mImageChat.setActivated(false);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onFragmentDetached Exception : " + e.toString());
        }
    }

	private void loadControllerTopMenu(ArrayList<ControllerTopMenuItem> menu) {
		try {
			mCurrentControllerTopMenu = menu;
			if (menu == null) {
				hideControllerTopControls();
				mLayoutControllerTopMenu.setVisibility(View.GONE);
			} else {
				mLayoutControllerTopMenu.setVisibility(View.VISIBLE);
				mTextTitle.setVisibility(View.GONE);
				hideControllerTopControls();
				for (int i = 0; i < mTextsMenu.length; i++) {
					if (i < menu.size()) {
						mCurrentControllerTopMenu.get(i).setIsActive(false);
						mTextsMenu[i].setTextColor(Color.WHITE);
						mTextsMenu[i].setText(menu.get(i).getMenuItemTitle());
						startSlideAnimation(mTextsMenu[i], mLayoutControllerTopMenu.getWidth(), 0, 0, 0);
						mTextsMenu[i].setVisibility(View.VISIBLE);
					} else {
						mTextsMenu[i].setText("");
						startSlideAnimation(mTextsMenu[i], 0, mLayoutControllerTopMenu.getWidth(), 0, 0);
						mTextsMenu[i].setVisibility(View.GONE);
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "loadMenu Exception : " + e.toString());
		}
	}

	private void onMenuItemClick(View view) {
		try {
			if (view == mImageMenuBack) {
				hideControllerTopControls();

				for (int i = 0; i < mCurrentControllerTopMenu.size(); i++) {
					mTextsMenu[i].setTextColor(Color.WHITE);
					mCurrentControllerTopMenu.get(i).setIsActive(false);
					startSlideAnimation(mTextsMenu[i], mLayoutControllerTopMenu.getWidth(), 0, 0, 0);
					mTextsMenu[i].setVisibility(View.VISIBLE);
				}

			} else if (view == mTextAction) {
				Log.e(TAG, "text action");
			} else {
				boolean isActive = false;
				for (int i = 0; i < mCurrentControllerTopMenu.size(); i++) {
					if (view == mTextsMenu[i] && mCurrentControllerTopMenu.get(i).isActive()) {
						isActive = true;
						break;
					}
				}
				if (!isActive) {
					for (int i = 0; i < mCurrentControllerTopMenu.size(); i++) {
						if (view == mTextsMenu[i]) {
							mCurrentControllerTopMenu.get(i).setIsActive(true);
							mTextsMenu[i].setTextColor(getResources().getColor(mCurrentMainFragmentBg));

							startSlideAnimation(mImageMenuBack, -1000, 0, 0, 0);
							mImageMenuBack.setVisibility(View.VISIBLE);

							if (mCurrentControllerTopMenu.get(i).getSubMenu() == null) {
								startSlideAnimation(mTextsMenu[i], -mImageMenuBack.getWidth(), 0, 0, 0);
								mTextsMenu[i].setVisibility(View.VISIBLE);
							} else {
								mTextsMenu[i].setVisibility(View.GONE);
								startSlideAnimation(mSpinnerSubmenu, -mImageMenuBack.getWidth(), 0, 0, 0);
								mSpinnerSubmenu.setVisibility(View.VISIBLE);
								mControllerTopSpinnerAdapter = new ControllerTopSpinnerAdapter(mCurrentControllerTopMenu.get(i).getSubMenu(), HostActivity.this);
								mSpinnerSubmenu.setAdapter(mControllerTopSpinnerAdapter);
							}

							if (mCurrentControllerTopMenu.get(i).getMenuItemAction() != null) {
								startSlideAnimation(mTextAction, mLayoutControllerTopMenu.getWidth(), 0, 0, 0);
								mTextAction.setText(mCurrentControllerTopMenu.get(i).getMenuItemAction());
								mTextAction.setVisibility(View.VISIBLE);
							} else {
								mTextAction.setVisibility(View.GONE);
							}
						} else {
							mCurrentControllerTopMenu.get(i).setIsActive(false);
							startSlideAnimation(mTextsMenu[i], 0, mLayoutControllerTopMenu.getWidth(), 0, 0);
							mTextsMenu[i].setVisibility(View.GONE);
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onMenuItemClick Exception : " + e.toString());
		}
	}

	private void hideControllerTopControls() {
		if (mImageMenuBack.getVisibility() == View.VISIBLE) {
			hideControllerTopBackButton();
		}
		if (mTextAction.getVisibility() == View.VISIBLE) {
			hideControllerTopAction();
		}
		if (mSpinnerSubmenu.getVisibility() == View.VISIBLE) {
			hideControllerTopSpinner();
		}
	}

	private void hideControllerTopBackButton() {
		startSlideAnimation(mImageMenuBack, 0, -1000, 0, 0);
		mImageMenuBack.setVisibility(View.GONE);
	}

	private void hideControllerTopAction() {
		startSlideAnimation(mTextAction, 0, mLayoutControllerTopMenu.getWidth(), 0, 0);
		mTextAction.setText("");
		mTextAction.setVisibility(View.GONE);
	}

	private void hideControllerTopSpinner() {
		mSpinnerSubmenu.setAdapter(null);
		startSlideAnimation(mSpinnerSubmenu, 0, -1000, 0, 0);
		mSpinnerSubmenu.setVisibility(View.GONE);
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
