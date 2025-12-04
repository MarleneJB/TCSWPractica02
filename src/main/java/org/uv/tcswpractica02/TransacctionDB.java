/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tcswpractica02;

import java.sql.Connection;

/**
 *
 * @author melis
 */
public abstract class TransacctionDB <T , ID>{
    protected T pojoDB;
    protected TransacctionDB(T pojoDB){
        this.pojoDB=pojoDB;
        
    }
    public abstract boolean execute (Connection con);
}
