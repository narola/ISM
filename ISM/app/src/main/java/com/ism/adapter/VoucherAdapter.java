package com.ism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;
import com.ism.ws.model.Voucher;

import java.util.ArrayList;

/**
 * Created by c161 on 24/11/15.
 */
public class VoucherAdapter extends BaseAdapter {

	private static final String TAG = VoucherAdapter.class.getSimpleName();

	private ArrayList<Voucher> arrListVoucher;
	private Context context;
	private LayoutInflater inflater;
	private MyTypeFace myTypeFace;

	public VoucherAdapter(Context context, ArrayList<Voucher> arrListVoucher) {
		this.arrListVoucher = arrListVoucher;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		myTypeFace = new MyTypeFace(context);
	}

	@Override
	public int getCount() {
		return arrListVoucher.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListVoucher.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_voucher, parent, false);
			holder = new ViewHolder();
			holder.txtCode = (TextView) convertView.findViewById(R.id.txt_voucher_code);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txt_voucher_date);
			holder.txtAmount = (TextView) convertView.findViewById(R.id.txt_voucher_amount);

			holder.txtCode.setTypeface(myTypeFace.getRalewayRegular());
			holder.txtDate.setTypeface(myTypeFace.getRalewayRegular());
			holder.txtAmount.setTypeface(myTypeFace.getRalewayRegular());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.bg_voucher_item_light : R.drawable.bg_voucher_item_dark);
			holder.txtCode.setText(arrListVoucher.get(position).getVoucherCode());
			holder.txtDate.setText(Utility.formatMySqlDate(arrListVoucher.get(position).getCreatedDate(), Utility.DATE_FORMAT_DDMMMYY));
			holder.txtAmount.setText(arrListVoucher.get(position).getAmount());
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		TextView txtCode, txtDate, txtAmount;
	}

}