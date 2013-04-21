
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.dao;

//~--- non-JDK imports --------------------------------------------------------
import com.agrologic.dto.DistribDto;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author JanL
 */
public interface DistribDao {

    public void insert(DistribDto gas) throws SQLException;

    public void remove(Long id) throws SQLException;

    public DistribDto getById(Long id) throws SQLException;

    public List<DistribDto> getAllByFlockId(Long flockId) throws SQLException;
}


//~ Formatted by Jindent --- http://www.jindent.com
