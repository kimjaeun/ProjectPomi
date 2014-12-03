package kr.hs.emirim.pomi.pomi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HelpActivity extends Activity implements OnClickListener {

	LinearLayout background;
	ImageView btnLeft, btnRight;
	int images[] = {
			R.drawable.help_01,
			R.drawable.help_02,
			R.drawable.help_03,
			R.drawable.help_04
			
	};
	int count = 40;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		background = (LinearLayout) findViewById(R.id.background);
		btnLeft = (ImageView) findViewById(R.id.help_left);
		btnRight = (ImageView) findViewById(R.id.help_right);

		
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.help_left) count--;
		if(v.getId() == R.id.help_right) count++;
		
		background.setBackgroundResource(images[count%4]);
	}
}