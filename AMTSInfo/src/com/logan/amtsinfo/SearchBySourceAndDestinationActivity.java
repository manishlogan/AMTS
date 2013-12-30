package com.logan.amtsinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.logan.amtsinfo.adapter.BusDetailsAdapter;

public class SearchBySourceAndDestinationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_source_and_destination);
        
        Set<String> stops = new HashSet<String>();
        for(String busNo : ApplicationUtility.data.keySet()){
        	stops.addAll(ApplicationUtility.data.get(busNo));
        }
        
        List<String> stopsList = new ArrayList<String>(stops);
        
        Collections.sort(stopsList);
        
        ArrayAdapter<String> srcSuggestion = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stopsList);
        ArrayAdapter<String> destSuggestion = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stopsList);
        
		AutoCompleteTextView srcTextView = (AutoCompleteTextView)
	                 findViewById(R.id.autoCompleteTextView1);
		
		AutoCompleteTextView destTextView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView2);
		
		srcTextView.setAdapter(srcSuggestion);
		srcTextView.setThreshold(1);
		
		destTextView.setAdapter(destSuggestion);
		destTextView.setThreshold(1);
    }
    
    public void search(View view){
    	String src = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1)).getText().toString();
    	String dest = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2)).getText().toString();
    	
    	if(src.trim().equals("") || dest.trim().equals("")){
    		Toast.makeText(this, "Please enter both source and destination", Toast.LENGTH_SHORT).show();
    	}else{
    		ArrayList<HashMap<String,String>> busDetails = new ArrayList<HashMap<String,String>>();
    		
    		for(String bus : ApplicationUtility.data.keySet()){
    			List<String> stops = ApplicationUtility.data.get(bus); 
    			if(stops.contains(src) && stops.contains(dest)){
    				HashMap<String,String> detail = new HashMap<String, String>();
    				detail.put("busNo",bus);
    				detail.put("description", stops.get(0)+"-"+stops.get(stops.size() - 1)+"-"+stops.get(0));
    				busDetails.add(detail);
    			}
    		}
    		
    		BusDetailsAdapter adapter = new BusDetailsAdapter(this, busDetails);
    		ListView listView = (ListView)findViewById(R.id.listView1);
    		listView.setAdapter(adapter);
    	}
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_by_source_and_destination, menu);
        return true;
    }
    
}
