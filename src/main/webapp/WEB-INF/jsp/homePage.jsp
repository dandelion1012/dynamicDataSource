<%@ page contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="doinsert"  value="/doInsertText" />
<c:url var="doLogout"  value="/doLogout" />
<html>
<h1>${user.userName}</h1>   <a href="${doLogout}">[退出]</a>

<br>
<form method="post" action="${doinsert}">
添加数据： <input  type="text"  name="text"><br>
<input type="submit" value="插入">
</form>
<table>
    <c:forEach items="${texts}" var="text" varStatus="vs">  
            <tr>  
                 <td>  
                    <s:property value="#vs.index+1"/>  
                 </td>  
                 <td align = "center">${text}</td>  
             </tr>  
    </c:forEach>  
    </table>
</html>