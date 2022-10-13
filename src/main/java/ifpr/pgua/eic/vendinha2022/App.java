package ifpr.pgua.eic.vendinha2022;

import ifpr.pgua.eic.vendinha2022.controllers.TelaClientes;
import ifpr.pgua.eic.vendinha2022.controllers.TelaPrincipal;
import ifpr.pgua.eic.vendinha2022.controllers.TelaProdutos;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaProdutosViewModel;
import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.daos.ClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.JDBCClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.JDBCProdutoDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.ProdutoDAO;
import ifpr.pgua.eic.vendinha2022.model.repositories.ClientesRepository;
import ifpr.pgua.eic.vendinha2022.model.repositories.GerenciadorLoja;
import ifpr.pgua.eic.vendinha2022.model.repositories.ProdutosRepository;
import ifpr.pgua.eic.vendinha2022.utils.Navigator.BaseAppNavigator;
import ifpr.pgua.eic.vendinha2022.utils.Navigator.ScreenRegistryFXML;


/**
 * JavaFX App
 */
public class App extends BaseAppNavigator {

    private GerenciadorLoja gerenciador;
    private ClienteDAO clienteDao;
    private ClientesRepository clientesRepository;
    private ProdutoDAO produtoDAO;
    private ProdutosRepository produtosRepository;

    @Override
    public void init() throws Exception {
        super.init();
        gerenciador = new GerenciadorLoja(FabricaConexoes.getInstance());
        
        clienteDao = new JDBCClienteDAO(FabricaConexoes.getInstance());
        clientesRepository = new ClientesRepository(clienteDao);

        produtoDAO = new JDBCProdutoDAO(FabricaConexoes.getInstance());
        produtosRepository = new ProdutosRepository(produtoDAO);
    
    
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }



    @Override
    public String getHome() {
        return "PRINCIPAL";
    }

    @Override
    public String getAppTitle() {
        return "Vendinha";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(getClass(), "fxml/principal.fxml", (o)->new TelaPrincipal()));
        registraTela("CLIENTES", new ScreenRegistryFXML(getClass(), "fxml/clientes.fxml", (o)->new TelaClientes(new TelaClientesViewModel(clientesRepository))));  
        registraTela("PRODUTOS", new ScreenRegistryFXML(getClass(), "fxml/produtos.fxml", (o)->new TelaProdutos(new TelaProdutosViewModel(produtosRepository))));  
    
    }


}