package com.ism.fragment.myAuthor.authorDesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.AuthorAssignmentsAdapter;
import com.ism.adapter.myAuthor.BookAssignmentsAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Assignment;

import java.util.ArrayList;

/**
 * Created by c166 on 1/1/2016
 */
public class BookAssignmentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = BookAssignmentsFragment.class.getSimpleName();
    private View view;
    private RecyclerView rvBookAssignmentsList;
    private BookAssignmentsAdapter bookAssignmentsAdapter;
    private Fragment mFragment;
    private ArrayList<Assignment> arrListAuthorBooksAssignment = new ArrayList<Assignment>();
    private TextView tvNoDataMsg;
    private LinearLayoutManager mLayoutManager;
    private ImageView imgSearchAssignments;
    private EditText etSearchAssignments;
    private HostActivity activityHost;
    private FragmentListener fragmentListener;


    public static BookAssignmentsFragment newInstance(Fragment fragment) {
        BookAssignmentsFragment bookAssignmentsFragment = new BookAssignmentsFragment();
        bookAssignmentsFragment.mFragment = fragment;
        return bookAssignmentsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_book_assignments, container, false);
        initGlobal();
        return view;
    }


    private void initGlobal() {
        tvNoDataMsg = (TextView) view.findViewById(R.id.txt_empty_view);
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());

        rvBookAssignmentsList = (RecyclerView) view.findViewById(R.id.rv_book_assignments_list);
        bookAssignmentsAdapter = new BookAssignmentsAdapter(mFragment, getActivity());
        rvBookAssignmentsList.setHasFixedSize(true);
        rvBookAssignmentsList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvBookAssignmentsList.setAdapter(bookAssignmentsAdapter);
        rvBookAssignmentsList.setAdapter(bookAssignmentsAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvBookAssignmentsList.setLayoutManager(mLayoutManager);


        imgSearchAssignments = (ImageView) view.findViewById(R.id.img_search_assignments);
        etSearchAssignments = (EditText) view.findViewById(R.id.et_search_assignments);
        imgSearchAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSearchAssignments.getVisibility() == View.VISIBLE) {
                    etSearchAssignments.setVisibility(View.GONE);
                    bookAssignmentsAdapter.filter("");
                    etSearchAssignments.setText("");

                } else {
                    startSlideAnimation(etSearchAssignments, etSearchAssignments.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgSearchAssignments, etSearchAssignments.getWidth(), 0, 0, 0);
                    etSearchAssignments.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSearchAssignments, getActivity());
                }
            }
        });


        etSearchAssignments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookAssignmentsAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        callApiGetAssignmentByBook();
    }


    private void startSlideAnimation(final View view, int fromX, int toX, int fromY, int toY) {
        TranslateAnimation slideOutAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideOutAnimation.setDuration(500);
        slideOutAnimation.setFillAfter(true);
        view.startAnimation(slideOutAnimation);
    }

    private void callApiGetAssignmentByBook() {

        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setBookId(activityHost.getBundle().getString(AuthorAssignmentsAdapter.ARG_BOOK_ID));

                new WebserviceWrapper(getActivity(), attribute,this).new WebserviceCaller()
                        .execute(WebConstants.GET_ASSIGNMENT_BY_BOOK);
            } catch (Exception e) {
                Utility.alertOffline(getActivity());
            }
        } else {
            Utility.alertOffline(getActivity());
        }

    }

    private void onResponseGetAssignmentByBook(Object object, Exception error) {

        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

                    if (responseHandler.getAssignment().size() > 0) {

                        arrListAuthorBooksAssignment.addAll(responseHandler.getAssignment());
                        bookAssignmentsAdapter.addAll(arrListAuthorBooksAssignment);
                        bookAssignmentsAdapter.notifyDataSetChanged();

                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Utility.showToast(getActivity(), responseHandler.getMessage());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAssignmentByBook api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAssignmentByBook Exception : " + e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            fragmentListener = (FragmentListener) activity;
            if (fragmentListener != null) {
                fragmentListener.onFragmentAttached(AuthorDeskFragment.FRAGMENT_BOOKASSIGNMENT);
            }

        } catch (Exception e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach fragment ");
            if (fragmentListener != null) {
                fragmentListener.onFragmentDetached(AuthorDeskFragment.FRAGMENT_BOOKASSIGNMENT);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragmentListener = null;
    }
    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {
                case WebConstants.GET_ASSIGNMENT_BY_BOOK:
                    onResponseGetAssignmentByBook(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }
}
