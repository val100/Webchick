
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.app.dao.DataDao;
import com.agrologic.app.dao.ProgramDao;
import com.agrologic.app.dao.TableDao;
import com.agrologic.app.dao.impl.DataDaoImpl;
import com.agrologic.app.dao.impl.ProgramDaoImpl;
import com.agrologic.app.dao.impl.TableDaoImpl;
import com.agrologic.app.model.ProgramDto;
import com.agrologic.app.model.TableDto;
import com.agrologic.app.utils.DateLocal;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JanL
 */
public class EditTableFormServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * Logger for this class and subclasses
         */
        final Logger logger = Logger.getLogger(EditTableFormServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long programId = Long.parseLong(request.getParameter("programId"));
                Long oldScreenId = Long.parseLong(request.getParameter("screenId"));
                Long tableId = Long.parseLong(request.getParameter("tableId"));
                String newTitle = request.getParameter("Ntabletitle");
                Long newScreenId = Long.parseLong(request.getParameter("NscreenId"));
                Integer newPosition = Integer.parseInt(request.getParameter("Nposition"));
                TableDao tableDao = new TableDaoImpl();
                DataDao dataDao = new DataDaoImpl();

                try {
                    TableDto table = tableDao.getById(programId, oldScreenId, tableId);
                    if (table != null) {
                        table.setTitle(newTitle);
                        table.setPosition(newPosition);
                        table.setScreenId(newScreenId);
                        tableDao.moveTable(table, oldScreenId);
                    }

                    logger.info("Table " + table + " successfully updated !");
                    dataDao.moveData(newScreenId, programId, tableId);
                    logger.info("Data from table " + table + " successfully moved !");

                    ProgramDao programDao = new ProgramDaoImpl();
                    ProgramDto program = programDao.getById(programId);

                    program.setModifiedDate(DateLocal.currentDate());
                    programDao.update(program);
                    request.getSession().setAttribute("message", "Table successfully updated !");
                    request.getSession().setAttribute("error", false);
                    request.getRequestDispatcher("./all-tables.html?programId=" + programId + "&screenId="
                            + oldScreenId).forward(request, response);
                } catch (SQLException ex) {
                    logger.error("Error occurs while updating table !" + "\n" + ex.getMessage());
                    ex.printStackTrace();
                    request.getSession().setAttribute("message", "Error occurs while updating table !");
                    request.getSession().setAttribute("error", true);
                    request.getRequestDispatcher("./all-tables.html?programId=" + programId + "&screenId="
                            + oldScreenId).forward(request, response);
                }
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }    // </editor-fold>
}


//~ Formatted by Jindent --- http://www.jindent.com
