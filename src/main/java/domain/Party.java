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
public class Party {
    private Integer id;
    private String name;
    private String website;

    public Party(Integer id, String name, String website) {
        this.id = id;
        this.name = name;
        this.website = website;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Party{" + "id=" + id + ", name=" + name + ", website=" + website + '}';
    }
    
}
