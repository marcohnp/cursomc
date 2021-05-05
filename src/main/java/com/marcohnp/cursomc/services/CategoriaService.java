package com.marcohnp.cursomc.services;

import com.marcohnp.cursomc.domain.Categoria;
import com.marcohnp.cursomc.repositories.CategoriaRepository;
import com.marcohnp.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria findById(Integer id){
        Optional<Categoria> objeto = repository.findById(id);
        return objeto.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
}
