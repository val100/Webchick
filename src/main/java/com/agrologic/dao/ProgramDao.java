
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dto.ProgramDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 * Title: ProgramDao <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public interface ProgramDao {
    public void insert(ProgramDto program) throws SQLException;

    public void update(ProgramDto program) throws SQLException;

    public void remove(Long id) throws SQLException;

    public int count() throws SQLException;

    public boolean checkNewProgramId(Long id) throws SQLException;

    public ProgramDto getById(Long id) throws SQLException;

    public List<ProgramDto> getAll() throws SQLException;

    public List<ProgramDto> getAll(String searchText) throws SQLException;

    public List<ProgramDto> getAllByUserId(String searchText, Long userId) throws SQLException;

    public List<ProgramDto> getAllByUserCompany(String searchText, String company) throws SQLException;

    public List<ProgramDto> getAll(String searchText, String index) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
