package com.karmick.stecs.common;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * This service run from three ways 1 : On Application start 2 : At 1 AM 3 :
 * Device boot
 * 
 * @author Rishikesh
 * 
 */
public class NetChecker extends Service {

	private Calendar alarmTime = Calendar.getInstance();
	private int notification_id = 1;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		

		stopSelf();

		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();

	}



	// Unused
	public void scheduleServicePeriodically(Context context) {
		// I want to restart this service again in one minute
		AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarm.set(alarm.RTC_WAKEUP, System.currentTimeMillis()
				+ (1000 * 1 * 60), PendingIntent.getService(context, 0,
				new Intent(context, NetChecker.class), 0));
	}



	protected Calendar getAlarmCalendarObject() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 01);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public Calendar getAlarmTime() {

		if (alarmTime.before(Calendar.getInstance()))
			alarmTime.add(Calendar.DAY_OF_MONTH, 1);

		return alarmTime;
	}

	public void setAlarmTime(Calendar alarmTime) {
		this.alarmTime = alarmTime;
	}

	interface NotificationForInsert {
		public void onNotificationForInsert();
	}
}