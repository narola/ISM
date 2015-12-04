package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.VoucherAdapter;
import com.ism.commonsource.view.ProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.InputValidator;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Voucher;

import java.util.ArrayList;

/**
 * Created by c161 on 06/11/15.
 */
public class MyWalletFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostActivity.ProfileControllerPresenceListener {

	private static final String TAG = MyWalletFragment.class.getSimpleName();

	private View view, viewHighlighterTriangle;
	private TextView txtBalance, txtEmptyListMessage;
	private EditText etVoucherAmount;
	private ListView lvVoucher;
	private ProcessButton btnGenerate;

	private HostActivity activityHost;
	private ArrayList<Voucher> arrListVoucher;
	private VoucherAdapter adpVoucher;
	private ProgressGenerator progressGenerator;
	private FragmentListener fragListener;

	private double dBalance;

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
		viewHighlighterTriangle = view.findViewById(R.id.view_highlighter_triangle);
		txtBalance = (TextView) view.findViewById(R.id.txt_wallet_balance);
		txtEmptyListMessage = (TextView) view.findViewById(R.id.txt_emptylist_message);
		etVoucherAmount = (EditText) view.findViewById(R.id.edit_voucher_amount);
		lvVoucher = (ListView) view.findViewById(R.id.lv_voucher);
		btnGenerate = (ProcessButton) view.findViewById(R.id.btn_generate);

		txtEmptyListMessage.setTypeface(Global.myTypeFace.getRalewayRegular());
		lvVoucher.setEmptyView(txtEmptyListMessage);
		viewHighlighterTriangle.setVisibility(activityHost.getCurrentRightFragment() == HostActivity.FRAGMENT_PROFILE_CONTROLLER ? View.VISIBLE : View.GONE);

		progressGenerator = new ProgressGenerator();

		if (Utility.isConnected(activityHost)) {
			callApiGetWalletSummary();
		} else {
			Utility.alertOffline(activityHost);
		}

		MyTypeFace myTypeFace = new MyTypeFace(getActivity());
		txtBalance.setTypeface(myTypeFace.getRalewaySemiBold());
		etVoucherAmount.setTypeface(myTypeFace.getRalewayRegular());
		btnGenerate.setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_generate_voucher)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_generate_voucher2)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_generate_voucher_msg)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_enter_amount)).setTypeface(myTypeFace.getRalewayBold());
		((TextView)view.findViewById(R.id.txt_voucher_code)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_date)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView)view.findViewById(R.id.txt_amount)).setTypeface(myTypeFace.getRalewayRegular());

		final InputValidator inputValidator = new InputValidator(activityHost);

		btnGenerate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (inputValidator.validateStringPresence(etVoucherAmount)) {
					if (Utility.isConnected(activityHost)) {
						callApiGenerateVoucher();
					} else {
						Utility.alertOffline(activityHost);
					}
				}
			}
		});

	}

	private void callApiGenerateVoucher() {
		try {
			btnGenerate.setProgress(1);
			btnGenerate.setEnabled(false);
			progressGenerator.start(btnGenerate);
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);
			attribute.setVoucherAmount(etVoucherAmount.getText().toString().trim());

			new WebserviceWrapper(activityHost, attribute, this).new WebserviceCaller()
					.execute(WebConstants.GENERATE_VOUCHER);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetWalletSummary Exception : " + e.toString());
		}
	}

	private void callApiGetWalletSummary() {
		try {
			activityHost.showProgress();
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(activityHost, attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_WALLET_SUMMARY);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetWalletSummary Exception : " + e.toString());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityHost = (HostActivity) activity;
			fragListener = (FragmentListener) activity;
			activityHost.setListenerProfileControllerPresence(this);
			if (fragListener != null) {
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_MY_WALLET);
			}
		} catch (ClassCastException e) {
			Debug.e(TAG, "onAttach Exception : " + e.toString());
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			if (fragListener != null) {
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_MY_WALLET);
			}
		} catch (ClassCastException e) {
			Debug.e(TAG, "onDetach Exception : " + e.toString());
		}
		fragListener = null;
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_WALLET_SUMMARY:
					onResponseGetWalletSummary(object, error);
					break;
				case WebConstants.GENERATE_VOUCHER:
					onResponseGenerateVoucher(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGenerateVoucher(Object object, Exception error) {
		try {
			btnGenerate.setProgress(100);
			btnGenerate.setEnabled(true);
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					if (etVoucherAmount != null) {
						etVoucherAmount.setText("");
					}

					if (responseHandler.getWallet().get(0).getWalletBalance() != null) {
						dBalance = Double.valueOf(responseHandler.getWallet().get(0).getWalletBalance());
					}
					showBalance(responseHandler.getWallet().get(0).getWalletBalance());
					arrListVoucher.add(responseHandler.getWallet().get(0).getVouchers().get(0));
					adpVoucher.notifyDataSetChanged();
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Utility.alert(activityHost, null, responseHandler.getMessage());
					Log.e(TAG, "onResponseGenerateVoucher failed : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGenerateVoucher api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGenerateVoucher Exception : " + e.toString());
		}
	}

	private void onResponseGetWalletSummary(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					if (responseHandler.getWallet().get(0).getWalletBalance() != null) {
						dBalance = Double.valueOf(responseHandler.getWallet().get(0).getWalletBalance());
					}
					showBalance(responseHandler.getWallet().get(0).getWalletBalance());
					arrListVoucher = responseHandler.getWallet().get(0).getVouchers();
					fillVoucherList();
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetWalletSummary failed : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetWalletSummary api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetWalletSummary Exception : " + e.toString());
		}
	}

	private void showBalance(String balance) {
		txtBalance.setText(Html.fromHtml("<font color='#323941'>Your wallet Balance is</font>" +
				"<br><font color='#DA534F'>" + (balance != null ? balance : 0) + "</font>"));
	}

	private void fillVoucherList() {
		try {
			if (arrListVoucher != null) {
				adpVoucher = new VoucherAdapter(activityHost, arrListVoucher);
				lvVoucher.setAdapter(adpVoucher);
			}
		} catch (Exception e) {
			Log.e(TAG, "fillVoucherList Exception : " + e.toString());
		}
	}

	@Override
	public void onProfileControllerAttached() {
		viewHighlighterTriangle.setVisibility(View.VISIBLE);
	}

	@Override
	public void onProfileControllerDetached() {
		viewHighlighterTriangle.setVisibility(View.GONE);
	}

}