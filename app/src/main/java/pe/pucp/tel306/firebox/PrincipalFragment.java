package pe.pucp.tel306.firebox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import pe.pucp.tel306.firebox.Clases.Usuario;


public class PrincipalFragment extends Fragment {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PrincipalFragment() {
        // Required empty public constructor
    }


    public static PrincipalFragment newInstance() {
        PrincipalFragment fragment = new PrincipalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        documento=0;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        usuario=funcionesPrincipalFragmento.obtenerUsuario();
        Log.d("Infoaoo",""+usuario.getNombre());
        TextView text=view.findViewById(R.id.nombreUsuario);
        text.setText(usuario.getNombre());
        Button btnSubir = view.findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              subirArchivo(view);
            }
        });


        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    ///////////////////////////////////////////////////////////////////////////////////
    int documento;

    //FIREBASE STORAGE
    //SUBIR ARCHIVO AL STORAGE
    public void subirArchivo(View view) {
        Intent intent = new Intent();
        intent.setType(usuario.getUid()+"/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar archivo"), 1);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode ==1 ){
                subirArchivoStorage(data.getData());

        }
        if (requestCode == 1 ) {
            subirArchivoStorage(data.getData());

        }
    }




    private void subirArchivoStorage(Uri uri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        Log.d("infoApp", uri.getPath());

        storageReference.child("archivo.jpg")
                .putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("infoApp", "subida exitosa");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("infoApp", "error en la subida");
                        e.printStackTrace();
                    }
                });
    }




    protected void recepcionUID(String uid, String nombre)
    {


    }


    private FuncionesPrincipalFragmento funcionesPrincipalFragmento;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        funcionesPrincipalFragmento= (FuncionesPrincipalFragmento) context;
    }


    public interface FuncionesPrincipalFragmento{

       Usuario obtenerUsuario();
    }


}