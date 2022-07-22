package dao;

import dto.UserFilter;
import entity.UsersEntity;
import exception.DaoException;
import util.ConnectionManager;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static java.util.stream.Collectors.*;

public class UsersDao {
    private static final UsersDao INSTANCE = new UsersDao();

    public UsersDao() {
    }

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id= ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO  users( nick_name, password, phone, is_root) 
            VALUES (?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE  users 
            SET nick_name=?,
            password=?,
            phone=?,
            is_root=?
            WHERE id=?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT  id,
            nick_name,
            password,
            phone,
            is_root 
            FROM users
                        
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
                        WHERE id=?
            """;

    public static UsersDao getInstance() {

        return INSTANCE;
    }

    public List<UsersEntity> findAll(UserFilter userFilter) {
        List<Object> parametres = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (userFilter.phone() != null) {
            whereSql.add("phone::text LIKE ?");
            parametres.add("%" + userFilter.phone().toString() + "%");
        }
        if (userFilter.nick_name() != null) {
            whereSql.add("nick_name = ?");
            parametres.add(userFilter.nick_name());
        }
        var where = whereSql.stream().
                collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        parametres.add(userFilter.limit());
        parametres.add(userFilter.offset());
        System.out.println("--------");
        System.out.println(parametres);
        var sql = FIND_ALL_SQL + where;
        try {
            var connection = ConnectionManager.get();
            var prepareStatement = connection.prepareStatement(sql);
            for (int i = 0; i < parametres.size(); i++) {
                prepareStatement.setObject(i + 1, parametres.get(i));
            }
            System.out.println("--------");
            System.out.println(prepareStatement);
            var resultSet = prepareStatement.executeQuery();
            List<UsersEntity> usersEntityList = new ArrayList<>();
            while (resultSet.next()) {
                usersEntityList.add(buildUser(resultSet));
            }
            return usersEntityList;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<UsersEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = prepareStatement.executeQuery();
            List<UsersEntity> usersEntityList = new ArrayList<>();
            while (resultSet.next()) {
                usersEntityList.add(buildUser(resultSet));
            }
            return usersEntityList;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<UsersEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);
            var resultSet = prepareStatement.executeQuery();
            UsersEntity usersEntity = null;
            if (resultSet.next()) {
                usersEntity = buildUser(resultSet);
            }
            return Optional.ofNullable(usersEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void update(UsersEntity usersEntity) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, usersEntity.getNick_name());
            prepareStatement.setString(2, usersEntity.getPassword());
            prepareStatement.setLong(3, usersEntity.getPhone());
            prepareStatement.setBoolean(4, usersEntity.getIs_root());
            prepareStatement.setLong(5, usersEntity.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public UsersEntity save(UsersEntity usersEntity) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, usersEntity.getNick_name());
            prepareStatement.setString(2, usersEntity.getPassword());
            prepareStatement.setLong(3, usersEntity.getPhone());
            prepareStatement.setBoolean(4, usersEntity.getIs_root());
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                usersEntity.setId(generatedKeys.getLong("id"));
            }
            return usersEntity;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private UsersEntity buildUser(ResultSet resultSet) throws SQLException {
        return new UsersEntity(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getString("password"),
                resultSet.getLong("phone"),
                resultSet.getBoolean("is_root")
        );
    }

}
