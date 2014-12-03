package kr.hs.emirim.pomi.pomi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class CollectionActivity extends Activity   {

	ImageView collcetion[] = new ImageView[6];
	boolean isCollect[] = new boolean[6];
	int imageResCollect[] = { R.drawable.collect_cong_01,
			R.drawable.collect_cong_02, R.drawable.collect_cong_03,
			R.drawable.collect_cong_04, R.drawable.collect_cong_05,
			R.drawable.collect_cong_06 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);

		String packName = this.getPackageName(); // 패키지명
		for (int i = 0; i < collcetion.length; i++) {
			String viewName = "collect_0" + (i + 1);
			int resID = getResources().getIdentifier(viewName, "id", packName);
			collcetion[i] = (ImageView) findViewById(resID);
		}// end for i

		isCollect = getIntent().getExtras().getBooleanArray("isCollect");

		for (int i = 0; i < isCollect.length; i++) {
			if (isCollect[i])
				collcetion[i].setImageResource(imageResCollect[i]);
		}
	}
}