
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Solucion {

    private HashMap<String, LinkedList<Tarea>> solucion;
    private int tiempoMax = -1;
    public Solucion(){
        this.solucion = new HashMap<String, LinkedList<Tarea>>();
    }

    public HashMap<String, LinkedList<Tarea>> getSolucion(){
        return this.solucion;
    }

    public boolean esMejorSolucion(Solucion solucion){
        if (solucion == null)
            return false;
        if (this.tiempoMax < 0)
            return true;
        return (solucion.getMaximoTiempo() < this.getMaximoTiempo());
    }

    public int getMaximoTiempo(){
        return this.tiempoMax;
    }

    public void cambiarASolucionOptimizada(Solucion solucion){
        this.solucion = (HashMap<String, LinkedList<Tarea>>) solucion.getSolucion().clone();
        this.tiempoMax = solucion.getMaximoTiempo();
    }

    public void addTareaASolucion(Procesador procesador, Tarea tarea){
        if (this.solucion.containsKey(procesador.getId())){
            LinkedList<Tarea> listaaux = this.solucion.get(procesador.getId());
            if (listaaux != null){
                listaaux.addFirst(tarea);
            }else{
                listaaux = new LinkedList<Tarea>();
                listaaux.addFirst(tarea);
                this.solucion.put(procesador.getId(),listaaux);
            }
        }else {
            LinkedList<Tarea> tareas = new LinkedList<Tarea>();
            tareas.addFirst(tarea);
            this.solucion.put(procesador.getId(), tareas);
        }
    }

    public void removeTareaDeSolucion(Procesador procesador, Tarea tarea){
        if (this.solucion.containsKey(procesador.getId())){
            LinkedList<Tarea> listaaux = this.solucion.get(procesador.getId());
            if (listaaux != null)
                listaaux.remove(tarea);
        }
    }
}
