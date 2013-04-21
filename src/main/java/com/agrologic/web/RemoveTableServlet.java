
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.TableDao;
import com.agrologic.dao.impl.TableDaoImpl;
import com.agrologic.dto.TableDto;

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
public class RemoveTableServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(RemoveTableServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long programId = Long.parseLong(request.getParameter("programId"));
                Long screenId  = Long.parseLong(request.getParameter("screenId"));
                Long tableId   = Long.parseLong(request.getParameter("tableId"));

                try {
                    TableDao tableDao = new TableDaoImpl();
                    TableDto  table    = tableDao.getById(programId, screenId, tableId);

                    tableDao.remove(table.getProgramId(), table.getScreenId(), table.getId());
                    logger.info("Table " + table + "successfully removed !");
                    request.getSession().setAttribute("message", "Table successfully removed !");
                    request.getSession().setAttribute("error", false);
                    request.getRequestDispatcher("./all-tables.html?programId=" + programId + "&screenId="
                                                 + screenId).forward(request, response);
                } catch (SQLException ex) {

                    // error page
                    logger.error("Error occurs while removing controlller !");
                    request.getSession().setAttribute("message", "Error occurs while removing table !");
                    request.getSession().setAttribute("error", true);
                    request.getRequestDispatcher("./all-tables.html?screenId=" + screenId).forward(request, response);
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
