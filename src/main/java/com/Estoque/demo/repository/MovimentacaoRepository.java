package com.Estoque.demo.repository;

import com.Estoque.demo.Model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    // O Spring gera o SQL: SELECT * FROM tb_movimentacao WHERE data_retirada BETWEEN ? AND ?
    List<MovimentacaoEstoque> findByDataRetiradaBetween(LocalDateTime inicio, LocalDateTime fim);
}