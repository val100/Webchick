<%--
    Document   : rmctrl-add-distribute.jsp
    Created on : Apr 12, 2011, 12:40:34 PM
    Author     : JanL
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="disableCaching.jsp" %>
<%@ include file="language.jsp" %>

<jsp:directive.page import="com.agrologic.app.model.DistribDto"/>
<jsp:directive.page import="com.agrologic.app.dao.DistribDao"/>
<jsp:directive.page import="com.agrologic.app.dao.impl.DistribDaoImpl"/>

<%
    Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));
    Long controllerId = Long.parseLong(request.getParameter("controllerId"));
    Long flockId = Long.parseLong(request.getParameter("flockId"));
    DistribDao distribDao = new DistribDaoImpl();
    List<DistribDto> distribList = distribDao.getAllByFlockId(flockId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <link rel="StyleSheet" type="text/css" href="css/admincontent.css"/>
        <link rel="stylesheet" type="text/css" href="css/calendar.css"/>
        <style type="text/css">
        div.tableHolder {
            OVERFLOW:auto;
            WIDTH: 650px;
            POSITION:relative;
            HEIGHT: 150px;
        }
        thead th {
            Z-INDEX: 20;
            HEIGHT: 20px;
            POSITION: relative;
            TOP: expression(this.offsetParent.scrollTop-2);
            TEXT-ALIGN: center
        }
        tfoot td {
                Z-INDEX: 20;
                HEIGHT: 20px;
                POSITION: relative;
                TOP: expression(this.offsetParent.clientHeight - this.offsetParent.scrollHeight + this.offsetParent.scrollTop); HEIGHT: 20px;
                TEXT-ALIGN: left;
                text-wrap: suppress
             }
        </style>
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
        function validate() {
            var name = document.getElementById('name').value;
            var amnt = document.getElementById('amount').value;
            var prc  = document.getElementById('price').value;

            if(amnt == "") {
                document.getElementById('msg').style.display='block';
                return;
            }
            if(prc == "") {
                document.getElementById('msg').style.display='block';
                return;
            }
            if(name == "") {
                document.getElementById('msg').style.display='block';
                return;
            }
            document.addform.submit();
        }
        function closePopup() {
            window.close();
            if (window.opener && !window.opener.closed) {
                window.opener.location.reload();
            }
        }
        </script>
        <title>Add Distribution</title>
    </head>
    <body>
        <h1>Distribution</h1>
        <br>
        <table id="msg" class="errMsg" align="center" style="display: none;">
            <tr>
                <td>Fields can't be empty</td>
            </tr>
        </table>
        <form id="addform" name="addform" action="./add-distribute.html" method="post">
        <input type="hidden" id="cellinkId" name="cellinkId" value="<%=cellinkId%>">
        <input type="hidden" id="controllerId" name="controllerId" value="<%=controllerId%>">
        <input type="hidden" id="flockId" name="flockId" value="<%=flockId%>">
        <button type="button" onclick='javascript:closePopup()'>Close</button>
        <input type="submit" value="Add">
        <br><br><br>
        <table border="1" style="width: 700px;border:1px solid #C6C6C6; border-collapse: collapse;">
        <thead>
            <th align="center">Date</th>
            <th align="center">Number Account</th>
            <th align="center">Sex</th>
            <th align="center">Target</th>
            <th align="center">Quantity Birds</th>
            <th align="center" width="100px">Action</th>
        </thead>
        <tr>
            <td><input type="text" id="startDate" name="startDate" size="10" readonly>
                <img src="img/calendar.png" border="0" onclick="GetDate('start');"/></td>
            <td><input type="text" id="numberAccount" name="numberAccount" size="10"></td>
            <td>
                <select id="sex" name="sex">
                <option value="none"></option>
                <option value="male">Male</option>
                <option value="female">Female</option>
                </select></td>
            <td><input type="text" id="target" name="target" size="10"></td>
            <td><input type="text" id="quantBird" name="quantBird" size="10"></td>
            <td align="center"><img src="img/plus1.gif" border="0" hspace="4">
                <a href="javascript:validate();">Add</a>
            </td>
        </tr>
        <tbody>
        </tbody>
        </table>
        </form>

    </body>
</html>