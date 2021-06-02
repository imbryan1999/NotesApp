package android.example.notesapp.ViewModel;

import android.app.Application;
import android.example.notesapp.Model.Notes;
import android.example.notesapp.Repository.NotesRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> getAllNotes;

    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
        highToLow = repository.highToLow;
        lowToHigh = repository.lowToHigh;
    }

    // function for inserting notes
    public void insertNote(Notes notes){
        repository.insertNotes(notes);
    }

    // function for deleting notes
    public void deleteNote(int id){
        repository.deleteNotes(id);
    }

    // function for updating notes
    public void updateNote(Notes notes){ repository.updateNotes(notes); }
}
