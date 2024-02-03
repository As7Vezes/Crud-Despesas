package dao;

import infra.ConnectionFactory;
import model.Categoria;
import model.Despesa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DespesaDAO implements IDespesaDAO {
    @Override
    public Despesa save(Despesa despesa) {
        String sql = "INSERT INTO despesas (descricao, valor, data, categoria)" +
                " VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, despesa.getDescricao());
            preparedStatement.setDouble(2, despesa.getValor());
            preparedStatement.setDate(3, java.sql.Date.valueOf(despesa.getData()));
            preparedStatement.setString(4, despesa.getCategoria().toString());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            Long generatedId = resultSet.getLong("id");
            despesa.setId(generatedId);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return despesa;
    }

    @Override
    public Despesa update(Despesa despesa) {

        try (Connection connection = ConnectionFactory.getConnection()) {

            String sql = "UPDATE despesas SET descricao = ?, valor = ?, data = ?, categoria = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, despesa.getDescricao());
            preparedStatement.setDouble(2, despesa.getValor());
            preparedStatement.setDate(3, java.sql.Date.valueOf(despesa.getData()));
            preparedStatement.setString(4, despesa.getCategoria().toString());
            preparedStatement.setLong(5, despesa.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return despesa;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {

            String sql = "DELETE FROM despesas WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<Despesa> findAll() {
        String sql = "SELECT id, descricao, data, valor, categoria FROM despesas";

        List<Despesa> despesas = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong( "id");
                String descricao = rs.getString( "descricao");
                LocalDate data = rs.getDate("data") != null ? rs.getDate("data").toLocalDate() : null;
                double valor = rs.getDouble("valor");
                Categoria categoria = Categoria.valueOf(rs.getString("categoria"));

                Despesa despesa = new Despesa(id, descricao, data, valor, categoria);
                despesas.add(despesa);
            }

        } catch (SQLException throwables){
            throw new RuntimeException();
        }
        return despesas;
    }

    @Override
    public Optional<Despesa> findById(Long id) {

        String sql = "SELECT id, descricao, data, valor, categoria FROM despesas WHERE id = ?";

        Despesa despesa = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong( 1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long pKey = rs.getLong( "id");
                String descricao = rs.getString( "descricao");
                LocalDate data = rs.getDate("data") != null ? rs.getDate("data").toLocalDate() : null;
                double valor = rs.getDouble("valor");
                Categoria categoria = Categoria.valueOf(rs.getString("categoria"));

                despesa = new Despesa(pKey, descricao, data, valor, categoria);

            }

        } catch (SQLException throwables){
            throw new RuntimeException();
        }

        return Optional.ofNullable(despesa);
    }

    @Override
    public List<Despesa> findCategoria(Categoria categoria) {
        String sql = "SELECT id, descricao, data, valor, categoria FROM despesas WHERE categoria = ?";

        List<Despesa> despesas = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString( 1, categoria.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long pKey = rs.getLong( "id");
                String descricao = rs.getString( "descricao");
                LocalDate data = rs.getDate("data") != null ? rs.getDate("data").toLocalDate() : null;
                double valor = rs.getDouble("valor");
                Categoria cat = Categoria.valueOf(rs.getString("categoria"));

                Despesa despesa = new Despesa(pKey, descricao, data, valor, cat);
                despesas.add(despesa);

            }

        } catch (SQLException throwables){
            throw new RuntimeException();
        }

        return despesas;
    }
}
