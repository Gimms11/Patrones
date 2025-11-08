package service;

import DTO.Empresa;
import DTO.Usuario;
import Interfaces.LectorJsonI;
import Lectores.LectorEmpresa;
import Lectores.LectorSesion;

public class LectorService {
    LectorJsonI lector;

    public Empresa leerEmpresa() {
        lector = new LectorEmpresa();
        return (Empresa) lector.leerArchivoJson(null);
    }

    public Usuario leerUsuario() {
        lector = new LectorSesion();
        return (Usuario) lector.leerArchivoJson(null);
    }
}
