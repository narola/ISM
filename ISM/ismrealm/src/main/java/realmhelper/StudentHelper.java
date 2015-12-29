package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmResults;
import model.AdminConfig;
import model.FeedComment;
import model.FeedImage;
import model.Feeds;
import model.Notes;
import model.Subjects;
import model.TutorialGroupDiscussion;
import model.TutorialTopic;
import model.User;

/**
 * Created by c161 on 08/12/15.
 */
public class StudentHelper {

    private static final String TAG = StudentHelper.class.getSimpleName();

    Realm realm;
    private RealmResults<Feeds> feedsRealmResults;
    private RealmResults<Notes> notesRealmResults;

    public StudentHelper(Context context) {
        realm = RealmAdaptor.getInstance(context);
    }

    /**
     * use to save user data in ISM database.
     */
    public void saveUser(User user) {
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
     * @return
     */
    public RealmResults<Feeds> getFeeds(final int feedId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (feedId != -1) {
                    feedsRealmResults = realm.where(Feeds.class).equalTo("feedId", feedId).findAll();
                } else {
                    feedsRealmResults = realm.where(Feeds.class).findAll();
                }
            }
        });
        Log.i(TAG, "getFeeds feedsRealmResults.size: " + feedsRealmResults.size());
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
        RealmResults<User> feedsRealmResults = realm.where(User.class).equalTo("userId", user_id).findAll();
        realm.commitTransaction();
        return feedsRealmResults.get(0);
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
        Log.i(TAG, "all comments : " + feedsRealmResults);
        Log.i(TAG, "getFeedComments feedsRealmResults.size: " + feedsRealmResults.size());

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
        toUpdateFeeds.setLike(feeds.getLike().equals("0") ? "1" : "0");
        toUpdateFeeds.setTotalLike(feeds.getLike().equals("0") ? feeds.getTotalLike() - 1 : feeds.getTotalLike() + 1);
        toUpdateFeeds.setIsSync(feeds.isSync() == 0 ? 1 : 0);
        Log.i(TAG, "updateFeedLikes : " + toUpdateFeeds.getLike() + "--" + toUpdateFeeds.getFeedId() + "--" + toUpdateFeeds.isSync());
        realm.commitTransaction();
    }

    public void updateTotalComments(Feeds feeds) {
        Feeds toUpdateFeeds = realm.where(Feeds.class).equalTo("feedId", feeds.getFeedId()).findFirst();
	    realm.beginTransaction();
        toUpdateFeeds.setTotalComment(feeds.getTotalComment() + 1);
        //toUpdateFeeds.getComments().add(feeds.getComments().get(0));
	    realm.commitTransaction();
    }

    public RealmResults<Feeds> getFeedLikes(boolean statusUpdation) {
//    public RealmResults<Feeds> getFeedLikes(Date lastSynch, Date modified) {
        realm.beginTransaction();
        RealmResults<Feeds> feedsRealmResults = realm.where(Feeds.class).equalTo("isSync", 1).findAll();
	    Log.i(TAG, "getFeedLikes feedsRealmResults.size: " + feedsRealmResults.size());
        if (statusUpdation) {
            for (int i = 0; i < feedsRealmResults.size(); i++)
                feedsRealmResults.get(i).setIsSync(0);
        }
	    realm.commitTransaction();
        return feedsRealmResults;
    }

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
            Log.i(TAG, "saveComments Exceptions : " + e.getLocalizedMessage());
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
            Log.i(TAG, "saveFeedImages Exceptions : " + e.getLocalizedMessage());
        }
    }

//    public void saveAllComments(FeedComment feedComment, int feedId) {
//        try {
//            Feeds toUpdateFeeds = realm.where(Feeds.class).equalTo("feedId", feedId).findFirst();
//            realm.beginTransaction();
//            realm.copyToRealmOrUpdate(feedComment);
//            toUpdateFeeds.getComments().add(feedComment);
//            realm.commitTransaction();
//        } catch (Exception e) {
//            Log.i(TAG, "saveAllComments Exceptions : " + e.getLocalizedMessage());
//        }
//    }

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
            Number subjectId = realm.where(Subjects.class).max("subjectId");
            long newId = 1;
            if (subjectId != null) {
                newId = (long) subjectId + 1;
            }
            realm.beginTransaction();
	        subjects.setSubjectId((int) newId);
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
                Number id = realm.where(Notes.class).max("noteId");
                long newId = 1;
                if (id != null) {
                    newId = (long) id + 1;
                }
                realm.beginTransaction();
                notes.setNoteId((int) newId);
                realm.copyToRealmOrUpdate(notes);
                realm.commitTransaction();

        } catch (Exception e) {
            Log.e(TAG, "saveNote Exception : " + e.toString());
        }
    }

    /**
     * get all notes or jotting for user
     *
     * @param user
     * @return
     */
    public RealmResults<Notes> getNotes(final User user) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                        notesRealmResults = realm.where(Notes.class).equalTo("user.userId", user.getUserId()).findAll();

                }
            });
            Log.i(TAG, "getNotes notesRealmResults.size: " + notesRealmResults.size());
        } catch (Exception e) {
            Log.i(TAG, "getNotes Exception : " + e.getLocalizedMessage());
        }
        return notesRealmResults;
    }

    /**
     * update the note(only for subject and note text)
     * @param notes
     * @param userId
     */
    public void updateNotes(final Notes notes, final int userId) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    notesRealmResults = realm.where(Notes.class).equalTo("user.userId", userId).equalTo("noteId", notes.getNoteId()).findAll();
                    notesRealmResults.get(0).setModifiedDate(notes.getModifiedDate());
                    notesRealmResults.get(0).setNoteSubject(notes.getNoteSubject());
                    notesRealmResults.get(0).setNoteText(notes.getNoteText());

                }
            });
            Log.i(TAG, "updateNotes : done  ");
        } catch (Exception e) {
            Log.i(TAG, "updateNotes Exception : " + e.getLocalizedMessage());
        }
    }
}
