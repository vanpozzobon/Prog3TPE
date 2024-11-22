
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Estado {
    private Tarea[] arregloTareas;
    private int indice = 0;

    public Estado(LinkedList<Tarea> tarea) {
        arregloTareas = new Tarea[tarea.size()];
        int i = 0;
        for (Tarea t : tarea) {
            arregloTareas[i] = t;
            i++;
        }
    }

    /*public Tarea[] getArregloTareas() {
        return arregloTareas;
    }*/

    public Tarea getNexTarea() {
        if (indice == arregloTareas.length) {
            return null;
        }
        return arregloTareas[indice];
    }

    public void avanzarTarea() {
        indice++;
    }

    public void retrocederTarea() {
        indice--;
    }

    public boolean es_estado_final(){
        return this.indice == this.arregloTareas.length;
    }

}
