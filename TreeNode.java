import java.util.LinkedList;

public class TreeNode {
    private int prioridad;
    private LinkedList<Tarea> tareas;
    private TreeNode izq;
    private TreeNode der;

    public TreeNode(Tarea tarea) {
        this.prioridad = tarea.getPrioridad();
        this.tareas = new LinkedList<>();
        this.addTareaNodo(tarea);
        this.izq = null;
        this.der = null;
    }
    public void addTareaNodo(Tarea tarea){
        this.tareas.addFirst(tarea);
    }
    public int getPrioridad(){
        return this.prioridad;
    }
    public LinkedList<Tarea> getTareas() {
        return tareas;
    }

    public TreeNode getIzq() {
        return izq;
    }

    public TreeNode getDer() {
        return der;
    }

    public void setIzq(TreeNode i) {
        this.izq = i;
    }

    public void setDer(TreeNode d) {
        this.der = d;
    }

    public boolean tieneAmbosHijos() {
        return this.izq != null && this.der != null;
    }

    public boolean tieneHijos() {
        return this.izq != null || this.der != null;
    }
}
