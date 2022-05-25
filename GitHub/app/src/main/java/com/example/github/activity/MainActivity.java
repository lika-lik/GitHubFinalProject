package com.example.github.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.github.R;
import com.example.github.entity.Repository;
import com.example.github.network.HttpClient;
import com.example.github.adapter.RepositoriesAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private HttpClient httpClient;
    private RepositoriesAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycleView();
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.repositories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RepositoriesAdapter.OnRepoClickListener listener = new RepositoriesAdapter.OnRepoClickListener() {
            @Override
            public void onRepoClick(Repository repository) {
                Intent intent = new Intent(MainActivity.this, RepoDetailsActivity.class);
                intent.putExtra(RepoDetailsActivity.EXTRA_REPO_NAME, repository.getName());
                intent.putExtra(RepoDetailsActivity.EXTRA_REPO_LOGIN, repository.getOwner().getLogin());
                startActivity(intent);
            }
        };
        adapter = new RepositoriesAdapter(listener);
        recyclerView.setAdapter(adapter);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        httpClient = new HttpClient();
        progressBar = findViewById(R.id.progressBar);

        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paramQ = editTextSearch.getText().toString();
                if (paramQ.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.enter_sth, Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        searchRepositories(paramQ);
                    }
                }).start();
            }
        });
    }

    private void searchRepositories(String paramQ) {
        ArrayList<Repository> repositories = null;
        try {
            repositories =  httpClient.getRepositories(paramQ);
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Repository> finalRepositories = repositories;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (finalRepositories != null){
                adapter.addItems(finalRepositories);}
                else{
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });


    }
}