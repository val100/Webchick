
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.app.model.LaborDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author JanL
 */
public interface LaborDao {

    public void insert(LaborDto labor) throws SQLException;

    public void remove(Long id) throws SQLException;

    public LaborDto getById(Long id) throws SQLException;

    public List<LaborDto> getAllByFlockId(Long flockId) throws SQLException;

    public String getCurrencyById(Long id) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
