package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import carrocompra.CarroCompra;
import carrocompra.ProductoCarro;
import conexion.Conexion;
import entidades.Producto;

public class DaoProducto {

	public DaoProducto() {
	}

	/**
	 * getAllProducts
	 */
	public List<Producto> getAllProducts() throws SQLException, Exception {
		Connection con = null;
		Statement stmt = null;
		List<Producto> listadoProductos = new ArrayList<Producto>();

		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTO");

			while (rs.next()) {
				Producto producto = new Producto();
				producto.setIdProducto(rs.getInt("ID"));
				producto.setNombre(rs.getString("NOMBRE"));
				producto.setPrecioNormal(rs.getDouble("PRECIO_NORMAL"));
				producto.setPrecioMinimo(rs.getDouble("PRECIO_MINIMO"));
				listadoProductos.add(producto);
				System.out.println("ResultSet productos: "
						+ producto.getNombre());
			}
			System.out.println("RETURN LISTADOLIBROSDEVUELTOSSSSS");
			return listadoProductos;

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

	public ProductoCarro getProductById(int idProducto) throws SQLException,
			Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "SELECT * FROM PRODUCTO WHERE ID = ?";
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, idProducto);
			ResultSet rs = pstmt.executeQuery();

			rs.next();
			ProductoCarro producto = new ProductoCarro();
			producto.setIdProducto(rs.getInt("ID"));
			producto.setNombre(rs.getString("NOMBRE"));
			producto.setPrecioNormal(rs.getDouble("PRECIO_NORMAL"));
			producto.setPrecioMinimo(rs.getDouble("PRECIO_MINIMO"));
			producto.setCantidad(1);
			System.out.println("getProductoById: " + producto.getNombre());
			return producto;

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

	public void terminarPedido(int idSocio, String direccion, CarroCompra miCarro) throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int secuenciaValue;
		
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			con.setAutoCommit(false);
			
			//saco el valor de la secuencia S_PEDIDO
			pstmt = con.prepareStatement("SELECT S_PEDIDO.NEXTVAL FROM DUAL");
			rs = pstmt.executeQuery();
			rs.next();
			secuenciaValue = rs.getInt(1);
			System.out.println("resultado valor Secuencia:" + secuenciaValue );
			
			//insercion en la tabla PEDIDO
			pstmt = con.prepareStatement("INSERT INTO PEDIDO VALUES(?, ?,'PENDIENTE', SYSDATE, ?, NULL )");
			pstmt.setInt(1, secuenciaValue);
			pstmt.setInt(2, idSocio);
			pstmt.setString(3, direccion);
			System.out.println("executeUpdate: " + pstmt.executeUpdate());

			//insercion en la tabla DETALLEPEDIDO
			int lineaDetalle = 1; //valor numérico q se incrementa cada línea del pedido
			for(ProductoCarro pc : miCarro.getElementos()){
				if(pc.getCantidad()!=0){ //si la cantidad es 0 no la inserto
					pstmt = con.prepareStatement("INSERT INTO DETALLEPEDIDO VALUES(?,?,?,?,?,?)");
					pstmt.setInt(1, secuenciaValue);
					pstmt.setInt(2, lineaDetalle);
					pstmt.setInt(3, pc.getIdProducto());
					pstmt.setInt(4, pc.getCantidad());
					pstmt.setDouble(5, pc.getPrecioNormal());
					pstmt.setDouble(6, (pc.getPrecioNormal() * pc.getCantidad()));
					++lineaDetalle;
					pstmt.execute();
				}
			}
			con.commit();

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

	public boolean existsIdUsuario(int idSocio) throws SQLException, Exception {
		System.out.println("existsIdUsuario");
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "SELECT COUNT(*) FROM CLIENTE WHERE ID = ?";
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, idSocio);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			System.out.println(rs.getInt(1));
			if(rs.getInt(1) > 0) //compruebo si count vale más de 0 
				return true;
			else
				return false;

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
