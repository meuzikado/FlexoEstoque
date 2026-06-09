package com.Estoque.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movimentacao")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_retirada", nullable = false)
    private LocalDateTime dataRetirada;

    @Column(name = "metragem_retirada", nullable = false)
    private double metragemRetirada; // Ex: 22000 metros

    @Column(name = "quantidade_bobinas_utilizadas", nullable = false)
    private int quantidadeBobinasUtilizadas; // Ex: 11 bobinas

    @Column(name = "numero_os", nullable = false)
    private String numeroOs; // Mudamos de OP para o termo que você usa: OS (Ordem de Serviço)

    @ManyToOne
    @JoinColumn(name = "bobina_id", nullable = false)
    private Bobina bobina;

    // Construtores
    public MovimentacaoEstoque() {}

    public MovimentacaoEstoque(Long id, LocalDateTime dataRetirada, double metragemRetirada, int quantidadeBobinasUtilizadas, String numeroOs, Bobina bobina) {
        this.id = id;
        this.dataRetirada = dataRetirada;
        this.metragemRetirada = metragemRetirada;
        this.quantidadeBobinasUtilizadas = quantidadeBobinasUtilizadas;
        this.numeroOs = numeroOs;
        this.bobina = bobina;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataRetirada() { return dataRetirada; }
    public void setDataRetirada(LocalDateTime dataRetirada) { this.dataRetirada = dataRetirada; }

    public double getMetragemRetirada() { return metragemRetirada; }
    public void setMetragemRetirada(double metragemRetirada) { this.metragemRetirada = metragemRetirada; }

    public int getQuantidadeBobinasUtilizadas() { return quantidadeBobinasUtilizadas; }
    public void setQuantidadeBobinasUtilizadas(int quantidadeBobinasUtilizadas) { this.quantidadeBobinasUtilizadas = quantidadeBobinasUtilizadas; }

    public String getNumeroOs() { return numeroOs; }
    public void setNumeroOs(String numeroOs) { this.numeroOs = numeroOs; }

    public Bobina getBobina() { return bobina; }
    public void setBobina(Bobina bobina) { this.bobina = bobina; }
}