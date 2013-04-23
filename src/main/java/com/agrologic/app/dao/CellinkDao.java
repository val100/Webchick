
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.app.model.CellinkDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 * Title: FarmDB - Encapsulate all SQL queries to database that are related to
 * cellink <br> Description: Contains 3 types of SQL methods:<ul> <li>regular
 * jdbc statements</li> <li>prepared statements<br></li></ul> Copyright:
 * Copyright (c) 2008 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public interface CellinkDao {

    public void insert(CellinkDto cellink) throws SQLException;

    public void update(CellinkDto cellink) throws SQLException;

    public void remove(Long id) throws SQLException;

    public int count() throws SQLException;

    public int count(long userId, int role, String company, Integer state, String type, String name)
            throws SQLException;

    // transferre to  class//
    public void insertScreenData(Long gprsId) throws SQLException;

    public void disconnect(Long cellinkId) throws SQLException;

    public void disconnectStarted(Long cellinkId) throws SQLException;

    public CellinkDto getById(Long cellinkId) throws SQLException;

    public CellinkDto validate(String name, String password) throws SQLException;

    public CellinkDto getActualCellink() throws SQLException;

    public List<CellinkDto> getAll() throws SQLException;

    public List<CellinkDto> getAll(Integer state) throws SQLException;

    public List<CellinkDto> getAll(Long userId, Integer state, String searchname, String type) throws SQLException;

    public List<CellinkDto> getAll(String company, Integer state, String type, String name) throws SQLException;

    public List<CellinkDto> getAll(String company, Integer state, String type, String name, String index)
            throws SQLException;

    public List<CellinkDto> getAll(int role, String company, Integer state, String type, String name, String index)
            throws SQLException;

    public List<CellinkDto> getAllUserCellinks(Long userId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
