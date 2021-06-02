/* Making Notes App using Room Database with MVVM architecture
 * Step1: Make Entity
 * Step2: Make Dao (Data Access Object)
 * Step3: Create Database
 * Step4: Make Repository
 * Step5: Make ViewModel
 * */

/*
 * Making Shape
 * Using DataBinding instead of findViewById
 */

package android.example.notesapp.Activity;

import android.content.Intent;
import android.example.notesapp.Adapter.NotesAdapter;
import android.example.notesapp.Model.Notes;
import android.example.notesapp.R;
import android.example.notesapp.ViewModel.NotesViewModel;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingBtn;
    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    NotesAdapter adapter;

    TextView noFilter, highToLow, lowToHigh;
    List<Notes> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noFilter = findViewById(R.id.no_filter);
        highToLow = findViewById(R.id.high_to_low);
        lowToHigh = findViewById(R.id.low_to_high);

        noFilter.setBackgroundResource(R.drawable.filter_selected_shape);

        noFilter.setOnClickListener(v -> {
            loadData(0);
            noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
            highToLow.setBackgroundResource(R.drawable.filter_un_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_un_shape);
        });

        highToLow.setOnClickListener(v -> {
            loadData(1);
            highToLow.setBackgroundResource(R.drawable.filter_selected_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_un_shape);
            noFilter.setBackgroundResource(R.drawable.filter_un_shape);
        });

        lowToHigh.setOnClickListener(v -> {
            loadData(2);
            lowToHigh.setBackgroundResource(R.drawable.filter_selected_shape);
            highToLow.setBackgroundResource(R.drawable.filter_un_shape);
            noFilter.setBackgroundResource(R.drawable.filter_un_shape);
        });

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        recyclerView = findViewById(R.id.rc_view);

        // listener for when float btn clicked...
        floatingBtn = findViewById(R.id.floating_btn);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
            }
        });

        notesViewModel.getAllNotes.observe(this, notes -> {
            setAdapter(notes);
            filterNotesAllList = notes;
        });
    }

    private void loadData(int i) {
        if (i == 0) {
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        } else if (i == 1) {
            notesViewModel.highToLow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        } else if (i == 2) {
            notesViewModel.lowToHigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }
    }

    private void setAdapter(List<Notes> notes) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();

        // properties of SearchView
        searchView.setQueryHint("Search your Notes here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // method of filtered notes with its priority
    private void notesFilter(String newText) {
        ArrayList<Notes> filterName = new ArrayList<>();
        for (Notes notes : this.filterNotesAllList) {
            // checking notes title and subtitle
            if (notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)) {
                filterName.add(notes);
            }
        }
        this.adapter.searchNotes(filterName);
    }
}