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

			<h1>Modificar Socio</h1>


			<div class="centrado">
				<form id="busquedaSencilla" method="POST"
					action="/Biblioteca/controlleradmin">
					<div class="centrado">
						<table>
							<tr>
								<input type="hidden" name="operacion" value="getSocioByName">
								<th class="alignDerecha">Nombre:</th>
								<td><input type="text" name="busqueda" value="${busqueda}"></td>
								<td class="alignIzquierda"><input class="btnEnviar"
									type="submit" value="Buscar"></td>
							</tr>
						</table>
					</div>
				</form>
			</div>



			<c:if test="${listadoSociosBusqueda!=null}">
				<c:choose>
					<c:when test="${not empty listadoSociosBusqueda}">
							<table width="100%" border="1" class="tabla">
								<thead>
								<tr>
									<th scope="col">id</th>
									<th scope="col">Nombre</th>
									<th scope="col">Dirección</th>
									<th scope="col">Editar</th>
								</tr>
								</thead>
								<c:forEach items="${listadoSociosBusqueda}" var="socio">
									<tr>
										<td class="alignCentrado">${socio.id}</td>
										<td class="alignLeftCenter">${socio.nombre}</td>
										<td class="alignLeftCenter">${socio.direccion}</td>
										<td class="alignCentrado"><a
											href="/Biblioteca/controlleradmin?operacion=modificarsocio&idsocio=${socio.id}">editar</a></td>
									</tr>
								</c:forEach>
							</table>
					
					</c:when>
					<c:otherwise>
						<p align="center">No hay registros coincidentes</p>
					</c:otherwise>
				</c:choose>
				<c:remove scope="session" var="listadoSociosBusqueda" />
			</c:if>

			<c:remove scope="session" var="busqueda" />

		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>