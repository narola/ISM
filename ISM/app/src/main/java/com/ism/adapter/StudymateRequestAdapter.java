package com.ism.adapter;

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

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.commonsource.utility.Utility;
import com.ism.commonsource.view.ProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.fragment.userprofile.ProfileControllerFragment;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.StudymateRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 07/11/15.
 */
public class StudymateRequestAdapter extends BaseAdapter implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = StudymateRequestAdapter.class.getSimpleName();

	private AlertDialog dialogRespond;
	private ProgressGenerator progressGenerator;
	private Button btnAccept;
	private ProcessButton progress;

	private ArrayList<StudymateRequest> arrListStudymate;
	private Context context;
	private ProfileControllerFragment fragProfileController;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private int listItemLimit = 0;

	public StudymateRequestAdapter(Context context, ArrayList<StudymateRequest> arrListStudymate) {
		this.arrListStudymate = arrListStudymate;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		myTypeFace = new MyTypeFace(context);
		progressGenerator = new ProgressGenerator();

	}

	public StudymateRequestAdapter(Context context, ProfileControllerFragment profileControllerFragment, ArrayList<StudymateRequest> arrListStudymate, int listItemLimit) {
		this(context, arrListStudymate);
		fragProfileController = profileControllerFragment;
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

			holder.txtNameRequest.setTypeface(myTypeFace.getRalewayRegular());
			holder.txtTime.setTypeface(myTypeFace.getRalewayRegular());
			holder.btnRespond.setTypeface(myTypeFace.getRalewayRegular());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			imageLoader.displayImage(Global.strProfilePic, holder.imgDp, ISMStudent.options);
			holder.txtNameRequest.setText(Html.fromHtml("<font color='#1BC4A2'>" + arrListStudymate.get(position).getRequestFromName()
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
					if (fragProfileController != null) {
						fragProfileController.showAllStudymateRequests(position);
					}
				}
			});
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
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
				if (com.ism.utility.Utility.isConnected(context)) {
					progress.setVisibility(View.VISIBLE);
					progress.setProgress(1);
					progressGenerator.start(progress);
					callApiRespondToRequest(studymateId);
				} else {
					com.ism.utility.Utility.alertOffline(context);
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
			attribute.setMateOf(studymateId);
			attribute.setMateId(Global.strUserId);

			new WebserviceWrapper(context, attribute, this).new WebserviceCaller().
					execute(WebConstants.RESPOND_TO_REQUEST);
		} catch (Exception e) {
			Log.e(TAG, "callApiRespondToRequest Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
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