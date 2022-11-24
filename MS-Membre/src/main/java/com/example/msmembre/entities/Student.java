package com.example.msmembre.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("std")
@EqualsAndHashCode(callSuper = true)
public class Student extends Member implements Serializable {
    @Temporal(TemporalType.DATE)
    private Date inscriptionDate;
    private String diploma;
    @ManyToOne
    private TeacherResearcher supervisor;
}
