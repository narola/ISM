package com.ism.fragment.TutorialGroup;

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
import com.ism.model.TestDiscussion;
import com.ism.object.Global;
import com.ism.scientificcalc.view.Calc;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.whiteboard.view.Whiteboard;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Discussion;
import com.ism.ws.model.GroupDiscussionData;

import java.util.ArrayList;

/**
 * Created by c161 on 12/10/15.
 */
public class TutorialDiscussionFragment extends Fragment implements WebserviceWrapper.WebserviceResponse,
		DiscussionAdapter.TutorialDiscussionAdapterListener {

	private static final String TAG = TutorialDiscussionFragment.class.getSimpleName();

	private View view;
	private TextView txtTopic, txtTopicValue, txtAlert, txtAdminNoticeTime, txtAdminNotice;
	private ImageView imgCalc, imgWhiteboard, imgSearch, imgDictionary, imgExpandDiscussion;
	private CircleImageView imgAdminDp;
	private RelativeLayout rlUtility;
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
	private ArrayList<TestDiscussion> arrListTestDiscussion;
	private ArrayList<GroupDiscussionData> arrListDiscussionData;
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
		if (getArguments() != null) {
			intWeekDay = getArguments().getInt(ARG_WEEK_DAY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_discussion, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
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

		imgUtilities = new ImageView[]{imgCalc, imgWhiteboard, imgSearch, imgDictionary};
		viewUtilities = new View[]{utilitySciCalc, utilityWhiteboard, utilityAssistantWebView};

		txtTopic.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtTopicValue.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtAlert.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtAdminNoticeTime.setTypeface(Global.myTypeFace.getRalewayThinItalic());
		txtAdminNotice.setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_admin_name)).setTypeface(Global.myTypeFace.getRalewayRegular());

		setDay(intWeekDay);

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
			public void onSendImageListener(Bitmap bitmap) {
				Log.e(TAG, "onSendImageListener");
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
					TestDiscussion testDiscussion = new TestDiscussion();
					testDiscussion.setMessage(etMessage.getText().toString().trim());
					testDiscussion.setTime("Aug 5, 2015  4:15pm");
					testDiscussion.setUserName(Global.strFullName);
					arrListTestDiscussion.add(0, testDiscussion);
					adpDiscussion.notifyDataSetChanged();
					layoutManagerChat.smoothScrollToPosition(recyclerChat, null, 0);
					etMessage.setText("");
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

	private void callApiGetGroupHistory() {
		try {
			Attribute attribute = new Attribute();
			attribute.setGroupId("134");
			attribute.setWeekNo("1");
			attribute.setDayNo("");

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_GROUP_HISTORY);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetGroupHistory Exception : " + e.toString());
		}
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
		switch (intWeekDay) {
			case com.ism.fragment.TutorialGroup.TutorialFragment.MON:
//				txtTopic.setText("Monday");
				break;
			case com.ism.fragment.TutorialGroup.TutorialFragment.TUE:
//				txtTopic.setText("Tuesday");
				break;
			case com.ism.fragment.TutorialGroup.TutorialFragment.WED:
//				txtTopic.setText("Wednesday");
				break;
			case com.ism.fragment.TutorialGroup.TutorialFragment.THU:
//				txtTopic.setText("Thursday");
				break;
			case com.ism.fragment.TutorialGroup.TutorialFragment.FRI:
//				txtTopic.setText("Friday");
				break;
			case com.ism.fragment.TutorialGroup.TutorialFragment.SAT:
//				txtTopic.setText("Saturday");
				break;
			case com.ism.fragment.TutorialGroup.TutorialFragment.SUN:
//				txtTopic.setText("Sunday");
				break;
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
				arrListDiscussionData = responseHandler.getGroupDiscussionData();
				arrListDiscussion = new ArrayList<>();
				for (int i = 0; i < arrListDiscussionData.size(); i++) {
					for (Discussion discussion : arrListDiscussionData.get(i).getDiscussion()) {
						discussion.setWeekDay(arrListDiscussionData.get(i).getDayName());
						discussion.setTopicPosition(i);
						arrListDiscussion.add(discussion);
					}
				}
				adpDiscussion = new DiscussionAdapter(getActivity(), arrListDiscussion, this);
				recyclerChat.setAdapter(adpDiscussion);
			} else if (error != null) {
				Log.e(TAG, "onResponseGetGroupHistory api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetGroupHistory Exception : " + e.toString());
		}
	}

	@Override
	public void onTutorialTopicPositionChanged(int topicPosition) {
		/*if (arrListDiscussion.get(layoutManagerChat.findFirstCompletelyVisibleItemPosition()).getWeekDay()
				!= arrListDiscussion.get(layoutManagerChat.findFirstCompletelyVisibleItemPosition()).getWeekDay()) {

		}*/
		showTopicDetails(topicPosition);
	}

	private void showTopicDetails(int topicPosition) {
		Log.e(TAG, "Position : " + topicPosition);
		txtAdminNoticeTime.setText(Utility.formatPHPDateToMMMDDYY_HHMMA(arrListDiscussionData.get(topicPosition).getAssignedTime())
				+ "  " + arrListDiscussionData.get(topicPosition).getDayName());
		txtAdminNotice.setText(arrListDiscussionData.get(topicPosition).getTopicDescription());
		txtTopicValue.setText(arrListDiscussionData.get(topicPosition).getTutorialTopic());
		if (arrListDiscussionData.get(topicPosition).getDayName().equalsIgnoreCase("Mon")) {
			listenerTutorialDiscussion.onDayChanged(com.ism.fragment.TutorialGroup.TutorialFragment.MON);
		} else if (arrListDiscussionData.get(topicPosition).getDayName().equalsIgnoreCase("Tues")) {
			listenerTutorialDiscussion.onDayChanged(com.ism.fragment.TutorialGroup.TutorialFragment.TUE);
		} else if (arrListDiscussionData.get(topicPosition).getDayName().equalsIgnoreCase("Wed")) {
			listenerTutorialDiscussion.onDayChanged(com.ism.fragment.TutorialGroup.TutorialFragment.WED);
		} else if (arrListDiscussionData.get(topicPosition).getDayName().equalsIgnoreCase("Thurs")) {
			listenerTutorialDiscussion.onDayChanged(com.ism.fragment.TutorialGroup.TutorialFragment.THU);
		} else if (arrListDiscussionData.get(topicPosition).getDayName().equalsIgnoreCase("Fri")) {
			listenerTutorialDiscussion.onDayChanged(com.ism.fragment.TutorialGroup.TutorialFragment.FRI);
		} else if (arrListDiscussionData.get(topicPosition).getDayName().equalsIgnoreCase("Sat")) {
			listenerTutorialDiscussion.onDayChanged(com.ism.fragment.TutorialGroup.TutorialFragment.SAT);
		}
	}

}
