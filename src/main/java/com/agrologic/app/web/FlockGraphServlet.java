
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.ControllerDao;
import com.agrologic.app.dao.DataDao;
import com.agrologic.app.dao.FlockDao;
import com.agrologic.app.dao.impl.ControllerDaoImpl;
import com.agrologic.app.dao.impl.DataDaoImpl;
import com.agrologic.app.dao.impl.FlockDaoImpl;
import com.agrologic.app.model.ControllerDto;
import com.agrologic.app.model.DataDto;
import com.agrologic.app.model.FlockDto;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JanL
 */
public class FlockGraphServlet extends HttpServlet {
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
        final Logger logger = Logger.getLogger(FlockGraphServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            Long   cellinkId   = Long.parseLong(request.getParameter("cellinkId"));
            Long   flockId     = Long.parseLong(request.getParameter("flockId"));
            String currGrowDay = request.getParameter("growDay");

            try {
                FlockDao      flockDao      = new FlockDaoImpl();
                FlockDto      flock         = flockDao.getById(flockId);
                ControllerDao controllerDao = new ControllerDaoImpl();
                ControllerDto controller    = controllerDao.getById(flock.getControllerId());
                DataDao       dataDao       = new DataDaoImpl();
                DataDto       data          = dataDao.getGrowDay(flock.getControllerId());

                if (currGrowDay == null) {
                    if (data.getValue() == null || Long.valueOf(-1).equals(data.getValue())) {
                        currGrowDay = "1";
                    } else {
                        currGrowDay = data.printDataValue();
                    }
                }

                request.getSession().setAttribute("flockName", flock.getFlockName());
                request.getSession().setAttribute("houseName", controller.getTitle());
                request.getRequestDispatcher("./rmctrl-flock-graphs.jsp?cellinkId=" + cellinkId + "&growDay="
                                             + currGrowDay).forward(request, response);
            } catch (Exception ex) {
                logger.trace("Fail save history setting", ex);
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
