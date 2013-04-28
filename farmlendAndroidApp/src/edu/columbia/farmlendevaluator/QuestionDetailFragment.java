package edu.columbia.farmlendevaluator;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.columbia.farmlendevaluator.data.Question;
import edu.columbia.farmlendevaluator.data.QuestionAdapter;
import edu.columbia.farmlendevaluator.data.QuestionCategories;

/**
 * A fragment representing a single Question detail screen. This fragment is
 * either contained in a {@link QuestionListActivity} in two-pane mode (on
 * tablets) or a {@link QuestionDetailActivity} on handsets.
 */
public class QuestionDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private QuestionCategories.Section mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public QuestionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = QuestionCategories.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
			
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_question_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.question_detail))
					.setText(mItem.content);
		}

//		GridLayout mainGrid = (GridLayout) rootView.findViewById(R.id.mainGrid);


	        
/*		for (String question : mItem.questions.keySet()) {
			TextView tv = new TextView(this.getActivity().getApplicationContext());
			tv.setText(question);
			mainGrid.add
			
			
		}
	*/	
		/*
		Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);

		String[] values = new String[] { "Choice 1", "Choice 2","Choice 3"};
				// Define a new Adapter
				// First parameter - Context
				// Second parameter - Layout for the row
				// Third parameter - ID of the TextView to which the data is written
				// Forth - the Array of data

		List<String> values2 = new ArrayList<String>(mItem.questions.keySet());
		
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
				  android.R.layout.simple_spinner_dropdown_item, values2);


				// Assign adapter to ListView
				spinner.setAdapter(adapter); */
/*		        listView.setItemsCanFocus(false);
		        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		*/
				Question[] qArray = new Question[0];
				qArray = new ArrayList<Question>(mItem.questions.values()).toArray(qArray);
		
			       QuestionAdapter ad=new QuestionAdapter(rootView.getContext(),R.layout.question_view, qArray);
			        ListView listView=(ListView)rootView.findViewById(R.id.questionListView);
			        listView.setAdapter(ad);
			        
					final Intent calcIntent = new Intent(rootView.getContext(), DisplayScoreActivity.class);
					final Button b = (Button) rootView.findViewById(R.id.calculateCreditScoreButton);
					b.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							startActivity(calcIntent);			
						}});
			        
		return rootView;
	}
}
