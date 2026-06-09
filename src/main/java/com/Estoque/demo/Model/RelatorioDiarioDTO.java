package com.Estoque.demo.Model;

import java.time.LocalDate;
import java.util.List;

public class RelatorioDiarioDTO {
    private LocalDate dataRelatorio;
    private double metragemTotalGeral;
    private int totalBobinasUtilizadasGeral;
    private List<MovimentacaoEstoque> movimentacoesDoDia;

    // Construtor completo
    public RelatorioDiarioDTO(LocalDate dataRelatorio, double metragemTotalGeral, int totalBobinasUtilizadasGeral, List<MovimentacaoEstoque> movimentacoesDoDia) {
        this.dataRelatorio = dataRelatorio;
        this.metragemTotalGeral = metragemTotalGeral;
        this.totalBobinasUtilizadasGeral = totalBobinasUtilizadasGeral;
        this.movimentacoesDoDia = movimentacoesDoDia;
    }

    // Getters e Setters
    public LocalDate getDataRelatorio() { return dataRelatorio; }
    public void setDataRelatorio(LocalDate dataRelatorio) { this.dataRelatorio = dataRelatorio; }

    public double getMetragemTotalGeral() { return metragemTotalGeral; }
    public void setMetragemTotalGeral(double metragemTotalGeral) { this.metragemTotalGeral = metragemTotalGeral; }

    public int getTotalBobinasUtilizadasGeral() { return totalBobinasUtilizadasGeral; }
    public void setTotalBobinasUtilizadasGeral(int totalBobinasUtilizadasGeral) { this.totalBobinasUtilizadasGeral = totalBobinasUtilizadasGeral; }

    public List<MovimentacaoEstoque> getMovimentacoesDoDia() { return movimentacoesDoDia; }
    public void setMovimentacoesDoDia(List<MovimentacaoEstoque> movimentacoesDoDia) { this.movimentacoesDoDia = movimentacoesDoDia; }
}