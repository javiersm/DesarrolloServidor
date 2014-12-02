<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Consulta Bibliográfica</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />

<script type="text/javascript">
	function seleccionarTodos() {
		var inputs = document.getElementsByTagName("input");
		if (inputs[0].checked) {
			for (var i = 0; i < inputs.length; i++)
				inputs[i].checked = false;
		} else {
			for (var i = 0; i < inputs.length; i++)
				inputs[i].checked = true;
		}

	}
	function comprobar(){
	if (confirm("¿Seguro desea realizar borrar estos ejemplares?") == true) {
        	return true;
    	} else {
        	return false;
    	}
	}
</script>

</head>
<body>
	<%@ include file="/WEB-INF/jspf/logo.jspf"%>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>

		<div id="contenido">
			<h1>Consulta Bibliográfica</h1>
			
				<div class="centrado">
					<form id="busquedaSencilla" method="GET"
						action="/Biblioteca/controlleradmin">
						<table>
							<input type="hidden" name="operacion"
								value="borrarEjemplaresLibro">
							<tr>
								<td><input type="text" name="nombre" value="${nombre}"></td>
								<td><select name="tipoBusqueda">
										<option value="autor"
											<c:if test="${!tipoBusqueda.isEmpty() && tipoBusqueda eq 'autor'}">
																	<c:out value="selected"/></c:if>>Autor</option>

										<option value="titulo"
											<c:if test="${!tipoBusqueda.isEmpty() && tipoBusqueda eq 'titulo'}">
																	<c:out value="selected"/></c:if>>Titulo</option>

										<option value="isbn"
											<c:if test="${!tipoBusqueda.isEmpty() && tipoBusqueda eq 'isbn'}">
																	<c:out value="selected"/></c:if>>ISBN</option>
								</select></td>
								<td class="derecha"><input class="btnEnviar" type="submit"
									value="Enviar"></td>
							</tr>
						</table>
					</form>
				</div>
			

			<c:if test="${listadoLibros!=null}">
				<c:choose>
					<c:when test="${not empty listadoLibros}">
						<div class="tabla">
							<table width="100%" border="1" class="tabla">
								<thead>
								<tr>
									<th scope="col">ISBN</th>
									<th scope="col">Titulo</th>
									<th scope="col">Autor</th>
									<th scope="col">Eliminar Ejemplar</th>
								</tr>
								</thead>
								<c:forEach items="${listadoLibros}" var="libro">
									<tr>
										<td class="alignCentrado">${libro.ISBN}</td>
										<td class="alignIzquierda">${libro.titulo}</td>
										<td class="alignIzquierda">${libro.nombreAutor}</td>
										<td class="alignCentrado"><a
											href="/Biblioteca/controlleradmin?operacion=getListadoEjemplares&isbn=${libro.ISBN}">Eliminar
												Ejemplar</a></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:when>
					<c:otherwise>
						<p align="center">No hay registros coincidentes</p>
					</c:otherwise>
				</c:choose>
				<c:remove scope="session" var="listadoLibros" />
			</c:if>


			<c:if test="${listadoEjemplares!=null}">
				<c:choose>
					<c:when test="${not empty listadoEjemplares}">
							<form method="POST" action="/Biblioteca/controlleradmin" onsubmit="return comprobar()">
								<input type="hidden" name="operacion" value="borrarEjemplares">
								<table width="100%" border="1" class="tabla">
									<thead>
										<tr>
											<th>Borrar Ejemplares</th>
										</tr>
									</thead>
									<tr>
										<td class="alignCentrado"><input type="checkbox"
											onClick="seleccionarTodos()"/>borrar todos</td>
									</tr>
									<c:forEach items="${listadoEjemplares}" var="libro">
										<tr>
											<td class="alignCentrado"><input type="checkbox"
												name="ejemplares" value="${libro.idEjemplar}"/>ejemplar: ${libro.idEjemplar }</td>
										</tr>
									</c:forEach>
									<tr>
										<td class="alignCentrado"><input type="submit"
											value="Eliminar" class="btnEnviar"></td>
									</tr>
								</table>
							</form>
						
					</c:when>
					<c:otherwise>
						<p align="center">En estos momentos no hay ningún ejemplar que se pueda borrar.</p>
					</c:otherwise>
				</c:choose>
				<c:remove scope="session" var="listadoEjemplares" />
			</c:if>


		</div>

	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>