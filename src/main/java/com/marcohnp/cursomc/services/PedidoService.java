package com.marcohnp.cursomc.services;

import com.marcohnp.cursomc.domain.ItemPedido;
import com.marcohnp.cursomc.domain.PagamentoComBoleto;
import com.marcohnp.cursomc.domain.Pedido;
import com.marcohnp.cursomc.domain.enums.EstadoPagamento;
import com.marcohnp.cursomc.repositories.ItemPedidoRepository;
import com.marcohnp.cursomc.repositories.PagamentoRepository;
import com.marcohnp.cursomc.repositories.PedidoRepository;
import com.marcohnp.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido findById(Integer id){
        Optional<Pedido> objeto = pedidoRepository.findById(id);
        return objeto.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for (ItemPedido ip : pedido.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.findById(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationHtmlEmail(pedido);
        return pedido;
    }
}
