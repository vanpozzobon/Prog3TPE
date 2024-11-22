import java.util.*;

public class Greedy {
    private ArrayList<Procesador> listaProcesadores;
    private LinkedList<Tarea> listaTareas;
    private Condicion condicion;

    public Greedy(ArrayList<Procesador> procesadores, LinkedList<Tarea> tareas){
        this.listaProcesadores = new ArrayList<Procesador>(procesadores);
        this.listaTareas = new LinkedList<>(tareas);
    }
    /**
     * Para el desarrollo de la solución mediante greedy usamos una estrategia que consiste en ordenar las tareas segun su tiempo
     * de forma descendente
     * Luego, para cada tarea, le pedimos a nuestra clase Solucion que nos retorne el procesador más apto para tomar dicha tarea
     * Este procesador debe cumplir con todos los requisitos.
     * Asignamos la tarea al procesador y continuamos con la siguiente
     */
    public Solucion greedy(Condicion cond) {
        this.condicion = cond;
        //inicializar solucion vacia
        Solucion solucion = new Solucion(this.listaProcesadores);
        // Ordenar la nueva lista por tiempo
        this.listaTareas.sort(Comparator.comparing(Tarea::getTiempo).reversed());
        boolean tiene_solucion = true;

        Iterator iter = this.listaTareas.iterator();
        while (iter.hasNext() && tiene_solucion){
            Tarea tarea = (Tarea)iter.next();
            Procesador elMejor = null;
            for (int i=0; i< solucion.getProcesadores().size();i++){
                solucion.incrementarEstadoGenerado();
                Procesador procesador = solucion.getProcesadores().get(i);
                //Toma el procesador que cumpla las condiciones y que su tiempo total de tareas asignadas sea menor
                if (this.condicion.cumple_condicion(procesador,tarea)){
                    if (elMejor == null || (procesador.getTiempoTotalTareasAsignadas() < elMejor.getTiempoTotalTareasAsignadas()))
                        elMejor = procesador;
                }
            }
            if (elMejor != null)
                elMejor.addTarea(tarea);
            else
                tiene_solucion = false;
        }

        if (tiene_solucion) {
            return solucion;
        } else {
            return null;
        }
    }
}
