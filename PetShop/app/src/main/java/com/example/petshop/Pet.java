package com.example.petshop;

import java.io.Serializable;

public class Pet implements Serializable {
    private char tipo;
    private String raca, nome;
    private int idade;
    private boolean vacinado;

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isVacinado() {
        return vacinado;
    }

    public void setVacinado(boolean vacinado) {
        this.vacinado = vacinado;
    }

    public String toString() {
        return tipo + " - " + nome + " - " + raca + " - " + idade;
    }
}
