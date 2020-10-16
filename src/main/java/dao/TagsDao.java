/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Policy;
import domain.Tag;
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
public class TagsDao implements TagsDaoInterface {
    
    private PolicyDaoInterface policyDao;
    private String url = "jdbc:h2:tcp://localhost:9092/~/policy;IFEXISTS=TRUE";

    public TagsDao() {
    }

    public TagsDao(String url) {
        this.url = url;
    }

    @Override
    public void addTags(Collection<Tag> tags) {
        String sql = "merge into Tags(tagname) values(?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            for (Tag tag : tags) {
                if(inDatabase(tag)){
                    continue;
                }
                stmt.setString(0, tag.getName());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void addTagsToPolicy(Integer tagId, Integer policyId) {
             String sql = "merge into policytag(policyid, tagid) values(?, ?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
                
                stmt.setInt(0, policyId);
                stmt.setInt(1, tagId);
                stmt.executeUpdate();
            

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }

    }

    @Override
    public Tag getTagId(Integer id) {
        String sql = "select * from tags where tagid = (?)";

        try (
                Connection dbCon = JdbcConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            stmt.setInt(0, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int tagId = rs.getInt("tagid");
                String name = rs.getString("tagname");
                Tag t = new Tag(tagId, name);
                return t;
            }
            else 
                return null;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

     @Override
    public Tag getTagName(String name) {
        String sql = "select * from tags where tagname = (?)";

        try (
                Connection dbCon = JdbcConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            stmt.setString(0, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int tagId = rs.getInt("tagid");
                String tagName = rs.getString("tagname");
                Tag t = new Tag(tagId, tagName);
                return t;
            }
            else 
                return null;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
    
    @Override
    public Collection<Tag> getTags() {
        String sql = "select distinct tagname from tags";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            Collection<Tag> cat = new HashSet();
            while (rs.next()) {
                Integer id = rs.getInt("tagid");
                cat.add(getTagId(id));

            }
            return cat;
        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    
    
    @Override
    public Collection<Policy> getTagPolicys(Integer id){//should get everything in on go not in multiple queres
        
        policyDao = new PolicyDao();
         String sql = "select distinct policyid from policy where policyid = "
                + "(select policynum from policytag where tagnum = (?)";
        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            Collection<Policy> cat = new HashSet();
            while (rs.next()) {
                int policyId = rs.getInt("policyid");
                cat.add(policyDao.searchId(policyId));

            }
            return cat;
        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Tag> getPolicyTags(Integer policyId) {
        String sql = "select distinct tagid from tags where tagsid = "
                + "(select tagsnum from policytag where policynum = (?)";
        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, policyId);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            Collection<Tag> cat = new HashSet();
            while (rs.next()) {
                Integer id = rs.getInt("tagid");
                cat.add(getTagId(id));

            }
            return cat;
        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public boolean inDatabase(Tag tag) {
        if (getTagName(tag.getName()) != null) {
            return true;
        } else {
            return false;
        }

    }

}
