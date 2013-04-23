
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.model.FeedTypeDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author JanL
 */
public interface FeedTypeDao {
    public void insert(FeedTypeDto gaz) throws SQLException;

    public void remove(Long id) throws SQLException;

    public FeedTypeDto getById(Long id) throws SQLException;

    public List<FeedTypeDto> getAllByCellinkId(Long cellinkId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
