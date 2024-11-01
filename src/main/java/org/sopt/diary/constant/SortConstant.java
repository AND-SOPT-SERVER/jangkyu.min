package org.sopt.diary.constant;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;

public enum SortConstant {
    LATEST("latest"),
    QUANTITY("quantity"),
    ;
    private final String criteria;

    // enum 은 생성자의 기본 접근자가 private 이다.
    SortConstant(String criteria) {
        this.criteria = criteria;
    }

    public static SortConstant of(String criteria) {
        return Arrays.stream(SortConstant.values())
                .filter(sortConstant -> sortConstant.criteria.equals(criteria))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 정렬 기준입니다."));
    }

    public String getSortCriteria() {
        return criteria;
    }
}
