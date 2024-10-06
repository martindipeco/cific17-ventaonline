package modelo.servicio;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptaServicio {

    public String encriptaHash(String paraEncriptar)
    {
        try
        {
            //Crea una instancia de MessageDigest para SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //ejecuta el encriptamiento
            byte[] bytesEncriptados = md.digest(paraEncriptar.getBytes());

            //convertir los bytes encriptados a formato hexadecimal
            StringBuilder sb = new StringBuilder();
            for(byte b : bytesEncriptados)
            {
                sb.append(String.format("%02x", b));
                // % comienza la especificacion de formato.
                // 0 -> si tiene menos de 2 caracteres, poner 0 al comienzo
                // 2 -> mínimo 2 caracteres de salida
                // x -> formateado a hexadecimal (base 16 con caracteres a - f)
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error al intentar encriptar. ", e);
        }
    }

    public boolean verificarEncriptado(String passOriginal, String passEncriptado)
    {
        return encriptaHash(passOriginal).equals(passEncriptado);
    }
}
