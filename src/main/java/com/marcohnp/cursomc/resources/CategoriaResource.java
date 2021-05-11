package com.marcohnp.cursomc.resources;

import com.marcohnp.cursomc.domain.Categoria;
import com.marcohnp.cursomc.dto.CategoriaDTO;
import com.marcohnp.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value= "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Categoria> findById(@PathVariable Integer id){
        Categoria obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDTO = list.stream().map(CategoriaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = service.fromDTO(categoriaDTO);
        categoria = service.insert(categoria);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
        Categoria categoria = service.fromDTO(categoriaDTO);
        categoria.setId(id);
        categoria = service.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                       @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                       @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDTO = list.map(CategoriaDTO::new);
        return ResponseEntity.ok().body(listDTO);
    }



}
