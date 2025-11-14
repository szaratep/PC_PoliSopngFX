package co.edu.poli.model;

import java.util.Date;

/**
 * Representa el catálogo publicado por un proveedor en la plataforma PoliSongStock.
 * Un catálogo puede estar asociado a uno o varios vinilos, pero en este modelo
 * inicial se registra la relación directa con un único vinilo al momento de crear
 * el objeto, asumiendo que dicho vinilo ya existe en la base de datos.
 *
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class catalogo {

    /**
     * Identificador único del catálogo
     */
    private int id_catalogo;

    /**
     * Identificador del proveedor que publica el catálogo
     */
    private int id_proveedor;

    /**
     * Identificador del vinilo incluido en el catálogo (opcional al crear)
     */
    private int id_vinilo;

    /**
     * Fecha en que el catálogo fue publicado
     */
    private Date fecha_publicacion;
    
    /**
     * Constructor vacío necesario para JDBC/DAO
     * cuando se crea el catálogo antes de asociar valores al objeto.
     */
    public catalogo() {
        // Constructor vacío
    }

    /**
     * Constructor principal para crear un catálogo sin asociar un vinilo inicial.
     * Este constructor se usa cuando se desea crear el catálogo primero y después
     * asociar vinilos manualmente.
     *
     * @param id_catalogo Identificador del catálogo
     * @param id_proveedor Identificador del proveedor
     * @param fecha_publicacion Fecha de publicación del catálogo
     */
    public catalogo(int id_catalogo, int id_proveedor, Date fecha_publicacion) {
        super();
        this.id_catalogo = id_catalogo;
        this.id_proveedor = id_proveedor;
        this.fecha_publicacion = fecha_publicacion;
    }

    /**
     * Constructor alternativo para crear un catálogo e inmediatamente asociarlo
     * a un vinilo existente en la base de datos.
     *
     * @param id_catalogo Identificador del catálogo
     * @param id_proveedor Identificador del proveedor
     * @param id_vinilo Identificador del vinilo asociado
     * @param fecha_publicacion Fecha de publicación
     */
    public catalogo(int id_catalogo, int id_proveedor, int id_vinilo, Date fecha_publicacion) {
        super();
        this.id_catalogo = id_catalogo;
        this.id_proveedor = id_proveedor;
        this.id_vinilo = id_vinilo;
        this.fecha_publicacion = fecha_publicacion;
    }

    /** @return ID del catálogo */
    public int getId_catalogo() {
        return id_catalogo;
    }

    /** @param id_catalogo Asigna el ID del catálogo */
    public void setId_catalogo(int id_catalogo) {
        this.id_catalogo = id_catalogo;
    }

    /** @return ID del proveedor */
    public int getId_proveedor() {
        return id_proveedor;
    }

    /** @param id_proveedor Asigna el ID del proveedor */
    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    /** @return ID del vinilo asociado */
    public int getId_vinilo() {
        return id_vinilo;
    }

    /** @param id_vinilo Asigna el ID del vinilo asociado */
    public void setId_vinilo(int id_vinilo) {
        this.id_vinilo = id_vinilo;
    }

    /** @return Fecha de publicación del catálogo */
    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    /** @param fecha_publicacion Asigna la fecha de publicación del catálogo */
    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }
}
