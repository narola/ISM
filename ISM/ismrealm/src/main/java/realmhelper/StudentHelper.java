package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmResults;
import model.ROAdminConfig;
import model.ROAuthorProfile;
import model.ROClassrooms;
import model.ROCourses;
import model.ROFeedComment;
import model.ROFeedImage;
import model.ROFeeds;
import model.RONotes;
import model.ROSchool;
import model.ROStudentProfile;
import model.ROSubjects;
import model.ROTrendingQuestion;
import model.ROTutorialGroupDiscussion;
import model.ROTutorialTopic;
import model.ROUser;
import model.ROTrendingQuestionComment;
import model.ROTrendingQuestionFollower;

/**
 * Created by c161 on 08/12/15.
 */
public class StudentHelper {

    private static final String TAG = StudentHelper.class.getSimpleName();

    public Realm realm;
    private RealmResults<ROFeeds> ROFeedsRealmResults;
    private RealmResults<RONotes> RONotesRealmResults;
    private RealmResults<ROTrendingQuestion> ROTrendingQuestionRealmResults;
    private ROTrendingQuestionFollower ROTrendingQuestionFollowerResults;
    private ROTrendingQuestion ROTrendingQuestion;

    public StudentHelper(Context context) {
        realm = RealmAdaptor.getInstance(context);
    }

    /**
     * use to save user data in ISM database.
     */
    public void saveUser(ROUser ROUser) {
        Log.e(TAG, "saveUser : " + ROUser.getUserId());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ROUser);
        realm.commitTransaction();
    }

    /**
     * Save admin config data
     *
     * @param ROAdminConfig
     */
    public void saveAdminConfig(ROAdminConfig ROAdminConfig) {
        try {
            Number configId = realm.where(ROAdminConfig.class).max("configId");
            long newId = 1;
            if (configId != null) {
                newId = (long) configId + 1;
            }
            realm.beginTransaction();
            ROAdminConfig.setConfigId((int) newId);
            realm.copyToRealmOrUpdate(ROAdminConfig);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveAdminConfig Exception : " + e.toString());
        }
    }

	/*User user = new User();
    user.setFirstName("krunal");
//  UserHelper user1 = new UserHelper(user,getActivity());
	user.saveUser();
	user  =null;
	System.gc();*/

    public void destroy() {
        if (realm != null) {
            realm.close();
        }
    }

    public String getGlobalPassword() {
        RealmResults<ROAdminConfig> ROAdminConfigs = realm.where(ROAdminConfig.class)
                .equalTo("configKey", "globalPassword")
                .findAll();
        if (ROAdminConfigs != null && ROAdminConfigs.size() > 0) {
            return ROAdminConfigs.get(0).getConfigValue();
        } else {
            return null;
        }
    }

    public int getFridayExamAnswerScore() {
        RealmResults<ROAdminConfig> ROAdminConfigs = realm.where(ROAdminConfig.class)
                .equalTo("configKey", "fridayInternalExamScore")
                .findAll();
        if (ROAdminConfigs != null && ROAdminConfigs.size() > 0) {
            return Integer.parseInt(ROAdminConfigs.get(0).getConfigValue());
        } else {
            return 0;
        }
    }

    public ROAdminConfig getActiveHoursStartTime() {
        RealmResults<ROAdminConfig> ROAdminConfigs = realm.where(ROAdminConfig.class)
                .equalTo("configKey", "activeHoursStartTime")
                .findAll();
        if (ROAdminConfigs != null && ROAdminConfigs.size() > 0) {
            return ROAdminConfigs.get(0);
        } else {
            return null;
        }
    }

    public ROAdminConfig getActiveHoursEndTime() {
        RealmResults<ROAdminConfig> ROAdminConfigs = realm.where(ROAdminConfig.class)
                .equalTo("configKey", "activeHoursEndTime")
                .findAll();
        if (ROAdminConfigs != null && ROAdminConfigs.size() > 0) {
            return ROAdminConfigs.get(0);
        } else {
            return null;
        }
    }

    public void saveFeeds(ROFeeds ROFeeds) {

        // remaining : to update feed with local id;
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ROFeeds);
        realm.commitTransaction();
    }

    /**
     * if feedId is -1 then its return the all feeds
     * else return the single record of @param feedId
     *
     * @param feedId
     * @param userId
     * @return
     */
    public RealmResults<ROFeeds> getFeeds(final int feedId, final int userId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (feedId != -1) {
                    ROFeedsRealmResults = realm.where(ROFeeds.class).equalTo("feedId", feedId).equalTo("user.userId", userId).findAll();
                } else {
                    ROFeedsRealmResults = realm.where(ROFeeds.class).equalTo("user.userId", userId).findAll();
                }
            }
        });
        Log.e(TAG, "getFeeds ROFeedsRealmResults.size: " + ROFeedsRealmResults.size());
        return ROFeedsRealmResults;
    }


    /**
     * get the User
     *
     * @param user_id
     * @return
     */
    public ROUser getUser(int user_id) {
        realm.beginTransaction();
        ROUser ROUser = realm.where(ROUser.class).equalTo("userId", user_id).findFirst();
        Log.i(TAG, "getUser : " + ROUser);
        realm.commitTransaction();
        return ROUser;
    }


    /**
     * return the all the comments of @param feedId
     *
     * @param feedId
     * @return
     */
    public RealmResults<ROFeedComment> getFeedComments(int feedId) {
        realm.beginTransaction();
        RealmResults<ROFeedComment> feedsRealmResults = realm.where(ROFeedComment.class).equalTo("feed.feedId", feedId).findAll();
        Log.e(TAG, "all comments : " + feedsRealmResults);
        Log.e(TAG, "getFeedComments ROFeedsRealmResults.size: " + feedsRealmResults.size());

        realm.commitTransaction();
        return feedsRealmResults;
    }

