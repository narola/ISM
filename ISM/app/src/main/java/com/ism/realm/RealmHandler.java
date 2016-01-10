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
import model.FeedComment;
import model.FeedImage;
import model.TrendingQuestionComment;
import model.User;
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
//                Log.e(TAG, "I item : " + i);
                model.Feeds feeds = new model.Feeds();
                feeds.setFeedId(Integer.parseInt(arrayList.get(i).getFeedId()));
                feeds.setUser(studentHelper.getUser(Integer.parseInt(arrayList.get(i).getUserId())));
                User feedBy = studentHelper.getUser(Integer.parseInt(arrayList.get(i).getFeedBy()));
                if (feedBy == null) {
                    feedBy = new User();
                    feedBy.setUserId(Integer.parseInt(arrayList.get(i).getFeedBy()));
                    feedBy.setFullName(arrayList.get(i).getFullName());
                    feedBy.setProfilePicture(arrayList.get(i).getProfilePic());
                    studentHelper.saveUser(feedBy);
                    feeds.setFeedBy(feedBy);
                } else {
                    feeds.setFeedBy(feedBy);
                }
                feeds.setFeedText(arrayList.get(i).getFeedText());
                //feeds.setProfilePic(arrayList.get(i).getProfilePic());
                feeds.setAudioLink(arrayList.get(i).getAudioLink());
                feeds.setVideoLink(arrayList.get(i).getVideoLink());
                feeds.setVideoThumbnail(arrayList.get(i).getVideoThumbnail());
                feeds.setTotalComment(Integer.parseInt(arrayList.get(i).getTotalComment()));
                feeds.setTotalLike(Integer.parseInt(arrayList.get(i).getTotalLike()));
                feeds.setCreatedDate(Utility.getDateFormateMySql(arrayList.get(i).getCreatedDate()));
                if (!arrayList.get(i).getModifiedDate().equals("0000-00-00 00:00:00"))
                    feeds.setModifiedDate(Utility.getDateFormateMySql(arrayList.get(i).getModifiedDate()));
                feeds.setSelfLike(arrayList.get(i).getLike());
                feeds.setIsSync(0);
                feeds.setPostedOn(Utility.getDateFormate(arrayList.get(i).getLike()));
                studentHelper.saveFeeds(feeds);
                ArrayList<Comment> arrayListComment = arrayList.get(i).getComments();
                if (arrayListComment.size() > 0) {
                    for (int j = 0; j < arrayListComment.size(); j++) {
                        FeedComment feedComment = new FeedComment();
                        feedComment.setFeedCommentId(Integer.parseInt(arrayListComment.get(j).getId()));
                        feedComment.setComment(arrayListComment.get(j).getComment());
                        feedComment.setFeed(feeds);
                        User commentBy = studentHelper.getUser(Integer.parseInt(arrayListComment.get(j).getCommentBy()));
                        if (commentBy == null) {
                            commentBy = new User();
                            Log.e(TAG, "comment by : " + arrayListComment.get(j).getCommentBy() + " pic : " + arrayListComment.get(j).getProfilePic());
                            commentBy.setUserId(Integer.parseInt(arrayListComment.get(j).getCommentBy()));
                            commentBy.setFullName(arrayListComment.get(j).getFullName());
                            commentBy.setProfilePicture(arrayListComment.get(j).getProfilePic());
                            studentHelper.saveUser(commentBy);
                            feedComment.setCommentBy(commentBy);
                        } else {
                            feedComment.setCommentBy(commentBy);
                        }
                        feedComment.setFeed(feeds);
                        feedComment.setCreatedDate(Utility.getDateFormateMySql(arrayListComment.get(j).getCreatedDate()));
                        studentHelper.saveComments(feedComment);
                        //feeds.getComments().add(feedComment);
                    }
                }
                ArrayList<FeedImages> arrayListImages = arrayList.get(i).getFeedImages();
                if (arrayListImages.size() > 0) {
                    for (int j = 0; j < arrayListImages.size(); j++) {
                        FeedImage feedImage = new FeedImage();
                        feedImage.setFeedImageId(Integer.parseInt(arrayListImages.get(j).getId()));
                        feedImage.setImageLink(arrayListImages.get(j).getImageLink());
                        feedImage.setFeed(feeds);
                        studentHelper.saveFeedImages(feedImage);
                        //feeds.getFeedImages().add(feedImage);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "saveFeeds Exception : " + e.toString());
        }
    }

    public RealmResults<model.Feeds> getFeeds(int feed_id, int userId) {
        return studentHelper.getFeeds(feed_id, userId);
    }

    public RealmResults<model.Feeds> getUpdatedFeedLikes(boolean status) {
        return studentHelper.managedFeedLikeStatus(status);
    }

    public void saveTrendingQuestion(ArrayList<TrendingQuestion> arrayList, String authorId) {
        User userPostFor = studentHelper.getUser(Integer.parseInt(authorId));
        for (TrendingQuestion question : arrayList) {
            model.TrendingQuestion trendingQuestion = new model.TrendingQuestion();
            trendingQuestion.setTrendingId(Integer.parseInt(question.getTrendingId()));
            trendingQuestion.setQuestionText(question.getQuestionText());
            trendingQuestion.setFollowerCount(Integer.parseInt(question.getFollowerCount() == null ? "0" : question.getFollowerCount()));
            trendingQuestion.setTotalComment(Integer.parseInt(question.getTotalComment() == null ? "0" : question.getTotalComment()));
            trendingQuestion.setIsFollowed(Integer.parseInt(question.getIsFollowed() == null ? "0" : question.getIsFollowed()));
            trendingQuestion.setCreatedDate(Utility.getDateFormateMySql(question.getPostedOn()));
            User userPostBy = studentHelper.getUser(Integer.parseInt(question.getPostedByUserId()));

            if (userPostBy == null) {
                userPostBy = new User();
                userPostBy.setUserId(Integer.parseInt(question.getPostedByUserId()));
                userPostBy.setFullName(question.getPostedByUsername());
                userPostBy.setProfilePicture(question.getPostedByPic());
                trendingQuestion.setQuestionBy(userPostBy);
            } else
                trendingQuestion.setQuestionBy(userPostBy);

            trendingQuestion.setQuestionFor(userPostFor);
            studentHelper.saveTrendingQuestion(trendingQuestion);
        }
    }

    public RealmResults<model.TrendingQuestion> getTrendingQuestions(String authorId) {

        return studentHelper.getTrendingQuestionResult(authorId,true);
    }

    public void updateFollowedStatus(final String trendingId) {
        final model.TrendingQuestion trendingQuestion = studentHelper.getTrendingQuestion(trendingId);
        studentHelper.realm.beginTransaction();
        Log.e(TAG, "(trendingQuestion.getIsFollowed() : " + trendingQuestion.getIsFollowed());
        trendingQuestion.setIsFollowed(trendingQuestion.getIsFollowed() == 0 ? 1 : 0);//0 is unfollowed 1 is followed
        studentHelper.realm.commitTransaction();
    }

    public void updateTrendingQuestion(TrendingQuestionDetails question) {
        model.TrendingQuestion trendingQuestion = studentHelper.getTrendingQuestion(question.getTrendingId());
        studentHelper.realm.beginTransaction();
        Log.e(TAG, "(trendingQuestion.getTrendingId() : " + trendingQuestion.getTrendingId());
        trendingQuestion.setFollowerCount(Integer.parseInt(question.getFollowerCount() == null ? "0" : question.getFollowerCount()));
        trendingQuestion.setTotalComment(Integer.parseInt(question.getTotalComment() == null ? "0" : question.getTotalComment()));
        trendingQuestion.setAnswerText(question.getAnswerText());
        if (question.getComment().size() > 0) {
            for (Comment comment : question.getComment()) {
                model.TrendingQuestionComment questionComment = new TrendingQuestionComment();
                questionComment.setTrendingQuestionCommentId(comment.getCommentId());
                questionComment.setComment(comment.getComment());
                questionComment.setTrendingQuestion(trendingQuestion);
                User commentBy = studentHelper.getUser(Integer.parseInt(comment.getCommentBy()));
                if (commentBy != null)
                    questionComment.setCommentBy(commentBy);
                else {
                    commentBy = new User();
                    commentBy.setUserId(Integer.parseInt(comment.getCommentBy()));
                    commentBy.setFullName(comment.getFullName());
                    commentBy.setProfilePicture(comment.getProfilePic());
                    questionComment.setCommentBy(commentBy);
                }
                questionComment.setComment(comment.getComment());
                trendingQuestion.getTrendingQuestionComments().add(questionComment);
               // studentHelper.saveTrendingQuestionComment(questionComment);
            }
        }


        // trendingQuestion.setIsFollowed(trendingQuestion.getIsFollowed() == 0 ? 1 : 0);//0 is unfollowed 1 is followed
        studentHelper.realm.commitTransaction();
    }

//    public void saveTrendingQuestionFollow(String trendingId, String userId) {
//        TrendingQuestionFollower follower=studentHelper.getTrendingQuestionFollower(trendingId,userId);
//        if(follower==null){
//            follower=new TrendingQuestionFollower();
//            follower.setFollowerBy(studentHelper.getUser(Integer.parseInt(userId)));
//            follower.setTrendingQuestion(studentHelper.getTrendingQuestion(trendingId));
//            studentHelper.saveTrendingQuestionFolloer(follower);
//        }
//        else{
//            studentHelper.removeTrendingQuestionFollower(follower.getQuestionFollowerId());
//        }
//    }
}
