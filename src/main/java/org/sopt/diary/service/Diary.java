package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;

import java.time.LocalDateTime;

public class Diary {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public Diary(
            Long id, String title, String content, LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static DiaryEntity updateContent(DiaryEntity diaryEntity, String newContent) {
        return new DiaryEntity(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                newContent,
                diaryEntity.getCreatedAt()
        );
    }
}