//    /**
//     * store feedlike records
//     *
//     * @param feedLike
//     */
//    public void saveFeedLikes(ROFeedLike feedLike) {
//        realm.beginTransaction();
//        Number feedLikeId = realm.where(ROFeedLike.class).max("feedLikeId");
//        long newId = 1;
//        if (feedLikeId != null) {
//            newId = (long) feedLikeId + 1;
//        }
//        feedLike.setFeedLikeId((int) newId);
//        realm.copyToRealmOrUpdate(feedLike);
//        realm.commitTransaction();
//    }

    public void updateFeedLikes(ROFeeds ROFeeds) {
        ROFeeds toUpdateROFeeds = realm.where(ROFeeds.class).equalTo("feedId", ROFeeds.getFeedId()).findFirst();
        realm.beginTransaction();
        toUpdateROFeeds.setSelfLike(ROFeeds.getSelfLike().equals("0") ? "1" : "0");
        toUpdateROFeeds.setTotalLike(ROFeeds.getSelfLike().equals("0") ? ROFeeds.getTotalLike() - 1 : ROFeeds.getTotalLike() + 1);
        toUpdateROFeeds.setIsSync(ROFeeds.isSync() == 0 ? 1 : 0);
        Log.i(TAG, "updateFeedLikes : " + toUpdateROFeeds.getSelfLike() + "--" + toUpdateROFeeds.getFeedId() + "--" + toUpdateROFeeds.isSync());
        realm.commitTransaction();
    }

    public void updateTotalComments(ROFeeds ROFeeds) {
        ROFeeds toUpdateROFeeds = realm.where(ROFeeds.class).equalTo("feedId", ROFeeds.getFeedId()).findFirst();
        realm.beginTransaction();
        toUpdateROFeeds.setTotalComment(ROFeeds.getTotalComment() + 1);
        //toUpdateROFeeds.getComments().add(ROFeeds.getComments().get(0));
        realm.commitTransaction();
    }

    /**
     * @param statusUpdation
     * @return
     */
    public RealmResults<ROFeeds> managedFeedLikeStatus(boolean statusUpdation) {
//    public RealmResults<ROFeeds> managedFeedLikeStatus(Date lastSynch, Date modified) {
        realm.beginTransaction();
        RealmResults<ROFeeds> ROFeedsRealmResults = realm.where(ROFeeds.class).equalTo("isSync", 1).findAll();
        Log.i(TAG, "managedFeedLikeStatus ROFeedsRealmResults.size: " + ROFeedsRealmResults.size());
        if (statusUpdation) {
            //after sync
            for (int i = 0; i < ROFeedsRealmResults.size(); i++)
                ROFeedsRealmResults.get(i).setIsSync(0);
        }
        realm.commitTransaction();
        if (ROFeedsRealmResults.size() == 0) {
            return null;
        } else {
            return ROFeedsRealmResults;
        }
    }

