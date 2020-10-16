/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Party;
import java.util.Collection;

/**
 *
 * @author paulo
 */
public interface PartyDaoInterface {

    void addParty(Party party); //todo delete party

    Party getPolicyParty(Integer id);

    Collection<Party> getPartys();

    Party getParty(Integer id);

    Party getParty(String name);

}
