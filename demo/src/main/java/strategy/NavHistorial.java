package strategy;

public class NavHistorial implements NavegacionStrategy {
    private service.PermisosNavegacion fachada;

    public NavHistorial() {
        this.fachada = service.PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        fachada.navegarA("History");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("History");
    }

    @Override
    public String getNombrePantalla() {
        return "Historial de Facturas";
    }

}
