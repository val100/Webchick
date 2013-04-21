
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.ProgramDao;
import com.agrologic.dao.ScreenDao;
import com.agrologic.dao.impl.ProgramDaoImpl;
import com.agrologic.dao.impl.ScreenDaoImpl;
import com.agrologic.dto.ProgramDto;
import com.agrologic.dto.ScreenDto;
import com.agrologic.utils.DateLocal;

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
public class AddScreenFormServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(AddProgramServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long   programId      = Long.parseLong(request.getParameter("programId"));
                String screenTitle    = (String) request.getParameter("NscreenTitle");
                String screenDescript = (String) request.getParameter("NscreenDescript");

                try {
                    ScreenDao screenDao = new ScreenDaoImpl();
                    int        size      = screenDao.getNextScreenPosByProgramId(programId);
                    ScreenDto  screen    = new ScreenDto();

                    screen.setProgramId(programId);
                    screen.setTitle(screenTitle);
                    screen.setDisplay("yes");
                    screen.setPosition(size + 1);
                    screen.setDescript(screenDescript);
                    screenDao.insert(screen);
                    logger.info("New screen successfully added !");

                    ProgramDao programDao = new ProgramDaoImpl();
                    ProgramDto  program    = programDao.getById(programId);

                    program.setModifiedDate(DateLocal.currentDate());
                    programDao.update(program);
                    request.getSession().setAttribute("message", "screen successfully added !");
                    request.getSession().setAttribute("error", false);
                    request.getRequestDispatcher("./all-screens.html?programId=" + programId).forward(request,
                                                 response);
                } catch (SQLException ex) {

                    // error page
                    logger.error("Error occurs while adding screen !", ex);
                    request.getSession().setAttribute("message", "Error occurs while adding table!");
                    request.getSession().setAttribute("error", true);
                    request.getRequestDispatcher("./all-screens.html?programId=" + programId).forward(request,
                                                 response);
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
