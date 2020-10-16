/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author paulo
 */
public class Tag {
    private String name;
    private Integer id;

    public Tag(String name){
        this.name = name;
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
