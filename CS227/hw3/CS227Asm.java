package hw3;

import api.Instruction;
import api.NVPair;
import api.SymbolTable;
import hw3.AsmFileUtil;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class CS227Asm {
	
	private SymbolTable data;
	private SymbolTable labels;
	private ArrayList<Instruction>  instructionStream;
	private ArrayList<String> assembler = new ArrayList<String>();
	private ArrayList<String> machineCode = new ArrayList<String>();
	private ArrayList<String> addresses = new ArrayList<String>();
	private int i = 1;
	
	/**
	 * Constructs an assembler for the given assembly language program,
	 * given as an ArrayList of strings (one per line of the program)
	 * Initially both symbol tables and the instruction stream are empty
	 * @param program
	 */
	public CS227Asm(ArrayList<String> program) {
		assembler = program;
		data = new SymbolTable();
		labels = new SymbolTable();
		instructionStream = new ArrayList<Instruction>();
	}
	
	/**
	 * Returns the symbol table for data (variables).
	 * @return
	 */
	public SymbolTable getData() {
		return data;
	}
	
	/**
	 * Returns the instruction stream. 
	 * The index of each instruction in the list is its memory location in the generated code.
	 * @return
	 */
	public java.util.ArrayList<Instruction> getInstructionStream() {
		return instructionStream;
	}
	
	/**
	 * Returns the symbol table for labels (jump targets).
	 * @return
	 */
	public SymbolTable getLabels() {
		return labels;
	}
	
	/**
	 * Creates the symbol table for the data section of this assembler's program.
	 */
	public void parseData() {		
		Scanner s = new Scanner(assembler.get(i));
		while (!assembler.get(i).equals("labels:")) {
			s = new Scanner(assembler.get(i));
			String name = s.next();
			int num = s.nextInt();
			data.add(name, num);
			i++;
		}
		i++;
	}
	
	/**
	 * Creates the symbol table for the label section of this assembler's program,
	 * leaving all label values as zeros.
	 */
	public void parseLabels() {		
		Scanner s = new Scanner(assembler.get(i));
		while (!assembler.get(i).equals("instructions:")) {
			s = new Scanner(assembler.get(i));
			String name = s.next();
			labels.add(name);
			i++;
		}
		i++;
	}
	
	/**
	 * Creates instruction stream from the instruction section of this assembler's program and 
	 * fills in label addresses in the symbol table for labels.
	 */
	public void parseInstructions() {
		int index = data.size() + labels.size() + 2;
		int numInstr = 0;
		if (assembler.get(index).equals("instructions:")){
			for (int i = index + 1; i < assembler.size(); i++) {
				String line = assembler.get(i);
				Scanner s = new Scanner(line);
				if (s.hasNext()) {
					String nextInstr = s.next();
					if (labels.containsName(nextInstr)) {
						labels.findByName(nextInstr).setValue(numInstr);
					} else {
						instructionStream.add(new Instruction(line));
						addresses.add(nextInstr);
						numInstr++;
					}
				}
			}
		}
		addAddresses();
//		System.out.println(instructionStream);
	}
	
	/**
	 * Helper
	 */
	private void addAddresses() {
		for (int i = 0; i < data.size(); i++) {
			addresses.add(data.getByIndex(i).getName().toString());
		}
		
		for (int i = 0; i < labels.size(); i++) {
			addresses.add(labels.getByIndex(i).getName().toString());
		}
	}
	
	/**
	 * Fills in the correct operand value for all instructions
	 * (either a data address or jump target address, depending on the instruction)
	 */
	public void setOperandValues() {		

		for (int i = 0; i < this.i; i++) {
			if (instructionStream.get(i).requiresDataAddress()) {
				instructionStream.get(i).setOperand(addresses.indexOf(instructionStream.get(i).getOperandString()));
//				System.out.println(addresses.indexOf(instructionStream.get(i).getOperandString()));
			} else if (instructionStream.get(i).requiresJumpTarget()) {
				if (data.containsName(instructionStream.get(i).getOperandString())) {
					instructionStream.get(i).setOperand(data.findByName(instructionStream.get(i).getOperandString()).getValue());
				} else if (labels.containsName(instructionStream.get(i).getOperandString())) {
					instructionStream.get(i).setOperand(labels.findByName(instructionStream.get(i).getOperandString()).getValue());
				}
			}
		}
	}
	
	/**
	 * For each instruction in the instruction stream that is a jump target,
	 * adds the label to the instruction's description.
	 * (See the method addLabelToDescription in the Instruction class.)
	 */
	public void addLabelAnnotations() {
		for (int i = 0; i < labels.size(); i++) {
				instructionStream.get(labels.getByIndex(i).getValue()).addLabelToDescription(labels.getByIndex(i).getName());
		}
	}
	
	/**
	 * Generates the machine code and data for this assembler's program,
	 * terminated by the string "-99999".
	 * Strings for instructions are produced by the Instruction method toString,
	 * and strings for data have the value formatted as a four-digit signed integer,
	 * followed by a space, followed by the variable name.
	 * @return
	 */
	public ArrayList<String> writeCode() {
		for (int i = 0; i < instructionStream.size(); i++) {
			machineCode.add(instructionStream.get(i).toString());
		}
				
		for (int i = 0; i < data.size(); i++) {
			int val = data.getByIndex(i).getValue();
			String name = data.getByIndex(i).getName();
			String value = String.format("%+05d ", val);
			String both = value + name;
			machineCode.add(both);
		}
		
		machineCode.add("-99999");
		
		return machineCode;
	}
	
	/**
	 * Assembles the source program represented by this assembler instance and 
	 * returns the generated machine code and data as an array of strings
	 * @return writeCode();
	 */
	public ArrayList<String> assemble() {
		parseData();
		parseLabels();
		parseInstructions();
		setOperandValues();
		addLabelAnnotations();
		return writeCode();
	}
}
