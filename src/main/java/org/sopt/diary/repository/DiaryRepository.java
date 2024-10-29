package org.sopt.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findTop10ByOrderByIdDesc();
    Optional<DiaryEntity> findTop1ByOrderByCreatedAtDesc();
    Boolean existsByTitle(String title);
}
