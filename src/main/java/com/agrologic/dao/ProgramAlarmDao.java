
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dto.ProgramAlarmDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

/**
 * Title: IProgramAlarmDao <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public interface ProgramAlarmDao {
    public void insert(ProgramAlarmDto programAlarm) throws SQLException;

    public void update(ProgramAlarmDto programAlarm) throws SQLException;

    public void remove(Long dataId, Integer digitNumber, Long programId) throws SQLException;

    public void insertAlarms(Long programId, Map<Long, Map<Integer, String>> alarmMap) throws SQLException;

    public List<ProgramAlarmDto> getAllProgramAlarms(Long programId) throws SQLException;

    public List<ProgramAlarmDto> getAllProgramAlarms(Long programId, Long langId) throws SQLException;

    public List<ProgramAlarmDto> getAllProgramAlarms(Long programId, String[] text) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
