package carrocompra;

import entidades.Producto;

public class ProductoCarro extends Producto {

	private int cantidad;
		
	public ProductoCarro() {
		super();
	}
	
	public ProductoCarro(Producto p,int cantidad) {
		this.setIdProducto(p.getIdProducto());
		this.setNombre(p.getNombre());
		this.setPrecioMinimo(p.getPrecioMinimo());
		this.setPrecioNormal(p.getPrecioNormal());
		this.cantidad=cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	

}
