package org.sopt.diary.repository;

import org.sopt.diary.constant.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findTop1ByOrderByCreatedAtDesc();

    Boolean existsByTitle(String title);

    @Query(
            "SELECT d.id, u.nickname, d.title, d.createdAt " +
            "FROM DiaryEntity d JOIN d.userEntity u " +
            "WHERE (:category = 'ALL' OR :category = d.category) " +
            "AND (:userId = u.id OR d.isPrivate = false) " +
            "ORDER BY d.createdAt DESC"
    )
    List<DiaryEntity> findTop10DiariesByCreatedAt(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(
            "SELECT d.id, u.nickname, d.title, d.createdAt " +
            "FROM DiaryEntity d JOIN d.userEntity u " +
            "WHERE (:category = 'ALL' OR :category = d.category) " +
            "AND (:userId = u.id OR d.isPrivate = false) " +
            "ORDER BY LENGTH(d.title) DESC"
    )
    List<DiaryEntity> findTop10DiariesByTitleLength(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(
            "SELECT d.id, u.nickname, d.title, d.createdAt " +
                    "FROM DiaryEntity d JOIN d.userEntity u " +
                    "WHERE (:category = 'ALL' OR :category = d.category) " +
                    "AND :userId = u.id " +
                    "ORDER BY d.createdAt DESC"
    )
    List<DiaryEntity> findMyTop10DiariesByCreatedAt(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(
            "SELECT d.id, u.nickname, d.title, d.createdAt " +
                    "FROM DiaryEntity d JOIN d.userEntity u " +
                    "WHERE (:category = 'ALL' OR :category = d.category) " +
                    "AND :userId = u.id " +
                    "ORDER BY LENGTH(d.title) DESC"
    )
    List<DiaryEntity> findMyTop10DiariesByTitleLength(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );
}
