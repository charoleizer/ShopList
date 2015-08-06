package br.edu.unoesc.shoplist.model;

/**
 * Created by roberson.alves on 29/07/2015.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "produto")
public class Produto extends BaseModel {
    @DatabaseField(generatedId = true)
    private Integer codigo;
    @DatabaseField
    private String descricao;
    @DatabaseField
    private String marca;
    @DatabaseField
    private Double quantidade;
    @DatabaseField
    private String unidadeMedida;

    public Produto() {
        // ORMLite precisa de um construtor sem par�metros
    }

    public Produto(String descricao, String marca, Double quantidade, String unidadeMedida) {
        this.descricao = descricao;
        this.marca = marca;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
    }



    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
