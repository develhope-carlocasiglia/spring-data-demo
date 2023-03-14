package co.develhope.DemoJPA.controller;

import co.develhope.DemoJPA.entities.Course;
import co.develhope.DemoJPA.entities.Student;
import co.develhope.DemoJPA.repository.CourseRepository;
import co.develhope.DemoJPA.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    /**
     *     --- INVERSION of CONTROL e DEPENDENCY INJECTION ------------------------------------------------------------
     *
     *     Dobbiamo creare un oggetto che implementa l'interfaccia StudentRepository
     *     e che implementa i metodi di StudentRepository (cioè quelli ereditati da JpaRepository)
     *
     *     Invece che farlo noi, lo facciamo fare a Spring: gli CEDIAMO IL CONTROLLO sulla creazione degli oggetti
     *
     *     Spring crea un oggetto che implementa JpaRepsitory e scrive automaticamente tutti i metodi necessari
     *     per fare operazioni sul database.
     *
     *     Dopodiché dobbiamo chiedere a Spring di prendere questo oggetto e darcelo, in modo che possiamo usarlo.
     *     Gli chiediamo, in sostanza, di INIETTARLO dentro alla variabile studentRepository.
     *
     *     Si fa semplicemente con l'annotazione @Autowired ("Collegato automaticamente").
     */
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    /**
     * ----------------------------------------------------------------------------------------------------------------
     */



    /**
     * Metodo per aggiungere un nuovo corso.
     * Prende due parametri in ingresso e li usa per instanziare un oggetto di tipo Course,
     * dopodiché chiede all'oggetto contenuto dentro courseRepository di salvarlo sul DB.
     * Noi non sappiamo come sia fatto questo oggetto, lo ha creato Spring e noi non ne abbiamo il controllo,
     * però conosciamo i suoi metodi perché implementa un interfaccia a noi nota (CourseRepository).
     */
    @PostMapping("/add-course")
    public void insertNewCourse(@RequestParam String courseName, @RequestParam String tutorName) {
        Course course = new Course(courseName, tutorName);
        courseRepository.save(course);
    }



    /**
     * Metodo per aggiungere un nuovo studente.
     * Lo studente che ci arriva in formato JSON viene convertito in un oggetto di tipo Student.
     * Questo oggetto non ha un id, e non ha un corso: queste variabili verranno settate successivamente.
     */
    @PostMapping("/add-student")
    public Student addStudent(@RequestBody Student student, @RequestParam String courseName) {
        // Per prima cosa dobbiamo cercare il corso indicato e aggiungerlo dentro la variabile course.
        // Usiamo courseRepository per cercarlo TRAMITE NOME, grazie ad un metodo appositamente creato
        // (vedi dentro CourseRepository, c'è la spiegazione)
        // Siccome potremmo non trovarlo (es. se il nome è scritto male) mettiamo il risultato dentro un optional.
        Optional<Course> optionalCourse = courseRepository.findByCourseName(courseName);
        if (optionalCourse.isPresent()) { // se lo abbiamo effettivamente trovato, procediamo:
            Course course = optionalCourse.get(); // prendiamo l'oggetto corso da dentro l'optional
            course.addStudent(student); // aggiungiamo lo studente alla lista di studenti del corso
            courseRepository.save(course); // avendo modificato il corso con l'aggiunta di uno studente, lo salviamo
            student.setCourse(course); // settiamo la variabile course dello studente con il corso appena trovato
        }
        return studentRepository.save(student); // salviamo il nuovo studente, ora completo, nel DB
        // l'operazione di salvataggio si occupa anche di generare automaticamente l'id per questo studente
    }



    /**
     * Metodo per vedere la lista completa di tutti gli studenti
     */
    @GetMapping("/get-students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // select * from students
    }

}
