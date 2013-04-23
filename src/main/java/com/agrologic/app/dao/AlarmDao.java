
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.app.model.AlarmDto;
//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;
import java.util.List;

/**
 * Title: AlarmDao <br> Decription: <br> Copyright: Copyright (c) 2009 <br>
 * Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.1 <br>
 */
public interface AlarmDao {

    public void insert(AlarmDto alarm) throws SQLException;

    public void update(AlarmDto alarm) throws SQLException;

    public void remove(Long id) throws SQLException;

    public void insertTranslation(Long alarmId, Long langId, String translation) throws SQLException;

    public AlarmDto getById(Long id) throws SQLException;

    public List<AlarmDto> getAll() throws SQLException;

    public List<AlarmDto> getAll(Long langId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
