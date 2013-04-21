
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.DataDao;
import com.agrologic.dao.ProgSysStateDao;
import com.agrologic.dao.ProgramAlarmDao;
import com.agrologic.dao.ProgramDao;
import com.agrologic.dao.ProgramRelayDao;
import com.agrologic.dao.impl.DataDaoImpl;
import com.agrologic.dao.impl.ProgramAlarmDaoImpl;
import com.agrologic.dao.impl.ProgramDaoImpl;
import com.agrologic.dao.impl.ProgramRelayDaoImpl;
import com.agrologic.dao.impl.ProgramSystemStateDaoImpl;
import com.agrologic.dto.DataDto;
import com.agrologic.dto.ProgramAlarmDto;
import com.agrologic.dto.ProgramDto;
import com.agrologic.dto.ProgramRelayDto;
import com.agrologic.dto.ProgramSystemStateDto;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JanL
 */
public class ProgramDetailsServet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(ProgramDetailsServet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (!CheckUserInSession.isUserInSession(request)) {
            logger.error("Unauthorized access!");
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }

        Long programId = Long.parseLong(request.getParameter("programId"));

        try {
            ProgramDao programDao = new ProgramDaoImpl();
            ProgramDto  program    = programDao.getById(programId);

            logger.info("retreive program details!");
            request.getSession().setAttribute("program", program);

            ProgramRelayDao      programRelayDao = new ProgramRelayDaoImpl();
            List<ProgramRelayDto> programRelays   = programRelayDao.getAllProgramRelays(program.getId());

            request.getSession().setAttribute("programRelays", programRelays);

            ProgramAlarmDao      programAlarmDao = new ProgramAlarmDaoImpl();
            List<ProgramAlarmDto> programAlarms   = programAlarmDao.getAllProgramAlarms(program.getId());

            request.getSession().setAttribute("programAlarms", programAlarms);

            ProgSysStateDao            programSystemStateDao = new ProgramSystemStateDaoImpl();
            List<ProgramSystemStateDto> programSystemStates   =
                programSystemStateDao.getAllProgramSystemStates(programId);

            request.getSession().setAttribute("programSystemStates", programSystemStates);

            DataDao      dataDao        = new DataDaoImpl();
            List<DataDto> relayDataTypes = dataDao.getProgramDataRelays(program.getId());

            request.getSession().setAttribute("relayDataTypes", relayDataTypes);

            List<DataDto> alarmDataTypes = dataDao.getProgramDataAlarms(program.getId());

            request.getSession().setAttribute("alarmDataTypes", alarmDataTypes);

            List<DataDto> sysStateDataTypes = dataDao.getProgramDataSystemStates(program.getId());

            request.getSession().setAttribute("sysStateDataTypes", sysStateDataTypes);
            request.getRequestDispatcher("./program-details.jsp").forward(request, response);
        } catch (SQLException ex) {
            logger.info("Error occurs while retreive controller details!");
            request.getRequestDispatcher("./all-programs.html").forward(request, response);
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
