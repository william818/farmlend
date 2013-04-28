package edu.columbia.farmlendevaluator.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;

public class Recommendations {

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
			
			String sectionName = tokens[1]; // + " > " + tokens[2] + " > " + tokens[3];
			
			if (!sectionMap.containsKey(sectionName)) {
				Section newSection = new Section(currentSection.toString(), sectionName);
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
			
			aSection.addRec(tokens[2], tokens[3], currentRecommendation);
			
		}
		fis.close();
	}
	
	/**
	 * A dummy item representing a piece of content.
	 */
	public static class Section {
		public String id;
		public String content;
		private Map<String, Map<String, List<String>>> recommendations = new HashMap<String, Map<String, List<String>>>();
		
		public Section(String id, String content) {
			this.id = id;
			this.content = content;
		}
		
		public void addRec(String s2, String s3, String rec) {
			if (!recommendations.containsKey(s2)) {
				recommendations.put(s2,  new HashMap<String, List<String>>());
			}
			if (!recommendations.get(s2).containsKey(s3)) {
				recommendations.get(s2).put(s3,  new ArrayList<String>());
			}
			
			List<String> currentRecs = recommendations.get(s2).get(s3);
			currentRecs.add(currentRecs.size() + 1 + ". " + rec);
		}
		
		public List<String> getAllRecs(String s2, String s3) {
			return recommendations.get(s2).get(s3);
		}
		
		public Set<String> getAllS2s() {
			return recommendations.keySet();
		}

		public Set<String> getAllS3s(String s2) {
			return recommendations.get(s2).keySet();
		}		
		
		@Override
		public String toString() {
			return content;
		}
	}
}
