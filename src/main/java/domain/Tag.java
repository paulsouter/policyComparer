/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Collection;

/**
 *
 * @author paulo
 */
public class Tag {
    private String name;
    private Integer id;
    private Collection<Policy> policys;

    public Tag(String name, Collection<Policy> policys){
        this.name = name;
        this.policys = policys;
    }
    public Tag(String name){
        this.name = name;
    }

    public Collection<Policy> getPolicys() {
        return policys;
    }

    public void setPolicys(Collection<Policy> policys) {
        this.policys = policys;
    }
    @Override
    public String toString() {
        return "Tag{" + "name=" + name + ", id=" + id + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tag(Integer id, String name) {
        this.name = name;
        this.id = id;
    }
    
    
}
