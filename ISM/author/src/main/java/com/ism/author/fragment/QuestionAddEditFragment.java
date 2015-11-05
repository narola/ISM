package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Utils;
import com.ism.author.autocomplete.ContactsCompletionView;
import com.ism.author.autocomplete.FilteredArrayAdapter;
import com.ism.author.autocomplete.TokenCompleteTextView;
import com.ism.author.model.TagsModel;


/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment implements TokenCompleteTextView.TokenListener {

    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }


    private ContactsCompletionView completionView;
    private TagsModel[] tags;
    private ArrayAdapter<TagsModel> tagsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);
        Utils.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        tags = new TagsModel[]{
                new TagsModel("Physics", "1"),
                new TagsModel("Biology", "2"),
                new TagsModel("Chemistry", "3"),
                new TagsModel("Maths", "4"),
                new TagsModel("Social Science", "5"),
                new TagsModel("Drawing", "6")
        };


        tagsAdapter = new FilteredArrayAdapter<TagsModel>(getActivity(), R.layout.tag_search_layout, tags) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.tag_search_layout, parent, false);
                }

                TagsModel tagModel = getItem(position);

                ((TextView) convertView.findViewById(R.id.tv_tag_name)).setText(tagModel.getTagName());

                return convertView;
            }

            @Override
            protected boolean keepObject(TagsModel tagModel, String mask) {
                mask = mask.toLowerCase();
                return tagModel.getTagName().toLowerCase().startsWith(mask);
            }
        };

        completionView = (ContactsCompletionView) view.findViewById(R.id.searchTagView);
        completionView.setAdapter(tagsAdapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);


    }


    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token : completionView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }

        Utils.showToast("The Tokens Are::::" + sb, getActivity());
    }


    @Override
    public void onTokenAdded(Object token) {

        updateTokenConfirmation();

    }

    @Override
    public void onTokenRemoved(Object token) {

        updateTokenConfirmation();

    }
}
