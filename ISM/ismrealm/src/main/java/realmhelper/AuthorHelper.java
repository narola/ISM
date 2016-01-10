package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.ROAdminConfig;
import model.ROAuthorBook;
import model.ROAuthorProfile;
import model.ROClassrooms;
import model.ROExam;
import model.ROFeedComment;
import model.ROFeedLike;
import model.ROFeeds;
import model.ROPreferences;
import model.ROUser;

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

        RealmResults<ROAdminConfig> ROAdminConfigs = realm.where(ROAdminConfig.class)
                .equalTo("configKey", "globalPassword")
                .findAll();

        if (ROAdminConfigs != null && ROAdminConfigs.size() > 0) {
            return ROAdminConfigs.get(0).getConfigValue();
        } else {
            return null;
        }
    }

    /**
     * Save admin config data
     *
     * @param ROAdminConfig
     */
    public void saveAdminConfig(ROAdminConfig ROAdminConfig) {
        try {
            Number configId = realm.where(ROAdminConfig.class).max("configId");
            long newId = 0;
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

        RealmQuery<ROAdminConfig> query = null;
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
    public RealmResults<ROFeeds> getAllPostFeeds() {
        RealmQuery<ROFeeds> query = realm.where(ROFeeds.class);
        return query.findAll();
    }


    /**
     * Add ROFeeds into table.
     *
     * @param ROFeeds
     */
    public void addFeeds(ROFeeds ROFeeds) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROFeeds);
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
    public void addComment(int feedId, ROFeedComment comment) {

        try {

            RealmQuery<ROFeeds> query = realm.where(ROFeeds.class).equalTo("feedId", feedId);
            ROFeeds feed = query.findFirst();

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
     * Method to update {@param selfLike} in ROFeedLike.
     *
     * @param feedId
     * @param selfLike
     */
    public void updateFeedSelfLikeStatus(int feedId, String selfLike) {

        try {
            RealmQuery<ROFeeds> query = realm.where(ROFeeds.class).equalTo("feedId", feedId);
            ROFeeds feed = query.findFirst();
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
     * Method to insert {@param ROFeedLike} data and its sync value.
     *
     * @param ROFeedLike
     */
    public void insertUpdateLikeFeedData(ROFeedLike ROFeedLike) {
        try {
            ROFeedLike isRecordExist = realm.where(ROFeedLike.class)
                    .equalTo("feed.feedId", ROFeedLike.getFeed().getFeedId()).equalTo("likeBy.userId", ROFeedLike.getLikeBy().getUserId()).findFirst();

            if (isRecordExist == null) {
                Number feedLikeId = realm.where(ROFeedLike.class).max("feedLikeId");
                long newId = 0;
                if (feedLikeId != null) {
                    newId = (long) feedLikeId + 1;
                }
                ROFeedLike.setFeedLikeId((int) newId);
            }

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROFeedLike);
            realm.commitTransaction();


            getTotalRecordsInTable(ROFeedLike.class);
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
                    ROFeedLike ROFeedLike = realm.where(ROFeedLike.class)
                            .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                    realm.beginTransaction();
                    ROFeedLike.setIsSync(1);
                    realm.copyToRealmOrUpdate(ROFeedLike);
                    realm.commitTransaction();

                }
            }
            if (arrListUnlikeFeedId.size() > 0) {
                for (int feedId : arrListUnlikeFeedId) {
                    ROFeedLike ROFeedLike = realm.where(ROFeedLike.class)
                            .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                    realm.beginTransaction();
                    ROFeedLike.setIsSync(1);
                    realm.copyToRealmOrUpdate(ROFeedLike);
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
                            ROFeedLike ROFeedLike = bgRealm.where(ROFeedLike.class)
                                    .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                            ROFeedLike.setIsSync(1);
                            bgRealm.copyToRealmOrUpdate(ROFeedLike);
                        }
                    }
                    if (arrListUnlikeFeedId.size() > 0) {
                        for (int feedId : arrListUnlikeFeedId) {
                            ROFeedLike ROFeedLike = bgRealm.where(ROFeedLike.class)
                                    .equalTo("feed.feedId", feedId).equalTo("likeBy.userId", Integer.valueOf(strUserId)).findFirst();
                            ROFeedLike.setIsSync(1);
                            bgRealm.copyToRealmOrUpdate(ROFeedLike);
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
     * Add ROAuthorBook.
     *
     * @param ROAuthorBook
     */
    public void addAuthorBooks(ROAuthorBook ROAuthorBook) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROAuthorBook);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addAuthorBooks Exception : " + e.toString());
        }
    }

    /**
     * @return all the authorbooks.
     */
    public RealmResults<ROAuthorBook> getAuthorBooks() {
        RealmQuery<ROAuthorBook> query = realm.where(ROAuthorBook.class);
        return query.findAll();
    }


    /**
     * Add ROClassrooms
     *
     * @param ROClassrooms
     */
    public void addClassrooms(ROClassrooms ROClassrooms) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROClassrooms);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addClassrooms Exception : " + e.toString());
        }
    }


    /**
     * @return all the classrooms.
     */
    public RealmResults<ROClassrooms> getClassrooms() {
        RealmQuery<ROClassrooms> query = realm.where(ROClassrooms.class);
        return query.findAll();
    }


    /**
     * Method to get the class data for created exam.
     *
     * @param classroomId
     * @return
     */
    public ROClassrooms getExamClassroom(int classroomId) {

        RealmQuery<ROClassrooms> query = realm.where(ROClassrooms.class).equalTo("classRoomId", classroomId);
        return query.findFirst();

    }

    /**
     * Method to get the book data of created exam.
     *
     * @param bookId
     * @return
     */
    public ROAuthorBook getExamAuthorBook(int bookId) {

        RealmQuery<ROAuthorBook> query = realm.where(ROAuthorBook.class).equalTo("book.bookId", bookId);
        return query.findFirst();

    }


    /**
     * Add classrooms
     *
     * @param ROExam
     */
    public void addExams(ROExam ROExam) {

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROExam);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addClassrooms Exception : " + e.toString());
        }
    }

    /**
     * @return all the exams.
     */
    public RealmResults<ROExam> getAllExams() {
        RealmQuery<ROExam> query = realm.where(ROExam.class);
        return query.findAll();
    }


    /**
     * filter exams by book.
     *
     * @param bookId
     * @return
     */
    public RealmResults<ROExam> getExamsByBooks(int bookId) {

        RealmQuery<ROExam> query = realm.where(ROExam.class).equalTo("authorBook.book.bookId", bookId);
        return query.findAll();

    }


    /**
     * filter exams by classroom.
     *
     * @param classRoomId
     * @return
     */
    public RealmResults<ROExam> getExamsByClassRooms(int classRoomId) {

        RealmQuery<ROExam> query = realm.where(ROExam.class).equalTo("classroom.classRoomId", classRoomId);
        return query.findAll();

    }

    /**
     * filter exams by evaluation status.
     *
     * @param evaluationStatus
     * @return
     */
    public RealmResults<ROExam> getExamsByEvaluationStatus(String evaluationStatus) {

        RealmQuery<ROExam> query = realm.where(ROExam.class).equalTo("evaluationStatus", evaluationStatus);
        return query.findAll();

    }


    /**
     * filter exams by date.
     *
     * @param startDate
     * @return
     */
    public RealmResults<ROExam> getExamsByDates(String startDate) {

        RealmQuery<ROExam> query = realm.where(ROExam.class).equalTo("examStartDate", startDate);
        return query.findAll();

    }


    public ROExam getExamForSubmission(int examId) {
        RealmQuery<ROExam> query = realm.where(ROExam.class).equalTo("examId", examId);
        return query.findFirst();
    }


    /**
     * arti's code starts from here...
     */

    /**
     * This method for save the preferences data
     *
     * @param ROPreferences
     */
    public void saveAllPreferences(ROPreferences ROPreferences) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROPreferences);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.i(TAG, " saveAllPreferences Exceptions : " + e.getLocalizedMessage());
        }
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
        } catch (Exception e) {
            Log.e(TAG, " saveAuthorProfile Exceptions : " + e.getLocalizedMessage());
        }
    }


    public ROUser getUser(int userId) {
        try {
            return realm.where(ROUser.class).equalTo("userId", userId).findFirst();
        } catch (Exception e) {
            Log.i(TAG, "getUser Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    public void saveUser(ROUser ROUser) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ROUser);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.i(TAG, " saveUser Exceptions : " + e.getLocalizedMessage());
        }
    }

    public ROAuthorProfile getAuthorprofile(int userId) {
        try {
            return realm.where(ROAuthorProfile.class).equalTo("serverAuthorId", userId).findFirst();
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
