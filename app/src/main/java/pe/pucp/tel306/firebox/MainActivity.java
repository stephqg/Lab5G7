package pe.pucp.tel306.firebox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import pe.pucp.tel306.firebox.Clases.Usuario;







public class MainActivity extends AppCompatActivity implements InicioFragment.Funciones,PrincipalFragment.FuncionesPrincipalFragmento{

    String miUID;
    String nombre;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        abrirFragmentoInicio();

    }


    //FUNCION PARA SOLO ABRIR EL FRAGMENTO INGRESO CON CONTRASEÑA
    public void abrirFragmentoInicio(){
        InicioFragment inicioSesionFragmento = new InicioFragment().newInstance();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainer,inicioSesionFragmento);
        fragmentTransaction.commit();
    }

    @Override
    public void guardarRegistro(String uid, String displayName, String email) {
        miUID= uid;
        nombre=displayName;
        correo=email;

        //CON ESTO SE SUPONE SE DEBERÍA ABRIR EL FRAGMENTO PRINCIPAL DONDE IRÁ TODA LA INFO
        PrincipalFragment f = (PrincipalFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        f.recepcionUID(uid,displayName);
    }



    //FUNCION PARA BORRAR FRAGMENTO DE INICIO
    @Override
    public void borrarFragmentoInicio(String uid, String displayName, String email) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        InicioFragment inicioSesionFragmento = (InicioFragment) supportFragmentManager.findFragmentById(R.id.fragmentContainer);
        if (inicioSesionFragmento != null) { //SI HAY UNFRAGMENTO QUE BORRAR SE INGRESA AL IF
            //se inicia la transaccion
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.remove(inicioSesionFragmento);
            fragmentTransaction.commit();
        }

        miUID=uid;
        nombre=displayName;
        correo=email;

        crearDocumento(miUID,nombre,correo);
        abrirFragmentoPrincipal();

    }


    public void abrirFragmentoPrincipal(){

        PrincipalFragment principalFragment = new PrincipalFragment().newInstance();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainer,principalFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }



    //FIRESTORE
    public void crearDocumento(String uid,String nombre,String correo) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //SE crea la coleccion con el documento con el uid del usuario
        Usuario usuario=new Usuario(nombre,"FREE","500MB",correo,uid);
        DocumentReference base = db.collection("User").document(uid);
        base.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("INFOAPP", "SUBIO");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("INFOAPP", "no");
                e.printStackTrace();

            }
        });


    }


    @Override
    public Usuario obtenerUsuario() {
        Usuario usuario=new Usuario(nombre,"FREE","500MB",correo,miUID);
       return usuario;
    }
}