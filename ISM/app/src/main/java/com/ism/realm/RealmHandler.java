package com.ism.realm;

import android.content.Context;
import android.util.Log;

import com.ism.utility.Utility;
import com.ism.ws.model.Comment;
import com.ism.ws.model.FeedImages;
import com.ism.ws.model.Feeds;

import java.util.ArrayList;

import io.realm.RealmResults;
import model.ROFeedComment;
import model.ROFeedImage;
import model.ROFeeds;
import model.ROUser;
import realmhelper.StudentHelper;

/**
 * Created by c162 on 07/01/16.
 */
public class RealmHandler {

    private static final String TAG = RealmHandler.class.getSimpleName();
    private StudentHelper studentHelper;

    public RealmHandler(Context context) {
        studentHelper = new StudentHelper(context);
    }

    public void removeRealm() {
        studentHelper.realm.close();
    }

    public void saveFeeds(ArrayList<Feeds> arrayList) {
        try {
            for (int i = 0; i < arrayList.size(); i++) {
                Log.e(TAG, "I item : " + i);
                ROFeeds ROFeeds = new ROFeeds();
                ROFeeds.setFeedId(Integer.parseInt(arrayList.get(i).getFeedId()));
                ROFeeds.setRoUser(studentHelper.getUser(Integer.parseInt(arrayList.get(i).getUserId())));
                ROUser feedBy = studentHelper.getUser(Integer.parseInt(arrayList.get(i).getFeedBy()));
                if (feedBy == null) {
                    feedBy = new ROUser();
                    feedBy.setUserId(Integer.parseInt(arrayList.get(i).getFeedBy()));
                    feedBy.setFullName(arrayList.get(i).getFullName());
                    feedBy.setProfilePicture(arrayList.get(i).getProfilePic());
                    studentHelper.saveUser(feedBy);
                    ROFeeds.setFeedBy(feedBy);
                } else {
                    ROFeeds.setFeedBy(feedBy);
                }
                ROFeeds.setFeedText(arrayList.get(i).getFeedText());
                //ROFeeds.setProfilePic(arrayList.get(i).getProfilePic());
                ROFeeds.setAudioLink(arrayList.get(i).getAudioLink());
                ROFeeds.setVideoLink(arrayList.get(i).getVideoLink());
                ROFeeds.setVideoThumbnail(arrayList.get(i).getVideoThumbnail());
                ROFeeds.setTotalComment(Integer.parseInt(arrayList.get(i).getTotalComment()));
                ROFeeds.setTotalLike(Integer.parseInt(arrayList.get(i).getTotalLike()));
                ROFeeds.setCreatedDate(Utility.getDateFormateMySql(arrayList.get(i).getCreatedDate()));
                if (!arrayList.get(i).getModifiedDate().equals("0000-00-00 00:00:00"))
                    ROFeeds.setModifiedDate(Utility.getDateFormateMySql(arrayList.get(i).getModifiedDate()));
                ROFeeds.setSelfLike(arrayList.get(i).getLike());
                ROFeeds.setIsSync(0);
                ROFeeds.setPostedOn(Utility.getDateFormate(arrayList.get(i).getLike()));
                studentHelper.saveFeeds(ROFeeds);
                ArrayList<Comment> arrayListComment = arrayList.get(i).getComments();
                if (arrayListComment.size() > 0) {
                    for (int j = 0; j < arrayListComment.size(); j++) {
                        ROFeedComment ROFeedComment = new ROFeedComment();
                        ROFeedComment.setFeedCommentId(Integer.parseInt(arrayListComment.get(j).getId()));
                        ROFeedComment.setComment(arrayListComment.get(j).getComment());
                        ROFeedComment.setFeed(ROFeeds);
                        ROUser commentBy = studentHelper.getUser(Integer.parseInt(arrayListComment.get(j).getCommentBy()));
                        if (commentBy == null) {
                            commentBy = new ROUser();
                            Log.e(TAG, "comment by : " + arrayListComment.get(j).getCommentBy() + " pic : " + arrayListComment.get(j).getProfilePic());
                            commentBy.setUserId(Integer.parseInt(arrayListComment.get(j).getCommentBy()));
                            commentBy.setFullName(arrayListComment.get(j).getFullName());
                            commentBy.setProfilePicture(arrayListComment.get(j).getProfilePic());
                            studentHelper.saveUser(commentBy);
                            ROFeedComment.setCommentBy(commentBy);
                        } else {
                            ROFeedComment.setCommentBy(commentBy);
                        }
                        ROFeedComment.setFeed(ROFeeds);
                        ROFeedComment.setCreatedDate(Utility.getDateFormateMySql(arrayListComment.get(j).getCreatedDate()));
                        studentHelper.saveComments(ROFeedComment);
                        //feeds.getComments().add(feedComment);
                    }
                }
                ArrayList<FeedImages> arrayListImages = arrayList.get(i).getFeedImages();
                if (arrayListImages.size() > 0) {
                    for (int j = 0; j < arrayListImages.size(); j++) {
                        ROFeedImage ROFeedImage = new ROFeedImage();
                        ROFeedImage.setFeedImageId(Integer.parseInt(arrayListImages.get(j).getId()));
                        ROFeedImage.setImageLink(arrayListImages.get(j).getImageLink());
                        ROFeedImage.setFeed(ROFeeds);
                        studentHelper.saveFeedImages(ROFeedImage);
                        //ROFeeds.getROFeedImages().add(ROFeedImage);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "saveFeeds Exception : " + e.toString());
        }
    }

    public RealmResults<ROFeeds> getFeeds(int feed_id, int userId) {
        return studentHelper.getFeeds(feed_id, userId);
    }

    public RealmResults<ROFeeds> getUpdatedFeedLikes(boolean status) {
        return studentHelper.managedFeedLikeStatus(status);
    }
}
