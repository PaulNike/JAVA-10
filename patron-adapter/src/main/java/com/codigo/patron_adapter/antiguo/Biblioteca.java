package com.codigo.patron_adapter.antiguo;

public class Biblioteca {

    public String obtenerInfo(int idLibro){
        return "<libro>\n" +
                "   <id>"+idLibro +  "</id>\n" +
                "   <titulo>Maria</titulo>\n" +
                "   <genero>Terror</genero>\n" +
                "   <anio>1996</anio>\n" +
                "   <editorial>\n" +
                "      <nombre>Ruben Editorial</nombre>\n" +
                "      <ciudad>Colombia</ciudad>\n" +
                "      <direccion>123 Calle Ficticia</direccion>\n" +
                "      <contacto>\n" +
                "         <email>ruben.editorial@example.com</email>\n" +
                "         <telefono>1234567890</telefono>\n" +
                "      </contacto>\n" +
                "   </editorial>\n" +
                "   <autores>\n" +
                "      <autor>\n" +
                "         <nombre>Juan Perez</nombre>\n" +
                "         <nacionalidad>Española</nacionalidad>\n" +
                "      </autor>\n" +
                "      <autor>\n" +
                "         <nombre>Maria Gomez</nombre>\n" +
                "         <nacionalidad>Argentina</nacionalidad>\n" +
                "      </autor>\n" +
                "   </autores>\n" +
                "   <ubicacion>\n" +
                "      <sucursal>Principal</sucursal>\n" +
                "      <piso>3</piso>\n" +
                "      <estanteria>B4</estanteria>\n" +
                "   </ubicacion>\n" +
                "</libro>";
    }

    public String obtenerInfo2(int idLibro){
        return "<libro>\n" +
                "    <id>" + idLibro +"</id>\n" +
                "    <titulo>Maria</titulo>\n" +
                "    <editorial>Ruben Editorial</editorial>\n" +
                "    <anio>1996</anio>\n" +
                "    <ciudad>Colombia</ciudad>\n" +
                "    <direccion>123 Calle Ficticia, Ciudad Ejemplo</direccion>\n" +
                "    <email>ruben.editorial@example.com</email>\n" +
                "    <telefono>1234567890</telefono>\n" +
                "    <genero>Terror</genero>\n" +
                "    <isbn>xxxxx-xxxx-xxx</isbn>\n" +
                "    <nacionalidad>Española</nacionalidad>\n" +
                "</libro>\n";
    }
}
