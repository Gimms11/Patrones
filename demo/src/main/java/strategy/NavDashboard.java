package strategy;

public class NavDashboard implements NavegacionStrategy {
    private service.PermisosNavegacion fachada;

    public NavDashboard() {
        this.fachada = service.PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        fachada.navegarA("Dashboard");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("Dashboard");
    }

    @Override
    public String getNombrePantalla() {
        return "Dashboard";
    }

}
