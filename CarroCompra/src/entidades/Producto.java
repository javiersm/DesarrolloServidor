package entidades;

public class Producto {

	private int idProducto;
	private String nombre;
	private double precioNormal;
	private double precioMinimo;

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int l) {
		this.idProducto = l;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecioNormal() {
		return precioNormal;
	}

	public void setPrecioNormal(double precioNormal) {
		this.precioNormal = precioNormal;
	}

	/**La sobreescritura del método equals siempre tendrá este aspecto. 
	 * Aquí le estamos diciendo a java que dos objetos Producto son iguales 
	 * si apuntan a la misma dirección de memoria o tienen el mismo identificador.
	 */
	@Override
	public boolean equals(Object q) {
		if (q == null)
			return false;
		if (q == this)
			return true;
		if (!(q instanceof Producto))
			return false;
		Producto p = (Producto) q;
		if (this.idProducto == (p.getIdProducto()))
			return true;
		else
			return false;
	}

	public double getPrecioMinimo() {
		return precioMinimo;
	}

	public void setPrecioMinimo(double precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	public Producto() {

	}

}
