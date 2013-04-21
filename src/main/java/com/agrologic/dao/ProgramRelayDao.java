
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dto.ProgramRelayDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

/**
 * Title: ProgramRelayDao <br>
 * Description: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public interface ProgramRelayDao {
    public void insert(ProgramRelayDto programRelay) throws SQLException;

    public void update(ProgramRelayDto programRelay) throws SQLException;

    public void remove(Long dataId, Integer bitNumber, Long programId) throws SQLException;

    public void insertRelays(Long programId, Map<Long, Map<Integer, String>> dataRelayMap) throws SQLException;

    public List<Long> getRelayNumberTypes(Long programId) throws SQLException;

    public List<ProgramRelayDto> getAllProgramRelays(Long programId) throws SQLException;

    public List<ProgramRelayDto> getAllProgramRelays(Long programId, Long langId) throws SQLException;

    public List<ProgramRelayDto> getAllProgramRelays(Long programId, String[] text) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
