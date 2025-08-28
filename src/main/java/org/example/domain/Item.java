package org.example.domain;

import java.sql.Date;

public class Item {
    private int id;
    private String nome;
    private int quantidade;
    private Date dataEntrada;
    private String usuarioRetirada;

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public Date getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(Date dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getUsuarioRetirada() { return usuarioRetirada; }
    public void setUsuarioRetirada(String usuarioRetirada) { this.usuarioRetirada = usuarioRetirada; }
}
