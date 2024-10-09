package org.sopt.seminar1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, Diary> storage = new ConcurrentHashMap<>();
    private final Map<Long, Diary> trashStorage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    final long count() {
        return storage.size();
    }

    final boolean existById(Long id) {
        return storage.containsKey(id);
    }

    final boolean existTrashById(Long id) {
        return trashStorage.containsKey(id);
    }

    final Diary findDiaryByMaxId() {
        Set<Long> keySet = storage.keySet();
        Long maxKey = null;

        for(Long key : keySet) {
            if (maxKey == null || key > maxKey) {
                maxKey = key;
            }
        }
        return storage.get(maxKey);
    }

    final void save(final Diary diary) {
        // 채번 과정
        final long id = numbering.addAndGet(1);

        // 저장 과정
        storage.put(id, diary);
    }

    final List<Diary> findAll() {
        // (1) diary 를 담을 자료구조
        final List<Diary> diaryList = new ArrayList<>();

        // (2) 저장한 값을 불러오는 반복 구조
        for(long index = 1; index <= numbering.longValue(); index++) {
            final Diary diary = storage.get(index);

            if(diary != null) {
                // (2-1) 불러온 값을 구성한 자료구조로 이관. index 통해 찾은 diary 비어 있을 경우(삭제된 경우) diaryList 에 추가하지 않음
                diaryList.add(new Diary(index, diary.getBody(), diary.getWriteTime()));
            }
        }

        // (3) 불러온 자료구조를 응답
        return diaryList;
    }

    final void patch(Long id, Diary diary) {
        storage.put(id, diary);
    }

    final void delete(Long id) {
        trashStorage.put(id, storage.get(id));
        storage.remove(id);
    }

    final void restore(Long id) {
        storage.put(id, trashStorage.get(id));
        trashStorage.remove(id);
    }
}
