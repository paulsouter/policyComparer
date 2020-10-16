/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author soupa868
 */
public class DaoTests {

//    private PolicyDaoInterface dao = new DAO();
    private PolicyDaoInterface dao = new PolicyDao("jdbc:h2:tcp://localhost:9004/project-testing");
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;
    private Collection<String> categorys = new HashSet();

    public DaoTests() {
    }

    @Before
    public void setUp() {
        this.prodOne = new Product(1, "name1", "cat1", "desc1",
                new BigDecimal("11.00"), new BigDecimal("22.00"));
        this.prodTwo = new Product(2, "name2", "cat2", "desc2",
                new BigDecimal("33.00"), new BigDecimal("44.00"));
        this.prodThree = new Product(3, "name3", "cat3", "desc3",
                new BigDecimal("55.00"), new BigDecimal("66.00"));
// save the products
        dao.addProduct(prodOne);
        categorys.add(prodOne.getCategory());
        dao.addProduct(prodTwo);
        categorys.add(prodTwo.getCategory());
    }

    @Test
    public void testDaoSave() {
// save the product using DAO
        dao.addProduct(prodThree);
// retrieve the same product via DAO
        Product retrieved = dao.searchId(3);
// ensure that the product we saved is the one we got back
        assertEquals("Retrieved product should be the same",
                prodThree, retrieved);
    }
    @Test 
    public void testDaoEdit(){
        Product p = dao.searchId(prodOne.getProductId());
        p.setName("hello");
        dao.addProduct(p);
        assertEquals(p.getName(), dao.searchId(prodOne.getProductId()).getName());
    }

    @Test
    public void testDaoDelete() {
// delete the product via the DAO
        dao.deleteProduct(prodOne);
// try to retrieve the deleted product
        Product retrieved = dao.searchId(1);
// ensure that the student was not retrieved (should be null)
        assertNull("Product should no longer exist", retrieved);
    }

    @Test
    public void testDaoGetAll() {
        Collection<Product> products = dao.getProducts();
// ensure the result includes the two saved products
        assertTrue("prodOne should exist", products.contains(prodOne));
        assertTrue("prodTwo should exist", products.contains(prodTwo));
// ensure the result ONLY includes the two saved products
        assertEquals("Only 2 products in result", 2, products.size());
// find prodOne - result is not a map, so we have to scan for it
        for (Product p : products) {
            if (p.equals(prodOne)) {
// ensure that all of the details were correctly retrieved
                assertEquals(prodOne.getProductId(), p.getProductId());
                assertEquals(prodOne.getName(), p.getName());
                assertEquals(prodOne.getDescription(), p.getDescription());
                assertEquals(prodOne.getCategory(), p.getCategory());
                assertEquals(prodOne.getPricelist(), p.getPricelist());
                assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());
            }
        }
    }

    @Test
    public void testDaoFindById() {
// get prodOne using findById method
        Product p = dao.searchId(prodOne.getProductId());
// assert that you got back prodOne, and not another product
        assertEquals("retrieved product should be the same", p, prodOne);
// assert that prodOne's details were properly retrieved
        assertEquals(prodOne.getProductId(), p.getProductId());
        assertEquals(prodOne.getName(), p.getName());
        assertEquals(prodOne.getDescription(), p.getDescription());
        assertEquals(prodOne.getCategory(), p.getCategory());
        assertEquals(prodOne.getPricelist(), p.getPricelist());
        assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());
// call getById using a non-existent ID
        Product p2 = dao.searchId(44);
// assert that the result is null
        assertNull("product should not exist ", p2);
    }
    
    @Test
    public void testGetCategory(){
        Collection<String> cat = dao.getCategorys();
        assertEquals(cat, categorys);
    }
      @Test
    public void testDaoFindByCategory() {
// get prodOne using findById method
        Collection<Product> products = dao.filterSearch(prodOne.getCategory());
/// ensure the result includes the two saved products
        assertTrue("prodOne should exist", products.contains(prodOne));
//        assertTrue("prodTwo should exist", products.contains(prodTwo));
// ensure the result ONLY includes the two saved products
//        assertEquals("Only 2 products in result", 2, products.size());
// find prodOne - result is not a map, so we have to scan for it
        for (Product p : products) {
            if (p.equals(prodOne)) {
// ensure that all of the details were correctly retrieved
                assertEquals(prodOne.getProductId(), p.getProductId());
                assertEquals(prodOne.getName(), p.getName());
                assertEquals(prodOne.getDescription(), p.getDescription());
                assertEquals(prodOne.getCategory(), p.getCategory());
                assertEquals(prodOne.getPricelist(), p.getPricelist());
                assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());
            }
        }
    }


    @After
    public void tearDown() {
        dao.deleteProduct(prodOne);
        dao.deleteProduct(prodTwo);
        dao.deleteProduct(prodThree);
    }

}
