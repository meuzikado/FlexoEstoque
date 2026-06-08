package com.Estoque.demo.repository;

import com.Estoque.demo.Model.Bobina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Avisa ao Spring que este cara mexe com banco de dados
public interface BobinaRepository extends JpaRepository<Bobina, Long> {
    // Não precisa digitar nada aqui dentro por enquanto! O JpaRepository faz tudo.
}
