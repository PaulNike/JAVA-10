package com.codigo.patron_adapter.adapter;

import com.codigo.patron_adapter.antiguo.Biblioteca;
import com.codigo.patron_adapter.model.Libro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

@Component
public class AdapterBiblioteca {
    private final Biblioteca biblioteca
             = new Biblioteca();

    private final XmlMapper xmlMapper = new XmlMapper();

    public String obtenerDetalle(int idLibro){
        //Obteniendo información de la Biblioteca Antigua XML
        String detalle = biblioteca.obtenerInfo(idLibro);
        //traducción de XML a Json
        JSONObject jsonObject = XML.toJSONObject(detalle);
        return jsonObject.toString();

    }

    public Libro obtenerDetalle2(int idLibro) throws JsonProcessingException {
        String detalleXML = biblioteca.obtenerInfo(idLibro);
        return xmlMapper.readValue(detalleXML,Libro.class);

    }
}
