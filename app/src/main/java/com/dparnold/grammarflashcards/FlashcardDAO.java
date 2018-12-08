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


    @Insert
    void insertAll(List<Flashcard> flashcards); //Flashcard...

    @Delete
    void delete(Flashcard flashcard);

    @Update
    void updateFlashcard(Flashcard flashcard);
}