package com.jornada.beyondthecodeapi.testesenha;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriadorDeSenhas {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//        String senhaCriptografada = bCryptPasswordEncoder.encode("12345");
//        System.out.println(senhaCriptografada);

        // 123 - $2a$10$EP9niJXpxHcNrKxCVAfm/Ol/7xlvl5qwRA7VDeGog20ncj77aPHBe
        // 123 - $2a$10$duVX8L8iXCstr2c.156W4.wbGP5hUb2bTrUyFZNGHhWeYmbRAzbUW
        // 12345 - $2a$10$t8it6MIZF8dAc1zVPAVOW.CsDnP9nwOYCyWZ4jdYRJ0wYouNyi3AW
        // gui-123 -> $2a$10$GcNGPiy1UHBC.atHVleYWuPsMo6.QBKB8uqcoH9hjCaX2BhZKIRaO

        boolean senhaCorreta = bCryptPasswordEncoder.matches("12345", "$2a$10$t8it6MIZF8dAc1zVPAVOW.CsDnP9nwOYCyWZ4jdYRJ0wYouNyi3AW");
        System.out.println(senhaCorreta);

    }
}
