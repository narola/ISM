package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

//import model.AdminConfig;
//import model.AuthorProfile;
//import model.Classrooms;
//import model.Courses;
//import model.FeedComment;
//import model.FeedImage;
//import model.Feeds;
//
//import model.Subjects;
//import model.TutorialGroup;
//import model.TutorialGroupDiscussion;
//import model.TutorialGroupTopicAllocation;
//
//import model.Notes;
//import model.School;
//import model.StudentProfile;
//
//
//import model.TutorialTopic;
//import model.User;

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
import model.ROTutorialGroupTopicAllocation;

/**
 * Created by c161 on 08/12/15.
 */
public class StudentHelper {

    private static final String TAG = StudentHelper.class.getSimpleName();

    public Realm realm;
    private RealmResults<ROFeeds> roFeedsRealmResults;
    private RealmResults<RONotes> RONotesRealmResults;
    private RealmResults<ROTrendingQuestion> ROTrendingQuestionRealmResults;
    private ROTrendingQuestionFollower ROTrendingQuestionFollowerResults;
    private ROTrendingQuestion ROTrendingQuestion;
    private ROFeeds roFeeds;

    public StudentHelper(Context context) {
        if(realm == null)
        realm = RealmAdaptor.getInstance(context);
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
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
     * use to save tutorialGroupTopicAllocation data in ISM database.
     */
    public void saveTutorialGroupTopicAllocation(ROTutorialGroupTopicAllocation tutorialGroupTopicAllocation) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(tutorialGroupTopicAllocation);
        realm.commitTransaction();

    }

    /**
     * use to save tutorialTopic data in ISM database.
     */
    public void saveTutorialTopic(ROTutorialTopic tutorialTopic ) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(tutorialTopic);
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
     * @return
     */
    public RealmResults<ROFeeds> getFeeds() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                if (feedId != -1) {
//                    roFeedsRealmResults = realm.where(ROFeeds.class).equalTo("feedId", feedId).findAll();
//                } else {
                roFeedsRealmResults = realm.where(ROFeeds.class).findAll();
            }
        });
        Log.e(TAG, "getFeeds roFeedsRealmResults.size: " + roFeedsRealmResults.size());
        return roFeedsRealmResults;
    }

    public ROFeeds getFeeds(final int feedId) {
        roFeeds = realm.where(ROFeeds.class).equalTo("feedId", feedId).findFirst();
        Log.e(TAG, "getFeeds :  " + roFeeds);
        return roFeeds;
    }

    /**
     * get the User
     *
     * @param user_id
     * @return
     */

