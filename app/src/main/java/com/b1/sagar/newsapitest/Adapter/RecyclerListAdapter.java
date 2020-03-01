package com.b1.sagar.newsapitest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.b1.sagar.newsapitest.DetailsActivity;
import com.b1.sagar.newsapitest.Model.Article;
import com.b1.sagar.newsapitest.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> {

    private List<Article> articleList;
    private Context context;
    Article article;

    public RecyclerListAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        article = articleList.get(position);

        holder.title.setText(article.getTitle());
        holder.time.setText(article.getPublishedAt());

        Glide.with(context).load(article.getUrlToImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("Web_Url",article.getUrl());
                    context.startActivity(intent);*/
                    showDialog(getAdapterPosition());
                }
            });
        }
    }

    private void showDialog(int adapterPosition){


        ImageView image;
        TextView tvSource,tvDateTime,tvTitle,tvDesription,tvAuthor;
        Button etBrowser,etWebView;

        Article article = articleList.get(adapterPosition);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.news_dialog,null,false);

        image = view.findViewById(R.id.image);
        tvSource = view.findViewById(R.id.tvSource);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDesription = view.findViewById(R.id.tvDesription);
        tvAuthor = view.findViewById(R.id.tvAuthor);
        tvDateTime = view.findViewById(R.id.tvDateTime);

        etWebView = view.findViewById(R.id.etWebView);
        etBrowser = view.findViewById(R.id.etBrowser);

        Glide.with(context).load(article.getUrlToImage()).into(image);
        tvSource.setText(article.getSource().getName());
        tvTitle.setText(article.getTitle());
        tvAuthor.setText(article.getAuthor());
        tvDesription.setText(article.getContent());
        tvDateTime.setText(getDateFormation(article.getPublishedAt()));

        etWebView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("Web_Url",article.getUrl());
            context.startActivity(intent);
        });

        etBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));
                String title = "Select Browser..";

                Intent chooser = Intent.createChooser(intent, title);
                context.startActivity(chooser);
            }
        });

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public  String getDateFormation(String formitingDate) {

        String expectedDate = "";
        Date date= null;
        String time="";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        formitingDate = formitingDate.replace("T", " ");
        formitingDate = formitingDate.replace("Z", "");

        try {
            date = dateFormat.parse( formitingDate );
            expectedDate = dateFormat.format( date );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        long postTime = date.getTime();
        long currentTime = System.currentTimeMillis();

        long difference = (currentTime-postTime)/1000;

        if (difference>= 30){
            time = "Few seconds ago";
        }else if (difference >= 60){
            time = "Few minutes ago";
        }else if (difference >= 1800){
            time = "30 minutes ago";
        }else if (difference >= 3600){
            time = "An hour ago";
        }

        return expectedDate;
    }
}
