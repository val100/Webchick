
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.DataDao;
import com.agrologic.app.dao.ProgramDao;
import com.agrologic.app.dao.ScreenDao;
import com.agrologic.app.dao.TableDao;
import com.agrologic.app.dao.impl.DataDaoImpl;
import com.agrologic.app.dao.impl.ProgramDaoImpl;
import com.agrologic.app.dao.impl.ScreenDaoImpl;
import com.agrologic.app.dao.impl.TableDaoImpl;
import com.agrologic.app.model.DataDto;
import com.agrologic.app.model.ProgramDto;
import com.agrologic.app.model.ScreenDto;
import com.agrologic.app.model.TableDto;
import com.agrologic.app.utils.DateLocal;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

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
public class AddTableDataFormServlet extends HttpServlet implements Serializable {
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
        final Logger logger = Logger.getLogger(AddProgramServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                Long    programId        = Long.parseLong(request.getParameter("programId"));
                Long    screenId         = Long.parseLong(request.getParameter("screenId"));
                Long    tableId          = Long.parseLong(request.getParameter("tableId"));
                Long    dataType         = Long.parseLong(request.getParameter("Ndatatype"));
                boolean isSpecial        = false;
                String  specialDataLabel = request.getParameter("Nlabel");
                Long    langId           = null;

                if ((specialDataLabel != null) &&!specialDataLabel.equals("")) {
                    langId    = Long.parseLong(request.getParameter("langListBox"));
                    isSpecial = true;
                }

                Long dataId = dataType;
                if ((dataType & 0xC000) != 0xC000) {
                    dataId = (dataType & 0xFFF); // remove type to get an index 4096&0xFFF -> 0
                } else {
                    dataId = (dataType & 0xFFFF);
                }


                String  display  = request.getParameter("display");
                Integer position = Integer.parseInt(request.getParameter("position"));

                try {
                    TableDao tableDao = new TableDaoImpl();
                    TableDto  table    = tableDao.getById(programId, screenId, tableId);
                    DataDao  dataDao  = new DataDaoImpl();

                    dataDao.insertDataToTable(programId, screenId, table.getId(), dataId, display, position);

                    if (isSpecial) {
                        dataDao.insertSpecialData(programId, dataId, langId, specialDataLabel);
                        logger.info("New special table data successfully added !");
                        request.getSession().setAttribute("message", "special table data successfully added !");
                        request.getSession().setAttribute("error", false);
                    } else {
                        logger.info("New table data successfully added !");
                        request.getSession().setAttribute("message", "table data successfully added !");
                        request.getSession().setAttribute("error", false);
                    }

                    ProgramDao programDao = new ProgramDaoImpl();
                    ProgramDto  program    = programDao.getById(programId);

                    program.setModifiedDate(DateLocal.currentDate());
                    programDao.update(program);

                    ScreenDao     screenDao = new ScreenDaoImpl();
                    ScreenDto      screen    = screenDao.getById(programId, screenId, langId);
                    List<TableDto> tables    = new ArrayList<TableDto>();

                    tables.add(table);
                    screen.setTables(tables);

                    List<DataDto> dataList = dataDao.getTableDataList(screen.getProgramId(), screen.getId(),
                                                 table.getId(), null);

                    table.setDataList(dataList);
                    request.getSession().setAttribute("screen", screen);
                } catch (SQLException e) {

                    // error page
                    logger.error("Error occurs while adding table data !", e);
                    request.getSession().setAttribute("message", "Error occurs while adding table data !");
                    request.getSession().setAttribute("error", true);
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