//    public User getUser(int user_id) {
//        realm.beginTransaction();
//        User user = realm.where(User.class).equalTo("userId", user_id).findFirst();
//        Log.i(TAG, "getUser : " + user);
//        realm.commitTransaction();
////<<<<<<< HEAD
////if(feedsRealmResults.size() == 0){
////    return  null;
////}
////        else{
////   return feedsRealmResults.get(0);
////        }
////
////=======
//        return user;
//>>>>>>> adddedd0f52cebbf2708517d26e06ea1e3c5b94b

    public ROUser getUser(int user_id) {
        return realm.where(ROUser.class).equalTo("userId", user_id).findFirst();

    }


    /**
     * return the all the comments of @param feedId
     *
     * @param feedId
     * @return
     */
    public RealmResults<ROFeedComment> getFeedComments(int feedId) {
        RealmResults<ROFeedComment> feedsRealmResults = realm.where(ROFeedComment.class).equalTo("roFeed.feedId", feedId).findAll();
//        Log.e(TAG, "all comments : " + feedsRealmResults);
        Log.e(TAG, "getFeedComments roFeedsRealmResults.size: " + feedsRealmResults.size());
        if (feedsRealmResults != null)
            return feedsRealmResults;
        else
            return null;
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
        Log.i(TAG, "managedFeedLikeStatus roFeedsRealmResults.size: " + ROFeedsRealmResults.size());
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
//        RealmResults<ROFeeds> roFeedsRealmResults = realm.where(ROFeeds.class).equalTo("isSync", 1).findAll();
//
//        Log.e(TAG, "managedFeedLikeStatus roFeedsRealmResults.size: " + roFeedsRealmResults.size());
//
//        realm.commitTransaction();
//        if (roFeedsRealmResults.size() == 0) {
//            return null;
//        } else {
//            return roFeedsRealmResults;
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
            ROFeeds ROFeeds = ROFeedComment.getRoFeed();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROFeedComment);
            ROFeeds.getRoFeedComment().add(ROFeedComment);
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
            ROFeeds.getRoFeedImages().add(ROFeedImage);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveFeedImages Exceptions : " + e.getLocalizedMessage());
        }
    }



    /**
     * get tutorial topic
     * @param tutorialTopicId - id for tutorial topic.
     * @return return {@link - TutorialTopic}
     */
    public ROTutorialTopic getTutorialTopic(int tutorialTopicId){
        realm.beginTransaction();
        RealmResults<ROTutorialTopic> tutorialTopics = realm.where(ROTutorialTopic.class).equalTo("tutorialTopicId", tutorialTopicId).findAll();
        realm.commitTransaction();
        if(tutorialTopics.size() == 0){
            return  null;
        }
        else{
            return tutorialTopics.get(0);
        }
    }

    /**
     * get subject
     * @param tutorialGroupId - id for group.
     * @return return {@link - TutorialGroupDiscussion}
     */
    public RealmResults<ROTutorialGroupDiscussion> getTutorialGroupDiscussionByGroup(int tutorialGroupId){
        realm.beginTransaction();
        RealmResults<ROTutorialGroupDiscussion> tutorialGroupDiscussions = realm.where(ROTutorialGroupDiscussion.class).findAll();
        tutorialGroupDiscussions.sort("localId", RealmResults.SORT_ORDER_DESCENDING);
        realm.commitTransaction();
        if(tutorialGroupDiscussions.size() == 0){
            return  null;
        }
        else{
            return tutorialGroupDiscussions;
        }
    }





    /**
     * get subject
     * @param weekDay - day of week.
     * @return return {@link - TutorialGroupTopicAllocation}
     */
    public ROTutorialGroupTopicAllocation getTutorialTopicAllocationByDayOfWeek(String weekDay){
        realm.beginTransaction();
        ROTutorialGroupTopicAllocation tutorialGroupTopicAllocation = realm.where(ROTutorialGroupTopicAllocation.class).equalTo("dateDay", weekDay).equalTo("weekNumber", 1).findFirst();
        realm.commitTransaction();
        return tutorialGroupTopicAllocation;
    }


    /**
     * get subject
     * @param tutorialTopicAllocation - id for subject.
     * @return return {@link - TutorialGroupDiscussion}
     */
    public ROTutorialGroupTopicAllocation getTutorialTopicAllocation(int tutorialTopicAllocation){
        realm.beginTransaction();
        RealmResults<ROTutorialGroupTopicAllocation> tutorialGroupTopicAllocations = realm.where(ROTutorialGroupTopicAllocation.class).equalTo("tutorialGroupTopicId", tutorialTopicAllocation).findAll();
        realm.commitTransaction();
        if(tutorialGroupTopicAllocations.size() == 0){
            return  null;
        }
        else{
            return tutorialGroupTopicAllocations.get(0);
        }
    }



    /**
     * get subject
     * @param groupDiscussionId - id for subject.
     * @return return {@link - TutorialGroupDiscussion}
     */
    public ROTutorialGroupDiscussion getTutorialGroupDiscussion(int groupDiscussionId){
        realm.beginTransaction();
        RealmResults<ROTutorialGroupDiscussion> tutorialGroupDiscussions = realm.where(ROTutorialGroupDiscussion.class).equalTo("tutorialGroupDiscussionId", groupDiscussionId).findAll();
        realm.commitTransaction();
        if(tutorialGroupDiscussions.size() == 0){
            return  null;
        }
        else{
            return tutorialGroupDiscussions.get(0);
        }
    }

    /**
     * get subject
     * @param subjectId - id for subject.
     * @return return {@link - Subjects}
     */
    public ROSubjects getSubject(int subjectId){
        realm.beginTransaction();
        RealmResults<ROSubjects> subjects = realm.where(ROSubjects.class).equalTo("subjectId", subjectId).findAll();
        realm.commitTransaction();
        if(subjects.size() == 0){
            return  null;
        }
        else{
            return subjects.get(0);
        }
    }



    /**
     * save subject in local
     * @param subject -  {@link - Subjects}
     */
    public void saveSubject(ROSubjects subject) {

        // remaining : to update subject with local id;
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(subject);
        realm.commitTransaction();
    }

    /**
     * save suect in local
     * @param rotutorialGroupDiscussion -  {@link - TutorialGroupDiscussion}
     */
