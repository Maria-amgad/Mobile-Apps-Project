package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.example.milestone2.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class LoginActivity extends AppCompatActivity {
    private ProgressDialog loadingbar;
    private EditText username, password;
    private Button login;
    private String parentDbName= "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        Bundle bundle = getIntent().getExtras();
//        if(bundle != null){
//            String new_uname = bundle.getString("username");
//            String new_pass = bundle.getString("password");
//        }

        login = (Button) findViewById(R.id.login_btn);
        username = (EditText) findViewById(R.id.email_input);
        password = (EditText) findViewById(R.id.password_input);
        loadingbar= new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(uname)){
                    Toast.makeText(LoginActivity.this, "please write your name..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "please write your password..", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingbar.setTitle("Login account");
                    loadingbar.setMessage("Please wait, while we are checking credentials.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    final DatabaseReference RootRef;
                    RootRef= FirebaseDatabase.getInstance().getReference();
                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if((snapshot.child(parentDbName).child(uname).exists())){
                                Users usersData= snapshot.child(parentDbName).child(uname).getValue(Users.class);

                                if(usersData.getUsername().equals(uname)){
                                    if(usersData.getPassword().equals(pass)){
                                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();

                                        Intent my_intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(my_intent);
                                    }else{
                                        loadingbar.dismiss();
                                        Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Account with this" + uname + " username doesn't exist", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

    }
}