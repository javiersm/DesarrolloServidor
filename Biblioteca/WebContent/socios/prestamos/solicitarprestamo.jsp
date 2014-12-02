<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Solicitar Prestamo</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />

<script>	
function validaCampo(campo){
	var ercp=/^\d+$/;
   	 if (!(ercp.test(campo.value))) { 
   	 	campo.classList.add("bordeError");
   	 	document.getElementById("error").classList.remove("oculto");
    	return false; 
    }
	campo.classList.remove("bordeError");         
	document.getElementById("error").classList.add("oculto");  
    return true;	
	}

function validar(){
		var numejemplar = document.getElementsByName("numejemplar")[0];
		var idsocio = document.getElementsByName("idsocio")[0];
		if(!validaCampo(numejemplar) || !validaCampo(idsocio)){
			return false;
		}
		document.getElementById("error").classList.add("oculto");
    	if (confirm("¿Seguro desea realizar el préstamo?") == true) {
        	return true;
    	} else {
        	return false;
    	}
}
</script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/logo.jspf" %>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>
		<div id="contenido">

			<h1>Solicitar Prestamo</h1>
			
			
					<form id="prestamoDevolucion"method="GET" action="/Biblioteca/controller" onsubmit="return validar()">
						<div class="centrado">
							<table>
								<tr>
									<input type="hidden" name="operacion" value="solicitarPrestamo">
									<th class="alignDerecha">ID Socio:</th>
									<td class="alignIzquierda"><input type="text" name="idsocio" onblur="validaCampo(this)"></td>
									<td></td>
								</tr>
								<tr>
									<th class="alignDerecha">ID Ejemplar:</th>
									<td class="alignIzquierda"><input type="text" name="numejemplar" onblur="validaCampo(this)"></td>
								</tr>
								<tr>
								<td></td>
								<td class="alignDerecha"><input class="btnEnviar" type="submit" value="Solicitar"></td>
								</tr>
									<tr id="error" class="oculto">
									<td colspan="2"><p style="color:red;"class="alignCentrado">Por favor rellena los campos con números.</p></td>
									<td></td>
								</tr>
							</table>
						</div>
					</form>
			


			<c:remove scope="session" var="busqueda" />

		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>