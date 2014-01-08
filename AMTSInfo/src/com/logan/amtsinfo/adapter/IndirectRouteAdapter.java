package com.logan.amtsinfo.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.logan.amtsinfo.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IndirectRouteAdapter extends BaseAdapter{

	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
	public IndirectRouteAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.indirect_route, null);
 
        TextView srcBusNo = (TextView)view.findViewById(R.id.srcBusNo);
        TextView srcDesc= (TextView)view.findViewById(R.id.srcBusDesc);
        TextView destBusNo = (TextView)view.findViewById(R.id.destBusNo);
        TextView destDesc = (TextView)view.findViewById(R.id.destBusDesc);
        
        HashMap<String, String> detail = new HashMap<String, String>();
        detail = data.get(position);
        srcBusNo.setText(detail.get("srcBusNo"));
        srcDesc.setText(detail.get("srcBusDesc"));
        destBusNo.setText(detail.get("destBusNo"));
        destDesc.setText(detail.get("destBusDesc"));
        srcBusNo.setTextSize(22);
        srcDesc.setTextSize(20);
        destBusNo.setTextSize(22);
        destDesc.setTextSize(20);
        
        return view;
	}
	
}
