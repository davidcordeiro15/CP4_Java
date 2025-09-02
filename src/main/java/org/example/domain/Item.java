package org.example.domain;

import java.sql.Date;

public class Item {
    private int id;
    private String nome;
    private int quantidade;
    private String descricao;
    private String categoria;
    private String localizacao;
    private Date dataEntrada;
    private Date dataRetirada;
    private String usuarioEntradaEmail; // ID do usuário que entrou o item
    private int usuarioRetiradaId; // ID do usuário que retirou o item
    private Usuario usuarioEntrada; // Objeto usuário (para joins)
    private Usuario usuarioRetirada; // Objeto usuário (para joins)

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public Date getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(Date dataEntrada) { this.dataEntrada = dataEntrada; }

    public Date getDataRetirada() { return dataRetirada; }
    public void setDataRetirada(Date dataRetirada) { this.dataRetirada = dataRetirada; }

    public String getUsuarioEntradaEmail() { return usuarioEntradaEmail; }
    public void setUsuarioEntradaEmail(String usuarioEntradaId) { this.usuarioEntradaEmail = usuarioEntradaId; }

    public int getUsuarioRetiradaId() { return usuarioRetiradaId; }
    public void setUsuarioRetiradaId(int usuarioRetiradaId) { this.usuarioRetiradaId = usuarioRetiradaId; }

    public Usuario getUsuarioEntrada() { return usuarioEntrada; }
    public void setUsuarioEntrada(Usuario usuarioEntrada) { this.usuarioEntrada = usuarioEntrada; }

    public Usuario getUsuarioRetirada() { return usuarioRetirada; }
    public void setUsuarioRetirada(Usuario usuarioRetirada) { this.usuarioRetirada = usuarioRetirada; }
}