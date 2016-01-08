package com.ism.teacher.model;


import com.ism.teacher.Utility.Utility;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.ClassPerformance;
import com.ism.teacher.ws.model.CommentList;
import com.ism.teacher.ws.model.FeedImages;
import com.ism.teacher.ws.model.Feeds;
import com.ism.teacher.ws.model.StudentsScore;
import com.ism.teacher.ws.model.SubjectWiseScore;

import model.teachermodel.ClassPerformanceRealmModel;
import model.teachermodel.StudentsScoreRealmModel;
import model.teachermodel.SubjectWiseScoreRealmModel;

/**
 * Created by c166 on 06/01/16.
 */
public class GetRealmDataModel {


    public GetRealmDataModel() {

    }

    /**
     * This method return ClassPerformanceRealmModel object which is used by realm to store data of classPerformance.
     *
     * @param classPerformance
     * @return
     */

    public ClassPerformanceRealmModel getRealmClassPerformance(ClassPerformance classPerformance) {

        ClassPerformanceRealmModel classPerformanceRealmModel = new ClassPerformanceRealmModel();
        classPerformanceRealmModel.setExamName(classPerformance.getExamName());
        classPerformanceRealmModel.setExamScore(classPerformance.getExamScore());
        classPerformanceRealmModel.setInternalMarks(classPerformance.getInternalMarks());

        for (StudentsScore studentsScore : classPerformance.getStudentsScore()) {

            StudentsScoreRealmModel studentsScoreRealmModel = new StudentsScoreRealmModel();

            studentsScoreRealmModel.setStudentId(studentsScore.getStudentId());
            studentsScoreRealmModel.setStudentName(studentsScore.getStudentName());
            studentsScoreRealmModel.setStudentPic(studentsScore.getStudentPic());
            studentsScoreRealmModel.setStudentScore(studentsScore.getStudentScore());
            studentsScoreRealmModel.setPercentage(studentsScore.getPercentage());
            studentsScoreRealmModel.setRank(studentsScore.getRank());
            studentsScoreRealmModel.setGrade(studentsScore.getGrade());
            studentsScoreRealmModel.setHeadMistressComment(studentsScore.getHeadMistressComment());
            studentsScoreRealmModel.setClassMistressComment(studentsScore.getClassMistressComment());

            for (SubjectWiseScore subjectWiseScore : studentsScore.getSubjectWiseScore()) {

                SubjectWiseScoreRealmModel subjectWiseScoreRealmModel = new SubjectWiseScoreRealmModel();
                subjectWiseScoreRealmModel.setSubjectId(subjectWiseScore.getSubjectId());
                subjectWiseScoreRealmModel.setSubjectName(subjectWiseScore.getSubjectName());
                subjectWiseScoreRealmModel.setMarksObtained(subjectWiseScore.getMarksObtained());
                subjectWiseScoreRealmModel.setPercentage(subjectWiseScore.getPercentage());
                subjectWiseScoreRealmModel.setSubjectGrade(subjectWiseScore.getSubjectGrade());
                subjectWiseScoreRealmModel.setRemarks(subjectWiseScore.getRemarks());
                subjectWiseScoreRealmModel.setSubjectRank(subjectWiseScore.getSubjectRank());
                subjectWiseScoreRealmModel.setInternalScore(subjectWiseScore.getInternalScore());

                studentsScoreRealmModel.getSubjectWiseScore().add(subjectWiseScoreRealmModel);

            }


            classPerformanceRealmModel.getStudentsScore().add(studentsScoreRealmModel);
        }
        return classPerformanceRealmModel;


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
//    public model.Classrooms getRealmClassroom(Classrooms classroom) {
//
//        model.Classrooms classrooms = new model.Classrooms();
//        classrooms.setClassRoomId(Integer.valueOf(classroom.getId()));
//        classrooms.setClassName(classroom.getClassName());
//        classrooms.setNickName(classroom.getClassNickname());
//        return classrooms;
//
//    }


//    public model.Exam getRealmExam(Exams exams, TeacherHelper teacherHelper) {
//
//        model.Exam exam = new model.Exam();
//        exam.setExamId(Integer.valueOf(exams.getExamId()));
//        exam.setExamName(exams.getExamName());
//        exam.setInstructions(exams.getExamInstructions());
//
//        model.Classrooms classroom = null;
//        classroom = teacherHelper.getExamClassroom(Integer.valueOf(exams.getClassroomId()));
//        if (classroom == null) {
//            classroom = new model.Classrooms();
//            classroom.setClassRoomId(Integer.valueOf(exams.getClassroomId()));
//            classroom.setClassName(exams.getClassroomName());
//            classroom.setNickName("");
//        }
//        exam.setClassroom(classroom);
//        exam.setCreatedBy(getRealmUser());
//
//        AuthorBook authorBook = null;
//        authorBook = teacherHelper.getExamAuthorBook(Integer.valueOf(exams.getBookId()));
//        if (authorBook == null) {
//            authorBook.setAuthorBookId(teacherHelper.getTotalRecordsInTable(AuthorBook.class) + 1);
//            authorBook.setUser(getRealmUser());
//
//            model.Books book = new model.Books();
//            book.setBookId(Integer.valueOf(exams.getBookId()));
//            book.setBookName(exams.getBookName());
//            authorBook.setBook(book);
//        }
//
//        exam.setAuthorBook(authorBook);
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
