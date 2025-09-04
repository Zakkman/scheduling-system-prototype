package application.backend.school.services;

import application.backend.school.models.*;
import application.backend.school.repositories.*;
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

    public InitSchoolDataLoader(TrackRepo trackRepo,
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
    }

    @Override
    public void run(String... args) throws Exception {
        loadTracks();
        loadStrands();
        loadSpecializations();
        loadSections();
        loadDepartments();
        loadSubjects();
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
        subjectRepo.save(new Subject("Philosophy") );
    }
}
