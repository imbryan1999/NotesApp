package android.example.notesapp.Dao;

import android.example.notesapp.Model.Notes;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes_DB")  // Query for getting data from db
    LiveData<List<Notes>> getAllNotes();

    @Query("SELECT * FROM Notes_DB ORDER BY notes_priority DESC")  // Query for getting data from db with its priority
    LiveData<List<Notes>> highToLow();

    @Query("SELECT * FROM Notes_DB ORDER BY notes_priority ASC")  // Query for getting data from db with its priority
    LiveData<List<Notes>> lowToHigh();

    @Insert // For inserting data
    void insertNotes(Notes... notes);

    @Query("DELETE FROM Notes_DB WHERE id=:id") // Query for deleting data
    void deleteNotes(int id);

    @Update // for updating the data
    void updateNotes(Notes notes);
}
