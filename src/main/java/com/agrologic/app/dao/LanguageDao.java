
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.model.LanguageDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;

/**
 * Title: LanguageDao <br>
 * Decription: <br>
 * Copyright:   Copyright (c) 2009 <br>
 * Company:     Agro Logic LTD. <br>
 * @author      Valery Manakhimov <br>
 * @version     1.1 <br>
 */
public interface LanguageDao {
    public Long getLanguageId(String l) throws SQLException;

    public LanguageDto getById(Long langId) throws SQLException;

    public List<LanguageDto> geAll() throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
