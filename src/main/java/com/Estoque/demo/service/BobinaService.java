package com.Estoque.demo.service;

import com.Estoque.demo.Model.Bobina;
import com.Estoque.demo.Model.MovimentacaoEstoque;
import com.Estoque.demo.repository.BobinaRepository;
import com.Estoque.demo.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BobinaService {

    @Autowired
    private BobinaRepository bobinaRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public MovimentacaoEstoque retirarMetragemParaOrdemServico(Long bobinaId, double metragemSolicitada, String numeroOs) {
        
        // 1. Verificar se o lote/bobina existe
        Bobina bobina = bobinaRepository.findById(bobinaId)
                .orElseThrow(() -> new RuntimeException("Erro: Bobina/Lote " + bobinaId + " não encontrado."));

        // 2. REGRA DE NEGÓCIO: Validar saldo em METROS
        if (bobina.getMetragemLinear() < metragemSolicitada) {
            throw new RuntimeException("Erro: Saldo de metragem insuficiente! O estoque possui apenas " 
                    + bobina.getMetragemLinear() + "m, mas a OS " + numeroOs + " solicita " + metragemSolicitada + "m.");
        }

        // 3. Regra de Negócio Inteligente: Calcular quantas bobinas físicas estão sendo levadas
        // Se a metragem linear atual representa o total do lote, estimamos o uso baseado na metragem informada.
        // Para o relatório ficar exato, dividimos a metragem total solicitada pelo tamanho padrão de 1 bobina deste lote.
        // Vamos assumir que o cadastro inicial da bobina representa o tamanho padrão dela (ex: 2000m).
        double tamanhoPadraoBobina = 2000.0; // Você pode tornar isso dinâmico depois se quiser
        int qtdBobinasUtilizadas = (int) Math.ceil(metragemSolicitada / tamanhoPadraoBobina);

        // 4. Atualizar a metragem linear restante no estoque
        double novaMetragem = bobina.getMetragemLinear() - metragemSolicitada;
        bobina.setMetragemLinear(novaMetragem);
        
        // Regra Proporcional Opcional: Se quiser reduzir o peso proporcionalmente aos metros rodados:
        if (metragemSolicitada > 0 && bobina.getMetragemLinear() > 0) {
            double proporcaoConsumo = metragemSolicitada / (bobina.getMetragemLinear() + metragemSolicitada);
            double pesoConsumido = bobina.getPesoAtual() * proporcaoConsumo;
            bobina.setPesoAtual(bobina.getPesoAtual() - pesoConsumido);
        }

        bobinaRepository.save(bobina);

        // 5. Salvar histórico da movimentação com os dados da OS
        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setBobina(bobina);
        movimentacao.setMetragemRetirada(metragemSolicitada);
        movimentacao.setQuantidadeBobinasUtilizadas(qtdBobinasUtilizadas);
        movimentacao.setNumeroOs(numeroOs);
        movimentacao.setDataRetirada(LocalDateTime.now());

        return movimentacaoRepository.save(movimentacao);
    }
}
