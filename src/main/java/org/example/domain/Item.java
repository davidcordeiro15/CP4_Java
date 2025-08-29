package org.example.domain;

import java.sql.Date;

public class Item {
    private int id;
    private String nome;
    private int quantidade;
    private Date dataEntrada;
    private String nomeUsuarioRetirada;
    private String nomeUsuarioEntrada;
    private Date dataRetirada;


    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public Date getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(Date dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getUsuarioRetirada() { return nomeUsuarioRetirada; }
    public void setUsuarioRetirada(String usuarioRetirada) { this.nomeUsuarioRetirada = usuarioRetirada; }

    public String getNomeUsuarioRetirada() {
        return nomeUsuarioRetirada;
    }

    public void setNomeUsuarioRetirada(String nomeUsuarioRetirada) {
        this.nomeUsuarioRetirada = nomeUsuarioRetirada;
    }

    public String getNomeUsuarioEntrada() {
        return nomeUsuarioEntrada;
    }

    public void setNomeUsuarioEntrada(String nomeUsuarioEntrada) {
        this.nomeUsuarioEntrada = nomeUsuarioEntrada;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }
}
