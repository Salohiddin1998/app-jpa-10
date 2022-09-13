package uz.pdp.appjpa10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjpa10.entity.Subject;


public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);

}
