package org.sopt.diary.repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    public DiaryEntity() {

    }

    // post 시 id 값을 null 로 만들면서 객체를 생성하기 위한 생성자
    public DiaryEntity(String title, String content, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // patch 시 id 값을 유지하면서 객체를 생성하기 위한 생성자
    public DiaryEntity(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
