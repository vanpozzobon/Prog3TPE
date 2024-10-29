
public class Procesador {

    private String id;
    private String codigo;
    private Boolean refrigerado;
    private Integer anio;

    // Constructor
    public Procesador(String id, String codigo, Boolean refrigerado, Integer anio) {
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Boolean isRefrigerado() {
        return refrigerado;
    }

    public Integer getAnio() {
        return anio;
    }

}
