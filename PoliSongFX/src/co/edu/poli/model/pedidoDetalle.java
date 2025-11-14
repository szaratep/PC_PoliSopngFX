package co.edu.poli.model;

/**
 * Representa el detalle individual de un pedido dentro del sistema PoliSongStock.
 * Cada pedido puede contener múltiples detalles, donde cada uno corresponde a un
 * producto adquirido (canción, vinilo o disco MP3), especificando su cantidad y precio.
 * 
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class pedidoDetalle {

    /**
     * Identificador único del detalle del pedido
     */
    private int id_detalle;

    /**
     * Identificador del pedido al que pertenece este detalle
     */
    private int id_pedido;

    /**
     * Tipo del producto (cancion, vinilo, discomp3)
     */
    private String tipo_Producto;

    /**
     * Identificador del producto asociado
     */
    private int id_producto;

    /**
     * Cantidad del producto adquirida
     */
    private int cantidad;

    /**
     * Precio unitario del producto al momento de compra
     */
    private double precio_unitario;

    /**
     * Constructor vacío necesario para operaciones JDBC/DAO.
     * Permite instanciar un objeto y luego poblar sus atributos con datos de la BD.
     */
    public pedidoDetalle() {
        // Constructor vacío
    }

    /**
     * Constructor para inicializar todos los atributos del detalle del pedido.
     *
     * @param id_detalle Identificador del detalle
     * @param id_pedido Identificador del pedido
     * @param tipo_Producto Tipo de producto adquirido
     * @param id_producto Identificador del producto
     * @param cantidad Cantidad adquirida
     * @param precio_unitario Precio unitario del producto
     */
    public pedidoDetalle(int id_detalle, int id_pedido, String tipo_Producto, int id_producto, int cantidad,
            double precio_unitario) {
        super();
        this.id_detalle = id_detalle;
       	this.id_pedido = id_pedido;
        this.tipo_Producto = tipo_Producto;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    /** @return ID del detalle del pedido */
    public int getId_detalle() {
        return id_detalle;
    }

    /** @param id_detalle Asigna el ID del detalle */
    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    /** @return ID del pedido */
    public int getId_pedido() {
        return id_pedido;
    }

    /** @param id_pedido Asigna el ID del pedido */
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    /** @return Tipo del producto */
    public String getTipo_Producto() {
        return tipo_Producto;
    }

    /** @param tipo_Producto Asigna el tipo de producto */
    public void setTipo_Producto(String tipo_Producto) {
        this.tipo_Producto = tipo_Producto;
    }

    /** @return ID del producto */
    public int getId_producto() {
        return id_producto;
    }

    /** @param id_producto Asigna el ID del producto */
    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    /** @return Cantidad del producto */
    public int getCantidad() {
        return cantidad;
    }

    /** @param cantidad Asigna la cantidad */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /** @return Precio unitario del producto */
    public double getPrecio_unitario() {
        return precio_unitario;
    }

    /** @param precio_unitario Asigna el precio unitario */
    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
}
