
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.dto.TransactionDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author JanL
 */
public interface TransactionDao {

    public void insert(TransactionDto transaction) throws SQLException;

    public void remove(Long id) throws SQLException;

    public TransactionDto getById(Long id) throws SQLException;

    public List<TransactionDto> getAll() throws SQLException;

    public List<TransactionDto> getAllByFlockId(Long flockId) throws SQLException;

    public String getCurrencyById(Long id) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
