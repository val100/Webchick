
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.dto;

//~--- JDK imports ------------------------------------------------------------
import java.util.HashMap;
import java.util.Map;

/**
 * Title: DataFormat <br> Description: <br> Copyright: Copyright (c) 2009 
 * <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.1 <br>
 */
public class DataFormat {

    public static final int DEC_0              = 0;
    public static final int DEC_1              = 1;
    public static final int DEC_2              = 2;
    public static final int DEC_3              = 3;
    public static final int HUMIDITY           = 4;
    public static final int TIME               = 5;
    public static final int TIME_SEC           = 6;
    public static final int DATE               = 7;
    public static final int DEC_4              = 8;
    public static final int DEC_5              = 10;
    public static final int DEC_11             = 11;
    public static final String DOT_DELIMETER   = ".";
    public static final String EMPTY_DELIMETER = "";
    public static final String DATE_DELIMETER  = "/";
    public static final String TIME_DELIMETER  = ":";
    /**
     * Map of Strings to GraphType objects.
     */
    private static final Map formatMap = new HashMap();

    // initialize a String -> CellinkState map
    static {
        formatMap.put(DEC_0, "xxxx");
        formatMap.put(DEC_1, "xx.x");
        formatMap.put(DEC_2, "xx.xx");
        formatMap.put(DEC_3, "xx.xxx");
        formatMap.put(HUMIDITY, "xxx");
        formatMap.put(TIME, "hh:mm");
        formatMap.put(TIME_SEC, "hh:mm:ss");
        formatMap.put(DATE, "dd/mm/yyyy");
        formatMap.put(DEC_4, "xxxxxx");
        formatMap.put(DEC_5, "xxxxx");
        formatMap.put(DEC_11, "xxx.x");
    }
    private int currentFormat;

    public DataFormat(int format) {
        this.currentFormat = format;
    }

    public int getFormat() {
        return currentFormat;
    }

    public void setFormat(int format) {
        this.currentFormat = format;
    }

    /**
     * Convert a Format to a String object.
     *
     * @param format The Format
     * @return The String or null
     */
    public String formatToPattern(int format) {
        return (String) formatMap.get(format);
    }

    /**
     * Convert a DataFormat to a String object.
     *
     * @param format
     * @return The String or null
     */
    public static String formatToStringValue(int format, long value) {
        try {
            StringBuilder sb = new StringBuilder();
            String delim = EMPTY_DELIMETER;

            sb.append(Long.toString(value));

            int position = 0;
            int len = sb.length();

            switch (format) {
                case DEC_0:

                    // do nothing           //xxxx
                    break;

                case DEC_1:
                    delim = DOT_DELIMETER;     // xxx.x
                    position = (sb.length() >= 2)
                            ? sb.length() - DEC_1
                            : -1;

                    if (len < DEC_1 + 1) {
                        len = DEC_1 - len + 1;

                        while (len > 0) {
                            sb.insert(0, "0");
                            len--;
                        }
                    }

                    position = sb.length() - DEC_1;
                    sb.insert(position, delim);

                    break;

                case DEC_2:
                    delim = DOT_DELIMETER;        // xx.xx

                    if (len < DEC_2 + 1) {
                        len = DEC_2 - len + 1;

                        while (len > 0) {
                            sb.insert(0, "0");
                            len--;
                        }
                    }

                    position = sb.length() - DEC_2;
                    sb.insert(position, delim);

                    break;

                case DEC_3:
                    delim = DOT_DELIMETER;        // xx.xxx

                    if (len < DEC_3 + 1) {
                        len = DEC_3 - len + 1;

                        while (len > 0) {
                            sb.insert(0, "0");
                            len--;
                        }
                    }

                    position = sb.length() - DEC_3;
                    sb.insert(position, delim);

                    break;

                case HUMIDITY:                   // xxx
                    break;

                case TIME:
                    delim = TIME_DELIMETER;       // hh:mm
                    while (len < 4) {
                        sb.insert(0, "0");
                        len++;
                    }

                    position = sb.length() - 2;
                    sb.insert(position, delim);

                    break;

                case TIME_SEC:
                    delim = TIME_DELIMETER;    // hh:mm:ss
                    position = sb.length() - 2;
                    sb.insert(position, delim).insert(position - 2, delim);

                    break;

                case DATE:
                    int vl = (int) value;
                    int days = ((vl >> 28) & 0xF) * 10 + ((vl >> 24) & 0xF);
                    int months = ((vl >> 20) & 0xF) * 10 + ((vl >> 16) & 0xF);
                    int years = ((vl >> 12) & 0xF) * 1000 + ((vl >> 8) & 0xF) * 100 + ((vl >> 4) & 0xF) * 10 + (vl & 0xF);

                    sb = new StringBuilder();
                    sb.append(days).append(DATE_DELIMETER).append(months).append(DATE_DELIMETER).append(years);

                    break;

                case DEC_4:

                    // XXXXXX
                    break;

                case DEC_5:
                    break;

                case DEC_11:
                    int val = (int) value;
                    int b_d = (val / 1000) * 100;
                        b_d = b_d + ((val % 1000) / 100) * 10;
                        b_d = b_d + (((val % 1000) % 100) / 10);
                    int a_d = (((val % 1000) % 100) % 10);
                        sb = new StringBuilder();
                        sb.append(b_d).append(DOT_DELIMETER).append(a_d);

                    break;
                default:
                    break;
            }

            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Returns a string representation of the current <tt>DataFormat</tt>.
     */
    public String pattern() {
        return formatToPattern(currentFormat);
    }

    /**
     * Convert value to string representation of the current <tt>DataFormat</tt>.
     *
     * @param value the value
     * @return a string representation of the current <tt>DataFormat</tt>.
     */
    public String toStringValue(long value) {
        return formatToStringValue(currentFormat, value);
    }

    @Override
    public String toString() {
        return pattern();
    }

    public static long convertToTimeFormat(long value) {
        long newValue;
        String hexString = Long.toHexString(value);

        if (!isLetter(hexString)) {
            newValue = Long.parseLong(hexString);
        } else {
            newValue = value;
        }

        return newValue;
    }

    public static boolean isLetter(String s) {
        for (Character c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        try {
            DataFormat df = new DataFormat(DataFormat.DEC_0);

            System.out.println(df.pattern());
            System.out.println(df.toStringValue(255));
            df.setFormat(DataFormat.DEC_1);
            System.out.println(df.pattern());
            System.out.println(df.toStringValue(255));
            df.setFormat(DataFormat.DEC_2);
            System.out.println(df.pattern());
            System.out.println(df.toStringValue(255));
            df.setFormat(DataFormat.DEC_3);
            System.out.println(df.pattern());
            System.out.println(df.toStringValue(255));
            df.setFormat(DataFormat.DATE);
            System.out.println(df.pattern());
            System.out.println(df.toStringValue(3112));
            df.setFormat(DataFormat.TIME);
            System.out.println(df.pattern());
            System.out.println(df.toStringValue(1212));
            df.setFormat(DataFormat.DEC_11);
            System.out.println(df.pattern());
            System.out.println(df.toStringValue(0));

        } catch (Exception ex) {
        }

    }
}


//~ Formatted by Jindent --- http://www.jindent.com
