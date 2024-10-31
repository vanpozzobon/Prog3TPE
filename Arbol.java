
import java.util.List;

public class Arbol {

    private TreeNode raiz;

    public Arbol() {
        this.raiz = null;
    }

    public void add(Tarea t) {
        if (raiz == null) {
            raiz = new TreeNode(t);
        } else {
            this.addRecursivo(t, raiz);
        }

    }
// aca empezar con los cambios a Tareas y ver si los ordenamos por que valor

    private void addRecursivo(Tarea t, TreeNode actual) {
        if (actual.getTarea().getPrioridad() > t.getPrioridad()) {
            if (actual.getIzq() == null) {
                actual.setIzq(new TreeNode(t));
            } else {
                this.addRecursivo(t, actual.getIzq());
            }
        } else if (actual.getTarea().getPrioridad() < t.getPrioridad()) {
            if (actual.getDer() == null) {
                actual.setDer(new TreeNode(t));
            } else {
                this.addRecursivo(t, actual.getDer());
            }
        }

    }

    public List<Tarea> obtenerTareasEnRango(int a, int b, List<Tarea> resultado) {
        obtenerTareasEnRangoRecursivo(this.raiz, a, b, resultado);
        return resultado;
    }

    private void obtenerTareasEnRangoRecursivo(TreeNode raiz, int prioridadInferior, int prioridadSuperior, List<Tarea> resultado) {
        if (raiz == null) {
            return;
        }
        int prioridadActual = raiz.getTarea().getPrioridad();

        if (prioridadInferior < prioridadActual) {
            obtenerTareasEnRangoRecursivo(raiz.getIzq(), prioridadInferior, prioridadSuperior, resultado);
        }

        if (prioridadInferior <= prioridadActual && prioridadActual <= prioridadSuperior) {
            resultado.add(raiz.getTarea());
        }

        if (prioridadSuperior > prioridadActual) {
            obtenerTareasEnRangoRecursivo(raiz.getDer(), prioridadInferior, prioridadSuperior, resultado);
        }

    }

    public static void main(String[] args) {
        Arbol arbol = new Arbol();

    }
}
