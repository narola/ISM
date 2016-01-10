package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmResults;
import model.AdminConfig;
import model.AuthorProfile;
import model.Classrooms;
import model.Courses;
import model.FeedComment;
import model.FeedImage;
import model.Feeds;
import model.Notes;
import model.School;
import model.StudentProfile;
import model.Subjects;
import model.TutorialGroupDiscussion;
import model.TutorialTopic;
import model.User;

/**
 * Created by c161 on 08/12/15.
 */
public class StudentHelper {

    private static final String TAG = StudentHelper.class.getSimpleName();

    public Realm realm;
    private RealmResults<Feeds> feedsRealmResults;
    private RealmResults<Notes> notesRealmResults;

    public StudentHelper(Context context) {
        realm = RealmAdaptor.getInstance(context);
    }

    /**
     * use to save user data in ISM database.
     */
    public void saveUser(User user) {
        //Log.e(TAG,"saveUser : "+user.getUserId());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    /**
     * Save admin config data
     *
     * @param adminConfig
     */
    public void saveAdminConfig(AdminConfig adminConfig) {
        try {
            Number configId = realm.where(AdminConfig.class).max("configId");
            long newId = 1;
            if (configId != null) {
                newId = (long) configId + 1;
            }
            realm.beginTransaction();
            adminConfig.setConfigId((int) newId);
            realm.copyToRealmOrUpdate(adminConfig);
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
        RealmResults<AdminConfig> adminConfigs = realm.where(AdminConfig.class)
                .equalTo("configKey", "globalPassword")
                .findAll();
        if (adminConfigs != null && adminConfigs.size() > 0) {
            return adminConfigs.get(0).getConfigValue();
        } else {
            return null;
        }
    }

    public int getFridayExamAnswerScore() {
        RealmResults<AdminConfig> adminConfigs = realm.where(AdminConfig.class)
                .equalTo("configKey", "fridayInternalExamScore")
                .findAll();
        if (adminConfigs != null && adminConfigs.size() > 0) {
            return Integer.parseInt(adminConfigs.get(0).getConfigValue());
        } else {
            return 0;
        }
    }

    public AdminConfig getActiveHoursStartTime() {
        RealmResults<AdminConfig> adminConfigs = realm.where(AdminConfig.class)
                .equalTo("configKey", "activeHoursStartTime")
                .findAll();
        if (adminConfigs != null && adminConfigs.size() > 0) {
            return adminConfigs.get(0);
        } else {
            return null;
        }
    }

    public AdminConfig getActiveHoursEndTime() {
        RealmResults<AdminConfig> adminConfigs = realm.where(AdminConfig.class)
                .equalTo("configKey", "activeHoursEndTime")
                .findAll();
        if (adminConfigs != null && adminConfigs.size() > 0) {
            return adminConfigs.get(0);
        } else {
            return null;
        }
    }

    public void saveFeeds(Feeds feeds) {

        // remaining : to update feed with local id;
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(feeds);
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
    public RealmResults<Feeds> getFeeds(final int feedId, final int userId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (feedId != -1) {
                    feedsRealmResults = realm.where(Feeds.class).equalTo("feedId", feedId).equalTo("user.userId", userId).findAll();
                } else {
                    feedsRealmResults = realm.where(Feeds.class).equalTo("user.userId", userId).findAll();
                }
            }
        });
        Log.e(TAG, "getFeeds feedsRealmResults.size: " + feedsRealmResults.size());
        return feedsRealmResults;
    }


    /**
     * get the User
     *
     * @param user_id
     * @return
     */
    public User getUser(int user_id) {
        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("userId", user_id).findFirst();
        //Log.e(TAG, "getUser : " + user);
        realm.commitTransaction();
        return user;
    }


