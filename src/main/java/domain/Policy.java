/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author paulo
 */
public class Policy {

    private Integer id;
    private String name;
    private String text;
    private Date date;
    private Party party;
    private Collection<Tag> tags;

    public Policy() {

    }

//    public Policy(Integer id, String name, String text, Party)
    public Policy(Integer id, String name, String text, Party party, Collection<Tag> tags, Date date) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.party = party;
        this.tags = tags;
        this.date = date;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public String toString() {
        String result = name + "\n" + party.toString() + "\t date" + date.toString() + "\n"
                + text + "\n" + party.getWebsite();
        return result;
    }
}
