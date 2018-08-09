package com.ubs.maven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringAccumulator {

	public int add(String numbers) throws Exception {

		if ("".equals(numbers)) {
			return 0;
		}

		// If input string contains new line, replace them with \r\n standard line break in windows
		if (numbers.contains("\n")) {
			numbers = numbers.replaceAll("\\n", "\r\n");
		}

		final List<Integer> negativeList = new ArrayList<>();
		int result = 0;
		final String start = "//";
		final String end = "\r\n";
		final String newLineDelimiter = "\r?\n";
		
		// Assuming initial delimiter to try with is comma
		final String initialDelimiter = ",";   
		Object[] delimitersArray = createDelimitersArray(newLineDelimiter, initialDelimiter);

		
		// Check if input string starts with //, this means delimiter will be changed and extract main input string after \n.
		if (numbers.startsWith(start)) {
			final String newDelimiters = StringUtils.substringBetween(numbers, start, end);
			delimitersArray = createDelimitersArray(newLineDelimiter, newDelimiters);
			numbers = StringUtils.substringAfter(numbers, end);
		}

		//get string representation of all the delimiters
		final String separatorChars = Arrays.toString(delimitersArray);
		final String[] splittedNumbers = StringUtils.split(numbers, separatorChars);
		
		// Convert all string to integer and sum them up. If any number is greater than 1000,ignore them.
		for (final String number : splittedNumbers) {
			int intNumber = Integer.valueOf(number);
			if (intNumber < 0) {
				negativeList.add(intNumber);
			} else {
				if (intNumber <= 1000) {
					result += intNumber;
				}
			}
		}

		// If negative list contains any numbers, then throw exception
		if (negativeList.size() > 0) {
			throw new Exception("negatives not allowed " + StringUtils.join(negativeList, ","));
		}
		return result;
	}

	// This method returns an array of delimiters, new line as first delimiter.
	public Object[] createDelimitersArray(final String newLineDelimiter, final String newDelimiters) {
		final List<String> delimiterList = new ArrayList<>();
		delimiterList.add(newLineDelimiter);
		final String[] newDelimsArr = StringUtils.split(newDelimiters, "|");
		for (final String delim : newDelimsArr) {
			delimiterList.add(delim);
		}
		return delimiterList.toArray();
	}
}
