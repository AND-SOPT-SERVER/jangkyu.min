package org.sopt.diary.service;

import jakarta.persistence.EntityNotFoundException;
import org.sopt.diary.constant.Category;
import org.sopt.diary.constant.DiaryConstant;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.sopt.diary.repository.UserEntity;
import org.sopt.diary.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    private UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 입니다."));
    }

    private DiaryEntity getDiaryEntityById(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 다이어리 입니다."));
    }

    private boolean isDiaryCreateInLimit() {
        Optional<DiaryEntity> optionalDiaryEntity = diaryRepository.findTop1ByOrderByCreatedAtDesc();

        // DB 상에 Diary 가 있다면 비교, 없다면 바로 저장
        if(optionalDiaryEntity.isPresent()) {
            DiaryEntity diaryEntity = optionalDiaryEntity.get();

            long betweenMinutes = Duration.between(
                    diaryEntity.getCreatedAt(), LocalDateTime.now()
            ).toMinutes();

            // betweenMinutes 이 DIARY_WRITE_LIMIT 보다 작다면 제한시간 내에 적은 것이므로 true 를 반환함
            return betweenMinutes < DiaryConstant.DIARY_WRITE_LIMIT;
        } else {
            return false;
        }
    }

    public void createDiary(Long userId, Diary diary) {
        final UserEntity userEntity = getUserEntityById(userId);

        // 5분안에 일기가 작성된다면 ? : 400 Bad Request 를 걸어주면 좋을 것 같다 !
        if(isDiaryCreateInLimit()) {
            throw new IllegalStateException("5분내에 다이어리를 다시 작성할 수 없습니다.");
        }

        // 이 역시 잘못된 요청이므로 400 Bad Request 를 걸어주면 좋을 것 같다 !
        // 비즈니스적인 로직이 필요하므로 서비스에서 처리해준다.
        if(diaryRepository.existsByTitle(
                diary.getTitle()
        )) {
            throw new IllegalArgumentException("이미 있는 제목의 일기입니다.");
        }

        diaryRepository.save(
                new DiaryEntity(
                        diary.getTitle(),
                        diary.getContent(),
                        diary.getPrivate(),
                        diary.getCategory(),
                        diary.getCreatedAt(),
                        userEntity
                )
        );
    }

    public List<Diary> getRecentDiaries() {
        // DB 에서 가져오는 값은 불변해야 한다. final 습관으로 달아주자.
        // (1) repository 로 부터 DiaryEntity 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByIdDesc();

        // (2) DiaryEntity 를 Diary 로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for(DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(
                    Diary.fromDiaryEntity(diaryEntity)
            );
        }

        return diaryList;
    }

    public Diary getDiaryById(Long id) {
        final DiaryEntity diaryEntity = getDiaryEntityById(id);

        return Diary.fromDiaryEntity(diaryEntity);
    }

    public void updateDiaryContent(
            Long userId, Long id, String content, Category category
    ) {
        final UserEntity userEntity = getUserEntityById(userId);
        final DiaryEntity diaryEntity = getDiaryEntityById(id);

        diaryRepository.save(
                Diary.updateContent(diaryEntity, content, category)
        );
    }

    public void deleteDiary(Long userId, Long id) {
        final UserEntity userEntity = getUserEntityById(userId);
        final DiaryEntity diaryEntity = getDiaryEntityById(id);

        diaryRepository.delete(diaryEntity);
    }
}

