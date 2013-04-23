
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.graph.daily;

//~--- non-JDK imports --------------------------------------------------------

import org.jfree.chart.JFreeChart;

//~--- JDK imports ------------------------------------------------------------

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Title: AbstractGraph <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public abstract class AbstractGraph implements Graph {
    public static final int       DAY_HOURS      = 24;
    public static final int       FEED_INDEX     = 72;
    public static final int       HUMIDITY_INDEX = 48;
    public static final int       IN_TEMP_INDEX  = 0;
    public static final int       OUT_TEMP_INDEX = 24;
    public static final int       WATER_INDEX    = 96;
    protected JFreeChart          chart;
    protected Long                currentTime;
    protected String[]            datasetString;
    protected Map<String, String> dictinary;
    protected boolean             empty;
    protected Locale              locale;
    protected int                 maxY;
    protected int                 minY;
    protected GraphType           type;

    public AbstractGraph(GraphType type, String values) {
        this.type          = type;
        this.datasetString = values.split(" ", -1);
        this.minY          = Integer.MAX_VALUE;
        this.maxY          = Integer.MIN_VALUE;
        setEmpty();
    }

    protected void initLaguage() {
        dictinary = new HashMap<String, String>();

        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        for (Enumeration<String> e = bundle.getKeys(); e.hasMoreElements(); ) {
            String key = e.nextElement();

            if (key.startsWith("graph")) {
                String value = bundle.getString(key);

                dictinary.put(key, value);
            }
        }
    }

    public GraphType getType() {
        return type;
    }

    protected void resetMinMaxY() {
        this.minY = Integer.MAX_VALUE;
        this.maxY = Integer.MIN_VALUE;
    }

    private void setEmpty() {
        if (datasetString.length > 0) {
            empty = false;
        } else {
            empty = true;
        }
    }

    protected boolean isEmpty() {
        return empty;
    }

//  protected abstract JFreeChart createChart();
//  protected abstract JFreeChart createChart(XYDataset dataset);
}


//~ Formatted by Jindent --- http://www.jindent.com
