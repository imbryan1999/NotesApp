package android.example.notesapp.Database;

import android.content.Context;
import android.example.notesapp.Dao.NotesDao;
import android.example.notesapp.Model.Notes;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class}, version = 1)
public abstract class NotesDB extends RoomDatabase {

    public static NotesDB INSTANCE;

    public static NotesDB getDatabaseInstance(Context context){
        if (INSTANCE == null){
               INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                       NotesDB.class,
                       "Notes_DB").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract NotesDao notesDao();
}
