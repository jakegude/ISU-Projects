/**
 * @author jakegude
 */
package cs228hw2;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class AmusingPreciseNumber {

	private ArrayList<String> beforedec = new ArrayList<>();
	private ArrayList<String> afterdec = new ArrayList<>();
	
	/**
	 * Create an AmusingPreciseNumber from an int type. 
	 * @param numb
	 */
	public AmusingPreciseNumber(int numb) {
		String s = Integer.toString(numb);
//		char [] numarr = s.toCharArray();
		int decimalindex = s.length();
		if (s.indexOf(".") > 0) {
			decimalindex = s.indexOf('.');
		}
		for (int i = 0; i < decimalindex; i++) {
			beforedec.add(s.substring(i, i + 1));
		}
		for (int j = decimalindex + 1; j < s.length(); j++) {
			afterdec.add(s.substring(j, j + 1));
		}
	}

	/**
	 * Create an AmusingPreciseNumber from a String.
	 * The formatting of the string is some number of digits with an optional decimal point.
	 * Your constructor is required to throw a runtime exception if the string does not have a valid syntax.
	 * Valid strings do include 0, 0.0, 0000, 00000123, 00000123.000001000, -23432, +1234., and +1234555
	 * That is, leading or trailing zeros, a single leading plus or minus sign, and no plus or minus sign are all valid numbers.
	 * In effect, any reasonable string of digits (no matter how long) that can be interpreted as a number is valid.
	 * @param numb
	 */
	public AmusingPreciseNumber(String numb) { 
		if (numb.length() == 0 || numb.startsWith("+-") || numb.startsWith("-+") 
				|| numb.startsWith(".") || numb.endsWith("+")  || numb.startsWith("+.")
				|| numb.endsWith("-") || numb.startsWith("-.") || numb.contains("$#/")
				|| numb.contains(" ")) 
		{
			throw new RuntimeException();
		}
		
		numb = numb.trim();
		
		if (numb.endsWith(".")) {
			numb = numb.substring(0, numb.length() - 1);
		}
		
		if (Double.parseDouble(numb) > 0 || Double.parseDouble(numb) < 0) {
			String number = numb;
			int decimalindex = numb.length();
			
			if (numb.contains(".")) {
				decimalindex = numb.indexOf(".");
				for (int i = decimalindex + 1; i < numb.length(); i++) {
					String s = numb.substring(i, i + 1);
//					afterdec.add(Integer.parseInt(s));
					afterdec.add(s);
				}
			}
				
			for (int j = 0; j < decimalindex - 1; j++) {
				if (numb.startsWith("+") ) {
					String pos = numb.substring(0, 1);
					numb = numb.substring(1);
					beforedec.add(pos);
				}
				if (numb.startsWith("-")) {
					String neg = numb.substring(0, 1);
					numb = numb.substring(1);
//					beforedec.add(Integer.parseInt(neg));
					beforedec.add(neg);
				}
					String s = numb.substring(j , j + 1);
//					last = numb.substring(numb.length() - 1);
//					beforedec.add(Integer.parseInt(s));
					beforedec.add(s); 
			}
			
			if (beforedec.size() != decimalindex) {
//				beforedec.add(Integer.parseInt(numb.substring(decimalindex - 2, decimalindex - 1)));
				beforedec.add(numb.substring(decimalindex - 1, decimalindex));
			}
			
			if (Double.parseDouble(number) < 1  && Double.parseDouble(number) > 0
					|| Double.parseDouble(number) == 0) {
//				beforedec.add(0);
				beforedec.add("0");
			}
			
			int i = 0;
			
			if (beforedec.contains("-")) {
				while (beforedec.get(1).equals("0") && beforedec.size() > 2) { // && amusinglist.get(1) != '.'
					beforedec.remove(1);
				}
			} else if (beforedec.contains("+")) {
				while (beforedec.get(1).equals("0") && beforedec.size() > 2) { // && amusinglist.get(1) != '.'
					beforedec.remove(1);
				}
			} else {
				while (beforedec.get(0).equals("0") && beforedec.size() > 1) { // && amusinglist.get(1) != '.'
					beforedec.remove(0);
				}
			}
			
			if (afterdec.size() > 0) { 
				while (afterdec.get(afterdec.size() - 1).equals("0") && afterdec.size() > 1) {
					afterdec.remove(afterdec.size() - 1);
				}
			}
		} else {
			beforedec.add("0");
		}
		
	}
	
	/**
	 * The same as the string constructor except the input comes from arbitrary Reader.
	 * This means that there is no bound on the number of digits for this constructor.
	 * The format is similar to the String constructor
	 * except that a whitespace character is treated as a termination of the input
	 * and no further data is read from the stream once this whitespace character is read.
	 * Leading whitespace characters are ignored. 
	 * We will test that this constructor can handle at least 100,000 digits of precision.
	 * @param r
	 */
	public AmusingPreciseNumber(Reader r) {
		this(readerHelp(r));
	}
	
	/**
	 * reader constructor helper method
	 * @param r
	 * @return
	 */
	private static String readerHelp(Reader r) {
		String s = "";
		char [] c = new char [100000];
		int read = 0;
		try {
			read = r.read(c);
			r.close();
			if (read == 0) throw new IOException();
			for (int i = 0; i < read; i++) {
				s += c[i];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * A simple copy constructor.  It is required that this be a deep copy. 
	 * @param numb
	 */
	public AmusingPreciseNumber(AmusingPreciseNumber numb) {	
		this.beforedec.clear();
		this.afterdec.clear();
		
		for (int i = 0; i < numb.beforedec.size(); i++) {
			this.beforedec.add(numb.beforedec.get(i).toString());
		}
		
		for (int j = 0; j < numb.afterdec.size(); j++) {
			this.afterdec.add(numb.afterdec.get(j).toString());
		}
//		System.out.println(numb);
//		System.out.println(this.toString());
		
	}
	
	/**
	 * changes the amusing precise number into a string
	 */
	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < beforedec.size(); i++) {
			ret += beforedec.get(i);
		} 
		if (afterdec.size() > 0) ret += ".";
		for (int i = 0; i < afterdec.size(); i++) {
			ret += afterdec.get(i);
		}
		return ret;
	}
	
	/**
	 * Add numb to this AmusingPreciseNumber
	 * @param numb
	 */
	public void add(AmusingPreciseNumber numb) {
		int beforesize = 0;
		int aftersize = 0;
		String thissign = "";
		String numbsign = "";
		boolean doubleneg = false;
		boolean thisbigger = false;

		if (Double.parseDouble(this.toString()) > 0) {
			thissign = "+";
		} else if (Double.parseDouble(this.toString()) < 0) {
			thissign = "-";
		}
		if (Double.parseDouble(numb.toString()) > 0) {
			numbsign = "+";
		} else if (Double.parseDouble(numb.toString()) < 0) {
			numbsign = "-";
		}
		
		if (Double.parseDouble(this.toString()) > Double.parseDouble(numb.toString()) || 
				Double.parseDouble(this.toString()) <= Double.parseDouble(numb.toString()) && 
				Double.parseDouble(this.toString()) < 0 && Double.parseDouble(numb.toString()) < 0 ||
				Double.parseDouble(AmusingPreciseNumber.abs(this).toString()) 
				> Double.parseDouble(AmusingPreciseNumber.abs(numb).toString())) {
			thisbigger = true;
		}
		
		if (thissign.equals("+") && numbsign.equals("-")) {
			numb.negate();
			this.subtract(numb);
			return;
		} else if (thissign.equals("-") && numbsign.equals("+")) {
			this.negate();
			this.subtract(numb);
			if (thisbigger) this.beforedec.add(0, "-");
			return;
		} else if (thissign.equals("-") && numbsign.equals("-")) {
			this.negate();
			numb.negate();
			doubleneg = true;
			this.add(numb);
			if (doubleneg) this.beforedec.add(0, "-");
			return;
		} 
		
		// find if this or numb has bigger size and set those
		if (this.beforedec.size() > numb.beforedec.size()) {
			beforesize = this.beforedec.size();
		} else {
			beforesize = numb.beforedec.size();
		}
		
		if (this.afterdec.size() > numb.afterdec.size()) {
			aftersize = this.afterdec.size();
		} else {
			aftersize = numb.afterdec.size();
		}
		
		// while this/numb is smaller than the bigger one
		// add "0"s to the beginning of them
		while (this.afterdec.size() < aftersize) {
			this.afterdec.add("0");
		}
		while (numb.afterdec.size() < aftersize) {
			numb.afterdec.add("0");
		}
		
		while (this.beforedec.size() < beforesize) {
			this.beforedec.add(0, "0");
		}
		while (numb.beforedec.size() < beforesize) {
			numb.beforedec.add(0, "0");
		}
		
		int i = 0;
		while(i < aftersize) {
			if (i > this.afterdec.size() - 1) {
				this.afterdec.set(i, "0");
			} else if (i > numb.afterdec.size() - 1) {
				this.afterdec.set(i, "0");
			} else {
				int num = Integer.parseInt(this.afterdec.get(i)) + Integer.parseInt(numb.afterdec.get(i));
				if (num >= 10 && i == 0) {
					int add = Integer.parseInt(beforedec.get(beforedec.size() - 1)) + 1;
					String s = Integer.toString(add);
					int remainder = num - 10;
					String addrem = Integer.toString(remainder);
					this.beforedec.set(beforedec.size() - 1, s);
					this.afterdec.set(0, addrem);
				} else if (num >= 10) {
					int add = Integer.parseInt(afterdec.get(i - 1)) + 1;
					String s = Integer.toString(add);
					int remainder = num - 10;
					String addrem = Integer.toString(remainder);
					this.afterdec.set(i - 1, s);
					this.afterdec.set(i , addrem);
				} else {
					String add = Integer.toString(num);
					this.afterdec.set(i, add);
				}
			}
			i++;
		}
		
		int j = beforesize - 1;
		while (j >= 0) {
			if (j > this.beforedec.size()) {
				this.beforedec.set(j, numb.beforedec.get(j));
			} else if (j > numb.beforedec.size()) {
				this.beforedec.set(j, beforedec.get(j));
			} else {
				int num = Integer.parseInt(this.beforedec.get(j)) + Integer.parseInt(numb.beforedec.get(j));
				if (num >= 10 && j == 0) {
					int add = Integer.parseInt(this.beforedec.get(j));
					String s = Integer.toString(add);
					int remainder = num - 10;
					String addrem = Integer.toString(remainder);
					this.beforedec.set(j, addrem);
					this.beforedec.add(j, "1");
				} else if (num >= 10) {
					int add = Integer.parseInt(this.beforedec.get(j - 1)) + 1;
					String s = Integer.toString(add);
					int remainder = num - 10;
					String addrem = Integer.toString(remainder);
					this.beforedec.set(j - 1, s);
					this.beforedec.set(j, addrem);
				} else {
					String add = Integer.toString(num);
					this.beforedec.set(j, add);
				}
			}
			j--;
		}
		
		if (thisbigger && doubleneg) this.beforedec.add(0, "-");
		
		if (Double.parseDouble(this.toString()) == 0) {
			this.beforedec.clear();
			this.beforedec.add("0");
		}
		
	}
	
	/**
	 * Subtract numb from this AmusingPreciseNumber
	 * @param numb
	 */
	public void subtract(AmusingPreciseNumber numb) {
		int beforesize = 0;
		int aftersize = 0;
		String thissign = "";
		String numbsign = "";
		boolean doubleneg = false;
		boolean thisbigger = false;

		if (Double.parseDouble(this.toString()) > 0) {
			thissign = "+";
		} else if (Double.parseDouble(this.toString()) < 0){
			thissign = "-";
		}
		if (Double.parseDouble(numb.toString()) > 0) {
			numbsign = "+";
		} else if (Double.parseDouble(numb.toString()) < 0) {
			numbsign = "-";
		}
		
		if (Double.parseDouble(this.toString()) > Double.parseDouble(numb.toString()) || 
				Double.parseDouble(this.toString()) < Double.parseDouble(numb.toString()) && 
				Double.parseDouble(this.toString()) < 0 && Double.parseDouble(numb.toString()) < 0 ||
				Double.parseDouble(AmusingPreciseNumber.abs(this).toString()) 
				> Double.parseDouble(AmusingPreciseNumber.abs(numb).toString()) ||
				Double.parseDouble(this.toString()) < Double.parseDouble(numb.toString())) {
			thisbigger = true;
		}
		
		if (thissign.equals("+") && numbsign.equals("-")) {
			numb.negate();
			this.add(numb);
			return;
		} else if (thissign.equals("-") && numbsign.equals("+")) {
			this.negate();
			this.add(numb);
			if (thisbigger) this.beforedec.add(0, "-");
			return;
		} else if (thissign.equals("-") && numbsign.equals("-")) {
			this.negate();
			numb.negate();
			doubleneg = true;
			numb.subtract(this);
			this.beforedec = numb.beforedec;
			this.afterdec = numb.afterdec;
			return;
		} 
		
		if (this.beforedec.size() > numb.beforedec.size()) {
			beforesize = this.beforedec.size();
		} else {
			beforesize = numb.beforedec.size();
		}
		
		if (this.afterdec.size() > numb.afterdec.size()) {
			aftersize = this.afterdec.size();
		} else {
			aftersize = numb.afterdec.size();
		}
		
		while (this.afterdec.size() < aftersize) {
			this.afterdec.add("0");
		}
		while (numb.afterdec.size() < aftersize) {
			numb.afterdec.add("0");
		}
		
		while (this.beforedec.size() < beforesize) {
			this.beforedec.add(0, "0");
		}
		while (numb.beforedec.size() < beforesize) {
			numb.beforedec.add(0, "0");
		}
		
		int i = aftersize - 1;
		while (i >= 0) {			
			if (Integer.parseInt(this.afterdec.get(i)) < Integer.parseInt(numb.afterdec.get(i))) {
				int sub = Integer.parseInt(this.afterdec.get(i)) + 10;
				String s = Integer.toString(sub);
				int replace = Integer.parseInt(this.afterdec.get(i - 1)) - 1;
				String minten = Integer.toString(replace);
				this.afterdec.set(i, s);
				this.afterdec.set(i - 1, minten);
			} 
			int sub = Integer.parseInt(this.afterdec.get(i)) - Integer.parseInt(numb.afterdec.get(i));
			String s = Integer.toString(sub);
			this.afterdec.set(i, s);
			i--;
		}
		
		int j = beforesize - 1;
		while (j >= 0) {			
			if (j == 0 && Integer.parseInt(this.beforedec.get(j)) < Integer.parseInt(numb.beforedec.get(j))) {
				int sub = Integer.parseInt(this.beforedec.get(j)) + 10 - Integer.parseInt(this.beforedec.get(j + 1));
				String s = Integer.toString(sub);
				String replace = "1";
				this.beforedec.set(j + 1, s);
				this.beforedec.set(j, replace);
				
			} else if (Integer.parseInt(this.beforedec.get(j)) < Integer.parseInt(numb.beforedec.get(j))) {
				int sub = Integer.parseInt(this.beforedec.get(j)) + 10;
				String s = Integer.toString(sub);
				int replace = Integer.parseInt(this.beforedec.get(j - 1)) - 1;
				String minten = Integer.toString(replace);
				this.beforedec.set(j, s);
				this.beforedec.set(j - 1, minten);
			} 
			int sub = Integer.parseInt(this.beforedec.get(j)) - Integer.parseInt(numb.beforedec.get(j));
			String s = Integer.toString(sub);
			this.beforedec.set(j, s);
			j--;
		}
		
		if (thisbigger && doubleneg && !this.beforedec.get(0).contains("-")) {
			this.beforedec.add(0, "-");
		}
		
		if (Double.parseDouble(this.toString()) == 0) {
			this.beforedec.clear();
			this.beforedec.add("0");
		}
		
	}

	/**
	 * Negate this AmusingPreciseNumber
	 */
	public void negate() {
		if (this.beforedec.get(0).equals("-")) {
			this.beforedec.remove(0);
		} else if (this.beforedec.get(0).equals("+")){
			this.beforedec.set(0, "-");
		} else if (this.toString().equals("0")){
			return;
		} else {
			this.beforedec.add(0, "-");
		}
	}
	
	/**
	 * Compute and store the absolute value of this AmusingPreciseNumber
	 */
	public void abs() {
		if (Double.parseDouble(this.toString()) < 0) {
			this.negate();
		}
	}
	
	/**
	 * Return an AmusingPreciseNumber that is the sum of numb1 and numb2.  Numb1 and numb2 are unchanged.
	 * @param numb1
	 * @param numb2
	 * @return
	 */
	public static AmusingPreciseNumber add(AmusingPreciseNumber numb1, AmusingPreciseNumber numb2) {
		AmusingPreciseNumber num1 = new AmusingPreciseNumber(numb1);
		AmusingPreciseNumber num2 = new AmusingPreciseNumber(numb2);
		num1.add(num2);
		return num1;
	}

	/**
	 * Return an AmusingPreciseNumber that is the difference of numb1 and numb2 (numb1 minus numb2)  
	 * Numb1 and numb2 are unchanged
	 * @param numb1
	 * @param numb2
	 * @return
	 */
	public static AmusingPreciseNumber subtract(AmusingPreciseNumber numb1, AmusingPreciseNumber numb2) {
		AmusingPreciseNumber num1 = new AmusingPreciseNumber(numb1);
		AmusingPreciseNumber num2 = new AmusingPreciseNumber(numb2);
		num1.subtract(num2);
		return num1;
	}
	
	/**
	 * Return an AmusingPreciseNumber that is the negative of numb and leave numb unchanged.
	 * @param numb
	 * @return
	 */
	public static AmusingPreciseNumber negate(AmusingPreciseNumber numb) {
		AmusingPreciseNumber answer = new AmusingPreciseNumber(numb);
		answer.negate();
		return answer;
	}
	
	/**
	 * Return an AmusingPreciseNumber that is the absolute value of numb and leave numb unchanged.
	 * @param numb
	 * @return
	 */
	public static AmusingPreciseNumber abs(AmusingPreciseNumber numb) {
		AmusingPreciseNumber absnumb = new AmusingPreciseNumber(numb);
		if (Double.parseDouble(numb.toString()) > 0) {
			return absnumb;
		} else {
			absnumb = AmusingPreciseNumber.negate(numb);
			return absnumb;
		}
	}
	
}