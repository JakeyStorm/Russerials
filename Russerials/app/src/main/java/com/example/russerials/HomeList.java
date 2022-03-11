package com.example.russerials;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.List;

public class HomeList extends Fragment {
    private RecyclerView rcView;
    private List<SerialView> result = new ArrayList<>();
    private SerialAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();
        updateList();

    }
    public void init(){

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(getResources().getString(R.string.refSerials));
        rcView = v.findViewById(R.id.rcView);
        rcView.setHasFixedSize(true);
        //rcView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);

        rcView.setLayoutManager(llm);
        adapter = new SerialAdapter(result, getContext());
        rcView.setAdapter(adapter);

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
