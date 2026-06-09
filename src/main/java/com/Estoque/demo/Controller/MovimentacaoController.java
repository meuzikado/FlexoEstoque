package com.Estoque.demo.Controller;

import com.Estoque.demo.Model.MovimentacaoEstoque;
import com.Estoque.demo.service.BobinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Classe auxiliar adaptada para metros e OS
class RequisicaoRetiradaMetros {
    public Long bobinaId;
    public double metragemRetirada;
    public String numeroOs;
}

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private BobinaService bobinaService;

    @PostMapping("/retirar")
    public ResponseEntity<?> registrarRetirada(@RequestBody RequisicaoRetiradaMetros requisicao) {
        try {
            MovimentacaoEstoque movimentacao = bobinaService.retirarMetragemParaOrdemServico(
                    requisicao.bobinaId, 
                    requisicao.metragemRetirada, 
                    requisicao.numeroOs
            );
            return ResponseEntity.ok(movimentacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
