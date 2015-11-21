package com.ism.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;

/**
 * Created by c161 on 06/11/15.
 */
public class MyWalletFragment extends Fragment {

	private static final String TAG = MyWalletFragment.class.getSimpleName();

	private View view;
	private TextView txtBalance;
	private EditText editCouponAmount;

	public static MyWalletFragment newInstance() {
		MyWalletFragment fragment = new MyWalletFragment();
		return fragment;
	}

	public MyWalletFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_wallet, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		txtBalance = (TextView) view.findViewById(R.id.txt_wallet_balance);
		editCouponAmount = (EditText) view.findViewById(R.id.edit_coupon_amount);

		MyTypeFace myTypeFace = new MyTypeFace(getActivity());
		txtBalance.setTypeface(myTypeFace.getRalewaySemiBold());
		editCouponAmount.setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_generate_coupon)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_generate_coupon2)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_generate_coupon_msg)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_enter_amount)).setTypeface(myTypeFace.getRalewayBold());
		((TextView)view.findViewById(R.id.txt_coupon_code)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_date)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_amount)).setTypeface(myTypeFace.getRalewayRegular());

		txtBalance.setText(Html.fromHtml("<font color='#323941'>Your wallet Balance is</font><br><font color='#DA534F'>Rs 1000</font>"));
	}

}
