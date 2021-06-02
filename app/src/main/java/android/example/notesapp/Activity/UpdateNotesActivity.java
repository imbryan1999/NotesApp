package android.example.notesapp.Activity;

import android.example.notesapp.Model.Notes;
import android.example.notesapp.R;
import android.example.notesapp.ViewModel.NotesViewModel;
import android.example.notesapp.databinding.ActivityUpdateNotesBinding;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {

    String sTitle, sSubTitle, sNotes, sPriority;
    NotesViewModel notesViewModel;
    private ActivityUpdateNotesBinding binding;
    private String priority = "1";
    private int iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // getting data with the help of intent from the NotesAdapter
        iid = getIntent().getIntExtra("id", 0);
        sTitle = getIntent().getStringExtra("title");
        sSubTitle = getIntent().getStringExtra("subtitle");
        sPriority = getIntent().getStringExtra("priority");
        sNotes = getIntent().getStringExtra("note");

        // set the data which is getting from the intent
        binding.upTitle.setText(sTitle);
        binding.upSubtitle.setText(sSubTitle);
        binding.upNotes.setText(sNotes);

        switch (sPriority) {
            case "1":
                binding.redShape.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "2":
                binding.greenShape.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "3":
                binding.yelloShape.setImageResource(R.drawable.ic_baseline_done_24);
                break;
        }

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

        // for update listener
        binding.floatingUpdateBtn.setOnClickListener(v -> {

            String title = binding.upTitle.getText().toString();
            String subtitle = binding.upSubtitle.getText().toString();
            String notes = binding.upNotes.getText().toString();

            UpdateNotes(title, subtitle, notes);
        });
    }

    private void UpdateNotes(String title, String subtitle, String notes) {
        // showing current date
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes updateNotes = new Notes();
        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subtitle;
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);

        String updateMessage = "Your Notes Updated Successfully...";
        Toast.makeText(this, updateMessage, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.ic_delete) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UpdateNotesActivity.this,
                    R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNotesActivity.this).
                    inflate(R.layout.delete_bottom_sheet, findViewById(R.id.bottom_sheet));
            bottomSheetDialog.setContentView(view);

            TextView yes, no;
            yes = view.findViewById(R.id.yes_delete);
            no = view.findViewById(R.id.no_delete);

            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iid);
                finish();
            });

            no.setOnClickListener(v -> bottomSheetDialog.dismiss());

            bottomSheetDialog.show();
        }
        return true;
    }
}