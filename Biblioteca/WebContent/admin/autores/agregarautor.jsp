<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="entidades.Autor, java.util.ArrayList;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Agregar Autor</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/logo.jspf"%>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>
		<div id="contenido">
			<h1>Agregar un Autor</h1>



			
				<div class="centrado">
					<form id="agregarAutor" method="POST"
						action="/Biblioteca/controlleradmin">
						<table>
							<input type="hidden" name="operacion" value="agregarautor">
							<tr>
								<th class="alignDerecha">Nombre:</th>
								<td class="alignIzquierda"><input type="text" name="nombre"></td>
							</tr>
							<tr>
								<th class="alignDerecha">Fecha Nacimiento:</th>
								<td class="alignIzquierda"><input type="text" name="fechanacimiento"
									placeholder="dd-mm-yyyy"></td>
								<td><span style="color: red;">(dd-mm-yyyy)</span></td>
							</tr>
							<tr>
								<td></td>
								<td class="derecha"><input class="btnEnviar" type="submit"
									name="enviar" value="Enviar"></td>
							</tr>
						</table>
					</form>
				</div>
	

		</div>




	</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>