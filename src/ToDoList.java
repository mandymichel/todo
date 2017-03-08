import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.joda.time.LocalDate;

public class ToDoList {

	public static String readFile(String classlist) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(new File(classlist)));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (IOException io) {
			throw new RuntimeException(io);
		} finally {
			try {
				br.close();
			} catch (IOException io) {

			}
		}
	}

	public static void writeToFile(String name, String classlist) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(new File(classlist)));
			pw.println(name);
		} catch (IOException io) {
			throw new RuntimeException(io);
		} finally {
			pw.close();
		}
	}

	public static String joinWords(List<String> toDoArrayList) {

		StringBuilder sb = new StringBuilder();
		for (String s : toDoArrayList) {
			sb.append(s);
			sb.append("\n");
		}

		String joinWords = sb.toString();

		return joinWords;
	}

	public String getDate() {
		LocalDate localDate = new LocalDate();
		out.println("localDate : " + localDate.toString());
		String listDate = localDate.toString();
		return listDate;
	}

	/*
	 * public void getFiles() { File folder = new
	 * File("/Users/mandymichel/Documents/workspace/ToDoList"); File[]
	 * listOfFiles = folder.listFiles(); out.println("Files: "); for (int i = 0;
	 * i < listOfFiles.length; i++) { if (listOfFiles[i].isFile()) {
	 * out.println(listOfFiles[i].getName()); } } }
	 */

	public void getFiles() {

		List<String> results = new ArrayList<String>();

		File[] files = new File("/Users/mandymichel/Documents/workspace/ToDoList").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		// If this pathname does not denote a directory, then listFiles()
		// returns null.

		for (File file : files) {
			if (file.isFile()) {
				results.add(file.getName());
			}
		}
		out.println("Files: ");
		for (int i = 0; i < results.size(); i++) {
			out.println(results.get(i));
		}
	}

	public void updateToDo() {
		Scanner keyboard = new Scanner(System.in);
		String toDoListFile = null;
		getFiles();
		out.println("Type the name of the file you want to update(omit .txt): ");
		toDoListFile = keyboard.nextLine();
		StringBuilder sb = new StringBuilder();
		String printedList = readFile(toDoListFile + ".txt");
		String eachline = null;
		String completed = null;
		String[] strArray = null;
		List<String> strList = null;
		strArray = printedList.split("\n");
		strList = Arrays.asList(strArray);

		Scanner inputScanner = new Scanner(printedList);
		// Go through each line in the txt file and print it.
		while (inputScanner.hasNext()) {
			eachline = inputScanner.nextLine();
			if (!eachline.equals(strList.get(0))) {
				out.println(eachline);
				do {
					out.println("Type c for complete and i for incomplete.");
					completed = keyboard.nextLine();
				} while ((!"c".equals(completed)) && (!"i".equals(completed)));
			}
			sb.append(eachline);
			sb.append("\t");
			if (completed != null) {
				sb.append(completed);
			}
			sb.append("\n");
		}

		String toDoCompleted = sb.toString();

		writeToFile(toDoCompleted, toDoListFile + ".txt");
		String report = readFile(toDoListFile + ".txt");
		out.println(report);
	}

	public void makeToDo() {
		Scanner keyboard = new Scanner(System.in);
		String toDoListFile = null;
		String item = null;
		String toDoList = null;
		List<String> toDoArrayList = new ArrayList<String>();
		String listDate = null;

		out.println("To Do List");
		out.println("Please enter the file name to save to: ");
		toDoListFile = keyboard.nextLine();
		listDate = getDate();
		toDoArrayList.add(listDate);
		do {
			out.println("Item: ");
			if (!"quit".equals(item)) {
				item = keyboard.nextLine();
			}
			toDoArrayList.add(item);
		} while (!"quit".equals(item));// constant before variable
		toDoArrayList.remove("quit");
		toDoList = joinWords(toDoArrayList);
		writeToFile(toDoList, toDoListFile + ".txt");
		out.println("To Do List:");
		String printedList = readFile(toDoListFile + ".txt");
		out.println(printedList);
	}

	public void printMenu() {
		out.println("To Do List Menu");
		out.println("1. Make a new list.");
		out.println("2. Update an existing list.");
		out.println("3. Exit");
	}

	public void run() {
		Scanner keyboard = new Scanner(System.in);
		int menuchoice;
		do {
			printMenu();
			menuchoice = keyboard.nextInt();
			switch (menuchoice) {
			case 1:
				makeToDo();
				break;
			case 2:
				updateToDo();
				break;
			}
		} while (menuchoice != 3);
		out.println("Goodbye!");
	}

	public static void main(String[] args) {
		ToDoList todo = new ToDoList();
		todo.run();
	}

}
