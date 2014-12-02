package entidades;

public class Cliente {

	private int idCliente;
	private String nombre;
	private String tipo;
	private int limiteCredito;
	
	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(int limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public Cliente() {
	}

}
