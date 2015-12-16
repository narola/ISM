package com.ism.model;

/**
 * Created by c161 on 14/10/15.
 */
public class OptionTest {

	private static final String TAG = OptionTest.class.getSimpleName();

	private String strOptionId;
	private String strOption;
	private boolean isAnswer;
	private boolean isSelected;

	public OptionTest() {
	}

	public OptionTest(String strOptionId, String strOption, boolean isAnswer, boolean isSelected) {
		this.strOptionId = strOptionId;
		this.strOption = strOption;
		this.isAnswer = isAnswer;
		this.isSelected = isSelected;
	}

	public String getOptionId() {
		return strOptionId;
	}

	public void setOptionId(String strOptionId) {
		this.strOptionId = strOptionId;
	}

	public String getOption() {
		return strOption;
	}

	public void setOption(String strOption) {
		this.strOption = strOption;
	}

	public boolean isAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(boolean isAnswer) {
		this.isAnswer = isAnswer;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
