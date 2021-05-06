package com.marcohnp.cursomc.repositories;

import com.marcohnp.cursomc.domain.Categoria;
import com.marcohnp.cursomc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
