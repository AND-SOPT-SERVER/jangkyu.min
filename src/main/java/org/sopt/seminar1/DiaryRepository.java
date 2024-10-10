package org.sopt.seminar1;

import java.io.*;
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
    private final Map<Long, Diary> patchStorage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();
    private final AtomicLong numberingPatch = new AtomicLong();

    final long countDiaries() {
        return storage.size();
    }

    final long countPatchDiaries() {
        return patchStorage.size();
    }

    final boolean existsInStorage(Long id) {
        return storage.containsKey(id);
    }

    final boolean existsInTrash(Long id) {
        return trashStorage.containsKey(id);
    }

    final Diary findFirstValueInPatch() {
        return patchStorage.values().iterator().next();
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
        final long patchId = numberingPatch.addAndGet(1);

        storage.put(id, diary);
        patchStorage.put(patchId, diary);
    }

    final void delete(Long id) {
        trashStorage.put(id, storage.get(id));
        storage.remove(id);
    }

    final void clearPatch() {
        patchStorage.clear();
    }

    final void restore(Long id) {
        storage.put(id, trashStorage.get(id));
        trashStorage.remove(id);
    }

    private void saveMap(Map<Long, Diary> map, String filePath, long size) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        for(long index = 1; index <= size; index++) {
            final Diary diary = map.get(index);

            if(diary != null) {
                String data = index + " " + diary.getBody() + " " + diary.getWriteTime();
                writer.write(data);
                writer.write("\n");
            }
        }
        writer.close();
    }

    public void saveStorage() throws IOException {
        saveMap(storage, "src/main/java/org/sopt/seminar1/storage/diary.txt", numbering.longValue());
    }

    public void saveTrashStorage() throws IOException {
        saveMap(trashStorage, "src/main/java/org/sopt/seminar1/storage/trash.txt", numbering.longValue());
    }

    public void savePatchStorage() throws IOException {
        saveMap(patchStorage, "src/main/java/org/sopt/seminar1/storage/patch.txt", numberingPatch.longValue());
    }

    private void loadMap(Map<Long, Diary> map, String filePath, boolean isPatch) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            String body = parts[1];
            LocalDate writeTime = LocalDate.parse(parts[2]);

            map.put(id, new Diary(id, body, writeTime));

            if(isPatch) {
                numberingPatch.set(Math.max(numberingPatch.longValue(), id));
            } else {
                numbering.set(Math.max(numbering.longValue(), id));
            }
        }
    }

    public void loadStorage() throws IOException {
        loadMap(storage, "src/main/java/org/sopt/seminar1/storage/diary.txt", false);
    }

    public void loadTrashStorage() throws IOException {
        loadMap(trashStorage, "src/main/java/org/sopt/seminar1/storage/trash.txt", false);
    }

    public void loadPatchStorage() throws IOException {
        loadMap(patchStorage, "src/main/java/org/sopt/seminar1/storage/patch.txt", true);
    }
}
