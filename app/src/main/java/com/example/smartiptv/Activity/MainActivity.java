package com.example.smartiptv.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smartiptv.R;

public class MainActivity extends AppCompatActivity {
CardView account,livetv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar myActionBar = getSupportActionBar();
		myActionBar.hide();
		getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backk));
		account=(CardView)findViewById(R.id.account);
		livetv=(CardView)findViewById(R.id.livetvcard);

		account.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent a=new Intent(MainActivity.this, Signup.class);
				startActivity(a);
			}
		});
		livetv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent a=new Intent(MainActivity.this, LiveTvActivity.class);
				startActivity(a);
			}
		});
	}
}

