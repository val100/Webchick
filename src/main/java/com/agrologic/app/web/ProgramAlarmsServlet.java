
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.AlarmDao;
import com.agrologic.app.dao.DataDao;
import com.agrologic.app.dao.ProgramAlarmDao;
import com.agrologic.app.dao.ProgramDao;
import com.agrologic.app.dao.impl.AlarmDaoImpl;
import com.agrologic.app.dao.impl.DataDaoImpl;
import com.agrologic.app.dao.impl.ProgramAlarmDaoImpl;
import com.agrologic.app.dao.impl.ProgramDaoImpl;
import com.agrologic.app.model.AlarmDto;
import com.agrologic.app.model.DataDto;
import com.agrologic.app.model.ProgramAlarmDto;
import com.agrologic.app.model.ProgramDto;

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
public class ProgramAlarmsServlet extends HttpServlet {

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

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long programId = Long.parseLong(request.getParameter("programId"));

                try {
                    ProgramDao programDao = new ProgramDaoImpl();
                    ProgramDto  program    = programDao.getById(programId);

                    logger.info("retrieve program!");

                    String[]              empty           = new String[0];
                    ProgramAlarmDao      programAlarmDao = new ProgramAlarmDaoImpl();
                    List<ProgramAlarmDto> programAlarms   = programAlarmDao.getAllProgramAlarms(program.getId(), empty);

                    program.setProgramAlarms(programAlarms);
                    logger.info("retreive program alarms!");
                    request.getSession().setAttribute("program", program);

                    DataDao      dataDao    = new DataDaoImpl();
                    List<DataDto> dataAlarms = dataDao.getAlarms();

                    logger.info("retreive program alarms datatypes!");
                    request.getSession().setAttribute("dataAlarms", dataAlarms);

                    AlarmDao      alarmDao   = new AlarmDaoImpl();
                    List<AlarmDto> alarmNames = alarmDao.getAll();

                    logger.info("retreive relay names!");
                    request.getSession().setAttribute("alarmNames", alarmNames);
                    request.getRequestDispatcher("./assign-alarms.jsp").forward(request, response);

                    // request.getRequestDispatcher("./program-alarms.jsp").forward(request, response);
                } catch (SQLException ex) {
                    logger.info("Error occurs while retreive program alarms!");
                    request.getSession().setAttribute("message", "Error occurs while retreive program alarms!");
                    request.getRequestDispatcher("./all-programs.html").forward(request, response);
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
