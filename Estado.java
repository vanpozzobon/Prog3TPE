
import java.util.LinkedList;

public class Estado {

    Solucion solucion;
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

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }

    public Integer getCantidadEstados() {
        return cantEstadosGenerados;
    }

    public void setCantidadEstados(int c) {
        this.cantEstadosGenerados = c;
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

    public int getTiempo() {
        return solucion.getMaximoTiempo();
    }

    public int getTareasCriticas(Procesador p) {
        return solucion.getCriticas(p);
    }
}
