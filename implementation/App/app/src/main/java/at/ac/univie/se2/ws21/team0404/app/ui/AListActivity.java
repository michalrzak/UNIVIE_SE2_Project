package at.ac.univie.se2.ws21.team0404.app.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collection;

import at.ac.univie.se2.ws21.team0404.app.R;
import java.util.List;

public abstract class AListActivity<ModelClass, ViewHolder extends RecyclerView.ViewHolder> extends AppCompatActivity {

    protected Intent passedIntent;

    /**
     * When this will get implemented it will return the class of the Activity, which should be
     * redirected to, after clicking on the floating action button.
     *
     * @return Class of the activity, which will be shown once clicked on the FAB
     */
    protected abstract Class getFabRedirect();

    /**
     * When this will get implemented it will return an instance of the adapter to be used with
     * this Activity
     *
     * @return Instance of the specific adapter
     */
    protected abstract ListAdapter<ModelClass, ViewHolder> getAdapter();

    /**
     * When this will get implemented it will return the list which will get used by the
     * recyclerview
     *
     * @return List\<ModelClass\> used by the recycler view
     */
    protected abstract List<ModelClass> getList();

    private ListAdapter<ModelClass, ViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        passedIntent = getIntent();

        RecyclerView recyclerView = findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        adapter.submitList(getList());

        FloatingActionButton fab = findViewById(R.id.floating_button);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, getFabRedirect());
            startActivity(intent);
        });
    }
}
