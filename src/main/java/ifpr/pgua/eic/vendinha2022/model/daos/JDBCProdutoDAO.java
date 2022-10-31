package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCProdutoDAO implements ProdutoDAO {

    private static final String INSERT = "INSERT INTO oo_produtos(nome,descricao,valor,quantidadeEstoque) VALUES(?,?,?,?);";
    private static final String LIST_ALL = "SELECT * FROM oo_produtos;";
    private static final String SELECT_BY_ID = "SELECT * FROM oo_produtor WHERE id=?;";

    private FabricaConexoes fabricaConexoes;

    public JDBCProdutoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public Result create(Produto produto) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT);
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getDescricao());
            pstm.setDouble(3, produto.getValor());
            pstm.setDouble(4, produto.getQuantidadeEstoque());

            pstm.execute();

            pstm.close();
            con.close();
            return Result.success("Produto criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result update(int id, Produto produto) {
        return null;
    }

    @Override
    public List<Produto> listAll() {
        ArrayList<Produto> produtos = new ArrayList<>();
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(LIST_ALL);

            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                produtos.add(buildObject(resultSet));
            }

            pstm.close();
            con.close();

            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Produto getById(int id) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement statement = con.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            statement.close();
            con.close();

            while (resultSet.next()) {
                return buildObject(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result delete(int id) {
        return null;
    }

    public Produto buildObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            String descricao = resultSet.getString("descricao");
            Double valor = resultSet.getDouble("valor");
            Double quantidadeEstoque = resultSet.getDouble("quantidadeEstoque");
            return new Produto(id, nome, descricao, valor, quantidadeEstoque);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
