package com.example.msmembre.service;


import com.example.msmembre.entities.Member;
import com.example.msmembre.entities.Student;
import com.example.msmembre.entities.TeacherResearcher;
import com.example.msmembre.repositories.MemberRepository;
import com.example.msmembre.repositories.StudentRepository;
import com.example.msmembre.repositories.TeacherResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemberImpl implements IMemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherResearcherRepository teacherResearcherRepository;

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member addMember(Member m) {
        m.setCreatedDate(new Date());
        memberRepository.save(m);
        return m;
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Member updateMember(Member m) {
        Member member = memberRepository.findById(m.getId()).get();
        m.setCreatedDate(member.getCreatedDate());
        return memberRepository.saveAndFlush(m);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findByCin(String cin) {
        return memberRepository.findByCin(cin);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> findByFirstName(String nom) {
        return memberRepository.findByFirstNameStartingWith(nom);
    }

    public List<Student> findByDiploma(String diploma) {
        return studentRepository.findByDiploma(diploma);
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }


    @Override
    public List<TeacherResearcher> findAllTeachers() {
        return teacherResearcherRepository.findAll();
    }

    @Override
    public List<TeacherResearcher> findByGrade(String grade) {

        return teacherResearcherRepository.findByGrade(grade);
    }

    @Override
    public List<TeacherResearcher> findByEtablishment(String etablishment) {

        return teacherResearcherRepository.findByEtablishment(etablishment);
    }

    @Override
    public Student affectSupervisorToStudent(Long idStudent, Long idSupervisor) {
        Student student = studentRepository.findById(idStudent).get();
        TeacherResearcher supervisor = teacherResearcherRepository.findById(idSupervisor).get();
        student.setSupervisor(supervisor);
        return studentRepository.save(student);
    }




//	@Override
//	public void affecterauteurTopublication(Long idauteur, Long idpub) {
//		Membre mbr= memberRepository.findById(idauteur).get();
//		Membre_Publication mbs= new Membre_Publication();
//		mbs.setAuteur(mbr);
//		mbs.setId(new Membre_Pub_Ids(idpub, idauteur));
//		membrepubrepository.save(mbs);
//	}


//	@Override
//	public List<PublicationBean> findPublicationparauteur(Long idauteur) {
//		List<PublicationBean> pubs=new ArrayList<PublicationBean>();
//
//		List< Membre_Publication> idpubs=membrepubrepository.findpubId(idauteur);
//
//		idpubs.forEach(s->{
//			System.out.println(s);
//			pubs.add(proxy.recupererUnePublication(s.getId().getPublication_id()).getContent());
//
//		}
//		);
//
//		return pubs;
//	}


}
