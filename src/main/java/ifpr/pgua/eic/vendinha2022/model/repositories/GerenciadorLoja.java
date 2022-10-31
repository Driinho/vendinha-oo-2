package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class GerenciadorLoja {

    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Venda> vendas;
    private Venda venda;

    private FabricaConexoes fabricaConexoes;

    public GerenciadorLoja(FabricaConexoes fabricaConexoes) {
        clientes = new ArrayList<>();
        produtos = new ArrayList<>();
        vendas = new ArrayList<>();

        this.fabricaConexoes = fabricaConexoes;
    }

    public void geraFakes() {
        clientes.add(new Cliente("Zé", "000.111.222.333-44", "ze@teste.com", "123-4567"));
        clientes.add(new Cliente("Maria", "111.111.222.333-44", "maria@teste.com", "123-4567"));
        clientes.add(new Cliente("Chico", "222.111.222.333-44", "chico@teste.com", "123-4567"));

    }

    public Venda getVendaAtual() {
        return venda;
    }

    public Result iniciarVenda(Cliente cliente) {
        if (venda != null) {
            return Result.fail("Não foi possível iniciar uma nova venda, já existe uma inicida!");
        }

        venda = new Venda(cliente, LocalDateTime.now());

        return Result.success("Venda iniciada!");
    }

    public Result adicionarProdutoVenda(Produto produto, double quantidade) {

        if (venda == null) {
            return Result.fail("Venda não iniciada!");
        }

        venda.adicionarProduto(produto, quantidade);

        return Result.success("Produto adicionado!");
    }

    public Result removerProdutoVenda(Produto produto, double quantidade) {
        if (venda == null) {
            return Result.fail("Venda não iniciada!");
        }

        if (venda.removerProduto(produto, quantidade)) {
            return Result.success("Quantidade removida!");
        }

        return Result.fail("Produto não encontrado!");
    }

    public Result inserirDescontoVenda(double desconto) {
        if (venda == null) {
            return Result.fail("Venda não iniciada!");
        }

        venda.setDesconto(desconto);
        return Result.success("Desconto registrado!");
    }

    public Result finalizarVenda() {
        if (venda == null) {
            return Result.fail("Venda não iniciada!");
        }

        this.vendas.add(venda);
        venda = null;

        return Result.success("Venda finalizada com sucesso!");

    }

    public List<Venda> getVendas() {
        return Collections.unmodifiableList(vendas);
    }

}
