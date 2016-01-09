package com.ism.teacher.model;


import com.ism.teacher.Utility.Utility;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.ClassPerformance;
import com.ism.teacher.ws.model.CommentList;
import com.ism.teacher.ws.model.FeedImages;
import com.ism.teacher.ws.model.Feeds;
import com.ism.teacher.ws.model.StudentsScore;
import com.ism.teacher.ws.model.SubjectWiseScore;

import model.ROFeedComment;
import model.ROFeedImage;
import model.ROFeeds;
import model.ROUser;
import model.teachermodel.ROClassPerformance;
import model.teachermodel.ROStudentsScore;
import model.teachermodel.ROSubjectWiseScore;

/**
 * Created by c166 on 06/01/16.
 */
public class GetRealmDataModel {


    public GetRealmDataModel() {

    }

    /**
     * This method return ROClassPerformance object which is used by realm to store data of classPerformance.
     *
     * @param classPerformance
     * @return
     */

    public ROClassPerformance getRealmClassPerformance(ClassPerformance classPerformance) {

        ROClassPerformance ROClassPerformance = new ROClassPerformance();
        ROClassPerformance.setExamName(classPerformance.getExamName());
        ROClassPerformance.setExamScore(classPerformance.getExamScore());
        ROClassPerformance.setInternalMarks(classPerformance.getInternalMarks());

        for (StudentsScore studentsScore : classPerformance.getStudentsScore()) {

            ROStudentsScore ROStudentsScore = new ROStudentsScore();

            ROStudentsScore.setStudentId(studentsScore.getStudentId());
            ROStudentsScore.setStudentName(studentsScore.getStudentName());
            ROStudentsScore.setStudentPic(studentsScore.getStudentPic());
            ROStudentsScore.setStudentScore(studentsScore.getStudentScore());
            ROStudentsScore.setPercentage(studentsScore.getPercentage());
            ROStudentsScore.setRank(studentsScore.getRank());
            ROStudentsScore.setGrade(studentsScore.getGrade());
            ROStudentsScore.setHeadMistressComment(studentsScore.getHeadMistressComment());
            ROStudentsScore.setClassMistressComment(studentsScore.getClassMistressComment());

            for (SubjectWiseScore subjectWiseScore : studentsScore.getSubjectWiseScore()) {

                ROSubjectWiseScore ROSubjectWiseScore = new ROSubjectWiseScore();
                ROSubjectWiseScore.setSubjectId(subjectWiseScore.getSubjectId());
                ROSubjectWiseScore.setSubjectName(subjectWiseScore.getSubjectName());
                ROSubjectWiseScore.setMarksObtained(subjectWiseScore.getMarksObtained());
                ROSubjectWiseScore.setPercentage(subjectWiseScore.getPercentage());
                ROSubjectWiseScore.setSubjectGrade(subjectWiseScore.getSubjectGrade());
                ROSubjectWiseScore.setRemarks(subjectWiseScore.getRemarks());
                ROSubjectWiseScore.setSubjectRank(subjectWiseScore.getSubjectRank());
                ROSubjectWiseScore.setInternalScore(subjectWiseScore.getInternalScore());

                ROStudentsScore.getRoSubjectWiseScore().add(ROSubjectWiseScore);

            }


            ROClassPerformance.getStudentsScore().add(ROStudentsScore);
        }
        return ROClassPerformance;


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
//    public model.ROClassrooms getRealmClassroom(ROClassrooms classroom) {
//
//        model.ROClassrooms classrooms = new model.ROClassrooms();
//        classrooms.setClassRoomId(Integer.valueOf(classroom.getId()));
//        classrooms.setClassName(classroom.getClassName());
//        classrooms.setNickName(classroom.getClassNickname());
//        return classrooms;
//
//    }


//    public model.ROExam getRealmExam(Exams exams, TeacherHelper teacherHelper) {
//
//        model.ROExam exam = new model.ROExam();
//        exam.setExamId(Integer.valueOf(exams.getExamId()));
//        exam.setExamName(exams.getExamName());
//        exam.setInstructions(exams.getExamInstructions());
//
//        model.ROClassrooms classroom = null;
//        classroom = teacherHelper.getExamClassroom(Integer.valueOf(exams.getClassroomId()));
//        if (classroom == null) {
//            classroom = new model.ROClassrooms();
//            classroom.setClassRoomId(Integer.valueOf(exams.getClassroomId()));
//            classroom.setClassName(exams.getClassroomName());
//            classroom.setNickName("");
//        }
//        exam.setRoClassroom(classroom);
//        exam.setCreatedBy(getRealmUser());
//
//        ROAuthorBook authorBook = null;
//        authorBook = teacherHelper.getExamAuthorBook(Integer.valueOf(exams.getBookId()));
//        if (authorBook == null) {
//            authorBook.setAuthorBookId(teacherHelper.getTotalRecordsInTable(ROAuthorBook.class) + 1);
//            authorBook.setUser(getRealmUser());
//
//            model.ROBooks book = new model.ROBooks();
//            book.setBookId(Integer.valueOf(exams.getBookId()));
//            book.setBookName(exams.getBookName());
//            authorBook.setRoBooks(book);
//        }
//
//        exam.setRoAuthorBook(authorBook);
//        exam.setExamType(exams.getExamType());
//        exam.setExamCategory(exams.getExamCategory());
//        exam.setExamMode(exams.getExamMode());
//        exam.setPassPercentage(exams.getPassPercentage());
//        exam.setDuration(exams.getDuration());
//        exam.setAttemptCount(exams.getAttemptCount());
//        exam.setNegativeMarkValue(exams.getNegativeMarkValue());
//        exam.setCorrectAnswerScore(exams.getCorrectAnswerScore());
//        exam.setNegativeMarking(exams.getNegativeMarking().equalsIgnoreCase("yes") ? true : false);
//        exam.setUseQuestionScore(exams.getUseQuestionScore().equalsIgnoreCase("yes") ? true : false);
//        exam.setRandomQuestion(exams.getRandomQuestion().equalsIgnoreCase("yes") ? true : false);
//        exam.setDeclareResults(exams.getDeclareResults().equalsIgnoreCase("yes") ? true : false);
//        exam.setCreatedDate(Utility.getRealmDateFormat(exams.getExamCreatedDate()));
//        exam.setModifiedDate(null);
//        exam.setTotalStudent(exams.getTotalStudent());
//        exam.setTotalStudentAttempted(exams.getTotalStudentAttempted());
//        exam.setExamAssessor(exams.getExamAssessor());
//        exam.setExamStartDate(Utility.getRealmDateFormat(exams.getExamStartDate()));
//        exam.setExamStartTime(exams.getExamStartTime());
//        exam.setTotalQuestion(exams.getTotalQuestion());
//        exam.setEvaluationStatus(exams.getEvaluationStatus());
//        exam.setAverageScore(exams.getAverageScore());
//        exam.setTotalAssessed(exams.getTotalAssessed());
//        exam.setTotalUnassessed(exams.getTotalUnAssessed());
//
//
//        return exam;
//    }


}
