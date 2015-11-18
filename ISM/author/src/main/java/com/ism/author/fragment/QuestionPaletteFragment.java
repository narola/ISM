package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.R;

/**
 * Created by c166 on 16/11/15.
 */
public class QuestionPaletteFragment extends Fragment {


    private static final String TAG = QuestionPaletteFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionPaletteFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_palette, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

    }
}
