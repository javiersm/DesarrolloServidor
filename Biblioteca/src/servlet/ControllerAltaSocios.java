package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoSocios;
import entidades.Socio;

/**
 * Servlet implementation class ControllerAltaSocios
 */
@WebServlet("/ControllerAltaSocios")
public class ControllerAltaSocios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAltaSocios() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacion = request.getParameter("operacion");
		String email = request.getParameter("email");
		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		String direccion = request.getParameter("direccion");
		System.out.println("nombre: " + nombre + " pass: "+pass +" email: "+email + " direccion: "+direccion );
		HttpSession session = request.getSession();
		
		if(operacion.equals("altasocio")){
			System.out.println("ControllerAltaSocios (SE VA A DAR DE ALTA UN SOCIO)");
			DaoSocios daoSocios = new DaoSocios();
			Socio socio = new Socio(email,nombre, direccion);
			try {
				daoSocios.altaSocio(socio, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("/Biblioteca/socios/home.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
