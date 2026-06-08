package com.Estoque.demo.Controller;

import com.Estoque.demo.Model.Bobina; // Importando o modelo que criamos acima
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bobinas")
public class BobinaController {

    // Nossa lista simulando o estoque em memória
    private List<Bobina> estoqueBobinas = new ArrayList<>();

    // Construtor que já inicia o sistema com duas bobinas de teste
    public BobinaController() {
        estoqueBobinas.add(new Bobina(1L, "BOPP Transparente", 400.0, 20.0, 150.5, 5000.0, "LOTE-A20"));
        estoqueBobinas.add(new Bobina(2L, "Papel Couchê", 600.0, 80.0, 210.0, 3000.0, "LOTE-B80"));
    }

    // 1. LISTAR TODAS (GET)
    @GetMapping
    public List<Bobina> listarTodas() {
        return estoqueBobinas;
    }

    // 2. CADASTRAR NOVA (POST)
    @PostMapping
    public String cadastrar(@RequestBody Bobina novaBobina) {
        estoqueBobinas.add(novaBobina);
        return "Bobina de " + novaBobina.getTipoMaterial() + " (Lote: " + novaBobina.getNumeroLote() + ") cadastrada com sucesso!";
    }

    // 3. ALTERAR BOBINA (PUT)
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @RequestBody Bobina dadosAtualizados) {
        for (Bobina b : estoqueBobinas) {
            if (b.getId().equals(id)) {
                b.setTipoMaterial(dadosAtualizados.getTipoMaterial());
                b.setLargura(dadosAtualizados.getLargura());
                b.setGramatura(dadosAtualizados.getGramatura());
                b.setPesoAtual(dadosAtualizados.getPesoAtual());
                b.setMetragemLinear(dadosAtualizados.getMetragemLinear());
                b.setNumeroLote(dadosAtualizados.getNumeroLote());
                return "Bobina ID " + id + " atualizada com sucesso!";
            }
        }
        return "Bobina com ID " + id + " não encontrada.";
    }

    // 4. EXCLUIR BOBINA (DELETE)
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        boolean removeu = estoqueBobinas.removeIf(b -> b.getId().equals(id));
        if (removeu) {
            return "Bobina ID " + id + " removida do estoque.";
        }
        return "Bobina com ID " + id + " não encontrada.";
    }
}