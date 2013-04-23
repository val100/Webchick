<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="anerrorpage.jsp"%>
<%@ include file="disableCaching.jsp" %>
<%@ include file="language.jsp" %>

<%@ page import="java.util.List"%>

<jsp:directive.page import="com.agrologic.app.web.UserRole"/>
<jsp:directive.page import="com.agrologic.app.model.UserDto"/>
<jsp:directive.page import="com.agrologic.app.model.ProgramDto"/>
<jsp:directive.page import="com.agrologic.app.model.ScreenDto"/>
<jsp:directive.page import="com.agrologic.app.model.TableDto"/>
<jsp:directive.page import="com.agrologic.app.model.DataDto"/>
<jsp:directive.page import="com.agrologic.app.model.LanguageDto"/>

<%  UserDto user = (UserDto)request.getSession().getAttribute("user");
    if(user == null) {
        response.sendRedirect("./index.htm");
        return;
    }
    ProgramDto program = (ProgramDto)request.getSession().getAttribute("program");
    ScreenDto screen = (ScreenDto)request.getSession().getAttribute("screen");
    List<TableDto> tables = screen.getTables();
    List<LanguageDto> languages = (List<LanguageDto>)request.getSession().getAttribute("languages");
    String ptl = (String)request.getParameter("translateLang");
    if(ptl == null) {
        ptl = "1";
    }
    Long translateLang = Long.parseLong(ptl);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Screen Manager - Tables On Screen</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
        <link rel="StyleSheet" type="text/css" href="css/menubar.css">
        <link rel="StyleSheet" type="text/css" href="css/admincontent.css">
        <script type="text/javascript" src="js/util.js"></script>
        <script type="text/javascript">
        function addTable(){
            window.document.location.replace("./add-table.jsp");
            return false;
        }
        function save(programId,screenId) {
            var showTableMap = new Hashtable();
            var posTableMap = new Hashtable();

            var chb = document.formScreen.list;
            var poss = document.formScreen.position;
            // if only one check table-list
            if(chb.tagName == "INPUT") {
                var tableId = chb.value;
                if (chb.checked ) {
                    showTableMap.put(tableId,"yes");
                } else {
                    showTableMap.put(tableId,"no");
                }
                var poss = poss.value;
                posTableMap.put(tableId,poss);
            // if two or more check table-list
            } else {
                for( var i = 0; i < chb.length; i++) {
                    var tableId = chb[i].value;
                    if (chb[i].checked ) {
                        showTableMap.put(tableId,"yes");
                    } else {
                        showTableMap.put(tableId,"no");
                    }
                    var pos = poss[i].value;
                    posTableMap.put(tableId,pos);
                }
            }
            window.document.location.replace("./save-tables.html?programId="+programId+"&screenId="+ screenId +"&showTableMap=" + showTableMap +"&posTableMap=" + posTableMap);
        }
        function removeTable(programId,screenId,tableId)
        {
            if(confirm("This action will remove table from database.\nDo you want to continue ?") ==  true) {
                window.document.location.replace("./removetable.html?programId=" + programId + "&screenId=" + screenId + "&tableId="+ tableId);
            }
        }
        function checkedAll () {
            var form = document.getElementById('formScreen');
            var checkall = document.getElementById('listall');
            for (var i = 0; i < form.elements.length; i++) {
                if (checkall.checked == true) {
                    form.elements[i].checked = true;
                } else {
                    form.elements[i].checked = false;
                }
            }
         }
        function check(index) {
            var form = document.getElementById('formScreen');
            var checkall = document.getElementById('listall');

            for (var i = 0; i < form.elements.length; i++) {
                if (form.elements[i].value == index && form.elements[i].checked == false) {
                    checkall.checked = false;
                }
            }
         }
        function back(link){
            window.document.location.replace(link);
            return false;
        }
        function filterLanguages(programId,screenId)
        {
           var langId = document.formScreen.Lang_Filter.value;
           window.document.location.replace("./all-tables.html?programId=" + programId+"&screenId="+ screenId +"&translateLang=" + langId);
           return false;
        }
        </script>
    </head>
    <body>
    <div id="header">
        <%@include file="usermenuontop.jsp"%>
    </div>
    <div id="main-shell">
      <table border="0" cellPadding=1 cellSpacing=1 width="100%">
        <tr>
          <td valign="top" height="648px">
            <table border=0 cellPadding=1 cellSpacing=1>
              <tr>
                <td>
                    <h1><%=session.getAttribute("tables.page.header") %></h1>
                    <h2><%=session.getAttribute("tables.page.sub.header") %></h2>
                    <h3>Program - <font color="teal"><%=program.getName()%></font>;Screen -  <font color="teal"><%=screen.getTitle()%></h3>
                </td>
                <td align="center" colspan="3">
                    <%@include file="messages.jsp" %>
                </td>
              </tr>
              <tr>
                <td>
                    <button id="btnCancel" style="float:left" name="btnCancel" onclick='return back("./all-screens.html?programId=<%=screen.getProgramId()%>&translateLang=<%=translateLang%>");'>
                        <%=session.getAttribute("button.back") %></button>
                    <button id="btnSave" style="float:left" name="btnSave" onclick="return save(<%=screen.getProgramId() %>,<%=screen.getId()%>);">
                        <%=session.getAttribute("button.save") %></button>
                    <button id="Add" name="Add" onclick="window.document.location.replace('./add-table.jsp');">
                        <%=session.getAttribute("button.add.table") %></button>
                </td>
              </tr>
              <tr>
                <td colspan=3>
                <form id="formScreen" name="formScreen">
                <%=tables.size()%>&nbsp;</b><%=session.getAttribute("label.records")%>
                <table class="table-list" border="1" style="behavior:url(tablehl.htc) url(sort.htc);">
                    <thead>
                        <tr>
                            <th class="centerHeader" width="150px">Title </th>
                            <th class="centerHeader" width="200px">Text
                            <select id="Lang_Filter" name="Lang_Filter" onchange="return filterLanguages(<%=screen.getProgramId()%>,<%=screen.getId()%>);">
                                <%for(LanguageDto l : languages){ %>
                                    <option value="<%=l.getId()%>"><%=l.getLanguage()%></option>
                                <%}%>
                            </select>
                            </th>
                            <th class="centerHeader" width="150px">Show All &nbsp;&nbsp;&nbsp;<input type="checkbox" id="listall" name="listall" title="Show" onclick="checkedAll();"></th>
                            <th class="centerHeader" width="150px">Position</th>
                            <th class="centerHeader" width="300px" colspan="2">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        for (TableDto table:tables) {%>
                        <tr onmouseover="this.style.background='#CEDEF4'" onmouseout="this.style.background='white'" title="Click for details">
                            <td class="leftCell"><a href="./all-tabledata.html?programId=<%=screen.getProgramId()%>&screenId=<%=table.getScreenId() %>&tableId=<%=table.getId()%>&translateLang=<%=translateLang%>"><%=table.getTitle()%></a></td>
                            <td class="leftCell" width="150px" ondblclick="window.open('add-tabletranslate.jsp?tableId=<%=table.getId()%>&langId=<%=translateLang%>&tableName=<%=table.getTitle()%>','mywindow','status=yes,width=300,height=250,left=350,top=400,screenX=100,screenY=100');"><%=table.getUnicodeTitle() %></td>
                            <td class="centerCell" width="150px">
                                <input type="checkbox" name="list" <%=table.isChecked()%> value="<%=table.getId()%>" onclick="check(<%=table.getId()%>);"></td>
                            <td class="centerCell" width="150px">
                                <input type="text" name="position" value="<%=table.getPosition() %>" size="5"></td>
                            <td class="centerCell">
                                <img src="img/edit.gif" style="cursor: pointer" border="0" title='Edit This Table'/>
                                <a href="./edit-table.jsp?tableId=<%=table.getId()%>"><%=session.getAttribute("button.edit") %></a>
                            </td>
                            <td class="centerCell">
                                <img src="img/close.png" hspace="5" border="0"/>
                                <a href="javascript:removeTable(<%=screen.getProgramId()%>,<%=table.getScreenId()%>,<%=table.getId()%>);">
                                    <%=session.getAttribute("button.delete") %></a>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                 </table>
                </form>
                </td>
              </tr>
              <tr>
                <td>
                    <button id="btnCancel" style="float:left" name="btnCancel" onclick='return back("./all-screens.html?programId=<%=screen.getProgramId()%>&translateLang=<%=translateLang%>");'>
                        <%=session.getAttribute("button.back") %></button>
                    <button id="btnSave" style="float:left" name="btnSave" onclick="return save(<%=screen.getProgramId() %>,<%=screen.getId()%>);">
                        <%=session.getAttribute("button.save") %></button>
                </td>
              </tr>
            </table>
            <script language="Javascript">
                var length = document.formScreen.Lang_Filter.options.length;
                for(var i = 0; i < length; i++) {
                    var translateLang = parseInt(<%=request.getParameter("translateLang")%>);
                    if(document.formScreen.Lang_Filter.options[i].value == translateLang) {
                        document.formScreen.Lang_Filter.selectedIndex=i;
                        break;
                    }
                }
            </script>
          </td>
        </tr>
    </table>
    </div>

</body>
</html>