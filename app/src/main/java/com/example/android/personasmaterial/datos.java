package com.example.android.personasmaterial;

import java.util.ArrayList;

public class datos {

    public static ArrayList<Persona> listap = new ArrayList();

    public static void agregar(Persona p){
        listap.add(p);
    }

    public static ArrayList<Persona> obtener (){
        return listap;
    }

}
