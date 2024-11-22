
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos. Sólo se podrá
 * adaptar el nombre de la clase "Tarea" según sus decisiones de implementación.
 */
public class Servicios {

    private LinkedList<Tarea> listaTareasCriticas;
    private LinkedList<Tarea> listaTareasNoCriticas;
    private HashMap<String, Tarea> mapaTareas;
    private Arbol arbolTareas;
    private ArrayList<Procesador> listaProcesadores;
    private int maxCantCriticas = 2;
    /*
     * La complejidad temporal del constructor en O(n) donde n es la cantidad de tareas + O(m) donde m es la cantidad de procesadores que contiene el archivo
     *
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        this.listaTareasCriticas = new LinkedList<>();
        this.listaTareasNoCriticas = new LinkedList<>();
        this.mapaTareas = new HashMap<>();
        this.arbolTareas = new Arbol();

        CSVReader reader = new CSVReader();
        this.listaProcesadores = reader.readProcessors(pathProcesadores); //O(m) donde m es la cantidad de procesadores que contiene el archivo
        reader.readTasks(pathTareas,this.mapaTareas, this.listaTareasNoCriticas,this.listaTareasCriticas, this.arbolTareas);
    }

    /*
     * Complejidad temporal O(1) ya que utilizamos un hashMap para almacenar 
     * por ID la tarea.
     */
    public Tarea servicio1(String ID) {
        return mapaTareas.get(ID);
    }

    /*
     * Complejidad temporal O(1) ya que evalua esCritica y retorna la lista correspondiente segun su valor
     */
    public List<Tarea> servicio2(boolean esCritica) {
        if (esCritica)
            return this.listaTareasCriticas;
        else
            return this.listaTareasNoCriticas;
    }

    /*
     * En el peor caso la complejidad de obtener todas las tareas en un rango dado es O(n) donde n es la cantidad de tareas
     * con diferente prioridad. Es O(n) ya que si la prioridad inferior solicitada es menor a la minima prioridad registrada en el arbol
     * y la prioridad superior solocitada es mayor a la prioridad de mayor rango en el arbol, el recorrido será completo
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        return arbolTareas.obtenerTareasEnRango(prioridadInferior, prioridadSuperior);
    }

    public Solucion getSolucionBacktracking(Condicion cond){
        LinkedList<Tarea> allTareas = new LinkedList<>(this.listaTareasCriticas);
        allTareas.addAll(this.listaTareasNoCriticas);
        ArrayList<Procesador> pro = new ArrayList<>(this.listaProcesadores);
        Backtracking backtracking = new Backtracking(pro,allTareas);
        return backtracking.solucionBacktracking(cond);
    }

    public Solucion getSolucionGreedy(Condicion cond){
        LinkedList<Tarea> allTareas = new LinkedList<>(this.listaTareasCriticas);
        allTareas.addAll(this.listaTareasNoCriticas);
        Greedy greedy = new Greedy(this.listaProcesadores, allTareas);
        return greedy.greedy(cond);
    }
}
