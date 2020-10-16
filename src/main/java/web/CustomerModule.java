///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package web;
//
//import dao.CustomerDaoInterface;
//import domain.Customer;
//import org.jooby.Err;
//import org.jooby.Jooby;
//import org.jooby.Status;
//
///**
// *
// * @author soupa868
// */
//public class CustomerModule extends Jooby {
//
//    public CustomerModule(CustomerDaoInterface dao) {
//        get("/api/customers/:username", (req) -> {
//            String username = req.param("username").value();
//            if(dao.getCustomer(username) == null)
//                 throw new Err(Status.NOT_FOUND);
//            return dao.getCustomer(username);
//        });
//        post("/api/register", (req, rsp) -> {
//            Customer customer = req.body().to(Customer.class);
//            dao.save(customer);
//            rsp.status(Status.CREATED);
//        });
//    }
//
//}
