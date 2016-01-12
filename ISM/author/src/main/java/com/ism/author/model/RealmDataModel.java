package com.ism.author.model;

import com.ism.author.object.Global;
import com.ism.author.utility.Utility;
import com.ism.author.ws.model.Answers;
import com.ism.author.ws.model.BookData;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.CommentList;
import com.ism.author.ws.model.Evaluation;
import com.ism.author.ws.model.ExamEvaluation;
import com.ism.author.ws.model.ExamQuestions;
import com.ism.author.ws.model.ExamSubmission;
import com.ism.author.ws.model.Exams;
import com.ism.author.ws.model.Examsubmittor;
import com.ism.author.ws.model.FeedImages;
import com.ism.author.ws.model.Feeds;
import com.ism.author.ws.model.QuestionPalette;
import com.ism.author.ws.model.Questions;
import com.ism.author.ws.model.Tags;

import model.ROAnswerChoices;
import model.ROAuthorBook;
import model.ROBooks;
import model.ROClassrooms;
import model.ROEvaluation;
import model.ROExam;
import model.ROExamQuestions;
import model.ROExamSubmission;
import model.ROFeedComment;
import model.ROFeedImage;
import model.ROFeeds;
import model.ROQuestionPalette;
import model.ROQuestions;
import model.ROStudentExamEvaluation;
import model.ROTags;
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
    public ROFeeds getROFeeds(Feeds feed) {

        ROFeeds roFeeds = new ROFeeds();
        roFeeds.setFeedId(feed.getFeedId());

        ROUser roUser = new ROUser();
        roUser.setUserId(feed.getFeedBy());
        roUser.setProfilePicture(feed.getProfilePic());
        roUser.setFullName(feed.getFullName());

        roFeeds.setFeedBy(roUser);
        roFeeds.setFeedText(feed.getFeedText());
        roFeeds.setVideoLink(feed.getVideoLink());
        roFeeds.setAudioLink(feed.getAudioLink());
        roFeeds.setVideoThumbnail(feed.getVideoThumbnail());
        roFeeds.setPostedOn(Utility.getRealmDateFormat(feed.getPostedOn()));
        roFeeds.setTotalLike(feed.getTotalLike());
        roFeeds.setTotalComment(feed.getTotalComment());
        roFeeds.setCreatedDate(Utility.getRealmDateFormat(feed.getCreatedDate()));
        roFeeds.setModifiedDate(Utility.getRealmDateFormat(feed.getModifiedDate()));
        roFeeds.setSelfLike(feed.getSelfLike());
        roFeeds.setIsSync(1);


        for (CommentList comment : feed.getCommentList()) {

            ROFeedComment roFeedComment = new ROFeedComment();
            roFeedComment.setFeedCommentId(comment.getId());
            roFeedComment.setComment(comment.getComment());
            roFeedComment.setCreatedDate(Utility.getRealmDateFormat(comment.getCreatedDate()));

            ROUser commentBy = new ROUser();
            commentBy.setUserId(comment.getCommentBy());
            commentBy.setProfilePicture(comment.getProfilePic());
            commentBy.setFullName(comment.getFullName());

            roFeedComment.setCommentBy(commentBy);
            roFeedComment.setRoFeed(roFeeds);
            roFeeds.getRoFeedComment().add(roFeedComment);
        }


        for (FeedImages image : feed.getFeedImages()) {

            ROFeedImage roFeedImage = new ROFeedImage();
            roFeedImage.setFeedImageId(image.getId());
            roFeedImage.setImageLink(image.getImageLink());
            roFeedImage.setFeed(roFeeds);

            roFeeds.getRoFeedImages().add(roFeedImage);
        }
        return roFeeds;
    }


    /**
     * get realm ROAuthorBook model.
     *
     * @param bookData
     * @param id
     * @return
     */
    public ROAuthorBook getROAuthorBook(BookData bookData, int id) {

        ROAuthorBook roAuthorBook = new ROAuthorBook();
        roAuthorBook.setAuthorBookId(id);
        roAuthorBook.setRoUser(getROUser());

        ROBooks roBook = new ROBooks();
        roBook.setBookId(Integer.valueOf(bookData.getBookId()));
        roBook.setBookName(bookData.getBookName());
        roBook.setBookDescription(bookData.getDescription());
        roBook.setEbookLink(bookData.getEbookLink());
        roBook.setImageLink(bookData.getFrontCoverImage());
        roBook.setFrontCoverImage(bookData.getFrontCoverImage());
        roBook.setBackCoverImage(bookData.getBackCoverImage());
        roBook.setPrice(bookData.getPrice());
        roBook.setPublisherName(bookData.getPublisherName());

        roAuthorBook.setRoBook(roBook);
        return roAuthorBook;

    }

    /**
     * get realm User model.
     *
     * @return
     */
    public ROUser getROUser() {
        ROUser roUser = new ROUser();
        roUser.setUserId(Integer.valueOf(Global.strUserId));
        roUser.setFullName(Global.strFullName);
        roUser.setProfilePicture(Global.strProfilePic);
        return roUser;
    }


    /**
     * get realm classroom model.
     *
     * @param classroom
     * @return
     */
    public ROClassrooms getROClassroom(Classrooms classroom) {

        ROClassrooms roClassroom = new ROClassrooms();
        roClassroom.setClassRoomId(Integer.valueOf(classroom.getId()));
        roClassroom.setClassName(classroom.getClassName());
        roClassroom.setNickName(classroom.getClassNickname());
        return roClassroom;

    }


    /**
     * get realm for ROExam.
     *
     * @param exams
     * @param authorHelper
     * @return
     */
    public ROExam getROExam(Exams exams, AuthorHelper authorHelper) {

        ROExam roExam = new ROExam();
        roExam.setExamId(Integer.valueOf(exams.getExamId()));
        roExam.setExamName(exams.getExamName());
        roExam.setInstructions(exams.getExamInstructions());

        ROClassrooms classroom = null;
        classroom = authorHelper.getExamClassroom(Integer.valueOf(exams.getClassroomId()));

        roExam.setRoClassroom(classroom);
        roExam.setCreatedBy(getROUser());

        ROAuthorBook authorBook = null;
        authorBook = authorHelper.getExamAuthorBook(Integer.valueOf(exams.getBookId()));


        roExam.setRoAuthorBook(authorBook);
        roExam.setExamType(exams.getExamType());
        roExam.setExamCategory(exams.getExamCategory());
        roExam.setExamMode(exams.getExamMode());
        roExam.setPassPercentage(exams.getPassPercentage());
        roExam.setDuration(exams.getDuration());
        roExam.setAttemptCount(exams.getAttemptCount());
        roExam.setNegativeMarkValue(exams.getNegativeMarkValue());
        roExam.setCorrectAnswerScore(exams.getCorrectAnswerScore());
        roExam.setNegativeMarking(exams.getNegativeMarking().equalsIgnoreCase("yes") ? true : false);
        roExam.setUseQuestionScore(exams.getUseQuestionScore().equalsIgnoreCase("yes") ? true : false);
        roExam.setRandomQuestion(exams.getRandomQuestion().equalsIgnoreCase("yes") ? true : false);
        roExam.setDeclareResults(exams.getDeclareResults().equalsIgnoreCase("yes") ? true : false);
        roExam.setCreatedDate(Utility.getRealmDateFormat(exams.getExamCreatedDate()));
        roExam.setModifiedDate(null);
        roExam.setTotalStudent(exams.getTotalStudent());
        roExam.setTotalStudentAttempted(exams.getTotalStudentAttempted());
        roExam.setExamAssessor(exams.getExamAssessor());
        roExam.setExamStartDate(Utility.getRealmDateFormat(exams.getExamStartDate()));
        roExam.setExamStartTime(exams.getExamStartTime());
        roExam.setTotalQuestion(exams.getTotalQuestion());
        roExam.setEvaluationStatus(exams.getEvaluationStatus());
        roExam.setAverageScore(exams.getAverageScore());
        roExam.setTotalAssessed(exams.getTotalAssessed());
        roExam.setTotalUnassessed(exams.getTotalUnAssessed());

        ROExamSubmission roExamSubmission = authorHelper.getExamSubmission(Integer.valueOf(exams.getExamId()));
        roExam.setExamSubmission(roExamSubmission);

        ROExamQuestions roExamQuestions = authorHelper.getExamQuestions(Integer.valueOf(exams.getExamId()));
        roExam.setExamQuestions(roExamQuestions);


        return roExam;
    }


    /**
     * get the students submitted the exam.
     *
     * @param examSubmission
     * @return
     */
    public ROExamSubmission getROExamSubmission(ExamSubmission examSubmission, AuthorHelper authorHelper) {

        ROExamSubmission roExamSubmission = new ROExamSubmission();
        roExamSubmission.setExamId(Integer.valueOf(examSubmission.getExamId()));

        ROExam roExam = authorHelper.getExam(Integer.valueOf(examSubmission.getExamId()));
        roExamSubmission.setRoExam(roExam);

        for (Examsubmittor examsubmittor : examSubmission.getExamsubmittor()) {

            ROExamSubmittor roExamSubmittor = new ROExamSubmittor();
            roExamSubmittor.setStudentId(Integer.valueOf(examsubmittor.getStudentId()));
            roExamSubmittor.setStudentName(examsubmittor.getStudentName());
            roExamSubmittor.setStudentProfilePic(examsubmittor.getStudentProfilePic());
            roExamSubmittor.setStudentSchoolName(examsubmittor.getStudentSchoolName());
            roExamSubmittor.setStudentClassName(examsubmittor.getStudentClassName());
            roExamSubmittor.setEvaluationScore(examsubmittor.getEvaluationScore());
            roExamSubmittor.setExamStatus(examsubmittor.getExamStatus());
            roExamSubmittor.setSubmissionDate(Utility.getRealmDateFormat(examsubmittor.getSubmissionDate()));
            roExamSubmittor.setRemarks(examsubmittor.getRemarks());


            /**
             * this is to check that exam is added or not for the student.
             */
            if (authorHelper.getExamSubmittor(Integer.valueOf(examsubmittor.getStudentId())) == null) {
                roExamSubmittor.getRoExams().add(roExam);
            } else {
                roExamSubmittor.getRoExams().addAll(authorHelper.getExamSubmittor(Integer.valueOf(examsubmittor.getStudentId())).getRoExams());
                if (!authorHelper.getExamSubmittor(Integer.valueOf(examsubmittor.getStudentId())).getRoExams().contains(roExam)) {
                    roExamSubmittor.getRoExams().add(roExam);
                }
            }


//            if (authorHelper.getStudentExamEvaluation(Integer.valueOf(examSubmission.getExamId()), Integer.valueOf(examsubmittor.getStudentId())) != null) {
//
//                roExamSubmittor.getRoStudentExamEvaluations().addAll();
//            }


            roExamSubmission.getRoExamSubmittors().add(roExamSubmittor);
        }
        return roExamSubmission;
    }


    /**
     * get the question data of exam questions.
     *
     * @param question
     * @param authorHelper
     * @param roExam
     * @return
     */
    public ROQuestions getROQuestion(Questions question, AuthorHelper authorHelper, ROExam roExam) {

        ROQuestions roQuestion = new ROQuestions();

        roQuestion.setQuestionId(Integer.valueOf(question.getQuestionId()));
        ROUser roUser = new ROUser();
        roUser.setUserId(Integer.valueOf(question.getQuestionCreatorId()));
        roUser.setFullName(question.getQuestionCreatorName());
        roQuestion.setQuestionCreator(roUser);

        roQuestion.setRoExam(roExam);

        roQuestion.setQuestionFormat(question.getQuestionFormat());
        roQuestion.setQuestionHint(question.getQuestionHint());
        roQuestion.setQuestionText(question.getQuestionText());
        roQuestion.setAssetsLink(question.getQuestionAssetsLink());
        roQuestion.setQuestionImageLink(question.getQuestionImageLink());
        roQuestion.setQuestionScore(question.getQuestionScore());
        roQuestion.setEvaluationNotes(question.getEvaluationNotes());
        roQuestion.setSolution(question.getSolution());
        roQuestion.setRoClassroom(authorHelper.getExamClassroom(Integer.valueOf(question.getClassroomId())));

        if (question.getBookId() != null) {
            roQuestion.setRoBook(authorHelper.getBook(Integer.valueOf(question.getBookId())));
        }


        for (Answers answer : question.getAnswers()) {
            ROAnswerChoices roAnswerChoices = new ROAnswerChoices();
            roAnswerChoices.setAnswerChoicesId(Integer.valueOf(answer.getId()));
            roAnswerChoices.setQuestionId(Integer.valueOf(answer.getQuestionId()));
            roAnswerChoices.setChoiceText(answer.getChoiceText());
            roAnswerChoices.setIsRight(answer.getIsRight().equals("1") ? true : false);
            roAnswerChoices.setImageLink(answer.getImageLink());
            roAnswerChoices.setAudioLink(answer.getAudioLink());
            roAnswerChoices.setVideoLink(answer.getVideoLink());
            roAnswerChoices.setRoQuestions(roQuestion);

            roQuestion.getRoAnswerChoices().add(roAnswerChoices);
        }


        for (Tags tag : question.getTags()) {
            ROTags roTag = new ROTags();
            roTag.setTagId(Integer.valueOf(tag.getTagId()));
            roTag.setTagName(tag.getTagName());

            roQuestion.getRoTags().add(roTag);
        }


        return roQuestion;

    }

    /**
     * get the exam questions.
     *
     * @param examQuestions
     * @param authorHelper
     * @return
     */

    public ROExamQuestions getROExamQuestions(ExamQuestions examQuestions, AuthorHelper authorHelper) {

        ROExamQuestions roExamQuestions = new ROExamQuestions();

        roExamQuestions.setExamId(Integer.valueOf(examQuestions.getId()));
        roExamQuestions.setRoUser(getROUser());

        ROExam roExam = authorHelper.getExam(Integer.valueOf(examQuestions.getId()));
        roExamQuestions.setRoExam(roExam);

        for (Questions question : examQuestions.getQuestions()) {
            roExamQuestions.getRoQuestions().add(getROQuestion(question, authorHelper, roExam));
        }
        return roExamQuestions;

    }


    /**
     * get Question palette data for exam evaluation.
     *
     * @param questionPalette
     * @return
     */
    public ROQuestionPalette getROQuestionPalette(QuestionPalette questionPalette, ROStudentExamEvaluation roStudentExamEvaluation) {

        ROQuestionPalette roQuestionPalette = new ROQuestionPalette();
        roQuestionPalette.setEvaluationId(roStudentExamEvaluation.getEvaluationId());
        roQuestionPalette.setValue(questionPalette.getValue());
        roQuestionPalette.setRoStudentExamEvaluation(roStudentExamEvaluation);
        return roQuestionPalette;

    }


    /**
     * get Evaluation for
     *
     * @param evaluation
     * @return
     */
    public ROEvaluation getROEvaluation(Evaluation evaluation, ROStudentExamEvaluation roStudentExamEvaluation) {

        ROEvaluation roEvaluation = new ROEvaluation();

        roEvaluation.setEvaluationId(roStudentExamEvaluation.getEvaluationId());
        roEvaluation.setStudentResposne(evaluation.getStudentResponse());
        roEvaluation.setEvaluationScore(evaluation.getEvaluationScore());
        roEvaluation.setIsRight(evaluation.getIsRight().equals("1") ? true : false);
        roEvaluation.setAnswerStatus(evaluation.getAnswerStatus());
        roEvaluation.setQuestionScore(evaluation.getQuestionScore());
        roEvaluation.setRoStudentExamEvaluation(roStudentExamEvaluation);

        return roEvaluation;
    }


    public ROStudentExamEvaluation getROStudentExamEvaluation(ExamEvaluation examEvaluation, int studentId, AuthorHelper authorHelper) {

        ROStudentExamEvaluation roStudentExamEvaluation = new ROStudentExamEvaluation();

        roStudentExamEvaluation.setEvaluationId(authorHelper.getTotalRecordsInTable(ROStudentExamEvaluation.class) + 1);
        roStudentExamEvaluation.setExamScore(examEvaluation.getExamScore());

        for (QuestionPalette questionPalette : examEvaluation.getQuestionPalette()) {
            roStudentExamEvaluation.getRoQuestionPalette().add(getROQuestionPalette(questionPalette, roStudentExamEvaluation));
        }

        for (Evaluation evaluation : examEvaluation.getEvaluation()) {
            roStudentExamEvaluation.getRoEvaluation().add(getROEvaluation(evaluation, roStudentExamEvaluation));
        }

        ROExam roExam = authorHelper.getExam(Integer.valueOf(examEvaluation.getExamId()));
        roStudentExamEvaluation.setRoExam(roExam);

        ROExamSubmittor roExamSubmittor = authorHelper.getExamSubmittor(studentId);
        roStudentExamEvaluation.setRoExamSubmittor(roExamSubmittor);

        return roStudentExamEvaluation;

    }

}
