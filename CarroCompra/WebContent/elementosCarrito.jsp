<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Index</title>
	<link rel="stylesheet" href="resources/css/estyle.css">
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

<c:if test="${carro!=null}">
				<c:choose>
					<c:when test="${not empty carro.getElementos()}">
					<h2>Productos en el carrito</h2>
						<div class="tabla">
							<table width="100%" border="1" class="table">
								<thead>
								<tr>
									<th>ID producto</th>
									<th>Nombre</th>
									<th>Precio</th>
									<th>Cantidad</th>
									<th>Total</th>
								</tr>
								</thead>
								<c:forEach items="${carro.getElementos()}" var="carroProducto">
									<tr>
										<td class="alignCentrado">${carroProducto.idProducto}</td>
										<td class="alignIzquierda">${carroProducto.nombre}</td>
										<td class="alignIzquierda">${carroProducto.precioNormal}</td>
										<td class="alignIzquierda">${carroProducto.cantidad}</td>
										<td>${carroProducto.precioNormal * carroProducto.cantidad}</td>
										<td>
										<a href="controller?operacion=agregarCarro&idProducto=${carroProducto.idProducto}"><img alt="añadir carro" src="resources/images/add_carro_icon.png"></a>
										<a href="controller?operacion=restarCarro&idProducto=${carroProducto.idProducto}"><img alt="restar carro" src="resources/images/remove_carro_icon.png"></a>
										<a href="controller?operacion=deleteCarro&idProducto=${carroProducto.idProducto}"><img alt="delete carro" src="resources/images/delete-icon-35x35.png"></a>
										</td>
									</tr>
								</c:forEach>
								<tfoot>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td>Total:</td>
										<td>${carro.getPrecioTotal()}</td>
										<td></td>
										<td></td>
									</tr>
								</tfoot>
							</table>
							<h2 class="formalizar"><a href="getDatosPedido.jsp">Formalizar Pedido</a></h2>
						</div>
					</c:when>
					<c:otherwise>
						<h2>No hay ningún producto en el carrito.</h2>
					</c:otherwise>
				</c:choose>
			</c:if>
<c:if test="${carro==null}"><h2>No hay ningún producto en el carrito.</h2></c:if>





	</div>
	<footer class="clear">
		
	</footer>
</body>
</html>