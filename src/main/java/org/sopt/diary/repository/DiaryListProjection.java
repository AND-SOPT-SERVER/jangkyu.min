package org.sopt.diary.repository;

import java.time.LocalDateTime;

public interface DiaryListProjection {
    Long getId();
    String getNickname();
    String getTitle();
    LocalDateTime getCreatedAt();
}
