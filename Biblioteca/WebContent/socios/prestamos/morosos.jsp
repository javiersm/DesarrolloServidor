<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Morosos</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/logo.jspf"%>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>
		<div id="contenido">
			<h1 style="margin-bottom: 0px;">Listado de Socios Morosos</h1>

			<c:if test="${!listadoSociosMorosos.isEmpty()}">
				<table border=1 class="tabla">
					<caption></caption>
					<thead><tr>
						<th>id</th>
						<th>Nombre</th>
						<th>Detalles Libros</th>
					</tr></thead>
					<c:forEach items="${sessionScope.listadoSociosMorosos}" var="socio">
						<tr>
							<td class="alignCentrado">${socio.id}</td>
							<td class="alignCentrado">${socio.nombre}</td>
							<td class="alignCentrado"><a
								href="/Biblioteca/controller?operacion=librosNoDevueltos&idsocio=${socio.id}">ver
									libros</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>

			<c:if test="${not empty listadoLibrosNoDevueltos}">
				<h1 style="margin-bottom: 0px;">Libros no devueltos de
					${sessionScope.nombreSocio}</h1>
				<div id="tabla">
					<table border=1 class="tabla">
						<caption></caption>
						<thead>
						<tr>
							<th scope="col">ISBN</th>
							<th scope="col">idEjemplar</th>
							<th scope="col">Titulo</th>
							<th scope="col">Dias de Demora</th>
						</tr>
						</thead>
						<c:forEach items="${sessionScope.listadoLibrosNoDevueltos}"
							var="libro">
							<tr>
								<td class="alignCentrado">${libro.ISBN}</td>
								<td class="alignCentrado">${libro.idEjemplar}</td>
								<td class="alignCentrado">${libro.titulo}</td>
								<td class="alignCentrado">${libro.diasDeDemora}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
			<c:if test="${empty listadoSociosMorosos}">
				<c:redirect url="/Biblioteca/controller?operacion=listadomorosos" />
			</c:if>
			<c:remove scope="session" var="listadoLibrosNoDevueltos" />
			<c:remove scope="session" var="nombreSocio" />
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>