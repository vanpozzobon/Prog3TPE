
import java.util.*;

public class Solucion {
    private ArrayList<Procesador> solucion;
    private int cantEstados;
    private int maxCantCriticas;
    private int maxCantSumaNoRefrigerados;
    public Solucion(ArrayList<Procesador> procesadores, int maxCantCriticas, int maxSuma) {
        this.solucion = new ArrayList<Procesador>(procesadores);
        this.cantEstados = 0;
        this.maxCantCriticas = maxCantCriticas;
        this.maxCantSumaNoRefrigerados = maxSuma;
    }

    public ArrayList<Procesador> getSolucion() {
        return this.solucion;
    }

    public boolean esMejorSolucion(Solucion solucion) {
        int solucionthis = this.getTiempoMaximoSolucion();
        int solucionsol = solucion.getTiempoMaximoSolucion();
        System.out.println("Solucion " + solucionthis + " LA OTRA "+ solucionsol);
        if (solucionthis > 0 && solucionsol < 0)
            return true;
        return  solucionsol > solucionthis;
    }

    /**
     * @return Retorna el tiempo acumulado del procesador que más tiempo acumulado tiene
     */
    public int getTiempoMaximoSolucion(){
        if (this.solucion.size() == 0)
            return -1;
        int mayor = -1;
        Iterator iter = this.solucion.iterator();
        while (iter.hasNext()){
            Procesador p = (Procesador)iter.next();
            if (p.getTiempoTotalTareasAsignadas() > mayor)
                mayor = p.getTiempoTotalTareasAsignadas();
        }
        return mayor;
    }
    public void cambiarASolucionOptimizada(Solucion solucion) {
        this.solucion.clear();
        this.solucion = new ArrayList<Procesador>(solucion.getSolucion());
    }

    public void addTareaASolucion(Procesador procesador, Tarea tarea) {
        for (int i=0; i< this.solucion.size();i++)
            if (this.solucion.get(i).equals(procesador)) {
                System.out.println("Agrega Tarea "+ tarea.getId());
                this.solucion.get(i).addTarea(tarea);
                break;
            }
    }

    public void removeTareaDeSolucion(Procesador procesador, Tarea tarea) {
        for (int i=0; i< this.solucion.size();i++)
            if (this.solucion.get(i).equals(procesador)) {
                System.out.println("QUITAR Tarea "+ tarea.getId());

                this.solucion.get(i).deleteTarea(tarea);
                break;
            }
    }

    /**
     *
     * @param procesador
     * @return Retorna el tiempo acumulado por todas las tareas asignadas que tiene un procesador
     */
    public int getTiempoProcesador(Procesador procesador){
        for (int i=0; i< this.solucion.size();i++)
            if (this.solucion.get(i).equals(procesador)) {
                return this.solucion.get(i).getTiempoTotalTareasAsignadas();
            }
        return -1;
    }

    public int getCantidadTareasCriticas(Procesador procesador){
        for (int i=0; i< this.solucion.size();i++)
            if (this.solucion.get(i).equals(procesador)) {
                return this.solucion.get(i).getCantidadTareasCriticas();
            }
        return 0;
    }

    public void setCantEstados(int cant) {
        this.cantEstados = cant;
    }

    public int getCantEstados() {
        return this.cantEstados;
    }

    public void printSolucion() {
        System.out.println("La solución obtenida por medio del backtracking es:");
        Iterator iter = this.solucion.iterator();
        while (iter.hasNext()) {
            Procesador p = (Procesador) iter.next();
            System.out.println("Procesador: " + p.getId());
            System.out.println("Tareas asociadas: ");
            Iterator iterTareas = p.getTareasAsignadas().iterator();
            while (iterTareas.hasNext()) {
                Tarea t = (Tarea) iterTareas.next();
                System.out.println(t.getId() + ": " + t.getNombre() + " - " + t.getTiempo());
            }
            System.out.println("La cantidad de estados generados fueron: " + cantEstados);
        }
    }

    public Procesador getMejorProcesador(Tarea tarea){
        if (this.solucion.size() == 0)
            return null;
        Procesador elMejor = null;
        for (int i=0; i< this.solucion.size();i++){
            if (this.cumple_condicion(this.solucion.get(i), tarea))
                if (elMejor == null || (this.solucion.get(i).getTiempoTotalTareasAsignadas() < elMejor.getTiempoTotalTareasAsignadas()))
                    elMejor = this.solucion.get(i);
        }
        return elMejor;
    }

    private boolean cumple_condicion(Procesador procesador, Tarea tarea){
        boolean cumple = true;
        if (tarea.isCritica() && procesador.getCantidadTareasCriticas() >= this.maxCantCriticas)
            cumple = false;
        if (!procesador.isRefrigerado() && procesador.getTiempoTotalTareasAsignadas() + tarea.getTiempo() > this.maxCantSumaNoRefrigerados)
            cumple = false;
        return cumple;
    }
}
