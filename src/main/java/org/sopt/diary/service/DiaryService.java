package org.sopt.diary.service;

import jakarta.persistence.EntityNotFoundException;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(Diary diary) {
        diaryRepository.save(
            new DiaryEntity(
                    diary.getTitle(),
                    diary.getContent(),
                    diary.getWriteDate()
            )
        );
    }

    public List<Diary> readDiaryList() {
        // DB 에서 가져오는 값은 불변해야 한다. final 습관으로 달아주자.
        // (1) repository 로 부터 DiaryEntity 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByIdDesc();

        // (2) DiaryEntity 를 Diary 로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for(DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(
                    new Diary(
                            diaryEntity.getId(),
                            diaryEntity.getTitle(),
                            diaryEntity.getContent(),
                            diaryEntity.getWriteDate()
                    )
            );
        }

        return diaryList;
    }

    public Diary readDiaryDetail(Long id) {
        final DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 다이어리 입니다."));

        return new Diary(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                diaryEntity.getContent(),
                diaryEntity.getWriteDate()
        );
    }

    public void updateDiary(Long id, String content) {
        final DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 다이어리 입니다."));

        diaryRepository.save(
                Diary.updateContent(diaryEntity, content)
        );
    }

    public void deleteDiary(Long id) {
        final DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 다이어리 입니다."));

        diaryRepository.delete(diaryEntity);
    }
}
