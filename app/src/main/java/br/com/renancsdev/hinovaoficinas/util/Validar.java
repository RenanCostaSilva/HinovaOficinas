package br.com.renancsdev.hinovaoficinas.util;

import android.widget.EditText;

public class Validar {

    private String regexTelefone = "\\(?([0-9]{2})\\)? 3([0-9]{3}-[0-9]{4})";

    private String regex = "^[a-z0-9_.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?$";
    public Boolean seCampEdtitTextVazio(EditText edit){
        return edit.getText().toString().equals("");
    }


    // ------------ validação telefone ---------------- //
    public Boolean seTelefoneValido(String celular){
        return (celular.matches(regexTelefone));
    }

    public Boolean seTelefoneCompativel(String telefone){
        return seTelefoneValido(telefone);
    }


    // ------------ validação email ---------------- //
    public Boolean seEmailValido(String email){
        return (email.matches(regex) && seEmailCompativelPTBR(email));
    }

    private Boolean seEmailCompativelPTBR(String email){
        return (seGmailValido(email) || seOutlooklValido(email) || seYahooValido(email) || seHinovaValido(email));
    }



    private Boolean seGmailValido(String email){
        return email.contains("gmail") && !email.contains(".br") && !email.contains(".com.");
    }

    private boolean seOutlooklValido(String email){
        return email.contains("outlook") && !email.contains(".br") && !email.contains(".com.");
    }

    private boolean seYahooValido(String email){
        return email.contains("yahoo") && !email.contains(".br") && !email.contains(".com.");
    }

    private boolean seHinovaValido(String email){
        return email.contains("hinovamobile") && email.contains(".br") && !email.contains(".com.");
    }

}
