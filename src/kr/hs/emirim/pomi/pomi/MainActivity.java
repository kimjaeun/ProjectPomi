package kr.hs.emirim.pomi.pomi;

import kr.hs.emirim.pomi.pomi.BackPressCloseHandler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	// 뒤로가기 버튼 클릭시 토스트 문구
	private BackPressCloseHandler backPressCloseHandler;
	
	

	// 콜렉션용
	boolean isCollect[] = new boolean[6];
	boolean first = true;
	// 레벨게이지바
	int imgLevel[] = { R.drawable.main_level_01, R.drawable.main_level_02,
			R.drawable.main_level_03, R.drawable.main_level_04,
			R.drawable.main_level_05, R.drawable.main_level_06,
			R.drawable.main_level_07, R.drawable.main_level_08,
			R.drawable.main_level_09, R.drawable.main_level_10,
			R.drawable.main_level_11 };
	// 단계별 콩
	int imgPot[] = { R.drawable.pot_01, R.drawable.pot_02, R.drawable.pot_03,
			R.drawable.pot_04, R.drawable.pot_05, R.drawable.pot_06, };
	ImageView level, btnCollection, btnSetting, pot, btnHelp;
	int xpLevel = 0;
	int potLevel = 0;
	int completeCong = 0;
	public static MainActivity life=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		life=this;
		// 스플래시
		startActivity(new Intent(this, SplashActivity.class));
		
		backPressCloseHandler = new BackPressCloseHandler(this);

		level = (ImageView) findViewById(R.id.main_level);
		btnCollection = (ImageView) findViewById(R.id.main_btn_collect);
		btnSetting = (ImageView) findViewById(R.id.main_btn_setting);
		btnHelp = (ImageView)findViewById(R.id.main_btn_help);
		pot = (ImageView) findViewById(R.id.main_pot);

		btnCollection.setOnClickListener(this);
		btnSetting.setOnClickListener(this);
		btnHelp.setOnClickListener(this);
		pot.setOnClickListener(this);
		
		setGage();
		
	}
	
	public void setGage(){
		// 이전에 저장된 데이터 가져오기
		SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
		potLevel = pref.getInt("potLevel", 0);
		completeCong = pref.getInt("completeCong", 0);
		xpLevel = pref.getInt("xpLevel", 0);
		for (int i = 0; i < completeCong; i++) {
			isCollect[i] = true;
		}

		// 현재 경험치 및 레벨에따른 이미지 및 데이터 설정
		if (potLevel >= 5 && xpLevel >= 11) {
			potLevel = 0;
			pot.setImageResource(imgPot[potLevel]);
			xpLevel = xpLevel % 11;
			level.setImageResource(imgLevel[0]);
			if(completeCong<6) completeCong++;
			for (int i = 0; i < completeCong; i++) {
				isCollect[i] = true;
			}
			return;
		}// end if potlevel
		else {
			if (xpLevel >= 11) {
				potLevel++;
				pot.setImageResource(imgPot[potLevel]);
				xpLevel = 0;
				level.setImageResource(imgLevel[0]);
			} else {
				level.setImageResource(imgLevel[xpLevel]);
			}
		}// end else potlevel		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		if (v.getId() == R.id.main_btn_collect) {
			intent = new Intent(this, CollectionActivity.class);
			intent.putExtra("isCollect", isCollect);
			startActivity(intent);
		}// end if click btn collection
		else if (v.getId() == R.id.main_btn_setting) {
			intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
		}// end if click btn setting
		else if (v.getId() == R.id.main_pot) {
			Toast.makeText(this, "단계 : " + (potLevel + 1), Toast.LENGTH_LONG)
					.show();
		}
		else if(v.getId() == R.id.main_btn_help){
			intent = new Intent(this, HelpActivity.class);
			startActivity(intent);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		if(first){
			editor.putInt("xpLevel", xpLevel); // 키값, 저장값
			first = false;
		}
				
		editor.putInt("potLevel", potLevel);
		editor.putInt("completeCong", completeCong);
		editor.commit();
	}
	
	
	
	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		life=null;
	}
}
