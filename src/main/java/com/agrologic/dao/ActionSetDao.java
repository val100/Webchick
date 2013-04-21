
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.dto.ActionSetDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

/**
 *
 * @author JanL
 */
public interface ActionSetDao {

    public ActionSetDto getById(Long valueId) throws SQLException;

    public void insert(ActionSetDto ActionSet) throws SQLException;

    public void update(ActionSetDto ActionSet) throws SQLException;

    public void remove(Long ActionSetId) throws SQLException;

    public void insertActionSetToTable(Long programId) throws SQLException;

    public void updateActionSetInTable(Long programId) throws SQLException;

    public void removeActionSetFromTable(Long programId) throws SQLException;

    public List<ActionSetDto> getAll() throws SQLException;

    public List<ActionSetDto> getAll(Long programId) throws SQLException;

    public List<ActionSetDto> getAll(Long programId, Long langId) throws SQLException;

    public List<ActionSetDto> getAllOnScreen(Long programid) throws SQLException;

    public List<ActionSetDto> getAllOnScreen(Long programId, Long langId) throws SQLException;

    public void insertActionSetList(List<ActionSetDto> actionsetList, Long programId) throws SQLException;

    public void saveChanges(Map<Long, String> showMap, Map<Long, Integer> positionMap, Long programId)
            throws SQLException;

    public void saveChanges(Long programId, Long screenId, Map<Long, String> showTableMap,
            Map<Long, Integer> posDataMap)
            throws SQLException;

    public void insertActionSetTranslation(Long actionsetId, Long langId, String translate) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
