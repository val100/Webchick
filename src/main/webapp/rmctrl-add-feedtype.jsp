<%--
    Document   : rmctrl-add-feedType
    Created on : Apr 5, 2011, 6:20:31 PM
    Author     : JanL
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="disableCaching.jsp" %>
<%@ include file="language.jsp" %>

<jsp:directive.page import="com.agrologic.app.model.FeedTypeDto"/>
<jsp:directive.page import="com.agrologic.app.dao.FeedTypeDao"/>
<jsp:directive.page import="com.agrologic.app.dao.impl.FeedTypeDaoImpl"/>

<%
        Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));
        Long controllerId = Long.parseLong(request.getParameter("controllerId"));
        Long flockId = Long.parseLong(request.getParameter("flockId"));
        FeedTypeDao feedTypeDao = new FeedTypeDaoImpl();
        List<FeedTypeDto> feedTypeList = feedTypeDao.getAllByCellinkId(cellinkId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <link rel="StyleSheet" type="text/css" href="css/admincontent.css"/>
        <link rel="stylesheet" type="text/css" href="css/calendar.css"/>
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript">
            function calcTotalCost(amount, price, total) {
                // calculate cost

                var amountG = document.getElementById(amount);
                if(amountG.value == "") {
                    amountG.value = "0";
                }
                var amountFeedType = parseInt(amountG.value);

                var priceG = document.getElementById(price);
                if(priceG.value == "") {
                    priceG.value = "0.0";
                }
                var priceFeedType  = parseFloat(priceG.value);

                var totalCost = 0.0;
                var totalCost = amountFeedType * priceFeedType;
                document.getElementById(total).value = totalCost;
            }
            function validate() {
                var fdtp = document.getElementById('feedtype').value;
                var prc  = document.getElementById('price').value;

                if(fdtp == "") {
                    document.getElementById('msg').style.display='block';
                    return;
                }
                if(prc == "") {
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
        <style type="text/css">
            div.tableHolder {
                OVERFLOW:auto;
                WIDTH: 450px;
                POSITION:relative;
                HEIGHT: 200px;
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
        <title>Add Feed Type</title>
    </head>
    <body>
        <h1>Add Feed Type</h1>
        <br>
        <button type="button" onclick='javascript:closePopup()'>Close</button>
        <button type="button" onclick="window.location='./rmctrl-add-feed.jsp?cellinkId=<%=cellinkId%>&controllerId=<%=controllerId%>&flockId=<%=flockId%>'">Add Feed </button>
        <table id="msg" class="errMsg" align="center" style="display: none;">
            <tr>
                <td>Fields can't be empty</td>
            </tr>
        </table>
        <form id="addform" name="addform" action="./add-feedtype.html" method="post">
        <input type="hidden" id="cellinkId" name="cellinkId" value="<%=cellinkId%>">
        <input type="hidden" id="controllerId" name="controllerId" value="<%=controllerId%>">
        <input type="hidden" id="flockId" name="flockId" value="<%=flockId%>">
        <div class="tableHolder" style="width: 450px; height: 200px; padding-left:5px;padding-right:10px;">
            <table border="1" style="width: 450px;border:1px solid #C6C6C6; border-collapse: collapse;">
                <thead>
                <th align="center">Feed Type</th>
                <th align="center">Price</th>
                <th align="center" width="100px">Action</th>
                </thead>
                <tr>
                    <td><input type="text" id="feedtype" name="feedtype"></td>
                    <td><input type="text" id="price" name="price" value=""></td>
                    <td align="center"><img src="img/plus1.gif" border="0" hspace="4">
                        <a href="javascript:validate();">Add</a>
                    </td>
                </tr>
                <%for (FeedTypeDto feedType : feedTypeList) {%>
                <tr>
                    <td><%=feedType.getFeedType()%></td>
                    <td><%=feedType.getPrice()%></td>
                    <td align="center"><img src="img/close.png" border="0" hspace="4">
                        <a href="javascript:window.location='./remove-feedtype.html?cellinkId=<%=cellinkId%>&controllerId=<%=controllerId%>&flockId=<%=flockId%>&feedTypeId=<%=feedType.getId()%>';">Remove</a>
                    </td>
                </tr>
                <%}%>
            </table>
        </div>
        </form>
    </body>
</html>