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

	// ��۹�ư
	ToggleButton toggle;
	boolean isToggled = true;

	// Ÿ����Ŀ�� ���� ������
	int selectTv = 0;// ���۽ð��������� ���۽ð��� tv�� ���ð��� ������ ���ð��� tv�� ������ �� �ְ� ��
	private int myHour, myMinute;
	// ���������� �ѱ���
	int startHour, startMinute, endHour, endMinute, repeatMinute = 30;
	// ȭ�� ǥ�ÿ�
	String startTime = "���� 00 : 00";
	String endTime = "���� 00 : 00";

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
		startTime = pref.getString("startTime", "���� 00 : 00");
		endTime = pref.getString("endTime", "���� 00 : 00");
		isToggled = pref.getBoolean("isToggled", true);

		tv_startTime.setText(startTime);// ���۽ð��� ���ڸ� �ٲ�
		tv_endTime.setText(endTime);// ����ð��� ���ڸ� �ٲ�

		// ����ʱ�ȭ
		toggle.setChecked(isToggled);

		// ��ۿ���
		toggle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (toggle.isChecked()) {
					isToggled = true;
				} else {
					isToggled = false;
					Toast.makeText(getApplicationContext(), "�˶��� ����� �ʽ��ϴ�.",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	// Ŭ��������
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
					myTimeSetListener,// ���̾�α׸� ����
					myHour, myMinute, false);
			dlgTime.show();// ������
			/*Toast.makeText(this, startTime + "\n" + endTime, Toast.LENGTH_LONG)
					.show();*/
		}
		if (v.getId() == R.id.btn_list) {
			// �˶���� �������� ���� ����Ʈ
			Toast.makeText(this, "����Ʈ ����", Toast.LENGTH_LONG).show();
			intent = new Intent(SettingActivity.this, ListMainActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_dev_info) {
			intent = new Intent(this, DeveloperActivity.class);
			startActivity(intent);
		}
	}

	// Ÿ����Ŀ ���̾�α�
	private TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String ampm = null;
			String nowHour = "00";
			String nowMinute = "00";

			// if hourOfDay �ð�ǥ�� ������ ���� if��
			if (hourOfDay >= 12) {// �����϶�
				ampm = "���� ";
				if (hourOfDay != 12) {// 12�ð� �ƴҶ�
					if (hourOfDay - 12 < 10) {
						nowHour = "0" + String.valueOf(hourOfDay - 12);
					} else {
						nowHour = String.valueOf(hourOfDay - 12);
					}
				} else {
					nowHour = String.valueOf(hourOfDay);
				}
			} else if (hourOfDay < 12) {// �����϶�
				ampm = "���� ";
				if (hourOfDay == 0) {
					nowHour = "12";
				} else if (hourOfDay < 10) {
					nowHour = "0" + String.valueOf(hourOfDay);
				} else {
					nowHour = String.valueOf(hourOfDay);
				}
			}// if hourOfDay �ð�ǥ�� ������ ���� if��

			// if minute �� ǥ�� ������ ���� if��
			if (minute < 10) {
				nowMinute = "0" + String.valueOf(minute);
			} else {
				nowMinute = String.valueOf(minute);
			}// if minute �� ǥ�� ������ ���� if��

			String time = ampm + nowHour + " : " + nowMinute;
			if (selectTv == 1) {// ���۽ð� ���ý�
				tv_startTime.setText(time);// ���۽ð��� ���ڸ� �ٲ�
				startHour = hourOfDay;
				startMinute = minute;
				startTime = time;
			} else if (selectTv == 2) {// ����ð� ���ý�
				tv_endTime.setText(time);// ����ð��� ���ڸ� �ٲ�
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
		// ���
		editor.putBoolean("isToggled", isToggled);
		editor.commit();
	}

	// ////////////////////////save��ư ��������
	public void clickBtnSave(View target) {
		Toast toast = Toast.makeText(this, "30�� �������� �˶��� �︳�ϴ�.",
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