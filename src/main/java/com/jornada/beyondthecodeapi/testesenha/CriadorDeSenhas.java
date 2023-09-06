package com.jornada.beyondthecodeapi.testesenha;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriadorDeSenhas {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

/*        String senhaCriptografada = bCryptPasswordEncoder.encode("gui-123");
        System.out.println(senhaCriptografada);*/

        // $2a$10$EP9niJXpxHcNrKxCVAfm/Ol/7xlvl5qwRA7VDeGog20ncj77aPHBe
        // $2a$10$duVX8L8iXCstr2c.156W4.wbGP5hUb2bTrUyFZNGHhWeYmbRAzbUW

        // gui-123 -> $2a$10$GcNGPiy1UHBC.atHVleYWuPsMo6.QBKB8uqcoH9hjCaX2BhZKIRaO

        boolean senhaCorreta = bCryptPasswordEncoder.matches("gui-123", "$2a$10$ZxKQbX09esGCfH27NxGynugCUSbywJLCyK9DgQv.PFLSsWsxiC9JO");
        System.out.println(senhaCorreta);

    }
}
