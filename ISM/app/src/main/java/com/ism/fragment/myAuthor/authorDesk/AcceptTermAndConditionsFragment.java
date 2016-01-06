package com.ism.fragment.myAuthor.authorDesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.constant.AppConstant;
import com.ism.fragment.MyAuthorFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Utility;

/**
 * Created by c162 on 04/1/16.
 */
public class AcceptTermAndConditionsFragment extends Fragment  {

    private static final String TAG = AcceptTermAndConditionsFragment.class.getSimpleName();
    private static final int FRAGMENT_MY_AUTHORS = 0;

    private View view;

    private TextView txtAuthorName,txtTermANdCondition;
    private HostActivity activityHost;
    public MyAuthorFragment myAuthorFragment;
    private FragmentListener fragListener;
    private ActionProcessButton btnSubmit;
    private CheckBox chkAccept;
    private String authorId;
    private String authorName;

    public static AcceptTermAndConditionsFragment newInstance() {
        AcceptTermAndConditionsFragment fragReportCard = new AcceptTermAndConditionsFragment();
        return fragReportCard;
    }

    public AcceptTermAndConditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_term_condition_author, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        myAuthorFragment = MyAuthorFragment.newInstance();

        txtAuthorName = (TextView) view.findViewById(R.id.txt_author_name);
        txtAuthorName.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtTermANdCondition = (TextView) view.findViewById(R.id.txt_conditions);
        txtTermANdCondition.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTermANdCondition.setMovementMethod(new ScrollingMovementMethod());

        chkAccept=(CheckBox)view.findViewById(R.id.chk_accept_term_condition);
        chkAccept.setTypeface(Global.myTypeFace.getRalewayRegular());

        authorId=activityHost.getBundle().getString(AppConstant.AUTHOR_ID);
        authorName=activityHost.getBundle().getString(AppConstant.AUTHOR_NAME);

        txtAuthorName.setText(authorName);
        btnSubmit = (ActionProcessButton) view.findViewById(R.id.btn_submit);
        btnSubmit.setTypeface(Global.myTypeFace.getRalewayMedium());

        onClicks();
    }

    private void onClicks() {
        try {

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(chkAccept.isChecked()){
                        Bundle bundle=activityHost.getBundle();
                        bundle.putString(AppConstant.AUTHOR_NAME, authorName);
                        bundle.putString(AppConstant.AUTHOR_ID, authorId);
                        activityHost.setBundle(bundle);
                        activityHost.loadFragment(MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE,bundle);
                    }
                    else
                    {
                        Utility.showToast(getActivity(),"Please accept the term and condition!");
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onCLicks Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            Log.e(TAG, "onAttach ");
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_TERM_AND_CONDITION);
            }

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach");
            fragListener.onFragmentDetached(MyAuthorFragment.FRAGMENT_TERM_AND_CONDITION);
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

}
