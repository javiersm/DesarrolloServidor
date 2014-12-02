package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.Autor;
import entidades.Socio;

public class DaoSocios {

	public DaoSocios() {

	}

	public void altaSocio(Socio socio, String password) throws SQLException,
			Exception {
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cstmt = null;

		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			cstmt = con.prepareCall("begin INSERTA_USUARIO(?,?,?,?); end;");

			//System.out.println("AVER JAVI: " + socio.getNombre() + socio.getDireccion() + socio.getEmail() + password);
			cstmt.setString(1, socio.getNombre());
			cstmt.setString(2, socio.getDireccion());
			cstmt.setString(3, socio.getEmail());
			cstmt.setString(4, password);
			cstmt.executeUpdate();

			con.commit(); // hago commit

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
	
	
	public int getNumSociosRegistrados() throws SQLException, Exception{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String queryNumeroSocios = "SELECT COUNT(*) FROM SOCIO";
		int numeroSocios;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(queryNumeroSocios);
			rs = pstmt.executeQuery();
			rs.next();
			numeroSocios = rs.getInt(1);
			System.out.println("El numero de socios es " + numeroSocios);
			return numeroSocios;
			
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

	/** listadoSocios() 
	 * 
	 * Este Método devuelve el listado de socio paginado, solamente le tengo que decir la página donde estoy y el número de registro que tendrá
	 *  cada página.
	 * 
	 */
	public ArrayList<Socio> listadoSocios(int paginaActual, int registrosPorPagina) throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<Socio> listadoSocios = new ArrayList<Socio>();
		String ListadoPaginadoquery = 	" SELECT FILA, IDSOCIO, NOMBRE, DIRECCION, EMAIL "
						+"FROM( "
						+"		SELECT ROWNUM FILA, IDSOCIO, NOMBRE, DIRECCION, EMAIL"
						+"		FROM("
						+"			SELECT IDSOCIO, NOMBRE, DIRECCION, EMAIL"
						+"			FROM SOCIO"
						+"			ORDER BY NOMBRE))"
						+"WHERE FILA>=? AND FILA <=?";
		
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();	
			pstmt = con.prepareStatement(ListadoPaginadoquery);
			pstmt.setInt(1, paginaActual );
			pstmt.setInt(2, registrosPorPagina);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Socio s = new Socio();
				s.setId(rs.getInt("IDSOCIO"));
				s.setNombre(rs.getString("NOMBRE"));
				s.setDireccion(rs.getString("DIRECCION"));
				s.setEmail(rs.getString("EMAIL"));
				listadoSocios.add(s);
				System.out.println("ResultSet Socio: " + s.getNombre());
			}
			return listadoSocios;

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

	/******************
	 *  getSociosByName()
	 ******************/
	public ArrayList<Socio> getSocioByName(String busqueda)
			throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<Socio> listadoSocios = new ArrayList<Socio>();
		String query = "SELECT * FROM SOCIO WHERE UPPER(NOMBRE) LIKE UPPER(?) ORDER BY NOMBRE";
		try {
			System.out.println("getSocioByName: " + busqueda);
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + busqueda + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Socio s = new Socio();
				s.setId(rs.getInt("IDSOCIO"));
				s.setNombre(rs.getString("NOMBRE"));
				s.setDireccion(rs.getString("DIRECCION"));
				System.out.println("ModificarSocio: " + s.getNombre());
				listadoSocios.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
		return listadoSocios;

	}
	
	/******************
	 *  getSociosById()
	 ******************/
	public Socio getSocioById(int idsocio) throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Socio socio = new Socio();
		String query = "SELECT * FROM SOCIO WHERE IDSOCIO=?";
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, idsocio);
			rs = pstmt.executeQuery();
			while(rs.next()){
				socio.setId(rs.getInt("IDSOCIO"));
				socio.setNombre(rs.getString("NOMBRE"));
				socio.setDireccion(rs.getString("DIRECCION"));
				System.out.println("Get Socio by id: " + socio.getNombre() + socio.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
		return socio;
	}
	/******************
	 *  updateSocio()
	 ******************/
	public void updateSocio(Socio socio) throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "UPDATE SOCIO SET NOMBRE=?, direccion=? where idsocio=?";
		
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			con.setAutoCommit(false);
			System.out.println("Update Socio con parametros: " + socio.getNombre() +socio.getDireccion());
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, socio.getNombre());
			pstmt.setString(2, socio.getDireccion());
			pstmt.setInt(3, socio.getId());
			
			System.out.println("ExecuteUpdate: " + pstmt.executeUpdate());
			System.out.println("Elementos Actualizados:: "+ pstmt.getUpdateCount());
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}
	
	/******************
	 *  getListadoSociosMorosos()
	 ******************/
	public ArrayList<Socio> getListadoSociosMorosos() throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<Socio> listadoSocios = new ArrayList<Socio>();
		String query =	 "SELECT S.IDSOCIO, S.NOMBRE, SH.LIBROSSINDEVOLVER LIBROS "
						+"FROM SOCIO S, ( "  
									+"SELECT IDSOCIO, COUNT(*) LIBROSSINDEVOLVER "
									+"FROM PRESTAMO "
									+"GROUP BY IDSOCIO "
									+"HAVING IDSOCIO IN ( "
											+"SELECT IDSOCIO "
											+"FROM PRESTAMO "
											+"WHERE PRESTAMO.FECHALIMITEDEVOLUCION < SYSDATE)) SH "
						+"WHERE S.IDSOCIO = SH.IDSOCIO";
		//String query2 = "SELECT S.IDSOCIO, S.NOMBRE, SH.LIBROSSINDEVOLVER LIBROS FROM SOCIO S, (SELECT IDSOCIO, COUNT(*) LIBROSSINDEVOLVER FROM PRESTAMO GROUP BY IDSOCIO) SH WHERE S.IDSOCIO = SH.IDSOCIO ORDER BY S.NOMBRE";
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				Socio s = new Socio();
				s.setId(rs.getInt("IDSOCIO"));
				s.setNombre(rs.getString("NOMBRE"));
				s.setLibrosSinDevolver(rs.getInt("LIBROS"));
				listadoSocios.add(s);
				//System.out.println("ResultSet morosos: " +s.getId()+" "+ s.getNombre()+s.getLibrosSinDevolver());
			}
			return listadoSocios;

		} catch (SQLException e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			throw e;
		} finally {
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}
	}

}
