package com.ism.teacher.post;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.helper.CircleImageView;

/**
 * Created by c75 on 19/10/15.
 */
public class TeacherPostActivity extends Activity implements View.OnClickListener {
    CircleImageView imgDpPostCreator;
    TextView txtUsernamePostCreator, txtPostContent, txtPostLikeCounter, txtPostCommentsCounter, txtViewAllComments;
    ImageView imgLikePost, imgComments, imgPostOption;
    EditText etWritePost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_teacher_post);

        initViews();

    }

    private void initViews() {
        imgDpPostCreator = (CircleImageView) findViewById(R.id.img_dp_post_creator);

        txtUsernamePostCreator = (TextView) findViewById(R.id.txt_username_post_creator);
        txtPostContent = (TextView) findViewById(R.id.txt_post_content);
        txtPostLikeCounter = (TextView) findViewById(R.id.txt_post_like_counter);
        txtPostCommentsCounter = (TextView) findViewById(R.id.txt_post_comments_counter);
        txtViewAllComments = (TextView) findViewById(R.id.txt_view_all_comments);

        imgLikePost = (ImageView) findViewById(R.id.img_like_post);
        imgComments = (ImageView) findViewById(R.id.img_comments);
        imgPostOption = (ImageView) findViewById(R.id.img_post_option);

        etWritePost = (EditText) findViewById(R.id.et_writePost);
    }

    @Override
    public void onClick(View view) {

    }
}
