package hw3;

import api.Instruction;
import api.NVPair;
import api.SymbolTable;
import hw3.CS227Asm;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class AsmFileUtil {
		
	/**
	 * Reads the given file and assembles the program,
	 * returning the machine code as a list of strings (including descriptions).
	 * @param filename
	 * @return
	 * @throws java.io.FileNotFoundException
	 */
	public static ArrayList<String>	assembleFromFile(String filename) throws java.io.FileNotFoundException {
		CS227Asm input;
		File f = new File(filename);
		Scanner s = new Scanner(f);
		ArrayList<String> lines = new ArrayList<String>();
		
		while(s.hasNextLine()) {
			String l = s.nextLine();
			lines.add(l);
		}
		
		input = new CS227Asm(lines);
		s.close();
		
		return input.assemble();
	}
	
	/**
	 * Reads the given file and assembles the program,
	 * returning the machine code as an array of integer values (not including the sentinel value).
	 * @param filename
	 * @return
	 * @throws java.io.FileNotFoundException
	 */
	public static int[] createMemoryImageFromFile(String filename) throws java.io.FileNotFoundException {
		int len = assembleFromFile(filename).size();
		int [] image = new int [len - 1];
		
		for (int i = 0; i< image.length; i++) {
			String line = assembleFromFile(filename).get(i);
			Scanner s = new Scanner(line);
			image[i] = s.nextInt();
			s.close();
		}

		return image;
	}
	
	/**
	 * Reads the given file and assembles the program, writing the machine code to a file.
	 * Descriptions are included only if the annotated parameter is true,
	 * otherwise each line in the output file includes just the first five characters
	 * (representing the integer value of the instruction).
	 * The name of the output file is the same as the name of the input file,
	 * with the file extension (portion after the last dot) removed and is replaced with ".mach227".
	 * For example, given the filename "test1.asm227", the output file would be named "test1.mach227".
	 * @param filename
	 * @param annotated
	 * @throws java.io.FileNotFoundException
	 */
	public static void assembleAndWriteFile(String filename, boolean annotated)  throws java.io.FileNotFoundException {
		if (!annotated) {
			String newf = filename.substring(0, filename.indexOf('.')) + ".mach227";
			File f = new File(newf);
			PrintWriter writer = new PrintWriter(f);
			
			for(int i = 0; i < createMemoryImageFromFile(filename).length; i++) {
				writer.printf("%+05d\n", createMemoryImageFromFile(filename));
			}
			
			writer.println("-99999");
			
			writer.close();
		} else {
			String newf = filename.substring(0,filename.indexOf('.')) + "mach227";
			File f = new File(newf);
			PrintWriter writer = new PrintWriter(f);
			
			for (int i = 0; i < assembleFromFile(filename).size(); i++) {
				writer.println(assembleFromFile(filename).get(i));
			}
			writer.close();
		}
	}
}
