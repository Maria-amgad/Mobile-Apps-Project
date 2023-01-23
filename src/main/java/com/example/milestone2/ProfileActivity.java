package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milestone2.Model.Products;
import com.example.milestone2.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button logout = (Button) findViewById(R.id.logout);
        Button home = (Button) findViewById(R.id.Home_page);
        Button search = (Button) findViewById(R.id.Search_page);
        Button cart = (Button) findViewById(R.id.Cart_page);

        final String[] username = new String[1];

        CharSequence options[] = new CharSequence[]
                {
                        "joe",
                        "mariaa",
                        "marita"
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Choose Username:");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    username[0] = "joe";
                }
                if(i == 1){
                    username[0] = "mariaa";
                }
                if(i == 2){
                    username[0] = "marita";
                }

                TextView user_greeting = (TextView) findViewById(R.id.username_greeting);
                TextView points = (TextView) findViewById(R.id.user_points);

                final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
                user_greeting.setText("Hi " + username[0]);

                usersRef.child(username[0]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Users users = snapshot.getValue(Users.class);

                            points.setText(Integer.toString(users.getPoints()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        builder.show();



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(my_intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(ProfileActivity.this, SearchActivity.class);
                startActivity(my_intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(my_intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(my_intent);
            }
        });


    }
}