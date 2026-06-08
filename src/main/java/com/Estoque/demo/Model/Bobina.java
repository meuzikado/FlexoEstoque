package com.Estoque.demo.Model;

import jakarta.persistence. *;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity // Diz que essa classe é uma tabela do banco de dados
@Table(name = "tb_bobina") // Define o nome da tabela no banco
public class Bobina {
    
    @Id // Define que o ID será a Chave Primária (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco vai gerar o ID automaticamente (1, 2, 3...)
    private Long id;

    @Column(name = "tipo_material", nullable = false) // Customiza a coluna no banco
    private String tipoMaterial;

    private double largura;
    private double gramatura;
    
    @Column(name = "peso_atual")
    private double pesoAtual;
    
    @Column(name = "metragem_linear")
    private double metragemLinear;
    
    @Column(name = "numero_lote")
    private String numeroLote;

    // Construtor sem argumentos (necessário para o Spring converter o JSON)
    public Bobina() {
    }

    // Construtor completo
    public Bobina(Long id, String tipoMaterial, double largura, double gramatura, double pesoAtual, double metragemLinear, String numeroLote) {
        this.id = id;
        this.tipoMaterial = tipoMaterial;
        this.largura = largura;
        this.gramatura = gramatura;
        this.pesoAtual = pesoAtual;
        this.metragemLinear = metragemLinear;
        this.numeroLote = numeroLote;
    }

    // GETTERS E SETTERS (O Spring usa para ler e escrever os dados)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoMaterial() { return tipoMaterial; }
    public void setTipoMaterial(String tipoMaterial) { this.tipoMaterial = tipoMaterial; }

    public double getLargura() { return largura; }
    public void setLargura(double largura) { this.largura = largura; }

    public double getGramatura() { return gramatura; }
    public void setGramatura(double gramatura) { this.gramatura = gramatura; }

    public double getPesoAtual() { return pesoAtual; }
    public void setPesoAtual(double pesoAtual) { this.pesoAtual = pesoAtual; }

    public double getMetragemLinear() { return metragemLinear; }
    public void setMetragemLinear(double metragemLinear) { this.metragemLinear = metragemLinear; }

    public String getNumeroLote() { return numeroLote; }
    public void setNumeroLote(String numeroLote) { this.numeroLote = numeroLote; }
}