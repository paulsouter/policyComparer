/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.PartyDaoInterface;
import org.jooby.Jooby;
import dao.PolicyDaoInterface;
import dao.TagsDaoInterface;
import domain.Policy;
import java.util.Collection;

/**
 *
 * @author soupa868
 */
public class ProductModule extends Jooby {

    public ProductModule(PolicyDaoInterface dao, PartyDaoInterface partyDao, TagsDaoInterface tagsDao) {
        get("/api/policies", () -> dao.getPolicy());
        get("/api/policies/:id", (req) -> {
            int id = req.param("id").intValue();
            return dao.searchId(id);
        });
        get("/api/tags", () -> tagsDao.getTags());
        get("/api/tags/:tag", (req) -> {
            int id = req.param("tag").intValue();
            Collection<Policy> results = tagsDao.getTagPolicys(id);
            return results;
        });

        get("/api/parties", () -> partyDao.getPartys());
        get("/api/parties/:party", (req) -> {
            String party = req.param("party").value();
            return partyDao.getParty(party);
        });
    }
}
