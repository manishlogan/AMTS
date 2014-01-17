package com.logan.amtsinfo.filter;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.widget.FilterQueryProvider;

public class SuggestionFilterQueryProvider extends LinkedList<String> implements
		FilterQueryProvider {

	public SuggestionFilterQueryProvider(List<String> stops) {
		super(stops);
	}
	
	@Override
	public Cursor runQuery(CharSequence constraint) {
		if(constraint == null){
			return null;
		}
		
		String lowerConstraint = constraint.toString().toLowerCase();
		String cols[] = {"_id","stopName"};
		
		int id = 0;
		
		MatrixCursor cursor = new MatrixCursor(cols);
		for(String name : this){
			String stopName = name.toLowerCase();
			if(stopName.contains(lowerConstraint)){
				cursor.newRow().add(id++).add(name);
			}
		}
		return cursor;
	}
}
