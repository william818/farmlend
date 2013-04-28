package edu.columbia.farmlendevaluator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.columbia.farmlendevaluator.data.Question;
import edu.columbia.farmlendevaluator.data.QuestionCategories;

public class DisplayScoreActivity extends Activity {

	public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#.#");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_score);
		// Show the Up button in the action bar.
		setupActionBar();
		
		final Intent recommendIntent = new Intent(this, RecommendationListActivity.class);
		final Button b = (Button) findViewById(R.id.linkToRecommendButton);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(recommendIntent);			
			}});

		TextView scoreTV = (TextView) findViewById(R.id.scoreTextView);
		TextView decisionTV = (TextView) findViewById(R.id.decisionTextView);

		Double currentScore = 0.0;
		
		for (QuestionCategories.Section s : QuestionCategories.ITEMS) {
			for (Question q : s.questions.values()) {
				currentScore += q.weight * q.choiceValues.get(q.currentlySelected);
			}
		}
		
		currentScore = currentScore * 100.0 / 180.0;
		
		scoreTV.setText(DECIMAL_FORMATTER.format(currentScore) + " / 100");
		
		if (currentScore < 50.0) {
			decisionTV.setText("Don't Lend");
		} else if (currentScore >= 70.0) {
			decisionTV.setText("Lend");
		} else {
			decisionTV.setText("Discretionary");
		}
		
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
		getMenuInflater().inflate(R.menu.display_score, menu);
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

}
