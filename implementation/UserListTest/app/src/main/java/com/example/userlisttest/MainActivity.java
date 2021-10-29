package com.example.userlisttest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.userlisttest.Data.Account;
import com.example.userlisttest.Data.AccountListAdapter;
import com.example.userlisttest.Data.AccountViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userlisttest.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private AccountViewModel accountViewModel;

    public static final int NEW_ACCOUNT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AccountAddActivity.class);
                // is depricated but am too tired to figure this out now
                // the alternative seems a bit more complex
                // https://developer.android.com/training/basics/intents/result
                startActivityForResult(intent, NEW_ACCOUNT_ACTIVITY_REQUEST_CODE);
            }
        });


        // add the accounts to the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final AccountListAdapter accountListAdapter = new AccountListAdapter(this);
        recyclerView.setAdapter(accountListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // don't 100% understand this part
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> accounts) {
                accountListAdapter.setAccounts(accounts);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ACCOUNT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Account account = new Account(data.getStringExtra(AccountAddActivity.EXTRA_REPLY));
            accountViewModel.insert(account);
        }
        else {
            Toast.makeText(getApplicationContext(), // why not just this? When can I use this and when not?
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}