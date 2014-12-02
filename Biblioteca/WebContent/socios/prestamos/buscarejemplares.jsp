<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Consulta Bibliográfica</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
	
	
</head>
<body>
<%@ include file="/WEB-INF/jspf/logo.jspf" %>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>

		<div id="contenido">
			<h1>Buscar Libro</h1>
				<div class="centrado">
					<form id="busquedaSencilla" method="POST" action="/Biblioteca/controller">
						<table>
							<input type="hidden" name="operacion" value="busquedaSimpleEjemplares">
							<tr>
								<td><input type="text" name="nombre" value="${nombre}"></td>
								<td><select name="tipoBusqueda">
										<option value="autor" <c:if test="${!tipoBusqueda.isEmpty() && tipoBusqueda eq 'autor'}">
																	<c:out value="selected"/></c:if>>Autor</option>
										
										<option value="titulo"  <c:if test="${!tipoBusqueda.isEmpty() && tipoBusqueda eq 'titulo'}">
																	<c:out value="selected"/></c:if>>Titulo</option>
										
										<option value="isbn"  <c:if test="${!tipoBusqueda.isEmpty() && tipoBusqueda eq 'isbn'}">
																	<c:out value="selected"/></c:if>>ISBN</option>
								</select></td>
								<td class="derecha"><input class="btnEnviar" type="submit" value="Buscar"></td>
							</tr>
						</table>
					</form>
				</div>
			
			<!-- LISTADO DE LIBROS -->
			<c:if test="${listadoLibros!=null}">
				<c:choose>
					<c:when test="${not empty listadoLibros}">
						<div class="tabla">
							<table width="100%" border="1" class="tabla">
								<thead>
								<tr>
									<th>ISBN</th>
									<th>Titulo</th>
									<th>Autor</th>
									<th>Ejemplares disponibles</th>
									<th>ver ID</th>
								</tr>
								</thead>
							<c:forEach items="${listadoLibros}" var="libro">
								<tr>
									<td class="alignCentrado">${libro.ISBN}</td>
									<td class="alignIzquierda">${libro.titulo}</td>
									<td class="alignIzquierda">${libro.nombreAutor}</td>
									<td class="alignCentrado">${libro.librosDisponibles}</td>
									<td class="alignCentrado"><a href="/Biblioteca/controller?operacion=buscarEjemplaresDisponibles&isbn=${libro.ISBN}">ver</a></td>
								</tr>
							</c:forEach>
							</table>
						</div>
					</c:when>
					<c:otherwise>
						<p align="center">No hay ningún ejemplar disponible en estos momentos.</p>
					</c:otherwise>
				</c:choose>
				<c:remove scope="session" var="listadoLibros" />
			</c:if>


			<!-- VER EL ID EJEMPLARES -->
			<c:if test="${listadoEjemplares!=null}">
				
				<c:choose>
					<c:when test="${not empty listadoEjemplares}">
						<div class="tabla">
							<table width="100%" border="1" class="tabla">
								<caption></caption>
								<thead><tr>
									<th>Ejemplares disponibles de: ${nombreLibro}</th>
								</tr>
								</thead>
							<c:forEach items="${listadoEjemplares}" var="libro">
								<tr>
									<td class="alignCentrado">Idejemplar: ${libro.idEjemplar }</td>
								</tr>
							</c:forEach>
							</table>
						</div>
					</c:when>
					<c:otherwise>
						<p align="center">En estos momentos no hay ningún ejemplar disponible del libro ${nombreLibro}.</p>
					</c:otherwise>
				</c:choose>
				<c:remove scope="session" var="listadoEjemplares" />
			</c:if>


		</div>

	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>