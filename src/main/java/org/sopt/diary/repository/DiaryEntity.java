package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.sopt.diary.constant.Category;

import java.time.LocalDateTime;

// 데이터베이스에 있는 데이터를 자바 어플리케이션에 끌어오기 위해 사용하는 녀석
@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public DiaryEntity() {

    }

    // post 시 id 값을 null 로 만들면서 객체를 생성하기 위한 생성자
    public DiaryEntity(
            String title, String content, Boolean isPrivate , Category category, LocalDateTime createdAt, UserEntity userEntity
    ) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.createdAt = createdAt;
        this.category = category;
        this.userEntity = userEntity;
    }

    // patch 시 id 값을 유지하면서 객체를 생성하기 위한 생성자
    public DiaryEntity(
            Long id, String title, String content, Boolean isPrivate, Category category, LocalDateTime createdAt, UserEntity userEntity
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.category = category;
        this.createdAt = createdAt;
        this.userEntity = userEntity;
    }

    public void updateContent(String newContent, Category newCategory) {
        this.content = newContent;
        this.category = newCategory;
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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}

