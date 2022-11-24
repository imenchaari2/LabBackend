package com.example.msmembre.controller;

import com.example.msmembre.entities.Member;
import com.example.msmembre.entities.Student;
import com.example.msmembre.entities.TeacherResearcher;
import com.example.msmembre.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping(path = "/api/member")
@CrossOrigin("*")
public class MemberController {
    @Autowired
    IMemberService iMemberService;
    @GetMapping(value = "/members")
    public List<Member> findAllMembers() {
        return iMemberService.findAll();
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

    @PutMapping(value = "/affectSupervisorToStudent")
    public Member affectSupervisorToStudent(@RequestParam Long idStudent, @RequestParam Long idSupervisor) {
        return iMemberService.affectSupervisorToStudent(idStudent, idSupervisor);
    }

//	@GetMapping("/publications/{id}")
//	public EntityModel<PublicationBean> listerunepublication(@PathVariable Long id)
//	{
//		return publicationproxy.recupererUnePublication(id);
//
//	}
//	@GetMapping("/publications/auteur/{id}")
//	public List<PublicationBean>listerpublicationbymembre(@PathVariable(name="id") Long idaut)
//	{
//		return iMemberService.findPublicationparauteur(idaut);
//	}

//	@GetMapping("/fullmember/{id}")
//	public Membre findAFullMember(@PathVariable(name="id") Long id)
//	{
//		Membre mbr=iMemberService.findMemberById(id);
//		mbr.setPubs(iMemberService.findPublicationparauteur(id));
//		return mbr;
//	}


}
