package com.isep.tsn.dal.model.postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Subject {

    @Id
    String subjectName;

}
