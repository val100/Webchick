
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.CellinkDao;
import com.agrologic.app.dao.ControllerDao;
import com.agrologic.app.dao.UserDao;
import com.agrologic.app.dao.impl.CellinkDaoImpl;
import com.agrologic.app.dao.impl.ControllerDaoImpl;
import com.agrologic.app.dao.impl.UserDaoImpl;
import com.agrologic.app.model.CellinkDto;
import com.agrologic.app.model.ControllerDto;
import com.agrologic.app.model.UserDto;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JanL
 */
public class RemoveCellinkServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(RemoveCellinkServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (!CheckUserInSession.isUserInSession(request)) {
            logger.error("Unauthorized access!");
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        } else {
            Long userId    = Long.parseLong(request.getParameter("userId"));
            Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));

            try {
                UserDao       userDao       = new UserDaoImpl();
                CellinkDao    cellinkDao    = new CellinkDaoImpl();
                ControllerDao controllerDao = new ControllerDaoImpl();
                CellinkDto     cellink       = cellinkDao.getById(cellinkId);

                cellinkDao.remove(cellink.getId());
                logger.info("Cellink " + cellink + "successfully removed !");
                request.getSession().setAttribute("message", "Cellink successfully  removed !");
                request.getSession().setAttribute("error", false);

                List<UserDto> users     = new ArrayList<UserDto>();
                String        paramRole = request.getParameter("role");

                if ((paramRole == null) || "3".equals(paramRole)) {
                    users     = userDao.getAll();
                    paramRole = "3";
                } else {
                    int role = Integer.parseInt(paramRole);

                    users = userDao.getAllByRole(role);
                }

                for (UserDto u : users) {
                    List<CellinkDto> cellinks = (List<CellinkDto>) cellinkDao.getAllUserCellinks(u.getId());

                    for (CellinkDto c : cellinks) {
                        List<ControllerDto> controllers = controllerDao.getAllByCellinkId(c.getId());

                        c.setControllers(controllers);
                    }

                    u.setCellinks(cellinks);
                }

                logger.info("retreive all users ");
                request.getSession().setAttribute("users", users);
                request.getRequestDispatcher("./userinfo.html?userId=" + userId).forward(request, response);
            } catch (SQLException ex) {

                // error page
                logger.error("Error occurs while removing cellink !");
                request.getSession().setAttribute("message", "Error occurs while removing cellink !");
                request.getSession().setAttribute("error", true);
                request.getRequestDispatcher("./userinfo.html?userId=" + userId).forward(request, response);
            } finally {
                out.close();
            }
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
