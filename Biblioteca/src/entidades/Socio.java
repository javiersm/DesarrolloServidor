package entidades;

public class Socio {
	private int id;
	private String email;
	private String nombre;
	private String direccion;
	private int librosSinDevolver;
	
	public int getLibrosSinDevolver() {
		return librosSinDevolver;
	}
	public void setLibrosSinDevolver(int librosSinDevolver) {
		this.librosSinDevolver = librosSinDevolver;
	}
	public Socio() {
		
	}
	public Socio(int id, String nombre, String direccion){
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
	}
	public Socio(String email, String nombre, String direccion){
		this.email = email;
		this.nombre = nombre;
		this.direccion = direccion;
	}
	public Socio(int id, String email, String nombre, String direccion){
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.direccion = direccion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	

}
