<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Listado Socios</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/logo.jspf"%>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>

		<div id="contenido">

			<h1 class="info gradient3">Listado de Socios</h1>

			<c:if test="${!listadoSocios.isEmpty()}">
				<div class="tabla">
					<table border=1 class="tabla">
						<caption></caption>
						<thead>
							<tr>
							<th>id</th>
							<th>Nombre</th>
							<th>Email</th>
							<th>Direccion</th>
							</tr>
						</thead>
						<c:forEach items="${sessionScope.listadoSocios}" var="socio">
							<tr>
								<td>${socio.id}</td>
								<td class="alignLeftCenter">${socio.nombre}</td>
								<td class="alignLeftCenter">${socio.email}</td>
								<td class="alignLeftCenter">${socio.direccion}</td>
							</tr>
						</c:forEach>
						<tr class="avanzarPagina">
							<td colspan="4"><span>Total registros:
									${totalRegistros}</span><span> Mostrando: ${limiteInferior} al
									${limiteSuperior}</span> <a
								href="/Biblioteca/controller?operacion=listadosocios&pag=${paginaActual-1<0?paginaMasAlta:paginaActual-1}&numRegistrosPagina=${registrosPorPagina}">Anterior</a>
								<a
								href="/Biblioteca/controller?operacion=listadosocios&pag=${paginaActual+1>paginaMasAlta?0:paginaActual+1}&numRegistrosPagina=${registrosPorPagina}">Siguiente</a>
							</td>
						</tr>
						<c:if
							test="${empty paginaActual || empty limiteInferior || empty limiteSuperior}">
							<c:redirect url="/controller?operacion=listadosocios&pag=0" />
						</c:if>

						<c:remove scope="session" var="listadoSocios" />
						<c:remove scope="session" var="paginaActual" />
						<c:remove scope="session" var="registrosPorPagina" />
						<c:remove scope="session" var="numRegistrosPagina" />
						<c:remove scope="session" var="limiteInferior" />
						<c:remove scope="session" var="limiteSuperior" />
						<c:remove scope="session" var="totalRegistros" />
					</table>
				</div>
			</c:if>


		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>