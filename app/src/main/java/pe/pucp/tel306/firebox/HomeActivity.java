package pe.pucp.tel306.firebox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            List<MediaFile> mediaFiles = data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if (mediaFiles != null) {
                setMediaFiles(mediaFiles);
            } else {
                Toast.makeText(HomeActivity.this, "Image not selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nombreUsuario = findViewById(R.id.textViewNombre);
        fabButton = findViewById(R.id.floatingActionButton);

        fabButton.setOnClickListener(v -> {
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

            // luego de seleccionar el o los archivos, se sube a la carpeta en la nube D:

            for (MediaFile mf :
                    mediaFiles) {
                PrincipalFragment homeFragment = (PrincipalFragment) HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer2);
                if (homeFragment.isAdded()) {
                    String path = homeFragment.getPath();
                    SubirArchivoAFirebase(mf, path);
                }

            }


        });
        setContentView(R.layout.activity_home);

        abrirFragmentoPrincipal();

    }

    private void SubirArchivoAFirebase(MediaFile mediaFile, String path) {
        // aqui deberia subir el archivo a firebase utilizando el path de referencia que obtengo del fragment
        // finalmente el proceso se va a monitorear hasta que termine de subir
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