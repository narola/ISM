package com.ism.realm;

import android.content.Context;
import android.util.Log;

import com.ism.utility.Utility;
import com.ism.ws.model.Comment;
import com.ism.ws.model.FeedImages;
import com.ism.ws.model.Feeds;
import com.ism.ws.model.TrendingQuestion;
import com.ism.ws.model.TrendingQuestionDetails;

import java.util.ArrayList;

import io.realm.RealmResults;
import model.ROFeedComment;
import model.ROFeedImage;
import model.ROFeeds;
import model.ROTrendingQuestion;
import model.ROUser;
import model.ROTrendingQuestionComment;
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
                ROFeeds roFeeds = new ROFeeds();
                roFeeds.setFeedId(Integer.parseInt(arrayList.get(i).getFeedId()));
                roFeeds.setRoUser(studentHelper.getUser(Integer.parseInt(arrayList.get(i).getUserId())));
                ROUser feedBy = studentHelper.getUser(Integer.parseInt(arrayList.get(i).getFeedBy()));
                if (feedBy == null) {
                    feedBy = new ROUser();
                    feedBy.setUserId(Integer.parseInt(arrayList.get(i).getFeedBy()));
                    feedBy.setFullName(arrayList.get(i).getFullName());
                    feedBy.setProfilePicture(arrayList.get(i).getProfilePic());
                    studentHelper.saveUser(feedBy);
                    roFeeds.setFeedBy(feedBy);
                } else {
                    roFeeds.setFeedBy(feedBy);
                }
                roFeeds.setFeedText(arrayList.get(i).getFeedText());
                //ROFeeds.setProfilePic(arrayList.get(i).getProfilePic());
                roFeeds.setAudioLink(arrayList.get(i).getAudioLink());
                roFeeds.setVideoLink(arrayList.get(i).getVideoLink());
                roFeeds.setVideoThumbnail(arrayList.get(i).getVideoThumbnail());
                roFeeds.setTotalComment(Integer.parseInt(arrayList.get(i).getTotalComment()));
                roFeeds.setTotalLike(Integer.parseInt(arrayList.get(i).getTotalLike()));
                roFeeds.setCreatedDate(Utility.getDateFormateMySql(arrayList.get(i).getCreatedDate()));
                if (!arrayList.get(i).getModifiedDate().equals("0000-00-00 00:00:00"))
                    roFeeds.setModifiedDate(Utility.getDateFormateMySql(arrayList.get(i).getModifiedDate()));
                roFeeds.setSelfLike(arrayList.get(i).getLike());
                roFeeds.setIsSync(0);
                roFeeds.setPostedOn(Utility.getDateFormate(arrayList.get(i).getLike()));
                studentHelper.saveFeeds(roFeeds);
                ArrayList<Comment> arrayListComment = arrayList.get(i).getComments();
                if (arrayListComment.size() > 0) {
                    for (int j = 0; j < arrayListComment.size(); j++) {
                        ROFeedComment roFeedComment = new ROFeedComment();
                        roFeedComment.setFeedCommentId(Integer.parseInt(arrayListComment.get(j).getId()));
                        roFeedComment.setComment(arrayListComment.get(j).getComment());
                        roFeedComment.setRoFeed(roFeeds);
                        ROUser commentBy = studentHelper.getUser(Integer.parseInt(arrayListComment.get(j).getCommentBy()));
                        if (commentBy == null) {
                            commentBy = new ROUser();
                            Log.e(TAG, "comment by : " + arrayListComment.get(j).getCommentBy() + " pic : " + arrayListComment.get(j).getProfilePic());
                            commentBy.setUserId(Integer.parseInt(arrayListComment.get(j).getCommentBy()));
                            commentBy.setFullName(arrayListComment.get(j).getFullName());
                            commentBy.setProfilePicture(arrayListComment.get(j).getProfilePic());
                            studentHelper.saveUser(commentBy);
                            roFeedComment.setCommentBy(commentBy);
                        } else {
                            roFeedComment.setCommentBy(commentBy);
                        }
                        roFeedComment.setRoFeed(roFeeds);
                        roFeedComment.setCreatedDate(Utility.getDateFormateMySql(arrayListComment.get(j).getCreatedDate()));
                        studentHelper.saveComments(roFeedComment);
                        //feeds.getComments().add(feedComment);
                    }
                }
                ArrayList<FeedImages> arrayListImages = arrayList.get(i).getFeedImages();
                if (arrayListImages.size() > 0) {
                    for (int j = 0; j < arrayListImages.size(); j++) {
                        ROFeedImage roFeedImage = new ROFeedImage();
                        roFeedImage.setFeedImageId(Integer.parseInt(arrayListImages.get(j).getId()));
                        roFeedImage.setImageLink(arrayListImages.get(j).getImageLink());
                        roFeedImage.setFeed(roFeeds);
                        studentHelper.saveFeedImages(roFeedImage);
                        //ROFeeds.getROFeedImages().add(ROFeedImage);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "saveFeeds Exception : " + e.toString());
        }
    }

    public RealmResults<ROFeeds> getFeeds() {
        return studentHelper.getFeeds();
    }

    public RealmResults<ROFeeds> getUpdatedFeedLikes(boolean status) {
        return studentHelper.managedFeedLikeStatus(status);
    }

    public void saveTrendingQuestion(ArrayList<TrendingQuestion> arrayList, String authorId) {
        ROUser userPostFor = studentHelper.getUser(Integer.parseInt(authorId));
        for (TrendingQuestion question : arrayList) {
            ROTrendingQuestion roTrendingQuestion = new ROTrendingQuestion();
            roTrendingQuestion.setTrendingId(Integer.parseInt(question.getTrendingId()));
            roTrendingQuestion.setQuestionText(question.getQuestionText());
            roTrendingQuestion.setFollowerCount(Integer.parseInt(question.getFollowerCount() == null ? "0" : question.getFollowerCount()));
            roTrendingQuestion.setTotalComment(Integer.parseInt(question.getTotalComment() == null ? "0" : question.getTotalComment()));
            roTrendingQuestion.setIsFollowed(Integer.parseInt(question.getIsFollowed() == null ? "0" : question.getIsFollowed()));
            roTrendingQuestion.setCreatedDate(Utility.getDateFormateMySql(question.getPostedOn()));
            ROUser userPostBy = studentHelper.getUser(Integer.parseInt(question.getPostedByUserId()));

            if (userPostBy == null) {
                userPostBy = new ROUser();
                userPostBy.setUserId(Integer.parseInt(question.getPostedByUserId()));
                userPostBy.setFullName(question.getPostedByUsername());
                userPostBy.setProfilePicture(question.getPostedByPic());
                roTrendingQuestion.setQuestionBy(userPostBy);
            } else
                roTrendingQuestion.setQuestionBy(userPostBy);

            roTrendingQuestion.setQuestionFor(userPostFor);
            studentHelper.saveTrendingQuestion(roTrendingQuestion);
        }
    }

    public RealmResults<ROTrendingQuestion> getTrendingQuestions(String authorId) {

        return studentHelper.getTrendingQuestionResult(authorId,true);
    }

    public void updateFollowedStatus(final String trendingId) {
        final ROTrendingQuestion ROTrendingQuestion = studentHelper.getTrendingQuestion(trendingId);
        studentHelper.realm.beginTransaction();
        Log.e(TAG, "(ROTrendingQuestion.getIsFollowed() : " + ROTrendingQuestion.getIsFollowed());
        ROTrendingQuestion.setIsFollowed(ROTrendingQuestion.getIsFollowed() == 0 ? 1 : 0);//0 is unfollowed 1 is followed
        studentHelper.realm.commitTransaction();
    }

    public void updateTrendingQuestion(TrendingQuestionDetails question) {
        ROTrendingQuestion ROTrendingQuestion = studentHelper.getTrendingQuestion(question.getTrendingId());
        studentHelper.realm.beginTransaction();
        Log.e(TAG, "(ROTrendingQuestion.getTrendingId() : " + ROTrendingQuestion.getTrendingId());
        ROTrendingQuestion.setFollowerCount(Integer.parseInt(question.getFollowerCount() == null ? "0" : question.getFollowerCount()));
        ROTrendingQuestion.setTotalComment(Integer.parseInt(question.getTotalComment() == null ? "0" : question.getTotalComment()));
        ROTrendingQuestion.setAnswerText(question.getAnswerText());
        if (question.getComment().size() > 0) {
            for (Comment comment : question.getComment()) {
                ROTrendingQuestionComment questionComment = new ROTrendingQuestionComment();
                questionComment.setTrendingQuestionCommentId(comment.getCommentId());
                questionComment.setComment(comment.getComment());
                questionComment.setROTrendingQuestion(ROTrendingQuestion);
               ROUser commentBy = studentHelper.getUser(Integer.parseInt(comment.getCommentBy()));
                if (commentBy != null)
                    questionComment.setCommentBy(commentBy);
                else {
                    commentBy = new ROUser();
                    commentBy.setUserId(Integer.parseInt(comment.getCommentBy()));
                    commentBy.setFullName(comment.getFullName());
                    commentBy.setProfilePicture(comment.getProfilePic());
                    questionComment.setCommentBy(commentBy);
                }
                questionComment.setComment(comment.getComment());
                ROTrendingQuestion.getROTrendingQuestionComments().add(questionComment);
               // studentHelper.saveTrendingQuestionComment(questionComment);
            }
        }


        // ROTrendingQuestion.setIsFollowed(ROTrendingQuestion.getIsFollowed() == 0 ? 1 : 0);//0 is unfollowed 1 is followed
        studentHelper.realm.commitTransaction();
    }

//    public void saveTrendingQuestionFollow(String trendingId, String userId) {
//        ROTrendingQuestionFollower follower=studentHelper.getTrendingQuestionFollower(trendingId,userId);
//        if(follower==null){
//            follower=new ROTrendingQuestionFollower();
//            follower.setFollowerBy(studentHelper.getUser(Integer.parseInt(userId)));
//            follower.setROTrendingQuestion(studentHelper.getROTrendingQuestion(trendingId));
//            studentHelper.saveTrendingQuestionFolloer(follower);
//        }
//        else{
//            studentHelper.removeTrendingQuestionFollower(follower.getQuestionFollowerId());
//        }
//    }
}
