package com.codigo.unit_testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraServiceTest {
    private CalculadoraService service;

    @BeforeEach
    void setUp() {
        service = new CalculadoraService();
    }

    @Test
    void testSumarHappyPath() {
        int resultado = service.sumar(2, 3);
        assertEquals(5, resultado, "El valor obtenido " + resultado
                + " no es igual al esperado");
    }

    @Test
    void testRestarHappyPath() {
        int resultado = service.restar(2, 10);
        assertEquals(-8, resultado);
    }

    @Test
    void testDividirHappyPath() {
        int resultado = service.dividir(10, 2);
        assertEquals(5, resultado);
    }

    @Test
    void testDividirErrorTesting() {
        assertThrows(ArithmeticException.class, () -> service.dividir(10, 0));
    }

    @Test
    void  testEsParHappyPath(){
        boolean resultado = service.esPar(4);
        assertTrue(resultado);
    }
    @Test
    void  testEsParErrorTesting(){
        boolean resultado = service.esPar(5);
        assertFalse(resultado);
    }

    @Test
    void testEstaEnRangoHappyPath(){
        assertTrue(service.estaEnRango(5,1,10));
    }
    @Test
    void testEstaEnRangoErrorTesting(){
        assertFalse(service.estaEnRango(5,1,4));
    }

    @Test
    void testObtenerNombreHappyPath(){
        String resultado = service.obtenerNombre("paul");
        assertEquals("PAUL", resultado);
    }
    @Test
    void testObtenerNombreErrorTesting(){
        String resultado = service.obtenerNombre("paul");
        assertNotEquals("Paul", resultado);
    }

    @Test
    void testMostrarResultadoHappyPath(){
        assertDoesNotThrow(() -> service.mostrarResultado(10));
    }
}