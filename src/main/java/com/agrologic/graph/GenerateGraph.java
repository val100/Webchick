
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.graph;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.ControllerDao;
import com.agrologic.dao.DataDao;
import com.agrologic.dao.impl.ControllerDaoImpl;
import com.agrologic.dao.impl.DataDaoImpl;
import com.agrologic.dto.DataDto;
import com.agrologic.graph.daily.Graph24FWI;
import com.agrologic.graph.daily.Graph24IOH;
import com.agrologic.graph.daily.GraphType;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
public class GenerateGraph {
    public static String generateChartTempHum(Long controllerId, HttpSession session, PrintWriter pw, Locale locale) {
        String filenameth = null;

        try {
            ControllerDao controllerDao = new ControllerDaoImpl();
            String         values        = controllerDao.getControllerGraph(controllerId);
            DataDao       dataDao       = new DataDaoImpl();
            DataDto        setClock      = dataDao.getSetClockByController(controllerId);

            if (values == null) {
                throw new Exception("No data available in database.");
            } else {
                Graph24IOH graph = null;

                if (setClock.getValue() == null) {
                    graph = new Graph24IOH(GraphType.IN_OUT_TEMP_HUMID, values, Long.valueOf("0"), locale);
                } else {
                    graph = new Graph24IOH(GraphType.IN_OUT_TEMP_HUMID, values, setClock.getValue(), locale);
                }

                // Write the chart image to the temporary directory
                ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

                filenameth = ServletUtilities.saveChartAsPNG(graph.createChart(), 800, 600, info, session);

                // Write the image map to the PrintWriter
                ChartUtilities.writeImageMap(pw, filenameth, info, false);
                session.setAttribute("filenameth", filenameth);
                pw.flush();
            }
        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filenameth = "public_error_500x300.png";
        }

        return filenameth;
    }

    public static String generateChartWaterFeedTemp(Long controllerId, HttpSession session, PrintWriter pw,
            Locale locale)
            throws IOException {
        String filenamewft = null;

        try {
            ControllerDao controllerDao = new ControllerDaoImpl();
            String         values        = controllerDao.getControllerGraph(controllerId);
            DataDao       dataDao       = new DataDaoImpl();
            DataDto        setClock      = dataDao.getSetClockByController(controllerId);

            if (values == null) {
                throw new Exception("No data available in database.");
            } else {
                Graph24FWI graph = null;

                if (setClock.getValue() == null) {
                    graph = new Graph24FWI(GraphType.IN_OUT_TEMP_HUMID, values, Long.valueOf("0"), locale);
                } else {
                    graph = new Graph24FWI(GraphType.IN_OUT_TEMP_HUMID, values, setClock.getValue(), locale);
                }

                // Write the chart image to the temporary directory
                ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

                filenamewft = ServletUtilities.saveChartAsPNG(graph.createChart(), 800, 600, info, session);

                // Write the image map to the PrintWriter
                ChartUtilities.writeImageMap(pw, filenamewft, info, false);
                session.setAttribute("filenamewct", filenamewft);
                pw.flush();
            }
        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filenamewft = "public_error_500x300.png";
        }

        return filenamewft;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
