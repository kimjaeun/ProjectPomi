package kr.hs.emirim.pomi.pomi;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListMainActivity  extends ListActivity  {

	static final String[] title = new String[] {"운동/건강", "대인관계/감정제어", "재미"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_main, title));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(ListMainActivity.this, ListViewActivity.class);
				intent.putExtra("title", ((TextView)view).getText());
				startActivity(intent);
			}
		}); //setListAdapter
		
	}
}