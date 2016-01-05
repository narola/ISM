package com.ism.fragment.myAuthor.authorDesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.fragment.MyAuthorFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;

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

        btnSubmit = (ActionProcessButton) view.findViewById(R.id.btn_submit);
        btnSubmit.setTypeface(Global.myTypeFace.getRalewayMedium());
        onClicks();
    }

    private void onClicks() {
        try {


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
