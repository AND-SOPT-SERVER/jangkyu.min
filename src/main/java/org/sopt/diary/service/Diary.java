package org.sopt.diary.service;

import org.sopt.diary.constant.Category;
import org.sopt.diary.repository.DiaryEntity;

import java.time.LocalDateTime;

public class Diary {
    private Long id;
    private String title;
    private String content;
    private Boolean isPrivate;
    private Category category;
    private LocalDateTime createdAt;
    private User user;

    public Diary(
            Long id, String title, String content, Boolean isPrivate, Category category, LocalDateTime createdAt, User user
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.category = category;
        this.createdAt = createdAt;
        this.user = user;
    }

    public static Diary fromDiaryEntity(DiaryEntity diaryEntity) {
        return new Diary(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                diaryEntity.getContent(),
                diaryEntity.getPrivate(),
                diaryEntity.getCategory(),
                diaryEntity.getCreatedAt(),
                User.fromUserEntity(diaryEntity.getUserEntity())
        );
    }

    public static DiaryEntity updateContent(
            DiaryEntity diaryEntity, String newContent, Category newCategory
    ) {
        return new DiaryEntity(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                newContent,
                diaryEntity.getPrivate(),
                newCategory,
                diaryEntity.getCreatedAt(),
                diaryEntity.getUserEntity()
        );
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
}

