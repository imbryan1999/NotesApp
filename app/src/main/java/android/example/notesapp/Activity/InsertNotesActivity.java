package android.example.notesapp.Activity;

import android.example.notesapp.Model.Notes;
import android.example.notesapp.R;
import android.example.notesapp.ViewModel.NotesViewModel;
import android.example.notesapp.databinding.ActivityInsertNotesBinding;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding; // data binding
    String title, subtitle, notes;
    NotesViewModel notesViewModel;

    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_notes);

        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        // For red circle shape
        binding.redShape.setOnClickListener(v -> {
            binding.redShape.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yelloShape.setImageResource(0);
            binding.greenShape.setImageResource(0);
            priority = "1";
        });

        // For green circle shape
        binding.greenShape.setOnClickListener(v -> {
            binding.greenShape.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yelloShape.setImageResource(0);
            binding.redShape.setImageResource(0);
            priority = "2";
        });

        // For yellow circle shape
        binding.yelloShape.setOnClickListener(v -> {
            binding.yelloShape.setImageResource(R.drawable.ic_baseline_done_24);
            binding.greenShape.setImageResource(0);
            binding.redShape.setImageResource(0);
            priority = "3";
        });

        // listener for adding notes
        binding.floatingDoneBtn.setOnClickListener(v -> {
            title = binding.titleTextView.getText().toString();
            subtitle = binding.subtitleTextView.getText().toString();
            notes = binding.notesTextView.getText().toString();

            createNotes(title, subtitle, notes);
        });
    }

    private void createNotes(String title, String subtitle, String notes) {
        // current date of create notes
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d,yyyy", date.getTime());

        Notes notes1 = new Notes();

        notes1.notesTitle = title;
        notes1.notesSubtitle = subtitle;
        notes1.notes = notes;
        notes1.notesPriority = priority;
        notes1.notesDate = sequence.toString();

        notesViewModel.insertNote(notes1);
        String message = "Your Notes Created Successfully....";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}