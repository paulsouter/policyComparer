package gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.*;
import domain.Product;
import gui.*;
import java.math.*;
import java.util.Collection;
import java.util.TreeSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author soupa868
 */
public class saveTest {

    public saveTest() {
    }

    private PolicyDaoInterface dao;
    private DialogFixture fixture;
    private Robot robot;
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;

    @Before
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        // Slow down the robot a little bit - default is 30 (milliseconds).
        // Do NOT make it less than 5 or you will have thread-race problems.
        robot.settings().delayBetweenEvents(10);
        dao = mock(DAO.class);

        // add some majors for testing with
        Collection<String> majors = new TreeSet<>();
        majors.add("cat1");
        majors.add("cat2");
        // stub the getMajors method to return the test majors
        when(dao.getCategorys()).thenReturn(majors);

        this.prodOne = new Product(1, "name1", "desc1", "cat1",
                new BigDecimal("11.00"), new BigDecimal("22.00"));
        this.prodTwo = new Product(2, "name2", "desc2", "cat2",
                new BigDecimal("33.00"), new BigDecimal("44.00"));
        this.prodThree = new Product(3, "name3", "cat3", "desc3",
                new BigDecimal("55.00"), new BigDecimal("66.00"));
//        dao.addProduct(prodOne);
//        dao.addProduct(prodTwo);
    }

    @After
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }

    @Test
    public void testEdit() {

        // create dialog passing in student and mocked DAO
        DataEntry dialog = new DataEntry(null, true, prodOne, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);

        // show the dialog on the screen, and ensure it is visible
        fixture.show().requireVisible();

        // verify that the UI componenents contains the student's details
        fixture.textBox("txtID").requireText("1");
        fixture.textBox("txtName").requireText("name1");
        fixture.textBox("txtDescription").requireText("desc1");
        fixture.comboBox("txtCategory").requireSelection("cat1");
        fixture.textBox("txtPrice").requireText("11.00");
        fixture.textBox("txtQuantity").requireText("22.00");

        // edit the name and major
        fixture.textBox("txtName").selectAll().deleteText().enterText("Jim");
        fixture.comboBox("txtCategory").selectItem("cat2");
        fixture.textBox("txtDescription").selectAll().deleteText().enterText("hello");
        fixture.textBox("txtPrice").selectAll().doubleClick().deleteText().enterText("24");
        fixture.textBox("txtQuantity").selectAll().doubleClick().deleteText().enterText("5");
        // click the save button
        fixture.button("saveButton").click();

        // create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed student
        verify(dao).addProduct(argument.capture());

        // retrieve the passed student from the captor
        Product editedProduct = argument.getValue();

        // check that the changes were saved
        assertEquals("Ensure the name was changed", "Jim", editedProduct.getName());
        assertEquals("Ensure the major was changed", "cat2", editedProduct.getCategory());
        assertEquals("Ensure the desc was changed", "hello", editedProduct.getDescription());
        assertEquals("Ensure the price was changed", new BigDecimal(24), editedProduct.getPricelist());
        assertEquals("Ensure the quantity was changed", new BigDecimal(5), editedProduct.getQuantityInStock());
    }

    @Test
    public void testSave() {
        // create the dialog passing in the mocked DAO
        DataEntry dialog = new DataEntry(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // enter some details into the UI components
        fixture.textBox("txtID").enterText("1234");
        fixture.textBox("txtName").enterText("Jack");
        fixture.comboBox("txtCategory").selectItem("cat2");
        fixture.textBox("txtDescription").enterText("hello");
        fixture.textBox("txtPrice").enterText("23");
        fixture.textBox("txtQuantity").enterText("3");

        // click the save button
        fixture.button("saveButton").click();

        // create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed student
        verify(dao).addProduct(argument.capture());

        // retrieve the passed student from the captor
        Product savedProduct = argument.getValue();

        // test that the student's details were properly saved
        assertEquals("Ensure the ID was saved", new Integer(1234), savedProduct.getProductId());
        assertEquals("Ensure the name was changed", "Jack", savedProduct.getName());
        assertEquals("Ensure the major was changed", "cat2", savedProduct.getCategory());
        assertEquals("Ensure the desc was changed", "hello", savedProduct.getDescription());
        assertEquals("Ensure the price was changed", new BigDecimal(23), savedProduct.getPricelist());
        assertEquals("Ensure the quantity was changed", new BigDecimal(3), savedProduct.getQuantityInStock());
    }

}
