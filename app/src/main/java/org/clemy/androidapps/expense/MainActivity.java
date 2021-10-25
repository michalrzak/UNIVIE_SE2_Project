package org.clemy.androidapps.expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.account_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        AccountListAdapter adapter = new AccountListAdapter();
        List<String> dataList = new java.util.ArrayList<>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
        adapter.submitList(dataList);
        list.setAdapter(adapter);
    }
}