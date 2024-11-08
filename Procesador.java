import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class Procesador {
    private String id;
    private String codigo;
    private boolean refrigerado;
    private int anio;

    private LinkedList<Tarea> tareasAsignadas;
    private int tiempoTotal;
    public Procesador(String id, String codigo, boolean refrigerado, int anio) {
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
        this.tareasAsignadas = new LinkedList<Tarea>();
        this.tiempoTotal  = 0;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isRefrigerado() {
        return refrigerado;
    }

    public int getAnio() {
        return anio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procesador that = (Procesador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addTarea(Tarea tarea){
        this.tareasAsignadas.addFirst(tarea);
        this.tiempoTotal += tarea.getTiempo();
    }
    public void deleteTarea(Tarea tarea){
        boolean eliminado = this.tareasAsignadas.remove(tarea);
        if (eliminado)
            this.tiempoTotal -= tarea.getTiempo();
    }

    public int getCantidadTareasCriticas(){
        if (this.tareasAsignadas.size() == 0) {
            return 0;
        }
        Iterator iterator = this.tareasAsignadas.iterator();
        int criticas = 0;
        while (iterator.hasNext()) {
            if (((Tarea) iterator.next()).isCritica()) {
                criticas++;
            }
        }
        return criticas;
    }

    public int getTiempoTotalTareasAsignadas(){
        return this.tiempoTotal;
    }

    public LinkedList<Tarea> getTareasAsignadas(){
        return this.tareasAsignadas;
    }
}
