package application.backend.school.services;

//DEMO

import application.backend.common.enums.Role;
import application.backend.common.enums.Status;
import application.backend.registration.models.RegistrationData;
import application.backend.registration.services.RegisterService;
//DEMO

import application.backend.school.models.*;
import application.backend.school.repositories.*;

//DEMO
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
//DEMO

//DEMO
import java.util.Set;
//DEMO

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitSchoolDataLoader implements CommandLineRunner {

    private final TrackRepo trackRepo;
    private final StrandRepo strandRepo;
    private final SpecializationRepo specializationRepo;
    private final SectionRepo sectionRepo;
    private final DepartmentRepo departmentRepo;
    private final SubjectRepo subjectRepo;

    private final RegisterService registerService;

    public InitSchoolDataLoader(TrackRepo trackRepo,
                                StrandRepo strandRepo,
                                SpecializationRepo specializationRepo,
                                SectionRepo sectionRepo,
                                DepartmentRepo departmentRepo,
                                SubjectRepo subjectRepo,

                                RegisterService registerService) {
        this.trackRepo = trackRepo;
        this.strandRepo = strandRepo;
        this.specializationRepo = specializationRepo;
        this.sectionRepo = sectionRepo;
        this.departmentRepo = departmentRepo;
        this.subjectRepo = subjectRepo;

        //DEMO
        this.registerService = registerService;
        //DEMO
    }

    @Override
    public void run(String... args) throws Exception {
        loadTracks();
        loadStrands();
        loadSpecializations();
        loadSections();
        loadDepartments();
        loadSubjects();

        //DEMO
        loadDemoStudent();
        loadDemoTeachers();
        //DEMO
    }

    private void loadTracks() {
        trackRepo.save(new Track("Academic"));
        trackRepo.save(new Track("Technical-Vocational-Livelihood (TVL)"));
    }

    private void loadStrands() {
        Track tvl = trackRepo.findByName("Technical-Vocational-Livelihood (TVL)").orElse(null);
        Track academic = trackRepo.findByName("Academic").orElse(null);

        if (tvl != null) {
            strandRepo.save(new Strand(tvl,
                "Information and Communications Technology (ICT)", "ICT"));
            strandRepo.save(new Strand(tvl,
                "Home Economics (HE)", "HE"));
            strandRepo.save(new Strand(tvl,
                "Industrial Arts", null));
        }

        if (academic != null) {
            strandRepo.save(new Strand(academic,
                "Science, Technology, Engineering, and Mathematics (STEM)", "STEM"));
            strandRepo.save(new Strand(academic,
                "Accountancy, Business, and Management (ABM)", "ABM"));
            strandRepo.save(new Strand(academic,
                "Humanities and Social Sciences (HUMSS)", "HUMSS"));
        }
    }

    private void loadSpecializations() {
        Strand ict = strandRepo.findByAbbreviation("ICT").orElse(null);

        if (ict != null) {
            specializationRepo.save(new Specialization(ict.getTrack(), ict,
                "Computer Programming (CP)", "CP"));
            specializationRepo.save(new Specialization(ict.getTrack(), ict,
                "Computer Systems Servicing (CSS)", "CSS"));
        }
    }

    private void loadSections() {
        Specialization cp = specializationRepo.findByAbbreviation("CP").orElse(null);
        Strand humss = strandRepo.findByAbbreviation("HUMSS").orElse(null);

        if (cp != null) {
            sectionRepo.save(new Section(cp.getTrack(), cp.getStrand(), cp,
                "Haskell"));
            sectionRepo.save(new Section(cp.getTrack(), cp.getStrand(), cp,
                "Swift"));
            sectionRepo.save(new Section(cp.getTrack(), cp.getStrand(), cp,
                "Kotlin"));
        }

        if (humss != null) {
            sectionRepo.save(new Section(humss.getTrack(), humss, null,
                "Apolinario Mabini"));
        }
    }

    private void loadDepartments() {
        departmentRepo.save(new Department("English"));
        departmentRepo.save(new Department("Science"));
        departmentRepo.save(new Department("Mathematics"));
    }

    private void loadSubjects() {
        subjectRepo.save(new Subject("Practical Research 2"));
        subjectRepo.save(new Subject("Entrepreneurship"));
        subjectRepo.save(new Subject("Philosophy"));
    }

    //DEMO
    private void loadDemoStudent() {
        Section haskell = sectionRepo.findByName("Haskell").orElse(null);

        if (haskell != null) {
            // Create the User
            User user = new User();
            user.setFirstName("Zachary");
            user.setLastName("Cresencio");
            user.setEmail("neozakkman@gmail.com");
            user.setPassword("testPassword1"); // The service will hash this
            user.setRole(Role.STUDENT);
            user.setStatus(Status.PENDING);

            // Create the Student
            Student student = new Student();
            student.setLrn("123456789012");
            student.setTrack(haskell.getTrack());
            student.setStrand(haskell.getStrand());
            student.setSpecialization(haskell.getSpecialization());
            student.setSection(haskell);
            // NOTE: The verificationPhoto is required, but for a demo, a placeholder can be used.
            student.setVerificationPhoto(new byte[0]);

            // Create and populate the RegistrationData object
            RegistrationData registrationData = new RegistrationData();
            registrationData.setUser(user);
            registrationData.setSpecificUser(student);

            // Use the injected RegisterService to save the student
            registerService.registerStudent(registrationData);
        }
    }
    //DEMO

    //DEMO
    private void loadDemoTeachers() {
        Department mathDept = departmentRepo.findByName("Mathematics").orElse(null);
        Subject pr2 = subjectRepo.findByName("Practical Research 2").orElse(null);
        Subject entrep = subjectRepo.findByName("Entrepreneurship").orElse(null);
        Subject philosophy = subjectRepo.findByName("Philosophy").orElse(null);
        Section apolinarioMabini = sectionRepo.findByName("Apolinario Mabini").orElse(null);
        Section haskell = sectionRepo.findByName("Haskell").orElse(null);
        Section swift = sectionRepo.findByName("Swift").orElse(null);

        if (mathDept != null && pr2 != null && apolinarioMabini != null) {
            // Teacher 1
            User teacher1User = new User();
            teacher1User.setFirstName("Marie");
            teacher1User.setLastName("Curie");
            teacher1User.setMiddleName("Suplex");
            teacher1User.setEmail("marie.curie@example.com");
            teacher1User.setPassword("teacherpass1");
            teacher1User.setRole(Role.TEACHER);
            teacher1User.setStatus(Status.PENDING);

            Teacher teacher1 = new Teacher();
            teacher1.setTeacherId("T-001");
            teacher1.setDepartment(mathDept);
            teacher1.setSubjectsHandled(Set.of(pr2, entrep));
            teacher1.setSectionsHandled(Set.of(apolinarioMabini, swift));
            teacher1.setVerificationPhoto(new byte[0]);

            RegistrationData teacher1Data = new RegistrationData();
            teacher1Data.setUser(teacher1User);
            teacher1Data.setSpecificUser(teacher1);
            registerService.registerTeacher(teacher1Data);

            // Teacher 2
            User teacher2User = new User();
            teacher2User.setFirstName("Albert");
            teacher2User.setLastName("Einstein");
            teacher2User.setMiddleName("Gravity");
            teacher2User.setEmail("albert.einstein@example.com");
            teacher2User.setPassword("teacherpass2");
            teacher2User.setRole(Role.TEACHER);
            teacher2User.setStatus(Status.PENDING);

            Teacher teacher2 = new Teacher();
            teacher2.setTeacherId("T-002");
            teacher2.setDepartment(mathDept);
            teacher2.setSubjectsHandled(Set.of(pr2, philosophy));
            teacher2.setSectionsHandled(Set.of(apolinarioMabini, haskell));
            teacher2.setVerificationPhoto(new byte[0]);

            RegistrationData teacher2Data = new RegistrationData();
            teacher2Data.setUser(teacher2User);
            teacher2Data.setSpecificUser(teacher2);
            registerService.registerTeacher(teacher2Data);
        }
    }
    //DEMO
}
