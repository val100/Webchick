
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.graph.daily;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Title: GraphType <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public class GraphType {
    public static final int BLANK_GRAPH             = 0;
    public static final int IN_FEED_WATER_GRAPH     = 2;
    public static final int IN_OUT_TEMP_HUMID_GRAPH = 1;

    /** Map of Strings to GraphType objects. */
    private static final Map graphTypeMap = new HashMap();

    /** The in/out temperature and humidity graph. */
    public static final GraphType IN_OUT_TEMP_HUMID = new GraphType(IN_OUT_TEMP_HUMID_GRAPH);

    /** The in temperature water and consumption graph */
    public static final GraphType IN_FEED_WATER = new GraphType(IN_FEED_WATER_GRAPH);

    /** The in temperature water and consumption graph */
    public static final GraphType BLANK = new GraphType(BLANK_GRAPH);

    // initialize a String -> GraphType map
    static {
        graphTypeMap.put(BLANK_GRAPH, "Blank Graph (24 hours)");
        graphTypeMap.put(IN_OUT_TEMP_HUMID_GRAPH, "In Temperature Water and Consumption Graph (24 hours)");
        graphTypeMap.put(IN_FEED_WATER_GRAPH, "In Temperature Water and Consumption Graph (24 hours)");
    }

    private int value;

    public GraphType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Convert a GraphType to a String object.
     * @param type The GraphType
     * @return The String or null
     */
    public static String typeToString(int type) {
        return (String) graphTypeMap.get(type);
    }

    /**
     * Convert a GraphType to a String object.
     * @param type The GraphType
     * @return The String or null
     */
    public static String typeToString(GraphType type) {
        Iterator keys = graphTypeMap.keySet().iterator();

        while (keys.hasNext()) {
            String key = (String) keys.next();

            if (((GraphType) graphTypeMap.get(key)).equals(type)) {
                return key;
            }
        }

        return null;
    }

    /**
     * Convert a String to a GraphType object.
     * @param str The String
     * @return The GraphType or null
     */
    public static GraphType stringToState(String str) {
        return (GraphType) graphTypeMap.get(str);
    }

    /**
     * Convert a int to a GraphType object.
     * @param str The String
     * @return The GraphType or null
     */
    public static GraphType intToGraphType(int i) {
        if (i == IN_OUT_TEMP_HUMID_GRAPH) {
            return IN_OUT_TEMP_HUMID;
        } else if (i == IN_FEED_WATER_GRAPH) {
            return IN_FEED_WATER;
        } else {
            return BLANK;
        }
    }

    public static Map listState() {
        return graphTypeMap;
    }

    public static GraphType getGraphType(int type) {
        return intToGraphType(type);
    }

    @Override
    public String toString() {
        return typeToString(value);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
