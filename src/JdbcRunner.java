import util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static util.ConnectionManager.closePool;

public class JdbcRunner {


    public static void main(String[] args) throws SQLException {
//        Integer value = 4500;
//        List<Integer> result = getAnyThingFromDataBase(value);
//        System.out.println(result);
        try{
            var idFreeRooms = getIdFreeRooms(LocalDate.of(2022,07,10), LocalDate.now());

        System.out.println(idFreeRooms+"  id");
        int date= 2022;}
        finally {
            closePool();
        }
    }
    private static List<Long> getIdFreeRooms(LocalDate start, LocalDate finish) throws SQLException {
        String sql= """
                SELECT id
                FROM booking_room
                WHERE rental_date_start BETWEEN ? AND ?
                """;
        List<Long> result=new ArrayList<>();
        try(var connection = ConnectionManager.get();
        var prepareStatement = connection.prepareStatement(sql)){
            prepareStatement.setDate(1, Date.valueOf(start));
            prepareStatement.setDate(2, Date.valueOf(finish));
            System.out.println(prepareStatement);
               var  resultSet  = prepareStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(resultSet.getObject("id",Long.class));
                }
            }
            return result;

    }
    private static List<Integer> getAnyThingFromDataBase(Integer value) throws SQLException {

        String sql = """
                SELECT  hotel_id 
                FROM rooms 
                WHERE cost_room=?
                """;
        List<Integer> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();//подключение к БД
             var prepareStatement = connection.prepareStatement(sql)){
             prepareStatement.setLong(1, value) ;
            var resultSet  = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getObject(1,Integer.class));
            }


        }
        return result;
    }


}