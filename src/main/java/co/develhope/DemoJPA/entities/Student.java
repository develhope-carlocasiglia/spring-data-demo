package co.develhope.DemoJPA.entities;

import javax.persistence.*;

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
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String surname;


    // Gli studenti hanno un corso; i corsi hanno molti studenti.
    // Si tratta di una relazione ManyToOne, e realizziamo questa connessione
    // con una colonna di join che chiameremo "course_name".
    @ManyToOne
    @JoinColumn(name = "course_name")
    private Course course;



    public Student() {
    }

    public Student(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
