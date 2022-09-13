package uz.pdp.appjpa10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjpa10.entity.Address;
import uz.pdp.appjpa10.entity.Group;
import uz.pdp.appjpa10.entity.Student;
import uz.pdp.appjpa10.entity.Subject;
import uz.pdp.appjpa10.payload.StudentDto;
import uz.pdp.appjpa10.repository.AddressRepository;
import uz.pdp.appjpa10.repository.GroupRepository;
import uz.pdp.appjpa10.repository.StudentRepository;
import uz.pdp.appjpa10.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAll(pageable);
    }

    //2. UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
    }

    //3. FACULTY DEKANAT

    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer facultyId,
                                                     @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_Faculty_Id(facultyId, pageable);
    }
    //4. GROUP OWNER

    @GetMapping("/forGroupOwner/{groupId}")
    public Page<Student> getStudentListForGroup(@PathVariable Integer groupId,
                                                  @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroupId(groupId, pageable);
    }

    @GetMapping("{id}")
    public Student get(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElseGet(Student::new);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            studentRepository.deleteById(id);
            return "Student deleted";
        }
        return "Student not found";
    }

    @PostMapping
    public String add(@RequestBody StudentDto studentDto){
        Student student = new Student();

        Address address = new Address();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address saveAddress = addressRepository.save(address);
        student.setAddress(saveAddress);
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isEmpty()){
            return "Group not found";
        }
        student.setGroup(optionalGroup.get());
        List<Integer> subjects = studentDto.getSubjects();
        List<Subject> studentSubjects = student.getSubjects();
        for (Integer subject : subjects) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subject);
            if (optionalSubject.isPresent()){
                Subject subject1 = optionalSubject.get();
                studentSubjects.add(subject1);
            }
        }
        student.setSubjects(studentSubjects);
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        studentRepository.save(student);
        return "Student added";

    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestBody StudentDto studentDto){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()){
            return "Student not found";
        }
        Student student = optionalStudent.get();
        Address address = new Address();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address saveAddress = addressRepository.save(address);
        student.setAddress(saveAddress);
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isEmpty()){
            return "Group not found";
        }
        student.setGroup(optionalGroup.get());
        List<Integer> subjects = studentDto.getSubjects();
        List<Subject> studentSubjects = student.getSubjects();
        for (Integer subject : subjects) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subject);
            if (optionalSubject.isPresent()){
                Subject subject1 = optionalSubject.get();
                studentSubjects.add(subject1);
            }
        }
        student.setSubjects(studentSubjects);
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        studentRepository.save(student);
        return "Student updated";


    }











}
