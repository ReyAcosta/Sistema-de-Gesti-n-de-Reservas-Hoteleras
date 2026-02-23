package com.vdr.huespedes.entities;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
<<<<<<< HEAD
import com.vdr.common_reservaciones.enums.TipoDocumento;
=======
import com.vdr.common_reservaciones.enums.Nacionalidad;
>>>>>>> fc1e2758ce6c28820c5d2795ef0aef071c2c6626

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HUESPEDES")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;

    @NotBlank
    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener exactamente 10 dígitos")
    @Column(name = "TELEFONO", length = 10, nullable = false)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DOCUMENTO", length = 30, nullable = false)
    private TipoDocumento tipoDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "NACIONALIDAD", length = 50, nullable = false)
    private Nacionalidad nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", length = 30, nullable = false)
    private EstadoRegistro estadoRegistro;

}

