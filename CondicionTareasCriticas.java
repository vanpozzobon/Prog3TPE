public class CondicionTareasCriticas extends Condicion{
    private int maxCantCriticas;
    public CondicionTareasCriticas(int maxCantCriticas){
        this.maxCantCriticas = maxCantCriticas;
    }

    public boolean cumple_condicion(Procesador procesador, Tarea tarea){
        // Si la tarea no es critica, o es critica y aun no llega al maximo de tareas criticas permitidas
        return (!tarea.isCritica()
                || (tarea.isCritica() && procesador.getCantidadTareasCriticas() < this.maxCantCriticas));
    }
}
