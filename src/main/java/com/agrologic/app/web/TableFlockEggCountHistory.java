/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.web;

import com.agrologic.app.dao.DataDao;
import com.agrologic.app.dao.FlockDao;
import com.agrologic.app.dao.impl.DataDaoImpl;
import com.agrologic.app.dao.impl.FlockDaoImpl;
import com.agrologic.app.model.DataDto;
import com.agrologic.app.model.DataFormat;
import com.agrologic.app.table.TableOfHistoryCreator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class TableFlockEggCountHistory extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final List<Long> historyDataIdList = new ArrayList<Long>();
    private static final Logger logger = Logger.getLogger(TableFlockEggCountHistory.class);

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * Logger for this class and subclasses
         */
        final Logger logger = Logger.getLogger(TableFlockHistory.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                long flockId = Long.parseLong(request.getParameter("flockId"));
                int fromDay = -1;
                int toDay = -1;
                StringBuilder range = new StringBuilder();

                try {
                    fromDay = Integer.parseInt(request.getParameter("fromDay"));
                    toDay = Integer.parseInt(request.getParameter("toDay"));

                    if ((fromDay != -1) && (toDay != -1)) {
                        range.append("( From ").append(fromDay).append(" to ").append(toDay).append(" grow day .)");
                    }
                } catch (Exception ex) {
                    fromDay = -1;
                    toDay = -1;
                }

                try {
                    FlockDao flockDao = new FlockDaoImpl();
                    Map<Integer, String> historyByGrowDay = flockDao.getAllHistoryByFlock(flockId, fromDay, toDay);
                    List<Map<Integer, DataDto>> historyDataList = new ArrayList<Map<Integer, DataDto>>();
                    List<String> columnTitles = new ArrayList<String>();

                    historyDataList = createHistoryByGrowDay(columnTitles, historyByGrowDay);
                    out.println("<p>");
                    out.println("<table class=table-list cellpadding=1 cellspacing=1 border=1 "
                            + "style=behavior:url(tablehl.htc) url(sort.htc);>");
                    out.println("<tr>");
                    for (String title : columnTitles) {
                        out.println("<th style=\"font-size: small\">" + title + "</th>");
                    }
                    out.println("</tr>");

                    Iterator<Integer> growdayIter = historyByGrowDay.keySet().iterator();
                    while (growdayIter.hasNext()) {
                        Integer growDay = growdayIter.next();
                        Iterator<Map<Integer, DataDto>> historyIter = historyDataList.iterator();
                        out.println("<tr>");
                        while (historyIter.hasNext()) {
                            try {
                                Map<Integer, DataDto> interestData = historyIter.next();
                                DataDto data = interestData.get(growDay);
                                if (data.getId() == 800) {
                                    out.println("<td align=center>" + growDay + "</td>");
                                } else {
                                    out.println("<td align=center>" + valueByType(data) + "</td>");
                                }
                            } catch (Exception e) {
                                out.println("<td align=center>&nbsp;</td>");
                                logger.error(e);
                            }
                        }
                        out.println("</tr>");
                    }
                    out.println("</table>");
                } catch (Exception ex) {
                    logger.error("Unknown error. ", ex);
                }
            }
        } catch (Exception e) {
            logger.error("Unknown error. ", e);
        }
    }

    /**
     * Create list of history data by grow day .
     *
     * @param columnTitles the list with column titles
     * @param historyByGrowDay all history by grow day map.
     * @return historyDataForTable the list of history data by grow day.
     * @throws UnsupportedOperationException
     */
    private static List<Map<Integer, DataDto>> createHistoryByGrowDay(List<String> columnTitles,
            Map<Integer, String> historyByGrowDay)
            throws UnsupportedOperationException {
        List<Map<Integer, DataDto>> historyDataForTable = new ArrayList<Map<Integer, DataDto>>();
        Map<Integer, DataDto> tempList = new TreeMap<Integer, DataDto>();
        List<Long> choosedList = new ArrayList<Long>();
        choosedList.add((long) 1465);
        choosedList.add((long) 1467);
        choosedList.add((long) 1469);
        choosedList.add((long) 1471);
        choosedList.add((long) 1473);
        choosedList.add((long) 1475);
        choosedList.add((long) 2600);
        choosedList.add((long) 2615);
        choosedList.add((long) 2630);
        choosedList.add((long) 2645);
        choosedList.add((long) 2660);
        choosedList.add((long) 2675);

        try {
            DataDao dataDao = new DataDaoImpl();
            DataDto data = dataDao.getById(Long.valueOf(800), (long) 1);
            columnTitles.add(data.getLabel());
            tempList = TableOfHistoryCreator.createGrowDayList(historyByGrowDay, data);
            historyDataForTable.add(tempList);
            for (Long dataId : choosedList) {
                data = dataDao.getById(dataId, (long) 1);
                tempList = TableOfHistoryCreator.createEggCountHistDataByGrowDay(historyByGrowDay, data);
                if (!tempList.isEmpty()) {
                    columnTitles.add(data.getLabel());
                    historyDataForTable.add(tempList);
                }
            }
        } catch (SQLException e) {

        }
        return historyDataForTable;
    }

    /**
     * Return formated data value string . 
     * If data type is Time than return time in minutes .
     *
     * @param data the data object
     * @return formated value string
     */
    private String valueByType(DataDto data) {
        Long value = data.getValue();

        if (DataFormat.TIME == data.getFormat()) {
            long h = value / 100;
            long m = value % 100;
            long t = h * 60 + m;

            return String.valueOf(t);
        }
        return data.getFormatedValue();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

