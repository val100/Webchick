
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dto.SystemStateDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 * Title: ISystemStateDao <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public interface SystemStateDao {
    public void insert(SystemStateDto systemState) throws SQLException;

    public void update(SystemStateDto systemState) throws SQLException;

    public void remove(Long id) throws SQLException;

    public SystemStateDto getById(Long id) throws SQLException;

    public List<SystemStateDto> getAll() throws SQLException;

    public void insertTranslation(Long screenId, Long langId, String translation) throws SQLException;

    public List<SystemStateDto> getAll(Long langId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
