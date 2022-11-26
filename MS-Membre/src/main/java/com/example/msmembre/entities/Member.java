package com.example.msmembre.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name= "type_mbr", discriminatorType = DiscriminatorType.STRING,length = 3)
public abstract class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @Transient
    private String fullName;
    private String type;
    @NonNull
    private String cin;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String cv;
    @Temporal(TemporalType.DATE)
    @NonNull
    private Date birthDate;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Lob
    private byte[] photo;
//    @Transient
//    List<ArticleBean> articles;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
