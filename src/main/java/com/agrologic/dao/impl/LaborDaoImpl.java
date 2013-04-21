
/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.dao.impl;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.dao.LaborDao;
import com.agrologic.dto.LaborDto;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JanL
 */
public class LaborDaoImpl extends ConnectorDao implements LaborDao {
    private LaborDto makeLabor(ResultSet rs) throws SQLException {
        LaborDto labor = new LaborDto();

        labor.setId(rs.getLong("ID"));
        labor.setFlockId(rs.getLong("FlockID"));
        labor.setWorkerId(rs.getLong("WorkerID"));
        labor.setDate(rs.getString("Date"));
        labor.setHours(rs.getInt("Hours"));
        labor.setSalary(rs.getFloat("Salary"));

        return labor;
    }

    private List<LaborDto> makeLaborList(ResultSet rs) throws SQLException {
        List<LaborDto> laborList = new ArrayList<LaborDto>();

        while (rs.next()) {
            laborList.add(makeLabor(rs));
        }

        return laborList;
    }

    @Override
    public void insert(LaborDto labor) throws SQLException {
        String            sqlQuery = "insert into labor values (?,?,?,?,?,?)";
        PreparedStatement prepstmt = null;
        Connection        con      = null;

        try {
            con      = getConnection();
            prepstmt = con.prepareStatement(sqlQuery);
            prepstmt.setObject(1, null);
            prepstmt.setString(2, labor.getDate());
            prepstmt.setLong(3, labor.getWorkerId());
            prepstmt.setInt(4, labor.getHours());
            prepstmt.setFloat(5, labor.getSalary());
            prepstmt.setLong(6, labor.getFlockId());
            prepstmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);

            throw new SQLException("Cannot Insert Labor To The DataBase");
        } finally {
            prepstmt.close();
            closeConnection(con);
        }
    }

    @Override
    public void remove(Long id) throws SQLException {
        String            sqlQuery = "delete from labor where ID=?";
        PreparedStatement prepstmt = null;
        Connection        con      = null;

        try {
            con      = getConnection();
            prepstmt = con.prepareStatement(sqlQuery);
            prepstmt.setLong(1, id);
            prepstmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);

            throw new SQLException("Cannot Delete Controller From DataBase");
        } finally {
            prepstmt.close();
            closeConnection(con);
        }
    }

    @Override
    public LaborDto getById(Long id) throws SQLException {
        String            sqlQuery = "select * from labor where ID=?";
        PreparedStatement prepstmt = null;
        Connection        con      = null;

        try {
            con      = getConnection();
            prepstmt = con.prepareStatement(sqlQuery);
            prepstmt.setLong(1, id);

            ResultSet rs = prepstmt.executeQuery();

            if (rs.next()) {
                return makeLabor(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            printSQLException(e);

            throw new SQLException("Cannot Retrieve Labor " + id + " From DataBase");
        } finally {
            prepstmt.close();
            closeConnection(con);
        }
    }

    @Override
    public List<LaborDto> getAllByFlockId(Long flockId) throws SQLException {
        String            sqlQuery = "select * from labor where FlockID=?";
        PreparedStatement prepstmt = null;
        Connection        con      = null;

        try {
            con      = getConnection();
            prepstmt = con.prepareStatement(sqlQuery);
            prepstmt.setLong(1, flockId);

            ResultSet rs = prepstmt.executeQuery();

            return makeLaborList(rs);
        } catch (SQLException e) {
            printSQLException(e);

            throw new SQLException("Cannot Retrieve All Labor of Flock " + flockId + " From DataBase");
        } finally {
            prepstmt.close();
            closeConnection(con);
        }
    }

    @Override
    public String getCurrencyById(Long id) throws SQLException {
        String            sqlQuery = "select * from currency where ID=?";
        PreparedStatement prepstmt = null;
        Connection        con      = null;

        try {
            con      = getConnection();
            prepstmt = con.prepareStatement(sqlQuery);
            prepstmt.setLong(1, id);

            ResultSet rs = prepstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Symbol");
            } else {
                return null;
            }
        } catch (SQLException e) {
            printSQLException(e);

            throw new SQLException("Cannot Retrieve Labor " + id + " From DataBase");
        } finally {
            prepstmt.close();
            closeConnection(con);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
