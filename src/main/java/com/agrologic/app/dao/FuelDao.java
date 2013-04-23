
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.app.model.FuelDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author JanL
 */
public interface FuelDao {

    public void insert(FuelDto fuel) throws SQLException;

    public void remove(Long id) throws SQLException;

    public FuelDto getById(Long id) throws SQLException;

    public List<FuelDto> getAllByFlockId(Long flockId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
