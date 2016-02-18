package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.adapter.DiscussionAdapter;
import com.ism.assistantwebview.view.AssistantWebView;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.scientificcalc.view.Calc;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.whiteboard.view.Whiteboard;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Discussion;
import com.ism.ws.model.GroupDiscussionData;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.socketdemo.c85.ismsocket.IOSocketHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



import io.realm.RealmResults;
import isminterface.OnSocketResponse;
import model.Subjects;
import model.TutorialGroupDiscussion;
import model.TutorialGroupTopicAllocation;
import model.TutorialTopic;
import model.User;

import realmhelper.StudentHelper;
import utils.SocketConstants;

/**
 * Created by c161 on 12/10/15.
 */
public class TutorialDiscussionFragment extends Fragment implements WebserviceWrapper.WebserviceResponse,OnSocketResponse {


	private static final String TAG = TutorialDiscussionFragment.class.getSimpleName();

	private View view;
	private TextView txtTopic, txtTopicValue, txtAlert, txtAdminNoticeTime, txtAdminNotice;
	private ImageView imgCalc, imgWhiteboard, imgSearch, imgDictionary, imgExpandDiscussion;
	private CircleImageView imgAdminDp;
	private RelativeLayout rlUtility, rlAdminNotice;
	private ViewStub vsWhiteboard, vsAssistantWebView;
	private Calc utilitySciCalc;
	private Whiteboard utilityWhiteboard;
	private AssistantWebView utilityAssistantWebView;
	private RecyclerView recyclerChat;
	private EditText etMessage;
	private Button btnSend;

	private ImageView[] imgUtilities;
	private View[] viewUtilities;

	private TutorialDiscussionFragmentListener listenerTutorialDiscussion;
	private Whiteboard.WhiteboardListener whiteboardListener;
	private View.OnClickListener listenerOnUtilityClick;
	private ArrayList<TutorialGroupDiscussion> arrListTestDiscussion = new ArrayList<TutorialGroupDiscussion>();
	//private ArrayList<TutorialGroupDiscussion> arrListDiscussionData = new ArrayList<TutorialGroupDiscussion>();;
	private ArrayList<Discussion> arrListDiscussion;
	private DiscussionAdapter adpDiscussion;
	private LinearLayoutManager layoutManagerChat;

	private static final String ARG_WEEK_DAY = "weekDay";
	private static final int UTILITY_CALC = 0;
	private static final int UTILITY_WHITEBOARD = 1;
	private static final int UTILITY_SEARCH = 2;
	private static final int UTILITY_DICTIONARY = 3;
	private static int currentUtility = -1;
	private static int currentWebUtility = -1;
	private int intWeekDay;
	private String strCurrentWeekDay = "";
    private String weekDay;
	private TutorialGroupTopicAllocation tutorialGroupTopicAllocationDetail;

  	public static TutorialDiscussionFragment newInstance(int weekDay) {
		TutorialDiscussionFragment fragment = new TutorialDiscussionFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_WEEK_DAY, weekDay);
		fragment.setArguments(args);

