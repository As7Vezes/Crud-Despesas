import dao.DespesaDAO;
import model.Categoria;
import model.Despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        DespesaDAO dao = new DespesaDAO();

        dao.delete(2L);

    }

}
