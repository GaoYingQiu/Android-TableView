package com.lvj.bookoneday.activity.view;

import java.util.Calendar;

import android.R.integer;

public class SpecialCalendar {

	private int daysOfMonth = 0;
	private int dayOfWeek = 0;
	private int eachDayOfWeek = 0;
	
	

	public boolean isLeapYear(int year) {
		if (year % 100 == 0 && year % 400 == 0) {
			return true;
		} else if (year % 100 != 0 && year % 4 == 0) {
			return true;
		}
		return false;
	}

	public int getDaysOfMonth(boolean isLeapyear, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			daysOfMonth = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			daysOfMonth = 30;
			break;
		case 2:
			if (isLeapyear) {
				daysOfMonth = 29;
			} else {
				daysOfMonth = 28;
			}

		}
		return daysOfMonth;
	}

	public int getWeekdayOfMonth(int year, int month){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, 1);
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		return dayOfWeek;
	}
	public int getWeekDayOfLastMonth(int year,int month,int day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);
		eachDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		return eachDayOfWeek;
	}
	
}
