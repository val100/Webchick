
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.ActionSetDao;
import com.agrologic.dao.CellinkDao;
import com.agrologic.dao.ControllerDao;
import com.agrologic.dao.DataDao;
import com.agrologic.dao.LanguageDao;
import com.agrologic.dao.ProgramDao;
import com.agrologic.dao.ProgramRelayDao;
import com.agrologic.dao.ScreenDao;
import com.agrologic.dao.impl.ActionSetDaoImpl;
import com.agrologic.dao.impl.CellinkDaoImpl;
import com.agrologic.dao.impl.ControllerDaoImpl;
import com.agrologic.dao.impl.DataDaoImpl;
import com.agrologic.dao.impl.LanguageDaoImpl;
import com.agrologic.dao.impl.ProgramDaoImpl;
import com.agrologic.dao.impl.ProgramRelayDaoImpl;
import com.agrologic.dao.impl.ScreenDaoImpl;
import com.agrologic.dto.ActionSetDto;
import com.agrologic.dto.CellinkDto;
import com.agrologic.dto.ControllerDto;
import com.agrologic.dto.DataDto;
import com.agrologic.dto.ProgramDto;
import com.agrologic.dto.ProgramRelayDto;
import com.agrologic.dto.ScreenDto;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class RCActionSetServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(RCGraphServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            long   userId       = Long.parseLong(request.getParameter("userId"));
            long   cellinkId    = Long.parseLong(request.getParameter("cellinkId"));
            long   controllerId = Long.parseLong(request.getParameter("controllerId"));
            long   screenId     = Long.parseLong(request.getParameter("screenId"));
            String lang         = (String) request.getSession().getAttribute("lang");

            if ((lang == null) || lang.equals("")) {
                lang = "en";
            }

            try {
                CellinkDao cellinkDao = new CellinkDaoImpl();
                CellinkDto  cellink    = cellinkDao.getById(cellinkId);

                cellinkDao.update(cellink);

                LanguageDao    languageDao   = new LanguageDaoImpl();
                long            langId        = languageDao.getLanguageId(lang);
                ControllerDao  controllerDao = new ControllerDaoImpl();
                ControllerDto   controller    = controllerDao.getById(controllerId);
                ProgramDao     programDao    = new ProgramDaoImpl();
                ProgramDto      program       = programDao.getById(controller.getProgramId());
                ScreenDao      screenDao     = new ScreenDaoImpl();
                List<ScreenDto> screens       = screenDao.getAllScreensByProgramAndLang(program.getId(), langId, false);

                program.setScreens(screens);
                controller.setProgram(program);

                ActionSetDao      actionsetDao = new ActionSetDaoImpl();
                List<ActionSetDto> actionset    = actionsetDao.getAllOnScreen(program.getId(), langId);

                logger.info("retreive program action set!");
                request.getSession().setAttribute("controller", controller);
                request.getSession().setAttribute("actionset", actionset);
                request.getRequestDispatcher("./rmctrl-controller-actionset.jsp?userId" + userId + "&cellinkId="
                                             + cellinkId + "&screenId=" + screenId).forward(request, response);
            } catch (SQLException ex) {

                // error page
                logger.error("SQLException", ex);
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
