
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.ProgramDao;
import com.agrologic.app.dao.impl.ProgramDaoImpl;
import com.agrologic.app.model.ProgramDto;
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
public class ListProgramServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(ListProgramServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                try {
                    String searchText = request.getParameter("searchText");

                    if (searchText == null) {
                        searchText = "";
                    }

                    String index = request.getParameter("index");

                    if (index == null) {
                        index = "0";
                    }

                    UserDto          user       = (UserDto) request.getSession().getAttribute("user");
                    ProgramDao       programDao = new ProgramDaoImpl();
                    int              count      = programDao.count();
                    List<ProgramDto> programs   = new ArrayList<ProgramDto>();
                    List<ProgramDto> allPrograms = new ArrayList<ProgramDto>();
                    allPrograms = programDao.getAll();

                    switch (user.getRole()) {
                    case UserRole.REGULAR :
                        programs = programDao.getAllByUserId(searchText, user.getId());
                        setTableParameters(request, index, count);

                        break;

                    case UserRole.ADMINISTRATOR :
                        programs = programDao.getAll(searchText, index);
                        setTableParameters(request, index, count);

                        break;

                    case UserRole.ADVANCED :
                        programs = programDao.getAllByUserCompany(searchText, user.getCompany());
                        setTableParameters(request, index, count);

                        break;

                    default :
                        break;
                    }

                    request.getSession().setAttribute("allprograms", allPrograms);
                    request.getSession().setAttribute("programs", programs);
                    request.getRequestDispatcher("./all-programs.jsp").forward(request, response);
                } catch (SQLException ex) {

                    // error page
                    logger.error("database error ! " + ex.getMessage());
                    request.setAttribute("errormessage", ex.getMessage());
                    request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
                }
            }
        } finally {
            out.close();
        }
    }

    private void setTableParameters(HttpServletRequest request, String index, int count) {
        if (index.equals("0")) {
            int from = 1;
            int to   = 25;
            int of   = count;

            request.getSession().setAttribute("from", from);
            request.getSession().setAttribute("to", to);
            request.getSession().setAttribute("of", of);
        } else {
            int from = Integer.parseInt(index);
            int to   = from + ((count - from) > 25
                               ? 25
                               : (count - from));
            int of   = count;

            request.getSession().setAttribute("from", from);
            request.getSession().setAttribute("to", to);
            request.getSession().setAttribute("of", of);
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
