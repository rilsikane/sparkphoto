package com.application.sparkapp.util;

import android.app.Application;

public class GlobalState extends Application{

	public int resendTime =0;

	public int getResendTime() {
		return resendTime;
	}

	public void setResendTime(int resendTime) {
		this.resendTime = resendTime;
	}
	
	
}
