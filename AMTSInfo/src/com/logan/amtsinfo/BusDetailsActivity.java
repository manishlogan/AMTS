package com.logan.amtsinfo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.evolvingvision.amtsinfo.R;

public class BusDetailsActivity extends Activity {

	private String busNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_details);
		Intent intent = getIntent();
		busNo = intent.getStringExtra("busNo");
		
		List<String> stops = ApplicationUtility.data.get(busNo); 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stops);
		ListView listView = (ListView)findViewById(R.id.stopsListView);
		listView.setAdapter(adapter);
		
		this.setTitle("Bus Route: "+busNo);
	}
}
