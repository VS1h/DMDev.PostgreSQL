package runner;

import dao.UsersDao;
import dto.UserFilter;
import entity.UsersEntity;


import static java.lang.Boolean.FALSE;

public class DaoRunner {
    public static void main(String[] args) {
        SaveTest();
        deleteTest(16L);
        updateUserTest();
        var userDao = UsersDao.getInstance().findAll();
        var userFilter = new UserFilter(10, 0, 959998269L, null);
        var userTest = UsersDao.getInstance().findAll(userFilter);



    }

    public static void SaveTest() {
        var usersDao = UsersDao.getInstance();
        var users = new UsersEntity();
        users.setNick_name("petya");
        users.setPassword("Mjrqwqq1SS");
        users.setPhone(89332482343L);
        users.setIs_root(FALSE);
        var saveUser = usersDao.save(users);
        System.out.println(saveUser);

    }

    public static void deleteTest(Long id) {
        var usersDao = UsersDao.getInstance();
        var deleteResult = usersDao.delete(id);
        System.out.println(deleteResult);
    }

    private static void updateUserTest() {
        var userDaoUpdate = UsersDao.getInstance();
        var maybeById = userDaoUpdate.findById(9L);
        System.out.println(maybeById);

        maybeById.ifPresent(usersEnt -> {
            usersEnt.setNick_name("JoeBidonDonDon");
            userDaoUpdate.update(usersEnt);
        });
        //почему такое исполнение не работает? NullPointerException, пишет что getPhone is null
//        if (maybeById.isPresent()) {
//            UsersEntity usersEntity = new UsersEntity();
//            usersEntity.setNick_name("JoeBidonDon");
//            userDaoUpdate.update(usersEntity);
//        }
    }
}
