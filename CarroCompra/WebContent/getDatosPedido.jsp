<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Index</title>
	<link rel="stylesheet" href="resources/css/estyle.css">
	
<script type="text/javascript">	
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
	function validaDireccion(campo){
	   	 if (campo.value == "") { 
	   	 	campo.classList.add("bordeError");
	   	 	document.getElementById("errorDireccion").classList.remove("oculto");
	    	return false; 
	    }
		campo.classList.remove("bordeError");         
		document.getElementById("errorDireccion").classList.add("oculto");  
	    return true;	
		}
	

function validar(){
		var valido = true;
		if(!validaCampo(document.getElementById("idSocio")) || !validaDireccion(document.getElementById("direccion"))){
			valido = false;
		}
		if(!validaDireccion(document.getElementById("direccion"))){
			valido = false;
		}
		if(valido){
			document.getElementById("error").classList.add("oculto");
    		if (confirm("¿Desea procesar el pedido?") == true) {
        		return true;
    		} else {
        		return false;
    		}
		}else{
			return valido;
		}
}
</script>
</head>
<body>
	<header>
		<div id="infosuperior"></div>
		<div class="logo"></div>
		<a href="elementosCarrito.jsp"><div class="shoppingCart"></div></a>
	</header>
	<nav>
		<ul>
			<li><a href="Controller?operacion=comprar">Productos</a></li>
		</ul>
	</nav>
	<div id="bg-skin"></div>
	<div class="contenido">

<h2>Datos del Pedido</h2>
	<div>
		<form id="prestamoDevolucion" method="POST" action="controller" onSubmit="return validar()">
			<div class="centrado">
				<table>
					<input type="hidden" name="operacion" value="terminarPedido">
					<tr>
						<th class="alignRight">ID Socio:</th>
						<td class="alignLeft"><input id="idSocio" type="text" name="idSocio" onblur="validaCampo(this)" value="${idcliente}"></td>
					</tr>
					<tr>
						<th class="alignRight">Direccion Envio:</th>
						<td class="alignLeft"><input id="direccion" type="text" name="direccion" onblur="validaDireccion(this)" value="${direccion}"></td>
					</tr>
					<tr>
						<td></td>
						<td><input class="btnEnviar"
							type="submit" value="Finalizar"></td>
					</tr>
					</tr>
						<tr id="error" class="oculto">
						<td colspan="2"><p style="color:red;"class="alignCenter">El campo IDSocio debe ser numérico.</p></td>
						<td></td>
					</tr>
						</tr>
						<tr id="errorDireccion" class="oculto">
						<td colspan="2"><p style="color:red;"class="alignCenter">El campo direccion no debe estar vacío.</p></td>
						<td></td>
					</tr>
					<c:if test="${idUsuarioError != null}">
						<c:if test="${idUsuarioError == true}">
							<p style="color:red;"class="alignCenter">El Cliente con ID ${idcliente} no encontrado</p>
						</c:if>
					</c:if>
				</table>
				<c:remove var="idcliente"/>
				<c:remove var="direccion"/>
				<c:remove var="idUsuarioError"/>
			</div>
		</form>
	</div>

	</div>
	<footer class="clear">
		
	</footer>
</body>
</html>
