<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="entidades.Autor, java.util.ArrayList;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Listado Autores</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
</head>
<body>
<%@ include file="/WEB-INF/jspf/logo.jspf" %>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>
		<div id="contenido">
			<h1>Listado de Autores</h1>


			<c:if test="${!listadoAutores.isEmpty()}">
				<div class="tabla">
					<table border=1 class="tabla">
						<caption></caption>
						<thead>
						<tr>
							<th scope="col">id</th>
							<th scope="col">Nombre</th>
							<th scope="col">Fecha Nacimiento</th>
						</tr>
						</thead>
						<c:forEach items="${sessionScope.listadoAutores}" var="autor">
							<tr>
								<td>${autor.id}</td>
								<td>${autor.nombre}</td>
								<td>${autor.getFechaNacimientoFormateada()}</td>
							</tr>
						</c:forEach>
						<c:remove scope="session" var="listadoAutores" />
					</table>
				</div>
			</c:if>





		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>