/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tripletriadhw3;

import java.io.Serializable;

/**
 *
 * @author danielgarcia
 */
public class Person implements Serializable{
    private String name;
    
    public Person(){
        name = "ble";
    }
    
    public void setPerson(String bla){
        name = bla;
    }
    
    public String getPerson(){
        return name;
    }
}
