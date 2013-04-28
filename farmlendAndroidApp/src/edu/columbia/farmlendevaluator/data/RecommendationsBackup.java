package edu.columbia.farmlendevaluator.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class RecommendationsBackup {

	private static final String RECOMMENDATION_FILE_NAME = "recommendationData.txt";

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Section> ITEMS = new ArrayList<Section>();
	
	private static volatile boolean isLoaded = false;

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, Section> ITEM_MAP = new HashMap<String, Section>();
	public static Map<String, Section> sectionMap = new HashMap<String, Section>();

	public static void initData(Context c) throws IOException {
		
		if (isLoaded)
			return;
		
		isLoaded = true;
		
		InputStream fis = c.getAssets().open(RECOMMENDATION_FILE_NAME);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line;
		Integer currentSection=1;
		while ((line = br.readLine()) != null) {
			String[] tokens = line.split("\t");
			
			String sectionName = tokens[1] + " > " + tokens[2] + " > " + tokens[3];
			
			if (!sectionMap.containsKey(sectionName)) {
				Section newSection = new Section(currentSection.toString(), sectionName, tokens[1], tokens[2], tokens[3]);
				ITEM_MAP.put(newSection.id, newSection);
				sectionMap.put(sectionName, newSection);
				ITEMS.add(newSection);
				currentSection++;
			}

			Section aSection = sectionMap.get(sectionName);
			
			String currentRecommendation = tokens[4];
			if (currentRecommendation.length() > 1) {
				int startIndex = 0;
				int endIndex = currentRecommendation.length();
				if (currentRecommendation.charAt(0) == '"')
					startIndex++;
				if (currentRecommendation.charAt(currentRecommendation.length()-1) == '"')
					endIndex--;
				currentRecommendation = currentRecommendation.substring(startIndex,  endIndex);
			}
			
			aSection.recommendations.add(aSection.recommendations.size() + 1 + ". " + currentRecommendation);
			
		}
		fis.close();
	}
	
	/**
	 * A dummy item representing a piece of content.
	 */
	public static class Section {
		public String id;
		public String content;
		public String s1;
		public String s2;
		public String s3;
		public List<String> recommendations = new ArrayList<String>();
		
		public Section(String id, String content, String s1, String s2, String s3) {
			this.id = id;
			this.content = content;
			this.s1 = s1;
			this.s2 = s2;
			this.s3 = s3;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}