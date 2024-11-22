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
    private int cantTareasCriticas;
    public Procesador(String id, String codigo, boolean refrigerado, int anio) {
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
        this.tareasAsignadas = new LinkedList<Tarea>();
        this.tiempoTotal  = 0;
        this.cantTareasCriticas = 0;
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
        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addTarea(Tarea tarea){
        this.tareasAsignadas.addFirst(tarea);
        this.tiempoTotal += tarea.getTiempo();
        if (tarea.isCritica())
            this.cantTareasCriticas++;
    }
    public void deleteTarea(Tarea tarea){
        boolean eliminado = this.tareasAsignadas.remove(tarea);
        if (eliminado) {
            this.tiempoTotal -= tarea.getTiempo();
            if (tarea.isCritica())
                this.cantTareasCriticas--;
        }
    }

    public int getCantidadTareasCriticas(){
       return this.cantTareasCriticas;
    }

    public int getTiempoTotalTareasAsignadas(){
        return this.tiempoTotal;
    }

    public LinkedList<Tarea> getTareasAsignadas(){
        return this.tareasAsignadas;
    }
    public void setTareasAsignadas(LinkedList<Tarea> tareas){
       /* Iterator iter = tareas.iterator();
        this.tareasAsignadas.clear();
        while (iter.hasNext()){
            Tarea t = (Tarea)iter.next();
            this.tareasAsignadas.addFirst(t);
        }*/
        this.tareasAsignadas = new LinkedList<>(tareas);
    }
    public void setTiempoTotal(int tiempo){
        this.tiempoTotal = tiempo;
    }
    private void setTareasCriticas(int tareasCriticas){
        this.cantTareasCriticas = tareasCriticas;
    }
    public Procesador clonar(){
        Procesador p = new Procesador(this.getId(),this.getCodigo(),this.isRefrigerado(),this.getAnio());
        p.setTareasAsignadas(this.getTareasAsignadas());
        p.setTiempoTotal(this.getTiempoTotalTareasAsignadas());
        p.setTareasCriticas(this.cantTareasCriticas);
        return p;
    }
}
