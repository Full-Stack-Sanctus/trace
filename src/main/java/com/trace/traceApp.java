/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trace;

import View.LoginView;

/**
 *
 * @author USER
 */
public class traceApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        // TODO code application logic here
        LoginView lg = new LoginView();
        lg.setVisible(true);
    }
    
}
