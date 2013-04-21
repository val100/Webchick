
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.CellinkDao;
import com.agrologic.dao.ControllerDao;
import com.agrologic.dao.ProgramDao;
import com.agrologic.dao.UserDao;
import com.agrologic.dao.impl.CellinkDaoImpl;
import com.agrologic.dao.impl.ControllerDaoImpl;
import com.agrologic.dao.impl.ProgramDaoImpl;
import com.agrologic.dao.impl.UserDaoImpl;
import com.agrologic.dto.CellinkDto;
import com.agrologic.dto.ControllerDto;
import com.agrologic.dto.ProgramDto;
import com.agrologic.dto.UserDto;

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
 * @author Administrator
 */
public class CellinkSetting extends HttpServlet {
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
        final Logger logger = Logger.getLogger(CellinkInfoServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long userId    = Long.parseLong(request.getParameter("userId"));
                Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));

                try {
                    ProgramDao      programDao = new ProgramDaoImpl();
                    List<ProgramDto> programs   = programDao.getAll();

                    request.getSession().setAttribute("programs", programs);

                    UserDao userDao  = new UserDaoImpl();
                    UserDto  editUser = userDao.getById(userId);

                    editUser.setCellinks(new ArrayList<CellinkDto>());

                    CellinkDao         cellinkDao    = new CellinkDaoImpl();
                    CellinkDto          c             = cellinkDao.getById(cellinkId);
                    ControllerDao      controllerDao = new ControllerDaoImpl();
                    List<ControllerDto> controllers   = controllerDao.getAllByCellinkId(c.getId());

                    for (ControllerDto ctrl : controllers) {
                        ProgramDto program = programDao.getById(ctrl.getProgramId());

                        ctrl.setProgram(program);
                    }

                    c.setControllers(controllers);
                    editUser.addCellink(c);
                    logger.info("retreive user");
                    logger.info("retrieve cellinks by user");
                    logger.info("retrieve controllers by user cellinks");
                    request.getSession().setAttribute("edituser", editUser);

                    List<String> controllernames = controllerDao.getControllerNames();

                    logger.info("retrieve controller names");
                    request.getSession().setAttribute("controllernames", controllernames);
                    response.sendRedirect("./cellink-setting.jsp?userId=" + userId + "&cellinkId=" + cellinkId);
                } catch (SQLException ex) {

                    // error page
                    logger.error("Database error.", ex);
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
