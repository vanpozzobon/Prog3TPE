
import java.util.LinkedList;
import java.util.Map;

public class Estado {

    private Map<String, LinkedList<Tarea>> solucion;
    private int tiempo;
    private int cantEstadosGenerados;
    private Tarea[] arregloTareas;
    private int indice = 0;

    public Estado(LinkedList<Tarea> tarea) {
        arregloTareas = new Tarea[tarea.size()];
        int indice = 0;
        for (Tarea t : tarea) {
            arregloTareas[indice] = t;
            indice++;
        }
    }

    public Tarea getNexTarea() {
        if (indice > arregloTareas.length) {
            return null;
        }
        return arregloTareas[indice];
    }

    public Map<String, LinkedList<Tarea>> getSolucion() {
        return solucion;
    }

    public void setSolucion(Map<String, LinkedList<Tarea>> solucion) {
        this.solucion = solucion;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(int t) {
        this.tiempo = t;
    }

    public Integer getCantidadEstados() {
        return cantEstadosGenerados;
    }

    public void setCantidadEstados(int c) {
        this.cantEstadosGenerados = c;
    }

}
