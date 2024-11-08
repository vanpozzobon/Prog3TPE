
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
     * La complejidad temporal del constructor en O(n2) para el peor de los casos (en el caso que el arbol sea una enredadera)
     * En un caso promedio donde tenemos un arbol balanceado es O(nlogn) donde n es la cantidad de prioridades diferentes existentes en la lista de tareas
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        this.listaTareas = new LinkedList<>();
        this.mapaTareas = new HashMap<>();
        this.arbolTareas = new Arbol();

        CSVReader reader = new CSVReader();
        this.listaProcesadores = reader.readProcessors(pathProcesadores);
        ArrayList<Tarea> tareasData = reader.readTasks(pathTareas);


        for (Tarea tarea : tareasData) {
            listaTareas.addFirst(tarea); // Añadir a LinkedList, agregar al inicio O(1)
            mapaTareas.put(tarea.getId(), tarea); // Añadir a HashMap usando el ID como clave O(1)
            arbolTareas.add(tarea); //O(n) en el peor caso
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

    /**
     * Para el desarrollo del problema por backtracking decidimos implementar una clase Estado que nos ayude a ir obteniendo las tareas
     * que podemos ir insertando en los procesadores. Pedimos la primera tarea y ejecutamos el backtracking
     * Para cada procesador, insertamos la tarea y llamamos recursivo con la siguiente Tarea.
     * Esto nos genera un arbol de exploracion en la que en cada nivel se van agregando las Tareas en diferentes Porcesadores.
     * El estado final es cuando ya no nos quedan tareas por agregar a los procesadores. Nuestro estado solucion optimo es cuando
     * el tiempo maximo de todos los procesadores que llevamos guardado en el Estado es mejor que el que tenemos en la Solucion que
     * pasamos por parametro
     */
    public Solucion backtracking(int tiempoMax) {
        //Inicializamos una solucion inicial para inicializar el Estado
        Solucion solucion_estado = new Solucion(this.listaProcesadores, this.maxCantCriticas,tiempoMax);
        Estado estado = new Estado(this.listaTareas, solucion_estado);
        //Inicializamos una solucion en la que guardaremos la mejor solucion
        Solucion solucion = new Solucion(this.listaProcesadores, this.maxCantCriticas,tiempoMax);

        backtracking(estado, tiempoMax, estado.getNexTarea(),solucion);
        solucion.setCantEstados(estado.getCantidadEstados());
        return solucion;
    }

    private void backtracking(Estado estado, int x, Tarea t,Solucion s) {
        //Si la tarea es null, quiere decir que ya se asignaron todas las tareas
        if (t == null) {
            if (estado.getSolucion().esMejorSolucion(s)) {
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
                    backtracking(estado, x, estado.getNexTarea(),s);
                    estado.quitarTarea(procesador, t);
                    estado.retrocederTarea();
                }
            }
        }

    }

    /**
     * Para el desarrollo de la solución mediante greedy usamos una estrategia que consiste en ordenar las tareas segun su tiempo
     * de forma descendente
     * Luego, para cada tarea, le pedimos a nuestra clase Solucion que nos retorne el procesador más apto para tomar dicha tarea
     * Este procesador debe cumplir con todos los requisitos.
     * Asignamos la tarea al procesador y continuamos con la siguiente
     */
    public Solucion greedy(int x) {
        //inicializar solucion vacia
        Solucion solucion = new Solucion(this.listaProcesadores, this.maxCantCriticas,x);
        List<Tarea> listaTareasOrdenadas = new LinkedList<>(listaTareas);
        // Ordenar la nueva lista por tiempo
        listaTareasOrdenadas.sort(Comparator.comparing(Tarea::getTiempo).reversed());
        boolean tiene_solucion = true;
        for (Tarea tarea : listaTareasOrdenadas) {
            Procesador procesador = solucion.getMejorProcesador(tarea);
            if (procesador != null)
                solucion.addTareaASolucion(procesador,tarea);
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
