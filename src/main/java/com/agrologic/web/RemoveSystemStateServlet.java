
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.SystemStateDao;
import com.agrologic.dao.impl.SystemStateDaoImpl;
import com.agrologic.dto.SystemStateDto;

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
 * @author Administrator
 */
public class RemoveSystemStateServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(RemoveSystemStateServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long translateLang = Long.parseLong(request.getParameter("translateLang"));
                Long systemstateId = Long.parseLong(request.getParameter("systemstateId"));

                try {
                    SystemStateDao systemstateDao = new SystemStateDaoImpl();
                    SystemStateDto  systemstate    = systemstateDao.getById(systemstateId);

                    systemstateDao.remove(systemstate.getId());
                    logger.info("SystemState " + systemstate + " successfully removed!");
                    request.getSession().setAttribute("message",
                                                      "SystemState <b style=\"color:gray\"> " + systemstate.getText()
                                                      + " </b> successfully  removed !");
                    request.getSession().setAttribute("error", false);
                    request.getRequestDispatcher("./all-systemstates.html?translateLang="
                                                 + translateLang).forward(request, response);
                } catch (SQLException ex) {

                    // error page
                    logger.error("Error occurs during removing systemstate" + ex.getMessage());
                    request.getSession().setAttribute("message", "Error occurs during removing systemstate !");
                    request.getSession().setAttribute("error", true);
                    request.getRequestDispatcher("./all-systemstates.html").forward(request, response);
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
