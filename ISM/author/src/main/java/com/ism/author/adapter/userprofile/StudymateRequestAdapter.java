package com.ism.author.adapter.userprofile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.userprofile.AuthorProfileFragment;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.StudymateRequest;
import com.ism.commonsource.utility.Utility;
import com.ism.commonsource.view.ProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

import java.util.ArrayList;

/**
 * Created by c162 on 27/11/15.
 */
public class StudymateRequestAdapter extends BaseAdapter implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = StudymateRequestAdapter.class.getSimpleName();

	private AlertDialog dialogRespond;
	private ProgressGenerator progressGenerator;
	private Button btnAccept;
	private ProcessButton progress;

	private ArrayList<StudymateRequest> arrListStudymate;
	private Context context;
	private LayoutInflater inflater;
	private int listItemLimit = 0;
	private AuthorProfileFragment fragment;

	public StudymateRequestAdapter(Context context, ArrayList<StudymateRequest> arrListStudymate) {
		this.arrListStudymate = arrListStudymate;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		progressGenerator = new ProgressGenerator();

	}

	public StudymateRequestAdapter(Context context, ArrayList<StudymateRequest> arrListStudymate, int listItemLimit,AuthorProfileFragment fragment) {
		this(context, arrListStudymate);
		this.fragment=fragment;
		this.listItemLimit = listItemLimit;
	}

	@Override
	public int getCount() {
		return listItemLimit > 0 ? listItemLimit < arrListStudymate.size() ? listItemLimit : arrListStudymate.size() : arrListStudymate.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListStudymate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(listItemLimit >= 0 ? R.layout.list_item_studymate_request_popup : R.layout.list_item_studymate_request,
					parent, false);
			holder = new ViewHolder();
			holder.imgDp = (CircleImageView) convertView.findViewById(R.id.img_dp);
			holder.txtNameRequest = (TextView) convertView.findViewById(R.id.txt_name_message);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
			holder.btnRespond = (Button) convertView.findViewById(R.id.btn_respond);

			holder.txtNameRequest.setTypeface(Global.myTypeFace.getRalewayRegular());
			holder.txtTime.setTypeface(Global.myTypeFace.getRalewayRegular());
			holder.btnRespond.setTypeface(Global.myTypeFace.getRalewayRegular());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			Global.imageLoader.displayImage(WebConstants.USER_IMAGES+arrListStudymate.get(position).getRequesterProfile(), holder.imgDp, ISMAuthor.options);
			holder.txtNameRequest.setText(Html.fromHtml("<font color='#41B1E3'>" + arrListStudymate.get(position).getRequestFromName()
					+ "</font><font color='#323941'> " + context.getString(R.string.msg_studymate_request) + "</font>"));
			holder.txtTime.setText(Utility.getTimeDuration(arrListStudymate.get(position).getRequestDate()));

			if (arrListStudymate.get(position).getIsSeen().equals("1")) {
				convertView.setBackgroundColor(Color.TRANSPARENT);
			} else {
				convertView.setBackgroundResource(R.drawable.shape_unread_notification);
			}

			if (arrListStudymate.get(position).getStatus().equals("1")) {
				holder.btnRespond.setVisibility(View.GONE);
			} else {
				holder.btnRespond.setVisibility(View.VISIBLE);
			}

			holder.btnRespond.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showRespondToRequestDialog(arrListStudymate.get(position).getRequestFromId());
				}
			});
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(fragment!=null)
					((AuthorProfileFragment)fragment).onclickStudymateRequestView(position);
				}
			});
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	@Override
	public void onResponse(int apiCode, Object object, Exception error) {
		try {
			switch (apiCode) {
				case WebConstants.RESPOND_TO_REQUEST:
					onResponseRespondToRequest(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	class ViewHolder {
		CircleImageView imgDp;
		TextView txtNameRequest, txtTime;
		Button btnRespond;
	}
	private void showRespondToRequestDialog(final String studymateId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		View view = LayoutInflater.from(context).inflate(R.layout.dialog_respond_to_request, null);
		progress = (ProcessButton) view.findViewById(R.id.progress);

		builder.setView(view)
				.setMessage(R.string.accept_request)
				.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				}).setNegativeButton(R.string.strclose, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setCancelable(false);
		dialogRespond = builder.create();
		dialogRespond.show();
		btnAccept = dialogRespond.getButton(DialogInterface.BUTTON_POSITIVE);
		btnAccept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (com.ism.author.Utility.Utility.isConnected(context)) {
					progress.setVisibility(View.VISIBLE);
					progress.setProgress(1);
					progressGenerator.start(progress);
					callApiRespondToRequest(studymateId);
				} else {
					com.ism.author.Utility.Utility.alertOffline(context);
				}
			}
		});
	}

	private void disableDialogButtons() {
		btnAccept.setEnabled(false);
	}

	private void enableDialogButtons() {
		btnAccept.setEnabled(true);
	}

	private void callApiRespondToRequest(String studymateId) {
		try {
			disableDialogButtons();
			Attribute attribute = new Attribute();
			attribute.setUserId(studymateId);
			attribute.setStudymateId(Global.strUserId);

			new WebserviceWrapper(context, attribute, this).new WebserviceCaller().
					execute(WebConstants.RESPOND_TO_REQUEST);
		} catch (Exception e) {
			Log.e(TAG, "callApiRespondToRequest Exception : " + e.toString());
		}
	}


	private void onResponseRespondToRequest(Object object, Exception error) {
		try {
			progress.setProgress(100);
			progress.setVisibility(View.GONE);
			enableDialogButtons();
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					Toast.makeText(context, R.string.you_are_now_studymates, Toast.LENGTH_LONG).show();
					dialogRespond.dismiss();
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Toast.makeText(context, R.string.failed_to_execute_request, Toast.LENGTH_LONG).show();
					Log.e(TAG, "onResponseRespondToRequest message : " + responseHandler.getMessage());
				}
			} else if(error != null) {
				Log.e(TAG, "onResponseRespondToRequest Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseRespondToRequest Exception : " + e.toString());
		}
	}

}