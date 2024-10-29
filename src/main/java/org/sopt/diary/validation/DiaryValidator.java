package org.sopt.diary.validation;

import org.sopt.diary.constant.DiaryConstant;
import org.springframework.stereotype.Component;

@Component
public class DiaryValidator {
    public void validateDiaryLength(String content) {
        if (
                content == null || content.length() > DiaryConstant.MAX_DIARY_LENGTH
        ) {
            throw new IllegalArgumentException("");
        }
    }
}
