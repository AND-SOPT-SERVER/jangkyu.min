package org.sopt.diary.repository;

import jakarta.persistence.*;

import java.time.LocalDate;

// 데이터베이스에 있는 데이터를 자바 어플리케이션에 끌어오기 위해 사용하는 녀석
@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDate writeDate;

    public DiaryEntity() {

    }

    public DiaryEntity(Long id, String title, String content, LocalDate writeDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
    }

    public DiaryEntity(String title, String content, LocalDate writeDate) {
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
    }

    public Long getId() {
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
}
