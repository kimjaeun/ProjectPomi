package kr.hs.emirim.pomi.pomi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import android.widget.Toast;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;

public class DialogActivity extends Activity {
	// 값 넘겨받기용
	private static final String KEY_FORME_PREFERENCE = "forme_preference";
	protected HashMap<String, Boolean> mSelectedMap = new HashMap<String, Boolean>();

	// 알람 미션
	ArrayList<String> missions = new ArrayList<String>();

	// 토글
	boolean isToggled;

	// 알람 반복용
	Intent intent;
	PendingIntent pending;
	AlarmManager am;
	long now;

	// TODO repeatMin과 repeatHour에 자은이의 설정시간 대입
	// 반복 타이밍
	long repeatMin = 0; // 분단위
	long repeatHour = 0; // 시단위
	long repeatTime;

	// 시작시간과 끝시간
	int startHour = 0;
	int startMin = 0;
	int endHour = 0;
	int endMin = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dailog);

		loadFromFile();
		int i = 0;

		// 토글 값 받아오기
		isToggled = getIntent().getExtras().getBoolean("isToggled", true);

		Iterator it = mSelectedMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Boolean> pairs = (Map.Entry<String, Boolean>) it
					.next();
			String name = pairs.getKey();
			Boolean value = pairs.getValue();

			if (value) {
				// missions[i] = name;
				// missions.addElement(name);
				// i++;
				missions.add(name);
			}
		}

		if (missions.size() < 1) {
			finish();
			return;
		}

		// 값 넘겨받기
		SharedPreferences prefs_totalmoney = getSharedPreferences(
				"PrefName_AlarmTime", MODE_PRIVATE);
		String times[] = prefs_totalmoney.getString(KEY_FORME_PREFERENCE, null)
				.split(";");

		startHour = Integer.valueOf(times[0]);
		startMin = Integer.valueOf(times[1]);
		endHour = Integer.valueOf(times[2]);
		endMin = Integer.valueOf(times[3]);
		repeatMin = Integer.valueOf(times[4]);
		repeatHour = repeatMin / 60l;
		repeatMin = repeatMin % 60l;

		repeatTime = repeatHour * 60 * 60 * 1000 + repeatMin * 60 * 1000;

		// 알람메니저 설정
		am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
		intent = new Intent(this, DialogActivity.class);
		pending = PendingIntent.getActivity(this, 0, intent, 0);
		now = System.currentTimeMillis();
		// ////, 지금 현재 시간 + 반복시간, 다시 띄울 시간, 살릴 인텐트
		am.setRepeating(AlarmManager.RTC_WAKEUP, now + repeatTime, repeatTime,
				pending);

		// 화면이 잠겨있고, 닫혀있을 때 화면 켜기
		// 잠든 단말을 깨워라.
		PushWakeLock.acquireCpuWakeLock(this);
		// WakeLock 해제.
		PushWakeLock.releaseCpuLock();

		// 랜덤미션
		int ran = (int) (Math.random() * (missions.size()));

		// FIXME
		// 토글이 on이면 실행, off면 캔슬
		if (isToggled)
			dialogMission((String) missions.get(ran)).show();
		else {
			am.cancel(pending);
			finish();
		}
	}

	protected void loadFromFile() {
		String dataFilePath = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
				+ "/country.dat";
		File file = new File(dataFilePath);
		try {
			FileInputStream f = new FileInputStream(file);
			ObjectInputStream s = new ObjectInputStream(f);
			mSelectedMap = (HashMap<String, Boolean>) s.readObject();
			s.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ///////뉴 다이얼로그
	public Dialog dialogMission(String result) {

		Calendar calNow = Calendar.getInstance();
		int myHour = calNow.get(Calendar.HOUR_OF_DAY);
		int myMinute = calNow.get(Calendar.MINUTE);

		// 끝시간이 되면 알람을 종료
		if (myHour >= endHour && myMinute + repeatMin >= endMin) {
			Toast.makeText(this, "시간종료", Toast.LENGTH_LONG).show();

			// 다이얼로그 리피트 종료
			am.cancel(pending);

			// 시작시간에 맞춰 다이얼로그 시작
			long time = now + ((24 - endHour) + startHour - 1) * 60 * 60 * 1000
					+ (((60 - endMin) + startMin) / 60) * 60 * 1000
					+ (((60 - endMin) + startMin) % 60) * 60 * 1000;
			am.set(AlarmManager.RTC, time, pending);
		}

		// builder는 다이얼로그
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("이번 미션")
				.setMessage(result + "\n수행하셨습니까?")
				.setCancelable(false)
				.setPositiveButton("예", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences pref = getSharedPreferences("main",
								MODE_PRIVATE);
						int xpLevel = pref.getInt("xpLevel", 0);
						xpLevel++;
						SharedPreferences.Editor editor = pref.edit();
						editor.putInt("xpLevel", xpLevel); // 키값, 저장값
						editor.commit();
						// 앱 종료
						finish();
						if(MainActivity.life!=null){
							MainActivity.life.setGage();
						}
					}

				})
				.setNegativeButton("아니오",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								SharedPreferences pref = getSharedPreferences("main",
										MODE_PRIVATE);
								int xpLevel = pref.getInt("xpLevel", 0);
								SharedPreferences.Editor editor = pref.edit();
								editor.putInt("xpLevel", xpLevel); // 키값, 저장값
								editor.commit();
								// 앱 종료
								finish();
							}
						});

		AlertDialog alert = builder.create();

		return alert; // 이 메소드를 부르면 .show()를 달아줘야 반환된 alert가 제대로 띄워짐
	} // 대화상자 생성

}
