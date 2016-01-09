package com.ism.author.model;

import com.ism.author.object.Global;
import com.ism.author.utility.Utility;
import com.ism.author.ws.model.BookData;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.CommentList;
import com.ism.author.ws.model.ExamSubmission;
import com.ism.author.ws.model.Exams;
import com.ism.author.ws.model.Examsubmittor;
import com.ism.author.ws.model.FeedImages;
import com.ism.author.ws.model.Feeds;

import model.ROAuthorBook;
import model.ROBooks;
import model.ROClassrooms;
import model.ROExam;
import model.ROExamSubmission;
import model.ROFeedComment;
import model.ROFeedImage;
import model.ROFeeds;
import model.ROUser;
import model.authormodel.ROExamSubmittor;
import realmhelper.AuthorHelper;

/**
 * Created by c166 on 06/01/16.
 */
public class RealmDataModel {


    private static final String TAG = RealmDataModel.class.getSimpleName();

    public RealmDataModel() {

    }


    /**
     * get the realm ROFeeds model for postfeed.
     *
     * @param feed
     * @return
     */
    public ROFeeds getRealmFeed(Feeds feed) {

        ROFeeds postFeed = new ROFeeds();
        postFeed.setFeedId(feed.getFeedId());

        ROUser ROUser = new ROUser();
        ROUser.setUserId(feed.getFeedBy());
        ROUser.setProfilePicture(feed.getProfilePic());
        ROUser.setFullName(feed.getFullName());

        postFeed.setFeedBy(ROUser);
        postFeed.setFeedText(feed.getFeedText());
        postFeed.setVideoLink(feed.getVideoLink());
        postFeed.setAudioLink(feed.getAudioLink());
        postFeed.setVideoThumbnail(feed.getVideoThumbnail());
        postFeed.setPostedOn(Utility.getRealmDateFormat(feed.getPostedOn()));
        postFeed.setTotalLike(feed.getTotalLike());
        postFeed.setTotalComment(feed.getTotalComment());
        postFeed.setCreatedDate(Utility.getRealmDateFormat(feed.getCreatedDate()));
        postFeed.setModifiedDate(Utility.getRealmDateFormat(feed.getModifiedDate()));
        postFeed.setSelfLike(feed.getSelfLike());
        postFeed.setIsSync(1);


        for (CommentList comment : feed.getCommentList()) {

            ROFeedComment ROFeedComment = new ROFeedComment();
            ROFeedComment.setFeedCommentId(comment.getId());
            ROFeedComment.setComment(comment.getComment());
            ROFeedComment.setCreatedDate(Utility.getRealmDateFormat(comment.getCreatedDate()));

            ROUser commentBy = new ROUser();
            commentBy.setUserId(comment.getCommentBy());
            commentBy.setProfilePicture(comment.getProfilePic());
            commentBy.setFullName(comment.getFullName());

            ROFeedComment.setCommentBy(commentBy);
            ROFeedComment.setFeed(postFeed);
            postFeed.getComments().add(ROFeedComment);
        }


        for (FeedImages image : feed.getFeedImages()) {

            ROFeedImage ROFeedImage = new ROFeedImage();
            ROFeedImage.setFeedImageId(image.getId());
            ROFeedImage.setImageLink(image.getImageLink());
            ROFeedImage.setFeed(postFeed);

            postFeed.getROFeedImages().add(ROFeedImage);
        }
        return postFeed;
    }


    /**
     * get realm ROAuthorBook model.
     *
     * @param bookData
     * @param id
     * @return
     */
    public ROAuthorBook getRealmAuthorBook(BookData bookData, int id) {

        ROAuthorBook ROAuthorBook = new ROAuthorBook();
        ROAuthorBook.setAuthorBookId(id);
        ROAuthorBook.setRoUser(getRealmUser());

        ROBooks book = new ROBooks();
        book.setBookId(Integer.valueOf(bookData.getBookId()));
        book.setBookName(bookData.getBookName());
        book.setBookDescription(bookData.getDescription());
        book.setEbookLink(bookData.getEbookLink());
        book.setImageLink(bookData.getFrontCoverImage());
        book.setFrontCoverImage(bookData.getFrontCoverImage());
        book.setBackCoverImage(bookData.getBackCoverImage());
        book.setPrice(bookData.getPrice());
        book.setPublisherName(bookData.getPublisherName());

        ROAuthorBook.setRoBooks(book);
        return ROAuthorBook;

    }

    /**
     * get realm User model.
     *
     * @return
     */
    public ROUser getRealmUser() {
        ROUser ROUser = new ROUser();
        ROUser.setUserId(Integer.valueOf(Global.strUserId));
        ROUser.setFullName(Global.strFullName);
        ROUser.setProfilePicture(Global.strProfilePic);
        return ROUser;
    }


    /**
     * get realm classroom model.
     *
     * @param classroom
     * @return
     */
    public ROClassrooms getRealmClassroom(Classrooms classroom) {

        ROClassrooms ROClassrooms = new ROClassrooms();
        ROClassrooms.setClassRoomId(Integer.valueOf(classroom.getId()));
        ROClassrooms.setClassName(classroom.getClassName());
        ROClassrooms.setNickName(classroom.getClassNickname());
        return ROClassrooms;

    }


