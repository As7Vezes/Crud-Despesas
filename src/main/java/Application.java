import dao.DespesaDAO;
import model.Categoria;
import model.Despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        DespesaDAO dao = new DespesaDAO();

        List<Despesa> despesas = dao.findCategoria(Categoria.TRANSPORTE);
        for (Despesa despesa : despesas) {
            System.out.println("ID: " + despesa.getId());
            System.out.println("Descrição: " + despesa.getDescricao());
            System.out.println("Categoria: " + despesa.getCategoria());
            System.out.println("Valor: " + despesa.getValor());
        }

    }

}
