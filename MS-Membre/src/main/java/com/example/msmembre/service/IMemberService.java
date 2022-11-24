package com.example.msmembre.service;

import com.example.msmembre.entities.Member;
import com.example.msmembre.entities.Student;
import com.example.msmembre.entities.TeacherResearcher;

import java.util.List;
import java.util.Optional;

public interface IMemberService {
    Member addMember(Member m);

    void deleteMember(Long id);

    Member updateMember(Member p);

    Optional<Member> findMemberById(Long id);

    List<Member> findAll();

    //Filtrage par propriété
    Member findByCin(String cin);
    Member findByEmail(String email);

    List<Member> findByFirstName(String firstName);

    //recherche spécifique des étudiants
    List<Student> findAllStudents();

    List<Student> findByDiploma(String diploma);

    //recherche spécifique des enseignants
    List<TeacherResearcher> findAllTeachers();

    List<TeacherResearcher> findByGrade(String grade);

    List<TeacherResearcher> findByEtablishment(String etablishment);

    //other ...

    Student affectSupervisorToStudent(Long idStudent, Long idSupervisor);


}
