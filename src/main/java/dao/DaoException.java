/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author soupa868
 */
public class DaoException extends RuntimeException{
    public DaoException(String reason) {
        super(reason);
    }
    public DaoException(String reason, Throwable cause) {
        super(reason, cause);
    }

}
