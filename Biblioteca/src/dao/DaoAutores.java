package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.Autor;

public class DaoAutores {

	public DaoAutores() {
	}

	
	public ArrayList<Autor> listadoAutores() throws SQLException, Exception{
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<Autor> listadoAutores = new ArrayList<Autor>();
		String query = "SELECT * FROM AUTOR ORDER BY NOMBRE";
		try{
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				Autor a = new Autor();
				a.setId(rs.getInt("IDAUTOR"));
				a.setNombre(rs.getString("NOMBRE"));
				a.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
				listadoAutores.add(a);
				System.out.println("ResultSet Autor: "+ a.getNombre() + " id: "+ a.getId() + " fechanacim: "+a.getFechaNacimiento());
			}	
			return listadoAutores;
	
		}
		catch(SQLException e){
			System.out.println("exception:" + e.getMessage());
			throw e;
		}
		catch(Exception e){
			System.out.println("exception:" + e.getMessage());
			throw e;
		}
		finally{
			if(stmt!=null)
				stmt.close();
			if(con!=null)
				con.close();
		}
	}
	
	public void agregarAutor(Autor autor) throws SQLException, Exception{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO AUTOR VALUES (S_AUTOR.NEXTVAL, ?, ?)";
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			con.setAutoCommit(false);
			java.util.Date fecha= autor.getFechaNacimiento();
			//Convertir el util.date a sql.date
			java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, autor.getNombre());
			pstmt.setDate(2, sqlDate);

			System.out.println("Autor insertado correctamente: " + pstmt.executeUpdate() );
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		}
	}
	
	
	
	
}
