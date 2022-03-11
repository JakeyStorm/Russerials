package com.example.russerials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private List<BookView> list;
    private Context parent;

    public BookAdapter(List<BookView> list, Context parent) {
        this.list = list;
        this.parent = parent;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookAdapter.BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        BookView bookView = list.get(position);
        holder.nameBook.setText(bookView.getName());
        holder.nameAuthor.setText(bookView.getAuthor());
        holder.year.setText(bookView.getYear());
        Picasso.get().load(bookView.getImageId()).into(holder.imageBook);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        TextView nameBook, nameAuthor, year;
        ImageView imageBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            nameBook = itemView.findViewById(R.id.txt_name);
            nameAuthor = itemView.findViewById(R.id.txtAuthorMain);
            year = itemView.findViewById(R.id.txtYearMain);
            imageBook = itemView.findViewById(R.id.imageBook);

        }
    }
}
