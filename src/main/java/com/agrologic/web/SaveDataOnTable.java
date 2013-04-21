
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.DataDao;
import com.agrologic.dao.ProgramDao;
import com.agrologic.dao.TableDao;
import com.agrologic.dao.impl.DataDaoImpl;
import com.agrologic.dao.impl.ProgramDaoImpl;
import com.agrologic.dao.impl.TableDaoImpl;
import com.agrologic.dto.ProgramDto;
import com.agrologic.dto.TableDto;
import com.agrologic.utils.DateLocal;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JanL
 */
public class SaveDataOnTable extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /** Logger for this class and subclasses */
        final Logger logger = Logger.getLogger(SaveDataOnTable.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long              programId      = Long.parseLong(request.getParameter("programId"));
                Long              screenId       = Long.parseLong(request.getParameter("screenId"));
                Long              tableId        = Long.parseLong(request.getParameter("tableId"));
                String            showDataMapStr = request.getParameter("showDataMap");
                String            posDataMapStr  = request.getParameter("posDataMap");
                String[]          showTablePairs = showDataMapStr.split(";");
                Map<Long, String> showTableMap   = new HashMap<Long, String>();

                for (String s : showTablePairs) {
                    StringTokenizer st     = new StringTokenizer(s, ",");
                    Long            dataId = Long.parseLong(st.nextToken());
                    String          show   = st.nextToken();

                    showTableMap.put(dataId, show);
                }

                String[]           posDataPairs = posDataMapStr.split(";");
                Map<Long, Integer> posDataMap   = new HashMap<Long, Integer>();

                for (String s : posDataPairs) {
                    StringTokenizer st     = new StringTokenizer(s, ",");
                    Long            dataId = Long.parseLong(st.nextToken());
                    Integer         pos    = Integer.parseInt(st.nextToken());

                    posDataMap.put(dataId, pos);
                }

                try {
                    TableDao tableDao = new TableDaoImpl();
                    TableDto  table    = tableDao.getById(programId, screenId, tableId);
                    DataDao  dataDao  = new DataDaoImpl();

                    dataDao.saveChanges(programId, screenId, tableId, showTableMap, posDataMap);

                    ProgramDao programDao = new ProgramDaoImpl();
                    ProgramDto  program    = programDao.getById(programId);

                    program.setModifiedDate(DateLocal.currentDate());
                    programDao.update(program);
                    request.getSession().setAttribute("message", "Changes successfully saved !");
                    request.getSession().setAttribute("error", false);
                    request.getRequestDispatcher("./all-tables.html?screenId=" + table.getScreenId() + "&tableId="
                                                 + tableId).forward(request, response);
                } catch (SQLException ex) {
                    logger.error("Error occurs while updating program !");
                    request.getSession().setAttribute("message", "Error occurs while saving changes !");
                    request.getSession().setAttribute("error", true);
                    request.getRequestDispatcher("./all-tables.html?tableId=" + tableId).forward(request, response);
                }
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }    // </editor-fold>
}


//~ Formatted by Jindent --- http://www.jindent.com
