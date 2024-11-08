
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Estado {

    private Solucion solucion;
    private int cantEstadosGenerados;
    private Tarea[] arregloTareas;
    private int indice = 0;

    public Estado(LinkedList<Tarea> tarea, Solucion inicial) {
        arregloTareas = new Tarea[tarea.size()];
        int indice = 0;
        for (Tarea t : tarea) {
            arregloTareas[indice] = t;
            indice++;
        }
        this.solucion = inicial;
    }

    public Tarea[] getArregloTareas() {
        return arregloTareas;
    }

    public Tarea getNexTarea() {
        if (indice > arregloTareas.length - 1) {
            return null;
        }
        this.cantEstadosGenerados++;
        return arregloTareas[indice];
    }

    public Solucion getSolucion() {
        return this.solucion;
    }

    public Integer getCantidadEstados() {
        return this.cantEstadosGenerados;
    }

    public void avanzarTarea() {
        indice++;
    }

    public void retrocederTarea() {
        indice--;
    }

    public void agregarTarea(Procesador p, Tarea t) {
        solucion.addTareaASolucion(p, t);
    }

    public void quitarTarea(Procesador p, Tarea t) {
        solucion.removeTareaDeSolucion(p, t);
    }

    public int getTiempo(Procesador p) {
        return this.solucion.getTiempoProcesador(p);
    }

    public int getTareasCriticas(Procesador p) {
        return this.solucion.getCantidadTareasCriticas(p);
    }
}
