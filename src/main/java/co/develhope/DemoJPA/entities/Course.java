package co.develhope.DemoJPA.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Questa classe rappresenta un entità.
 * - Con l'annotazione @Entity informiamo Spring di questo fatto
 * - Con l'annotazione @Table specifichiamo il nome della tabella sul db
 *
 * Tutte le entità devono avere:
 * - una chiave primaria (@Id)
 * - costruttore vuoto (e ci fa comodo anche un costruttore parametrico)
 * - getter e setter per tutte le variabili (obbligatori!!)
 *
 */

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue
    private int id;
    private String courseName;
    private String tutorName;


    // Relazione tra Studenti e Corsi
    // Ogni corso ha tanti studenti
    @OneToMany(mappedBy = "course")
    @JsonIgnore // a cosa serve? provate a toglierla e vedere cosa succede
    private List<Student> students; // Potrei anche usare un Set (non voglio duplicati)


    // COSTRUTTORI

    public Course() {
    }

    public Course(String coruseName, String tutorName) {
        this.courseName = coruseName;
        this.tutorName = tutorName;
    }


    // GETTER E SETTER (e addStudent per aggiungere uno studente alla lista)

    public void addStudent(Student s) {
        students.add(s);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
