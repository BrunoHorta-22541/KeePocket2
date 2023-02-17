package com.example.keepocket2.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.CategoryDAO;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.data.localDatabase.MovementDAO;
import com.example.keepocket2.data.localDatabase.UserDAO;
import com.example.keepocket2.data.service.CategoryService;
import com.example.keepocket2.data.service.MovementService;
import com.example.keepocket2.data.service.UserService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private CategoryDAO categoryDAO;
    private MovementDAO movementDAO;
    private UserDAO userDAO;
    private CategoryService categoryService;
    private MovementService movementService;
    private UserService userService;
    private User user;
    private LiveData<List<User>> userList;
    public Repository(Context context){
        this.categoryDAO = Database.getInstance(context).getcategoryDAO();
        this.movementDAO = Database.getInstance(context).getmovementsDAO();
        this.userDAO = Database.getInstance(context).getuserDAO();
        this.categoryService = retrofit.create(CategoryService.class);
        this.movementService = retrofit.create(MovementService.class);
        this.userService = retrofit.create(UserService.class);
    }
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<Category>> getCategoryFromId(long userId){
       return this.categoryDAO.getUserCategories(userId);
    }
    public LiveData<List<Category>> getCategoryLimitFromId(long userId){
        return this.categoryDAO.getUserCategoryLimit(userId);
    }
    public Category getCategoryFromCategoryId(long categoryId){
        return this.categoryDAO.getById(categoryId);
    }

    public LiveData<List<Movement>> getExpenseFromId(long userId){
        return this.movementDAO.getExpense(userId);
    }

    public LiveData<List<Movement>> getIncomeFromId(long userId){
        return this.movementDAO.getIncome(userId);
    }

    public void createCategory(Category category){
     executor.execute(() -> categoryDAO.insertCategory(category));
    }
    public void updateCategory(Category category){
        executor.execute(() -> categoryDAO.updateCategory(category));
    }
    public void createUser(User user){

        User userOut = new User(user.getId(), user.getEmail(),user.getEmail()
                , user.getEmailver(),user.getPassword(), user.getRemeber(), user.getCreated(),user.getUpdated());
        this.userService.createUser(userOut).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    executor.execute(() -> userDAO.insertUser(user));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
    public void refreshUser() {

        this.userService.getUserList().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    executor.execute(() -> userDAO.createUser(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
    public User getUserByEmail(String email){
        new Thread(new Runnable() {
            @Override
            public void run() {
                user = userDAO.getByEmail(email);

            }

        });
        return user;
    }
    public LiveData<List<User>> listAllUsers(){
        return userDAO.getAll();
    }
    public long userCreated(User user){
        executor.execute(() -> userDAO.insertedUser(user));
        return userDAO.insertedUser(user);
    }
    public void updateUser(User user){
        executor.execute(() -> userDAO.updateUser(user));
    }

    public void createCategoryAPI(Category category){
        Category categoryOut = new Category(category.getIdCategory(), category.getCategoryName()
                , category.getLimit(), category.getIdUser());
        this.categoryService.createCategory(categoryOut).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> categoryDAO.insertCategory(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
    public void refreshCategory() {

        this.categoryService.getCategoryList().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    executor.execute(() -> categoryDAO.createCategorys(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    public void refreshMovement(){
        this.movementService.getMovementList().enqueue(new Callback<List<Movement>>() {
            @Override
            public void onResponse(Call<List<Movement>> call, Response<List<Movement>> response) {
                if(response.isSuccessful()){
                    executor.execute(() -> movementDAO.createMovements(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Movement>> call, Throwable t) {

            }
        });
    }
}
