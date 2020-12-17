package pe.pucp.tel306.firebox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            String uid = currentUser.getUid();
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            Log.d("infoApp", "uid: " + uid + " | displayName: " + displayName + " | email: " + email );

            //ACÁ INTENT HACIA OTRA ACTIVIDAD
        }

    }


    public void login (View view){

        List<AuthUI.IdpConfig> proveedores = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(proveedores).build();

        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null){
                String uid = currentUser.getUid();
                String displayName = currentUser.getDisplayName();
                String email = currentUser.getEmail();

                Log.d("infoApp", "uid: " + uid + " | displayName: " + displayName + " | email: " + email );

                //ACÁ INTENT HACIA OTRA ACTIVIDAD
            }

        }
    }
}