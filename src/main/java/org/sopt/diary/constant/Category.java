package org.sopt.diary.constant;

public enum Category {
    FOOD("food"),
    SCHOOL("school"),
    MOVIE("movie"),
    EXERCISE("exercise"),
    ;
    private final String category;

    // enum 은 생성자의 기본 접근자가 private 이다.
    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
