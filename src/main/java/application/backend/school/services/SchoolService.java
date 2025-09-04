package application.backend.school.services;

import application.backend.school.models.*;
import application.backend.school.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    private final TrackRepo trackRepo;
    private final StrandRepo strandRepo;
    private final SectionRepo sectionRepo;
    private final SpecializationRepo specializationRepo;
    private final DepartmentRepo departmentRepo;
    private final SubjectRepo subjectRepo;

    public SchoolService(TrackRepo trackRepo,
                         StrandRepo strandRepo,
                         SpecializationRepo specializationRepo,
                         SectionRepo sectionRepo,
                         DepartmentRepo departmentRepo,
                         SubjectRepo subjectRepo) {
        this.trackRepo = trackRepo;
        this.strandRepo = strandRepo;
        this.specializationRepo = specializationRepo;
        this.sectionRepo = sectionRepo;
        this.departmentRepo = departmentRepo;
        this.subjectRepo = subjectRepo;

        //TODO: make strand and track entities and repos

    }

    //TODO: make mock data to test registration

    public List<Track> getTracks() {
        return trackRepo.findAll();
    }

    public List<Strand> getStrands() {
        return strandRepo.findAll();
    }

    public List<Specialization> getSpecializations() {
        return specializationRepo.findAll();
    }

    public List<Section> getSections() {
        return sectionRepo.findAll();
    }

    public List<Department> getDepartments() {
        return departmentRepo.findAll();
    }

    public List<Subject> getSubjects() {
        return subjectRepo.findAll();
    }

}
