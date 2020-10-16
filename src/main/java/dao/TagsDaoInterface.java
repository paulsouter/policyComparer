/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Policy;
import domain.Tag;
import java.util.Collection;

/**
 *
 * @author paulo
 */
public interface TagsDaoInterface {

   

    Collection<Tag> getTags();

    Collection<Tag> getPolicyTags(Integer id);
    
    Collection<Policy> getTagPolicys(Integer id);
    
    void addTags(Collection<Tag> tags);
    
    void addTagsToPolicy(Integer policyId, Integer tagId);
    
    Tag getTagId(Integer id);
    
    Tag getTagName(String name);
    
    boolean inDatabase(Tag tag);

}
