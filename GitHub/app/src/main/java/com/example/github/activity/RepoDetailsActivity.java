package com.example.github.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.github.R;
import com.example.github.entity.Repository;
import com.example.github.network.HttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RepoDetailsActivity extends AppCompatActivity {
    private HttpClient httpClient;
    public static final String EXTRA_REPO_NAME = "repoName";
    public static final String EXTRA_REPO_LOGIN = "repoLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);

        httpClient = new HttpClient();
        String repoName = getIntent().getStringExtra(EXTRA_REPO_NAME);
        String repoLogin = getIntent().getStringExtra(EXTRA_REPO_LOGIN);

        displayRepository(repoName, repoLogin);
    }


    private void displayRepository(String repo_name, String repo_login){
        Callable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return httpClient.getRepository(repo_name, repo_login);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Repository> future = executor.submit(task);
        try {
            Repository repository = future.get();
            init_view(repository);
        } catch (ExecutionException | InterruptedException e) {
            Toast.makeText(RepoDetailsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void init_view(Repository repository){
        TextView textViewName = findViewById(R.id.RepoName);
        textViewName.setText(repository.getName());
        TextView textViewDesc = findViewById(R.id.Description);
        textViewDesc.setText(repository.getDescription());
        TextView ratingStarView = findViewById(R.id.rating_stars_text);
        ratingStarView.setText(String.valueOf(repository.getStargazers_count()));
        TextView ratingForksView = findViewById(R.id.rating_forks_text);
        ratingForksView.setText(String.valueOf(repository.getForks_count()));
        TextView languageView = findViewById(R.id.language_text);
        languageView.setText(repository.getLanguage());
        TextView createdAtView = findViewById(R.id.created_at_text);
        createdAtView.setText(getFormattedDate(repository.getCreated_at()));
        TextView updatedAtView = findViewById(R.id.updated_at_text);
        updatedAtView.setText(getFormattedDate(repository.getUpdated_at()));
    }

    private String getFormattedDate(String dateString) {
        SimpleDateFormat githubFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = githubFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            return outputFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
