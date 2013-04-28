package edu.columbia.farmlendevaluator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class GeoSelector extends Activity {

	private static String[] values = new String[] { "East Asia and Pacific", "Latin America and Carribean", "Eastern Europe and Central Asia", "Middle East and North Africa", "South Asia", "Sub-Saharan Africa"};
	public static String currentGeoSelection = values[0];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geo_selector);
		// Show the Up button in the action bar.
		setupActionBar();

		final Spinner s = (Spinner) findViewById(R.id.geoChoices);

		setupChoices(s);

		final Intent scoreIntent = new Intent(this, QuestionListActivity.class);
		final Button b = (Button) findViewById(R.id.nextButton);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentGeoSelection = (String)s.getSelectedItem();
				startActivity(scoreIntent);			
			}});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geo_selector, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setupChoices(Spinner s) {

				// Define a new Adapter
				// First parameter - Context
				// Second parameter - Layout for the row
				// Third parameter - ID of the TextView to which the data is written
				// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_spinner_dropdown_item, values);
		
		s.setAdapter(adapter);
	}
	
}
