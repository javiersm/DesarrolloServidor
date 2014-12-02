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
 * Servlet implementation class Controller
 */
@WebServlet(name = "controller", urlPatterns = { "/controller" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String operacion = request.getParameter("operacion");
		String idsocio = null, nombreSocio, nombre = null, tipoBusqueda = null, numEjemplar = null, busqueda = null;
		ArrayList<Libro> listadoLibros = null;
		DaoSocios daoSocios = new DaoSocios();
		DaoLibros daoLibros = new DaoLibros();
		DaoAutores daoautores = new DaoAutores();
		
		switch (operacion) {
		case "cerrarsession":
			System.out.println("cerrar sesion");
			session.invalidate(); // esto es para cerrar sesion
			response.sendRedirect("/Biblioteca/socios/home.jsp");
			break;

		case "listadoautores":
			System.out.println("Listado de Autores");
			daoautores = new DaoAutores();
			ArrayList<Autor> listadoAutores;
			try {
				listadoAutores = daoautores.listadoAutores();
				session.setAttribute("listadoAutores", listadoAutores);
				response.sendRedirect("socios/autores/listadoautores.jsp");
			} catch (SQLException e) {
				System.out.println("listadoautores exception sql");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("listado autores exception");
				e.printStackTrace();
			}
			break;

		case "listadosocios":
			System.out.println("listadosocios");
		    daoSocios = new DaoSocios();
			ArrayList<Socio> listadoSocios;
			int totalRegistros;
			int paginaMasAlta;
			int limiteInferior, limiteSuperior;
			int paginaActual = Integer.parseInt(request.getParameter("pag"));
			System.out.println("paginaActual: "+paginaActual);
			int registrosPorPagina = 5;
			
			try {
				totalRegistros =daoSocios.getNumSociosRegistrados();
				paginaMasAlta = totalRegistros / registrosPorPagina; //hago el calculo para saber cuantas paginas como maximo debe haber
				System.out.println("Pagina mas alta es: " + paginaMasAlta);
				
				/* Hago el calculo de los limites para buscar en la consulta los socios que tengo q presentar*/
				limiteInferior = (registrosPorPagina * paginaActual)+1;
				limiteSuperior = (registrosPorPagina * paginaActual)+ registrosPorPagina;
				if(limiteSuperior> totalRegistros)
					limiteSuperior = totalRegistros;
				if(limiteInferior < 1)
					limiteInferior = 1;
				
				listadoSocios = daoSocios.listadoSocios(limiteInferior, limiteSuperior);
				System.out.println("limiteInferior: "+limiteInferior+"   limiteSuperior"+limiteSuperior);
				session.setAttribute("limiteInferior", limiteInferior);
				session.setAttribute("limiteSuperior", limiteSuperior);
				session.setAttribute("totalRegistros", totalRegistros);
				session.setAttribute("paginaActual", paginaActual);
				session.setAttribute("registrosPorPagina", registrosPorPagina);
				session.setAttribute("paginaMasAlta", paginaMasAlta);
				session.setAttribute("listadoSocios", listadoSocios);   
				response.sendRedirect("socios/listadosocios.jsp");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;


		
			
		case "listadomorosos":
			System.out.println("listado morosos");
			daoSocios = new DaoSocios();
			ArrayList<Socio> listadoSociosMorosos = new ArrayList<Socio>();
			try {
				listadoSociosMorosos = daoSocios.getListadoSociosMorosos();
				session.setAttribute("listadoSociosMorosos", listadoSociosMorosos);
				response.sendRedirect("socios/prestamos/morosos.jsp");
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;	
			
			
		case "librosNoDevueltos":
			System.out.println("libros no devueltos");
			daoLibros = new DaoLibros();
			ArrayList<Libro> listadoLibrosNoDevueltos = new ArrayList<Libro>();
			idsocio = request.getParameter("idsocio");
			try {
				listadoLibrosNoDevueltos = daoLibros.getLibrosNoDevueltos(Integer.parseInt(idsocio));
				nombreSocio = listadoLibrosNoDevueltos.get(0).getNombreSocioDelPrestamo();
				session.setAttribute("nombreSocio", nombreSocio);
				session.setAttribute("listadoLibrosNoDevueltos", listadoLibrosNoDevueltos);
				response.sendRedirect("socios/prestamos/morosos.jsp");
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;
		
			
		case "busquedaSimple":
			if(request.getParameter("nombre")!=null && (request.getParameter("tipoBusqueda")!=null) && (request.getParameter("tipoBusqueda")!="")){
				nombre = request.getParameter("nombre");
				tipoBusqueda  = request.getParameter("tipoBusqueda");
			}else{
				procesarError(request, response, new Exception("Se han introducido valores erroneos en el parámetro nombre"));
			}
			
			listadoLibros = new ArrayList<Libro>();
			System.out.println("BusquedaSimple Nombre: "+"nombre" + " tipoBusqueda: "+ tipoBusqueda);
			try {
				daoLibros = new DaoLibros();
				listadoLibros = daoLibros.busquedaSimple(nombre, tipoBusqueda);
				session.setAttribute("listadoLibros", listadoLibros);
				session.setAttribute("nombre", nombre);
				session.setAttribute("tipoBusqueda", tipoBusqueda);
				request.getRequestDispatcher("socios/libros/consulta.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;
		
		case "busquedaSimpleEjemplares":
			if(request.getParameter("nombre")!=null && (request.getParameter("tipoBusqueda")!=null) && (!request.getParameter("tipoBusqueda").equals(""))){
				nombre = request.getParameter("nombre");
				tipoBusqueda  = request.getParameter("tipoBusqueda");
			}else{
				procesarError(request, response, new Exception("Se han introducido valores erroneos en el parámetro nombre"));
			}
			
			listadoLibros = new ArrayList<Libro>();
			System.out.println("BusquedaSimple Nombre: "+"nombre" + " tipoBusqueda: "+ tipoBusqueda);
			try {
				daoLibros = new DaoLibros();
				listadoLibros = daoLibros.busquedaSimple(nombre, tipoBusqueda);
				session.setAttribute("listadoLibros", listadoLibros);
				session.setAttribute("nombre", nombre);
				session.setAttribute("tipoBusqueda", tipoBusqueda);
				request.getRequestDispatcher("socios/prestamos/buscarejemplares.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;	
		
	
		case "buscarEjemplaresDisponibles":
			String isbn = null;
			if(request.getParameter("isbn")!=null){
				isbn = request.getParameter("isbn");
			}else{
				procesarError(request, response, new Exception("Se han introducido valores erroneos en el parámetro nombre"));
			}
			ArrayList<Libro> listadoEjemplares = new ArrayList<Libro>();
			//System.out.println("BusquedaSimple Nombre: "+"nombre" + " tipoBusqueda: "+ tipoBusqueda);
			try {
				daoLibros = new DaoLibros();
				listadoEjemplares = daoLibros.getListadoEjemplares(isbn);
				session.setAttribute("listadoEjemplares", listadoEjemplares);
				String nombreLibro = daoLibros.getNombreByISBN(isbn);
				session.setAttribute("nombreLibro", nombreLibro);
				//request.getRequestDispatcher("socios/prestamos/buscarejemplares.jsp").forward(request, response);
				request.getRequestDispatcher("socios/libros/consulta.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;	
			
		case "solicitarPrestamo":
			if(request.getParameter("idsocio")!=null && !request.getParameter("idsocio").equals("") && request.getParameter("numejemplar")!=null && !request.getParameter("numejemplar").equals("")){
				idsocio = request.getParameter("idsocio");
				numEjemplar = request.getParameter("numejemplar"); 
			}else{
				procesarError(request, response, new Exception("Se han introducido valores erroneos en el parámetro nombre"));
			}
			System.out.println("idsocio: "+idsocio +"  numejemplar: "+numEjemplar);
			try {
				daoLibros = new DaoLibros();
				daoLibros.solicitarPrestamo(idsocio, numEjemplar);
				request.getRequestDispatcher("operacioncorrecta.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarErrorSQL(request, response, e);
			}catch (Exception e) {
				procesarError(request, response, e);
			}
			break;
	
		case "devolverEjemplar":
			if(request.getParameter("idsocio")!=null && !request.getParameter("idsocio").equals("") && request.getParameter("numejemplar")!=null && !request.getParameter("numejemplar").equals("")){
				idsocio = request.getParameter("idsocio");
				numEjemplar = request.getParameter("numejemplar"); 
			}else{
				procesarError(request, response, new Exception("Se han introducido valores erroneos en el parámetro nombre"));
			}
			System.out.println("idsocio: "+idsocio +"  numejemplar: "+numEjemplar);
			try {
				daoLibros = new DaoLibros();
				daoLibros.devolverEjemplar(idsocio, numEjemplar);
				request.getRequestDispatcher("operacioncorrecta.jsp").forward(request, response);
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
		case 20002:
			mensajeError = "Error "+codigoError+": "+ "Error. El socio tiene libros fuera de plazo. ";
			break;
		case 2291:
			mensajeError = "Error "+codigoError+": " +"Clave de busqueda no encontrada en el sistema";
			break;
		case 20000:
			mensajeError = "Error "+codigoError+": " +"El socio ya tiene un libro en préstamo.";
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
