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
            "SELECT d.id AS id, u.nickname AS nickname, d.title AS title, d.createdAt AS createdAt " +
            "FROM DiaryEntity d JOIN d.userEntity u " +
            "WHERE (:category IS NULL OR :category = d.category) " +
            "AND (:userId = u.id OR d.isPrivate = false) " +
            "ORDER BY d.createdAt DESC"
    )
    List<DiaryListProjection> findTop10DiariesByCreatedAt(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(
            "SELECT d.id AS id, u.nickname AS nickname, d.title AS title, d.createdAt AS createdAt " +
            "FROM DiaryEntity d JOIN d.userEntity u " +
            "WHERE (:category IS NULL OR :category = d.category) " +
            "AND (:userId = u.id OR d.isPrivate = false) " +
            "ORDER BY LENGTH(d.title) DESC"
    )
    List<DiaryListProjection> findTop10DiariesByTitleLength(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(
            "SELECT d.id AS id, u.nickname AS nickname, d.title AS title, d.createdAt AS createdAt " +
            "FROM DiaryEntity d JOIN d.userEntity u " +
            "WHERE (:category IS NULL OR :category = d.category) " +
            "AND :userId = u.id " +
            "ORDER BY d.createdAt DESC"
    )
    List<DiaryListProjection> findMyTop10DiariesByCreatedAt(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(
            "SELECT d.id AS id, u.nickname AS nickname, d.title AS title, d.createdAt AS createdAt " +
            "FROM DiaryEntity d JOIN d.userEntity u " +
            "WHERE (:category IS NULL OR :category = d.category) " +
            "AND :userId = u.id " +
            "ORDER BY LENGTH(d.title) DESC"
    )
    List<DiaryListProjection> findMyTop10DiariesByTitleLength(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Pageable pageable
    );
}


