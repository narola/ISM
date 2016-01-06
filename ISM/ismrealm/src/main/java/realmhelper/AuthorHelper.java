package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.AdminConfig;
import model.AuthorBook;
import model.AuthorProfile;
import model.Classrooms;
import model.Exam;
import model.FeedComment;
import model.FeedLike;
import model.Feeds;
import model.Preferences;
import model.User;

/**
 * Created by c166 on 16/12/15.
 */
public class AuthorHelper {

    private static final String TAG = AuthorHelper.class.getSimpleName();

    public Realm realm;

    public AuthorHelper(Context context) {
        realm = RealmAdaptor.getInstance(context);
    }


    /**
     * Method to get the config data
     *
     * @return config data
     */

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

    /**
     * Save admin config data
     *
     * @param adminConfig
     */
    public void saveAdminConfig(AdminConfig adminConfig) {
        try {
            Number configId = realm.where(AdminConfig.class).max("configId");
            long newId = 0;
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


    /**
     * Method to clear table data
     *
     * @param className
     */
    public void clearTableData(Class className) {
        try {
            realm.beginTransaction();
            realm.clear(className);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "clearTableData Exception : " + e.toString());
        }
    }

    /**
     * Method to get the total records of table.
     *
     * @param className
     */
    public int getTotalRecordsInTable(Class className) {

        RealmQuery<AdminConfig> query = null;
        try {
            query = realm.where(className);
            Log.e(TAG, "The Total No Of records in " + className.getSimpleName() + " table are::::" + query.findAll().size());

        } catch (Exception e) {
            Log.e(TAG, "getTotalRecordsInTable Exception : " + e.toString());
        }
        return query.findAll().size();
    }

    /**
     * @return all the postfeeds.
     */
    public RealmResults<Feeds> getAllPostFeeds() {
        RealmQuery<Feeds> query = realm.where(Feeds.class);
        return query.findAll();
    }


    /**
     * Add feeds into table.
     *
     * @param feeds
     */
    public void addFeeds(Feeds feeds) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(feeds);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addFeedsData Exception : " + e.toString());
        }
    }

    /**
     * Add Comment added by user if only one comment is there.
     *
     * @param feedId
     * @param comment
     */
    public void addComment(int feedId, FeedComment comment) {

        try {

            RealmQuery<Feeds> query = realm.where(Feeds.class).equalTo("feedId", feedId);
            Feeds feed = query.findFirst();

            if (feed.getTotalComment() < 2) {
                realm.beginTransaction();
                feed.getComments().add(comment);
                feed.setTotalComment(feed.getTotalComment() + 1);
                realm.copyToRealmOrUpdate(feed);
                realm.commitTransaction();
            }

        } catch (Exception e) {
            Log.e(TAG, "addCommentData Exception : " + e.toString());
        }

    }


    /**
     * Method to update {@param selfLike} in FeedLike.
     *
     * @param feedId
     * @param selfLike
     */
    public void updateFeedSelfLikeStatus(int feedId, String selfLike) {

        try {
            RealmQuery<Feeds> query = realm.where(Feeds.class).equalTo("feedId", feedId);
            Feeds feed = query.findFirst();
            realm.beginTransaction();
            feed.setSelfLike(selfLike);
            feed.setTotalLike(selfLike.equals("1") ? feed.getTotalLike() + 1 : feed.getTotalLike() - 1);
            realm.copyToRealmOrUpdate(feed);
            realm.commitTransaction();

        } catch (Exception e) {
            Log.e(TAG, "updateFeedSelfLikeStatus Exception : " + e.toString());
        }

    }


    /**
     * Method to insert {@param feedLike} data and its sync value.
     *
     * @param feedLike
     */
    public void insertUpdateLikeFeedData(FeedLike feedLike) {
        try {
            FeedLike isRecordExist = realm.where(FeedLike.class)
                    .equalTo("feed.feedId", feedLike.getFeed().getFeedId()).equalTo("likeBy.userId", feedLike.getLikeBy().getUserId()).findFirst();

            if (isRecordExist == null) {
                Number feedLikeId = realm.where(FeedLike.class).max("feedLikeId");
                long newId = 0;
                if (feedLikeId != null) {
                    newId = (long) feedLikeId + 1;
                }
                feedLike.setFeedLikeId((int) newId);
            }

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(feedLike);
            realm.commitTransaction();


            getTotalRecordsInTable(FeedLike.class);
        } catch (Exception e) {
            Log.e(TAG, "insertUpdateLikeFeedData Exception : " + e.toString());
        }
    }

    /**
     * Method to update sync status after successfully update data at server side.
     *
     * @param arrListLikeFeedId
     * @param arrListUnlikeFeedId
     * @param strUserId
     */
    public void updateSyncStatusForFeeds(ArrayList<Integer> arrListLikeFeedId, ArrayList<Integer> arrListUnlikeFeedId, String strUserId) {
        try {
            if (arrListLikeFeedId.size() > 0) {
                for (int feedId : arrListLikeFeedId) {
                    FeedLike feedLike = realm.where(FeedLike.class)
                            .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                    realm.beginTransaction();
                    feedLike.setIsSync(1);
                    realm.copyToRealmOrUpdate(feedLike);
                    realm.commitTransaction();

                }
            }
            if (arrListUnlikeFeedId.size() > 0) {
                for (int feedId : arrListUnlikeFeedId) {
                    model.FeedLike feedLike = realm.where(FeedLike.class)
                            .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                    realm.beginTransaction();
                    feedLike.setIsSync(1);
                    realm.copyToRealmOrUpdate(feedLike);
                    realm.commitTransaction();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateSyncStatusForFeeds Exception : " + e.toString());
        }
    }

    /**
     * Method to update sync status asychrounsly after successfull data at server side.
     *
     * @param arrListLikeFeedId
     * @param arrListUnlikeFeedId
     * @param strUserId
     */
    public void updateSyncStatusForFeedsAsychrounsly(final ArrayList<Integer> arrListLikeFeedId, final ArrayList<Integer> arrListUnlikeFeedId,
                                                     final String strUserId) {

        try {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm bgRealm) {
                    if (arrListLikeFeedId.size() > 0) {
                        for (int feedId : arrListLikeFeedId) {
                            model.FeedLike feedLike = bgRealm.where(model.FeedLike.class)
                                    .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                            feedLike.setIsSync(1);
                            bgRealm.copyToRealmOrUpdate(feedLike);
                        }
                    }
                    if (arrListUnlikeFeedId.size() > 0) {
                        for (int feedId : arrListUnlikeFeedId) {
                            model.FeedLike feedLike = bgRealm.where(model.FeedLike.class)
                                    .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                            feedLike.setIsSync(1);
                            bgRealm.copyToRealmOrUpdate(feedLike);
                        }
                    }
                }
            }, new Realm.Transaction.Callback() {
                @Override
                public void onSuccess() {
                    Log.e(TAG, "Sync Successfully");
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "updateSyncStatusForFeedsAsychrounsly Exception : " + e.toString());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "updateSyncStatusForFeedsAsychrounsly Exception : " + e.toString());
        }
    }


    /**
     * Add authorBook.
     *
     * @param authorBook
     */
    public void addAuthorBooks(AuthorBook authorBook) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(authorBook);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addAuthorBooks Exception : " + e.toString());
        }
    }

    /**
     * @return all the authorbooks.
     */
    public RealmResults<AuthorBook> getAuthorBooks() {
        RealmQuery<AuthorBook> query = realm.where(AuthorBook.class);
        return query.findAll();
    }


    /**
     * Add classrooms
     *
     * @param classrooms
     */
    public void addClassrooms(Classrooms classrooms) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(classrooms);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addClassrooms Exception : " + e.toString());
        }
    }


    /**
     * @return all the classrooms.
     */
    public RealmResults<Classrooms> getClassrooms() {
        RealmQuery<Classrooms> query = realm.where(Classrooms.class);
        return query.findAll();
    }


    /**
     * Method to get the class data for created exam.
     *
     * @param classroomId
     * @return
     */
    public Classrooms getExamClassroom(int classroomId) {
        RealmQuery<Classrooms> query = realm.where(Classrooms.class).equalTo("classRoomId", classroomId);
        return query.findFirst();
    }

    /**
     * Method to get the book data of created exam.
     *
     * @param bookId
     * @return
     */
    public AuthorBook getExamAuthorBook(int bookId) {
        RealmQuery<AuthorBook> query = realm.where(AuthorBook.class).equalTo("book.bookId", bookId);
        return query.findFirst();
    }


    /**
     * Add classrooms
     *
     * @param exam
     */
    public void addExams(Exam exam) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(exam);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addClassrooms Exception : " + e.toString());
        }
    }

    /**
     * arti's code starts from here...
     */

    /**
     * This method for save the preferences data
     *
     * @param preferences
     */
    public void saveAllPreferences(Preferences preferences) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(preferences);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.i(TAG, " saveAllPreferences Exceptions : " + e.getLocalizedMessage());
        }
    }


    /**
     * save author profile information
     *
     * @param authorProfile
     */
    public void saveAuthorProfile(AuthorProfile authorProfile) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(authorProfile);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.i(TAG, " saveAuthorProfile Exceptions : " + e.getLocalizedMessage());
        }
    }


    public User getUser(int userId) {
        try {
            return realm.where(User.class).equalTo("userId", userId).findFirst();
        } catch (Exception e) {
            Log.i(TAG, "getUser Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    public void saveUser(User user) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.i(TAG, " saveUser Exceptions : " + e.getLocalizedMessage());
        }
    }

    public AuthorProfile getAuthorprofile(int userId) {
        try {
            return realm.where(AuthorProfile.class).equalTo("authorId", userId).findFirst();
        } catch (Exception e) {
            Log.i(TAG, "getAuthorProfile Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    public void destroy() {
        if (realm != null) {
            realm.close();
        }
    }


}
