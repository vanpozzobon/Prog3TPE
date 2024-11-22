
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CSVReader {

    public CSVReader() {
    }

    public void readTasks(String taskPath,
                                       HashMap<String, Tarea> mapaTareas,
                                       LinkedList<Tarea> listaTareasNoCriticas,
                                       LinkedList<Tarea> listaTareasCriticas,
                                       Arbol arbolTareas) {

        // Obtengo una lista con las lineas del archivo
        // lines.get(0) tiene la primer linea del archivo
        // lines.get(1) tiene la segunda linea del archivo... y así
        ArrayList<String[]> lines = this.readContent(taskPath);

        for (String[] line : lines) {
            if (line.length > 4){
                // Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
                String id = line[0].trim();
                String nombre = line[1].trim();
                Integer tiempo = Integer.parseInt(line[2].trim());
                Boolean critica = Boolean.parseBoolean(line[3].trim());
                Integer prioridad = Integer.parseInt(line[4].trim());
                // Aca instanciar lo que necesiten en base a los datos leidos
                Tarea tarea = new Tarea(id, nombre, tiempo, critica, prioridad);
                if (tarea.isCritica())
                    listaTareasCriticas.addFirst(tarea); // Añadir a LinkedList, agregar al inicio O(1)
                else
                    listaTareasNoCriticas.addFirst(tarea);
                mapaTareas.put(tarea.getId(), tarea); // Añadir a HashMap usando el ID como clave O(1)
                arbolTareas.add(tarea); //O(n) en el peor caso
            }
        }
    }

    public ArrayList<Procesador> readProcessors(String processorPath) {

        // Obtengo una lista con las lineas del archivo
        // lines.get(0) tiene la primer linea del archivo
        // lines.get(1) tiene la segunda linea del archivo... y así
        ArrayList<String[]> lines = this.readContent(processorPath);
        ArrayList<Procesador> procesadores = new ArrayList<Procesador>();

        for (String[] line : lines) {
            // Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
            String id = line[0].trim();
            String codigo = line[1].trim();
            Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
            Integer anio = Integer.parseInt(line[3].trim());

            // Aca instanciar lo que necesiten en base a los datos leidos
            procesadores.add(new Procesador(id, codigo, refrigerado, anio));
        }
        return procesadores;

    }

    private ArrayList<String[]> readContent(String path) {
        ArrayList<String[]> lines = new ArrayList<String[]>();

        File file = new File(path);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                lines.add(line.split(";"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (bufferedReader != null)
				try {
                bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return lines;
    }

}
