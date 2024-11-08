
import java.util.Objects;

public class Tarea {

    private String id;
    private String nombre;
    private int tiempo;
    private boolean critica;
    private int prioridad;

    public Tarea(String id, String nombre, int tiempo, boolean critica, int prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.critica = critica;
        this.prioridad = prioridad;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public boolean isCritica() {
        return critica;
    }

    public int getPrioridad() {
        return prioridad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tarea tarea = (Tarea) o;
        return tarea.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Tarea clonar(){
        Tarea t = new Tarea(this.getId(),this.getNombre(),this.getTiempo(),this.isCritica(),this.getPrioridad());
        return t;

    }
}
