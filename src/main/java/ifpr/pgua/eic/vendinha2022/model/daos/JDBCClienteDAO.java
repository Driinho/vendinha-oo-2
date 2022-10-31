package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCClienteDAO implements ClienteDAO {

    private static final String INSERT = "INSERT INTO oo_clientes(nome,cpf,email,telefone) VALUES (?,?,?,?);";
    private static final String LIST_ALL = "SELECT * FROM oo_clientes;";
    private static final String SELECT_BY_ID = "SELECT * FROM oo_clientes WHERE id=?;";

    private FabricaConexoes fabricaConexoes;

    public JDBCClienteDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public Result create(Cliente cliente) {
        try {
            // criando uma conexão
            Connection con = fabricaConexoes.getConnection();

            // preparando o comando sql
            PreparedStatement pstm = con.prepareStatement(INSERT);

            // ajustando os parâmetros do comando
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setString(3, cliente.getEmail());
            pstm.setString(4, cliente.getTelefone());

            pstm.execute();

            pstm.close();
            con.close();
            return Result.success("Cliente criado com sucesso!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }

    }

    @Override
    public Result update(int id, Cliente cliente) {
        return null;
    }

    @Override
    public List<Cliente> listAll() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            // criando uma conexão
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(LIST_ALL);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                clientes.add(buildObject(rs));
            }

            pstm.close();
            con.close();

            return clientes;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Cliente getById(int id) {
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

    public Cliente buildObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            String cpf = resultSet.getString("cpf");
            String email = resultSet.getString("email");
            String telefone = resultSet.getString("telefone");

            return new Cliente(id, nome, cpf, email, telefone);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
