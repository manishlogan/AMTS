package com.logan.amtsinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

public class SearchByNumber extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final SearchByNumber currentView = this;
		setContentView(R.layout.activity_search_by_number);
		List<String> busNos = new ArrayList<String>(ApplicationUtility.data.keySet());
		
		Collections.sort(busNos);
		
		ArrayAdapter<String> buses = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, busNos);
		 
		AutoCompleteTextView textView = (AutoCompleteTextView)
	                 findViewById(R.id.autoCompleteTextView1);
		
		textView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				String busNo = (String)adapterView.getItemAtPosition(position);
				List<String> stops = ApplicationUtility.data.get(busNo); 
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(currentView, android.R.layout.simple_list_item_1,stops);
				ListView listView = (ListView)findViewById(R.id.listView1);
				listView.setAdapter(adapter);
			}
		});
		
	         textView.setAdapter(buses);
	         textView.setThreshold(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_by_number, menu);
		return true;
	}
	
	public void searchByBusNumber(View view){
		AutoCompleteTextView text = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		String busNo = text.getText().toString();
		
		List<String> stops = ApplicationUtility.data.get(busNo); 
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stops);
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}
}
