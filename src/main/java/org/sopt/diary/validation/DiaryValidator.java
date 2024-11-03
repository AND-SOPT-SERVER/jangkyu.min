package org.sopt.diary.validation;

import org.sopt.diary.constant.DiaryConstant;
import org.springframework.stereotype.Component;

@Component
public class DiaryValidator {
    public void validateDiaryTitleLength(String title) {
        if(title == null) {
            throw new IllegalArgumentException("제목이 없는 일기는 저장할 수 없습니다.");
        }
        if(title.length() > DiaryConstant.MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목의 길이는 10자를 초과할 수 없습니다.");
        }
    }

    public void validateDiaryContentLength(String content) {
        if(content == null) {
            throw new IllegalArgumentException("내용이 없는 일기는 저장할 수 없습니다.");
        }
        if(content.length() > DiaryConstant.MAX_DIARY_LENGTH) {
            throw new IllegalArgumentException("일기의 길이는 30자를 초과할 수 없습니다.");
        }
    }

    public void validatePage(Integer page) {
        if(page == null || page < 1) {
            throw new IllegalArgumentException("유효하지 않은 페이지 번호입니다.");
        }
    }
}
