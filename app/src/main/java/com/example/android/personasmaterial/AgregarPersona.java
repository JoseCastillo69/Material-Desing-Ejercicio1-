package com.example.android.personasmaterial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;

public class AgregarPersona extends Activity {

    private EditText txtCedula, txtNombre, txtApellido;
    private Spinner cmbSexo;
    private ArrayAdapter<String> adapter;
    private String opc[];
    private ArrayList<Integer> fotos;
    private ImageView foto;
    private Uri uri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_persona);

        txtCedula = findViewById(R.id.txtCedula);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        cmbSexo = findViewById(R.id.cmbSexo);
        foto = findViewById(R.id.fotoe);
        fotos = new ArrayList<>();
        fotos.add(R.drawable.images);
        fotos.add(R.drawable.images2);
        fotos.add(R.drawable.images3);

        opc = getResources().getStringArray(R.array.sexo);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opc);
        cmbSexo.setAdapter(adapter);

        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public int fotoAleatoria(){
        int fotoSelecionada;
        Random r = new Random();
        fotoSelecionada = r.nextInt(this.fotos.size());
        return fotos.get(fotoSelecionada);
    }

    public void guardar(View v){
        String cedula, nombre, apellido, foto, id;
        int sexo;
        //foto = this.fotoAleatoria();
        if (validar()){
            id = datos.getId();
            foto = id+".jpg";
            cedula = txtCedula.getText().toString();
            nombre = txtNombre.getText().toString();
            apellido = txtApellido.getText().toString();
            sexo = cmbSexo.getSelectedItemPosition();

            Persona p = new Persona(id, foto, cedula, nombre, apellido, sexo);
            p.NuevaP();
            Subir_Foto(foto);

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
        foto.setImageResource(android.R.drawable.ic_menu_gallery);
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
            Toast.makeText(this, getResources().getString(R.string.error_sexo), Toast.LENGTH_SHORT).show();
            cmbSexo.requestFocus();
            return false;
        }
        if (foto==null){
            Toast.makeText(this, getResources().getString(R.string.error_f), Toast.LENGTH_SHORT).show();
            foto.requestFocus();
            return false;
        }

        return true;

    }

    public void seleccionar_foto(View v){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,
                "Seleccionar Foto"),1);
    }

    protected void onActivityResult (int i, int o, Intent data){
        super.onActivityResult(i, o, data);
        if (i == 1){
            uri = data.getData();
            if (uri != null){
                foto.setImageURI(uri);
            }
        }
    }

    private void Subir_Foto(String foto){
        StorageReference child = storageReference.child(foto);
        UploadTask uploadTask =child.putFile(uri);
    }

}
