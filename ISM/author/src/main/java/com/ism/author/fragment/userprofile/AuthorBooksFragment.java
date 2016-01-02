package com.ism.author.fragment.userprofile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.R;
import com.ism.author.ws.helper.WebserviceWrapper;

/**
 * Created by c166 on 01/01/16.
 */
public class AuthorBooksFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = AuthorBooksFragment.class.getSimpleName();
    private View view;

    public AuthorBooksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_books, container, false);

        return view;
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

    }
}