    /**
     * return the all the comments of @param feedId
     *
     * @param feedId
     * @return
     */
    public RealmResults<FeedComment> getFeedComments(int feedId) {
        realm.beginTransaction();
        RealmResults<FeedComment> feedsRealmResults = realm.where(FeedComment.class).equalTo("feed.feedId", feedId).findAll();
        Log.e(TAG, "all comments : " + feedsRealmResults);
        Log.e(TAG, "getFeedComments feedsRealmResults.size: " + feedsRealmResults.size());

        realm.commitTransaction();
        return feedsRealmResults;
    }

//    /**
//     * store feedlike records
//     *
//     * @param feedLike
//     */
//    public void saveFeedLikes(FeedLike feedLike) {
//        realm.beginTransaction();
//        Number feedLikeId = realm.where(FeedLike.class).max("feedLikeId");
//        long newId = 1;
//        if (feedLikeId != null) {
//            newId = (long) feedLikeId + 1;
//        }
//        feedLike.setFeedLikeId((int) newId);
//        realm.copyToRealmOrUpdate(feedLike);
//        realm.commitTransaction();
//    }

    public void updateFeedLikes(Feeds feeds) {
        Feeds toUpdateFeeds = realm.where(Feeds.class).equalTo("feedId", feeds.getFeedId()).findFirst();
        realm.beginTransaction();
        toUpdateFeeds.setSelfLike(feeds.getSelfLike().equals("0") ? "1" : "0");
        toUpdateFeeds.setTotalLike(feeds.getSelfLike().equals("0") ? feeds.getTotalLike() - 1 : feeds.getTotalLike() + 1);
        toUpdateFeeds.setIsSync(feeds.isSync() == 0 ? 1 : 0);
        Log.e(TAG, "updateFeedLikes : " + toUpdateFeeds.getSelfLike() + "--" + toUpdateFeeds.getFeedId() + "--" + toUpdateFeeds.isSync());
        realm.commitTransaction();
    }

    public void updateTotalComments(Feeds feeds) {
        Feeds toUpdateFeeds = realm.where(Feeds.class).equalTo("feedId", feeds.getFeedId()).findFirst();
        realm.beginTransaction();
        toUpdateFeeds.setTotalComment(feeds.getTotalComment() + 1);
        //toUpdateFeeds.getComments().add(feeds.getComments().get(0));
        realm.commitTransaction();
    }

    /**
     *
     * @param statusUpdation
     * @return
     */
    public RealmResults<Feeds> managedFeedLikeStatus(boolean statusUpdation) {
//    public RealmResults<Feeds> managedFeedLikeStatus(Date lastSynch, Date modified) {
        realm.beginTransaction();
        RealmResults<Feeds> feedsRealmResults = realm.where(Feeds.class).equalTo("isSync", 1).findAll();
        Log.e(TAG, "managedFeedLikeStatus feedsRealmResults.size: " + feedsRealmResults.size());
        if (statusUpdation) {
            //after sync
            for (int i = 0; i < feedsRealmResults.size(); i++)
                feedsRealmResults.get(i).setIsSync(0);
        }
        realm.commitTransaction();
        if (feedsRealmResults.size() == 0) {
            return null;
        } else {
            return feedsRealmResults;
        }
    }

//    public RealmResults<Feeds> getUpdatedFeedLikeStatus() {
//        realm.beginTransaction();
//        RealmResults<Feeds> feedsRealmResults = realm.where(Feeds.class).equalTo("isSync", 1).findAll();
//
//        Log.e(TAG, "managedFeedLikeStatus feedsRealmResults.size: " + feedsRealmResults.size());
//
//        realm.commitTransaction();
//        if (feedsRealmResults.size() == 0) {
//            return null;
//        } else {
//            return feedsRealmResults;
//        }
//    }


