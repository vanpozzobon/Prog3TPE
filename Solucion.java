
import java.util.*;

public class Solucion {
    private ArrayList<Procesador> procesadores;
    private int cantEstados;
    public Solucion(ArrayList<Procesador> procesadores) {
        this.procesadores = procesadores;
    }

    public ArrayList<Procesador> getProcesadores() {
        return this.procesadores;
    }

    public boolean esMejorSolucion(Solucion solucion) {
        int solucionthis = this.getTiempoMaximoSolucion();
        int solucionsol = solucion.getTiempoMaximoSolucion();
        if (solucionthis > 0 && solucionsol == 0)
            return true;
        return  solucionsol > solucionthis;
    }

    /**
     * @return Retorna el tiempo acumulado del procesador que más tiempo acumulado tiene
     */
    public int getTiempoMaximoSolucion(){
        int mayor = 0;
        Iterator iter = this.procesadores.iterator();
        while (iter.hasNext()){
            Procesador p = (Procesador)iter.next();
            if (p.getTiempoTotalTareasAsignadas() > mayor)
                mayor = p.getTiempoTotalTareasAsignadas();
        }
        return mayor;
    }

    public Solucion copiarSolucion(){
        ArrayList<Procesador> aux = new ArrayList<>();
        Iterator iter = this.procesadores.iterator();
        while (iter.hasNext()){
            Procesador p = (Procesador)iter.next();
            aux.add(p.clonar());
        }
        Solucion sol = new Solucion(aux);
        sol.setEstadosGenerados(this.cantEstados);
        return sol;
    }

    public void printSolucion() {
        Iterator iter = this.procesadores.iterator();
        while (iter.hasNext()) {
            Procesador p = (Procesador) iter.next();
            System.out.println("Procesador: " + p.getId() + " -> Refrigerado: " + p.isRefrigerado());
            System.out.println("Tareas asociadas: ");
            Iterator iterTareas = p.getTareasAsignadas().iterator();
            while (iterTareas.hasNext()) {
                Tarea t = (Tarea) iterTareas.next();
                System.out.println(t.getId() + ": " + t.getNombre() + " - " + t.getTiempo() + " Es critica: "+ t.isCritica());
            }
            System.out.println("----------------------------------------------------------");
        }
        System.out.println("Tiempo maximo de ejecucion " + this.getTiempoMaximoSolucion());
    }

    public void incrementarEstadoGenerado(){
        this.cantEstados++;
    }

    public void setEstadosGenerados(int cantidad){
        this.cantEstados = cantidad;
    }

    public int getCantEstadosGenerados(){
        return this.cantEstados;
    }
}
