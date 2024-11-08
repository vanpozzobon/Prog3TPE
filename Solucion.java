
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Solucion {

    private HashMap<String, LinkedList<Tarea>> solucion;

    private int cantEstados;

    public Solucion() {
        this.solucion = new HashMap<String, LinkedList<Tarea>>();
        this.cantEstados = 0;
    }

    public HashMap<String, LinkedList<Tarea>> getSolucion() {
        return this.solucion;
    }

    public boolean esMejorSolucion(Solucion solucion) {
        if (solucion == null) {
            return false;
        }
        System.out.println("MAP Mejor" + this.solucion.toString());
        System.out.println("MAP 2" + solucion.getSolucion().toString());
        int tiemposol = solucion.calcularTiempoMaximoSolucion();
        int tiempoActual = this.calcularTiempoMaximoSolucion();
        if (tiempoActual < 0 && tiemposol >= 0) {
            return true;
        }
        return (tiemposol < tiempoActual);
    }

    public void cambiarASolucionOptimizada(Solucion solucion) {
        //this.solucion = (HashMap<String, LinkedList<Tarea>>) solucion.getSolucion().clone();
        this.solucion.clear();
        for (Map.Entry<String, LinkedList<Tarea>> entry : solucion.getSolucion().entrySet()) {
            LinkedList<Tarea> tareasaux = (LinkedList<Tarea>) entry.getValue().clone();
            this.solucion.put(entry.getKey(), tareasaux);
        }
    }

    public void addTareaASolucion(Procesador procesador, Tarea tarea) {
        if (this.solucion.containsKey(procesador.getId())) {
            LinkedList<Tarea> listaaux = this.solucion.get(procesador.getId());
            if (listaaux != null) {
                listaaux.addFirst(tarea);

            } else {
                listaaux = new LinkedList<Tarea>();
                listaaux.addFirst(tarea);
                this.solucion.put(procesador.getId(), listaaux);

            }
        } else {
            LinkedList<Tarea> tareas = new LinkedList<Tarea>();
            tareas.addFirst(tarea);
            this.solucion.put(procesador.getId(), tareas);

        }
    }

    public void removeTareaDeSolucion(Procesador procesador, Tarea tarea) {
        if (this.solucion.containsKey(procesador.getId())) {
            LinkedList<Tarea> listaaux = this.solucion.get(procesador.getId());
            if (listaaux != null) {
                listaaux.remove(tarea);
            }
        }
    }

    private int calcularTiempoMaximoSolucion() {
        int mayor = -1;
        for (Map.Entry<String, LinkedList<Tarea>> entry : solucion.entrySet()) {
            int suma = this.calcularTiempoMaximoPorProcesador(entry.getKey());
            if (suma > mayor) {
                mayor = suma;
            }
        }
        System.out.println("MAYOR " + mayor);
        return mayor;
    }

    public int calcularTiempoMaximoPorProcesador(String key) {
        LinkedList<Tarea> tareas = this.solucion.get(key);
        int total = 0;
        for (Tarea tarea : tareas) {
            total += tarea.getTiempo();
        }
        return total;
    }

    public LinkedList<Tarea> getTareasAsociadas(Procesador procesador) {
        if (this.solucion.containsKey(procesador.getId())) {
            return this.solucion.get(procesador.getId());
        }
        return null;
    }

    public void setCantEstados(int cant) {
        this.cantEstados = cant;
    }

    public int getCantEstados() {
        return this.cantEstados;
    }

    public void printSolucion() {
        System.out.println("La solución obtenida por medio del backtracking es:");
        for (Map.Entry<String, LinkedList<Tarea>> entry : solucion.entrySet()) {
            System.out.println("Procesador: " + entry.getKey());
            System.out.println("Tareas asociadas: ");
            Iterator iter = (Iterator) entry.getValue().iterator();
            while (iter.hasNext()) {
                Tarea t = (Tarea) iter.next();
                System.out.println(t.getId() + ": " + t.getNombre() + " - " + t.getTiempo());
            }
        }
        System.out.println("La cantidad de estados generados fueron: " + cantEstados);
    }

}
