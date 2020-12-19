package pe.pucp.tel306.firebox.Clases;

public class Usuario {

    private String nombre;
    private String type;
    private String capacidad;

    public Usuario(String nombre, String type, String capacidad) {
        this.nombre = nombre;
        this.type = type;
        this.capacidad = capacidad;
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
