package org.example.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personajes")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private int ki;
    private String maxKi;
    @ManyToMany
    @JoinTable(
            name = "personaje_raza",
            joinColumns = @JoinColumn(name = "personaje_id"),
            inverseJoinColumns = @JoinColumn(name = "raza_id")
    )
    private Set<Raza> razas = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String imagen;
    private String descripcion;
    private String afiliacion;
}
