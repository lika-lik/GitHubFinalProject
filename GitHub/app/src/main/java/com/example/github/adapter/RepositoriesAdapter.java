package com.example.github.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.entity.Repository;
import com.example.github.entity.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>{
    private List<Repository> repositories = new ArrayList<>();
    private OnRepoClickListener onRepoClick;

    public interface OnRepoClickListener{
        void onRepoClick(Repository repository);
    }

    public RepositoriesAdapter(OnRepoClickListener onRepoClick) {
        this.onRepoClick = onRepoClick;
    }

    public void addItems(List<Repository> repositories){
        this.repositories.clear();
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View item_view = inflater.inflate(R.layout.repository_item_view, parent, false);

        return new RepositoryViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.bind(repository);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

     class RepositoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewOwner;
        private TextView textViewName;
        private TextView textViewDescription;

        public RepositoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewOwner = itemView.findViewById(R.id.owner_image_view);
            textViewName = itemView.findViewById(R.id.name_text_view);
            textViewDescription = itemView.findViewById(R.id.description_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Repository repository = repositories.get(getLayoutPosition());
                    onRepoClick.onRepoClick(repository);
                }
            });
        }

        public void bind(Repository repository) {
            textViewName.setText(repository.getName());
            textViewDescription.setText(repository.getDescription());

            String avatar_url = repository.getOwner().getAvatar_url();
            Picasso.get()
                    .load(avatar_url)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(imageViewOwner);
        }
    }
}
