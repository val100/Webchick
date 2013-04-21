
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.AlarmDao;
import com.agrologic.dao.impl.AlarmDaoImpl;
import com.agrologic.dto.AlarmDto;

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
public class EditAlarmFormServlet extends HttpServlet {

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

        final Logger logger = Logger.getLogger(AddAlarmFormServlet.class);
        PrintWriter  out    = response.getWriter();

        try {
            Long      alarmId       = Long.parseLong(request.getParameter("NalarmId"));
            String    alarmText     = request.getParameter("Ntext");
            Long      translateLang = Long.parseLong(request.getParameter("translateLang"));
            AlarmDao alarmDao      = new AlarmDaoImpl();

            try {
                AlarmDto alarm = alarmDao.getById(alarmId);

                alarm.setText(alarmText);
                alarmDao.update(alarm);
                logger.info("Alarm " + alarm.getText() + "  successfully updated");
                alarmDao.insertTranslation(alarm.getId(), translateLang, alarm.getText());
                request.getSession().setAttribute("message",
                                                  "Alarm <b style=\"color:gray\"> " + alarm.getText()
                                                  + " </b> successfully  updated");
                request.getSession().setAttribute("error", false);
            } catch (SQLException ex) {
                logger.error("Error occurs during editiing alarm ", ex);
                request.getSession().setAttribute("message", "Error occurs during editing alarm ");
                request.getSession().setAttribute("error", true);
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
