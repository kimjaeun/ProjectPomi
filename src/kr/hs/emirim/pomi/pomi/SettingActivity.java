package kr.hs.emirim.pomi.pomi;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity implements OnClickListener {
	private static final String KEY_FORME_PREFERENCE = "forme_preference";

	TextView tv_startTime, tv_endTime, tv_version;
	EditText edit_repeatTime;
	ImageView btn_alram, btn_develope;

	// 토글버튼
	ToggleButton toggle;
	boolean isToggled = true;

	// 타임피커를 위한 변수들
	int selectTv = 0;// 시작시간을누르면 시작시간의 tv를 끝시간을 누르면 끝시간의 tv를 선택할 수 있게 함
	private int myHour, myMinute;
	// 예빈이한테 넘기기용
	int startHour, startMinute, endHour, endMinute, repeatMinute = 30;
	// 화면 표시용
	String startTime = "오전 00 : 00";
	String endTime = "오전 00 : 00";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		tv_startTime = (TextView) findViewById(R.id.textView1);
		tv_endTime = (TextView) findViewById(R.id.textView2);
		tv_version = (TextView) findViewById(R.id.textView3);
		edit_repeatTime = (EditText) findViewById(R.id.editRepeatTime);
		btn_alram = (ImageView) findViewById(R.id.btn_list);
		btn_develope = (ImageView) findViewById(R.id.btn_dev_info);

		toggle = (ToggleButton) findViewById(R.id.toggle);

		tv_startTime.setOnClickListener(this);
		tv_endTime.setOnClickListener(this);
		btn_alram.setOnClickListener(this);
		btn_develope.setOnClickListener(this);

		SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
		startHour = pref.getInt("startHour", 0);
		startMinute = pref.getInt("startMinute", 0);
		endHour = pref.getInt("endHour", 0);
		endMinute = pref.getInt("endMinute", 0);
		startTime = pref.getString("startTime", "오전 00 : 00");
		endTime = pref.getString("endTime", "오전 00 : 00");
		isToggled = pref.getBoolean("isToggled", true);

		tv_startTime.setText(startTime);// 시작시간의 글자를 바꿈
		tv_endTime.setText(endTime);// 종료시간의 글자를 바꿈

		// 토글초기화
		toggle.setChecked(isToggled);

		// 토글여부
		toggle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (toggle.isChecked()) {
					isToggled = true;
				} else {
					isToggled = false;
					Toast.makeText(getApplicationContext(), "알람을 띄우지 않습니다.",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	// 클릭했을때
	@Override
	public void onClick(View v) {
		Intent intent = null;
		if (v.getId() == R.id.textView1 || v.getId() == R.id.textView2) {
			if (v.getId() == R.id.textView1)
				selectTv = 1;
			else if (v.getId() == R.id.textView2)
				selectTv = 2;
			Calendar c = Calendar.getInstance();
			myHour = c.get(Calendar.HOUR_OF_DAY);
			myMinute = c.get(Calendar.MINUTE);

			TimePickerDialog dlgTime = new TimePickerDialog(this,
					myTimeSetListener,// 다이얼로그를 생성
					myHour, myMinute, false);
			dlgTime.show();// 보여줌
			/*Toast.makeText(this, startTime + "\n" + endTime, Toast.LENGTH_LONG)
					.show();*/
		}
		if (v.getId() == R.id.btn_list) {
			// 알람목록 설정으로 가는 인텐트
			Toast.makeText(this, "리스트 선택", Toast.LENGTH_LONG).show();
			intent = new Intent(SettingActivity.this, ListMainActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_dev_info) {
			intent = new Intent(this, DeveloperActivity.class);
			startActivity(intent);
		}
	}

	// 타임피커 다이얼로그
	private TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String ampm = null;
			String nowHour = "00";
			String nowMinute = "00";

			// if hourOfDay 시간표시 조정을 위한 if문
			if (hourOfDay >= 12) {// 오후일때
				ampm = "오후 ";
				if (hourOfDay != 12) {// 12시가 아닐때
					if (hourOfDay - 12 < 10) {
						nowHour = "0" + String.valueOf(hourOfDay - 12);
					} else {
						nowHour = String.valueOf(hourOfDay - 12);
					}
				} else {
					nowHour = String.valueOf(hourOfDay);
				}
			} else if (hourOfDay < 12) {// 오전일때
				ampm = "오전 ";
				if (hourOfDay == 0) {
					nowHour = "12";
				} else if (hourOfDay < 10) {
					nowHour = "0" + String.valueOf(hourOfDay);
				} else {
					nowHour = String.valueOf(hourOfDay);
				}
			}// if hourOfDay 시간표시 조정을 위한 if문

			// if minute 분 표시 조정을 위한 if문
			if (minute < 10) {
				nowMinute = "0" + String.valueOf(minute);
			} else {
				nowMinute = String.valueOf(minute);
			}// if minute 분 표시 조정을 위한 if문

			String time = ampm + nowHour + " : " + nowMinute;
			if (selectTv == 1) {// 시작시간 선택시
				tv_startTime.setText(time);// 시작시간의 글자를 바꿈
				startHour = hourOfDay;
				startMinute = minute;
				startTime = time;
			} else if (selectTv == 2) {// 종료시간 선택시
				tv_endTime.setText(time);// 종료시간의 글자를 바꿈
				endHour = hourOfDay;
				endMinute = minute;
				endTime = time;
			}
		}
	};

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt("startHour", startHour);
		editor.putInt("startMinute", startMinute);
		editor.putInt("endHour", endHour);
		editor.putInt("endMinute", endMinute);
		editor.putString("startTime", startTime);
		editor.putString("endTime", endTime);
		// 토글
		editor.putBoolean("isToggled", isToggled);
		editor.commit();
	}

	// ////////////////////////save버튼 눌렸을때
	public void clickBtnSave(View target) {
		Toast toast = Toast.makeText(this, "30분 간격으로 알람이 울립니다.",
				Toast.LENGTH_LONG);

		String str_repeat = edit_repeatTime.getText().toString();
		if (!(str_repeat == null)) {
			try {
				repeatMinute = Integer.valueOf(edit_repeatTime.getText()
						.toString());
			} catch (Exception e) {
				repeatMinute = 30;
				toast.show();
			}
		}
		String preTime = startHour + ";" + startMinute + ";" + endHour + ";"
				+ endMinute + ";" + repeatMinute;

		SharedPreferences prefs = getSharedPreferences("PrefName_AlarmTime",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KEY_FORME_PREFERENCE, preTime);
		editor.commit();

		if (!isToggled)
			toast.cancel();

		Intent intent = new Intent(this, DialogActivity.class);
		intent.putExtra("isToggled", isToggled);
		startActivity(intent);
		finish();
	}
}