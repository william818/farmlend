package edu.columbia.farmlendevaluator.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class QuestionCategories {

	private static final String QUESTION_FILE_NAME = "questionData.txt";

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Section> ITEMS = new ArrayList<Section>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, Section> ITEM_MAP = new HashMap<String, Section>();
	public static Map<String, Section> sectionMap = new HashMap<String, Section>();

	public static void initData(Context c, String geoSelection) throws IOException {
		
		// clear all
		ITEMS = new ArrayList<Section>();
		ITEM_MAP = new HashMap<String, Section>();
		sectionMap = new HashMap<String, Section>();
		
		Set<String> setsToLoad = new HashSet<String>();
		setsToLoad.add("Global");
		setsToLoad.add(geoSelection);
		
		InputStream fis = c.getAssets().open(QUESTION_FILE_NAME);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line;
		Integer currentSection=1;
		while ((line = br.readLine()) != null) {
			String[] tokens = line.split("\t");
			
			if (!setsToLoad.contains(tokens[0]))
				continue;
			//System.out.println("SELECTED GEO==>" + geoSelection + ", and T0=" + tokens[0]);
			
			if (!sectionMap.containsKey(tokens[1])) {
				Section newSection = new Section(currentSection.toString(), tokens[1]);
				ITEM_MAP.put(newSection.id, newSection);
				sectionMap.put(tokens[1], newSection);
				ITEMS.add(newSection);
				currentSection++;
			}

			Section aSection = sectionMap.get(tokens[1]);
			
			if (!aSection.questions.containsKey(tokens[3]))
				aSection.questions.put(tokens[3], new Question(tokens[3], Double.parseDouble(tokens[2])));
				
			aSection.questions.get(tokens[3]).addValue(tokens[4], Double.parseDouble(tokens[5]));
			
		}
		fis.close();
	}
	
	/**
	 * A dummy item representing a piece of content.
	 */
	public static class Section {
		public String id;
		public String content;
		public Map<String, Question> questions = new HashMap<String, Question>();
		
		public Section(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
