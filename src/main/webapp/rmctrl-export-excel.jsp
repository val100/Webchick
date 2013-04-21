<%--
    Document   : rmctrl-export-excel.jsp
    Created on : Jan 27, 2011, 5:33:29 PM
    Author     : Valery Manakhimov
    Company    : Agrologic Ltd. ®
    Version    : 0.1.1.1
--%>
<% String filename = request.getParameter("filename"); %>


<%@ include file="disableCaching.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Download Excel</title>
    </head>
    <body>
        <table style="border:1px solid #ff0000;background-color:f7f7f7" align="center">
            <tr style="font-weight:bold;">
                <td align="center" align="center" colspan=2 style="border-bottom: 2px solid #000000;">Download Excel File</td>
            </tr>
            <tr>
                <td align="center">
                    <a href="rmctrl-download-file.jsp?filename=<%=filename%>" onclick="javascript:history.back()">Click To Download File</a>
                </td>
            </tr>
            <tr>
                <td><hr></>
            </tr>
            <tr>
                <td align="center">
                    <p><button id="btnClose" name="btnClose" onclick='self.close()'><%=session.getAttribute("button.close")%></button></p>
                </td>
            </tr>
        </table>
    </body>
</html>