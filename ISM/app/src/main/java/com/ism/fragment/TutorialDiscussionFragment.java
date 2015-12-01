package com.ism.fragment;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.adapter.DiscussionAdapter;
import com.ism.model.TestDiscussion;
import com.ism.object.MyTypeFace;

import java.util.ArrayList;

/**
 * Created by c161 on 12/10/15.
 */
public class TutorialDiscussionFragment extends Fragment {

	private static final String TAG = TutorialDiscussionFragment.class.getSimpleName();

	private View view;
	private TextView txtTopic, txtTopicValue;
	private RecyclerView recyclerChat;

	private TutorialDiscussionFragmentListener listenerTutorialDiscussion;
	private ArrayList<TestDiscussion> arrListDiscussion;
	private DiscussionAdapter adpDiscussion;

	private static final String ARG_WEEK_DAY = "weekDay";
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
		recyclerChat = (RecyclerView) view.findViewById(R.id.recycler_chat);

		MyTypeFace myTypeFace = new MyTypeFace(getActivity());
		txtTopic.setTypeface(myTypeFace.getRalewayRegular());
		txtTopicValue.setTypeface(myTypeFace.getRalewaySemiBold());

		txtTopicValue.setText("Osmosis");

		setDay(intWeekDay);

		recyclerChat.setLayoutManager(new LinearLayoutManager(getActivity()));
		RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				if (parent.getChildLayoutPosition(view) == 0) {
					outRect.top = 10;
				}
				outRect.left = 10;
				outRect.right = 10;
			}
		};
		recyclerChat.addItemDecoration(decoration);

		arrListDiscussion = new ArrayList<>();
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
			testDiscussion.setUserName("Albert Crowley");
			arrListDiscussion.add(testDiscussion);
		}
		adpDiscussion = new DiscussionAdapter(getActivity(), arrListDiscussion);
		recyclerChat.setAdapter(adpDiscussion);
	}

	public void setTutorialDiscussionListener(TutorialDiscussionFragmentListener listenerTutorialDiscussion) {
		this.listenerTutorialDiscussion = listenerTutorialDiscussion;
	}

	public void setDay(int day) {
		intWeekDay = day;
		switch (intWeekDay) {
			case TutorialFragment.MON:
//				txtTopic.setText("Monday");
				break;
			case TutorialFragment.TUE:
//				txtTopic.setText("Tuesday");
				break;
			case TutorialFragment.WED:
//				txtTopic.setText("Wednesday");
				break;
			case TutorialFragment.THU:
//				txtTopic.setText("Thursday");
				break;
			case TutorialFragment.FRI:
//				txtTopic.setText("Friday");
				break;
			case TutorialFragment.SAT:
//				txtTopic.setText("Saturday");
				break;
			case TutorialFragment.SUN:
//				txtTopic.setText("Sunday");
				break;
		}
	}

}
