
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.model.UserDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 * Title: UserDao - Encapsulate all SQL queries to database that are related to USERS<br>
 * Description: Contains 3 types of SQL methods:<ul>
 *                        <li>regular jdbc statements</li>
 *                        <li>prepared statements<br></li></ul>
 * Copyright:    Copyright (c) 2007
 * @author Valery Manakhimov
 * @version 1.0
 */
public interface UserDao {
    public void insert(UserDto user) throws SQLException;

    public void update(UserDto user) throws SQLException;

    public void remove(Long id) throws SQLException;

    public List<String> getUserCompanies() throws SQLException;

    public Boolean checkNewLoginName(String name) throws SQLException;

    public Integer getTotalNumUsers() throws SQLException;

    public UserDto getById(Long id) throws SQLException;

    public UserDto validate(String name, String password) throws SQLException;

    public List<UserDto> getAll() throws SQLException;

    public List<UserDto> getAll(Integer role, String company, String searchText) throws SQLException;

    public List<UserDto> getAllUsers() throws SQLException;

    public List<UserDto> getAllByRole(Integer role) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
