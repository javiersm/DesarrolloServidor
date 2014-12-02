package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoAutores;
import dao.DaoLibros;
import dao.DaoSocios;
import entidades.Autor;
import entidades.Libro;
import entidades.Socio;

/**
 * Servlet implementation class controllerAdmin
 */
@WebServlet("/controlleradmin")
public class controllerAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public controllerAdmin() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String busqueda, idsocio, isbn = null, nombre = null, fechaNacimiento, tipoBusqueda = null,  direccion = null;
		DaoAutores daoAutores;
		DaoSocios daoSocios;
		DaoLibros daoLibros;
		ArrayList<Socio> listadoSocios;
		String operacion = request.getParameter("operacion");
		switch (operacion) {
		case "getSocioByName":
			System.out.println("getSocioByName");
			busqueda = request.getParameter("busqueda");
			daoSocios = new DaoSocios();
			try {
				listadoSocios = daoSocios.getSocioByName(busqueda);
				session.setAttribute("busqueda", busqueda);
				request.setAttribute("listadoSociosBusqueda", listadoSocios);
				request.getRequestDispatcher("admin/socios/getsociobyname.jsp")
						.forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			} catch (Exception e) {
				procesarError(request, response, e);
			}
			break;
			
		case "modificarsocio":
			System.out.println("modificar socio");
			idsocio = request.getParameter("idsocio");
			daoSocios = new DaoSocios();
			try {
				Socio socio = daoSocios.getSocioById(Integer.parseInt(idsocio));
				request.setAttribute("socio", socio);
				request.getRequestDispatcher("admin/socios/modificarsocio.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;
			
			
		
		case "updateSocio":
			System.out.println("modificar socio final");
			idsocio = request.getParameter("idsocio");
			nombre = request.getParameter("nombre");
			direccion = request.getParameter("direccion");
			Socio socio = new Socio(Integer.parseInt(idsocio), nombre, direccion);
			session.removeAttribute("socio");
			try {
				daoSocios = new DaoSocios();
				daoSocios.updateSocio(socio);
				response.sendRedirect("operacioncorrecta.jsp");
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;	
		
			
		case "agregarautor":
			System.out.println("agregarautor");
			nombre = request.getParameter("nombre");
			fechaNacimiento = request.getParameter("fechanacimiento");
			System.out.println(nombre + fechaNacimiento);
			DaoAutores daoAutor = new DaoAutores();
			try {
				Autor autor = new Autor(nombre, fechaNacimiento);
				daoAutor.agregarAutor(autor);
				response.sendRedirect("operacioncorrecta.jsp");
			}catch (SQLException e){
				procesarErrorSQL(request, response, e);
			} catch (Exception e) {
				procesarError(request, response, e);
			}
			break;
		
		
		case "borrarEjemplaresLibro":
			
			if(request.getParameter("nombre")!=null || request.getParameter("tipoBusqueda")!=null || request.getParameter("tipoBusqueda")!=""){
				nombre = request.getParameter("nombre");
				tipoBusqueda  = request.getParameter("tipoBusqueda");
			}else{
				procesarError(request, response, new Exception("Se han introducido valores erroneos en el parámetro nombre o tipo de busqueda"));
			}
			ArrayList<Libro> listadoLibros = new ArrayList<Libro>();
			System.out.println("BusquedaSimple Nombre: "+"nombre" + " tipoBusqueda: "+ tipoBusqueda);
			try {
				daoLibros = new DaoLibros();
				listadoLibros = daoLibros.busquedaSimple(nombre, tipoBusqueda);
				session.setAttribute("listadoLibros", listadoLibros);
				session.setAttribute("nombre", nombre);
				session.setAttribute("tipoBusqueda", tipoBusqueda);
				request.getRequestDispatcher("admin/libros/borrarejemplar.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;	
		
	case "getListadoEjemplares":
			
			if(request.getParameter("isbn")!=null || request.getParameter("isbn")!=""){
				isbn = request.getParameter("isbn");
			}else{
				procesarError(request, response, new Exception("Se han introducido valores erroneos de parámetro"));
			}
			ArrayList<Libro> listadoEjemplares = new ArrayList<Libro>();
			try {
				daoLibros = new DaoLibros();
				listadoEjemplares = daoLibros.getListadoEjemplares(isbn);
				session.setAttribute("listadoEjemplares", listadoEjemplares);
				request.getRequestDispatcher("admin/libros/borrarejemplar.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;	
			
	case "borrarEjemplares":
		String[] ejemplares = null;
		if(request.getParameter("ejemplares")!=null){
			ejemplares = request.getParameterValues("ejemplares");
		}else{
			procesarError(request, response, new Exception("Se han introducido valores erroneos en el parámetro nombre"));
		}
		try {
			daoLibros = new DaoLibros();
			for(String e : ejemplares){
				System.out.println("borrar ejemplar: " + e);
				daoLibros.borrarEjemplar(Integer.parseInt(e));
			}
			response.sendRedirect("operacioncorrecta.jsp");
		} catch (SQLException e) {
			procesarErrorSQL(request, response, e);
		}catch (Exception e) {
			procesarError(request, response, e);
		}
		break;	
			
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	/** Funcion que procesa los errores generales del programa. Los redirige a la pagina error.jsp
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException{
		String mensajeError = e.getMessage();
		request.setAttribute("error", mensajeError);
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}
	
	/**Funcion que procesa los errores SQL. Los redirige a la pagina error.jsp
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void procesarErrorSQL(HttpServletRequest request, HttpServletResponse response, SQLException e) throws ServletException, IOException{
		int codigoError = e.getErrorCode();
		String mensajeError;
		switch(codigoError)
		{
		case 30006:
			mensajeError = "Registro en proceso de modificacion. Inténtelo más tarde.";
			break;
			
		default:
			mensajeError = e.getMessage();
			break;
		}
		request.setAttribute("error", mensajeError);
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}
}
