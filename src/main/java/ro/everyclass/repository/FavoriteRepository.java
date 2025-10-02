package com.everyclass.repository;

import ro.everyclass.entity.Favorite;
import ro.everyclass.entity.TeacherProfile;
import ro.everyclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    boolean existsByUserAndTeacher(User user, TeacherProfile teacher);
}
