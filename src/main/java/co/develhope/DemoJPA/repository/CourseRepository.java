package co.develhope.DemoJPA.repository;

import co.develhope.DemoJPA.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    /*
    Abbiamo dichiarato un metodo per cercare un corso tramite nome.
    Ma come funziona questo metodo? Dove è implementato?
    Spring implementa AUTOMATICAMENTE questi metodi, leggendo il nome (che è scritto secondo regole ben precise)
    e creando le query necessarie (in questo caso "select * from courses where name = ?").
    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details
     */
    Optional<Course> findByCourseName(String name);

}
