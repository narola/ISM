package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ism.author.R;
import com.ism.author.Utility.Utils;


/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment {


    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    Button flip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);
        Utils.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        flip = (Button) view.findViewById(R.id.flip);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddQuestionFragment) mFragment).flipCard();

            }
        });

    }

}
