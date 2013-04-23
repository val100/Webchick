
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.model.SystemStateDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Title: ISystemStateDao <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public interface SystemStateDao1 {
    public void insert(SystemStateDto systemState) throws SQLException;

    public void insertSystemStates(Long programId, SortedMap<Long, Map<Integer, String>> dataSystemStateMap)
            throws SQLException;

    public void update(SystemStateDto systemState) throws SQLException;

    public void delete(Long id) throws SQLException;

    public SystemStateDto getById(Long id) throws SQLException;

    public List<SystemStateDto> getAll() throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
