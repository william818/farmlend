package edu.columbia.farmlendevaluator.data;

import java.util.ArrayList;
import java.util.List;

public class Question {

	public String text;
	public Integer currentlySelected = 0;
	public Double weight;
	public List<String> choices = new ArrayList<String>();
	public List<Double> choiceValues = new ArrayList<Double>();
	
	public Question(String t, Double w) {
		text = t;
		weight = w;
	}
	
	public void addValue(String s, Double v) {
		choices.add(s);
		choiceValues.add(v);
	}

}
