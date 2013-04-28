package edu.columbia.farmlendevaluator.data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import edu.columbia.farmlendevaluator.R;

public class QuestionAdapter extends ArrayAdapter<Question>{

    Context context;
    int layoutResourceId;   
    Question data[] = null;
   
    public QuestionAdapter(Context context, int layoutResourceId, Question[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        QuestionHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new QuestionHolder();
            holder.textView = (TextView)row.findViewById(R.id.textView2);
            holder.spinner = (Spinner)row.findViewById(R.id.spinner2);
           
            row.setTag(holder);
        }
        else
        {
            holder = (QuestionHolder)row.getTag();
        }
       
        final Question question = data[position];
        holder.textView.setText(question.text);
        
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
		  android.R.layout.simple_spinner_dropdown_item, question.choices);

		// Assign adapter to ListView
		holder.spinner.setAdapter(adapter);
		
		// select the currently selected value
		holder.spinner.setSelection(question.currentlySelected);
		
		holder.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        question.currentlySelected = position;
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        question.currentlySelected = 0;
		    }

		});

        return row;
    }
   
    static class QuestionHolder
    {
        TextView textView;
        Spinner spinner;
    }
}