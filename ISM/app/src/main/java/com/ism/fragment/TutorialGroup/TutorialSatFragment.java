package com.ism.fragment.TutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.Global;

/**
 * Created by c161 on 12/10/15.
 */
public class TutorialSatFragment extends Fragment {

	private static final String TAG = TutorialSatFragment.class.getSimpleName();

	private View view;

	private TextView txtMessage, txtMessage2;

	public static TutorialSatFragment newInstance() {
		TutorialSatFragment fragment = new TutorialSatFragment();
		return fragment;
	}

	public TutorialSatFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_sat, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		txtMessage = (TextView) view.findViewById(R.id.txt_message);
		txtMessage2 = (TextView) view.findViewById(R.id.txt_message2);

		txtMessage.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtMessage2.setTypeface(Global.myTypeFace.getSchadowBT());

		txtMessage2.setText(Html.fromHtml(getString(R.string.msg_saturday)));
	}

}