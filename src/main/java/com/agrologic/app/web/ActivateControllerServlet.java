
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.ControllerDao;
import com.agrologic.app.dao.impl.ControllerDaoImpl;
import com.agrologic.app.model.ControllerDto;

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
public class ActivateControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter  out    = response.getWriter();
        final Logger logger = Logger.getLogger(ActivateControllerServlet.class);

        request.getRequestURL();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long   userId       = Long.parseLong(request.getParameter("userId"));
                Long   cellinkId    = Long.parseLong(request.getParameter("cellinkId"));
                Long   controllerId = Long.parseLong(request.getParameter("controllerId"));
                String active       = request.getParameter("active");
                String url          = request.getParameter("url");
                String message      = "";

                try {
                    ControllerDao controllerDao = new ControllerDaoImpl();
                    ControllerDto  controller    = controllerDao.getById(controllerId);

                    if ((active != null) && "ON".equals(active.toUpperCase())) {
                        message = "Controller with ID " + controller.getId() + " activated .";
                        controller.setActive(true);
                    } else {
                        message = "Controller with ID " + controller.getId() + " deactivated .";
                        controller.setActive(false);
                    }

                    controllerDao.update(controller);
                    logger.info(message);
                    request.getSession().setAttribute("message", message);
                    request.getSession().setAttribute("error", false);

                    if (url.equals("cellink-setting.html")) {
                        request.getRequestDispatcher("./cellink-setting.html?userId" + userId + "&cellinkId"
                                                     + cellinkId).forward(request, response);
                    } else {
                        request.getRequestDispatcher("./overview.html?userId" + userId + "&cellinkId"
                                                     + cellinkId).forward(request, response);
                    }
                } catch (SQLException ex) {
                    logger.info("Error occurs while updating controller   with ID " + controllerId, ex);
                    request.getSession().setAttribute("message",
                                                      "Error occurs while updating controller with ID " + controllerId);
                    request.getSession().setAttribute("error", true);

                    if (url.equals("cellink-setting.html")) {
                        request.getRequestDispatcher("./cellink-setting.html?userId" + userId + "&cellinkId"
                                                     + cellinkId).forward(request, response);
                    } else {
                        request.getRequestDispatcher("./overview.html?userId" + userId + "&cellinkId"
                                                     + cellinkId).forward(request, response);
                    }
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
