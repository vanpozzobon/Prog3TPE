
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String args[]) {
        Servicios servicios = new Servicios("procesadores.csv", "tareas.csv");
       /*
        System.out.println("******************* SERVICIO 1 ***********************");
        Tarea ts1 = servicios.servicio1("T3");
        System.out.println("Tarea " + ts1.getId() + " Prioridad " + ts1.getPrioridad() + "\n");
        System.out.println("******************* SERVICIO 2 ***********************");

        LinkedList<Tarea> ts2 = (LinkedList<Tarea>) servicios.servicio2(false);
        for (Tarea t2 : ts2) {
            System.out.println("Tarea " + t2.getId() + " Prioridad " + t2.getPrioridad() + " Es critica: " + t2.isCritica() + "\n");
        }*/
        /*
         System.out.println("******************* SERVICIO 3 ***********************");

        LinkedList<Tarea> result = (LinkedList<Tarea>) servicios.servicio3(40, 80);
        for (Tarea tarea : result) {
            System.out.print("Tarea " + tarea.getId() + " Prioridad " + tarea.getPrioridad() + "\n");
        }
        */

        Condicion c1 = new CondicionTiempoMaximo(100);
        Condicion c2 = new CondicionTareasCriticas(2);
        Condicion condicion = new CondicionAND(c1,c2);

        System.out.println("******************* BACKTRACKING ***********************");
        Solucion solucion = servicios.getSolucionBacktracking(condicion);
        if (solucion != null) {
            System.out.println("La solucion por backtracking es: ");
            solucion.printSolucion();
            System.out.println("La cantidad de estados generados fue: " + solucion.getCantEstadosGenerados());
        }else
            System.out.println("No se pudo calcular una solución factible");

        System.out.println("******************* GREEDY ***********************");
        Solucion solucion1 = servicios.getSolucionGreedy(condicion);
        if (solucion1 != null) {
            System.out.println("La solucion por Greedy es: ");
            solucion1.printSolucion();
            System.out.println("La cantidad de adyacentes generados fue: " + solucion1.getCantEstadosGenerados());
        }else
            System.out.println("No se pudo calcular una solución factible");


    }
}
