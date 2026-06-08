package com.Estoque.demo.Model;

public class Bobina {
    private Long id;
    private String tipoMaterial;   // Ex: BOPP Transparente, PEBD, Couchê
    private double largura;        // Em milímetros (ex: 400.0)
    private double gramatura;      // Em micras ou g/m²
    private double pesoAtual;      // Em KG (crucial para o controle de saldo)
    private double metragemLinear; // Em metros
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