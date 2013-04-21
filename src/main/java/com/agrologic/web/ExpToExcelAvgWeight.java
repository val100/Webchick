
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.DataDao;
import com.agrologic.dao.FlockDao;
import com.agrologic.dao.impl.DataDaoImpl;
import com.agrologic.dao.impl.FlockDaoImpl;
import com.agrologic.dto.DataDto;
import com.agrologic.excel.DataForExcelCreator;
import com.agrologic.excel.WriteToExcel;
import com.agrologic.utils.FileDownloadUtil;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExpToExcelAvgWeight extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final Logger              logger           = Logger.getLogger(ExpToExcelAvgWeight.class);
    private String            outfile          = "c:/avgweight.xls";

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
                long                 flockId          = Long.parseLong(request.getParameter("flockId"));
                FlockDao            flockDao         = new FlockDaoImpl();
                Map<Integer, String> historyByGrowDay = flockDao.getAllHistoryByFlock(flockId);
                DataDao             dataDao          = new DataDaoImpl();
                List<List<String>>   histotyDataList  = new ArrayList<List<String>>();
                DataDto              data0            = dataDao.getById(Long.valueOf(800), Long.valueOf(1));

                histotyDataList.add(DataForExcelCreator.createDataList(historyByGrowDay.keySet()));

                DataDto data1 = dataDao.getById(Long.valueOf(2933), Long.valueOf(1));

                histotyDataList.add(DataForExcelCreator.createDataHistoryList(historyByGrowDay, data1));

                DataDto data2 = dataDao.getById(Long.valueOf(2934), Long.valueOf(1));

                histotyDataList.add(DataForExcelCreator.createDataHistoryList(historyByGrowDay, data2));

                DataDto data3 = dataDao.getById(Long.valueOf(2935), Long.valueOf(1));

                histotyDataList.add(DataForExcelCreator.createDataHistoryList(historyByGrowDay, data3));

                DataDto data4 = dataDao.getById(Long.valueOf(2936), Long.valueOf(1));

                histotyDataList.add(DataForExcelCreator.createDataHistoryList(historyByGrowDay, data4));

                List<String> tableTitles = new ArrayList<String>();

                tableTitles.add(data0.getLabel());
                tableTitles.add(data1.getLabel());
                tableTitles.add(data2.getLabel());
                tableTitles.add(data3.getLabel());
                tableTitles.add(data4.getLabel());

                WriteToExcel excel = new WriteToExcel();

                excel.setTitleList(tableTitles);
                excel.setCellDataList(histotyDataList);
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
