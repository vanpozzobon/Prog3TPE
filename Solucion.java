
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Solucion {

    private HashMap<String, LinkedList<Tarea>> solucion;
    public Solucion(){
        this.solucion = new HashMap<String, LinkedList<Tarea>>();
    }

    public HashMap<String, LinkedList<Tarea>> getSolucion(){
        return this.solucion;
    }

    public boolean esMejorSolucion(Solucion solucion){
        if (solucion == null)
            return false;
        return (solucion.calcularTiempoMaximoSolucion() < this.calcularTiempoMaximoSolucion());
    }

    public void cambiarASolucionOptimizada(Solucion solucion){
        this.solucion = (HashMap<String, LinkedList<Tarea>>) solucion.getSolucion().clone();
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
    private int calcularTiempoMaximoSolucion(){
        int mayor = 0;
        for (Map.Entry<String, LinkedList<Tarea>> entry : solucion.entrySet()) {
            int suma = this.calcularTiempoMaximoPorProcesador(entry.getKey());
            if (suma > mayor)
                mayor = suma;
        }
        return mayor;
    }
    private int calcularTiempoMaximoPorProcesador(String key){
        LinkedList<Tarea> tareas = this.solucion.get(key);
        int total = 0;
        for (Tarea tarea : tareas){
            total += tarea.getTiempo();
        }
        return total;
    }

    public LinkedList<Tarea> getTareasAsociadas(Procesador procesador){
        if (this.solucion.containsKey(procesador.getId()))
            return this.solucion.get(procesador.getId());
        return null;
    }
}
