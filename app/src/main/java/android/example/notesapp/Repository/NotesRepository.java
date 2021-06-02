package android.example.notesapp.Repository;

import android.app.Application;
import android.example.notesapp.Dao.NotesDao;
import android.example.notesapp.Database.NotesDB;
import android.example.notesapp.Model.Notes;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;

    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;

    public NotesRepository(Application application){
        NotesDB notesDB = NotesDB.getDatabaseInstance(application);
        notesDao = notesDB.notesDao();
        getAllNotes = notesDao.getAllNotes();
        highToLow = notesDao.highToLow();
        lowToHigh = notesDao.lowToHigh();
    }

    // method of insert data which will be used by ViewModel
    public void insertNotes(Notes notes){
        notesDao.insertNotes(notes);
    }

    // method of delete data which will be used by ViewModel
    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }

    // method of update data which will be used by ViewModel
    public void updateNotes(Notes notes){
        notesDao.updateNotes(notes);
    }
}
