package com.logan.amtsinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchByLocationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_location);

        final SearchByLocationActivity currentView = this;
        
        Set<String> stops = new HashSet<String>();
        for(String busNo : ApplicationUtility.data.keySet()){
        	stops.addAll(ApplicationUtility.data.get(busNo));
        }
        
        List<String> stopsList = new ArrayList<String>(stops);
        
        Collections.sort(stopsList);
        
        ArrayAdapter<String> stopsSuggestion = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stopsList);
		 
		AutoCompleteTextView textView = (AutoCompleteTextView)
	                 findViewById(R.id.autoCompleteTextView1);
		
		textView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				String stop = (String)adapterView.getItemAtPosition(position);
				List<String> busNos = new ArrayList<String>();
				
				for(String busNo : ApplicationUtility.data.keySet()){
					if(ApplicationUtility.data.get(busNo).contains(stop)){
						busNos.add(busNo);
					}
				}
				
				Collections.sort(busNos);
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(currentView, android.R.layout.simple_list_item_1,busNos);
				ListView listView = (ListView)findViewById(R.id.listView1);
				listView.setAdapter(adapter);
			}
		});
		
	         textView.setAdapter(stopsSuggestion);
	         textView.setThreshold(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_by_location, menu);
        return true;
    }
    
}
