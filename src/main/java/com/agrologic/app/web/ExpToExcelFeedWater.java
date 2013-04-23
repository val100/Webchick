
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.DataDao;
import com.agrologic.app.dao.FlockDao;
import com.agrologic.app.dao.impl.DataDaoImpl;
import com.agrologic.app.dao.impl.FlockDaoImpl;
import com.agrologic.app.model.DataDto;
import com.agrologic.app.excel.DataForExcelCreator;
import com.agrologic.app.excel.WriteToExcel;
import com.agrologic.app.utils.FileDownloadUtil;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExpToExcelFeedWater extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final Logger              logger           = Logger.getLogger(ExpToExcelFeedWater.class);
    private String            outfile          = "c:/feedwater.xls";

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
        response.setContentType("text/html;charset=UTF-8");

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                long                 flockId             = Long.parseLong(request.getParameter("flockId"));
                FlockDao            flockDao            = new FlockDaoImpl();
                Map<Integer, String> historyByGrowDay    = flockDao.getAllHistoryByFlock(flockId);
                List<List<String>>   allHistDataForExcel = new ArrayList<List<String>>();
                DataDao             dataDao             = new DataDaoImpl();
                long                 langId              = 1;
                DataDto              data0               = dataDao.getById(Long.valueOf(800), langId);

                allHistDataForExcel.add(DataForExcelCreator.createDataList(historyByGrowDay.keySet()));

                DataDto data1 = dataDao.getById(Long.valueOf(1301), langId);

                allHistDataForExcel.add(DataForExcelCreator.createDataHistoryList(historyByGrowDay, data1));

                DataDto data2 = dataDao.getById(Long.valueOf(1302), langId);

                allHistDataForExcel.add(DataForExcelCreator.createDataHistoryList(historyByGrowDay, data2));

                List<String> tableTitles = new ArrayList<String>();

                tableTitles.add(data0.getLabel());
                tableTitles.add(data1.getLabel());
                tableTitles.add(data2.getLabel());

                WriteToExcel excel = new WriteToExcel();

                excel.setTitleList(tableTitles);
                excel.setCellDataList(allHistDataForExcel);
                excel.setOutputFile(outfile);
                excel.write();
                FileDownloadUtil.doDownload(response, outfile, "xls");
            }
        } catch (Exception e) {
            logger.error("Unknown error. ", e);
        } finally {
            response.getOutputStream().close();
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
