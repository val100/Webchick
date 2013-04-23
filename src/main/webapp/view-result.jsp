<%--
    Document   : veiw-result
    Created on : Dec 30, 2009, 4:20:44 PM
    Author     : JanL
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="disableCaching.jsp" %>
<%@ page errorPage="anerrorpage.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<jsp:directive.page import="com.agrologic.app.model.UserDto"/>
<jsp:directive.page import="com.agrologic.app.web.UserRole"/>
<jsp:directive.page import="com.agrologic.app.model.ProgramDto"/>
<jsp:directive.page import="com.agrologic.app.model.ScreenDto"/>
<jsp:directive.page import="com.agrologic.app.model.TableDto"/>
<jsp:directive.page import="com.agrologic.app.model.DataDto"/>
<jsp:directive.page import="com.agrologic.app.model.LanguageDto"/>
<jsp:directive.page import="com.agrologic.app.model.ProgramRelayDto"/>

<%  UserDto user = (UserDto) request.getSession().getAttribute("user");
    if (user == null) {
        response.sendRedirect("./index.htm");
        return;
    }

    Long screenId = Long.parseLong(request.getParameter("screenId"));
    Long screenLangId = Long.parseLong(request.getParameter("screenLangId"));
    ProgramDto program = (ProgramDto) request.getSession().getAttribute("program");
    List<DataDto> dataRelays = (List<DataDto>) request.getSession().getAttribute("dataRelays");
    List<ProgramDto> programs = (List<ProgramDto>) request.getSession().getAttribute("programs");
    List<LanguageDto> languages = (List<LanguageDto>) request.getSession().getAttribute("languages");
%>
<%! ScreenDto getCurrentScreen(Long screenId, List<ScreenDto> screens) {

        for (ScreenDto screen : screens) {
            if (screenId.equals(screen.getId())) {
                return screen;
            }
        }
        return null;
    }