//    public void saveTutorialGroupDiscussion(ROTutorialGroupDiscussion tutorialGroupDiscussion) {
//
//        // remaining : to update subject with local id;
//        Number tutorialGroupDiscussionId = realm.where(ROTutorialGroupDiscussion.class).max("localId");
//        long newId = 1;
//        if (tutorialGroupDiscussionId != null) {
//            newId = (long) tutorialGroupDiscussionId + 1;
//        }
//        realm.beginTransaction();
//        tutorialGroupDiscussion.setLocalId((int) newId);
//        realm.copyToRealmOrUpdate(tutorialGroupDiscussion);
//        realm.commitTransaction();
//    }

//    public void saveAllComments(ROFeedComment feedComment, int feedId) {
//        try {
//            ROFeeds toUpdateFeeds = realm.where(ROFeeds.class).equalTo("feedId", feedId).findFirst();
//            realm.beginTransaction();
//            realm.copyToRealmOrUpdate(feedComment);
//            toUpdateFeeds.getComments().add(feedComment);
//        }

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
            Log.i(TAG, "saveAllComments Exceptions : " + e.getLocalizedMessage());
        }
    }




//    public void saveTutorialGroupDiscussion(TutorialGroupDiscussion tutorialGroupDiscussion) {
//        try {
//            Number discussionId = realm.where(TutorialGroupDiscussion.class).max("tutorialGroupDiscussionId");
//            long newId = 1;
//            if (discussionId != null) {
//                newId = (long) discussionId + 1;
//            }
//            realm.beginTransaction();
//            tutorialGroupDiscussion.setTutorialGroupDiscussionId((int) newId);
//            realm.copyToRealmOrUpdate(tutorialGroupDiscussion);
//            realm.commitTransaction();
//        } catch (Exception e) {
//            Log.e(TAG, "saveTutorialGroupDiscussion Exception : " + e.toString());
//        }
//    }

//    public void saveTutorialGroupTopic(TutorialTopic tutorialTopic) {
//=======
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

//
//    /**
//     * used for the save notes or jottings
//     *
//     * @param notes
//     */
//    public void saveNote(Notes notes) {
//=======

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
//
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
//
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
//<<<<<<< HEAD
////
//    public AuthorProfile getAuthorprofile(int userId) {
//=======

    public ROAuthorProfile getAuthorprofile(int userId) {

        try {
            return realm.where(ROAuthorProfile.class).equalTo("serverAuthorId", userId).findFirst();
        } catch (Exception e) {
            Log.e(TAG, "getAuthorprofile Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }
//
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
//
    /**
     * save school
     *
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
//<<<<<<< HEAD
////>>>>>>> adddedd0f52cebbf2708517d26e06ea1e3c5b94b
//=======

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
