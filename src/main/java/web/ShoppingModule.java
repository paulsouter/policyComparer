///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package web;
//
//import dao.SaleDAO;
//import dao.SaleJdbcDAO;
//import domain.Sale;
//import domain.SaleItem;
//import java.math.BigDecimal;
//import java.util.concurrent.CompletableFuture;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.SimpleEmail;
//import org.jooby.Jooby;
//import org.jooby.Status;
//
///**
// *
// * @author soupa868
// */
//public class ShoppingModule extends Jooby {
//
//    private SaleDAO dao = new SaleJdbcDAO();
//
//    public ShoppingModule() {
//        post("/api/save", (req, rsp) -> {
//            Sale sale = req.body().to(Sale.class);
//            System.out.println(sale.toString());
//            dao.save(sale);
//            CompletableFuture.runAsync(() -> {
//                try {
//                Email e = new SimpleEmail();
//                e.setHostName("localHost");
//                e.setSmtpPort(2525);
//                e.setFrom("hello@test");
//                e.addTo(sale.getCustomer().getEmailAddress());
//                e.setSubject("comfirm order");
//                String order = "Your order is: \n";
//                BigDecimal num = new BigDecimal(0);
//                order += "name\tquantity\tprice\ttotalprice\n";
//                for (SaleItem i : sale.getSaleItems()) {
//                    order += i.toString();
//                    order += "\n";
//                    System.out.println("old" + num);
//                    System.out.println(i.getSalePrice() +"\n"+ i.getQuantityPurchased());
//                    System.out.println(i.getSalePrice().multiply(i.getQuantityPurchased()));
//                    num = num.add(i.getSalePrice().multiply(i.getQuantityPurchased()));
//                    System.out.println("new " + num);
//                }
//                order+="total\t\t\t" + num.toString();
//                e.setMsg(order);
//                e.send();
//                }catch(Exception e){}
////new EmailTest().sendEmail(sale.getCustomer(), sale);
//            });
//
//            rsp.status(Status.CREATED);
//
//        });
//  
//    }
//
//}