//    public RealmResults<ROFeeds> getUpdatedFeedLikeStatus() {
//        realm.beginTransaction();
//        RealmResults<ROFeeds> ROFeedsRealmResults = realm.where(ROFeeds.class).equalTo("isSync", 1).findAll();
//
//        Log.e(TAG, "managedFeedLikeStatus ROFeedsRealmResults.size: " + ROFeedsRealmResults.size());
//
//        realm.commitTransaction();
//        if (ROFeedsRealmResults.size() == 0) {
//            return null;
//        } else {
//            return ROFeedsRealmResults;
//        }
//    }


    /**
     * store feed related comments
     *
     * @param ROFeedComment
     * @param
     */
    public void saveComments(ROFeedComment ROFeedComment) {
        try {
            ROFeeds ROFeeds = ROFeedComment.getFeed();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROFeedComment);
            ROFeeds.getComments().add(ROFeedComment);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveComments Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     * store feed images
     *
     * @param ROFeedImage
     */
    public void saveFeedImages(ROFeedImage ROFeedImage) {
        try {
            ROFeeds ROFeeds = ROFeedImage.getFeed();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROFeedImage);
            ROFeeds.getROFeedImages().add(ROFeedImage);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveFeedImages Exceptions : " + e.getLocalizedMessage());
        }
    }

    public void saveTutorialGroupDiscussion(ROTutorialGroupDiscussion ROTutorialGroupDiscussion) {
        try {
            Number discussionId = realm.where(ROTutorialGroupDiscussion.class).max("tutorialGroupDiscussionId");
            long newId = 1;
            if (discussionId != null) {
                newId = (long) discussionId + 1;
            }
            realm.beginTransaction();
            ROTutorialGroupDiscussion.setTutorialGroupDiscussionId((int) newId);
            realm.copyToRealmOrUpdate(ROTutorialGroupDiscussion);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveTutorialGroupDiscussion Exception : " + e.toString());
        }
    }

    public void saveTutorialGroupTopic(ROTutorialTopic ROTutorialTopic) {
        try {
            Number topicId = realm.where(ROTutorialTopic.class).max("tutorialTopicId");
            long newId = 1;
            if (topicId != null) {
                newId = (long) topicId + 1;
            }
            realm.beginTransaction();
            ROTutorialTopic.setTutorialTopicId((int) newId);
            realm.copyToRealmOrUpdate(ROTutorialTopic);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveTutorialGroupTopic Exception : " + e.toString());
        }
    }

    public void saveSubjects(ROSubjects ROSubjects) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROSubjects);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveSubjects Exception : " + e.toString());
        }
    }

    /**
     * used for the save notes or jottings
     *
     * @param RONotes
     */
    public void saveNote(RONotes RONotes) {
        try {
            if (RONotes.getLocalNoteId() == 0) {
                Number id = realm.where(RONotes.class).max("localNoteId");
                long newId = 1;
                if (id != null) {
                    newId = (long) id + 1;
                }
                RONotes.setLocalNoteId((int) newId);
            }
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(RONotes);
            realm.commitTransaction();

        } catch (Exception e) {
            Log.e(TAG, "saveNote Exception : " + e.toString());
        }
    }

    /**
     * get all notes or jotting for user
     *
     * @param userId
     * @return
     */
    public RealmResults<RONotes> getNotes(final int userId) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RONotesRealmResults = realm.where(RONotes.class).equalTo("user.userId", userId).findAll();
                }
            });
           // Log.e(TAG, "getNotes notesRealmResults.size: " + RONotesRealmResults.size());
          //  Log.e(TAG, "getNotes notesRealmResults : " + RONotesRealmResults);
        } catch (Exception e) {
            Log.e(TAG, "getNotes Exception : " + e.getLocalizedMessage());
        }
        return RONotesRealmResults;
    }

    /**
     * save author profile information
     *
     * @param ROAuthorProfile
     */
    public void saveAuthorProfile(ROAuthorProfile ROAuthorProfile) {
        try {
//            Number id = realm.where(ROAuthorProfile.class).max("localAuthorId");
//            long newId = 1;
//            if (id != null) {
//                newId = (long) id + 1;
//            }
//            ROAuthorProfile.setLocalAuthorId((int) newId);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROAuthorProfile);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in ROAuthorProfile table :" + realm.where(ROAuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveAuthorProfile Exceptions : " + e.getLocalizedMessage());
        }
    }

    public ROAuthorProfile getAuthorprofile(int userId) {
        try {
            return realm.where(ROAuthorProfile.class).equalTo("serverAuthorId", userId).findFirst();
        } catch (Exception e) {
            Log.e(TAG, "getAuthorprofile Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * save student profile information
     *
     * @param ROStudentProfile
     */
    public void saveStudentProfile(ROStudentProfile ROStudentProfile) {
        try {
            Number id = realm.where(ROStudentProfile.class).max("localStudentId");
            long newId = 1;
            if (id != null) {
                newId = (long) id + 1;
            }
            ROStudentProfile.setLocalStudentId((int) newId);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROStudentProfile);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(ROAuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveStudentProfile Exceptions : " + e.getLocalizedMessage());
        }
    }

    public ROStudentProfile getStudentProfile(int id) {
        try {
            return realm.where(ROStudentProfile.class).equalTo("serverStudentId", id).findFirst();
        } catch (Exception e) {
            Log.e(TAG, "getStudentProfile Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * save school
     * @param ROSchool
     */
    public void saveSchool(ROSchool ROSchool) {
        try {

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROSchool);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(ROAuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveSchool Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     * save classroom
     *
     * @param classroom
     */
    public void saveClassRoom(ROClassrooms classroom) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(classroom);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(ROAuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveClassRoom Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     * @param ROCourses
     */
    public void saveCourse(ROCourses ROCourses) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROCourses);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(ROAuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveCourse Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     * @param ROTrendingQuestion
     */
    public void saveTrendingQuestion(ROTrendingQuestion ROTrendingQuestion) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ROTrendingQuestion);
        realm.commitTransaction();
    }

    /**
     * @param questionFollower
     */
    public void saveTrendingQuestionFolloer(ROTrendingQuestionFollower questionFollower) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(questionFollower);
        realm.commitTransaction();
    }

    public ROTrendingQuestion getTrendingQuestion(final String trendingQuestionId) {

        try {
            realm.beginTransaction();
            ROTrendingQuestion = realm.where(ROTrendingQuestion.class).equalTo("trendingId", Integer.parseInt(trendingQuestionId)).findFirst();
            realm.commitTransaction();
            return ROTrendingQuestion;
        } catch (Exception e) {
            Log.e(TAG, "getROTrendingQuestion Exception : " + e.getLocalizedMessage());
        }
        return null;
    }

    public RealmResults<ROTrendingQuestion> getTrendingQuestionResult(final String authorId, boolean isForWeek) {

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ROTrendingQuestionRealmResults
                            = realm.where(ROTrendingQuestion.class).equalTo("questionFor.userId", Integer.parseInt(authorId)).findAll();
                }
            });
            return ROTrendingQuestionRealmResults;
        } catch (Exception e) {
            Log.e(TAG, "getTrendingQuestionResult Exception : " + e.getLocalizedMessage());
        }
        return null;
    }

    public ROTrendingQuestionFollower getTrendingQuestionFollower(final String trendingId, final String userId) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ROTrendingQuestionFollowerResults = realm.where(ROTrendingQuestionFollower.class).equalTo("ROTrendingQuestion.trendingId", Integer.parseInt(trendingId)).equalTo("followerBy.userId", Integer.parseInt(userId)).findFirst();
                }
            });
            return ROTrendingQuestionFollowerResults;
        } catch (Exception e) {
            Log.e(TAG, "getTrendingQuestionFollower Exception : " + e.getLocalizedMessage());
        }
        return null;
    }

    public void removeTrendingQuestionFollower(final int questionFollowerId) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.where(ROTrendingQuestionFollower.class).equalTo("questionFollowerId", questionFollowerId).findFirst().removeFromRealm();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getTrendingQuestionFollower Exception : " + e.getLocalizedMessage());
        }

    }

    public void saveTrendingQuestionComment(ROTrendingQuestionComment questionComment) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(questionComment);
        realm.commitTransaction();
    }
}
