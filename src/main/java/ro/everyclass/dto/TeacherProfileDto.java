package com.everyclass.dto;
import lombok.Builder; import lombok.Data; import java.util.List;
@Data @Builder
public class TeacherProfileDto { private Long id; private Long userId; private String name; private String city; private String county; private Double pricePerHour; private String description; private String photoUrl; private Double ratingAvg; private List<String> subjects; }