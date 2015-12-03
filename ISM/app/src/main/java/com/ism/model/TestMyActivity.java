package com.ism.model;

import com.ism.ws.model.User;

/**
 * Created by c161 on 26/11/15.
 */
public class TestMyActivity {

	private User user;
	private int activityType;
	private TestActivity1 testActivity1;
	private TestActivity2 testActivity2;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public TestActivity1 getTestActivity1() {
		return testActivity1;
	}

	public void setTestActivity1(TestActivity1 testActivity1) {
		this.testActivity1 = testActivity1;
	}

	public TestActivity2 getTestActivity2() {
		return testActivity2;
	}

	public void setTestActivity2(TestActivity2 testActivity2) {
		this.testActivity2 = testActivity2;
	}
}
