package com.example.russerials;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class SettingsList extends Fragment {
    private ConstraintLayout btnSign, btnReg;
    private FirebaseAuth mAuth;
    private Button btnExit;
    private FirebaseDatabase database;
    DatabaseReference reference;
    private AlertDialog dialog;
    EditText etEmail, etPass;
    private  TextView userEmail;
    private ScrollView scrollView;
    View v;
    String titleAlertSign, titleAlertReg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signDialog(titleAlertSign, 1);
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signDialog(titleAlertReg, 0);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getUserData();
            }
        });
    }

    public void init(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(getResources().getString(R.string.refUsers));
        btnSign = v.findViewById(R.id.btnSign);
        btnReg = v.findViewById(R.id.btnReg);
        mAuth = FirebaseAuth.getInstance();
        titleAlertSign = "Вход";
        titleAlertReg = "Регистрация";
        userEmail = v.findViewById(R.id.userEmTxt);
        scrollView = v.findViewById(R.id.ScrollView);
        btnExit = v.findViewById(R.id.bntExit);
    }

    public void signDialog(String title, final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View builderView = inflater.inflate(R.layout.sign_up_layout, null);
        builder.setView(builderView);
        final TextView  titleTxt = builderView.findViewById(R.id.textAlertTitle);
        titleTxt.setText(title);
        etEmail = builderView.findViewById(R.id.etEmail);
        etPass = builderView.findViewById(R.id.etPass);
        Button btnSignAlert = builderView.findViewById(R.id.btnAlertSign);
        dialog = builder.create();
        dialog.show();
        btnSignAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0){
                    signUp(etEmail.getText().toString(), etPass.getText().toString());
                }
                else {
                    signIn(etEmail.getText().toString(), etPass.getText().toString());
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getUserData();
    }

    private void getUserData(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            userEmail.setText(currentUser.getEmail());
            btnSign.setVisibility(View.GONE);
            btnReg.setVisibility(View.GONE);

        }
        else {
            userEmail.setText("войдите или зарегиструйтесь");
            btnSign.setVisibility(View.VISIBLE);
            btnReg.setVisibility(View.VISIBLE);
            scrollView.removeAllViews();
        }
    }

    private void signUp(String email, String password){
        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getContext(), "Успешно." + user.getEmail(), Toast.LENGTH_SHORT).show();
                                getUserData();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        else {
            Toast.makeText(getContext(),"Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn(String email, String password){
        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getContext(), "Успешно." + user.getEmail(), Toast.LENGTH_SHORT).show();
                                getUserData();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
