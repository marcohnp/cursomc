package com.marcohnp.cursomc.services;

import com.marcohnp.cursomc.domain.Cidade;
import com.marcohnp.cursomc.domain.Cliente;
import com.marcohnp.cursomc.domain.Endereco;
import com.marcohnp.cursomc.domain.enums.TipoCliente;
import com.marcohnp.cursomc.dto.ClienteDTO;
import com.marcohnp.cursomc.dto.ClienteNewDTO;
import com.marcohnp.cursomc.repositories.ClienteRepository;
import com.marcohnp.cursomc.repositories.EnderecoRepository;
import com.marcohnp.cursomc.services.exceptions.DataIntegrityException;
import com.marcohnp.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> objeto = clienteRepository.findById(id);
        return objeto.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente update(Cliente cliente) {
        var clienteAtualizado = findById(cliente.getId());
        updateData(clienteAtualizado, cliente);
        return clienteRepository.save(clienteAtualizado);
    }

    private void updateData(Cliente clienteAtualizado, Cliente cliente) {
        clienteAtualizado.setNome(cliente.getNome());
        clienteAtualizado.setEmail(cliente.getEmail());
    }

    @Transient
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public void delete(Integer id) {
        findById(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas.");
        }
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        var cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
                TipoCliente.toEnum(objDto.getTipo()));
        var cid = new Cidade(objDto.getCidadeId(), null, null);
        var end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(),
                objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }
}
