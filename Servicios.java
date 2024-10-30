
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos. Sólo se podrá
 * adaptar el nombre de la clase "Tarea" según sus decisiones de implementación.
 */
public class Servicios {

    private LinkedList<Tarea> listaTareas;
    private Map<String, Tarea> mapaTareas;
    private TreeSet<Tarea> arbolTareas;

    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        this.listaTareas = new LinkedList<>();
        this.mapaTareas = new HashMap<>();
        this.arbolTareas = new TreeAlgo();

        CSVReader reader = new CSVReader();
        reader.readProcessors(pathProcesadores);
        ArrayList<Tarea> tareasData = reader.readTasks(pathTareas);

        for (Tarea tarea : tareasData) {
            listaTareas.add(tarea); // Añadir a LinkedList
            mapaTareas.put(tarea.getId(), tarea); // Añadir a HashMap usando el ID como clave
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
     * para corroborar tarea por tarea
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
     * Expresar la complejidad temporal del servicio 3.
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
    }

}
