package strategy;

public class NavProductos implements NavegacionStrategy {
    private service.PermisosNavegacion fachada;

    public NavProductos() {
        this.fachada = service.PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        fachada.navegarA("GenProducts");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("GenProducts");
    }

    @Override
    public String getNombrePantalla() {
        return "Generacion de Productos";
    }

}
