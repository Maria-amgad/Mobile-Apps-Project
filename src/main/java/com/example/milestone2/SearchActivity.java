package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import org.jsoup.*;
//import org.jsoup.select.Elements;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class SearchActivity extends AppCompatActivity {

    TextView trial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        Product p = new Product();

        EditText item_searrch = (EditText) findViewById(R.id.itemsearch);
        Button search_url = (Button) findViewById(R.id.search_url);
        Button home = (Button) findViewById(R.id.Home_page);
        Button search = (Button) findViewById(R.id.Search_page);
        Button cart = (Button) findViewById(R.id.Cart_page);
        Button profile = (Button) findViewById(R.id.Profile_page);
//        trial = (TextView) findViewById(R.id.ettrial);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(my_intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(SearchActivity.this, CartActivity.class);
                startActivity(my_intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(my_intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String WEBSITE = "https://www.revengeofficial.com";
//                String WEBSITE2 = "https://www.google.com/search?q=mesozoic+era";
//                String Ebay_site = "https://www.ebay.com/itm/295103908645?epid=27032664237&hash=item44b5904b25:g:rsgAAOSwXAxi2C8U&amdata=enc%3AAQAHAAAA4HltoHD8tImsS6u9GX%2BYw1IU6mfJurZQf3qnd%2Fzd6ekrXKlf8vH8oQBOX5BwucuNDeTxQ6S5iaJY7HQH%2F6NnsTbEVOAe9baZQMkfqGdWFSO%2FPESc0nu9ipCW%2FGlfMfdauMG4JYVSLQF8c8DX%2B%2BZfPVaW85djNwBErToBlK86fGg3IxC7NR6qH0Jrh9EQOnGrd2HB0szex9gtXKemkG94LhmdPp%2FPXvNAc3R9SgCmrWcve3WOJ2KIYv3sMzrOky%2Bz%2B3UV%2FNpxiTo2KK0UtreB%2BiMSnWfq7e0B2ev0PD9G68nF%7Ctkp%3ABFBMyurC1bhh";
//                Document document = null;
//                TextView trial = (TextView) findViewById(R.id.ettrial);
//
//                try {
//                    document = (Document) Jsoup.connect(Ebay_site).get();
//                    Elements title = document.select("ux-textspans ux-textspans--BOLD");
//                    trial.setText(document.text());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                new webscraper().execute();

//                String description = elements.text();

//                trial.setText("description");

//                for(Element element : elements){
//                    String link = element.attributes().get("href");
//                    trial.setText(link);
//                }

            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                TextView trial = (TextView) findViewById(R.id.ettrial);
//                String Ebay_site = "https://www.ebay.com/itm/295103908645?epid=27032664237&hash=item44b5904b25:g:rsgAAOSwXAxi2C8U&amdata=enc%3AAQAHAAAA4HltoHD8tImsS6u9GX%2BYw1IU6mfJurZQf3qnd%2Fzd6ekrXKlf8vH8oQBOX5BwucuNDeTxQ6S5iaJY7HQH%2F6NnsTbEVOAe9baZQMkfqGdWFSO%2FPESc0nu9ipCW%2FGlfMfdauMG4JYVSLQF8c8DX%2B%2BZfPVaW85djNwBErToBlK86fGg3IxC7NR6qH0Jrh9EQOnGrd2HB0szex9gtXKemkG94LhmdPp%2FPXvNAc3R9SgCmrWcve3WOJ2KIYv3sMzrOky%2Bz%2B3UV%2FNpxiTo2KK0UtreB%2BiMSnWfq7e0B2ev0PD9G68nF%7Ctkp%3ABFBMyurC1bhh";
//
//                try {
//                    Document document = (Document) Jsoup.connect(Ebay_site).get();
//                    Elements title = document.getElementsByClass("vim x-item-title");
//                    p.title = title.text();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                trial.setText(p.title);
//            }
//        });

        search_url.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

//                String Desc = productsRef.child(item_searrch.getText().toString()).child("Description").getKey().toString();
//
//                trial.setText("Description: " + Desc);

                productsRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.exists())
                        {
                            String pushkey = snapshot.getKey();
                            productsRef.child(pushkey).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s)
                                {
                                    if (dataSnapshot.exists())
                                    {
                                        if (dataSnapshot.getKey().toString().equals("Pid")) {
                                            //edit efg to any desired value
                                            if (dataSnapshot.getValue().toString().equals(item_searrch.getText().toString())) {
                                                Toast.makeText(SearchActivity.this, pushkey, Toast.LENGTH_SHORT).show();
                                                Intent my_intent = new Intent(SearchActivity.this, ProductDetailsActivity.class);
                                                my_intent.putExtra("pname", pushkey);
                                                startActivity(my_intent);
                                            }
                                        }
                                    }
                                } //https://firebasestorage.googleapis.com/v0/b/project-7b963.appspot.com/o/pants.png?alt=media&token=5e2c5230-61cd-4316-baa7-e257785287bd
                                @Override
                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }
                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
                                @Override
                                public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) { }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) { }
                            });
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public class webscraper extends AsyncTask<Void, Void, Void> {

        String words;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

//            Document document = null;
//            String GOOGLE = "https://www.google.com";
//            String JAMAICA = "http://jamaica-star.com/article/entertainment/20160513/reggae-sumfest-introduce-road-shows";
//            String Shein = "https://roe.shein.com/PARTHEA-Tie-Backless-Draped-Detail-Flounce-Sleeve-Glitter-Mesh-Dress-p-12376855-cat-1727.html?src_identifier=fc%3DWomen%60sc%3DCLOTHING%60tc%3DDRESSES%60oc%3D0%60ps%3Dtab01navbar05menu04%60jc%3Dreal_1727&src_module=topcat&src_tab_page_id=page_home1674156827389&mallCode=1&scici=navbar_WomenHomePage~~tab01navbar05menu04~~5_4~~real_1727~~~~0";
//
//            try {
//                document = (Document) Jsoup.connect(Shein).get();
//                words = document.text();
//            } catch (IOException e) {
//                e.printStackTrace();
//                words = "ERROR";
//            }

//            Elements elements = document.getElementsByClass("Productlist-item-link");


//            Toast.makeText(SearchActivity.this, words, Toast.LENGTH_LONG).show();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            trial.setText(words);
        }


    }
}