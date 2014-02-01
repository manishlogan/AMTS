package com.logan.amtsinfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import com.evolvingvision.amtsinfo.R;
import com.startapp.android.publish.StartAppAd;

public class MainActivity extends Activity {

	private MainActivity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, "111147141", "211077704");
		setContentView(R.layout.activity_main);
		activity = this;
		
		Thread t = new Thread(){
			@Override
			public void run() {
				AssetManager manager = activity.getAssets();
				try {
					InputStream is = manager.open("busStops.txt");
					ObjectInputStream ois = new ObjectInputStream(is);
					ApplicationUtility.data = (Map<String, ArrayList<String>>)ois.readObject();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	public void searchByBusNumber(View view){
		Intent nextActivity = new Intent(this, SearchByNumber.class);
		startActivity(nextActivity);
	}
	
	public void searchBySrcDest(View view){
		Intent nextActivity = new Intent(this, SearchBySourceAndDestinationActivity.class);
		startActivity(nextActivity);
	}
	
	public void searchByLocation(View view){
		Intent nextActivity = new Intent(this, SearchByLocationActivity.class);
		startActivity(nextActivity);
	}
	
	public void feedback(View view){
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"manishjain.forever@gmail.com"}); // recipients
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "AMTS Info Feedback");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter your feedback below:");
		startActivity(emailIntent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
}
