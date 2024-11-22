import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Backtracking {
    private ArrayList<Procesador> listaProcesadores;
    private LinkedList<Tarea> listaTareas;
    private Solucion solucion;
    private Condicion condicion;
    public Backtracking(ArrayList<Procesador> procesadores, LinkedList<Tarea> tareas){
        this.listaProcesadores = new ArrayList<>(procesadores);
        this.listaTareas = new LinkedList<>(tareas);
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
        public Solucion solucionBacktracking(Condicion cond) {
        this.condicion = cond;
        this.solucion = null;
        //Inicializamos una solucion en la que guardaremos la solucion parcial
        Solucion solucion = new Solucion(this.listaProcesadores);
        //Si no hay Tareas en la lista, retornamos la solucion con los procesadores vacios
        if (this.listaTareas.isEmpty())
            return solucion;

        Estado estado = new Estado(this.listaTareas);
        this.backtracking(estado,solucion);
        this.solucion.setEstadosGenerados(solucion.getCantEstadosGenerados());
        return this.solucion;
    }

    private void backtracking(Estado estado,Solucion s) {
        //Es estado final cuando se asignaron todas las tareas
        if (estado.es_estado_final()){
            if (this.solucion == null || s.esMejorSolucion(this.solucion)) {
                this.solucion = s.copiarSolucion();
            }
        } else {
            s.incrementarEstadoGenerado();
            Tarea t = estado.getNexTarea();
            Iterator iter = s.getProcesadores().iterator();
            while (iter.hasNext()){
                Procesador procesador = (Procesador)iter.next();
                if (this.condicion.cumple_condicion(procesador,t)){
                    //Agregamos la tarea al procesador
                    procesador.addTarea(t);
                    //Avanzamos a la siguiente Tarea en nuestro Estado
                    estado.avanzarTarea();
                    backtracking(estado,s);
                    //Retrocedemos en las acciones tomadas antes del llamado recursivo
                    procesador.deleteTarea(t);
                    estado.retrocederTarea();
                }
            }
        }
    }
}
