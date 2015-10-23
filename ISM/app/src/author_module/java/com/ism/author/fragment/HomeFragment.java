package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.AuthorHostActivity;
import com.ism.author.asynctask.API_METHOD_NAME;
import com.ism.author.interfaces.OnApiResponseListener;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/*
* This is the homefragment containg the newsfeed.
*/
public class HomeFragment extends Fragment implements OnApiResponseListener {


    private static final String TAG = HomeFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;

    public static HomeFragment newInstance() {
        HomeFragment fragHome = new HomeFragment();
        return fragHome;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    ImageView imgAddEmoticonsInPost, imgAttachFileInPost, imgAttachImageInPost, imgTagInPost;
    EditText etWritePost;
    TextView txtAddPost;
    ListView lvPostFeeds;

    OnClickListener onClickAttachFile, onClickAddPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initGlobal(view);

        return view;
    }

    private void initGlobal(View view) {

        imgAddEmoticonsInPost = (ImageView) view.findViewById(R.id.img_add_emoticons_in_post);
        imgAttachFileInPost = (ImageView) view.findViewById(R.id.img_attach_file_in_post);
        imgAttachImageInPost = (ImageView) view.findViewById(R.id.img_attach_image_in_post);
        imgTagInPost = (ImageView) view.findViewById(R.id.img_tag_in_post);

        etWritePost = (EditText) view.findViewById(R.id.et_writePost);
        txtAddPost = (TextView) view.findViewById(R.id.txt_add_post);

        lvPostFeeds = (ListView) view.findViewById(R.id.lv_post_feeds);


        onClickAttachFile = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAttachFileClick(view);


            }
        };

        imgAddEmoticonsInPost.setOnClickListener(onClickAttachFile);
        imgAttachFileInPost.setOnClickListener(onClickAttachFile);
        imgAttachImageInPost.setOnClickListener(onClickAttachFile);
        imgTagInPost.setOnClickListener(onClickAttachFile);

        onClickAddPost = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAddPostClick(view);


            }
        };


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_HOME);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_HOME);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    private void onAddPostClick(View view) {

        if (view == txtAddPost) {

        }


    }

    private void onAttachFileClick(View view) {

        if (view == imgAddEmoticonsInPost) {

        } else if (view == imgAttachFileInPost) {

        } else if (view == imgAttachImageInPost) {

        } else if (view == imgTagInPost) {

        }

    }


    @Override
    public void onResponseReceived(API_METHOD_NAME api_method_name, String response) {


        if (api_method_name == API_METHOD_NAME.get_all_feeds) {


        }


    }
}
