package com.example.android.personasmaterial;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.security.Principal;

public class DetallePersona extends Activity {
    private TextView lblCedula, lblNombre, lblApellido;
    private ImageView Foto;
    private Bundle bundle;
    private Intent i;
    private String ced, nom, apell, foto, id;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_persona);

        lblCedula = findViewById(R.id.txtCedulaE);
        lblNombre = findViewById(R.id.txtNombreE);
        lblApellido = findViewById(R.id.txtApellidoE);
        Foto = findViewById(R.id.fotoE);
        storageReference = FirebaseStorage.getInstance().getReference();
        i = getIntent();
        bundle = i.getBundleExtra("datos");
        ced =bundle.getString("cedula");
        nom =bundle.getString("nombre");
        apell =bundle.getString("apellido");
        foto = bundle.getString("foto");
        id = bundle.getString("id");

        lblCedula.setText(ced);
        lblNombre.setText(nom);
        lblApellido.setText(apell);
        storageReference.child(foto).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Foto);
            }
        });

    }

    public void Eliminar (View v){
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.titulo_eliminar));
        builder.setMessage(getResources().getString(R.string.mensaje_eliminar));
        builder.setMessage(getResources().getString(R.string.mensaje_eliminar));
        positivo = getResources().getString(R.string.si);
        negativo= getResources().getString(R.string.no);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Persona p = new Persona();
                p.setId(id);
                p.EliminarP();
                onBackPressed();
            }
        });

        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog= builder.create();
        dialog.show();

        Persona p = new Persona();
        p.setId(id);
        p.EliminarP();
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(DetallePersona.this, principal.class);
        startActivity(i);
    }
}
