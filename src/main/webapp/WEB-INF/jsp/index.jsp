<%@ page contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="doLogin" value="/doLogin" />
<html>
<form method="post" action="${doLogin}">
租户名称： <input  type="text"  name="userName"><br>
<input type="submit" value="进入">
</form>

</html>