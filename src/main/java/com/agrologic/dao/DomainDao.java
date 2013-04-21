
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.dao;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public interface DomainDao {
    String getDomain() throws SQLException;

    String getLogoPath(String domain) throws SQLException;

    String getCompany(String domain) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
