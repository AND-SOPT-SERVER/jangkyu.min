package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;

import java.time.LocalDate;

public class Diary {
    private Long id;
    private String title;
    private String content;
    private LocalDate writeDate;

    public Diary(
            Long id, String title, String content, LocalDate writeDate
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
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

    public LocalDate getWriteDate() {
        return writeDate;
    }

    public static DiaryEntity updateContent(DiaryEntity diaryEntity, String newContent) {
        return new DiaryEntity(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                newContent,
                diaryEntity.getWriteDate()
        );
    }
}
