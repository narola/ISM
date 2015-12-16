package com.ism.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionObjectiveTest {

	private static final String TAG = QuestionObjectiveTest.class.getSimpleName();

	private String strQuestionId;
	private String strQuestion;
	private ArrayList<OptionTest> arrListOptionTests;
	private boolean isSkipped;
	private boolean isReviewLater;
	private boolean isAnswered;
	private boolean isCorrect;

	public QuestionObjectiveTest() {
	}

	public QuestionObjectiveTest(String strQuestionId, String strQuestion, ArrayList<OptionTest> arrListOptionTests) {
		this.strQuestionId = strQuestionId;
		this.strQuestion = strQuestion;
		this.arrListOptionTests = arrListOptionTests;
	}

	public String getQuestionId() {
		return strQuestionId;
	}

	public void setQuestionId(String strQuestionId) {
		this.strQuestionId = strQuestionId;
	}

	public String getQuestion() {
		return strQuestion;
	}

	public void setQuestion(String strQuestion) {
		this.strQuestion = strQuestion;
	}

	public ArrayList<OptionTest> getOptions() {
		return arrListOptionTests;
	}

	public void setOptions(ArrayList<OptionTest> arrListOptionTests) {
		this.arrListOptionTests = arrListOptionTests;
	}

	public boolean isSkipped() {
		return isSkipped;
	}

	public void setIsSkipped(boolean isSkipped) {
		this.isSkipped = isSkipped;
	}

	public boolean isReviewLater() {
		return isReviewLater;
	}

	public void setIsReviewLater(boolean isReviewLater) {
		this.isReviewLater = isReviewLater;
	}

	public boolean isAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public static ArrayList<QuestionObjectiveTest> getQuestions() {
		try {
			ArrayList<QuestionObjectiveTest> questionObjectiveTests = new ArrayList<QuestionObjectiveTest>();

			for (int i = 0; i < 5; i++) {
				ArrayList<OptionTest> optionTests = new ArrayList<OptionTest>();
				for (int j = 0; j < 4; j++) {
					optionTests.add(new OptionTest("O" + j, "Option text " + j, j == 3 ? true : false, false));
				}
				questionObjectiveTests.add(new QuestionObjectiveTest("Q" + i, "Question text " + i, optionTests));
			}

			for (int i = 5; i < 10; i++) {
				ArrayList<OptionTest> optionTests = new ArrayList<OptionTest>();
				for (int j = 0; j < 6; j++) {
					optionTests.add(new OptionTest("O" + j, "Option text " + j, j == 2 ? true : false, false));
				}
				questionObjectiveTests.add(new QuestionObjectiveTest("Q" + i, "Question text " + i, optionTests));
			}

			for (int i = 10; i < 15; i++) {
				ArrayList<OptionTest> optionTests = new ArrayList<OptionTest>();
				for (int j = 0; j < 4; j++) {
					optionTests.add(new OptionTest("O" + j, "Option text " + j, j == 3 ? true : false, false));
				}
				questionObjectiveTests.add(new QuestionObjectiveTest("Q" + i, "Question text " + i, optionTests));
			}

			for (int i = 15; i < 20; i++) {
				ArrayList<OptionTest> optionTests = new ArrayList<OptionTest>();
				for (int j = 0; j < 6; j++) {
					optionTests.add(new OptionTest("O" + j, "Option text " + j, j == 2 ? true : false, false));
				}
				questionObjectiveTests.add(new QuestionObjectiveTest("Q" + i, "Question text " + i, optionTests));
			}
			return questionObjectiveTests;
		} catch (Exception e) {
			Log.e(TAG, "getQuestions Exception : " + e.toString());
			return null;
		}
	}

}
