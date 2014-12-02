package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.Libro;
import entidades.Socio;

public class DaoLibros {

	public DaoLibros() {
	}

	
	/*
	 * 
	 * @param idsocio
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<Libro> getLibrosNoDevueltos(int idsocio) throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<Libro> listadoLibrosNoDevueltos = new ArrayList<Libro>();
		String query =   "SELECT  SH.IDEJEMPLAR, L.TITULO, SH.ISBN, SH.IDSOCIO, SH.SOCIONOMBRE, SH.FECHALIMITEDEVOLUCION, SH.DIASDEMORA "
						+"FROM LIBRO L," 
							+" (SELECT E.IDEJEMPLAR IDEJEMPLAR , E.ISBN ISBN, S.IDSOCIO IDSOCIO, S.NOMBRE SOCIONOMBRE,P.FECHALIMITEDEVOLUCION FECHALIMITEDEVOLUCION, ROUND((SYSDATE - P.FECHALIMITEDEVOLUCION)) DIASDEMORA"
							+" FROM PRESTAMO P, SOCIO S, EJEMPLAR E" 
							+" WHERE P.IDEJEMPLAR = E.IDEJEMPLAR" 
							+" AND P.IDSOCIO = S.IDSOCIO" 
							+" AND S.IDSOCIO = ?"
							+ "AND FECHALIMITEDEVOLUCION < SYSDATE) SH " 
						+"WHERE L.ISBN = SH.ISBN";
		
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, idsocio);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Libro libro = new Libro();
				libro.setISBN(rs.getString("ISBN"));
				libro.setTitulo(rs.getString("TITULO"));
				libro.setDiasDeDemora(rs.getInt("DIASDEMORA"));
				libro.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
				libro.setNombreSocioDelPrestamo(rs.getString("SOCIONOMBRE"));
				listadoLibrosNoDevueltos.add(libro);
				//System.out.println("ResultSet LIBROSNODEVUELTOS: " +libro.getISBN()+" "+ libro.getTitulo() +libro.getDiasDeDemora());
			}
			
			System.out.println("RETURN LISTADOLIBROSDEVUELTOSSSSS");
			return listadoLibrosNoDevueltos;

		} catch (SQLException e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}


	public ArrayList<Libro> busquedaSimple(String nombre, String tipoBusqueda) throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt = null;
		ArrayList<Libro> listadoLibros = new ArrayList<Libro>();
		//Con esta saco el ISBN, titulo, autor del libro
		String query =  "SELECT L.ISBN, L.TITULO, A.IDAUTOR, A.NOMBRE NOMBREAUTOR "
			      		+"FROM LIBRO L, AUTOR A "
			      		+"WHERE TRANSLATE(UPPER(L.TITULO),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') "
			      		+"AND TRANSLATE(UPPER(A.NOMBRE),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') "
			      		+"AND TRANSLATE(UPPER(L.ISBN),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') "
			      		+"AND A.IDAUTOR = L.IDAUTOR";
		
		/*  YA LO HE CORREGIDO ARRIBA JAVI NO TE OLVIDES
			Únicamente he detectado un fallo en tu aplicación. La búsqueda de libros ignorando la acentuación de los parámetros y el almacenamiento en la base de datos no funciona correctamente.
			La parte de la consulta errónea es la siguiente;
			+"WHERE L.TITULO LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') "
			+"AND A.NOMBRE LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') "
			+"AND L.ISBN like TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') "
			
			Sustitúyelo por:
			
			" AND TRANSLATE(UPPER(AUTOR.NOMBRE),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U')"+
			" AND TRANSLATE(UPPER(T.TITULO),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U')"+
			" AND T.ISBN LIKE ?"+
		*/
		
