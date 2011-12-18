package com.epic.framework.util;

import java.util.*;

public class StringHelper {
	public static boolean contains(String string, String match) {
		return string.indexOf(match) != -1;
	}
	public static String format(String msg, Object... args) {
		int i = 0;
		int arg_i = 0;

		StringBuffer output = new StringBuffer();
		while(i < msg.length()) {
			char c = msg.charAt(i++);
			switch(c) {
			case '%': {
				char c2 = msg.charAt(i++);
				switch(c2) {
				case '%':
					output.append('%');
					break;
				case 'd': {
					if(arg_i >= args.length) throw EpicFail.stringFormat("Not enough arguments supplied for format string");
					Object arg = args[arg_i++];
					Integer casted = (Integer)arg;
					output.append(casted.toString());
					break;
				}
				case 's': {
					if(arg_i >= args.length) throw EpicFail.stringFormat("Not enough arguments supplied for format string");
					Object arg = args[arg_i++];
					String casted = (String)arg;
					output.append(casted);
					break;
				}
				case 'f': {
					if(arg_i >= args.length) throw EpicFail.stringFormat("Not enough arguments supplied for format string");
					Object arg = args[arg_i++];
					Float casted = (Float)arg;
					output.append(casted.toString());
					break;				
				}
				default:
					throw EpicFail.stringFormat("unexpected character in variable description '" + c2 + "'");
				}
				break;
			}
			default:
				output.append(c);
			}
		}
		return output.toString();
	}
	
	public static String zeroPaddedInt(int i, int length) {
		String string = Integer.toString(i);
		int zeros = length - string.length();
		if(zeros < 0) {
			EpicLog.w("zeroPaddedInt exceeds desired length of '" + length + "' actual='" + string.length() + "'");
		}
		for(int j = 0; j < zeros; j++) {
			string = "0" + string;
		}
		return string;
	}

	public static String stripSuffix(String string, String suffix) {
		if(string.length() < suffix.length())  {
			throw EpicFail.invalid_argument("string is shorter than expected suffix");
		}

		int index = string.length() - suffix.length();
		if(!string.substring(index, string.length()).equals(suffix)) {
			throw EpicFail.invalid_argument("string '" + string + "'does not match expected suffix '" + suffix + "'");
		}
		return string.substring(0, index);
	}

	public static String join(String separator, List<String> strings) {
		if(strings.size() == 0) {
			return "";
		}
		int totalSize = (strings.size() - 1) * separator.length();
		for(int i = 0; i < strings.size(); i++) {
			totalSize += strings.get(i).length();
		}
		StringBuffer joined = new StringBuffer(totalSize);
		for(int i = 0; i < strings.size(); i++) {
			if(i > 0) {
				joined.append(separator);
			}
			joined.append(strings.get(i));
		}
		return joined.toString();
	}
	public static String join(String separator, String... strings) {
		if(strings.length == 0) {
			return "";
		}
		int totalSize = (strings.length - 1) * separator.length();
		for(int i = 0; i < strings.length; i++) {
			totalSize += strings[i].length();
		}
		StringBuffer joined = new StringBuffer(totalSize);
		for(int i = 0; i < strings.length; i++) {
			if(i > 0) {
				joined.append(separator);
			}
			joined.append(strings[i]);
		}
		return joined.toString();
	}

	public static String[] split(String string, String separator) {
		return string.split(separator);
//		int nTokens = 0;
//		int index = 0;
//		int matchIndex;
//		do {
//			matchIndex = string.indexOf(separator, index);
//			index = matchIndex + separator.length();
//			nTokens++;
//		} while(matchIndex != -1);
//		
//		String[] results = new String[nTokens];
//
//		index = 0;
//		for(int i=0; i < nTokens; i++) {
//			matchIndex = string.indexOf(separator, index);
//			results[i] = matchIndex == index ? "" : string.substring(index, matchIndex > 0 ? matchIndex : string.length());
//			index = matchIndex + separator.length();
//		}
//
//		return results;
	}


	private static final int BUFFER_SIZE = 1024;
	private static final char[] buffer = new char[BUFFER_SIZE];
	public static String leftPad(String string, int length) {
		EpicFail.assertBounds(0, length, BUFFER_SIZE);
		int i = 0;

		for(int n = 0; n < length - string.length(); n++) {
			buffer[i++] = ' ';
		}

		string.getChars(0, string.length(), buffer, i);
		i += string.length();

		return new String(buffer, 0, i);
	}

