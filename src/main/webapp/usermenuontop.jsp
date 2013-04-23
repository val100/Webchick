<%@page import="com.agrologic.app.dao.impl.DomainDaoImpl"%>
<%@page import="com.agrologic.app.dao.DomainDao"%>
<jsp:directive.page import="com.agrologic.app.model.UserDto"/>
<jsp:directive.page import="com.agrologic.app.web.UserRole"/>
<%
    UserDto logedUser = (UserDto) request.getSession().getAttribute("user");
    //UserDto logedUser = (UserDto) getServletContext().getAttribute("user");
    if (logedUser == null) {
        response.sendRedirect("./index.htm");
        return;
    }
    Integer userRole = logedUser.getRole();
    DomainDao domainDao = new DomainDaoImpl();

    String logoString = domainDao.getLogoPath((String)session.getAttribute("domain"));
    if(logoString == null) {
        logoString = " img/agrologiclogo.png";
    }
    String company = domainDao.getCompany((String)session.getAttribute("domain"));
    if(company == null) {
        company="agrologic";
    }
%>
<script language="JavaScript" type="text/javascript">
    function setActive() {
        var link = document.location.href;
        var iLastSlash = link.lastIndexOf('/');
        var iFirstParm = link.lastIndexOf('?');
        var iLastPoint = link.lastIndexOf('.');
        if(iLastPoint == -1) {
            link = link.substring(iLastSlash+1, iLastPoint);

        } else {
            //link = link.substring(iLastSlash, iFirstParm);
            link = link.substring(iLastSlash+1, iLastPoint);
        }
        var activated = false;
        var aObj = document.getElementsByName('mnu');
        for(i=0; i <aObj.length; i++) {
            aaObj = aObj[i].getElementsByTagName('a')
            for(j=0; j < aaObj.length; j++) {
                if(aaObj[j].href.indexOf(link) >= 0) {
                    aaObj[j].className='active';
                    setCookie("active-menu", link)
                    activated = true;
                }
            }
        }

        var activemenu = getCookie("active-menu");
        if (!activated) {
            for(i=0; i <aObj.length; i++) {
                aaObj = aObj[i].getElementsByTagName('a')
                for(j=0; j < aaObj.length; j++) {
                    if(aaObj[j].href.indexOf(activemenu) >= 0) {
                        aaObj[j].className='active';
                        setCookie("active-menu", activemenu)
                    }
                }
            }
        }
    }
    window.onload = setActive;
    function getCookie(name) {
        var search = name + "="
        var returnvalue = "";
        if (document.cookie.length > 0) {
            offset = document.cookie.indexOf(search)

            // if cookie exists
            if (offset != -1) {
                offset += search.length
                // set index of beginning of value
                end = document.cookie.indexOf(";", offset);
                // set index of end of cookie value
                if (end == -1) end = document.cookie.length;
                returnvalue=unescape(document.cookie.substring(offset, end))
            }
        }
        return returnvalue;
    }
    function setCookie(name, value) {
        document.cookie = name + "=" + value;
    }
</script>
<table border="0" align="center" >
    <tr>
        <td valign="top" width="160px" style="padding: 0px; margin: 0px">
            <img src="<%=logoString%>" title="<%=company%>" style="border-color:white;">
        </td>
        <td valign="top" width="800px"><%@include file="toplang.jsp"%></td>
        <td valign="top" nowrap>
            <%if (logedUser.getLogin() != null) {%>
                <%=session.getAttribute("label.welcome")%>
                <strong><%=logedUser.getLogin()%></strong>
                <span>|</span>
                <a href="./my-profile.jsp?userId=<%=logedUser.getId()%>"><%=session.getAttribute("button.profile")%></a>&nbsp;
                <span>|</span>
                <a href="./help.html"><%=session.getAttribute("label.help")%></a>
                <span>|</span>
                <a href="./logout.html"><%=session.getAttribute("label.logout")%></a>
            <%}%>
        </td>
    </tr>
    <tr>
        <td colspan="3" width="100%">
            <table id="mnubar" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <% if (userRole == UserRole.ADMINISTRATOR) {%>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/main.jsp"><%=session.getAttribute("menu.home")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/overview.html?userId=<%=logedUser.getId()%>"><%=session.getAttribute("menu.overview")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/all-users.html"><%=session.getAttribute("menu.users")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/all-programs.html"><%=session.getAttribute("menu.screens")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/database.jsp"><%=session.getAttribute("menu.database")%></a></td>
                    <%} else if (userRole == UserRole.ADVANCED) {%>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/overview.html?userId=<%=logedUser.getId()%>"><%=session.getAttribute("menu.overview")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/all-users.html"><%=session.getAttribute("menu.users")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/all-programs.html"><%=session.getAttribute("menu.screens")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/database.jsp"><%=session.getAttribute("menu.database")%></a></td>
                    <%} else {%>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"><a href="<%=request.getContextPath()%>/my-farms.html?userId=<%=logedUser.getId()%>"><%=session.getAttribute("menu.myfarms")%></a></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"></td>
                        <td id="mnu" name="mnu" valign="bottom" nowrap width="20%"></td>
                    <%}%>
                </tr>
            </table>
        </td>
    </tr>
</table>
