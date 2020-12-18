package pe.pucp.tel306.firebox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {


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

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        Button botonInicio = view.findViewById(R.id.btnIngresar);

        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AuthUI.IdpConfig> proveedores = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                );

                Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(proveedores).build();

                startActivityForResult(intent, 1);
            }

        });


        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null){
                String uid = currentUser.getUid();
                String displayName = currentUser.getDisplayName();
                String email = currentUser.getEmail();

                Log.d("infoApp", "uid: " + uid + " | displayName: " + displayName + " | email: " + email );

            }

        }

    }

    public interface Funciones {
        void guardarRegistro(String uid, String displayName, String email);
        void borrarFragmentoInicio();
    }

    private Funciones funciones;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        funciones = (Funciones)context;
    }
}