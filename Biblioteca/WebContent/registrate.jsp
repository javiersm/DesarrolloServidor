<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Registrate</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/logo.jspf"%>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>
		<div id="contenido">
			<h1>Formulario de alta socios</h1>

			<div>

				<fieldset>
					<form id="formRegistro" method="POST" action="ControllerAltaSocios">
						<div class="centrado">
							<table>
								<input type="hidden" name="operacion" value="altasocio">
								<tr>
									<th class="alignDerecha">Email:</th>
									<td class="alignIzquierda"><input type="text" name="email"></td>
								</tr>
								<tr>
									<th class="alignDerecha">Nombre:</th>
									<td class="alignIzquierda"><input type="text" name="nombre"></td>
								</tr>
								<tr>
									<th class="alignDerecha">Direccion:</th>
									<td class="alignIzquierda"><input type="text" name="direccion"></td>
								</tr>
								<tr>
									<th class="alignDerecha">Contraseña:</th>
									<td class="alignIzquierda"><input type="password" name="pass"></td>

								</tr>
								<tr>
									<td></td>
									<td class="alignIzquierda"><input class="btnEnviar" type="submit" name="enviar"
										value="Enviar"></td>
								</tr>
							</table>
						</div>
					</form>
				</fieldset>




			</div>
		</div>
</body>
</html>