package com.example.mapets.testamalgama.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapets.testamalgama.R;
import com.example.mapets.testamalgama.adapter.RazaAdapter;
import com.example.mapets.testamalgama.entity.Perro;
import com.example.mapets.testamalgama.helper.SingletonRazas;
import com.example.mapets.testamalgama.listener.RazasListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RazasListener {

    private RecyclerView dogList;
    private RazaAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private ImageView imagenPerro;
    private ImageView imagenPrincipal;
    private TextView titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface faceToolbar = Typeface.createFromAsset(getAssets(), "fonts/river.ttf");
        imagenPrincipal = findViewById(R.id.imagen_titulo);
        titulo = findViewById(R.id.titulo);
        TextView textoTitulo = findViewById(R.id.titulo);
        textoTitulo.setTypeface(faceToolbar);
        dogList = (RecyclerView) findViewById(R.id.recycler_main);
        dogList.setHasFixedSize(true);
        dogList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        layoutManager = new LinearLayoutManager(this);
        dogList.setLayoutManager(layoutManager);
        dogList.setItemAnimator(new DefaultItemAnimator());
        SingletonRazas.getIntance().buscadorRazas(this, this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void razaslistener(ArrayList<Perro> perros) {
        mAdapter = new RazaAdapter(perros, this);
        if(mAdapter.getItemCount()>=0)
            dogList.setAdapter(mAdapter);
    }
}
