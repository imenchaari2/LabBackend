package com.example.msmembre.controller;

import com.example.msmembre.entities.Member;
import com.example.msmembre.entities.Student;
import com.example.msmembre.entities.TeacherResearcher;
import com.example.msmembre.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping(path = "/api/member")
@CrossOrigin()
public class MemberController {
    @Autowired
    IMemberService iMemberService;

    @GetMapping(value = "/members")
    public List<Member> findAllMembers() {
        return iMemberService.findAll();
    }

    @GetMapping("/findStudentBySearch")
    public List<Student> findByFirstNameOrLastName(@RequestParam String firstName,
                                                   @RequestParam String lastName,
                                                   @RequestParam String cin,
                                                   @RequestParam String diploma) {
        return iMemberService.findByFirstNameAndLastNameAndCinAndDiploma(firstName, lastName, cin, diploma);
    }

    @GetMapping("/findTeacherBySearch")
    public List<TeacherResearcher> findByFirstNameAndLastNameAndCinAndEtablishmentAndGrade(@RequestParam String firstName,
                                                                                           @RequestParam String lastName,
                                                                                           @RequestParam String cin,
                                                                                           @RequestParam String etablishment,
                                                                                           @RequestParam String grade
    ) {
        return iMemberService.findByFirstNameAndLastNameAndCinAndEtablishmentAndGrade(firstName, lastName, cin, etablishment, grade);
    }

    @GetMapping(value = "/students")
    public List<Student> findAllStudents() {
        return iMemberService.findAllStudents();
    }

    @GetMapping(value = "/teachers")
    public List<TeacherResearcher> findAllTeachers() {
        return iMemberService.findAllTeachers();
    }

    @GetMapping(value = "/member/{id}")
    public Optional<Member> findMemberById(@PathVariable Long id) {
        return iMemberService.findMemberById(id);
    }

    @PostMapping(value = "/addStudent")
    public Member addMemberStudent(@RequestBody Student student) {
        student.setCreatedDate(Date.from(Instant.now()));
        student.setType("Student");
        return iMemberService.addMember(student);
    }

    @PostMapping(value = "/addTeacherResearcher")
    public Member addMemberTeacherResearcher(@RequestBody TeacherResearcher teacherResearcher) {
        teacherResearcher.setCreatedDate(Date.from(Instant.now()));
        teacherResearcher.setType("TeacherResearcher");

        return iMemberService.addMember(teacherResearcher);
    }

    @PutMapping(value = "/updateStudent/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return iMemberService.updateMember(student);
    }

    @DeleteMapping(value = "/deleteMember/{id}")
    public void deleteMember(@PathVariable Long id) {
        iMemberService.deleteMember(id);

    }

    @PutMapping(value = "/updateTeacherResearcher/{id}")
    public Member updateMembre(@PathVariable Long id, @RequestBody TeacherResearcher teacherResearcher) {
        teacherResearcher.setId(id);
        return iMemberService.updateMember(teacherResearcher);
    }

    @PutMapping(value = "/affectSupervisorToStudent/{idSupervisor}")
    public Member affectSupervisorToStudent(@RequestBody Student student ,@PathVariable Long idSupervisor) {
        return iMemberService.affectSupervisorToStudent(student, idSupervisor);
    }

    @GetMapping("/findByInscriptionDatePeriod")
    public List<Student> findStudentByInscriptionDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inscriptionDateGT,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inscriptionDateLT) {
        return iMemberService.findStudentByInscriptionDateBetween(inscriptionDateGT, inscriptionDateLT);

    }

    @GetMapping("/studentsBySupervisorName")
    public List<Student> getAllStudentsBySupervisorName(@RequestParam String name)
        {
            return iMemberService.getAllStudentsBySupervisorName(name);
        }


    }
