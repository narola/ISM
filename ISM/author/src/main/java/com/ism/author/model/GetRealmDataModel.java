package com.ism.author.model;

import com.ism.author.object.Global;
import com.ism.author.utility.Utility;
import com.ism.author.ws.model.BookData;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.CommentList;
import com.ism.author.ws.model.Exams;
import com.ism.author.ws.model.FeedImages;
import com.ism.author.ws.model.Feeds;

import model.AuthorBook;
import realmhelper.AuthorHelper;

/**
 * Created by c166 on 06/01/16.
 */
public class GetRealmDataModel {


    public GetRealmDataModel() {

    }


    /**
     * get the realm Feeds model for postfeed.
     *
     * @param feed
     * @return
     */
    public model.Feeds getRealmFeed(Feeds feed) {

        model.Feeds postFeed = new model.Feeds();
        postFeed.setFeedId(feed.getFeedId());

        model.User user = new model.User();
        user.setUserId(feed.getFeedBy());
        user.setProfilePicture(feed.getProfilePic());
        user.setFullName(feed.getFullName());

        postFeed.setFeedBy(user);
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

            model.FeedComment feedComment = new model.FeedComment();
            feedComment.setFeedCommentId(comment.getId());
            feedComment.setComment(comment.getComment());
            feedComment.setCreatedDate(Utility.getRealmDateFormat(comment.getCreatedDate()));

            model.User commentBy = new model.User();
            commentBy.setUserId(comment.getCommentBy());
            commentBy.setProfilePicture(comment.getProfilePic());
            commentBy.setFullName(comment.getFullName());

            feedComment.setCommentBy(commentBy);
            feedComment.setFeed(postFeed);
            postFeed.getComments().add(feedComment);
        }


        for (FeedImages image : feed.getFeedImages()) {

            model.FeedImage feedImage = new model.FeedImage();
            feedImage.setFeedImageId(image.getId());
            feedImage.setImageLink(image.getImageLink());
            feedImage.setFeed(postFeed);

            postFeed.getFeedImages().add(feedImage);
        }
        return postFeed;
    }


    /**
     * get realm AuthorBook model.
     *
     * @param bookData
     * @param id
     * @return
     */
    public model.AuthorBook getRealmAuthorBook(BookData bookData, int id) {

        model.AuthorBook authorBook = new model.AuthorBook();
        authorBook.setAuthorBookId(id);
        authorBook.setUser(getRealmUser());

        model.Books book = new model.Books();
        book.setBookId(Integer.valueOf(bookData.getBookId()));
        book.setBookName(bookData.getBookName());
        book.setBookDescription(bookData.getDescription());
        book.setEbookLink(bookData.getEbookLink());
        book.setImageLink(bookData.getFrontCoverImage());
        book.setFrontCoverImage(bookData.getFrontCoverImage());
        book.setBackCoverImage(bookData.getBackCoverImage());
        book.setPrice(bookData.getPrice());
        book.setPublisherName(bookData.getPublisherName());

        authorBook.setBook(book);
        return authorBook;

    }

    /**
     * get realm User model.
     *
     * @return
     */
    public model.User getRealmUser() {
        model.User user = new model.User();
        user.setUserId(Integer.valueOf(Global.strUserId));
        user.setFullName(Global.strFullName);
        user.setProfilePicture(Global.strProfilePic);
        return user;
    }


    /**
     * get realm classroom model.
     *
     * @param classroom
     * @return
     */
    public model.Classrooms getRealmClassroom(Classrooms classroom) {

        model.Classrooms classrooms = new model.Classrooms();
        classrooms.setClassRoomId(Integer.valueOf(classroom.getId()));
        classrooms.setClassName(classroom.getClassName());
        classrooms.setNickName(classroom.getClassNickname());
        return classrooms;

    }


    public model.Exam getRealmExam(Exams exams, AuthorHelper authorHelper) {

        model.Exam exam = new model.Exam();
        exam.setExamId(Integer.valueOf(exams.getExamId()));
        exam.setExamName(exams.getExamName());
        exam.setInstructions(exams.getExamInstructions());

        model.Classrooms classroom = null;
        classroom = authorHelper.getExamClassroom(Integer.valueOf(exams.getClassroomId()));
        if (classroom == null) {
            classroom = new model.Classrooms();
            classroom.setClassRoomId(Integer.valueOf(exams.getClassroomId()));
            classroom.setClassName(exams.getClassroomName());
            classroom.setNickName("");
        }
        exam.setClassroom(classroom);
        exam.setCreatedBy(getRealmUser());

        model.AuthorBook authorBook = null;
        authorBook = authorHelper.getExamAuthorBook(Integer.valueOf(exams.getBookId()));
        if (authorBook == null) {
            authorBook.setAuthorBookId(authorHelper.getTotalRecordsInTable(AuthorBook.class) + 1);
            authorBook.setUser(getRealmUser());

            model.Books book = new model.Books();
            book.setBookId(Integer.valueOf(exams.getBookId()));
            book.setBookName(exams.getBookName());
            authorBook.setBook(book);
        }

        exam.setAuthorBook(authorBook);
        exam.setExamType(exams.getExamType());
        exam.setExamCategory(exams.getExamCategory());
        exam.setExamMode(exams.getExamMode());
        exam.setPassPercentage(exams.getPassPercentage());
        exam.setDuration(exams.getDuration());
        exam.setAttemptCount(exams.getAttemptCount());
        exam.setNegativeMarkValue(exams.getNegativeMarkValue());
        exam.setCorrectAnswerScore(exams.getCorrectAnswerScore());
        exam.setNegativeMarking(exams.getNegativeMarking().equalsIgnoreCase("yes") ? true : false);
        exam.setUseQuestionScore(exams.getUseQuestionScore().equalsIgnoreCase("yes") ? true : false);
        exam.setRandomQuestion(exams.getRandomQuestion().equalsIgnoreCase("yes") ? true : false);
        exam.setDeclareResults(exams.getDeclareResults().equalsIgnoreCase("yes") ? true : false);
        exam.setCreatedDate(Utility.getRealmDateFormat(exams.getExamCreatedDate()));
        exam.setModifiedDate(null);
        exam.setTotalStudent(exams.getTotalStudent());
        exam.setTotalStudentAttempted(exams.getTotalStudentAttempted());
        exam.setExamAssessor(exams.getExamAssessor());
        exam.setExamStartDate(Utility.getRealmDateFormat(exams.getExamStartDate()));
        exam.setExamStartTime(exams.getExamStartTime());
        exam.setTotalQuestion(exams.getTotalQuestion());
        exam.setEvaluationStatus(exams.getEvaluationStatus());
        exam.setAverageScore(exams.getAverageScore());
        exam.setTotalAssessed(exams.getTotalAssessed());
        exam.setTotalUnassessed(exams.getTotalUnAssessed());


        return exam;
    }


}
