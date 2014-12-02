<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Modificar Socio</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/logo.jspf"%>
	<div id="contenedor">

		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>
		<div id="contenido">

			<h1>Modificar un socio</h1>


			<div>
					<form id="prestamoDevolucion" method="POST"
						action="/Biblioteca/controlleradmin">
						<div class="centrado">
							<table>
								<input type="hidden" name="operacion" value="updateSocio">
								<input type="hidden" name="idsocio" value="${socio.id}">
								<tr>
									<th class="alignDerecha">Nombre:</th>
									<td class="alignIzquierda"><input type="text" name="nombre"
										value="${socio.nombre}"></td>
								</tr>
								<tr>
									<th class="alignDerecha">Direccion:</th>
									<td class="alignIzquierda"><input type="text" name="direccion"
										value="${socio.direccion}"></td>
								</tr>
								<tr>
									<td></td>
									<td class="alignDerecha"><input class="btnEnviar" type="submit"
										value="Modificar"></td>
								</tr>
							</table>
						</div>
					</form>
			</div>

		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>