    /**
     * store feed related comments
     *
     * @param feedComment
     * @param
     */
    public void saveComments(FeedComment feedComment) {
        try {
            Feeds feeds = feedComment.getFeed();
            realm.beginTransaction();
           realm.copyToRealmOrUpdate(feedComment);
            feeds.getComments().add(feedComment);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveComments Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     * store feed images
     *
     * @param feedImage
     */
    public void saveFeedImages(FeedImage feedImage) {
        try {
            Feeds feeds = feedImage.getFeed();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(feedImage);
            feeds.getFeedImages().add(feedImage);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveFeedImages Exceptions : " + e.getLocalizedMessage());
        }
    }

    public void saveTutorialGroupDiscussion(TutorialGroupDiscussion tutorialGroupDiscussion) {
        try {
            Number discussionId = realm.where(TutorialGroupDiscussion.class).max("tutorialGroupDiscussionId");
            long newId = 1;
            if (discussionId != null) {
                newId = (long) discussionId + 1;
            }
            realm.beginTransaction();
            tutorialGroupDiscussion.setTutorialGroupDiscussionId((int) newId);
            realm.copyToRealmOrUpdate(tutorialGroupDiscussion);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveTutorialGroupDiscussion Exception : " + e.toString());
        }
    }

    public void saveTutorialGroupTopic(TutorialTopic tutorialTopic) {
        try {
            Number topicId = realm.where(TutorialTopic.class).max("tutorialTopicId");
            long newId = 1;
            if (topicId != null) {
                newId = (long) topicId + 1;
            }
            realm.beginTransaction();
            tutorialTopic.setTutorialTopicId((int) newId);
            realm.copyToRealmOrUpdate(tutorialTopic);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveTutorialGroupTopic Exception : " + e.toString());
        }
    }

    public void saveSubjects(Subjects subjects) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(subjects);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveSubjects Exception : " + e.toString());
        }
    }

    /**
     * used for the save notes or jottings
     *
     * @param notes
     */
    public void saveNote(Notes notes) {
        try {
            if (notes.getLocalNoteId() == 0) {
                Number id = realm.where(Notes.class).max("localNoteId");
                long newId = 1;
                if (id != null) {
                    newId = (long) id + 1;
                }
                notes.setLocalNoteId((int) newId);
            }
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(notes);
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
    public RealmResults<Notes> getNotes(final int userId) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    notesRealmResults = realm.where(Notes.class).equalTo("user.userId", userId).findAll();
                }
            });
            Log.e(TAG, "getNotes notesRealmResults.size: " + notesRealmResults.size());
            Log.e(TAG, "getNotes notesRealmResults : " + notesRealmResults);
        } catch (Exception e) {
            Log.e(TAG, "getNotes Exception : " + e.getLocalizedMessage());
        }
        return notesRealmResults;
    }

    /**
     * save author profile information
     *
     * @param authorProfile
     */
    public void saveAuthorProfile(AuthorProfile authorProfile) {
        try {
//            Number id = realm.where(AuthorProfile.class).max("localAuthorId");
//            long newId = 1;
//            if (id != null) {
//                newId = (long) id + 1;
//            }
//            authorProfile.setLocalAuthorId((int) newId);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(authorProfile);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(AuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveAuthorProfile Exceptions : " + e.getLocalizedMessage());
        }
    }

    public AuthorProfile getAuthorprofile(int userId) {
        try {
            return realm.where(AuthorProfile.class).equalTo("serverAuthorId", userId).findFirst();
        } catch (Exception e) {
            Log.e(TAG, "getAuthorprofile Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * save student profile information
     *
     * @param  studentProfile
     */
    public void saveStudentProfile(StudentProfile studentProfile) {
        try {
            Number id = realm.where(StudentProfile.class).max("localStudentId");
            long newId = 1;
            if (id != null) {
                newId = (long) id + 1;
            }
            studentProfile.setLocalStudentId((int) newId);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(studentProfile);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(AuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveStudentProfile Exceptions : " + e.getLocalizedMessage());
        }
    }

    public StudentProfile getStudentProfile(int id) {
        try {
            return realm.where(StudentProfile.class).equalTo("serverStudentId", id).findFirst();
        } catch (Exception e) {
            Log.e(TAG, "getStudentProfile Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     *  save school
     * @param school
     */
    public void saveSchool(School school) {
        try {

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(school);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(AuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveSchool Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     * save classroom
     * @param classroom
     */
    public void saveClassRoom(Classrooms classroom) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(classroom);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(AuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveClassRoom Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     *
     * @param courses
     */
    public void saveCourse(Courses courses) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(courses);
            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in authorProfile table :" + realm.where(AuthorProfile.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, " saveCourse Exceptions : " + e.getLocalizedMessage());
        }
    }
}
