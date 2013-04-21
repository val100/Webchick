
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.dto.ProgramSystemStateDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Title: IProgSysStateDao <br> Decription: <br> Copyright: Copyright (c) 2009 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.1 <br>
 */
public interface ProgSysStateDao {

    public void insert(ProgramSystemStateDto programSystemState) throws SQLException;

    public void update(ProgramSystemStateDto programSystemState) throws SQLException;

    public void remove(Long dataId, Integer number, Long programId) throws SQLException;

    public void insertSystemStates(Long programId, SortedMap<Long, Map<Integer, String>> systemStateMap)
            throws SQLException;

    public List<ProgramSystemStateDto> getAllProgramSystemStates(Long programId) throws SQLException;

    public List<ProgramSystemStateDto> getAllProgramSystemStates(Long programId, Long langId) throws SQLException;

    public List<ProgramSystemStateDto> getAllProgramSystemStates(Long programId, String[] text) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
