package co.edu.poli.model;

public class carritoItem {

    private int id_item;
    private int id_carrito;
    private String tipo_producto;
    private Integer id_cancion;
    private Integer id_vinilo;
    private Integer id_mp3;
    private int cantidad;

    public carritoItem(int id_item, int id_carrito, String tipo_producto,
                       Integer id_cancion, Integer id_vinilo, Integer id_mp3, int cantidad) {
        this.id_item = id_item;
        this.id_carrito = id_carrito;
        this.tipo_producto = tipo_producto;
        this.id_cancion = id_cancion;
        this.id_vinilo = id_vinilo;
        this.id_mp3 = id_mp3;
        this.cantidad = cantidad;
    }

    public carritoItem() {}

    public int getId_item() { return id_item; }
    public void setId_item(int id_item) { this.id_item = id_item; }

    public int getId_carrito() { return id_carrito; }
    public void setId_carrito(int id_carrito) { this.id_carrito = id_carrito; }

    public String getTipo_producto() { return tipo_producto; }
    public void setTipo_producto(String tipo_producto) { this.tipo_producto = tipo_producto; }

    public Integer getId_cancion() { return id_cancion; }
    public void setId_cancion(Integer id_cancion) { this.id_cancion = id_cancion; }

    public Integer getId_vinilo() { return id_vinilo; }
    public void setId_vinilo(Integer id_vinilo) { this.id_vinilo = id_vinilo; }

    public Integer getId_mp3() { return id_mp3; }
    public void setId_mp3(Integer id_mp3) { this.id_mp3 = id_mp3; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
