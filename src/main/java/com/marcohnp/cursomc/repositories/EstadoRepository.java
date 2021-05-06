package com.marcohnp.cursomc.repositories;

import com.marcohnp.cursomc.domain.Categoria;
import com.marcohnp.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
