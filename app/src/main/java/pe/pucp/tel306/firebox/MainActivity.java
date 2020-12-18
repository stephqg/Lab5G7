package pe.pucp.tel306.firebox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;



public class MainActivity extends AppCompatActivity implements InicioFragment.Funciones{

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
    public void borrarFragmentoInicio() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        InicioFragment inicioSesionFragmento = (InicioFragment) supportFragmentManager.findFragmentById(R.id.fragmentContainer);
        if (inicioSesionFragmento != null) { //SI HAY UNFRAGMENTO QUE BORRAR SE INGRESA AL IF
            //se inicia la transaccion
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.remove(inicioSesionFragmento);
            fragmentTransaction.commit();

        }
    }





}