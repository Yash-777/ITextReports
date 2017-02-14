package com.github.sample.reports;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yashwanth.m
 *
 */
public class CsvFileWriter {
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "id,firstName,lastName,gender,age";

	public static void main(String[] args) {
		String fileName = "D:/tempsv.csv";
		writeCsvFile(fileName);
	}
	public static void writeCsvFile(String fileName) {
		
		//Create new students objects
		Student student1 = new Student(1, "Yashwanth", "M", "M", 25);
		Student student2 = new Student(2, "Sri", "S", "F", 23);
		Student student3 = new Student(3, "Ali", "MD", "M", 24);
		
		//Create a new list of student objects
		List<Student> students = new ArrayList<Student>();
		students.add(student1);
		students.add(student2);
		students.add(student3);
		
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new student object list to the CSV file
			for (Student student : students) {
				fileWriter.append(String.valueOf(student.getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getFirstName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getLastName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getGender());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getAge()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
			
		}
	}
}

class Student {
	
	private long id;
	private String firstName;
	private String lastName;
	private String gender;
	private int age;

	public Student(long id, String firstName, String lastName, String gender,
			int age) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", gender=" + gender + ", age="
				+ age + "]";
	}
}
