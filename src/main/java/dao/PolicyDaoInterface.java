/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Party;
import domain.Policy;
import java.util.Collection;

/**
 *
 * @author soupa868
 */
public interface PolicyDaoInterface {

    void addPolicy(Policy policy);

    void deletePolicy(Policy policy);

    Collection<Policy> getPolicy();

    Collection<Policy> getPartyPolicy(Integer id);

    Collection<Policy> filterSearch(String[] tagss);

    Policy searchId(int id);

    boolean inDatabase(int id);

}
