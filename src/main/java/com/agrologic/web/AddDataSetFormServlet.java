
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.DataDao;
import com.agrologic.dao.impl.DataDaoImpl;
import com.agrologic.dto.DataDto;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class AddDataSetFormServlet extends HttpServlet {
    DataDao dataDao;
    long     endDataId;
    int      pos;
    long     programid;
    long     screenid;
    String   show;
    long     startDataId;
    long     tableid;

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

        PrintWriter out = response.getWriter();

        try {
            programid   = Long.parseLong(request.getParameter("programId"));
            screenid    = Long.parseLong(request.getParameter("screenId"));
            tableid     = Long.parseLong(request.getParameter("tableId"));
            startDataId = Long.parseLong(request.getParameter("startdataId"));
            endDataId   = Long.parseLong(request.getParameter("enddataId"));
            show        = request.getParameter("show");
            pos         = Integer.parseInt(request.getParameter("startpos"));
            dataDao     = new DataDaoImpl();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int type = (int) startDataId;        // type of value (like 4096)

                    if ((type & 0xC000) != 0xC000) {
                        startDataId = (type & 0xFFF);    // remove type to get an index 4096&0xFFF -> 0
                    } else {
                        startDataId = (type & 0xFFFF);
                    }

                    type = (int) endDataId;              // type of value (like 4096)

                    if ((type & 0xC000) != 0xC000) {
                        endDataId = (type & 0xFFF);      // remove type to get an index 4096&0xFFF -> 0
                    } else {
                        endDataId = (type & 0xFFFF);
                    }

                    DataDto data = null;

                    try {
                        data = dataDao.getById(startDataId);
                    } catch (SQLException ex) {
                        data = new DataDto();
                        data.setFormat(0);
                    }

                    if (startDataId > endDataId) {
                        return;
                    }

                    while (startDataId <= endDataId) {
                        try {
                            dataDao.insertDataToTable(programid, screenid, tableid, startDataId, show, pos);
                        } catch (SQLException ex) {

//                          jTextArea2.append("Error adding dataids\n");
                            for (Throwable e : ex) {
                                if (e instanceof SQLException) {
                                    e.printStackTrace(System.err);

                                    Throwable t = ex.getCause();

                                    while (t != null) {

//                                      jTextArea2.append("Cause: " + t + "\n");
                                        t = t.getCause();
                                    }
                                }
                            }
                        }

                        if (data.isLong()) {
                            startDataId++;
                        }

                        startDataId++;
                        pos++;

//                      jTextArea2.append(".");
                    }

//                  jTextArea2.append("\n");
//                  jTextArea2.append("All data are added\n");
                }
            });

            thread.start();
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
