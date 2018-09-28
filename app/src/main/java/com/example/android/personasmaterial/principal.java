package com.example.android.personasmaterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class principal extends AppCompatActivity {

    Intent i;
    private RecyclerView lstP;
    private ArrayList<Persona> personas;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstP= findViewById(R.id.lstPersonas);
        personas = datos.obtener();

        AdaptadorP adaptP = new AdaptadorP(personas);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lstP.setLayoutManager(llm);
        lstP.setAdapter(adaptP);
    }

    public void agregarPersona(View v){
        i = new Intent(principal.this,AgregarPersona.class);
        startActivity(i);
        finish();
    }
}
