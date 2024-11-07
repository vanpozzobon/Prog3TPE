
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Estado {

    private Solucion solucion;
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
        this.solucion = new Solucion();
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
        System.out.println("Avanza indice "+ indice);

    }

    public void retrocederTarea() {
        indice--;
        System.out.println("Retrocede indice "+ indice);
    }

    public void agregarTarea(Procesador p, Tarea t) {
        solucion.addTareaASolucion(p, t);
    }

    public void quitarTarea(Procesador p, Tarea t) {
        solucion.removeTareaDeSolucion(p, t);
    }

    public int getTiempo(Procesador p) {
        LinkedList<Tarea> tareas = this.solucion.getTareasAsociadas(p);
        if (tareas == null)
            return 0;
        Iterator iterator = tareas.iterator();
        int tiempo = 0;
        while (iterator.hasNext())
            tiempo += (((Tarea)iterator.next()).getTiempo());
        return tiempo;
    }

    public int getTareasCriticas(Procesador p) {
        LinkedList<Tarea> tareas = this.solucion.getTareasAsociadas(p);
        if (tareas == null)
            return 0;
        Iterator iterator = tareas.iterator();
        int criticas = 0;
        while (iterator.hasNext())
            if (((Tarea)iterator.next()).isCritica())
                criticas++;
        return criticas;
    }
}
