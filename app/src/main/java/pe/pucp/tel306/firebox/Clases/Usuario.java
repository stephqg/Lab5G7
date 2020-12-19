package pe.pucp.tel306.firebox.Clases;

public class Usuario {

    private String nombre;
    private String type;
    private String capacidad;
    private String correo;
    private String uid;

    public Usuario(String nombre, String type, String capacidad,String correo,String uid) {
        this.nombre = nombre;
        this.type = type;
        this.capacidad = capacidad;
        this.correo=correo;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Usuario() {

    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }
}
