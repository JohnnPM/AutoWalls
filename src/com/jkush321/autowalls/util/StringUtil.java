package com.jkush321.autowalls.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	public static List<String> wrapWords(String text, int lineLength) {
		String[] intendedLines = text.split("\\n");
		ArrayList<String> lines = new ArrayList<>();
		for (String intendedLine : intendedLines) {
			String[] words = intendedLine.split(" ");
			StringBuilder buffer = new StringBuilder();

			for (String word : words) {
				if (word.length() >= lineLength) {
					if (buffer.length() != 0) {
						lines.add(buffer.toString());
					}
					lines.add(word);
					buffer = new StringBuilder();
					continue;
				}
				if (buffer.length() + word.length() >= lineLength) {
					lines.add(buffer.toString());
					buffer = new StringBuilder();
				}
				if (buffer.length() != 0) {
					buffer.append(' ');
				}
				buffer.append(word);
			}
			lines.add(buffer.toString());
		}

		return lines;
	}
}