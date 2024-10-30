
public class TreeNode {

    private Tarea tarea;
    private TreeNode izq;
    private TreeNode der;

    public TreeNode(Tarea t) {
        this.tarea = t;
        this.izq = null;
        this.der = null;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public TreeNode getIzq() {
        return izq;
    }

    public TreeNode getDer() {
        return der;
    }

    public void setTarea(Tarea t) {
        this.tarea = t;
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
