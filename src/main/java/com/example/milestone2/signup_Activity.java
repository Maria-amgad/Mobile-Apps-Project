package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class signup_Activity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPassword;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup = (Button) findViewById(R.id.signup_btn);
        inputName= (EditText) findViewById(R.id.username_signup);
        inputEmail= (EditText) findViewById(R.id.email_signup);
        inputPassword= (EditText) findViewById(R.id.password_signup);
        loadingbar= new ProgressDialog(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(inputName.getText().toString())){
                    Toast.makeText(signup_Activity.this, "please write your name..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(inputPassword.getText().toString())){
                    Toast.makeText(signup_Activity.this, "please write your password..", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingbar.setTitle("Create account");
                    loadingbar.setMessage("Please wait, while we are checking credentials.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                }

                final DatabaseReference RootRef;
                RootRef= FirebaseDatabase.getInstance().getReference();
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!(snapshot.child("Users").child(inputName.getText().toString()).exists())){
                            HashMap<String, Object> userdataMap= new HashMap<>();
                            userdataMap.put("username",inputName.getText().toString() );
                            userdataMap.put("password",inputPassword.getText().toString() );
                            userdataMap.put("points", "0");

                            RootRef.child("Users").child(inputName.getText().toString()).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(signup_Activity.this, "Congrats, your account has been created", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }else {
                                        loadingbar.dismiss();
                                        Toast.makeText(signup_Activity.this, "Network error, try again", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                        }
                        else{
                           Toast.makeText(signup_Activity.this, "This" + inputName.getText().toString() + "already exists", Toast.LENGTH_SHORT).show();
                           loadingbar.dismiss();
                            Toast.makeText(signup_Activity.this, "please try again using another username", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                Intent my_intent = new Intent(signup_Activity.this, LoginActivity.class);
                my_intent.putExtra("username", inputName.getText().toString());
                my_intent.putExtra("password", inputPassword.getText().toString());
                startActivity(my_intent);
            }
        });
    }
}