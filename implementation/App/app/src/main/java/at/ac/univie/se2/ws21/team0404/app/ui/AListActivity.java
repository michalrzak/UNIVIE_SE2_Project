package at.ac.univie.se2.ws21.team0404.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract.IView;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import java.util.List;

public abstract class AListActivity<ModelClass, ViewHolder extends RecyclerView.ViewHolder, Presenter extends IListActivityContract.IPresenter<ModelClass>> extends
    AppCompatActivity implements IView<ModelClass> {

  protected Intent passedIntent;
  private ListAdapter<ModelClass, ViewHolder> adapter;

  private LifecycleHandler<AListActivity<ModelClass, ViewHolder, Presenter>> lifecycleHandler;

  protected Presenter presenter;

  /**
   * When this will get implemented it will return an instance of the adapter to be used with this
   * Activity
   *
   * @return Instance of the specific adapter
   */
  protected abstract ListAdapter<ModelClass, ViewHolder> getAdapter();

  /**
   * When this will get implemented it will return the presenter to be used by this class.
   *
   * @return presenter to be used
   */
  protected abstract Presenter getPresenter();

  /**
   * When this will get implemented it will return the id of the target R.string resource
   *
   * @return int id of the R.string
   */
  protected abstract int getListTitle();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    setTitle(getListTitle());

    ActionBar actionBar = getSupportActionBar();
    assert (actionBar != null);

    actionBar.setDisplayHomeAsUpEnabled(true);

    passedIntent = getIntent();

    RecyclerView recyclerView = findViewById(R.id.list_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    adapter = getAdapter();
    recyclerView.setAdapter(adapter);

    presenter = getPresenter();
    lifecycleHandler = new LifecycleHandler<>(presenter, this);

    // removed as this should happen in other parts of the code? (=presenter)
    /*
    IChangingData<List<ModelClass>> listData = getList();
    listData.observe(data -> {
      adapter.submitList(data);
    });
    */
  }

  // added this implementation which shows the provided list
  @Override
  public void showList(@NonNull List<ModelClass> list) {
    adapter.submitList(list);
  }

  public void onFabClick(View view) {
    presenter.clickFab();
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    Log.d("OptionsItemSelected", String.format("Selected item with id %d", item.getItemId()));

    switch (item.getItemId()) {
      default:
        finish();
        return true;
    }
  }
}
