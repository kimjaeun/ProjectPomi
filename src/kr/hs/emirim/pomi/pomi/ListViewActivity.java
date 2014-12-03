package kr.hs.emirim.pomi.pomi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewActivity extends Activity{
	protected static final String TAG = ListViewActivity.class.getSimpleName();
	protected HashMap<String, Boolean> mSelectedMap = new HashMap<String, Boolean>();
	protected String dataFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/country.dat";

	MyCustomAdapter dataAdapter = null;
	String itext="";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		Intent intent = getIntent();
		itext = intent.getStringExtra("title");
		//Toast.makeText(getApplicationContext(), itext, Toast.LENGTH_LONG).show();
		TextView tx1 = (TextView) findViewById(R.id.text01);
		tx1.setText(itext);

		loadFromFile();

		displayListView();
		checkButtonClick();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		saveToFile();
	}

	private void displayListView() {

		//운동리스트 내용 입력
		ArrayList<Country> ExerList = new ArrayList<Country>();
		Country exer = new Country("줄넘기 100회 실시!",false);
		ExerList.add(exer);
		exer = new Country("목 돌리는 스트레칭을 해.",false);
		ExerList.add(exer);
		exer = new Country("뻐근한 어깨를 위해 스트레칭 해줘.",false);
		ExerList.add(exer);
		exer = new Country("두 팔을 위로 쭉 펴고 스트레칭 해줘.",false);
		ExerList.add(exer);
		exer = new Country("윗몸일으키기 30회 실시!",false);
		ExerList.add(exer);
		exer = new Country("피곤한 눈 감았다 뜨기 5회 실시!",false);
		ExerList.add(exer);
		exer = new Country("밖에 나가서 20분 동안 걷고 와.",false);
		ExerList.add(exer);
		exer = new Country("움츠린 어깨 곧게 펴줘.",false);
		ExerList.add(exer);
		exer = new Country("관자놀이 마사지도 해줘.",false);
		ExerList.add(exer);
		exer = new Country("물 한 컵 마시고 와.",false);
		ExerList.add(exer);
		exer = new Country("손목 스트레칭 해줘.",false);
		ExerList.add(exer);
		exer = new Country("팔굽혀펴기 10회 실시!",false);
		ExerList.add(exer);
		exer = new Country("최소 20m 이상 떨어진 곳을 응시해.",false);
		ExerList.add(exer);
		exer = new Country("손 깨끗하게 씻어.",false);
		ExerList.add(exer);
		exer = new Country("휴대전화를 내려놓지 못할까!",false);
		ExerList.add(exer);
		exer = new Country("다리를 쭉 펴고 흔드는 동작 반복해!",false);
		ExerList.add(exer);
		exer = new Country("명상을 하며 생각을 정리해.",false);
		ExerList.add(exer);
		exer = new Country("어지러운 방을 청소해줘.",false);
		ExerList.add(exer);
		exer = new Country("닫힌 창문을 열어 환기해줘.",false);
		ExerList.add(exer);
		exer = new Country("양치 구석구석 3분간 실시!",false);
		ExerList.add(exer);

		//재미리스트 내용 입력
		ArrayList<Country> FunList = new ArrayList<Country>();
		Country fun = new Country("주변 친구에게 '형님'하고 인사하기",false);
		FunList.add(fun);
		fun = new Country("코끼리코 10바퀴 돌기",false);
		FunList.add(fun);
		fun = new Country("친구 머리에 노크하고 '똑똑 계세요?'하기",false);
		FunList.add(fun);
		fun = new Country("거울보고 가위바위보해서 이기기",false);
		FunList.add(fun);
		fun = new Country("옆에 있는 친구를 세게 안아줘.",false);
		FunList.add(fun);
		fun = new Country("특정인물을 향해 “사랑합니다.”를 세 번 외쳐.",false);
		FunList.add(fun);
		fun = new Country("옆에 있는 사람과 포테토칩 게임을 해.",false);
		FunList.add(fun);
		fun = new Country("옆에 있는 사람과 가위바위보 하고 꿀밤을 때려.",false);
		FunList.add(fun);
		fun = new Country("동생이나 후배에게 존댓말을 써.",false);
		FunList.add(fun);
		fun = new Country("친구와 함께 아이스크림 내기를 해.",false);
		FunList.add(fun);
		fun = new Country("초콜릿과 콜라를 함께 먹어봐.",false);
		FunList.add(fun);
		fun = new Country("큰 소리로 가사 아는 노래를 불러봐.",false);
		FunList.add(fun);
		fun = new Country("친구와 댄스 배틀을 벌여 승자를 가려봐.",false);
		FunList.add(fun);
		fun = new Country("빙고 게임해서 이긴 사람이 간식을 사줘.",false);
		FunList.add(fun);
		fun = new Country("묵찌빠 후 진 사람이 소원을 들어줘.",false);
		FunList.add(fun);
		fun = new Country("친구와 끝말잇기로 간식 내기를 해.",false);
		FunList.add(fun);
		fun = new Country("자신의 SNS 중 한 곳에 이 앱을 소개해줘.",false);
		FunList.add(fun);
		
		//재미리스트 내용 입력
		ArrayList<Country> RealList = new ArrayList<Country>();
		Country real = new Country("친구 한명에게 사랑한다고 말해봐.",false);
		RealList.add(real);
		real = new Country("부모님께 하트를 붙인 문자를 보내드려.",false);
		RealList.add(real);
		real = new Country("어색한 친구에게 가서 웃으며 말 걸어봐.",false);
		RealList.add(real);
		real = new Country("감사한 선생님께 손 편지를 써봐.",false);
		RealList.add(real);
		real = new Country("가족에게 달달한 간식을 선물해봐.",false);
		RealList.add(real);
		real = new Country("할아버지 할머니께 전화를 해드려.",false);
		RealList.add(real);
		real = new Country("하루에 ‘고마워.’를 10번 이상 말해봐.",false);
		RealList.add(real);
		real = new Country("먹고 싶은 음식 한 가지를 먹어.",false);
		RealList.add(real);
		real = new Country("보고 싶은 사람에게 메시지를 보내봐.",false);
		RealList.add(real);
		real = new Country("연인에게 존댓말을 써봐.",false);
		RealList.add(real);
		real = new Country("친구의 장점을 칭찬해줘.",false);
		RealList.add(real);
		real = new Country("큰소리로 2분간 웃어보자.",false);
		RealList.add(real);
		real = new Country("걱정되는 일에 대해 진지하게 생각해봐.",false);
		RealList.add(real);
		real = new Country("연락이 뜸한 지인에게 전화를 해봐.",false);
		RealList.add(real);
		real = new Country("내 스스로를 마음껏 칭찬해봐.",false);
		RealList.add(real);
		real = new Country("평소에 좋아하는 노래를 들어봐.",false);
		RealList.add(real);
		

		ArrayList<Country> adaterlist;
		
		//문자형 배열로부터 ArrayAdaptar 내용받음
		if(itext.equals("운동/건강")) {
			adaterlist = ExerList;
		}
		else if(itext.equals("재미")){
			adaterlist = FunList;
		}
		else if(itext.equals("대인관계/감정제어")){
			adaterlist = RealList;
		}
		else{
			adaterlist = new ArrayList<Country>();
			Toast.makeText(getApplicationContext(), itext, Toast.LENGTH_LONG).show();
		}

		dataAdapter = new MyCustomAdapter(this, R.layout.row, adaterlist);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		// ListView에 adapter넣기
		listView.setAdapter(dataAdapter);


		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//클릭하면, 토스트를 보여줌
				Country country = (Country) parent.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),
						"선택한 것 : " + country.getName(), 
						Toast.LENGTH_LONG).show();
			}
		});
	}

	protected void saveToFile() {
		File file = new File(dataFilePath);
		FileOutputStream f = null;
		ObjectOutputStream s = null;

		try {
			f = new FileOutputStream(file);
			s = new ObjectOutputStream(f);
			s.writeObject(mSelectedMap);
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void loadFromFile() {
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

	private class MyCustomAdapter extends ArrayAdapter<Country> {

		private ArrayList<Country> countryList;

		public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Country> countryList) {
			super(context, textViewResourceId, countryList);
			this.countryList = new ArrayList<Country>();
			this.countryList.addAll(countryList);
		}

		private class ViewHolder {
			CheckBox name;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			Country country = countryList.get(position);

			Log.e(TAG, "[" + position + "]" + country.getName() + ", " + country.isSelected() + ", " + mSelectedMap.get(country.getName()));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.row, null);

				holder = new ViewHolder();
				holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			boolean isSelected = false;
			if (mSelectedMap.containsKey(country.getName())) {
				isSelected = mSelectedMap.get(country.getName());
			} else {
				isSelected = country.isSelected();
			}
			country.setSelected(isSelected);
			mSelectedMap.put(country.getName(), isSelected);
			convertView.setTag(holder);

			holder.name.setOnClickListener( new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox)v;
					Country country = (Country) cb.getTag();
					/*Toast.makeText(getApplicationContext(),
							"선택 여부 : " + cb.getText() +
							" 은/는 " + cb.isChecked(),
							Toast.LENGTH_LONG).show();*/
					country.setSelected(cb.isChecked());
					mSelectedMap.put(country.getName(), cb.isChecked());
					saveToFile();

					Log.e(TAG, "[" + position + "]" + country.getName() + ", " + country.isSelected() + ", " + mSelectedMap.get(country.getName()) + ", " + cb.isChecked());
				}
			});

			holder.name.setText(country.getName());
			holder.name.setChecked(country.isSelected());
			holder.name.setTag(country);

			return convertView;

		}
	}//MyCustomAdapter -> list 형식

	private void checkButtonClick() { //선택확인

		final Button myButton = (Button) findViewById(R.id.findSelected);
		myButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

//				Intent intent = null;
//				intent = new Intent(ListViewActivity.this, ListMainActivity.class);
//				startActivity(intent);
			}//onClick
		});
	}
}