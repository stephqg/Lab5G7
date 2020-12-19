package pe.pucp.tel306.firebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final int FILE_REQUEST_CODE = 1;
    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid = currentUser.getUid();
    String displayName = currentUser.getDisplayName();
    String email = currentUser.getEmail();
    TextView nombreUsuario;
    Button fabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nombreUsuario = findViewById(R.id.textViewNombre);
        fabButton = findViewById(R.id.floatingActionButton);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aqui primero se muestra el dialogo de obtener archivo desde el cel
                Intent intent = new Intent(HomeActivity.this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .setShowFiles(true)
                        .setShowImages(true)
                        .setShowAudios(true)
                        .setShowVideos(true)
                        .setIgnoreNoMedia(false)
                        .enableVideoCapture(true)
                        .enableImageCapture(true)
                        .setIgnoreHiddenFile(true)
                        // .setMaxSelection(10)
                        // .setTitle("Select a file")
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);

                // luego de seleccionar el archivo, se sube a la carpeta en la nube D:


                // finalmente el proceso se va a monitorear hasta que termine de subir


            }
        });
        setContentView(R.layout.activity_home);

        abrirFragmentoPrincipal();

    }

    public void abrirFragmentoPrincipal() {

        PrincipalFragment HomeFragmento = new PrincipalFragment().newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainer2, HomeFragmento);
        fragmentTransaction.commit();
    }

    private void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles.clear();
        this.mediaFiles.addAll(mediaFiles);
        // esto ponerlo en un recyclerview
        // fileListAdapter.notifyDataSetChanged();
    }


}