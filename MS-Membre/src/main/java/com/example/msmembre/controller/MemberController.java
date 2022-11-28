package com.example.msmembre.controller;

import com.example.msmembre.entities.Member;
import com.example.msmembre.entities.Student;
import com.example.msmembre.entities.TeacherResearcher;
import com.example.msmembre.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;


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

    @PostMapping(value = "/addStudent", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})

    public Member addMemberStudent(@ModelAttribute("student") Student student,
                                   @RequestPart("cvFile") MultipartFile cvFile,
                                   @RequestPart("photoFile") MultipartFile photoFile
    ) throws IOException {
        student.setCreatedDate(Date.from(Instant.now()));
        student.setType("Student");
        String cvName = StringUtils.cleanPath(Objects.requireNonNull(cvFile.getOriginalFilename()));
        Path cvStorage = get("E:/gi3/lab-de-recherche/LabBackend/MS-Membre/cv/", cvName).toAbsolutePath().normalize();
        copy(cvFile.getInputStream(), cvStorage, REPLACE_EXISTING);
        String photoName = StringUtils.cleanPath(Objects.requireNonNull(photoFile.getOriginalFilename()));
        Path photoStorage = get("E:/gi3/lab-de-recherche/LabBackend/MS-Membre/photos/", photoName).toAbsolutePath().normalize();
        copy(photoFile.getInputStream(), photoStorage, REPLACE_EXISTING);
        return iMemberService.addMember(student, cvName, photoName);
    }

    @PostMapping(value = "/addTeacherResearcher", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})

    public Member addMemberTeacherResearcher(@ModelAttribute("teacher") TeacherResearcher teacherResearcher,
                                             @RequestParam("cvFile") MultipartFile cvFile,
                                             @RequestParam("photoFile") MultipartFile photoFile
    ) throws IOException {
        teacherResearcher.setCreatedDate(Date.from(Instant.now()));
        String cvName = StringUtils.cleanPath(Objects.requireNonNull(cvFile.getOriginalFilename()));
        Path cvStorage = get("E:/gi3/lab-de-recherche/LabBackend/MS-Membre/cvTeachers/", cvName).toAbsolutePath().normalize();
        copy(cvFile.getInputStream(), cvStorage, REPLACE_EXISTING);
        teacherResearcher.setCv(cvName);

        String photoName = StringUtils.cleanPath(Objects.requireNonNull(photoFile.getOriginalFilename()));
        Path photoStorage = get("E:/gi3/lab-de-recherche/LabBackend/MS-Membre/photosTeachers/", photoName).toAbsolutePath().normalize();
        copy(photoFile.getInputStream(), photoStorage, REPLACE_EXISTING);
        return iMemberService.addMember(teacherResearcher, cvName, photoName);
    }

    @PutMapping(value = "/updateStudent/{id}")
    public Member updateMember(@PathVariable Long id, Student student, @RequestParam("cvFile") MultipartFile cvFile,
                               @RequestParam("photoFile") MultipartFile photoFile) throws IOException {
        student.setId(id);
        String cvName = StringUtils.cleanPath(Objects.requireNonNull(cvFile.getOriginalFilename()));
        Path cvStorage = get("E:/gi3/lab-de-recherche/LabBackend/MS-Membre/cvTeachers/", cvName).toAbsolutePath().normalize();
        copy(cvFile.getInputStream(), cvStorage, REPLACE_EXISTING);
        String photoName = StringUtils.cleanPath(Objects.requireNonNull(photoFile.getOriginalFilename()));
        Path photoStorage = get("E:/gi3/lab-de-recherche/LabBackend/MS-Membre/photosTeachers/", photoName).toAbsolutePath().normalize();
        copy(photoFile.getInputStream(), photoStorage, REPLACE_EXISTING);
        return iMemberService.updateMember(student,cvName, photoName);
    }

    @DeleteMapping(value = "/deleteMember/{id}")
    public void deleteMember(@PathVariable Long id) {
        iMemberService.deleteMember(id);

    }

//    @PutMapping(value = "/updateTeacherResearcher/{id}")
//    public Member updateMembre(@PathVariable Long id, @RequestBody TeacherResearcher teacherResearcher) {
//        teacherResearcher.setId(id);
//        return iMemberService.updateMember(teacherResearcher);
//    }

    @PutMapping(value = "/affectSupervisorToStudent/{idSupervisor}")
    public Member affectSupervisorToStudent(@RequestBody Student student, @PathVariable Long idSupervisor) {
        return iMemberService.affectSupervisorToStudent(student, idSupervisor);
    }

    @GetMapping("/findByInscriptionDatePeriod")
    public List<Student> findStudentByInscriptionDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inscriptionDateGT,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inscriptionDateLT) {
        return iMemberService.findStudentByInscriptionDateBetween(inscriptionDateGT, inscriptionDateLT);

    }

    @GetMapping("/studentsBySupervisorName")
    public List<Student> getAllStudentsBySupervisorName(@RequestParam String name) {
        return iMemberService.getAllStudentsBySupervisorName(name);
    }

    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(filename)
                .toUriString();
        Path filePath = get("E:/gi3/lab-de-recherche/LabBackend/MS-Membre/Downloads/").toAbsolutePath().normalize().resolve(filename);
        System.out.println(fileDownloadUri);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }


}
