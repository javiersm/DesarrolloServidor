<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>index</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/logo.jspf"%>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>

		<div id="contenido">
			<h1>Inicio</h1>
			<!-- Esto sirve para que el usuario tenga q identificarse, antes de poder hacer nada, le redirige a una zona segura, con lo
	cual el glassfish le redirige a identificate.jsp. Una vez identificado lo reenvia a usuarios home.jsp -->
			<!-- response.sendRedirect("controller?operacion=inicio");-->

			<jsp:scriptlet>response.sendRedirect("/Biblioteca/socios/home.jsp");</jsp:scriptlet>
		</div>

	</div>
</body>
</html>