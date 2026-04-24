package br.com.pradolabs.cruddemo;

import br.com.pradolabs.cruddemo.dao.StudentDAO;
import br.com.pradolabs.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {

		return runner -> {
			long inicio = System.currentTimeMillis();

			// createStudent(studentDAO);
			createMultipleStudents(studentDAO);
			// readStudent(studentDAO);
			// queryForSudents(studentDAO);
			// queryForSudentsByLastName(studentDAO);
			// updateStudent(studentDAO);
			// deleteStudent(studentDAO);
			// deleteAllStudents(studentDAO);

			long fim = System.currentTimeMillis();

			System.out.println("\n -------------- \nTempo de execução: " + (fim - inicio) + " ms");
		};
	}

    private void deleteAllStudents(StudentDAO studentDAO) {
        System.out.println("Deleting all students...");
		int numRowsDeleted = studentDAO.deleteAll();
		System.out.println("Deleted row count: " + numRowsDeleted);
    }

	private void deleteStudent(StudentDAO studentDAO) {
		int studentId = 4;
		System.out.println("Deleting student id: " + studentId);
		studentDAO.delete(studentId);
	}

	private void updateStudent(StudentDAO studentDAO) {

		// retrieve student based on the id: primary key
		 int studentId = 5;
		 System.out.println("Retrieving student with id: " + studentId);
		 Student myStudent = studentDAO.findById(studentId);

		 System.out.println("Found the student: " + myStudent);
		// change first name to "Scooby"
		myStudent.setFirstName("John");

		// update the student
		studentDAO.update(myStudent);

		// display the update student
		System.out.println("Updated student: " + myStudent);
	}

	private void queryForSudentsByLastName(StudentDAO studentDAO) {
		List<Student> theStudents = studentDAO.findByLastName("Dom");
		theStudents.forEach(System.out::println);
	}

	private void queryForSudents(StudentDAO studentDAO) {
		List<Student> students = studentDAO.findAll();
		students.stream().forEach(System.out::println);
	}

	private void createStudent(StudentDAO studentDAO) {
        System.out.println("Creating new student object...");
		var tempStudent = new Student("Mary", "Jane", "mj@pradolabs.com.br");

		System.out.println("Saving the student...");
		studentDAO.save(tempStudent);

		System.out.println("Saved student. Generated id: " + tempStudent.getId());
    }

	private void createMultipleStudents(StudentDAO studentDAO) {
		System.out.println("Creating multiple students...");
		var tempStudent1 = new Student("Sharon", "Jane", "sj@pradolabs.com.br");
		var tempStudent2 = new Student("Bruno", "Mars", "bm@pradolabs.com.br");
		var tempStudent3 = new Student("Shadow", "Dom", "sd@pradolabs.com.br");

		System.out.println("Saving the students...");
		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);
	}

	private void readStudent(StudentDAO studentDAO) {

		// create  a student object
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("Daffy", "Duck", "dd@pradolabs.com.br");

		// save the student
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent);

		// display id of the saved student
		int theId = tempStudent.getId();
		System.out.println("Saved student. Generated id: " + theId);

		// retrieve student based on the id: primary key
		System.out.println("Retrieving student with id: " + theId);
		Student myStudent = studentDAO.findById(theId);

		// display student
		System.out.println("Found the student: " + myStudent);
	}
}
