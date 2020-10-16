/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Party;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author paulo
 */
public class PartyDao implements PartyDaoInterface {

    private String url = "jdbc:h2:tcp://localhost:9092/~/policy;IFEXISTS=TRUE";

    public PartyDao() {

    }

    public PartyDao(String url) {
        this.url = url;
    }

    @Override
    public void addParty(Party party) {
        String sql = "insert into party(partyname, website) values(?, ?)";

        try (
                Connection connection = JdbcConnection.getConnection(url);
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, party.getName());
            stmt.setString(2, party.getWebsite());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Party> getPartys() {
        String sql = "select * from party";
        Collection<Party> partys = new HashSet();
        try (
                Connection connection = JdbcConnection.getConnection(url);
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer partyId = rs.getInt("partyid");
                String name = rs.getString("partyname");
                String website = rs.getString("website");
                Party p = new Party(partyId, name, website);
                partys.add(p);
            }

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
        return partys;
    }

    @Override
    public Party getParty(Integer id) {
        String sql = "select * from party where partyid = (?)";

        try (
                Connection connection = JdbcConnection.getConnection(sql);
                PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            Integer partyId = rs.getInt("partyid");
            String name = rs.getString("partyname");
            String website = rs.getString("website");

            Party p = new Party(id, name, website);
            return p;

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }

    }

    @Override
    public Party getParty(String name) {
        String sql = "select * from party where partyname =(?)";

        try (
                Connection connection = JdbcConnection.getConnection(sql);
                PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, name);
            System.out.println("test " + stmt.toString());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
            System.out.println("Hi " + rs.toString());

//            System.out.println("test " + temp);
            String website = rs.getString("website");
            String partyName = rs.getString("partyname");
            Integer temp = rs.getInt("partyid");
//            BigDecimal temp = rs.getBigDecimal("partyid");
            Integer partyId = temp.intValue();

            Party p = new Party(partyId, partyName, website);
            System.out.println("party " + p.toString());
            return p;
            }else{
                System.out.println("what the fuck");
                return null;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }

    }

    @Override
    public Party getPolicyParty(Integer id) {
        String sql = "select * from party where policyid = (?)";

        try (
                Connection connection = JdbcConnection.getConnection(url);
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Integer partyId = rs.getInt("partyid");
            String name = rs.getString("partyname");
            String website = rs.getString("website");
            Party p = new Party(partyId, name, website);

            return p;

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

}
