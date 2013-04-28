package edu.columbia.farmlendevaluator;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.columbia.farmlendevaluator.data.Recommendations;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			Recommendations.initData(getApplicationContext());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final ListView listView = (ListView) findViewById(R.id.listView);

		String[] values = new String[] { "Calculate Credit Score", "Obtain Recommendations"}; //, "Record Lending Result"};

				// Define a new Adapter
				// First parameter - Context
				// Second parameter - Layout for the row
				// Third parameter - ID of the TextView to which the data is written
				// Forth - the Array of data

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_list_item_1, values);

        		final Intent recommendationIntent = new Intent(this, RecommendationListActivity.class);
        		final Intent scoreIntent = new Intent(this, GeoSelector.class);
        		final Intent loginIntent = new Intent(this, LoginActivity.class);

				// Assign adapter to ListView
				listView.setAdapter(adapter);
		        listView.setItemsCanFocus(false);
		        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		        listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View v,
							int position, long arg3) {
		            	if (position == 2) {
		            		startActivity(loginIntent);
		            	} else if (position == 1) {
		            		startActivity(recommendationIntent);
		            	} else if (position == 0) {
		            		startActivity(scoreIntent);
		            	}
					}
		        });
		        
		        /*
		        SimpleStandardAdapter simpleAdapter;
		        TreeViewList treeView = (TreeViewList) findViewById(R.id.treeListView);
		        treeView.setCollapsible(true);

		        final Set<Long> selected = new HashSet<Long>();
	            InMemoryTreeStateManager<Long> manager = new InMemoryTreeStateManager<Long>();
	            int[] DEMO_NODES = new int[] { 0, 0, 1, 1, 1, 2, 2, 1,
	                1, 2, 1, 0, 0, 0, 1, 2, 3, 2, 0, 0, 1, 2, 0, 1, 2, 0, 1 };

	            final TreeBuilder<Long> treeBuilder = new TreeBuilder<Long>(manager);
	            for (int i = 0; i < DEMO_NODES.length; i++) {
	                treeBuilder.sequentiallyAddNextNode((long) i, DEMO_NODES[i]);
	            }

		        simpleAdapter = new SimpleStandardAdapter(this, selected, manager,
		                2);
		        treeView.setAdapter(simpleAdapter);
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
