
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

    private LinkedList<Tarea> listaTareas;
    private Map<String, Tarea> mapaTareas;
    private Arbol arbolTareas;
    private ArrayList<Procesador> listaProcesadores;
    private int maxCantCriticas = 2;

    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        this.listaTareas = new LinkedList<>();
        this.mapaTareas = new HashMap<>();
        this.arbolTareas = new Arbol();

        CSVReader reader = new CSVReader();
        this.listaProcesadores = reader.readProcessors(pathProcesadores);
        ArrayList<Tarea> tareasData = reader.readTasks(pathTareas);

        for (Tarea tarea : tareasData) {
            listaTareas.add(tarea); // Añadir a LinkedList
            mapaTareas.put(tarea.getId(), tarea); // Añadir a HashMap usando el ID como clave
            arbolTareas.add(tarea);
        }

        /*for (Procesador procesador : procesadoresData) {
            listaProcesadores.add(procesador);
        }*/
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

    public Solucion backtracking(int tiempoMax) {
        Estado estado = new Estado(listaTareas);
        Solucion solucion = new Solucion();
        backtracking(estado, tiempoMax, estado.getNexTarea(), solucion);
        solucion.setCantEstados(estado.getCantidadEstados());
        return solucion;
    }

    private void backtracking(Estado estado, int x, Tarea t, Solucion s) {
        //Si la tarea es null, quiere decir que ya se asignaron todas las tareas
        if (t == null) {
            System.out.println("ENTRA");
            if (s.esMejorSolucion(estado.getSolucion())) {
                System.out.println("ENTRA MEJOR SOLUCION");

                s.cambiarASolucionOptimizada(estado.getSolucion());

            }
        } else {
            for (Procesador procesador : listaProcesadores) {
                //Si el procesador es refrigerado o no es refrigerado y el tiempo de las tareas asociadas + la nueva tarea es menor que la indicada por el usuario
                // Y la tarea no es critica, o es critica y aun no llega al maximo de tareas criticas permitidas
                if ((procesador.isRefrigerado()
                        || (!procesador.isRefrigerado() && estado.getTiempo(procesador) + t.getTiempo() < x))
                        && (!t.isCritica()
                        || (t.isCritica() && estado.getTareasCriticas(procesador) < this.maxCantCriticas))) {
                    estado.agregarTarea(procesador, t);
                    estado.avanzarTarea();
                    backtracking(estado, x, estado.getNexTarea(), s);
                    estado.quitarTarea(procesador, t);
                    estado.retrocederTarea();
                }
            }
        }

    }

    public Solucion greedy(int x) {
        //inicializar solucion vacia
        Solucion solucion = new Solucion();
        List<Tarea> listaTareasOrdenadas = new LinkedList<>(listaTareas);
        // Ordenar la nueva lista por tiempo
        listaTareasOrdenadas.sort(Comparator.comparing(Tarea::getTiempo).reversed());

        for (Tarea tarea : listaTareasOrdenadas) {
            //Si el procesador es refrigerado o no es refrigerado y el tiempo de las tareas asociadas + la nueva tarea es menor que la indicada por el usuario
            // Y la tarea no es critica, o es critica y aun no llega al maximo de tareas criticas permitidas
            for (Procesador p : listaProcesadores) {
                if (tarea.esLaMejoropcion(p, x, solucion, maxCantCriticas)) {
                    solucion.addTareaASolucion(p, tarea);
                }
            }
        }
        if (solucion.esValida()) {
            return solucion;
        } else {
            return null;
        }
    }
}
