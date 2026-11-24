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
 * @author Zombo
 */
public class InscripcionTest {
    
    public InscripcionTest() {
    }

    @Test
    public void testNombreValido() {
    }
    
    @Test
    public void testToString() {
        List<String> bingos = new ArrayList<>();
        Inscripcion r = new Inscripcion("Daniel","","Ajedrez","","","",8,bingos);
        assertEquals("Daniel--Ajedrez",r.toString());
    }
    
    //HE CREADO EL METODO NOMBRE VALIDO PARA PODER HACER TEST DE EJEMPLO.
    
    @Test
    public void nombreConCEROCaracteresNoEsValido() {
        Inscripcion i = new Inscripcion("", "Robótica");
        assertFalse(i.nombreValido());
    }
    
    @Test
    public void nombreCon1Caracter() {
        Inscripcion i = new Inscripcion("a","Robótica");
        assertTrue(i.nombreValido());
    }
    
    @Test 
    public void nombreCon15Caracteres() {
        Inscripcion i = new Inscripcion("aaaaaaaaaaaaaaa","Robótica");
        assertTrue(i.nombreValido());
    }
    
    @Test 
    public void nombreCon16Caracteres() {
        Inscripcion i = new Inscripcion("aaaaaaaaaaaaaaaa","Robótica");
        assertFalse(i.nombreValido());
    }
    
    //HE CREADO EL MÉTODO HORAS VALIDAS PARA PODER HACER TEST DE EJEMPLO.
    
    @Test
    public void horasCon1SonValidas() {
        Inscripcion i = new Inscripcion("Ana", "1º ESO", "Robótica", "Lun-Mié 16:00", "Iniciación", "Mensual", 1, new ArrayList<>());
        assertTrue(i.horasValidas());
    }
    
    @Test
    public void horasCon99SonValidas() {
        Inscripcion i = new Inscripcion("Ana", "1º ESO", "Robótica", "Lun-Mié 16:00", "Iniciación", "Mensual", 99, new ArrayList<>());
        assertFalse(i.horasValidas());
    }
    
    @Test
    public void horasCon11SonValidas() {
        Inscripcion i = new Inscripcion("Ana", "1º ESO", "Robótica", "Lun-Mié 16:00", "Iniciación", "Mensual", 11, new ArrayList<>());
        assertFalse(i.horasValidas());
    }
    
    @Test
    public void horasCon10SonValidas() {
        Inscripcion i = new Inscripcion("Ana", "1º ESO", "Robótica", "Lun-Mié 16:00", "Iniciación", "Mensual", 10, new ArrayList<>());
        assertTrue(i.horasValidas());
    }
    
    @Test
    public void horasCon5SonValidas() {
        Inscripcion i = new Inscripcion("Ana", "1º ESO", "Robótica", "Lun-Mié 16:00", "Iniciación", "Mensual", 5, new ArrayList<>());
        assertTrue(i.horasValidas());
    }
    
}