    /**
     * get realm for ROExam.
     *
     * @param exams
     * @param authorHelper
     * @return
     */
    public ROExam getRealmExam(Exams exams, AuthorHelper authorHelper) {

        ROExam ROExam = new ROExam();
        ROExam.setExamId(Integer.valueOf(exams.getExamId()));
        ROExam.setExamName(exams.getExamName());
        ROExam.setInstructions(exams.getExamInstructions());

        ROClassrooms classroom = null;
        classroom = authorHelper.getExamClassroom(Integer.valueOf(exams.getClassroomId()));

        ROExam.setRoClassroom(classroom);
        ROExam.setCreatedBy(getRealmUser());

        ROAuthorBook ROAuthorBook = null;
        ROAuthorBook = authorHelper.getExamAuthorBook(Integer.valueOf(exams.getBookId()));


        ROExam.setRoAuthorBook(ROAuthorBook);
        ROExam.setExamType(exams.getExamType());
        ROExam.setExamCategory(exams.getExamCategory());
        ROExam.setExamMode(exams.getExamMode());
        ROExam.setPassPercentage(exams.getPassPercentage());
        ROExam.setDuration(exams.getDuration());
        ROExam.setAttemptCount(exams.getAttemptCount());
        ROExam.setNegativeMarkValue(exams.getNegativeMarkValue());
        ROExam.setCorrectAnswerScore(exams.getCorrectAnswerScore());
        ROExam.setNegativeMarking(exams.getNegativeMarking().equalsIgnoreCase("yes") ? true : false);
        ROExam.setUseQuestionScore(exams.getUseQuestionScore().equalsIgnoreCase("yes") ? true : false);
        ROExam.setRandomQuestion(exams.getRandomQuestion().equalsIgnoreCase("yes") ? true : false);
        ROExam.setDeclareResults(exams.getDeclareResults().equalsIgnoreCase("yes") ? true : false);
        ROExam.setCreatedDate(Utility.getRealmDateFormat(exams.getExamCreatedDate()));
        ROExam.setModifiedDate(null);
        ROExam.setTotalStudent(exams.getTotalStudent());
        ROExam.setTotalStudentAttempted(exams.getTotalStudentAttempted());
        ROExam.setExamAssessor(exams.getExamAssessor());
        ROExam.setExamStartDate(Utility.getRealmDateFormat(exams.getExamStartDate()));
        ROExam.setExamStartTime(exams.getExamStartTime());
        ROExam.setTotalQuestion(exams.getTotalQuestion());
        ROExam.setEvaluationStatus(exams.getEvaluationStatus());
        ROExam.setAverageScore(exams.getAverageScore());
        ROExam.setTotalAssessed(exams.getTotalAssessed());
        ROExam.setTotalUnassessed(exams.getTotalUnAssessed());

        return ROExam;


//        if (classroom == null && !exams.getClassroomId().equals("0")) {
//            classroom = new model.ROClassrooms();
//            classroom.setClassRoomId(Integer.valueOf(exams.getClassroomId()));
//            classroom.setClassName(exams.getClassroomName());
//            classroom.setNickName("");
//
//        }


//        if (ROAuthorBook == null) {
//            ROAuthorBook.setAuthorBookId(authorHelper.getTotalRecordsInTable(ROAuthorBook.class) + 1);
//            ROAuthorBook.setUser(getRealmUser());
//
//            model.ROBooks book = new model.ROBooks();
//            book.setBookId(Integer.valueOf(exams.getBookId()));
//            book.setBookName(exams.getBookName());
//            ROAuthorBook.setRoBooks(book);
//        }
    }


    /**
     * get the students submitted the exam.
     *
     * @param examSubmission
     * @return
     */
    public ROExamSubmission getRealmExamSubmission(ExamSubmission examSubmission, AuthorHelper authorHelper) {

        ROExamSubmission ROExamSubmissionModel = new ROExamSubmission();

        ROExamSubmissionModel.setExamId(Integer.valueOf(examSubmission.getExamId()));

        ROExam ROExam = authorHelper.getExamForSubmission(Integer.valueOf(examSubmission.getExamId()));
        ROExamSubmissionModel.setRoExam(ROExam);

        for (Examsubmittor examsubmittor : examSubmission.getExamsubmittor()) {

            ROExamSubmittor ROExamSubmittorModel = new ROExamSubmittor();
            ROExamSubmittorModel.setStudentId(Integer.valueOf(examsubmittor.getStudentId()));
            ROExamSubmittorModel.setStudentName(examsubmittor.getStudentName());
            ROExamSubmittorModel.setStudentProfilePic(examsubmittor.getStudentProfilePic());
            ROExamSubmittorModel.setStudentSchoolName(examsubmittor.getStudentSchoolName());
            ROExamSubmittorModel.setStudentClassName(examsubmittor.getStudentClassName());
            ROExamSubmittorModel.setEvaluationScore(examsubmittor.getEvaluationScore());
            ROExamSubmittorModel.setExamStatus(examsubmittor.getExamStatus());
            ROExamSubmittorModel.setSubmissionDate(examsubmittor.getSubmissionDate());
            ROExamSubmittorModel.setRemarks(examsubmittor.getRemarks());

            ROExamSubmissionModel.getRoExamSubmittors().add(ROExamSubmittorModel);

        }

        return ROExamSubmissionModel;

    }

}
