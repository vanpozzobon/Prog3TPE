public class CondicionAND extends Condicion{
    private Condicion cond1;
    private Condicion cond2;

    public CondicionAND(Condicion c1, Condicion c2){
        this.cond1 = c1;
        this.cond2 = c2;
    }
    public boolean cumple_condicion(Procesador procesador, Tarea tarea){
        return this.cond1.cumple_condicion(procesador,tarea) && this.cond2.cumple_condicion(procesador,tarea);
    }
}
