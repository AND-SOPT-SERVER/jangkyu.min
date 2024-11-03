package org.sopt.diary.constant;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;

public enum Category {
    FOOD("food"),
    SCHOOL("school"),
    MOVIE("movie"),
    EXERCISE("exercise"),
    ALL(null)
    ;
    private final String category;

    // enum 은 생성자의 기본 접근자가 private 이다.
    Category(String category) {
        this.category = category;
    }

    public static Category of(String categoryInput) {
        return Arrays.stream(Category.values())
                .filter(categoryValue -> categoryValue.category.equals(categoryInput))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 정렬 카테고리입니다."));
    }

    public String getCategory() {
        return category;
    }
}
