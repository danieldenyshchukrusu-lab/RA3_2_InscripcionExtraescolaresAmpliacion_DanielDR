/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.ra3_inscripcionextraescolaresampliacion_danieldr;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AlumnoD
 * 
 */
public class InscripcionTest {

    /**
    *
    * @author AlumnoDaniel
    * @param 
    */
    @Test
    public void testToString() {
        List<String> bingos = new ArrayList<>();
        Inscripcion r = new Inscripcion("Daniel","","Ajedrez","","","",8,bingos);
        assertEquals("Daniel--Ajedrez",r.toString());
    }
    
    //PARA HACER JAVADOC TIENES QUE PONER LA DEPEDENCIA BRO
    
}
