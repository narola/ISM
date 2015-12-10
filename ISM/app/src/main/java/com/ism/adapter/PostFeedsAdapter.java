package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.dialog.TagStudyMatesDialog;
import com.ism.dialog.ViewAllCommentsDialog;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Comment;
import com.ism.ws.model.Feeds;

import java.util.ArrayList;

import io.realm.RealmResults;
import model.FeedComment;
import model.FeedLike;
import model.User;
import realmhelper.StudentHelper;

/**
 * Created by c161 on 30/10/15.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder>
        implements WebserviceWrapper.WebserviceResponse, TagStudyMatesDialog.TagStudyMatesListener {

    private static final String TAG = PostFeedsAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Feeds> arrListFeeds;

    private int addCommentFeedPosition = -1;
    private int tagFeedPosition = -1;
    private StudentHelper studentHelper;
    private int viewAllFeedId = -1;

    public PostFeedsAdapter(Context context, ArrayList<Feeds> arrListFeeds) {
        this.context = context;
        this.arrListFeeds = arrListFeeds;
        studentHelper = new StudentHelper(context);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imgDp;
        public ImageView imgLike, imgTagStudyMates;
        public TextView txtName, txtPost, txtLikes, txtComments, txtViewAll;
        public EditText etComment;
        public Button btnComment;
        public LinearLayout llComments;

        public ViewHolder(View view) {
            super(view);
            imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
            imgLike = (ImageView) view.findViewById(R.id.img_like);
            imgTagStudyMates = (ImageView) view.findViewById(R.id.img_tag_study_mates);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtPost = (TextView) view.findViewById(R.id.txt_post);
            txtLikes = (TextView) view.findViewById(R.id.txt_likes);
            txtComments = (TextView) view.findViewById(R.id.txt_comments);
            txtViewAll = (TextView) view.findViewById(R.id.txt_view_all);
            etComment = (EditText) view.findViewById(R.id.et_comment);
            btnComment = (Button) view.findViewById(R.id.btn_comment);
            llComments = (LinearLayout) view.findViewById(R.id.ll_comments);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
//			imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgDp, ISMStudent.options);
            holder.txtName.setText(arrListFeeds.get(position).getFullName());
            holder.txtPost.setText(arrListFeeds.get(position).getFeedText());
            holder.txtLikes.setText(arrListFeeds.get(position).getTotalLike());
            holder.txtComments.setText(arrListFeeds.get(position).getTotalComment());

            if (addCommentFeedPosition != -1 && addCommentFeedPosition == position) {
                holder.etComment.setText("");
                addCommentFeedPosition = -1;
            }

            holder.llComments.removeAllViews();

            if (arrListFeeds.get(position).getComments() != null) {
                holder.txtViewAll.setVisibility(Integer.parseInt(arrListFeeds.get(position).getTotalComment()) > 2 ? View.VISIBLE : View.GONE);
                for (int i = 0; i < arrListFeeds.get(position).getComments().size(); i++) {
                    holder.llComments.addView(getCommentView(arrListFeeds.get(position).getComments().get(i)));
                }
            }

            holder.imgLike.setActivated(arrListFeeds.get(position).getLike().equals("1"));

            holder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrListFeeds.get(position).getLike().equals("1")) {
                        arrListFeeds.get(position).setLike("0");
                        arrListFeeds.get(position).setTotalLike("" + (Integer.parseInt(arrListFeeds.get(position).getTotalLike()) - 1));
                    } else {
                        arrListFeeds.get(position).setLike("1");
                        arrListFeeds.get(position).setTotalLike("" + (Integer.parseInt(arrListFeeds.get(position).getTotalLike()) + 1));
                    }
                    holder.imgLike.setActivated(arrListFeeds.get(position).getLike().equals("1"));
                    updateFeedLike(Integer.parseInt(arrListFeeds.get(position).getFeedId()),Integer.parseInt(arrListFeeds.get(position).getLike()));
                    notifyDataSetChanged();
                }
            });

            holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utility.isConnected(context)) {
                        viewAllFeedId = position;
                        callApiGetAllComments(position);
                    } else {
                        setUpData(studentHelper.getFeedComments(Integer.parseInt(arrListFeeds.get(position).getFeedId())));
                        Utility.alertOffline(context);
                    }
                }
            });

            holder.imgTagStudyMates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utility.isConnected(context)) {
                        tagFeedPosition = position;
                        callApiGetStudyMates();
                    } else {
                        Utility.alertOffline(context);
                    }
                }
            });

            holder.btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String comment = holder.etComment.getText().toString().trim();
                    if (comment != null && comment.length() > 0) {
                        if (Utility.isConnected(context)) {
                            callApiComment(position, comment);
                        } else {
                            Utility.alertOffline(context);
                        }
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }

    private void updateFeedLike(int feedId, int like) {
        try {
            FeedLike feedLike=new FeedLike();
            feedLike.setFeed(studentHelper.getFeeds(feedId).get(0));
            feedLike.setFeedLikeId(like);
           // studentHelper.saveFeedLikes(f);
        }
        catch (Exception e){
            Debug.i(TAG,"updateFeedLike Exception : "+e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return arrListFeeds.size();
    }

    private View getCommentView(Comment comment) {
        View view = null;
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.item_comment_post, null);

            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + comment.getProfilePic(),
                    (CircleImageView) view.findViewById(R.id.img_dp), ISMStudent.options);
//			imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
//					(CircleImageView) view.findViewById(R.id.img_dp), ISMStudent.options);

            ((TextView) view.findViewById(R.id.txt_name)).setText(comment.getFullName());
            ((TextView) view.findViewById(R.id.txt_comment)).setText(comment.getComment());
            ((TextView) view.findViewById(R.id.txt_duration)).setText(com.ism.commonsource.utility.Utility.getTimeDuration(comment.getCreatedDate()));
        } catch (Exception e) {
            Log.e(TAG, "getCommentView Exception : " + e.toString());
        }
        return view;
    }

    private void callApiComment(int position, String comment) {
        try {
            addCommentFeedPosition = position;
            Attribute attribute = new Attribute();
            attribute.setFeedId(arrListFeeds.get(position).getFeedId());
            attribute.setCommentBy(Global.strUserId);
            attribute.setComment(comment);

            new WebserviceWrapper(context, attribute, this).new WebserviceCaller()
                    .execute(WebConstants.ADD_COMMENT);
        } catch (Exception e) {
            Log.e(TAG, "callApiComment Exception : " + e.toString());
        }
    }

    private void callApiGetAllComments(int position) {
        try {
            Attribute attribute = new Attribute();
            attribute.setFeedId(arrListFeeds.get(position).getFeedId());

            new WebserviceWrapper(context, attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_COMMENTS);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetAllComments Exception : " + e.toString());
        }
    }

    public void callApiGetStudyMates() {
        try {
            Attribute attribute = new Attribute();
            attribute.setUserId(Global.strUserId);

            new WebserviceWrapper(context, attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_STUDYMATES);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetStudyMates Exception : " + e.toString());
        }
    }

    @Override
    public void tagStudyMates(String[] arrTagUser) {
        if (Utility.isConnected(context)) {
            try {
                Attribute attribute = new Attribute();
                attribute.setFeedId(arrListFeeds.get(tagFeedPosition).getFeedId());
                attribute.setTaggedBy(Global.strUserId);
                attribute.setTaggedUserIds(arrTagUser);

                new WebserviceWrapper(context, attribute, this).new WebserviceCaller()
                        .execute(WebConstants.TAG_STUDY_MATES);
            } catch (Exception e) {
                Log.e(TAG, "tagStudyMates Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(context);
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            if (object != null) {
                switch (apiCode) {
                    case WebConstants.ADD_COMMENT:
                        onResponseAddComment(object);
                        break;
                    case WebConstants.GET_ALL_COMMENTS:
                        onResponseGetAllComments(object);
                        break;
                    case WebConstants.GET_ALL_STUDYMATES:
                        onResponseGetAllStudyMates(object);
                        break;
                    case WebConstants.TAG_STUDY_MATES:
                        onResponseTagStudyMates(object);
                        break;
                }
            } else {
                Log.e(TAG, "onResponse ApiCall Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseTagStudyMates(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            tagFeedPosition = -1;
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                Toast.makeText(context, "Tag done!", Toast.LENGTH_SHORT).show();
            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                Toast.makeText(context, "Tag failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseTagStudyMates Exception : " + e.toString());
        }
    }

    private void onResponseGetAllStudyMates(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                if (responseHandler.getStudymates().size() > 0) {
                    TagStudyMatesDialog tagStudyMatesDialog = new TagStudyMatesDialog(context, responseHandler.getStudymates(), this);
                    tagStudyMatesDialog.show();
                }
            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                Toast.makeText(context, responseHandler.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAllStudyMates Exception : " + e.toString());
        }
    }

    private void onResponseGetAllComments(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                ParseAllData(responseHandler.getComments());
            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                Toast.makeText(context, responseHandler.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAllComments Exception : " + e.toString());
        }
    }

    private void onResponseAddComment(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                arrListFeeds.get(addCommentFeedPosition).setTotalComment("" + (Integer.parseInt(arrListFeeds.get(addCommentFeedPosition).getTotalComment()) + 1));
                notifyDataSetChanged();
            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                Toast.makeText(context, R.string.msg_failed_comment, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseAddComment Exception : " + e.toString());
        }
    }

    private void ParseAllData(ArrayList<Comment> comments) {
        try {
            if (comments.size() > 0) {
                for (int i = 0; i < comments.size(); i++) {
                    FeedComment feedComment = new FeedComment();
                    feedComment.setFeedCommentId(Integer.parseInt(comments.get(i).getId()));
                    User user = new User();
                    user.setFullName(comments.get(i).getFullName());
                    user.setProfilePicture(comments.get(i).getProfilePic());
                    user.setUserId(Integer.parseInt(comments.get(i).getCommentBy()));
                    studentHelper.saveUser(user);
                    feedComment.setCommentBy(user);
                    model.Feeds feeds = new model.Feeds();
                    feeds.setFeedId(Integer.parseInt(arrListFeeds.get(viewAllFeedId).getFeedId()));
                    feedComment.setFeed(feeds);
                    feedComment.setComment(comments.get(i).getComment());
                    studentHelper.FeedCommments(feedComment);
                }
            }
            setUpData(studentHelper.getFeedComments(Integer.parseInt(arrListFeeds.get(viewAllFeedId).getFeedId())));

        } catch (Exception e) {
            Log.e(TAG, "ParseAllData Exception : " + e.toString());
        }
    }

    public void setUpData(RealmResults<model.FeedComment> realmResult) {
        try {
            ArrayList<Comment> arrayList = new ArrayList<>();
            if(realmResult.size()>0) {
                for (int i = 0; i < realmResult.size(); i++) {
                    Comment comment = new Comment();
                    comment.setFullName((realmResult.get(i).getCommentBy() == null ? realmResult.get(i).getCommentBy() + "" : realmResult.get(i).getCommentBy().getFullName()));
                    comment.setComment(realmResult.get(i).getComment());
                    comment.setProfilePic((realmResult.get(i).getCommentBy() == null ? realmResult.get(i).getCommentBy() + "" : realmResult.get(i).getCommentBy().getProfilePicture()));
                    comment.setId(String.valueOf(realmResult.get(i).getFeedCommentId()));
                    arrayList.add(comment);
                }
            }
            ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(context, arrayList);
            viewAllCommentsDialog.show();
        } catch (Exception e) {
            Debug.i(TAG, "setUpData Exception :" + e.getLocalizedMessage());
        }
    }
}