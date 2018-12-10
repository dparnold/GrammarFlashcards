package com.dparnold.grammarflashcards;



import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;



@Dao
public interface FlashcardDAO {
    @Query("SELECT * FROM flashcard")
    List<Flashcard> getAll();

    @Query("SELECT * FROM flashcard WHERE ID IN (:IDs)")
    List<Flashcard> loadAllByIds(int[] IDs);

    @Query("DELETE FROM flashcard")
    public void nukeTable();

    @Query("SELECT * FROM flashcard WHERE ignored = 0 AND learning = 1 ORDER BY score DESC, timesStudied DESC LIMIT (:number)")
    List<Flashcard> getMostRelevant(int number);

    @Query("SELECT * FROM flashcard WHERE ignored = 0 AND learning = 0 LIMIT (:number)")
    List<Flashcard> getNewFlashcards(int number);

    @Insert
    void insertAll(List<Flashcard> flashcards); //Flashcard...

    @Delete
    void delete(Flashcard flashcard);

    @Update
    void updateFlashcard(Flashcard flashcard);
}