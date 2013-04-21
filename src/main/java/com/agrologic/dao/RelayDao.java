
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dto.RelayDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 * Title: RelayDao <br> Description: <br> Copyright: Copyright (c) 2009 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.1 <br>
 */
public interface RelayDao {
    public void insert(RelayDto relay) throws SQLException;

    public void insertTranslation(Long relayId, Long langId, String translation) throws SQLException;

    public void update(RelayDto relay) throws SQLException;

    public void remove(Long relayId) throws SQLException;

    public RelayDto getById(Long id) throws SQLException;

    public List<RelayDto> getAll() throws SQLException;

    public List<RelayDto> getAll(Long langId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
