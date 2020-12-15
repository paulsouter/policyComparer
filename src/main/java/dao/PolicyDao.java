/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// todo split into parts policy and maybe tags?
package dao;

import domain.Party;
import domain.Policy;
import domain.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author soupa868
 */
public class PolicyDao implements PolicyDaoInterface {

//    private String url = "jdbc:h2:tcp://localhost:9004/project;IFEXISTS=TRUE";
    private String url = "jdbc:h2:tcp://localhost:9092/~/policy;IFEXISTS=TRUE";
    private PartyDao partyDao = new PartyDao();
    private TagsDaoInterface tagsDao = new TagsDao();

    public PolicyDao() {

    }

    public PolicyDao(String url) {
        this.url = url;
    }

    @Override
    public void addPolicy(Policy policy) {
        String sql = "merge into policy(policyid, policyname, policyText"
                + ",partyid, dateupload) values (?,?,?,?,?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // copy the data from the student domain object into the SQL parameters
            Timestamp time = new Timestamp(policy.getDate().getTime());
            stmt.setInt(1, policy.getId());
            stmt.setString(2, policy.getName());
            stmt.setString(3, policy.getText());
            stmt.setInt(4,policy.getParty().getId());
            stmt.setTimestamp(5, time);//might be a problem in the db
            stmt.executeUpdate();  // execute the statement
            
        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
        for(Tag tag : policy.getTags()){
            Integer tagid = tagsDao.getTagName(tag.getName()).getId();
            tagsDao.addTagsToPolicy(policy.getId(), tagid);
        }

    }

    @Override
    public void deletePolicy(Policy policy) {
        String sql = "delete from policy where policyid = ?";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // execute the query
            stmt.setInt(1, policy.getId());
            stmt.executeUpdate();

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Policy> filterSearch(String[] tags) {
        String sql = "select * from policy where policyid ="
                + "(select policynum from policytag where tagsnum ="
                + " (select tagsId from tags where tagsName = (?))) = ";
        Collection<Policy> policy = new HashSet();
        for (int index = 0; index < tags.length; index++) {
            try (
                    // get connection to database
                    Connection connection = JdbcConnection.getConnection(url);
                    // create the statement
                    PreparedStatement stmt = connection.prepareStatement(sql);) {
                // execute the query
                stmt.setString(1, tags[index]);
                policy = new HashSet();
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Integer id = rs.getInt("policyId");
                    String name = rs.getString("policyname");
                    String text = rs.getString("policytext");
                    Date date = rs.getDate("dateupload");
                    Party party = partyDao.getParty(id);
                    Collection<Tag> policyTags = tagsDao.getPolicyTags(id);

                    // use the data to create a student object
                    Policy p = new Policy(id, name, text, party, policyTags, date);

                    policy.add(p);
                }
                return policy;
            } catch (SQLException ex) {  // we are forced to catch SQLException
                // don't let the SQLException leak from our DAO encapsulation
                throw new DaoException(ex.getMessage(), ex);
            }
        }
        return policy;
    }

    @Override
    public Collection<Policy> getPolicy() {//need to gt the date add to the policy.
        String sql = "select * from policy order by policyid";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            List<Policy> policys = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {

                // get the data out of the query
                Integer id = rs.getInt("policyid");
                String name = rs.getString("policyname");
                String text = rs.getString("policyText");
//                String date = rs.getString("date");
                Party party = partyDao.getPolicyParty(id);
                Collection<Tag> tags = tagsDao.getPolicyTags(id);
                Date date = rs.getDate("dateupload");

                // use the data to create a student object
                Policy p = new Policy(id, name, text, party, tags, date);

                // and put it in the collection
                policys.add(p);
            }

            return policys;

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Policy searchId(int id) {
        String sql = "select * from policy where policyId = (?)";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // execute the query
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Integer policyId = rs.getInt("policyid");
                String name = rs.getString("policyname");
                String text = rs.getString("policytext");
//                String date = rs.getString("category");
                Party party = partyDao.getPolicyParty(id);
                Collection<Tag> tags = tagsDao.getPolicyTags(id);
                Date date = rs.getDate("dateupload");

                // use the data to create a student object
                Policy p = new Policy(policyId, name, text, party, tags, date);
                return p;

            } else {
                return null;
            }

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Policy> getPartyPolicy(Integer id) {
        String sql = "select * from policy where id = "
                + "(select partyid from party where partyid = (?))";
        try (
                Connection connection = JdbcConnection.getConnection(url);
                PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Policy> policys = new ArrayList();
            while (rs.next()) {
                Integer policyId = rs.getInt("policyid");
                String name = rs.getString("policyname");
                String text = rs.getString("policyText");
                Date date = rs.getDate("dateupload");
                Party party = partyDao.getParty(id);
                Collection<Tag> tags = tagsDao.getPolicyTags(policyId);
                
                Policy p = new Policy(policyId, name, text, party, tags, date);
                policys.add(p);
            }

            return policys;

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean inDatabase(int id) {
        if (searchId(id) != null) {
            return true;
        } else {
            return false;
        }

    }

}