	public static String nSpaces(int n) {
		for(int i = 0; i < n; i++) {
			buffer[i] = ' ';
		}
		return new String(buffer, 0, n);
	}

	public static Object rightPad(String string, int length) {
		EpicFail.assertBounds(0, length, BUFFER_SIZE);
		int i = 0;

		string.getChars(0, string.length(), buffer, i);
		i += string.length();

		for(int n = 0; n < length - string.length(); n++) {
			buffer[i++] = ' ';
		}

		return new String(buffer, 0, i);	
	}

	public static String namedArgList(Object... params) {
		EpicFail.assertTrue(params.length % 2 == 0, "named arg list expects an even sized list of params");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < params.length; i += 2) {
			sb.append(params[i]);
			sb.append("='");
			sb.append(params[i+1]);
			if(i+2 < params.length) {
				sb.append("', ");
			}
			else {
				sb.append("'");
			}
		}
		return sb.toString();
	}
	
	public static String formatInt_mil(long n) {
		return "" + n / 1000000 + "." + (n / 100000) % 10 + "M";
	}
	
	private static final char[] _scratch = new char[32];
	public static String formatInteger(long i, int minLength, char padchar, boolean addCommas) {
		boolean isNegative = false;
		
		if(i == 0) return "0";
		
		if(i < 0) {
			isNegative = true;
			i = -i;
			minLength--;
		}
		int d = 0;
		while(i > 0) {
			if(addCommas && (d % 4 == 3)) {
				_scratch[32-(++d)] = ',';
			}
			int dv = (int)(i % 10);
			_scratch[32-(++d)] = (char)('0' + dv);
			i /= 10;
		}
		while(d < minLength) {
			_scratch[32-(++d)] = padchar;
		}
		if(isNegative) {
			_scratch[32-(++d)] = '-';
		}
		return new String(_scratch, 32-d, d);
	}

	public static String formatIntegerWithCommas(int i) {
		return formatInteger(i, 0, ' ', true);
	}

	public static String formatInt_mib(long n) {
		return "" + n / 1024 / 1024 + "." + (10 * n / 1024 / 1024) % 10 + "" + (100 * n / 1024 / 1024) % 10 + "M";
	}

	public static boolean validateEmail(String email) {

        if (email == null || email.length() == 0 || email.indexOf("@") == -1 || email.indexOf(" ") != -1) {
            return false;
        }
        int emailLenght = email.length();
        int atPosition = email.indexOf("@");

        String beforeAt = email.substring(0, atPosition);
        String afterAt = email.substring(atPosition + 1, emailLenght);

        if (beforeAt.length() == 0 || afterAt.length() == 0) {
            return false;
        }
        if (email.charAt(atPosition - 1) == '.') {
            return false;
        }
        if (email.charAt(atPosition + 1) == '.') {
            return false;
        }
        if (afterAt.indexOf(".") == -1) {
            return false;
        }
        char dotCh = 0;
        for (int i = 0; i < afterAt.length(); i++) {
            char ch = afterAt.charAt(i);
            if ((ch == 0x2e) && (ch == dotCh)) {
                return false;
            }
            dotCh = ch;
        }
        if (afterAt.indexOf("@") != -1) {
            return false;
        }
        int ind = 0;
        do {
            int newInd = afterAt.indexOf(".", ind + 1);

            if (newInd == ind || newInd == -1) {
                String prefix = afterAt.substring(ind + 1);
                if (prefix.length() > 1 && prefix.length() < 6) {
                    break;
                } else {
                    return false;
                }
            } else {
                ind = newInd;
            }
        } while (true);
        dotCh = 0;
        for (int i = 0; i < beforeAt.length(); i++) {
            char ch = beforeAt.charAt(i);
            if (!((ch >= 0x30 && ch <= 0x39) || (ch >= 0x41 && ch <= 0x5a) || (ch >= 0x61 && ch <= 0x7a)
                    || (ch == 0x2e) || (ch == 0x2d) || (ch == 0x5f))) {
                return false;
            }
            if ((ch == 0x2e) && (ch == dotCh)) {
                return false;
            }
            dotCh = ch;
        }
        return true;
    }

}
