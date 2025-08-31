package application.service;

import application.models.school.Department;
import application.models.school.Section;
import application.models.school.Specialization;
import application.models.school.Subject;
import application.repository.school.DepartmentRepo;
import application.repository.school.SectionRepo;
import application.repository.school.SpecializationRepo;
import application.repository.school.SubjectRepo;
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
