///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package web;
//
//import domain.Customer;
//import domain.Sale;
//import domain.SaleItem;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.SimpleEmail;
//
///**
// *
// * @author soupa868
// */
//public class EmailTest {
//
//
//    public void sendEmail(Customer c, Sale s) throws Exception {
//        System.out.println("hi");
//        Email e = new SimpleEmail();
//        e.setHostName("localHost");
//        e.setSmtpPort(2525);
//        e.setFrom("hello@test");
//        e.addTo(c.getEmailAddress());
//        e.setSubject("comfirm order");
//        String order = "Your order is: \n";
//        order+= "name\tquantity\tprice\ttotalprice\n";
//        for (SaleItem i : s.getSaleItems()) {
//            order += i.toString();
//            order += "\n";
//        }
//        e.setMsg(order);
//        e.send();
//    }
//}
