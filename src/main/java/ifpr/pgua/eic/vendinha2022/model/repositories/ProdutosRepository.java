package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.vendinha2022.model.daos.ProdutoDAO;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class ProdutosRepository {
    
    private List<Produto> produtos;
    private ProdutoDAO dao;

    public ProdutosRepository(ProdutoDAO dao) {
        this.dao = dao;
    }

    public Result adicionarProduto(String nome, String descricao, Double valor, Double quantidadeEstoque) {
        Optional<Produto> busca = produtos.stream().filter((pro)->pro.getNome().equals(nome)).findFirst();

        if(busca.isPresent()) {
            return Result.fail("Produto ja cadastrado");
        }

        Produto produto = new Produto(nome, descricao, valor, quantidadeEstoque);

        return dao.create(produto);
    }

    public List<Produto> getProdutos() {
        produtos = dao.listAll();
        return Collections.unmodifiableList(produtos);
    }
}
