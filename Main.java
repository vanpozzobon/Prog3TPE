import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String args[]) {
        Servicios servicios = new Servicios("procesadores.csv", "tareas.csv");
        System.out.println("******************* SERVICIO 3 ***********************");

        LinkedList<Tarea> result = (LinkedList<Tarea>) servicios.servicio3(0,100);
        for (Tarea tarea :result) {
            System.out.print("Tarea " + tarea.getId() + " Prioridad " + tarea.getPrioridad()+ "\n");
        }
    System.out.println("******************* SERVICIO 1 ***********************");
        Tarea ts1 = servicios.servicio1("T3");
        System.out.println("Tarea " + ts1.getId() + " Prioridad " + ts1.getPrioridad()+ "\n");
        System.out.println("******************* SERVICIO 2 ***********************");

        ArrayList<Tarea> ts2 = (ArrayList<Tarea>) servicios.servicio2(false);
        for (Tarea t2 : ts2)
            System.out.println("Tarea " + t2.getId() + " Prioridad " + t2.getPrioridad()+ " Es critica: "+ t2.isCritica() + "\n");
    }
}
