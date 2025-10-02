package com.everyclass.service;

import com.everyclass.dto.TeacherProfileDto;
import com.everyclass.entity.Subject;
import com.everyclass.entity.TeacherProfile;
import com.everyclass.entity.User;
import com.everyclass.exception.ResourceNotFoundException;
import com.everyclass.repository.SubjectRepository;
import com.everyclass.repository.TeacherProfileRepository;
import com.everyclass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherProfileRepository teacherRepo;
    private final SubjectRepository subjectRepo;
    private final UserRepository userRepo;

    public TeacherProfile createOrUpdate(Long userId, String city, String county, Double price, String description, String photoUrl, List<String> subjects){
        User u = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Set<Subject> subjectSet = subjects == null ? Set.of() : subjects.stream()
                .map(s -> subjectRepo.findByNameIgnoreCase(s).orElseGet(() -> subjectRepo.save(Subject.builder().name(s).build())))
                .collect(Collectors.toSet());
        TeacherProfile tp = teacherRepo.findAll().stream().filter(t -> t.getUser().getId().equals(userId)).findFirst().orElse(
                TeacherProfile.builder().user(u).build()
        );
        tp.setCity(city); tp.setCounty(county); tp.setPricePerHour(price); tp.setDescription(description); tp.setPhotoUrl(photoUrl); tp.setSubjects(subjectSet);
        return teacherRepo.save(tp);
    }

    public List<TeacherProfileDto> search(String city, String county, Double minPrice, Double maxPrice){
        List<TeacherProfile> all = teacherRepo.findAll();
        return all.stream().filter(t ->
                (city==null || (t.getCity()!=null && t.getCity().equalsIgnoreCase(city))) &&
                (county==null || (t.getCounty()!=null && t.getCounty().equalsIgnoreCase(county))) &&
                (minPrice==null || (t.getPricePerHour()!=null && t.getPricePerHour()>=minPrice)) &&
                (maxPrice==null || (t.getPricePerHour()!=null && t.getPricePerHour()<=maxPrice))
        ).map(this::toDto).collect(Collectors.toList());
    }

    public TeacherProfileDto getOne(Long id){
        TeacherProfile t = teacherRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        return toDto(t);
    }

    private TeacherProfileDto toDto(TeacherProfile t){
        return TeacherProfileDto.builder()
                .id(t.getId())
                .userId(t.getUser().getId())
                .name(t.getUser().getName())
                .city(t.getCity())
                .county(t.getCounty())
                .pricePerHour(t.getPricePerHour())
                .description(t.getDescription())
                .photoUrl(t.getPhotoUrl())
                .ratingAvg(t.getRatingAvg())
                .subjects(t.getSubjects()==null? List.of() : t.getSubjects().stream().map(Subject::getName).toList())
                .build();
    }
}
