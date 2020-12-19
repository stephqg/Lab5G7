package pe.pucp.tel306.firebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid = currentUser.getUid();
    String displayName = currentUser.getDisplayName();
    String email = currentUser.getEmail();
    TextView nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nombreUsuario = findViewById(R.id.textViewNombre);
        setContentView(R.layout.activity_home);

        abrirFragmentoPrincipal();

    }

    public void abrirFragmentoPrincipal(){

        PrincipalFragment HomeFragmento = new PrincipalFragment().newInstance();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainer2,HomeFragmento);
        fragmentTransaction.commit();
    }


    //FIREBASE STORAGE



    //FIRESTORE
    public void crearDocumento(String uid,String nombre) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //SE crea la coleccion con el documento con el uid del usuario

        //INFORAMCION BASICA DEL USUARIO
        Map<String, Object> user = new HashMap<>();
        user.put("Nombre", nombre);
        user.put("Type", "FREE");
        user.put("Capacidad", "500MB");

        DocumentReference base = db.collection("User").document(uid);
        base.set(user);

    }



}