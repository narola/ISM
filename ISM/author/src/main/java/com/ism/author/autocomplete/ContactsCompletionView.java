package com.ism.author.autocomplete;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.model.TagsModel;

/**
 * Sample token completion view for basic contact info
 * <p/>
 * Created on 9/12/13.
 *
 * @author mgod
 */
public class ContactsCompletionView extends TokenCompleteTextView<TagsModel> {

    public ContactsCompletionView(Context context) {
        super(context);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(TagsModel tagsModel) {

        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) l.inflate(R.layout.tag_layout, (ViewGroup) ContactsCompletionView.this.getParent(), false);
        ((TextView) view.findViewById(R.id.tv_tag_name)).setText(tagsModel.getTagName());

        return view;
    }

    @Override
    protected TagsModel defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not


//        int index = completionText.indexOf('@');
//        if (index == -1) {
//            return new Person(completionText, completionText.replace(" ", "") + "@example.com");
//        } else {
//            return new Person(completionText.substring(0, index), completionText);
//        }

        //this is to avoid tag generation from random added text.

        return new TagsModel("", "-1");
    }
}
