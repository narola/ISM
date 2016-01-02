package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.ism.author.adapter.userprofile.AuthorBooksAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c166 on 01/01/16.
 */
public class AuthorBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = AuthorBooksFragment.class.getSimpleName();
    private View view;
    private ImageView imgSearchAuthorBooks;
    private TextView tvNoDataMsg;
    private EditText etSearchAuthorBooks;
    private RecyclerView rvAuthorBooks;
    private AuthorBooksAdapter authorBooksAdapter;
    private ArrayList<BookData> arrListAuthorBooks;
    private FragmentListener fragListener;

    public static AuthorBooksFragment newInstance() {
        AuthorBooksFragment fragAuthorBooks = new AuthorBooksFragment();
        return fragAuthorBooks;
    }

    public AuthorBooksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_books, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        imgSearchAuthorBooks = (ImageView) view.findViewById(R.id.img_search_author_books);
        etSearchAuthorBooks = (EditText) view.findViewById(R.id.et_search_author_books);
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);


        rvAuthorBooks = (RecyclerView) view.findViewById(R.id.rv_author_books);
        authorBooksAdapter = new AuthorBooksAdapter(getActivity());
        rvAuthorBooks.setHasFixedSize(true);
        rvAuthorBooks.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAuthorBooks.setAdapter(authorBooksAdapter);

        setEmptyView(false);
        callApiGetAuthorBooks();


        imgSearchAuthorBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSearchAuthorBooks.getVisibility() == View.VISIBLE) {
                    etSearchAuthorBooks.setVisibility(View.GONE);
                    authorBooksAdapter.filter("");
                    etSearchAuthorBooks.setText("");

                } else {
                    startSlideAnimation(etSearchAuthorBooks, etSearchAuthorBooks.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgSearchAuthorBooks, etSearchAuthorBooks.getWidth(), 0, 0, 0);
                    etSearchAuthorBooks.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSearchAuthorBooks, getActivity());
                }
            }
        });


        etSearchAuthorBooks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                authorBooksAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_author_books));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvAuthorBooks.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }

    private void callApiGetAuthorBooks() {

        if (Utility.isConnected(getActivity())) {
            try {

                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETBOOKSBYAUTHOR);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETBOOKSBYAUTHOR:
                    onResponseGetAuthorBooks(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAuthorBooks(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {


                    if (responseHandler.getAuthorBook().size() > 0) {
                        arrListAuthorBooks = new ArrayList<BookData>();
                        arrListAuthorBooks.addAll(responseHandler.getAuthorBook());

                        authorBooksAdapter.addAll(arrListAuthorBooks);
                        authorBooksAdapter.notifyDataSetChanged();
                        setEmptyView(false);
                    } else {
                        setEmptyView(false);
                    }
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAuthorBooks api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAuthorBooks Exception : " + e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_MY_BOOKS);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_MY_BOOKS);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