%>
<%!    List<ProgramRelayDto> getProgramRelaysByRelayType(List<ProgramRelayDto> dataRelays, Long relayType) {
        List<ProgramRelayDto> relayList = new ArrayList<ProgramRelayDto>();
        for (ProgramRelayDto pr : dataRelays) {
            if (pr.getDataId().equals(relayType)) {
                relayList.add(pr);
            }
        }
        return relayList;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Screens Preview </title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
        <link rel="StyleSheet" type="text/css" href="css/menubar.css"/>
        <link rel="StyleSheet" type="text/css" href="css/admincontent.css"/>
        <link rel="stylesheet" type="text/css" href="css/tabstyle.css"/>
        <script type="text/javascript">
            function back(link) {
                window.document.location.replace(link);
                return false;
            }
            function addProgram(){
                window.document.location.replace("./add-program.jsp");
                return false;
            }
            function removeProgram(programId)
            {
                if(confirm("Are you sure ?") ==  true) {
                    window.document.location.replace("./removeprogram.html?programId="+programId);
                }
            }
            function filterPrograms()
            {
                var programId = document.formFilterPrograms.Program_Filter.value;
                var langId = document.formFilterLanguages.Lang_Filter.value;
                window.document.location.replace("./view-result.html?programId=" + programId+"&screenLangId=" + langId);
                return false;
            }
            function filterLanguages()
            {
                var programId = document.formFilterPrograms.Program_Filter.value;
                var langId = document.formFilterLanguages.Lang_Filter.value;
                window.document.location.replace("./view-result.html?programId=" + programId+"&screenLangId=" + langId);
                return false;
            }
        </script>
    </head>
    <body>
        <div id="header">
            <%@include file="usermenuontop.jsp"%>
        </div>
        <div id="main-shell">
            <table border=0 cellPadding=1 cellSpacing=1  width="100%">
                <tr>
                    <td>
                        <h1>Preview</h1>
                        <% ScreenDto currScreen = getCurrentScreen(screenId, program.getScreens());%>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" width="100%">
                        <table>
                            <tr>
                                <td>
                                    <form id="formFilterPrograms" name="formFilterPrograms">
                                        Program&nbsp;<select id="Program_Filter" name="Program_Filter" onchange="return filterPrograms();">
                                            <%for (ProgramDto p : programs) {%>
                                            <option value="<%=p.getId()%>"><%=p.getName()%></option>
                                            <%}%>
                                        </select>
                                    </form>
                                </td>
                                <td>
                                    <form id="formFilterLanguages" name="formFilterLanguages">
                                        Language&nbsp;
                                        <select id="Lang_Filter" name="Lang_Filter" onchange="return filterLanguages();">
                                            <%for (LanguageDto l : languages) {%>
                                            <option value="<%=l.getId()%>"><%=l.getLanguage()%></option>
                                            <%}%>
                                        </select>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table width="100%">
                            <tr>
                                <td width="100%">
                                    <table border="0" id="topnav" width="100%">
                                        <tr>
                                            <%int col = 0;%>
                                            <%List<ScreenDto> screens = program.getScreens();%>
                                            <%for (ScreenDto screen : screens) {%>
                                            <% if ((col % 6) == 0) {%>
                                        </tr>
                                        <tr>
                                            <% }%>
                                            <% col++;%>
                                            <%String cssClass = "";%>
                                            <% if (screen.getId() == currScreen.getId()) {%>
                                            <% cssClass = "active";%>
                                            <% } else {%>
                                            <% cssClass = "";%>
                                            <% }%>
                                            <td>
                                                <a class="<%=cssClass%>" href="./view-result.html?programId=<%=program.getId()%>&screenId=<%=screen.getId()%>&screenLangId=<%=screenLangId%>"><%=screen.getUnicodeTitle()%></a>
                                            </td>
                                            <%}%>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td width="100%">
                        <table cellPadding="2" cellSpacing="2"  width="100%">
                            <%
                                int column = 0;
                                List<TableDto> tables = currScreen.getTables();
                                if (tables.size() > 0) {%>
                                <%for (TableDto table : tables) {
                            if ((column % ScreenDto.COLUMN_NUMBERS) == 0) {%>
                            <tr>
                                <%}%>
                                <td valign="top">
                                    <form id="formTable<%=table.getId()%>" name="formTable<%=table.getId()%>">
                                        <table class="table-screens" border="1">
                                            <thead>
                                                <th width="150px" nowrap colspan="2" align="left"><%=table.getUnicodeTitle()%></th>
                                            </thead>
                                            <body>
                                                <%for (DataDto data : table.getDataList()) {%>
                                                <tr>
                                                    <td nowrap class="label"><%=data.getUnicodeLabel()%></td>
                                                    <td nowrap class="value"><%=data.displayTemplateValue()%></td>
                                                </tr>
                                                <%}%>
                                            </body>
                                        </table>
                                    </form>
                                </td>
                                <%column++;%>
                                <%if ((column % ScreenDto.COLUMN_NUMBERS) == 0) {%>
                            </tr>
                            <%}%>

                            <%}%>
                            <%}%>
                        </table>
                    </td>
                </tr>
            </table>
            </table>
        </div>

        <script language="Javascript">
            var length = document.formFilterPrograms.Program_Filter.options.length;
            for(var i = 0; i < length; i++) {
                var programId = parseInt(<%=request.getParameter("programId")%>);
                if(document.formFilterPrograms.Program_Filter.options[i].value == programId) {
                    document.formFilterPrograms.Program_Filter.selectedIndex=i;
                    break;
                }
            }
            var length = document.formFilterLanguages.Lang_Filter.options.length;
            for(var i = 0; i < length; i++) {
                var screenLangId = parseInt(<%=request.getParameter("screenLangId")%>);
                if(document.formFilterLanguages.Lang_Filter.options[i].value == screenLangId) {
                    document.formFilterLanguages.Lang_Filter.selectedIndex=i;
                    break;
                }
            }
        </script>
    </body>
</html>