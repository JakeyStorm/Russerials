package com.example.russerials;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EpisodelistActivity extends AppCompatActivity {

    private RecyclerView rcView;
    private List<BookView> result = new ArrayList<>();
    private BookAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Intent intentAdapdet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodelist);
        init();
        updateList();
    }

    public void init(){
        intentAdapdet = getIntent();
        database = FirebaseDatabase.getInstance();
        String eplistfirebase = intentAdapdet.getStringExtra("eplist");
        reference = database.getReference(eplistfirebase);
        rcView = findViewById(R.id.episodeListRec);
        rcView.setHasFixedSize(true);
        //rcView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rcView.setLayoutManager(llm);
        adapter = new BookAdapter(result, this);
        rcView.setAdapter(adapter);
    }
    private void updateList(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BookView bookView = snapshot.getValue(BookView.class);
                bookView.setKey(snapshot.getKey());//получение ключа не трогать а то крашитьс апк
                result.add(bookView);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BookView bookView = snapshot.getValue(BookView.class);
                bookView.setKey(snapshot.getKey());
                int index = getItemIndex(bookView);
                result.set(index, bookView);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BookView bookView = snapshot.getValue(BookView.class);
                bookView.setKey(snapshot.getKey());
                int index = getItemIndex(bookView);
                result.remove(index);
                adapter.notifyItemRemoved(index);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private int getItemIndex(BookView bookView){
        int index = -1;
        for (int i = 0; i < result.size(); i++){
            if (result.get(i).getKey() != null && result.get(i).getKey().equals(bookView.getKey())){
                index = i;
                break;
            }
        }
        return index;
    }
}
