package application.backend.school.services;

import application.backend.school.models.Department;
import application.backend.school.models.Section;
import application.backend.school.models.Specialization;
import application.backend.school.models.Subject;
import application.backend.school.repositories.DepartmentRepo;
import application.backend.school.repositories.SectionRepo;
import application.backend.school.repositories.SpecializationRepo;
import application.backend.school.repositories.SubjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    private final DepartmentRepo departmentRepo;
    private final SectionRepo sectionRepo;
    private final SpecializationRepo specializationRepo;
    private final SubjectRepo subjectRepo;

    public SchoolService(DepartmentRepo departmentRepo,
                         SectionRepo sectionRepo,
                         SpecializationRepo specializationRepo,
                         SubjectRepo subjectRepo) {
        this.departmentRepo = departmentRepo;
        this.sectionRepo = sectionRepo;
        this.specializationRepo = specializationRepo;
        this.subjectRepo = subjectRepo;
    }

    public List<Department> getDepartments() {
        return departmentRepo.findAll();
    }

    public List<Section> getSections() {
        return sectionRepo.findAll();
    }

    public List<Specialization> getSpecializations() {
        return specializationRepo.findAll();
    }

    public List<Subject> getSubjects() {
        return subjectRepo.findAll();
    }

}
