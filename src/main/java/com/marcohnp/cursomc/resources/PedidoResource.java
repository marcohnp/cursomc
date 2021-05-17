package com.marcohnp.cursomc.resources;

import com.marcohnp.cursomc.domain.Categoria;
import com.marcohnp.cursomc.domain.Pedido;
import com.marcohnp.cursomc.dto.CategoriaDTO;
import com.marcohnp.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value= "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Pedido> findById(@PathVariable Integer id){
        Pedido obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {
        pedido = service.insert(pedido);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