		return fragment;
	}

	public TutorialDiscussionFragment() {
		// Required empty public constructor
	}



    public interface TutorialDiscussionFragmentListener {
		public void onDayChanged(int dayId);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (getArguments() != null) {
//
//			intWeekDay = getArguments().getInt(ARG_WEEK_DAY);
//		}


		Calendar calendar = Calendar.getInstance();
		intWeekDay = calendar.get(Calendar.DAY_OF_WEEK);
//		if(intWeekDay == 1){
//			intWeekDay =6;
//		}
//		else{
//			intWeekDay =intWeekDay-2;
//		}
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_discussion, container, false);

		initGlobal();

		return view;
	}

	private int count = 0;
	private void initGlobal() {
      IOSocketHandler.setOnSocketResponse(this);
		txtTopic = (TextView) view.findViewById(R.id.txt_topic);
		txtTopicValue = (TextView) view.findViewById(R.id.txt_topic_value);
		txtAlert = (TextView) view.findViewById(R.id.txt_alert);
		recyclerChat = (RecyclerView) view.findViewById(R.id.recycler_chat);
		imgCalc = (ImageView) view.findViewById(R.id.img_utility_calc);
		imgWhiteboard = (ImageView) view.findViewById(R.id.img_utility_whiteboard);
		imgSearch = (ImageView) view.findViewById(R.id.img_utility_search);
		imgDictionary = (ImageView) view.findViewById(R.id.img_utility_dictionary);
		utilitySciCalc = (Calc) view.findViewById(R.id.utility_scientific_calc);
		vsWhiteboard = (ViewStub) view.findViewById(R.id.vs_whiteboard);
		vsAssistantWebView = (ViewStub) view.findViewById(R.id.vs_assistant_webview);
		etMessage = (EditText) view.findViewById(R.id.et_message);
		btnSend = (Button) view.findViewById(R.id.btn_send);
		imgExpandDiscussion = (ImageView) view.findViewById(R.id.img_expand_discussion);
		rlUtility = (RelativeLayout) view.findViewById(R.id.rl_utility);
		imgAdminDp = (CircleImageView) view.findViewById(R.id.img_admin_dp);
		txtAdminNoticeTime = (TextView) view.findViewById(R.id.txt_admin_notice_time);
		txtAdminNotice = (TextView) view.findViewById(R.id.txt_admin_notice);
		rlAdminNotice = (RelativeLayout) view.findViewById(R.id.rl_admin_notice);

		imgUtilities = new ImageView[]{imgCalc, imgWhiteboard, imgSearch, imgDictionary};
		viewUtilities = new View[]{utilitySciCalc, utilityWhiteboard, utilityAssistantWebView};

		txtTopic.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtTopicValue.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtAlert.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtAdminNoticeTime.setTypeface(Global.myTypeFace.getRalewayThinItalic());
		txtAdminNotice.setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_admin_name)).setTypeface(Global.myTypeFace.getRalewayRegular());
		getGroupDiscussion();
		callApiGetGroupHistory();

		imgCalc.setEnabled(false);

		imgExpandDiscussion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rlUtility.setVisibility(rlUtility.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
			}
		});

		listenerOnUtilityClick = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.img_utility_calc:
						currentUtility = UTILITY_CALC;
						break;
					case R.id.img_utility_whiteboard:
						currentUtility = UTILITY_WHITEBOARD;
						break;
					case R.id.img_utility_search:
						currentUtility = UTILITY_SEARCH;
						break;
					case R.id.img_utility_dictionary:
						currentUtility = UTILITY_DICTIONARY;
						break;
				}
				showUtility(currentUtility);
			}
		};

		whiteboardListener = new Whiteboard.WhiteboardListener() {

            @Override
            public void onSendImageClick(Bitmap bitmap) {

            }
        };

		imgCalc.setOnClickListener(listenerOnUtilityClick);
		imgWhiteboard.setOnClickListener(listenerOnUtilityClick);
		imgSearch.setOnClickListener(listenerOnUtilityClick);
		imgDictionary.setOnClickListener(listenerOnUtilityClick);

		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (etMessage.getText() != null && etMessage.getText().toString().length() > 0) {
					count ++;
//					TestDiscussion testDiscussion = new TestDiscussion();
//					testDiscussion.setMessage(etMessage.getText().toString().trim());
//					testDiscussion.setTime("Aug 5, 2015  4:15pm");
//					testDiscussion.setUserName(Global.strFullName);


					/*** new one ***/
				 	StudentHelper studentHelper = new StudentHelper(getActivity());
					TutorialGroupDiscussion tutorialGroupDiscussion = new TutorialGroupDiscussion();
					tutorialGroupDiscussion.setMessage(etMessage.getText().toString().trim());
					tutorialGroupDiscussion.setCommentScore(2);
					tutorialGroupDiscussion.setInActiveHours(true);
					tutorialGroupDiscussion.setCreatedDate(new Date());
					//tutorialGroupDiscussion.setShowDetails(true);
					//if(tutorialGroupTopicAllocationDetail == null){
					TutorialGroupTopicAllocation tutorialGroupTopicAllocation = studentHelper.getTutorialTopicAllocationByDayOfWeek(String.valueOf(intWeekDay-1));
					//}
					tutorialGroupDiscussion.setTopic(tutorialGroupTopicAllocation.getTutorialTopic());

					User user = studentHelper.getUser(Integer.parseInt(Global.strUserId));
					tutorialGroupDiscussion.setSender(user);

					/*** new one ***/

					arrListTestDiscussion.add(0,tutorialGroupDiscussion);

					if(1 == arrListTestDiscussion.size()){
						tutorialGroupDiscussion.setShowDetails(true);
					}
					else if(arrListTestDiscussion.get(1).getSender().getUserId() != tutorialGroupDiscussion.getSender().getUserId()) {
						tutorialGroupDiscussion.setShowDetails(true);
					}

					adpDiscussion.notifyDataSetChanged();
					layoutManagerChat.smoothScrollToPosition(recyclerChat, null, 0);
					etMessage.setText("");

                    sendTutorialMessage(tutorialGroupDiscussion);

                    studentHelper.saveTutorialGroupDiscussion(tutorialGroupDiscussion);

                    tutorialGroupDiscussion = null;

				}
			}
		});

		layoutManagerChat = new LinearLayoutManager(getActivity());
		layoutManagerChat.setReverseLayout(true);
		recyclerChat.setLayoutManager(layoutManagerChat);
		RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				if (parent.getChildLayoutPosition(view) == 0) {
					outRect.bottom = 20;
				}
				outRect.top = 20;
				outRect.left = 20;
				outRect.right = 20;
			}
		};
		recyclerChat.addItemDecoration(decoration);

		recyclerChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int lastPosition = layoutManagerChat.findLastCompletelyVisibleItemPosition();
				if (lastPosition != 0
						&& lastPosition != RecyclerView.NO_POSITION
						&& !arrListTestDiscussion.get(layoutManagerChat.findLastCompletelyVisibleItemPosition()).getTopic().getTopicDay()
						.equals(strCurrentWeekDay)) {
					strCurrentWeekDay = arrListTestDiscussion.get(layoutManagerChat.findLastCompletelyVisibleItemPosition()).getTopic().getTopicDay();
					showTopicDetails(arrListTestDiscussion.get(layoutManagerChat.findLastCompletelyVisibleItemPosition()).getTopicPosition());
					Log.e(TAG, "day changed to : " + strCurrentWeekDay);
				}
			}
		});

		txtAdminNotice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (txtAdminNotice.getMaxLines() == 2) {
					txtAdminNotice.setMaxLines(Integer.MAX_VALUE);
				} else {
					txtAdminNotice.setMaxLines(2);
				}
			}
		});

