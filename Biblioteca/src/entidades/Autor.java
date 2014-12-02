package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Autor {

	private int id;
	private String nombre;
	private Date fechaNacimiento;

	public Autor() {

	}

	public Autor(String nombre, String fechaString) throws Exception {
		this.nombre = nombre;
		this.fechaNacimiento = setFechaNacimientoFormateada(fechaString);
	}

	public Autor(int id, String nombre, Date fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getFechaNacimientoFormateada() {
		SimpleDateFormat formatoDefecha = new SimpleDateFormat("dd-MM-yyyy");
		String fechaFormateada = null;
		if (fechaNacimiento != null) {
			fechaFormateada = formatoDefecha
					.format((java.util.Date) fechaNacimiento);
		}
		return fechaFormateada;
	}

	public Date setFechaNacimientoFormateada(String fechaString) throws Exception {

		if (fechaString == null
				|| !fechaString.matches("[0-3][0-9]-[01][0-9]-\\d{4}")) {
			System.out.println("FORMATO DE FECHA ERRONEA");
			throw new Exception("Formato de Fecha Erronea. Utiliza el formato 'dd-mm-yyyy'");
		} else {
			String[] fechaSplit = fechaString.split("-");
			if ((Integer.parseInt(fechaSplit[0]) >= 32) || (Integer.parseInt(fechaSplit[1]) >= 13)) {
				System.out.println("FECHA ERRONEA");
				throw new Exception("Fecha introducida no es válida. Utiliza el formato 'dd-mm-yyyy' ");
			}
		}
		SimpleDateFormat formatoDefecha = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha = null;
		try {
			fecha = formatoDefecha.parse(fechaString);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Fecha formateada es: " + fecha.toString());
		return fecha;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
