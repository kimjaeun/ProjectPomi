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

		//�����Ʈ ���� �Է�
		ArrayList<Country> ExerList = new ArrayList<Country>();
		Country exer = new Country("�ٳѱ� 100ȸ �ǽ�!",false);
		ExerList.add(exer);
		exer = new Country("�� ������ ��Ʈ��Ī�� ��.",false);
		ExerList.add(exer);
		exer = new Country("������ ����� ���� ��Ʈ��Ī ����.",false);
		ExerList.add(exer);
		exer = new Country("�� ���� ���� �� ��� ��Ʈ��Ī ����.",false);
		ExerList.add(exer);
		exer = new Country("��������Ű�� 30ȸ �ǽ�!",false);
		ExerList.add(exer);
		exer = new Country("�ǰ��� �� ���Ҵ� �߱� 5ȸ �ǽ�!",false);
		ExerList.add(exer);
		exer = new Country("�ۿ� ������ 20�� ���� �Ȱ� ��.",false);
		ExerList.add(exer);
		exer = new Country("������ ��� ��� ����.",false);
		ExerList.add(exer);
		exer = new Country("���ڳ��� �������� ����.",false);
		ExerList.add(exer);
		exer = new Country("�� �� �� ���ð� ��.",false);
		ExerList.add(exer);
		exer = new Country("�ո� ��Ʈ��Ī ����.",false);
		ExerList.add(exer);
		exer = new Country("�ȱ������ 10ȸ �ǽ�!",false);
		ExerList.add(exer);
		exer = new Country("�ּ� 20m �̻� ������ ���� ������.",false);
		ExerList.add(exer);
		exer = new Country("�� �����ϰ� �ľ�.",false);
		ExerList.add(exer);
		exer = new Country("�޴���ȭ�� �������� ���ұ�!",false);
		ExerList.add(exer);
		exer = new Country("�ٸ��� �� ��� ���� ���� �ݺ���!",false);
		ExerList.add(exer);
		exer = new Country("����� �ϸ� ������ ������.",false);
		ExerList.add(exer);
		exer = new Country("�������� ���� û������.",false);
		ExerList.add(exer);
		exer = new Country("���� â���� ���� ȯ������.",false);
		ExerList.add(exer);
		exer = new Country("��ġ �������� 3�а� �ǽ�!",false);
		ExerList.add(exer);

		//��̸���Ʈ ���� �Է�
		ArrayList<Country> FunList = new ArrayList<Country>();
		Country fun = new Country("�ֺ� ģ������ '����'�ϰ� �λ��ϱ�",false);
		FunList.add(fun);
		fun = new Country("�ڳ����� 10���� ����",false);
		FunList.add(fun);
		fun = new Country("ģ�� �Ӹ��� ��ũ�ϰ� '�ȶ� �輼��?'�ϱ�",false);
		FunList.add(fun);
		fun = new Country("�ſﺸ�� �����������ؼ� �̱��",false);
		FunList.add(fun);
		fun = new Country("���� �ִ� ģ���� ���� �Ⱦ���.",false);
		FunList.add(fun);
		fun = new Country("Ư���ι��� ���� ������մϴ�.���� �� �� ����.",false);
		FunList.add(fun);
		fun = new Country("���� �ִ� ����� ������Ĩ ������ ��.",false);
		FunList.add(fun);
		fun = new Country("���� �ִ� ����� ���������� �ϰ� �ܹ��� ����.",false);
		FunList.add(fun);
		fun = new Country("�����̳� �Ĺ迡�� ������ ��.",false);
		FunList.add(fun);
		fun = new Country("ģ���� �Բ� ���̽�ũ�� ���⸦ ��.",false);
		FunList.add(fun);
		fun = new Country("���ݸ��� �ݶ� �Բ� �Ծ��.",false);
		FunList.add(fun);
		fun = new Country("ū �Ҹ��� ���� �ƴ� �뷡�� �ҷ���.",false);
		FunList.add(fun);
		fun = new Country("ģ���� �� ��Ʋ�� ���� ���ڸ� ������.",false);
		FunList.add(fun);
		fun = new Country("���� �����ؼ� �̱� ����� ������ ����.",false);
		FunList.add(fun);
		fun = new Country("����� �� �� ����� �ҿ��� �����.",false);
		FunList.add(fun);
		fun = new Country("ģ���� �����ձ�� ���� ���⸦ ��.",false);
		FunList.add(fun);
		fun = new Country("�ڽ��� SNS �� �� ���� �� ���� �Ұ�����.",false);
		FunList.add(fun);
		
		//��̸���Ʈ ���� �Է�
		ArrayList<Country> RealList = new ArrayList<Country>();
		Country real = new Country("ģ�� �Ѹ��� ����Ѵٰ� ���غ�.",false);
		RealList.add(real);
		real = new Country("�θ�Բ� ��Ʈ�� ���� ���ڸ� �������.",false);
		RealList.add(real);
		real = new Country("����� ģ������ ���� ������ �� �ɾ��.",false);
		RealList.add(real);
		real = new Country("������ �����Բ� �� ������ ���.",false);
		RealList.add(real);
		real = new Country("�������� �޴��� ������ �����غ�.",false);
		RealList.add(real);
		real = new Country("�Ҿƹ��� �ҸӴϲ� ��ȭ�� �ص��.",false);
		RealList.add(real);
		real = new Country("�Ϸ翡 ������.���� 10�� �̻� ���غ�.",false);
		RealList.add(real);
		real = new Country("�԰� ���� ���� �� ������ �Ծ�.",false);
		RealList.add(real);
		real = new Country("���� ���� ������� �޽����� ������.",false);
		RealList.add(real);
		real = new Country("���ο��� ������ ���.",false);
		RealList.add(real);
		real = new Country("ģ���� ������ Ī������.",false);
		RealList.add(real);
		real = new Country("ū�Ҹ��� 2�а� �����.",false);
		RealList.add(real);
		real = new Country("�����Ǵ� �Ͽ� ���� �����ϰ� �����غ�.",false);
		RealList.add(real);
		real = new Country("������ ���� ���ο��� ��ȭ�� �غ�.",false);
		RealList.add(real);
		real = new Country("�� �����θ� ������ Ī���غ�.",false);
		RealList.add(real);
		real = new Country("��ҿ� �����ϴ� �뷡�� ����.",false);
		RealList.add(real);
		

		ArrayList<Country> adaterlist;
		
		//������ �迭�κ��� ArrayAdaptar �������
		if(itext.equals("�/�ǰ�")) {
			adaterlist = ExerList;
		}
		else if(itext.equals("���")){
			adaterlist = FunList;
		}
		else if(itext.equals("���ΰ���/��������")){
			adaterlist = RealList;
		}
		else{
			adaterlist = new ArrayList<Country>();
			Toast.makeText(getApplicationContext(), itext, Toast.LENGTH_LONG).show();
		}

		dataAdapter = new MyCustomAdapter(this, R.layout.row, adaterlist);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		// ListView�� adapter�ֱ�
		listView.setAdapter(dataAdapter);


		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Ŭ���ϸ�, �佺Ʈ�� ������
				Country country = (Country) parent.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),
						"������ �� : " + country.getName(), 
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
							"���� ���� : " + cb.getText() +
							" ��/�� " + cb.isChecked(),
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
	}//MyCustomAdapter -> list ����

	private void checkButtonClick() { //����Ȯ��

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