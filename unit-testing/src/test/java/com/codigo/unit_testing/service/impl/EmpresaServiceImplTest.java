package com.codigo.unit_testing.service.impl;

import com.codigo.unit_testing.aggregates.constants.Constants;
import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import com.codigo.unit_testing.dao.EmpresaRepository;
import com.codigo.unit_testing.entity.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class EmpresaServiceImplTest {

    @Mock
    private EmpresaRepository empresaRepository;
    @InjectMocks
    private EmpresaServiceImpl empresaService;

    private Empresa empresa;
    private EmpresaRequest empresaRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        empresa = new Empresa();
        empresaRequest = new EmpresaRequest();
        empresaRequest.setNumeroDocumento("123456789");
    }

    @Test
    @DisplayName("Deberia retornar conflicto si la empresa ya existe")
    void testDeberiaRetornarEmpresaExiste(){
        //ARRANGE
        when(empresaRepository.existsByNumeroDocumento(anyString())).thenReturn(true);

        //ACT
        ResponseEntity<BaseResponse<Empresa>> resultado = empresaService.crear(empresaRequest);

        //ASSERT
        assertNotNull(resultado);
        assertEquals(Constants.CODE_EXIST, resultado.getBody().getCode());
        assertFalse(resultado.getBody().getObjeto().isPresent());

    }
    @Test
    @DisplayName("Deberia crear empresa nueva correctamente")
    void testDeberiaCrearEmpresaNueva(){
        //ARRANGE
        when(empresaRepository.existsByNumeroDocumento(anyString())).thenReturn(false);
        when(empresaRepository.save(any())).thenReturn(empresa);

        //ACT
        ResponseEntity<BaseResponse<Empresa>> resultado = empresaService.crear(empresaRequest);

        //ASSERT
        assertEquals(Constants.CODE_OK, resultado.getBody().getCode());
        assertTrue(resultado.getBody().getObjeto().isPresent());
        assertSame(empresa, resultado.getBody().getObjeto().get());
    }

}