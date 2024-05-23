package com.isep.tsn.dal.postgres.repository;

import com.isep.tsn.dal.model.postgres.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{

}
