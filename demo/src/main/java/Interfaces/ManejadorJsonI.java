package Interfaces;

public interface ManejadorJsonI<T> {
    T leerArchivoJson(String rutaArchivo);
    void escribirArchivoJson(T objeto, String rutaArchivo);
}