<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="anerrorpage.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>

<jsp:directive.page import="com.agrologic.app.model.UserDto"/>
<jsp:directive.page import="com.agrologic.app.model.CellinkDto"/>
<jsp:directive.page import="com.agrologic.app.model.ControllerDto"/>
<jsp:directive.page import="com.agrologic.app.web.UserRole"/>
<jsp:directive.page import="com.agrologic.app.web.CellinkState"/>

<%  UserDto user = (UserDto)request.getSession().getAttribute("user");
    if(user == null) {
        response.sendRedirect("./index.htm");
        return;
    }
    String message =(String)request.getSession().getAttribute("message");
    request.getSession().setAttribute("message",null);
    Boolean errorFlag = (Boolean)request.getSession().getAttribute("error");
    request.getSession().setAttribute("error",null);

    Long userId = Long.parseLong(request.getParameter("userId"));
    Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));
    long flockId = Long.parseLong(request.getParameter("flockId"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
    <head>
        <title></title>
    </head>
    <body>
        <h1></h1>
        <table>
            <tr>
                <td>
                    <img border="0" src="./feedwatergraph.html?userId=<%=userId%>&cellinkId=<%=cellinkId%>&flockId=<%=flockId%>"/>
                </td>
            </tr>
        </table>
    </body>
</html>
