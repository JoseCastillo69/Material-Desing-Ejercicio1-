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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Principal;
import java.util.ArrayList;

public class principal extends AppCompatActivity implements AdaptadorP.OnPersonaClickListener {

    Intent i;
    private RecyclerView lstP;
    private ArrayList<Persona> personas;
    private LinearLayoutManager llm;
    private DatabaseReference databaseReference;
    private String db = "Personas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstP= findViewById(R.id.lstActividades);
        personas = new ArrayList<>();

        final AdaptadorP adaptP = new AdaptadorP(personas, this);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lstP.setLayoutManager(llm);
        lstP.setAdapter(adaptP);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personas.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                        Persona p = snapshot.getValue(Persona.class);
                        personas.add(p);
                    }
                }
                adaptP.notifyDataSetChanged();
                datos.setPersonas(personas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void agregarPersona(View v){
        i = new Intent(principal.this,AgregarPersona.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onPersonaClick(Persona p) {
        Intent i = new Intent(principal.this,DetallePersona.class);
        Bundle b = new Bundle();
        b.putString("id",p.getId());
        b.putString("cedula",p.getCedula());
        b.putString("nombre",p.getNombre());
        b.putString("apellido",p.getApellido());
        b.putString("foto",p.getFoto());
        i.putExtra("datos",b);
        startActivity(i);
        finish();

    }
}
