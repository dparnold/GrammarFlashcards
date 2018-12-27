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
    void nukeTable();

    @Query("DELETE FROM flashcard WHERE packageName = :packageName")
    void removePackage(String packageName);

    @Query("SELECT * FROM flashcard WHERE ignored = 0 AND learning = 1 ORDER BY score DESC, timesStudied DESC LIMIT (:number)")
    List<Flashcard> getMostRelevant(int number);

    @Query("SELECT * FROM flashcard WHERE ignored = 0 AND learning = 1 AND packageName=:packageName ORDER BY score ASC, timesStudied DESC LIMIT (:number)")
    List<Flashcard> getSomeCards(int number, String packageName);

    @Query("SELECT * FROM flashcard WHERE ignored = 0 AND learning = 1 AND packageName=:packageName AND learnNextTime<=:timestamp ORDER BY learnNextTime ASC LIMIT (:number)")
    List<Flashcard>  getCardsToReview(String packageName, double timestamp, int number);

    @Query("SELECT * FROM flashcard WHERE ignored = 0 AND learning = 0 LIMIT (:number)")
    List<Flashcard> getNewFlashcards(int number);

    @Query("SELECT * FROM flashcard WHERE ignored = 0 AND learning = 0 AND packageName=:packageName LIMIT (:number)")
    List<Flashcard> getNewFlashcardsOfPackage(int number,String packageName);

    @Query("SELECT * FROM flashcard WHERE packageName=:packageName")
    List<Flashcard> getAllFromPackage(String packageName);

    @Insert
    void insertAll(List<Flashcard> flashcards); //Flashcard...

    @Delete
    void delete(Flashcard flashcard);

    @Update
    void updateFlashcard(Flashcard flashcard);

    @Query("SELECT COUNT(*) FROM flashcard WHERE packageName = :packageName")
    int getNumberOfCards(String packageName);

    @Query("SELECT COUNT(*) FROM flashcard WHERE packageName = :packageName AND learning=1")
    int getNumberOfCardsStudied(String packageName);

    @Query("SELECT COUNT(*) FROM flashcard WHERE packageName = :packageName AND learning=1 AND ignored=0 AND learnNextTime<=:timestamp ORDER BY learnNextTime ASC")
    int getNumberOfCardsToReview(String packageName, double timestamp);

    @Query("SELECT COUNT(*) FROM flashcard WHERE packageName = :packageName AND ignored=1")
    int getNumberOfCardsIgnored(String packageName);
}