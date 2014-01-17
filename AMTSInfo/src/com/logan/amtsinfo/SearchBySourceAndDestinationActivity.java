package com.logan.amtsinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.logan.amtsinfo.adapter.BusDetailsAdapter;
import com.logan.amtsinfo.adapter.IndirectRouteAdapter;
import com.logan.amtsinfo.filter.SuggestionFilterQueryProvider;

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
        String[] from = {"stopName"};
        int[] to = {android.R.id.text1};
        
        Collections.sort(stopsList);
        
        SimpleCursorAdapter srcSuggestion = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, null, from, to);
        SimpleCursorAdapter destSuggestion = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, null, from, to);
        SuggestionFilterQueryProvider filter = new SuggestionFilterQueryProvider(stopsList);
		srcSuggestion.setFilterQueryProvider(filter); 
		destSuggestion.setFilterQueryProvider(filter);
		
		SimpleCursorAdapter.CursorToStringConverter converter = new SimpleCursorAdapter.CursorToStringConverter() {
			@Override
			public CharSequence convertToString(Cursor cursor) {
				return cursor.getString(1);
			}
		};
		
		srcSuggestion.setCursorToStringConverter(converter);
		destSuggestion.setCursorToStringConverter(converter);
		
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
    				detail.put("description", stops.get(0)+"-"+stops.get(stops.size() - 1));
    				busDetails.add(detail);
    			}
    		}
    		if(busDetails.isEmpty()){
    			Button indirectRoutesButton = ((Button)findViewById(R.id.indirectRoutesButton));
    			indirectRoutesButton.setVisibility(0);
    			Toast.makeText(this, "No direct bus found...", Toast.LENGTH_SHORT).show();
    		}
    		
    		BusDetailsAdapter adapter = new BusDetailsAdapter(this, busDetails);
    		ListView listView = (ListView)findViewById(R.id.listView1);
    		listView.setAdapter(adapter);
    	}
    }

	public void findIndirectRoutes(View view) {
		String src = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1)).getText().toString();
    	String dest = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2)).getText().toString();
    	
		HashMap<String, ArrayList<String>> srcBuses = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> destBuses = new HashMap<String, ArrayList<String>>();
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		
		for(String bus : ApplicationUtility.data.keySet()){
			ArrayList<String> stops = ApplicationUtility.data.get(bus);
			if(stops.contains(src)){
				srcBuses.put(bus, stops);
			}else if(stops.contains(dest)){
				destBuses.put(bus, stops);
			}
		}
		
		
		for(String bus : destBuses.keySet()){
			for(String srcBus : srcBuses.keySet()){
				ArrayList<String> desStops = new ArrayList<String>(destBuses.get(bus));
				ArrayList<String> srcStops = new ArrayList<String>(srcBuses.get(srcBus));
				desStops.retainAll(srcStops);
				if(!desStops.isEmpty()){
					HashMap<String, String> buses = new HashMap<String, String>();
					buses.put("srcBusNo",srcBus);
					buses.put("srcBusDesc",src + "-"+desStops.get(0));
					
					buses.put("destBusNo",bus);
					buses.put("destBusDesc",desStops.get(0) + "-" + dest);
					result.add(buses);
				}
			}
		}
		
		if(result.isEmpty()){
			Toast.makeText(this, "No indirect bus found...", Toast.LENGTH_SHORT).show();
		}
		
		IndirectRouteAdapter adapter = new IndirectRouteAdapter(this, result);
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}
}
