package edu.columbia.farmlendevaluator;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.columbia.farmlendevaluator.data.Recommendations;

/**
 * A fragment representing a single Recommendation detail screen. This fragment
 * is either contained in a {@link RecommendationListActivity} in two-pane mode
 * (on tablets) or a {@link RecommendationDetailActivity} on handsets.
 */
public class RecommendationDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	public static final String ARG_S2 = "item_id_S2";
	public static final String ARG_S3 = "item_id_S3";
	
	/**
	 * The dummy content this fragment is presenting.
	 */
	private Recommendations.Section mItem;
	
	private String title;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public RecommendationDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = Recommendations.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
			
			title = mItem.content + " > " + getArguments().getString(ARG_S2) + " > " + getArguments().getString(ARG_S3);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_recommendation_detail, container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.recommendation_detail))
					.setText(title);
		}
		
		List<String> recContent = mItem.getAllRecs(getArguments().getString(ARG_S2), getArguments().getString(ARG_S3));

		ListView listView = (ListView) rootView.findViewById(R.id.recommendationsListView);

				// Define a new Adapter
				// First parameter - Context
				// Second parameter - Layout for the row
				// Third parameter - ID of the TextView to which the data is written
				// Forth - the Array of data

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
				  android.R.layout.simple_list_item_1, recContent);


				// Assign adapter to ListView
				listView.setAdapter(adapter);
		        listView.setItemsCanFocus(false);
//		        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		
				Button b = (Button) rootView.findViewById(R.id.sendRecommendationEmail);
				final String sectionTitle = title;
				final StringBuffer recommendText = new StringBuffer();
				
				for (String r : recContent) {
					recommendText.append(r);
					recommendText.append("\n\n");
				}
				
				b.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// send email
				        Intent i = new Intent(Intent.ACTION_SEND);
				        i.setType("message/rfc822");
				        //i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
				        i.putExtra(Intent.EXTRA_SUBJECT, "Farmlend Recommendations");
				        i.putExtra(Intent.EXTRA_TEXT   , sectionTitle + "\n\n" + recommendText.toString());
				        try {
				            startActivity(Intent.createChooser(i, "Send email..."));
				        } catch (android.content.ActivityNotFoundException ex) {
				            //Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				        }
						
					}});

		        
		return rootView;
	}
}
