package com.logan.amtsinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.evolvingvision.amtsinfo.R;
import com.logan.amtsinfo.adapter.BusDetailsAdapter;
import com.logan.amtsinfo.filter.SuggestionFilterQueryProvider;

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
        String[] from = {"stopName"};
        int[] to = {android.R.id.text1};
        
        Collections.sort(stopsList);
        
        SimpleCursorAdapter stopsSuggestion = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, null, from, to);
		SuggestionFilterQueryProvider filter = new SuggestionFilterQueryProvider(stopsList);
		stopsSuggestion.setFilterQueryProvider(filter); 
		
		stopsSuggestion.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
			@Override
			public CharSequence convertToString(Cursor cursor) {
				return cursor.getString(1);
			}
		});
		
		AutoCompleteTextView textView = (AutoCompleteTextView)
	                 findViewById(R.id.autoCompleteTextView1);
		
		textView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				MatrixCursor cursor = (MatrixCursor)adapterView.getItemAtPosition(position);
				String stop = cursor.getString(1);
				List<String> busNos = new ArrayList<String>();
	    		ArrayList<HashMap<String,String>> busDetails = new ArrayList<HashMap<String,String>>();

				for(String busNo : ApplicationUtility.data.keySet()){
	    			List<String> stops = ApplicationUtility.data.get(busNo); 
					if(stops.contains(stop)){
						HashMap<String,String> detail = new HashMap<String, String>();
	    				detail.put("busNo",busNo);
	    				detail.put("description", stops.get(0)+"-"+stops.get(stops.size() - 1));
	    				busDetails.add(detail);
					}
				}
				
				Collections.sort(busNos);
				
				BusDetailsAdapter adapter = new BusDetailsAdapter(currentView, busDetails);
	    		ListView listView = (ListView)findViewById(R.id.srcStops);
	    		listView.setAdapter(adapter);
	    		
	    		listView.setOnItemClickListener(new OnItemClickListener() {
	    			@Override
	    			public void onItemClick(AdapterView<?> adapter, View view,
	    					int position, long arg3) {
	    				HashMap<String, String> data = (HashMap<String, String>)adapter.getItemAtPosition(position);
	    				Intent nextActivity = new Intent(currentView, BusDetailsActivity.class);
	    				nextActivity.putExtra("busNo", data.get("busNo"));
	    				currentView.startActivity(nextActivity);
	    			}
				});
			}
		});
		
	         textView.setAdapter(stopsSuggestion);
	         textView.setThreshold(1);
    }
    
}
