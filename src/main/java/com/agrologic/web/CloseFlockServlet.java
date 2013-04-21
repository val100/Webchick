
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.FlockDao;
import com.agrologic.dao.impl.FlockDaoImpl;
import com.agrologic.dto.FlockDto;

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
public class CloseFlockServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(RemoveFlockServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out       = response.getWriter();
        Long        userId    = Long.parseLong(request.getParameter("userId"));
        Long        cellinkId = Long.parseLong(request.getParameter("cellinkId"));
        Long        flockId   = Long.parseLong(request.getParameter("flockId"));
        String      endDate   = request.getParameter("endDate");

        try {
            FlockDao flockDao = new FlockDaoImpl();
            FlockDto  flock    = flockDao.getById(flockId);

            flockDao.close(flockId, endDate);
            logger.info("Flock  " + flock + "successfully closed !");
            request.getSession().setAttribute("message", "Flock successfully  closed !");
            request.getSession().setAttribute("error", false);
            request.getRequestDispatcher("./flocks.html?userId=" + userId + "&cellinkId=" + cellinkId).forward(request,
                                         response);
        } catch (SQLException ex) {

            // error page
            logger.error("Error occurs during closing flock !");
            request.getSession().setAttribute("message", "Error occurs during closing flock !");
            request.getSession().setAttribute("error", true);
            request.getRequestDispatcher("./flocks.html?userId=" + userId + "&cellinkId=" + cellinkId).forward(request,
                                         response);
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
