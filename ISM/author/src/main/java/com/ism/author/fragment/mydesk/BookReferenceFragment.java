package com.ism.author.fragment.mydesk;

import android.app.Fragment;
import android.view.View;

import com.ism.author.ws.helper.WebserviceWrapper;

/**
 * Created by c166 on 21/12/15.
 */
public class BookReferenceFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = BookReferenceFragment.class.getSimpleName();
    private View view;
    private Fragment mFragment;


    public static BookReferenceFragment newInstance(Fragment fragment) {
        BookReferenceFragment bookReferenceFragment = new BookReferenceFragment();
        bookReferenceFragment.mFragment = fragment;
        return bookReferenceFragment;
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

    }
}
