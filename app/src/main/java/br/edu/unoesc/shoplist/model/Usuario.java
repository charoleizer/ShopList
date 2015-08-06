package br.edu.unoesc.shoplist.model;

/**
 * Created by roberson.alves on 29/07/2015.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuario")
public class Usuario extends BaseModel {
    @DatabaseField(generatedId = true)
    private Integer codigo;
    @DatabaseField
    private String nome;
    @DatabaseField
    private String email;
    @DatabaseField
    private String latitude;
    @DatabaseField
    private String longitude;

    public Usuario() {
        // ORMLite precisa de um construtor sem par�metros
    }

    public Usuario(String nome, String email, String latitude, String longitude) {
        this.nome = nome;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
