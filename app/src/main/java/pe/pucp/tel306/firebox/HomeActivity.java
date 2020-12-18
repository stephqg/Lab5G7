package pe.pucp.tel306.firebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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



}