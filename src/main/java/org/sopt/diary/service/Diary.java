package org.sopt.diary.service;

import java.time.LocalDate;

public class Diary {
    private long id;
    private String title;
    private String content;
    private LocalDate writeDate;

    // 다이어리 post
    public Diary(String title, String content, LocalDate writeDate) {
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
    }

    // 다이어리 리스트 생성
    public Diary(long id, String title) {
        this.id = id;
        this.title = title;
    }

    // 다이어리 상세 조회
    public Diary(long id, String title, String content, LocalDate writeDate) {
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

    public void updateContent(String content) {
        this.content = content;
    }
}