		//Con esta le paso el ISBN y saco los totales, prestados y disponibles
		String query2 = "SELECT SS.ISBN, SS.TOTALES, NVL(TT.PRESTADOS, 0) PRESTADOS "
						+"FROM ( "
								+"SELECT ISBN, COUNT(*) TOTALES "
								+"FROM EJEMPLAR "
								+"WHERE ISBN = ? "
								+"GROUP BY ISBN ) SS LEFT OUTER JOIN "
										+"(SELECT E.ISBN ISBN, COUNT(*) PRESTADOS " 
										+"FROM PRESTAMO P, EJEMPLAR E "
										+"WHERE P.IDEJEMPLAR = E.IDEJEMPLAR "
										+"AND E.ISBN = ? "
										+"GROUP BY E.ISBN) TT "
					    +"ON SS.ISBN = TT.ISBN";
		
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			switch(tipoBusqueda){
			case "titulo":
				System.out.println("BUSQUEDA titulo");
				pstmt.setString(1, "%" + nombre + "%");
				pstmt.setString(2, "%");
				pstmt.setString(3, "%");
				break;
			case "autor":
				System.out.println("BUSQUEDA autor");
				pstmt.setString(1, "%");
				pstmt.setString(2, "%" + nombre + "%");
				pstmt.setString(3, "%");
				break;
			case "isbn":
				System.out.println("BUSQUEDA isbn");
				pstmt.setString(1, "%");
				pstmt.setString(2, "%");
				pstmt.setString(3, nombre);
				break;
			default:
				throw new Exception("El campo TipoBusqueda en consulta bibliográfica no es correcto.");
			}
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Libro libro = new Libro();
				libro.setISBN(rs.getString("ISBN"));
				libro.setTitulo(rs.getString("TITULO"));
				libro.setNombreAutor(rs.getString("NOMBREAUTOR"));
				//Ahora hago otra busqueda con query2 para sacar los libros totales, prestados y disponibles
				pstmt = con.prepareStatement(query2);
				pstmt.setString(1, libro.getISBN());
				pstmt.setString(2, libro.getISBN());
				rs2 = pstmt.executeQuery();
				while(rs2.next()){
					libro.setLibrosTotales(rs2.getInt("TOTALES"));
					libro.setLibrosPrestados(rs2.getInt("PRESTADOS"));
					libro.setLibrosDisponibles(rs2.getInt("TOTALES") - rs2.getInt("PRESTADOS"));
				}
				listadoLibros.add(libro);
				System.out.println("ResultSet busquedaSimple: " +libro.getISBN()+" "+ libro.getTitulo() +libro.getDiasDeDemora() + "TOTAL: "+ libro.getLibrosTotales() + "Prest: "+ libro.getLibrosPrestados());
			}
			return listadoLibros;

		} catch (SQLException e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}


	public ArrayList<Libro> getListadoEjemplares(String isbn) throws SQLException, Exception{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<Libro> listadoEjemplares = new ArrayList<Libro>();
		String query = 	" SELECT * "
						+"FROM EJEMPLAR "
						+"WHERE IDEJEMPLAR NOT IN ( "
								+"SELECT IDEJEMPLAR "
								+"FROM PRESTAMO) "
								+"AND ISBN = ?";
		
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Libro libro = new Libro();
				libro.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
				listadoEjemplares.add(libro);
				//System.out.println("ResultSet LIBROSNODEVUELTOS: " +libro.getISBN()+" "+ libro.getTitulo() +libro.getDiasDeDemora());
			}
			return listadoEjemplares;

		} catch (SQLException e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}


	public void borrarEjemplar(int ejemplar) throws SQLException, Exception{
		Connection con = null;
		CallableStatement cstmt = null;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			cstmt = con.prepareCall("BEGIN BORRAREJEMPLAR(?); END;");
			cstmt.setInt(1, ejemplar);
			cstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} finally {
			if (cstmt != null)
				cstmt.close();
			if (con != null)
				con.close();
		}
		
	}


	/**	SOLICITAR PRESTAMO
	 * 
	 * @param idsocio
	 * @param numEjemplar
	 */
	public void solicitarPrestamo(String idsocio, String numEjemplar) throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = 	"INSERT INTO PRESTAMO(IDEJEMPLAR,IDSOCIO)VALUES(?,?)";
		
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(numEjemplar));
			pstmt.setInt(2, Integer.parseInt(idsocio));
			System.out.println("Solicitar prestamo: " +pstmt.executeQuery());
			System.out.println("solicit ejemp aki llego...");
		} catch (SQLException e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}
		
	/** DEVOLVER EJEMPLAR
	 * 
	 * @param idsocio
	 * @param numEjemplar
	 */
		public void devolverEjemplar(String idsocio, String numEjemplar) throws SQLException, Exception {
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			String query = 	"DELETE FROM PRESTAMO WHERE IDEJEMPLAR = ? AND IDSOCIO = ?";
			
			try {
				Conexion miconex = new Conexion();
				con = miconex.getConexion();
				
				//esto es para comprobar que existe el libro en la tabla antes de borrarlo, sino le muestro un error al usuario.
				pstmt = con.prepareStatement("SELECT COUNT(*) FROM PRESTAMO WHERE IDEJEMPLAR = ? AND IDSOCIO = ? ");
				pstmt.setInt(1, Integer.parseInt(numEjemplar));
				pstmt.setInt(2, Integer.parseInt(idsocio));
				rs = pstmt.executeQuery();
				rs.next();
				if(rs.getString(1).equals("0")){
					System.out.println("LO Q VAS A BORRAR NO EXISTE...");
					throw new SQLException("El prestamo que vas a borrar no existe!");
				}
				
				
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(numEjemplar));
				pstmt.setInt(2, Integer.parseInt(idsocio));
				System.out.println("Devolver prestamo: " +pstmt.executeQuery());
				System.out.println("DEVOLVER PRESTAMO...");
			} catch (SQLException e) {
				System.out.println("exception:" + e.getMessage());
				throw e;
			} catch (Exception e) {
				System.out.println("exception:" + e.getMessage());
				throw e;
			} finally {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			}
		}
	
			
		public String getNombreByISBN(String isbn) throws SQLException, Exception{
				Connection con = null;
				ResultSet rs = null;
				PreparedStatement pstmt = null;
				String nombreLibro = null;
				String query = 	"SELECT TITULO FROM LIBRO WHERE ISBN = ? ";
				
				try {
					Conexion miconex = new Conexion();
					con = miconex.getConexion();
					pstmt = con.prepareStatement(query);
					pstmt.setString(1, isbn);
					rs = pstmt.executeQuery();
					
					while (rs.next()) {
						nombreLibro = rs.getString("TITULO");
						//System.out.println("ResultSet LIBROSNODEVUELTOS: " +libro.getISBN()+" "+ libro.getTitulo() +libro.getDiasDeDemora());
					}
					return nombreLibro;

				} catch (SQLException e) {
					System.out.println("exception:" + e.getMessage());
					throw e;
				} catch (Exception e) {
					System.out.println("exception:" + e.getMessage());
					throw e;
				} finally {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				}
		
		}
	

	

	
	
	
}
