

package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.app.model.ControllerDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 * Title: CtrlTypeDB - Encapsulate all SQL queries to database that are related
 * to controllers <br> Description: Contains 3 types of SQL methods:<ul>
 * <li>regular jdbc statements</li> <li>prepared statements<br></li></ul>
 * Copyright: Copyright (c) 2008 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public interface ControllerDao {

    public void insert(ControllerDto controller) throws SQLException;

    public void update(ControllerDto controller) throws SQLException;

    public void remove(Long id) throws SQLException;

    public void sendNewDataValueToController(Long controllerId, Long dataId, Long value) throws SQLException;

    public void saveNewDataValueOnController(Long controllerId, Long dataId, Long value) throws SQLException;

    public void removeControllerData(Long controllerId) throws SQLException;

    public String getControllerGraph(Long controllerId) throws SQLException;

    public boolean isDataReady(Long userId) throws SQLException;

    public boolean isControllerDataReady(Long controllerId) throws SQLException;

    public ControllerDto getById(Long id) throws SQLException;

    public List<String> getControllerNames() throws SQLException;

    public List<ControllerDto> getAll() throws SQLException;

    public List<ControllerDto> getAllByCellinkId(Long cellinkId) throws SQLException;

    public List<ControllerDto> getAllActiveByCellinkId(Long cellinkId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
