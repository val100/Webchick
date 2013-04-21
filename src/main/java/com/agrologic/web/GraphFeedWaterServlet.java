
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
import com.agrologic.graph.DataGraphCreator;
import com.agrologic.graph.daily.Graph;
import com.agrologic.graph.daily.Graph24Empty;
import com.agrologic.graph.daily.GraphType;
import com.agrologic.graph.history.HistoryGraph;

import org.apache.log4j.Logger;

import org.jfree.chart.ChartUtilities;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GraphFeedWaterServlet extends HttpServlet {

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
        final Logger logger = Logger.getLogger(GraphFeedWaterServlet.class);

        response.setContentType("text/html;charset=UTF-8");

        OutputStream out = response.getOutputStream();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                long          flockId = Long.parseLong(request.getParameter("flockId"));
                int           fromDay = -1;
                int           toDay   = -1;
                StringBuilder range   = new StringBuilder();

                try {
                    fromDay = Integer.parseInt(request.getParameter("fromDay"));
                    toDay   = Integer.parseInt(request.getParameter("toDay"));

                    if ((fromDay != -1) && (toDay != -1)) {
                        range.append("( From ").append(fromDay).append(" to ").append(toDay).append(" grow day .)");
                    }
                } catch (Exception ex) {
                    fromDay = -1;
                    toDay   = -1;
                }

                try {
                    FlockDao                    flockDao         = new FlockDaoImpl();
                    Map<Integer, String>        historyByGrowDay = flockDao.getAllHistoryByFlock(flockId, fromDay, toDay);
                    List<Map<Integer, DataDto>> dataHistroryList = new ArrayList<Map<Integer, DataDto>>();
                    List<String>                axisTitles       = new ArrayList<String>();
                    DataDao                     dataDao          = new DataDaoImpl();
                    DataDto                     data1            = dataDao.getById(Long.valueOf(1301));

                    axisTitles.add(data1.getLabel());
                    dataHistroryList.add(DataGraphCreator.createHistoryDataByGrowDay(historyByGrowDay, data1));

                    String       title          = "Feed And Water Consumption";
                    String       xAxisTitle     = "Grow Day[Day]";
                    String       yAxisTitle     = "Feed[KG]";
                    HistoryGraph waterFeedGraph = new HistoryGraph();

                    waterFeedGraph.setDataHistoryList(dataHistroryList);
                    waterFeedGraph.createChart(title, xAxisTitle, yAxisTitle);

                    DataDto data2 = dataDao.getById(Long.valueOf(1302));

                    axisTitles.add(data2.getLabel());
                    dataHistroryList.clear();
                    dataHistroryList.add(DataGraphCreator.createHistoryDataByGrowDay(historyByGrowDay, data2));
                    waterFeedGraph.createAndAddSeriesCollection(dataHistroryList, "Water[Liter]");
                    ChartUtilities.writeChartAsPNG(out, waterFeedGraph.getChart(), 800, 600);
                    out.flush();
                    out.close();
                } catch (Exception ex) {
                    logger.error("Unknown error. ", ex);

                    Graph24Empty graph = new Graph24Empty(GraphType.BLANK, "");

                    ChartUtilities.writeChartAsPNG(out, graph.getChart(), 600, 300);
                    out.flush();
                    out.close();
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
