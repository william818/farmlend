package edu.columbia.farmlendevaluator.data;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import edu.columbia.farmlendevaluator.R;
import edu.columbia.farmlendevaluator.RecommendationListFragment.Callbacks;

public class RecommendationAdapter extends ArrayAdapter<Recommendations.Section>{

    Context context;
    int layoutResourceId;   
    Recommendations.Section data[] = null;
    
    private Callbacks recCallback;
    
    //private static final String NAME = "NAME";
   
    public RecommendationAdapter(Context context, int layoutResourceId, Recommendations.Section[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecommendationExpandableHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new RecommendationExpandableHolder();
            holder.firstCategory = (TextView)row.findViewById(R.id.recFirstCategory);
            holder.recExpandList = (ExpandableListView)row.findViewById(R.id.recExpandList);
           
            row.setTag(holder);
        }
        else
        {
            holder = (RecommendationExpandableHolder)row.getTag();
        }
       
        final Recommendations.Section section = data[position];
        holder.firstCategory.setText(section.content);
        
        /*
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (String s2s : section.getAllS2s()) {
        	Map<String, String> tempMap = new HashMap<String, String>();
        	tempMap.put(NAME, s2s);
        	groupData.add(tempMap);

        	List<Map<String, String>> childMaps = new ArrayList<Map<String, String>>();
            for (String s3s : section.getAllS3s(s2s)) {
	        	Map<String, String> tempMap2 = new HashMap<String, String>();
	        	tempMap2.put(NAME, s3s);
	        	childMaps.add(tempMap);
            }
            childData.add(childMaps);
        }
        
        for (Map<String, String> aGroup : groupData) {
        	System.out.println("GROUP VAL:" + aGroup.get(NAME));
        }
        System.out.println("FINISH GROUP");
        
        SimpleExpandableListAdapter mAdapter = new SimpleExpandableListAdapter(
                context,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME },
                new int[] { android.R.id.text1 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { NAME },
                new int[] { android.R.id.text1 }
                );
        */
        
        final List<String> groups = new ArrayList<String>(section.getAllS2s());
        final List<List<String>> children = new ArrayList<List<String>>();
        for (String group : groups) {
        	children.add(new ArrayList<String>(section.getAllS3s(group)));
        }
        final RecommendationExpanderAdapter mAdapter = new RecommendationExpanderAdapter(groups, children, context);
        final ExpandableListView recExpandList = holder.recExpandList;
        holder.recExpandList.setAdapter(mAdapter);
        
        holder.recExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				recCallback.onItemSelected(section.id, groups.get(groupPosition), children.get(groupPosition).get(childPosition));
				return true;
			}
        });
        /*
        holder.recExpandList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mAdapter.notifyDataSetChanged();
				notifyDataSetChanged();
				recExpandList.getLayoutParams().height=500;
				System.out.println("Click'!");
			}
		});
        
        holder.recExpandList.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				mAdapter.notifyDataSetChanged();
				notifyDataSetChanged();
				recExpandList.getLayoutParams().height=500;
				System.out.println("Click222'!");
				return false;
			}
		});
			
        
        holder.recExpandList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
			    recExpandList.expandGroup(groupPosition);
				mAdapter.notifyDataSetChanged();
				notifyDataSetChanged();
				
			}
		});

        holder.recExpandList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
			    recExpandList.collapseGroup(groupPosition);
				mAdapter.notifyDataSetChanged();
				notifyDataSetChanged();
			}
		});*/
        
/*        
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
*/
        return row;
    }
   
    static class RecommendationExpandableHolder
    {
        TextView firstCategory;
        ExpandableListView recExpandList;
    }

	public void setCallback(Callbacks mCallbacks) {
		recCallback = mCallbacks;
	}
}