package com.example.android.personasmaterial;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class datos {

    private static String db = "Personas";

    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static ArrayList<Persona> listap = new ArrayList();

    public static void agregar(Persona p){
        databaseReference.child(db).child(p.getId()).setValue(p);
    }

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static void setPersonas(ArrayList<Persona> personas){
        datos.listap = personas;
    }

    public static ArrayList<Persona> obtener (){
        return listap;
    }

    public static void eliminar (Persona p){
        databaseReference.child(db).child(p.getId()).removeValue();
    }

    public static void editar (Persona p){
        databaseReference.child(db).child(p.getId()).setValue(p);
    }

}
