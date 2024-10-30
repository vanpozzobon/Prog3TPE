
import java.util.ArrayList;
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

    private void addRecursivo(int valor, TreeNode actual) {
        if (actual.getKey() > valor) {
            if (actual.getIzq() == null) {
                actual.setIzq(new TreeNode(valor));
            } else {
                this.addRecursivo(valor, actual.getIzq());
            }
        } else if (actual.getKey() < valor) {
            if (actual.getDer() == null) {
                actual.setDer(new TreeNode(valor));
            } else {
                this.addRecursivo(valor, actual.getDer());
            }
        }
    }

    public TreeNode search_el_nodo_mayor_de_la_rama_izquierda(TreeNode t) {
        if (t.getDer() == null) {
            return t;
        } else {
            return search_el_nodo_mayor_de_la_rama_izquierda(t.getDer());
        }

    }

    // Método para imprimir el árbol en orden
    public void printInOrder(TreeNode node) {
        if (node != null) {
            printInOrder(node.getIzq());   // Imprimir el subárbol izquierdo
            System.out.print(node.getKey() + " "); // Imprimir el nodo actual
            printInOrder(node.getDer());   // Imprimir el subárbol derecho
        }
    }

    //int getHeight() metodo para obtener altura
    public int getHeight() {
        return getHeightRec(this.raiz);
    }

    private int getHeightRec(TreeNode node) {

        if (node.getDer() == null && node.getIzq() == null) {
            return 0;
        } else {
            int contadorDer = 0;
            int contadorIzq = 0;
            if (node.getIzq() != null) {
                contadorIzq = getHeightRec(node.getIzq());
            }

            if (node.getDer() != null) {
                contadorDer = getHeightRec(node.getDer());
            }

            if (contadorIzq > contadorDer) {
                return contadorIzq + 1;
            } else {

                return contadorDer + 1;
            }
        }

    }

    //List getLongestBranch()
    public List<Integer> getRamaMasLarga() {
        if (raiz == null) {
            return new ArrayList<>();
        } else {
            return getRamaMasLargaRec(raiz);
        }
    }

    private List<Integer> getRamaMasLargaRec(TreeNode node) {

        List<Integer> listaIzq = new ArrayList<>();
        List<Integer> listaDer = new ArrayList<>();

        if (node.getIzq() != null) {
            listaIzq = getRamaMasLargaRec(node.getIzq());
        }
        if (node.getIzq() != null) {
            listaDer = getRamaMasLargaRec(node.getDer());
        }

        if (listaIzq.size() > listaDer.size()) {
            listaIzq.add(node.getKey());
            return listaIzq;
        } else {
            listaDer.add(node.getKey());
            return listaDer;
        }
    }

    //list get frontera
    public List<Integer> getFrontera() {
        if (raiz == null) {
            return new ArrayList<>();
        }
        if (!raiz.tieneAmbosHijos()) {
            List<Integer> lista = new ArrayList<>();
            lista.add(raiz.getKey());
            return lista;
        }
        return getFronteraRec(raiz);
    }

    private List<Integer> getFronteraRec(TreeNode node) {
        List<Integer> lista = new ArrayList<>();
        if (node.getIzq() == null && node.getDer() == null) {
            lista.add(node.getKey());
            return lista;
        }

        if (node.getDer() != null) {

            lista.addAll(getFronteraRec(node.getDer()));
        }
        if (node.getIzq() != null) {
            lista.addAll(getFronteraRec(node.getIzq()));

        }
        return lista;

    }

    //Suma de los nodos
    public int sumarElem() {
        return sumarElementos(raiz);
    }

    private int sumarElementos(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int contador = node.getKey();

            contador += sumarElementos(node.getIzq());

            contador += sumarElementos(node.getDer());

            return contador;

        }
    }

    public List<Integer> buscarMayoresA(int k) {
        return buscarMayoresRec(raiz, k);
    }

    private List<Integer> buscarMayoresRec(TreeNode node, int k) {
        List<Integer> lista = new ArrayList<>();
        if (node == null) {
            return lista;
        }
        if (node.getKey() >= k) {
            lista.add(node.getKey());
        }

        // Llamada recursiva al subárbol izquierdo y derecho
        lista.addAll(buscarMayoresRec(node.getIzq(), k));  // Recorrer el subárbol izquierdo
        lista.addAll(buscarMayoresRec(node.getDer(), k)); // Recorrer el subárbol derecho

        return lista;  // Retornar la lista acumulada

    }

    public static void main(String[] args) {
        Arbol arbol = new Arbol();

    }
