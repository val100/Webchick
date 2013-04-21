
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author JanL
 */
public class Log4JInitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Log4JInitServlet is initializing log4j");

        String         log4jLocation = config.getInitParameter("log4j-properties-location");
        ServletContext sc            = config.getServletContext();

        if (log4jLocation == null) {
            System.err.println(
                "*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        } else {
            String webAppPath = sc.getRealPath("/");
            String log4jProp  = webAppPath + log4jLocation;
            File   log4jFile  = new File(log4jProp);

            if (log4jFile.exists()) {
                System.out.println("Initializing log4j with: " + log4jProp);
                PropertyConfigurator.configure(log4jProp);
            } else {
                System.err.println("*** " + log4jProp
                                   + " file not found, so initializing log4j with BasicConfigurator");
                BasicConfigurator.configure();
            }
        }

        super.init(config);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
