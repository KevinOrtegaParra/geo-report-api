package com.geo_report_api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "reports")
public class Report implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String title;

    @Column
    String descriptio;

    @Column
    LocalDateTime reportDate;

    @ManyToOne
    @JoinColumn(name = "Incident_id")
    Incident incidentType;

    @Column(columnDefinition = "geometry(Point, 4326)")
    Point ubication;

    @ManyToOne
    @JoinColumn(name = "users_id")
    UserEntity user;

    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
/*import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;

...

GeometryFactory geometryFactory = new GeometryFactory();
Point punto = geometryFactory.createPoint(new Coordinate(longitud, latitud));
punto.setSRID(4326); // IMPORTANTE: establece el SRID

Reporte reporte = new Reporte();
reporte.setUbicacion(punto); */


}