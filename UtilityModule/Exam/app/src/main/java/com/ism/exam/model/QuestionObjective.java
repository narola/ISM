package com.ism.exam.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionObjective {

	private static final String TAG = QuestionObjective.class.getSimpleName();

	private String strQuestionId;
	private String strQuestion;
	private ArrayList<Option> arrListOptions;
	private boolean isSkipped;
	private boolean isReviewLater;
	private boolean isAnswered;
	private boolean isCorrect;

	public QuestionObjective() {
	}

	public QuestionObjective(String strQuestionId, String strQuestion, ArrayList<Option> arrListOptions) {
		this.strQuestionId = strQuestionId;
		this.strQuestion = strQuestion;
		this.arrListOptions = arrListOptions;
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

	public ArrayList<Option> getOptions() {
		return arrListOptions;
	}

	public void setOptions(ArrayList<Option> arrListOptions) {
		this.arrListOptions = arrListOptions;
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

	public static ArrayList<QuestionObjective> getQuestions() {
		try {
			ArrayList<QuestionObjective> questionObjectives = new ArrayList<QuestionObjective>();

			for (int i = 0; i < 5; i++) {
				ArrayList<Option> options = new ArrayList<Option>();
				for (int j = 0; j < 4; j++) {
					options.add(new Option("O" + j, "Option text " + j, j == 3 ? true : false, false));
				}
				questionObjectives.add(new QuestionObjective("Q" + i, "Question text " + i, options));
			}

			for (int i = 5; i < 10; i++) {
				ArrayList<Option> options = new ArrayList<Option>();
				for (int j = 0; j < 6; j++) {
					options.add(new Option("O" + j, "Option text " + j, j == 2 ? true : false, false));
				}
				questionObjectives.add(new QuestionObjective("Q" + i, "Question text " + i, options));
			}

			for (int i = 10; i < 15; i++) {
				ArrayList<Option> options = new ArrayList<Option>();
				for (int j = 0; j < 4; j++) {
					options.add(new Option("O" + j, "Option text " + j, j == 3 ? true : false, false));
				}
				questionObjectives.add(new QuestionObjective("Q" + i, "Question text " + i, options));
			}

			for (int i = 15; i < 20; i++) {
				ArrayList<Option> options = new ArrayList<Option>();
				for (int j = 0; j < 6; j++) {
					options.add(new Option("O" + j, "Option text " + j, j == 2 ? true : false, false));
				}
				questionObjectives.add(new QuestionObjective("Q" + i, "Question text " + i, options));
			}
			return questionObjectives;
		} catch (Exception e) {
			Log.e(TAG, "getQuestions Exception : " + e.toString());
			return null;
		}
	}

}
