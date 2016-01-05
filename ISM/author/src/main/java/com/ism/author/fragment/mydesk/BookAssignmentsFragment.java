package com.ism.author.fragment.mydesk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.BookAssignmentsAdapter;
import com.ism.author.adapter.MyDeskAssignmentsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Assignment;

import java.util.ArrayList;

/**
 * Created by c166 on 21/12/15.
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


    public static BookAssignmentsFragment newInstance(Fragment fragment) {
        BookAssignmentsFragment bookAssignmentsFragment = new BookAssignmentsFragment();
        bookAssignmentsFragment.mFragment = fragment;
        return bookAssignmentsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book_assignments, container, false);
        initGlobal();
        return view;
    }


    private void initGlobal() {
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);

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
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setBookId(((AuthorHostActivity) getActivity()).getBundle().getString(MyDeskAssignmentsAdapter.ARG_BOOK_ID));

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETASSIGNMENTBYBOOK);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETASSIGNMENTBYBOOK:
                    onResponseGetAssignmentByBook(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAssignmentByBook(Object object, Exception error) {

        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getAssignment().size() > 0) {

                        arrListAuthorBooksAssignment.addAll(responseHandler.getAssignment());
                        bookAssignmentsAdapter.addAll(arrListAuthorBooksAssignment);
                        bookAssignmentsAdapter.notifyDataSetChanged();
                        setEmptyView(false);

                    } else {
                        setEmptyView(true);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAssignmentByBook api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAssignmentByBook Exception : " + e.toString());
        }
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_book_assignments));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);

        rvBookAssignmentsList.setVisibility(isEnable ? View.GONE : View.VISIBLE);

    }

}
