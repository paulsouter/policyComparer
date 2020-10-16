/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.DAO;
import domain.Product;
import gui.helpers.SimpleListModel;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import dao.PolicyDaoInterface;

/**
 *
 * @author soupa868
 */
public class testView {

    public testView() {
    }

    private PolicyDaoInterface dao;
    private DialogFixture fixture;
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;

    @Before
    public void setUp() {
      
        
        this.prodOne = new Product(1, "name1", "desc1", "cat1",
                new BigDecimal("11.00"), new BigDecimal("22.00"));
        this.prodTwo = new Product(2, "name2", "desc2", "cat2",
                new BigDecimal("33.00"), new BigDecimal("44.00"));
        this.prodThree = new Product(3, "name3", "cat3", "desc3",
                new BigDecimal("55.00"), new BigDecimal("66.00"));

        // add some majors for testing with
        Collection<Product> products = new HashSet<>();
        products.add(prodOne);
        products.add(prodTwo);
        // stub the getMajors method to return the test majors
          dao = mock(DAO.class);
        when(dao.getProducts()).thenReturn(products);

//        dao.addProduct(prodOne);
//        dao.addProduct(prodTwo);
    }

    @After
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }

    @Test
    public void testProducts() {
        // get the model
        System.err.println("hi");
        ViewProduct dialog = new ViewProduct(null, true, dao);
        fixture = new DialogFixture(dialog);
        fixture.show().requireVisible();

        SimpleListModel model = (SimpleListModel) fixture.list("lstProducts").target().getModel();
// check the contents
        assertTrue("list contains the expected product", model.contains(dao.getProducts()));
        assertEquals("list contains the correct number of products", 2, model.getSize());

    }

}
