package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import carrocompra.CarroCompra;
import carrocompra.ProductoCarro;
import dao.DaoProducto;
import entidades.Producto;



@WebServlet({ "/Controller", "/controller" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Controller() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		CarroCompra miCarro;
		String idProducto = null, idSocio = null, direccion = null;
		DaoProducto daoProducto = null;
		String operacion = request.getParameter("operacion");
		
		switch(operacion){
			case "comprar":
				miCarro = (CarroCompra) session.getAttribute("carro"); // !importante
				if(miCarro == null){
					miCarro = new CarroCompra();
					session.setAttribute("carro", miCarro);
				}
				try {
					daoProducto = new DaoProducto();
					List<Producto> listadoProducto = daoProducto.getAllProducts();
					session.setAttribute("listadoProducto", listadoProducto);
					for(Producto p: listadoProducto){
						System.out.println("PROD: "+ p.getNombre());
					}
					request.getRequestDispatcher("paginaCompra.jsp").forward(request, response);
				} catch (SQLException e) {	
					procesarErrorSQL(request, response, e);
				} catch (Exception e) {
					procesarError(request, response, e);
				}
				break;
				
			case "agregarCarro":
				System.out.println("agregarCarro");
				if(request.getParameter("idProducto")!= null && request.getParameter("idProducto")!=""){
					idProducto = request.getParameter("idProducto");
				}else{
					procesarError(request, response, new Exception("Parámetros idproducto inválido"));
				}
				miCarro = (CarroCompra) session.getAttribute("carro");
				daoProducto = new DaoProducto();
				try {
					ProductoCarro productoCarro = daoProducto.getProductById(Integer.parseInt(idProducto));
					miCarro.addElemento(productoCarro);
					session.setAttribute("carro", miCarro);
					request.getRequestDispatcher("paginaCompra.jsp").forward(request, response);
				}catch (SQLException e) {
					procesarErrorSQL(request, response, e);
				}catch (Exception e) {
					procesarError(request, response, e);
				}			
				break;
				
			case "restarCarro":
				System.out.println("agregarCarro");
				if(request.getParameter("idProducto")!= null && request.getParameter("idProducto")!=""){
					idProducto = request.getParameter("idProducto");
				}else{
					procesarError(request, response, new Exception("Parámetros idproducto inválido"));
				}
				miCarro = (CarroCompra) session.getAttribute("carro");
				daoProducto = new DaoProducto();
				try {
					ProductoCarro productoCarro = daoProducto.getProductById(Integer.parseInt(idProducto));
					miCarro.restarElemento(productoCarro);
					session.setAttribute("carro", miCarro);
					request.getRequestDispatcher("paginaCompra.jsp").forward(request, response);
				}catch (SQLException e) {
					procesarErrorSQL(request, response, e);
				}catch (Exception e) {
					procesarError(request, response, e);
				}			
				break;	
				
			case "deleteCarro":
				System.out.println("deleteCarro");
				if(request.getParameter("idProducto")!= null && request.getParameter("idProducto")!=""){
					idProducto = request.getParameter("idProducto");
				}else{
					procesarError(request, response, new Exception("Parámetros idproducto inválido"));
				}
				miCarro = (CarroCompra) session.getAttribute("carro");
				daoProducto = new DaoProducto();
				try {
					ProductoCarro productoCarro = daoProducto.getProductById(Integer.parseInt(idProducto));
					miCarro.deleteElemento(productoCarro);
					session.setAttribute("carro", miCarro);
					request.getRequestDispatcher("paginaCompra.jsp").forward(request, response);
				}catch (SQLException e) {
					procesarErrorSQL(request, response, e);
				}catch (Exception e) {
					procesarError(request, response, e);
				}			
				break;	
		
			case "terminarPedido":
				System.out.println("terminarPedido");
				if(request.getParameter("idSocio")!= null && request.getParameter("idSocio")!="" && request.getParameter("direccion")!= null && request.getParameter("direccion")!=""){
					idSocio = request.getParameter("idSocio");
					direccion = request.getParameter("direccion");
				}else{
					procesarError(request, response, new Exception("Parámetros inválido"));
				}
				try {
					daoProducto = new DaoProducto();
					//aqui hago la comprobación de si existe el idusuario en la base de datos
					if(!daoProducto.existsIdUsuario(Integer.parseInt(idSocio))){
						session.setAttribute("idcliente", idSocio);
						session.setAttribute("direccion", direccion);
						System.out.println("mandalo pa la pagina de introducir idsocio");
						session.setAttribute("idUsuarioError", true);
						response.sendRedirect("getDatosPedido.jsp");
					}else{
						System.out.println("procesar terminar pedido");
						miCarro = (CarroCompra) session.getAttribute("carro");
						daoProducto.terminarPedido(Integer.parseInt(idSocio), direccion, miCarro);
						session.removeAttribute("carro");
						response.sendRedirect("operacioncorrecta.jsp");
					}
				}catch (SQLException e) {
					procesarErrorSQL(request, response, e);
				}catch (Exception e) {
					procesarError(request, response, e);
				}
				break;
			
			
			
		}
	}
	

	protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException{
		String mensajeError = e.getMessage();
		request.setAttribute("error", mensajeError);
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}
	
	
	protected void procesarErrorSQL(HttpServletRequest request, HttpServletResponse response, SQLException e) throws ServletException, IOException{
		int codigoError = e.getErrorCode();
		String mensajeError;
		switch(codigoError)
		{
		case 30006:
			mensajeError = "Error "+codigoError+": "+ "Registro en proceso de modificacion. Inténtelo más tarde.";
			break;
		/*case 1:
			mensajeError = "Error "+codigoError+": "+ "No se pueden solicitar dos prestamos del mismo libro.";
			break;	
		*/
		default:
			mensajeError = e.getMessage();
			break;
		}
		request.setAttribute("error", mensajeError);
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}
	

}
