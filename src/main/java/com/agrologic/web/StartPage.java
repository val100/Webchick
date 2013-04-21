
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.CellinkDao;
import com.agrologic.dao.UserDao;
import com.agrologic.dao.impl.CellinkDaoImpl;
import com.agrologic.dao.impl.UserDaoImpl;
import com.agrologic.dto.CellinkDto;
import com.agrologic.dto.UserDto;

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
 * @author Administrator
 */
public class StartPage extends HttpServlet {
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
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter  out    = response.getWriter();
        final Logger logger = Logger.getLogger(StartPage.class);

        try {
            String access = request.getParameter("access");

            if (access.equals("regular")) {
                request.getSession().setAttribute("access", access);

                CellinkDao cellinkDao = new CellinkDaoImpl();
                CellinkDto  cellink    = cellinkDao.getActualCellink();

                logger.info(cellink);

                UserDao userDao = new UserDaoImpl();
                UserDto  user    = userDao.getById(cellink.getUserId());

                logger.info(user);
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("./rmctrl-main-screen-ajax.jsp?userId=" + cellink.getUserId()
                                             + "&cellinkId=" + cellink.getId()).forward(request, response);
            } else {
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error(e);
            request.getRequestDispatcher("./errorPage.jsp").forward(request, response);
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
