package com.example.msmembre.service;


import com.example.msmembre.entities.Member;
import com.example.msmembre.entities.Student;
import com.example.msmembre.entities.TeacherResearcher;
import com.example.msmembre.repositories.MemberRepository;
import com.example.msmembre.repositories.StudentRepository;
import com.example.msmembre.repositories.TeacherResearcherRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class MemberImpl implements IMemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherResearcherRepository teacherResearcherRepository;
    @PersistenceContext
    EntityManager em;


    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }
    public Member addMember(Member m) {
        m.setCreatedDate(new Date());
        memberRepository.save(m);
        return m;
    }
//    public Member addMember(Member m, String cv, String photo) {
//        m.setCreatedDate(new Date());
//        m.setCv(cv);
//        m.setPhoto(photo);
//        memberRepository.save(m);
//        return m;
//    }

    public void deleteMember(Long id) {
        List<Student> students = findAllStudentsBySupervisor(id);
        if (students != null) {
            students.forEach(student -> student.setSupervisor(null));
        }
        memberRepository.deleteById(id);
    }

//    public Member updateMember(Member m,String cv, String photo) {
//        Member member = memberRepository.findById(m.getId()).get();
//        m.setCreatedDate(member.getCreatedDate());
//        m.setCv(cv);
//        m.setPhoto(photo);
//        return memberRepository.saveAndFlush(m);
//    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public List<Student> findByFirstNameAndLastNameAndCinAndDiploma(String firstName, String lastName, String cin, String diploma) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> teacher = cq.from(Student.class);

        if (firstName != null) {
            predicates.add(cb.like(teacher.get("firstName"), "%" + firstName + "%"));
        }
        if (lastName != null) {
            predicates.add(cb.like(teacher.get("lastName"), "%" + lastName + "%"));
        }
        if (cin != null) {
            predicates.add(cb.like(teacher.get("cin"), "%" + cin + "%"));
        }
        if (diploma != null) {
            predicates.add(cb.like(teacher.get("diploma"), "%" + diploma + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<TeacherResearcher> findByFirstNameAndLastNameAndCinAndEtablishmentAndGrade(String firstName, String lastName, String cin, String etablishment, String grade) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery<TeacherResearcher> cq = cb.createQuery(TeacherResearcher.class);
        Root<TeacherResearcher> teacher = cq.from(TeacherResearcher.class);
        if (firstName != null) {
            predicates.add(cb.like(teacher.get("firstName"), "%" + firstName + "%"));
        }
        if (lastName != null) {
            predicates.add(cb.like(teacher.get("lastName"), "%" + lastName + "%"));
        }
        if (cin != null) {
            predicates.add(cb.like(teacher.get("cin"), "%" + cin + "%"));
        }
        if (etablishment != null) {
            predicates.add(cb.like(teacher.get("etablishment"), "%" + etablishment + "%"));
        }
        if (grade != null) {
            predicates.add(cb.like(teacher.get("grade"), "%" + grade + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    public List<Student> findByDiploma(String diploma) {
        return studentRepository.findByDiploma(diploma);
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAllStudentsBySupervisor(Long id) {
        List<Student> studentsByMember = Lists.newArrayList();
        List<Student> students = findAllStudents();
        students.forEach(student -> {
            if (student.getSupervisor() != null && student.getSupervisor().getId().equals(id)) {
                studentsByMember.add(student);
            }
        });
        return studentsByMember;
    }

    @Override
    public List<Student> findStudentByInscriptionDateBetween(Date inscriptionDateGT, Date inscriptionDateLT) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> student = cq.from(Student.class);

        if (inscriptionDateGT != null && inscriptionDateLT != null) {
            predicates.add(cb.greaterThanOrEqualTo(student.get("inscriptionDate"), inscriptionDateGT));
            predicates.add(cb.lessThanOrEqualTo(student.get("inscriptionDate"), inscriptionDateLT));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }


    @Override
    public List<TeacherResearcher> findAllTeachers() {
        return teacherResearcherRepository.findAll();
    }

    @Override
    public Student affectSupervisorToStudent(Student student, Long idSupervisor) {
        TeacherResearcher supervisor = teacherResearcherRepository.findById(idSupervisor).get();
        student.setSupervisor(supervisor);
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudentsBySupervisorName(String name) {
        return studentRepository.findStudentBySupervisor_FirstNameContainingIgnoreCase(name);
    }


}
