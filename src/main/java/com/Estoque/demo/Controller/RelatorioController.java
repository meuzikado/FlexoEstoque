package com.Estoque.demo.Controller;

import com.Estoque.demo.Model.RelatorioDiarioDTO;
import com.Estoque.demo.service.BobinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private BobinaService bobinaService;

    // Endpoint que o PCP ou a gerência vai acessar para extrair os dados do dia
    @GetMapping("/diario")
    public RelatorioDiarioDTO obterRelatorioDiario() {
        return bobinaService.gerarRelatorioDiario();
    }
}
