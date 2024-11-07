
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos. Sólo se podrá
 * adaptar el nombre de la clase "Tarea" según sus decisiones de implementación.
 */
public class Servicios {

    private LinkedList<Tarea> listaTareas;
    private Map<String, Tarea> mapaTareas;
    private Arbol arbolTareas;
    private LinkedList<Procesador> listaProcesadores;

    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        this.listaTareas = new LinkedList<>();
        this.mapaTareas = new HashMap<>();
        this.arbolTareas = new Arbol();

        CSVReader reader = new CSVReader();
        ArrayList<Procesador> procesadoresData = reader.readProcessors(pathProcesadores);
        ArrayList<Tarea> tareasData = reader.readTasks(pathTareas);

        for (Tarea tarea : tareasData) {
            listaTareas.add(tarea); // Añadir a LinkedList
            mapaTareas.put(tarea.getId(), tarea); // Añadir a HashMap usando el ID como clave
            arbolTareas.add(tarea);
        }

        for (Procesador procesador : procesadoresData) {
            listaProcesadores.add(procesador);
        }

    }

    /*
     * Complejidad temporal O(1) ya que utilizamos un hashMap para almacenar 
     * por ID la tarea.
     */
    public Tarea servicio1(String ID) {
        return mapaTareas.get(ID);
    }

    /*
     * Complejidad temporal O(n) ya que debe recorrer toda la LinkedList
     * para corroborar tarea por tarea y asi obtener si es critica 
     */
    public List<Tarea> servicio2(boolean esCritica) {
        List<Tarea> resultado = new ArrayList<>();

        for (Tarea tarea : listaTareas) {
            if (tarea.isCritica() == esCritica) {
                resultado.add(tarea);
            }
        }

        return resultado;

    }

    /*
     * En el peor caso la complejidad de obtener todas las tareas en un rango dado es O(n) donde n es la cantidad de tareas
     * con diferente prioridad. Es O(n) ya que si la prioridad inferior solicitada es menor a la minima prioridad registrada en el arbol
     * y la prioridad superior solocitada es mayor a la prioridad de mayor rango en el arbol, el recorrido será completo
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        return arbolTareas.obtenerTareasEnRango(prioridadInferior, prioridadSuperior);
    }

    // funcion backtracking(Estado e, int x, Tarea t){
    //     Si t == null
    //     si e.es_solucion_optima()
    //     e.cambiarSolucionOptima();	
    //     sino
    //         Obtengo todos los procesadores
    //         Para cada procesador
    //                 Si e.getSumaTiempo(procesador) + tarea.tiempo < x y 
    //             (!tarea.esCritica() || (tarea.esCritica() y e.getCriticas(procesador) < 2)
    //         e.agregarTarea(procesador,tarea)
    //         backtracking(e,x,cant+1, e.getNextTarea())
    //         e.quitarTarea(procesador,tarea)
    public Solucion backtracking(int tiempoMax) {
        Estado estado = new Estado(listaTareas);
        Solucion solucion = new Solucion();
        backtracking(estado, tiempoMax, estado.getNexTarea(), solucion);
        return solucion;
    }

    private void backtracking(Estado estado, int x, Tarea t, Solucion s) {

        if (t == null) {
            if (s.esMejorSolucion(estado.getSolucion())) {
                s.cambiarASolucionOptimizada(estado.solucion);
            }
        } else {
//             Si procesador.isRefrigerado() || (!procesador.isRefrigerado() Y tiempo_acumulado(procesador) + tarea.getTiempo()) Y 
//  (!tarea.esCritica() || (tarea.esCritica() y e.getCriticas(procesador) < 2)
            for (Procesador procesador : listaProcesadores) {
                // if (procesador.isRefrigerado() || (!procesador.isRefrigerado() && tiempo_acumulado)) {
                if (estado.getTareasCriticas(procesador) < 2) {
                    if (!procesador.isRefrigerado()) {
                        if (estado.getTiempo() + t.getTiempo() < x) {
                            estado.agregarTarea(procesador, t);
                            estado.avanzarTarea();
                            backtracking(estado, x, estado.getNexTarea(), s);
                            estado.quitarTarea(procesador, t);
                            estado.retrocederTarea();
                        }
                    }

                }
            }
        }

    }
}
}
