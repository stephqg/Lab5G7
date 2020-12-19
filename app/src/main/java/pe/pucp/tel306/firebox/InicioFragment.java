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
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.List;

public class InicioFragment extends Fragment {

    View vista;
    private Button botonIngresar;
    private  FirebaseUser currentUser = null;

    public InicioFragment() {
        // Required empty public constructor
    }

    public static InicioFragment newInstance() {
        InicioFragment fragment = new InicioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_inicio,container,false);
        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        botonIngresar = vista.findViewById(R.id.btnIngresar);

        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingresar();
            }

        });

    }

    public void Ingresar(){
        List<AuthUI.IdpConfig> proveedores = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent intent =
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(proveedores)
                        .build();
        startActivityForResult(intent, 2002);
    }

   @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2002){
            currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null){
                String uid = currentUser.getUid();
                String displayName = currentUser.getDisplayName();
                String email = currentUser.getEmail();

                Log.d("infoApp", "uid: " + uid + " | displayName: " + displayName + " | email: " + email );

                funciones.borrarFragmentoInicio(uid,displayName,email);


            }

        }


    }







    private Funciones funciones;
    public interface Funciones {
        void guardarRegistro(String uid, String displayName, String email);
        void borrarFragmentoInicio(String uid,String displayName,String email);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        funciones = (Funciones)context;
    }



}