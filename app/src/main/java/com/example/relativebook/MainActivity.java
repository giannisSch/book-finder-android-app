package com.example.relativebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Object> booksList;
    //private CustomAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        booksList = new ArrayList<>();
        //recyclewView
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        adapter = new CustomAdapter(new ItemClickListener() {
//            @Override
//            public void onItemClick(Object item) {
//                Log.d("TAG", item.toString());
//                if (item instanceof Store){
//                    createSharedPreferences(((Store) item).getId());
//                    Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
//                    intent.putExtra("BranchId", ((Store) item).getId());
//                    startActivity(intent);
//                }
//            }
//        });
//        recyclerView.setAdapter(adapter);


        //json
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/books/v1/volumes?q=+inauthor:keyes&key=AIzaSyB6w-x9GDuaPsRv2DCBQcKuQ8zpVpn3-OQ";
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE); // make progress bar invisible
                        try {
                            JSONArray storesArray = new JSONArray(response);
                            addBooksToList(storesArray);
                            //adapter.submitList(booksList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Παρακαλούμε ελένξτε την σύνδεση στο Internet", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void addBooksToList(JSONArray storesArray) throws JSONException {
        JSONObject jsonobject;
        Book book;
        for(int i = 0; i < storesArray.length(); i++){
            jsonobject = storesArray.getJSONObject(i);
            if (jsonobject.has("description")) {
                book = new Book(jsonobject.getString("title"),
                                jsonobject.getString("subtitle"),
                        (List) jsonobject.getJSONArray("authors"),
                                jsonobject.getString("publisher"),
                                jsonobject.getString("publishedDate"),
                                jsonobject.getInt("pageCount"),
                                jsonobject.getJSONObject("imageLines").getString("thumbnail"),
                                jsonobject.getString("description"));
                booksList.add(book);
            }
        }
    }
}
