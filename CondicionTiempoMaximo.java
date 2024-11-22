public class CondicionTiempoMaximo extends Condicion{
    private int tiempoMaximo;
    public CondicionTiempoMaximo( int tiempoMaximo) {

        this.tiempoMaximo = tiempoMaximo;
    }

    public boolean cumple_condicion(Procesador procesador, Tarea tarea){
        return (procesador.isRefrigerado()
                || (!procesador.isRefrigerado() &&
                procesador.getTiempoTotalTareasAsignadas() + tarea.getTiempo() <= this.tiempoMaximo));
    }
}
