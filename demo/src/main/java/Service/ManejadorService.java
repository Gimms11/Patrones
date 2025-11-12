package service;

import DTO.Empresa;
import DTO.Usuario;
import Interfaces.ManejadorJsonI;
import Lectores.ManejadorEmpresa;
import Lectores.ManejadorSesion;

public class ManejadorService {
    private final ManejadorJsonI<Empresa> manejadorEmpresa;
    private final ManejadorJsonI<Usuario> manejadorUsuario;

    public ManejadorService() {
        this.manejadorEmpresa = new ManejadorEmpresa();
        this.manejadorUsuario = new ManejadorSesion();
    }

    public ManejadorService(ManejadorJsonI<Empresa> manejadorEmpresa,
                            ManejadorJsonI<Usuario> manejadorUsuario) {
        this.manejadorEmpresa = manejadorEmpresa;
        this.manejadorUsuario = manejadorUsuario;
    }

    // Lectura
    public Empresa leerEmpresa() {
        return manejadorEmpresa.leerArchivoJson(null);
    }

    public Usuario leerUsuario() {
        return manejadorUsuario.leerArchivoJson(null);
    }

    // Escritura
    public void guardarEmpresa(Empresa empresa) {
        manejadorEmpresa.escribirArchivoJson(empresa, null);
    }

    public void guardarUsuario(Usuario usuario) {
        manejadorUsuario.escribirArchivoJson(usuario, null);
    }
}