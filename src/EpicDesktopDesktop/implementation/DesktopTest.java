//package com.epic.framework.implementation;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Map.Entry;
//import java.util.PriorityQueue;
//
//import com.epic.framework.common.util.EpicLog;
//import com.epic.framework.common.util.EpicRandom;
//import com.epic.framework.common.util.StringHelper;
//import com.realcasualgames.words.*;
//
//public class DesktopTest {
//	public static void main(String[] args) {
////		Dictionary.ensure_initialized();
////		Dictionary.test();
////		testLetterPools();
//		testStringHelper();
//		System.exit(0);
//	}
//	
//	
//	public static void testStringHelper() {
//		System.out.println(StringHelper.formatInteger(123456789, 0, ' ', true));
//		
//	}
//	
//	public static void testLetterPools() {
//		//		EpicRandomImplementation.random.setSeed(78)v;
//		WordCollection classicWords  = new WordCollection("Classic");
//		WordCollection weightedWords = new WordCollection("Weightd");
//		ScreenGame fakeScreen = new ScreenGame();
//		for(int i = 0; i < 1000; i++) {
//			EpicLog.i("Starting simulation " + i + " ...");
//
//			WordsGame fakeGame1 = new WordsGame(WordsGameConfiguration.classicConfig, fakeScreen);
//			classicWords.addAll(fakeGame1.findAllWords());
//			
//			WordsGame fakeGame2 = new WordsGame(WordsGameConfiguration.weightedConfig, fakeScreen);
//			weightedWords.addAll(fakeGame2.findAllWords());
//		}
//		ArrayList<String> classicOutput = classicWords.getOutputLines(WordsLetterPool.classicPool);
//		ArrayList<String> weightedOutput = weightedWords.getOutputLines(WordsLetterPool.weightedPool);
//		for(int i = 0; i < classicOutput.size(); i++) {
//			String a = classicOutput.get(i);
//			String b = weightedOutput.get(i);
//			System.out.println(StringHelper.rightPad(a, 80) + b);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public static class WordCollection {
//		private static final int MAX_WORD_LENGTH = 20;
//		private final HashMap<String, Integer>[] wordsByLength;
//		private final String prefix;
//		
//		public WordCollection(String prefix) {
//			this.wordsByLength = new HashMap[MAX_WORD_LENGTH];
//			this.prefix = prefix;
//			for(int i = 0; i < MAX_WORD_LENGTH; i++) {
//				wordsByLength[i] = new HashMap<String, Integer>();
//			}
//		}
//
//		public void addAll(Collection<String> words) {
//			for(String word : words) {
//				int l = word.length();
//				Integer curr = wordsByLength[l].get(word);
//				wordsByLength[l].put(word, curr == null ? 1 : curr + 1);
//			}
//		}
//		
//		public ArrayList<String> getOutputLines(WordsLetterPool letterPool) {
//			ArrayList<String> outputLines = new ArrayList<String>();
//			avgSize(outputLines);
//			logTotals(outputLines, letterPool);
//			mostFreq(outputLines);
//			letterCount(outputLines);
//			return outputLines;
//		}
//
//		public void avgSize(ArrayList<String> output) {
//			int total_len = 0;
//			int total_n = 0;
//			for(int i = 0; i < MAX_WORD_LENGTH; i++) {
//				int n = 0;
//				for(Entry<String, Integer> entry : wordsByLength[i].entrySet()) {
//					n += entry.getValue();
//				}
//				total_n += n;
//				total_len += n * i;
//				output.add(String.format("%s: n[%d] = %d", prefix, i, n));
//			}
//			output.add(String.format("%s: %d total, avg_len = %.1f", prefix, total_n, (float) total_len / (float) total_n));
//		}
//		
//		private void logTotals(ArrayList<String> output, WordsLetterPool letterPool) {
//			int[] totals = letterPool.getTotals();
//			int total = 0;
//			for(int c = 'a'; c <= 'z'; c++) {
//				total += totals[c - 'a'];
//			}
//			for(int c = 'a'; c <= 'z'; c++) {
//				String pct = String.format("%.1f%%", totals[c - 'a'] * 100 / (float)total);
//				output.add(prefix + "Letter['" + (char)c + "'] = " + pct + " (" + WordsLetter.get((char)c).scrabbleWeight + "% expected)");
//			}
//		}
//		
//		public void mostFreq(ArrayList<String> output) {
//			for(int i = 0; i < MAX_WORD_LENGTH; i++) {
//				int MostFreqSize = 80 / (i + 1);
//				PriorityQueue<Entry<String, Integer>> mostFreq = new PriorityQueue<Entry<String, Integer>>(100, new Comparator<Entry<String, Integer>>() {
//					public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
//						return a.getValue().compareTo(b.getValue());
//					}
//				});
//				for(Entry<String, Integer> entry : wordsByLength[i].entrySet()) {
//					if(mostFreq.size() < MostFreqSize) {
//						mostFreq.add(entry);
//					}
//					else {
//						if(entry.getValue() > mostFreq.peek().getValue()) {
//							mostFreq.remove();
//							mostFreq.add(entry);
//						}
//					}
//				}
//				ArrayList<String> sorted = new ArrayList<String>();
//				for(Entry<String, Integer> entry : mostFreq) {
//					sorted.add(entry.getKey());
//				}
//				Collections.sort(sorted);
//				output.add(StringHelper.join(",", sorted));
//			}
//		}
//		
//		public void letterCount(ArrayList<String> output) {
//			int[] letterCounts = new int['z'+1];
//			for(int i = 0; i < MAX_WORD_LENGTH; i++) {
//				for(Entry<String, Integer> entry : wordsByLength[i].entrySet()) {
//					String word = entry.getKey();
//					int c = entry.getValue();
//					for(int li = 0; li < i; li++) {
//						letterCounts[word.charAt(li)]++;
//					}
//				}
//			}
//			int total = 0;
//			for(int l = 'a'; l <= 'z'; l++) {
//				total += letterCounts[l];
//			}
//			for(int l = 'a'; l <= 'z'; l++) {
//				String pct = String.format("%.1f%%", letterCounts[l] * 100 / (float)total);
//				output.add(prefix + "LetterUse[" + (char)l + "] = " + pct + " (" + WordsLetter.get((char)l).scrabbleWeight + "% expected)");
//			}
//		}
//	}
//}
