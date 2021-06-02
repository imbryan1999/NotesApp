package android.example.notesapp.Adapter;

import android.content.Intent;
import android.example.notesapp.Activity.MainActivity;
import android.example.notesapp.Activity.UpdateNotesActivity;
import android.example.notesapp.Model.Notes;
import android.example.notesapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesItem = new ArrayList<>(notes);
    }

    public void searchNotes(List<Notes> filteredName) {
        this.notes = filteredName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.notes_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes note = notes.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.notesPrio.setBackgroundResource(R.drawable.red_shape);
                break;
            case "2":
                holder.notesPrio.setBackgroundResource(R.drawable.green_shape);
                break;
            case "3":
                holder.notesPrio.setBackgroundResource(R.drawable.yellow_shape);
                break;
        }

//        holder.titleTextView.setText(notes.get(position).notesTitle);
        holder.titleTextView.setText(note.notesTitle);
        holder.subtitleTextView.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        // listener for when notes will clicked
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, UpdateNotesActivity.class);
            intent.putExtra("id", note.id);
            intent.putExtra("title", note.notesTitle);
            intent.putExtra("subtitle", note.notesSubtitle);
            intent.putExtra("note", note.notes);
            intent.putExtra("priority", note.notesPriority);
            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, subtitleTextView, notesDate;
        View notesPrio;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notes_title);
            subtitleTextView = itemView.findViewById(R.id.notes_subtitle);
            notesDate = itemView.findViewById(R.id.notes_date);
            notesPrio = itemView.findViewById(R.id.notesPriority);
        }
    }

}
