package com.example.russerials;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LikeList extends Fragment {
    private RecyclerView rcViewLike;
    private List<SerialView> result = new ArrayList<>();
    private SerialAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.like_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
//        init();
//        updateList();

    }

    public void init(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        if(user != null){
            reference = database.getReference("users/" + user.getUid());
        }
        else {
            Toast.makeText(getContext(), "Войдите или зарегестрируйтесь", Toast.LENGTH_SHORT).show();
        }
        rcViewLike = v.findViewById(R.id.rclike);
        rcViewLike.setHasFixedSize(true);

        //rcView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);

        rcViewLike.setLayoutManager(llm);
        adapter = new SerialAdapter(result, getContext());
        rcViewLike.setAdapter(adapter);
    }

    private void updateList(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                SerialView serialModel = snapshot.getValue(SerialView.class);
                serialModel.setKey(snapshot.getKey());//получение ключа не трогать а то крашитьс апк
                result.add(serialModel);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                SerialView serialModel = snapshot.getValue(SerialView.class);
                serialModel.setKey(snapshot.getKey());
                int index = getItemIndex(serialModel);
                result.set(index, serialModel);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                SerialView serialModel = snapshot.getValue(SerialView.class);
                serialModel.setKey(snapshot.getKey());
                int index = getItemIndex(serialModel);
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



    private int getItemIndex(SerialView serialView){
        int index = -1;
        for (int i = 0; i < result.size(); i++){
            if (result.get(i).getKey() != null && result.get(i).getKey().equals(serialView.getKey())){
                index = i;
                break;
            }
        }
        return index;
    }
}
