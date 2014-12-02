package entidades;

public class Libro {

	private String ISBN;
	private String titulo;
	private String nombreSocioDelPrestamo;
	private String nombreAutor;
	private int librosTotales, librosDisponibles, librosPrestados;
	private int idEjemplar;
	private int diasDeDemora;
	private Autor autor;
	
	
	public String getNombreAutor() {
		return nombreAutor;
	}

	public int getLibrosTotales() {
		return librosTotales;
	}

	public void setLibrosTotales(int librosTotales) {
		this.librosTotales = librosTotales;
	}

	public int getLibrosDisponibles() {
		return librosDisponibles;
	}

	public void setLibrosDisponibles(int librosDisponibles) {
		this.librosDisponibles = librosDisponibles;
	}

	public int getLibrosPrestados() {
		return librosPrestados;
	}

	public void setLibrosPrestados(int librosPrestados) {
		this.librosPrestados = librosPrestados;
	}

	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}


	
	public String getNombreSocioDelPrestamo() {
		return nombreSocioDelPrestamo;
	}

	public void setNombreSocioDelPrestamo(String nombreSocioPrestamo) {
		this.nombreSocioDelPrestamo = nombreSocioPrestamo;
	}
	
	
	public int getIdEjemplar() {
		return idEjemplar;
	}

	public void setIdEjemplar(int idEjemplar) {
		this.idEjemplar = idEjemplar;
	}
	public int getDiasDeDemora() {
		return diasDeDemora;
	}
	public void setDiasDeDemora(int diasDeDemora) {
		this.diasDeDemora = diasDeDemora;
	}
	public Libro() {
	}
	
	public Libro(String ISBN, String titulo){
		this.ISBN = ISBN;
		this.titulo = titulo;
	}
	public Libro(String ISBN, String titulo, Autor autor){
		this.ISBN = ISBN;
		this.titulo = titulo;
		this.autor = autor;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

}
