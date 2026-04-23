package org.example.db.model;

public enum Genero {
    MASCULINO,
    FEMENINO,
    DESCONOCIDO;

    public static Genero toApi(String valor){
        if (valor == null) {
            return DESCONOCIDO;
        }
        return switch (valor.toLowerCase()){
            case "male" -> MASCULINO;
            case "female" -> FEMENINO;
            default -> DESCONOCIDO;
        };
    }
}
