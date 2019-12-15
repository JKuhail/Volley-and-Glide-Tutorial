package com.jkuhail.android.volleyandglide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jkuhail.android.volleyandglide.adapter.RvAdapter;
import com.jkuhail.android.volleyandglide.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


        private String URL_JSON = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json";
        private JsonArrayRequest ArrayRequest ;
        private RequestQueue requestQueue ;
        private List<Anime> lstAnime = new ArrayList<>();
        private RecyclerView myrv ;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            myrv = findViewById(R.id.rv);
            jsoncall();




        }

        public void jsoncall() {


            ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {



                    for (int i = 0 ; i<response.length();i++) {


                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            Anime anime = new Anime();

                            anime.setName(jsonObject.getString("name"));
                            anime.setRating(jsonObject.getString("Rating"));
                            anime.setDescription(jsonObject.getString("description"));
                            anime.setImage_url(jsonObject.getString("img"));
                            anime.setStudio(jsonObject.getString("studio"));
                            anime.setCategorie(jsonObject.getString("categorie"));
                            lstAnime.add(anime);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setRvadapter(lstAnime);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


            requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(ArrayRequest);
        }



        public void setRvadapter (List<Anime> lst) {

            RvAdapter myAdapter = new RvAdapter(this,lst) ;
            myrv.setLayoutManager(new LinearLayoutManager(this));
            myrv.setAdapter(myAdapter);




        }

    }