//    private static final String TAG = TutorialDiscussionFragment.class.getSimpleName();
//
//    private View view;
//    private TextView txtTopic, txtTopicValue, txtAlert, txtAdminNoticeTime, txtAdminNotice, txtEmptyListMsg;
//    private ImageView imgCalc, imgWhiteboard, imgSearch, imgDictionary, imgExpandDiscussion;
//    private CircleImageView imgAdminDp;
//    private RelativeLayout rlUtility, rlAdminNotice;
//    private ViewStub vsWhiteboard, vsAssistantWebView;
//    private Calc utilitySciCalc;
//    private Whiteboard utilityWhiteboard;
//    private AssistantWebView utilityAssistantWebView;
//    private RecyclerView recyclerChat;
//    private EditText etMessage;
//    private Button btnSend;
//
//    private ImageView[] imgUtilities;
//
//    private TutorialDiscussionFragmentListener listenerTutorialDiscussion;
//    private Whiteboard.WhiteboardListener whiteboardListener;
//    private View.OnClickListener listenerOnUtilityClick;
//    private ArrayList<GroupDiscussionData> arrListDiscussionData;
//    private ArrayList<Discussion> arrListDiscussion;
//    private DiscussionAdapter adpDiscussion;
//    private LinearLayoutManager layoutManagerChat;
//    private StudentHelper studentHelper;
//
//    private static final String ARG_WEEK_DAY = "weekDay";
//    private static final int UTILITY_CALC = 0;
//    private static final int UTILITY_WHITEBOARD = 1;
//    private static final int UTILITY_SEARCH = 2;
//    private static final int UTILITY_DICTIONARY = 3;
//    private static int currentUtility = -1;
//    private static int currentWebUtility = -1;
//    private int intWeekDay;
//    private String strCurrentWeekDay = "";
//
//    public static TutorialDiscussionFragment newInstance(int weekDay) {
//        TutorialDiscussionFragment fragment = new TutorialDiscussionFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_WEEK_DAY, weekDay);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public TutorialDiscussionFragment() {
//        // Required empty public constructor
//    }
//
//    public interface TutorialDiscussionFragmentListener {
//        public void onDayChanged(int dayId);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            intWeekDay = getArguments().getInt(ARG_WEEK_DAY);
//        }
//        Log.e(TAG, "Week day : " + intWeekDay);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_tutorial_discussion, container, false);
//
//        initGlobal();
//
//        return view;
//    }
//
//    private void initGlobal() {
//        txtTopic = (TextView) view.findViewById(R.id.txt_topic);
//        txtTopicValue = (TextView) view.findViewById(R.id.txt_topic_value);
//        txtAlert = (TextView) view.findViewById(R.id.txt_alert);
//        txtEmptyListMsg = (TextView) view.findViewById(R.id.txt_empty_list_msg);
//        recyclerChat = (RecyclerView) view.findViewById(R.id.recycler_chat);
//        imgCalc = (ImageView) view.findViewById(R.id.img_utility_calc);
//        imgWhiteboard = (ImageView) view.findViewById(R.id.img_utility_whiteboard);
//        imgSearch = (ImageView) view.findViewById(R.id.img_utility_search);
//        imgDictionary = (ImageView) view.findViewById(R.id.img_utility_dictionary);
//        utilitySciCalc = (Calc) view.findViewById(R.id.utility_scientific_calc);
//        vsWhiteboard = (ViewStub) view.findViewById(R.id.vs_whiteboard);
//        vsAssistantWebView = (ViewStub) view.findViewById(R.id.vs_assistant_webview);
//        etMessage = (EditText) view.findViewById(R.id.et_message);
//        btnSend = (Button) view.findViewById(R.id.btn_send);
//        imgExpandDiscussion = (ImageView) view.findViewById(R.id.img_expand_discussion);
//        rlUtility = (RelativeLayout) view.findViewById(R.id.rl_utility);
//        imgAdminDp = (CircleImageView) view.findViewById(R.id.img_admin_dp);
//        txtAdminNoticeTime = (TextView) view.findViewById(R.id.txt_admin_notice_time);
//        txtAdminNotice = (TextView) view.findViewById(R.id.txt_admin_notice);
//        rlAdminNotice = (RelativeLayout) view.findViewById(R.id.rl_admin_notice);
//
//        studentHelper = new StudentHelper(getActivity());
//
//        imgUtilities = new ImageView[]{imgCalc, imgWhiteboard, imgSearch, imgDictionary};
//
//        txtTopic.setTypeface(Global.myTypeFace.getRalewayRegular());
//        txtTopicValue.setTypeface(Global.myTypeFace.getRalewaySemiBold());
//        txtAlert.setTypeface(Global.myTypeFace.getRalewayRegular());
//        txtAdminNoticeTime.setTypeface(Global.myTypeFace.getRalewayThinItalic());
//        txtAdminNotice.setTypeface(Global.myTypeFace.getRalewayRegular());
//        txtEmptyListMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
//        ((TextView) view.findViewById(R.id.txt_admin_name)).setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        callApiGetGroupHistory();
//
//        imgCalc.setEnabled(false);
//
//        imgExpandDiscussion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rlUtility.setVisibility(rlUtility.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//            }
//        });
//
//        listenerOnUtilityClick = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.img_utility_calc:
//                        currentUtility = UTILITY_CALC;
//                        break;
//                    case R.id.img_utility_whiteboard:
//                        currentUtility = UTILITY_WHITEBOARD;
//                        break;
//                    case R.id.img_utility_search:
//                        currentUtility = UTILITY_SEARCH;
//                        break;
//                    case R.id.img_utility_dictionary:
//                        currentUtility = UTILITY_DICTIONARY;
//                        break;
//                }
//                showUtility(currentUtility);
//            }
//        };
//
//        whiteboardListener = new Whiteboard.WhiteboardListener() {
//            @Override
//            public void onSendImageClick(Bitmap bitmap) {
//                Log.e(TAG, "onSendImageClick");
//            }
//        };
//
//        imgCalc.setOnClickListener(listenerOnUtilityClick);
//        imgWhiteboard.setOnClickListener(listenerOnUtilityClick);
//        imgSearch.setOnClickListener(listenerOnUtilityClick);
//        imgDictionary.setOnClickListener(listenerOnUtilityClick);
//
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*if (etMessage.getText() != null && etMessage.getText().toString().length() > 0) {
//					TestDiscussion testDiscussion = new TestDiscussion();
//					testDiscussion.setMessage(etMessage.getText().toString().trim());
//					testDiscussion.setTime("Aug 5, 2015  4:15pm");
//					testDiscussion.setUserName(Global.strFullName);
//					arrListTestDiscussion.add(0, testDiscussion);
//					adpDiscussion.notifyDataSetChanged();
//					layoutManagerChat.smoothScrollToPosition(recyclerChat, null, 0);
//					etMessage.setText("");
//				}*/
//            }
//        });
//
//        layoutManagerChat = new LinearLayoutManager(getActivity());
//        layoutManagerChat.setReverseLayout(true);
//        recyclerChat.setLayoutManager(layoutManagerChat);
//        RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                if (parent.getChildLayoutPosition(view) == 0) {
//                    outRect.bottom = 20;
//                }
//                outRect.top = 20;
//                outRect.left = 20;
//                outRect.right = 20;
//            }
//        };
//        recyclerChat.addItemDecoration(decoration);
//
//        recyclerChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastItemPosition = layoutManagerChat.findLastCompletelyVisibleItemPosition();
//                if (layoutManagerChat.findFirstCompletelyVisibleItemPosition() != 0
//                        && lastItemPosition != RecyclerView.NO_POSITION
//                        && !arrListDiscussion.get(lastItemPosition).getWeekDay()
//                        .equals(strCurrentWeekDay)) {
//                    strCurrentWeekDay = arrListDiscussion.get(layoutManagerChat.findLastCompletelyVisibleItemPosition()).getWeekDay();
//                    showTopicDetails(arrListDiscussion.get(layoutManagerChat.findLastCompletelyVisibleItemPosition()).getTopicPosition());
//                    Log.e(TAG, "day changed to : " + strCurrentWeekDay);
//
//                }
//            }
//        });
//
//        txtAdminNotice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (txtAdminNotice.getMaxLines() == 2) {
//                    txtAdminNotice.setMaxLines(Integer.MAX_VALUE);
//                } else {
//                    txtAdminNotice.setMaxLines(2);
//                }
//            }
//        });
//>>>>>>> adddedd0f52cebbf2708517d26e06ea1e3c5b94b

		/*arrListTestDiscussion = new ArrayList<>();
		String time1 = "Aug 5, 2015  4:15pm";
		String time2 = "Nov 25, 2015  7:10pm";
		String time = time1;
		for (int i = 0; i < 30; i++) {
			TestDiscussion testDiscussion = new TestDiscussion();
			if (i % 5 == 0) {
				time = time.equals(time1) ? time2 : time1;
				testDiscussion.setMessage("K");
			} else {
				testDiscussion.setMessage("Types of solutions, expression of concentration of solutions of solids in liquids, solubility of gases in liquids, solid solutions, colligative properties - relative lowering of vapour pressure, Raoult&#039;s law, elevation of boiling point, depression of freezing point, osmotic pressure, determination of molecular masses using colligative properties, abnormal molecular mass, Van&#039;t Hoff factor.");
			}
			testDiscussion.setTime(time);
			testDiscussion.setUserName("Albert Crowley " + i);
			arrListTestDiscussion.add(testDiscussion);
		}
		adpDiscussion = new DiscussionAdapter(getActivity(), arrListTestDiscussion);
		recyclerChat.setAdapter(adpDiscussion);*/
    }


	private void getGroupDiscussion(){

    StudentHelper studentHelper = new StudentHelper(getActivity());
		RealmResults<TutorialGroupDiscussion> tutorialGroupDiscussions = studentHelper.getTutorialGroupDiscussionByGroup(135);

		if(tutorialGroupDiscussions != null){
		int j = 0;
			int i =0;
			int topicId =0;
        for(TutorialGroupDiscussion tutorialGroupDiscussion : tutorialGroupDiscussions) {
           j++;


			if(j == tutorialGroupDiscussions.size()){
				tutorialGroupDiscussion.setShowDetails(true);
			}
			else if(tutorialGroupDiscussions.get(j).getSender().getUserId() != tutorialGroupDiscussion.getSender().getUserId()){
				tutorialGroupDiscussion.setShowDetails(true);
			}


			if(topicId == 0) {
				tutorialGroupDiscussion.setTopicPosition(i);
				i++;
			}
			else if(topicId != tutorialGroupDiscussion.getTopic().getTutorialTopicId()){
				tutorialGroupDiscussion.setTopicPosition(i++);
			}
			arrListTestDiscussion.add(tutorialGroupDiscussion);
			topicId = tutorialGroupDiscussion.getTopic().getTutorialTopicId();
		}

				if(adpDiscussion == null) {
					adpDiscussion = new DiscussionAdapter(getActivity(), arrListTestDiscussion);
					recyclerChat.setAdapter(adpDiscussion);
				}
		}

	}
	private void callApiGetGroupHistory() {
		try {
			//getLastDate();
			Attribute attribute = new Attribute();

			attribute.setGroupId("135");
			attribute.setWeekNo("1");
			attribute.setDayNo("");
            attribute.setUserId(Global.strUserId);
            //attribute.setFromDate(getLastDate());

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_GROUP_HISTORY);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetGroupHistory Exception : " + e.toString());
		}
	}

	/**
	 * get last updated date for tutorial discussion
	 * @return return string date
	 */
	private String getLastDate(){
		try {
			String strDate = "";
			StudentHelper studentHelper = new StudentHelper(getActivity());
			User user = studentHelper.getUser(Integer.parseInt(Global.strUserId));
           if(user.getCreatedDate() == null){

			   Date date = null;
			   strDate = Utility.getDateTime(date,Utility.DATE_FORMAT_YYYYMMDDHHMMSS);
			   studentHelper.getRealm().beginTransaction();
			   user.setCreatedDate(Utility.getDateTime(strDate,Utility.DATE_FORMAT_YYYYMMDDHHMMSS));
			   studentHelper.getRealm().commitTransaction();
			   studentHelper.saveUser(user);

			   strDate = "";

		   }
			else {
			   strDate = Utility.getDateTime(user.getCreatedDate(),Utility.DATE_FORMAT_YYYYMMDDHHMMSS);

			   Date date = null;
			   studentHelper.getRealm().beginTransaction();
			   user.setCreatedDate(Utility.getDateTime(Utility.getDateTime(date,Utility.DATE_FORMAT_YYYYMMDDHHMMSS),Utility.DATE_FORMAT_YYYYMMDDHHMMSS));
			   studentHelper.getRealm().commitTransaction();
			   studentHelper.saveUser(user);

		   }

			return strDate;

		}
		catch (Exception e){
			Log.e(TAG,"error "+e.getLocalizedMessage());
		}

		return "";


	}

	private void showUtility(int selectedUtilityId) {

		for (int utilityId = 0; utilityId < imgUtilities.length; utilityId++) {

			if (utilityId != selectedUtilityId) {
				imgUtilities[utilityId].setEnabled(true);

				switch (utilityId) {
					case UTILITY_CALC:
						utilitySciCalc.setVisibility(View.GONE);
						break;
					case UTILITY_WHITEBOARD:
						if (utilityWhiteboard != null) {
							utilityWhiteboard.setVisibility(View.GONE);
						}
						break;
				}
			} else {
				imgUtilities[utilityId].setEnabled(false);

				switch (utilityId) {
					case UTILITY_CALC:
						utilitySciCalc.setVisibility(View.VISIBLE);
						break;
					case UTILITY_WHITEBOARD:
						if (utilityWhiteboard != null) {
							utilityWhiteboard.setVisibility(View.VISIBLE);
						} else {
							utilityWhiteboard = (Whiteboard) vsWhiteboard.inflate();
							utilityWhiteboard.setWhiteboardListener(whiteboardListener);
						}
						break;
				}
			}
		}

		if (selectedUtilityId == UTILITY_SEARCH || selectedUtilityId == UTILITY_DICTIONARY) {
			if (utilityAssistantWebView != null) {
				utilityAssistantWebView.setVisibility(View.VISIBLE);
				if (currentWebUtility != selectedUtilityId) {
					loadUtilityUrl(selectedUtilityId);
				}
			} else {
				utilityAssistantWebView = (AssistantWebView) vsAssistantWebView.inflate();
				loadUtilityUrl(selectedUtilityId);
			}
			currentWebUtility = selectedUtilityId;
		} else {
			if (utilityAssistantWebView != null) {
				utilityAssistantWebView.setVisibility(View.GONE);
			}
		}
	}

	private void loadUtilityUrl(int selectedUtilityId) {
		if (selectedUtilityId == UTILITY_SEARCH) {
			utilityAssistantWebView.loadUrl("http://google.com");
		} else {
			utilityAssistantWebView.loadUrl("http://dictionary.com");
		}
	}

	public void setTutorialDiscussionListener(TutorialDiscussionFragmentListener listenerTutorialDiscussion) {
		this.listenerTutorialDiscussion = listenerTutorialDiscussion;
	}

	public void setDay(int day) {
		intWeekDay = day;


		if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != day) {  // if day is not today
			switch (intWeekDay) {
				case Calendar.MONDAY:
					showDiscussionFor(WebConstants.MONDAY);
					break;
				case Calendar.TUESDAY:
					showDiscussionFor(WebConstants.TUESDAY);
					break;
				case Calendar.WEDNESDAY:
					showDiscussionFor(WebConstants.WEDNESDAY);
					break;
				case Calendar.THURSDAY:
					showDiscussionFor(WebConstants.THURSDAY);
					break;
			}
		} else {
			recyclerChat.scrollToPosition(0);
			strCurrentWeekDay = arrListTestDiscussion.get(0).getTopic().getTopicDay();
			showTopicDetails(0);

		}
	}

	private void showDiscussionFor(String weekDay) {
		this.weekDay = weekDay;
		for (int i = 0; i < arrListTestDiscussion.size(); i++) {
			if (arrListTestDiscussion.get(i).getTopic().getTopicDay().equals(weekDay)
					&& (i == arrListTestDiscussion.size() - 1 || !arrListTestDiscussion.get(i + 1).getTopic().getTopicDay().equals(weekDay))) {
				if (i != arrListTestDiscussion.size() - 1) {
					((LinearLayoutManager) recyclerChat.getLayoutManager()).scrollToPositionWithOffset(i + 1, recyclerChat.getHeight());
				} else {
					recyclerChat.scrollToPosition(i);
				}
				break;
			}
		}

		/**
		 * Show topic details when no discussion done.
		 */
		if (arrListDiscussion == null || arrListDiscussion.size() == 0) {
			for (int i = 0; i < arrListTestDiscussion.size(); i++) {
				if (arrListTestDiscussion.get(i).getTopic().getTopicDay().equals(weekDay)) {
					strCurrentWeekDay = weekDay;
					showTopicDetails(i);
					break;
				}
			}
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_GROUP_HISTORY:
					onResponseGetGroupHistory(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetGroupHistory(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;


                saveGroupDiscussion(responseHandler);

//				arrListDiscussionData = responseHandler.getGroupDiscussionData();
//				arrListDiscussion = new ArrayList<>();
//
//				// Discussion previousDiscussion=null;
//				for (int i = 0; i < arrListDiscussionData.size(); i++) {
//					int j = 0;
//					//String userId ="0";
//					for (Discussion discussion : arrListDiscussionData.get(i).getDiscussion()) {
//
//						j++;
//						discussion.setWeekDay(arrListDiscussionData.get(i).getDayName());
//						discussion.setTopicPosition(i);
//
//                        if(j == arrListDiscussionData.get(i).getDiscussion().size()){
//							discussion.setShowDetails(true);
//						}
//						else if(!arrListDiscussionData.get(i).getDiscussion().get(j).getUserId().equalsIgnoreCase(discussion.getUserId())){
//							discussion.setShowDetails(true);
//						}
//
//						arrListDiscussion.add(discussion);
//
//					}
//				}


				adpDiscussion = new DiscussionAdapter(getActivity(), arrListTestDiscussion);
				recyclerChat.setAdapter(adpDiscussion);
				setDay(intWeekDay);
			} else if (error != null) {
				Log.e(TAG, "onResponseGetGroupHistory api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetGroupHistory Exception : " + e.toString());
		}
	}

	/**
	 * save or update<br/>
	 * 1){@link TutorialTopic}<br/>
	 * 2){@link TutorialGroupDiscussion}<br/>
	 * 3){@link TutorialGroupTopicAllocation}<br/>
	 * 4){@link User} - topic created by<br/>
	 * 5){@link User} - sender in discussion<br/>
	 * 6){@link Subjects} - subject for  {@link TutorialTopic}<br/>
	 *
	 * @param response - Group Discussion Tutorial response object
	 */
	private void saveGroupDiscussion(ResponseHandler response){

		try {
			if(response!=null) {

				int i = 0;
				for (GroupDiscussionData groupDiscussionData : response.getGroupDiscussionData()) {
                    boolean isTopicSaved = false;
					StudentHelper studentHelper = new StudentHelper(getActivity());
					TutorialTopic tutorialTopic = studentHelper.getTutorialTopic(Integer.parseInt(groupDiscussionData.getTutorialTopicId()));

					if(tutorialTopic == null){

						tutorialTopic = new TutorialTopic();
						tutorialTopic.setTutorialTopicId(Integer.parseInt(groupDiscussionData.getTutorialTopicId()));
						tutorialTopic.setTopicName(groupDiscussionData.getTutorialTopic());
						tutorialTopic.setTopicDescription(groupDiscussionData.getTopicDescription());

						tutorialTopic.setTopicDay(groupDiscussionData.getDayName());


                        // add subject
                        Subjects subject = studentHelper.getSubject(Integer.parseInt(groupDiscussionData.getSubjectId()));
						if(subject == null){
							subject = new Subjects();
							subject.setSubjectId(Integer.parseInt(groupDiscussionData.getSubjectId()));
							subject.setSubjectName(groupDiscussionData.getSubjectName());
 							studentHelper.saveSubject(subject);
						}
						tutorialTopic.setSubject(subject);

						User createdBy = studentHelper.getUser(Integer.parseInt(groupDiscussionData.getAssignedBy()));
						if(createdBy == null){
							createdBy = new User();
								createdBy.setUserId(Integer.parseInt(groupDiscussionData.getAssignedBy()));
							studentHelper.saveUser(createdBy);
						}

						tutorialTopic.setCreatedBy(createdBy);

						int j = 0;
						for(Discussion discussion : groupDiscussionData.getDiscussion()){
							j++;
							TutorialGroupDiscussion tutorialGroupDiscussion = studentHelper.getTutorialGroupDiscussion(Integer.parseInt(discussion.getTutorialGroupDiscussionId()));

							if(tutorialGroupDiscussion == null){

								tutorialGroupDiscussion = new TutorialGroupDiscussion();
								tutorialGroupDiscussion.setTutorialGroupDiscussionId(Integer.parseInt(discussion.getTutorialGroupDiscussionId()));
								tutorialGroupDiscussion.setLocalId(Integer.parseInt(discussion.getTutorialGroupDiscussionId()));
								tutorialGroupDiscussion.setMessage(discussion.getComment());
								//add sender detail

								tutorialGroupDiscussion.setCreatedDate(Utility.getDateTime(discussion.getCommentTimestamp(),Utility.DATE_FORMAT_MMMDDYY_HHMMA));
								tutorialGroupDiscussion.setMessageType(discussion.getMessageType());
								tutorialGroupDiscussion.setMediaLink(discussion.getMediaLink());
								User user = studentHelper.getUser(Integer.parseInt(discussion.getUserId()));
								if(user == null){
									user = new User();
									user.setFullName(discussion.getFullName());
									user.setProfilePicture(discussion.getProfilePic());
									user.setUserId(Integer.parseInt(discussion.getUserId()));
									studentHelper.saveUser(user);
								}

								tutorialGroupDiscussion.setSender(user);
								tutorialGroupDiscussion.setTopic(tutorialTopic);
								studentHelper.saveTutorialGroupDiscussion(tutorialGroupDiscussion);
							}




//							if(j == tutorialGroupDiscussions.size()){
//								tutorialGroupDiscussion.setShowDetails(true);
//							}
//							else if(tutorialGroupDiscussions.get(j).getSender().getUserId() != tutorialGroupDiscussion.getSender().getUserId()){
//								tutorialGroupDiscussion.setShowDetails(true);
//							}
							tutorialGroupDiscussion.setTopicPosition(i);
							arrListTestDiscussion.add(tutorialGroupDiscussion);

							if(j == arrListTestDiscussion.size()){
								tutorialGroupDiscussion.setShowDetails(true);
							}
							else if(arrListTestDiscussion.get(j).getSender().getUserId() != tutorialGroupDiscussion.getSender().getUserId()) {
								tutorialGroupDiscussion.setShowDetails(true);
							}

						}

					}
					else{
                        isTopicSaved = true;
						int j = 0;
						for(Discussion discussion : groupDiscussionData.getDiscussion()){
							j++;
							TutorialGroupDiscussion tutorialGroupDiscussion = studentHelper.getTutorialGroupDiscussion(Integer.parseInt(discussion.getTutorialGroupDiscussionId()));

							if(tutorialGroupDiscussion == null){
								tutorialGroupDiscussion = new TutorialGroupDiscussion();
								tutorialGroupDiscussion.setTutorialGroupDiscussionId(Integer.parseInt(discussion.getTutorialGroupDiscussionId()));
								tutorialGroupDiscussion.setLocalId(Integer.parseInt(discussion.getTutorialGroupDiscussionId()));
								tutorialGroupDiscussion.setMessage(discussion.getComment());

								tutorialGroupDiscussion.setCreatedDate(Utility.getDateTime(discussion.getCommentTimestamp(),Utility.DATE_FORMAT_MMMDDYY_HHMMA));
								tutorialGroupDiscussion.setMessageType(discussion.getMessageType());
								tutorialGroupDiscussion.setMediaLink(discussion.getMediaLink());
								User user = studentHelper.getUser(Integer.parseInt(discussion.getUserId()));
								if(user == null){
									user = new User();
									user.setFullName(discussion.getFullName());
									user.setProfilePicture(discussion.getProfilePic());
									user.setUserId(Integer.parseInt(discussion.getUserId()));
									studentHelper.saveUser(user);
								}

								tutorialGroupDiscussion.setSender(user);
								tutorialGroupDiscussion.setTopic(tutorialTopic);
								studentHelper.saveTutorialGroupDiscussion(tutorialGroupDiscussion);
							}

							if(j == groupDiscussionData.getDiscussion().size()){
								tutorialGroupDiscussion.setShowDetails(true);
							}
							else if(!groupDiscussionData.getDiscussion().get(j).getUserId().equalsIgnoreCase(discussion.getUserId())){
								tutorialGroupDiscussion.setShowDetails(true);
							}

							tutorialGroupDiscussion.setTopicPosition(i);
							arrListTestDiscussion.add(tutorialGroupDiscussion);


						}
					}

					TutorialGroupTopicAllocation tutorialGroupTopicAllocation = studentHelper.getTutorialTopicAllocation(Integer.parseInt(groupDiscussionData.getTutorialTopicAllocationId()));
					if(tutorialGroupTopicAllocation == null){
						tutorialGroupTopicAllocation = new TutorialGroupTopicAllocation();
						tutorialGroupTopicAllocation.setTutorialGroupTopicId(Integer.parseInt(groupDiscussionData.getTutorialTopicAllocationId()));
						tutorialGroupTopicAllocation.setTutorialTopic(tutorialTopic);
						tutorialGroupTopicAllocation.setWeekNumber(Integer.parseInt(groupDiscussionData.getWeekNumber()));
						tutorialGroupTopicAllocation.setDateDay(groupDiscussionData.getWeekDay());
						tutorialGroupTopicAllocation.setCreatedDate(Utility.getDateTime(groupDiscussionData.getAssignedTime(),Utility.DATE_FORMAT_MY_SQL));
						studentHelper.saveTutorialGroupTopicAllocation(tutorialGroupTopicAllocation);
					}

                    if(!isTopicSaved) {
                        tutorialTopic.setParent(tutorialGroupTopicAllocation);

                        studentHelper.saveTutorialTopic(tutorialTopic);
                    }

					i++;
				}
				}
		}
		catch (Exception error){
			Log.e(TAG,"error -> saveGroupDiscussion"+error.getLocalizedMessage());
		}


	}


	private void showTopicDetails(int topicPosition) {
			StudentHelper studentHelper = new StudentHelper(getActivity());
		txtAdminNoticeTime.setText(com.ism.commonsource.utility.Utility.getTimeDuration(arrListTestDiscussion.get(topicPosition).getTopic().getParent().getCreatedDate()));
//txtAdminNoticeTime.setText("test")
				txtAdminNotice.setText(arrListTestDiscussion.get(topicPosition).getTopic().getTopicDescription());
		txtTopicValue.setText(arrListTestDiscussion.get(topicPosition).getTopic().getTopicName());
		if (arrListTestDiscussion.get(topicPosition).getTopic().getTopicDay().equalsIgnoreCase(WebConstants.MONDAY)) {
			listenerTutorialDiscussion.onDayChanged(Calendar.MONDAY);
		} else if (arrListTestDiscussion.get(topicPosition).getTopic().getTopicDay().equalsIgnoreCase(WebConstants.TUESDAY)) {
			listenerTutorialDiscussion.onDayChanged(Calendar.TUESDAY);
		} else if (arrListTestDiscussion.get(topicPosition).getTopic().getTopicDay().equalsIgnoreCase(WebConstants.WEDNESDAY)) {
			listenerTutorialDiscussion.onDayChanged(Calendar.WEDNESDAY);
		} else if (arrListTestDiscussion.get(topicPosition).getTopic().getTopicDay().equalsIgnoreCase(WebConstants.THURSDAY)) {
			listenerTutorialDiscussion.onDayChanged(Calendar.THURSDAY);
		} else if (arrListTestDiscussion.get(topicPosition).getTopic().getTopicDay().equalsIgnoreCase(WebConstants.FRIDAY)) {
			listenerTutorialDiscussion.onDayChanged(Calendar.FRIDAY);
		} else if (arrListTestDiscussion.get(topicPosition).getTopic().getTopicDay().equalsIgnoreCase(WebConstants.SATURDAY)) {
			listenerTutorialDiscussion.onDayChanged(Calendar.SATURDAY);
		}
	}

	/**
	 * send message to tutorial group
	 * @param tutorialGroupDiscussion
	 */
	private void sendTutorialMessage(TutorialGroupDiscussion tutorialGroupDiscussion){

		IOSocketHandler ioSocketHandler = new IOSocketHandler();

		if(IOSocketHandler.getSocketIOClient().isConnected()){

			ioSocketHandler.sendMessage(tutorialGroupDiscussion);
		}

		ioSocketHandler = null;

	}


    @Override
    public void onNewMessage(JSONObject message) throws JSONException {
            /*** new one ***/
        StudentHelper studentHelper = new StudentHelper(getActivity());
        TutorialGroupDiscussion tutorialGroupDiscussion = new TutorialGroupDiscussion();
        tutorialGroupDiscussion.setMessage(message.getString(SocketConstants.MESSAGE));
        tutorialGroupDiscussion.setCommentScore(message.getInt("comment_score"));
        tutorialGroupDiscussion.setInActiveHours(Boolean.parseBoolean(message.getString("in_active_hours")));

        tutorialGroupDiscussion.setCreatedDate(Utility.convertStringToDate(message.getString(SocketConstants.CREATED_DATE)));


        TutorialGroupTopicAllocation tutorialGroupTopicAllocation = studentHelper.getTutorialTopicAllocationByDayOfWeek(String.valueOf(intWeekDay
		-1));

        tutorialGroupDiscussion.setTopic(tutorialGroupTopicAllocation.getTutorialTopic());

        User user = studentHelper.getUser(message.getInt("sender_id"));

        if(user == null){
            user = new User();
            user.setProfilePicture(message.getString(SocketConstants.PROFILE_PICTURE));
            user.setUserId(message.getInt("sender_id"));
            user.setFullName(message.getString(SocketConstants.FULL_NAME));
            studentHelper.saveUser(user);
        }

        tutorialGroupDiscussion.setSender(user);
        /*** new one ***/
         studentHelper.saveTutorialGroupDiscussion(tutorialGroupDiscussion);

        arrListTestDiscussion.add(0,tutorialGroupDiscussion);

		if(1 == arrListTestDiscussion.size()){
			tutorialGroupDiscussion.setShowDetails(true);
		}
		else if(arrListTestDiscussion.get(1).getSender().getUserId() != tutorialGroupDiscussion.getSender().getUserId()) {
			tutorialGroupDiscussion.setShowDetails(true);
		}

        adpDiscussion.notifyDataSetChanged();
        layoutManagerChat.smoothScrollToPosition(recyclerChat, null, 0);

    }

}



