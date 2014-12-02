<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Pagina de identificacion</title>
<link rel="stylesheet" href="/Biblioteca/resources/css/identificate.css"
	type="text/css" />
<link rel="stylesheet" href="/Biblioteca/resources/css/principal.css"
	type="text/css" />
<script type="text/javascript">
	function validaCampo(campo){
   	 	if (campo.value=="") { 
   	 		campo.classList.add("bordeError");
    		return false; 
    	}
    	campo.classList.remove("bordeError");
    	return true;
    }
</script>
</head>
<%@ include file="/WEB-INF/jspf/logo.jspf"%>
<body>
	<div id="contenedor">
		<%@ include file="/WEB-INF/jspf/cabecera.jspf"%>


		<div class="identificate">
			<fieldset>
				<legend>Acceder a la Biblioteca</legend>
				<form action="j_security_check" method="POST">
					<div class="centrado">
						<table>
							<tr>
								<td colspan="2" class="colError"><c:choose>
										<c:when test="${sessionScope.intentosLogin==null}">
											<c:set var="intentosLogin" scope="session" value="0" />
										</c:when>
										<c:otherwise>
											<font> <span class="error"><strong>Error:
												</strong>Usuario o password incorrectos</span> <c:set var="intentosLogin"
													scope="session" value="${sessionScope.intentosLogin + 1}" />
											</font>
										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<th>Usuario:</th>
								<td  class="alignIzquierda"><input type="text" name="j_username" onblur="validaCampo(this)"/></td>
							</tr>
							<tr>
								<th>Contraseña:</th>
								<td class="alignIzquierda"><input type="password" name="j_password" onblur="validaCampo(this)"/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td class="alignLeftCenter"><input class="btnEnviar" type="submit" name="login"
									value="Login"/></td>
							</tr>
						</table>
					</div>
				</form>
			</fieldset>
			<a href="/Biblioteca/registrate.jsp"><input	type="button" id="btnRegistrate" value="Registrate" /><a />
		</div>





	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>