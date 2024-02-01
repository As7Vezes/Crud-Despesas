import dao.DespesaDAO;
import model.Categoria;
import model.Despesa;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        DespesaDAO dao = new DespesaDAO();

        Despesa despesa = new Despesa();
        despesa.setDescricao("Pagamento do alugel");
        despesa.setCategoria(Categoria.MORADIA);
        despesa.setValor(1200);
        despesa.setData(LocalDate.of(2024, 1, 31));

        dao.save(despesa);

    }

}
