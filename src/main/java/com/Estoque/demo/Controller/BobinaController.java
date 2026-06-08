package com.Estoque.demo.Controller;

import com.Estoque.demo.Model.Bobina; // Importando o modelo que criamos acima
import com.Estoque.demo.repository.BobinaRepository; // Importando o repositório que criamos acima
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bobinas")
public class BobinaController {

    @Autowired // O Spring gerencia e injeta o repositório de banco de dados aqui automaticamente
    private BobinaRepository bobinaRepository;

    // 1. LISTAR TODAS (Buscando direto do Banco)
    @GetMapping
    public List<Bobina> listarTodas() {
        return bobinaRepository.findAll();
    }

    // 2. CADASTRAR NOVA (Salvando no Banco)
    @PostMapping
    public Bobina cadastrar(@RequestBody Bobina novaBobina) {
        // Como o ID é gerado pelo banco, garantimos que comece nulo no insert
        novaBobina.setId(null); 
        return bobinaRepository.save(novaBobina);
    }

    // 3. ALTERAR BOBINA (Atualizando no Banco)
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @RequestBody Bobina dadosAtualizados) {
        Optional<Bobina> bobinaExistente = bobinaRepository.findById(id);
        
        if (bobinaExistente.isPresent()) {
            Bobina bobina = bobinaExistente.get();
            bobina.setTipoMaterial(dadosAtualizados.getTipoMaterial());
            bobina.setLargura(dadosAtualizados.getLargura());
            bobina.setGramatura(dadosAtualizados.getGramatura());
            bobina.setPesoAtual(dadosAtualizados.getPesoAtual());
            bobina.setMetragemLinear(dadosAtualizados.getMetragemLinear());
            bobina.setNumeroLote(dadosAtualizados.getNumeroLote());
            
            bobinaRepository.save(bobina); // O .save() atualiza se o ID já existir
            return "Bobina ID " + id + " atualizada no banco com sucesso!";
        }
        
        return "Bobina com ID " + id + " não encontrada.";
    }

    // 4. EXCLUIR BOBINA (Deletando do Banco)
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        if (bobinaRepository.existsById(id)) {
            bobinaRepository.deleteById(id);
            return "Bobina ID " + id + " removida do banco de dados.";
        }
        return "Bobina com ID " + id + " não encontrada.";
    }
}