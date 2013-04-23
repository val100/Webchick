
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.model.HistorySettingDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;


public interface HistorySettingDao {
    List<HistorySettingDto> getHistorySetting(Long programId) throws SQLException;

    List<HistorySettingDto> getSelectedHistorySetting(Long programId) throws SQLException;

    void saveHistorySetting(List<HistorySettingDto> hsl) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
