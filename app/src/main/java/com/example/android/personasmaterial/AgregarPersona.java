package com.example.android.personasmaterial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class AgregarPersona extends Activity {

    private EditText txtCedula, txtNombre, txtApellido;
    private Spinner cmbSexo;
    private String opc[];
    private ArrayList<Integer> fotos;
    private ImageView foto;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_persona);

        txtCedula = findViewById(R.id.txtCedula);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        cmbSexo = findViewById(R.id.cmbSexo);
        foto = findViewById(R.id.foto);
        fotos = new ArrayList<>();
        fotos.add(R.drawable.images);
        fotos.add(R.drawable.images2);
        fotos.add(R.drawable.images3);

        opc = getResources().getStringArray(R.array.sexo);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opc);
        cmbSexo.setAdapter(adapter);
    }

    public int fotoAleatoria(){
        int fotoSelecionada;
        Random r = new Random();
        fotoSelecionada = r.nextInt(this.fotos.size());
        return fotos.get(fotoSelecionada);
    }

    public void guardar(View v){
        String cedula, nombre, apellido;
        int foto, sexo;
        foto = this.fotoAleatoria();
        if (validar()){
            cedula = txtCedula.getText().toString();
            nombre = txtNombre.getText().toString();
            apellido = txtApellido.getText().toString();
            sexo = cmbSexo.getSelectedItemPosition();

            Persona p = new Persona(foto, cedula, nombre, apellido, sexo);
            p.NuevaP();

            Snackbar.make(v, getResources().getString(R.string.guardadoE), Snackbar.LENGTH_SHORT).show();

            borrar();
        }
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AgregarPersona.this,principal.class);
        startActivity(i);
    }

    public void limpiar(View v){
        borrar();
    }

    public void borrar(){
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cmbSexo.setSelection(0);
        txtCedula.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public boolean validar() {
        int o1 = cmbSexo.getSelectedItemPosition();

        if (txtCedula.getText().toString().isEmpty()) {
            txtCedula.setError(getResources().getString(R.string.error_cedula));
            txtCedula.requestFocus();
            return false;
        }

        if (txtNombre.getText().toString().isEmpty()) {
            txtNombre.setError(getResources().getString(R.string.error_nombre));
            txtNombre.requestFocus();
            return false;
        }

        if (txtApellido.getText().toString().isEmpty()) {
            txtApellido.setError(getResources().getString(R.string.error_apellido));
            txtApellido.requestFocus();
            return false;
        }

        if (o1 == 0) {
            Toast.makeText(this, getResources().getString(R.string.error_2), Toast.LENGTH_SHORT).show();
            cmbSexo.requestFocus();
            return false;
        }

        return true;

    }
}
