package com.example.keepocket2.data.repository;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.keepocket2.data.APIResponse;
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

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
    private static final String TAG = "YourClassTag";
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
    public LiveData<List<Category>> getALL(){
        return this.categoryDAO.getALLCategories();
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


    public void updateCategory(Category category){
        executor.execute(() -> categoryDAO.updateCategory(category));
    }
    public void createUser(User user){

        User userOut = new User(user.getId(), user.getEmail(),user.getEmail()
                , user.getEmail_verified_at(),user.getPassword(), user.getRemember_token(), user.getCreated_at(),user.getUpdated_at());
        this.userService.createUser(userOut).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    refreshUser();
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
        this.userService.getUserList().enqueue(new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                if (response.isSuccessful()) {
                    APIResponse<User> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<User> userList = apiResponse.getData();
                        if (userList != null) {
                            Log.d(TAG, "Received " + userList.size() + " users");
                            executor.execute(() -> userDAO.createUser(userList));
                        } else {
                            Log.d(TAG, "Received null user list");
                        }
                    } else {
                        Log.d(TAG, "Received null API response");
                    }
                } else {
                    Log.d(TAG, "Response code: " + response.code());
                    //Toast.makeText(mContext, "Error fetching data: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "Error fetching data: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void refreshCategory() {
        this.categoryService.getCategoryList().enqueue(new Callback<APIResponse<Category>>() {
            @Override
            public void onResponse(Call<APIResponse<Category>> call, Response<APIResponse<Category>> response) {
                if (response.isSuccessful()) {
                    APIResponse<Category> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Category> categoryList = apiResponse.getData();
                        if (categoryList != null) {
                            Log.d(TAG, "Received " + categoryList.size() + " Categories");
                            executor.execute(() -> categoryDAO.createCategorys(categoryList));
                        } else {
                            Log.d(TAG, "Received null Categories list");
                        }
                    } else {
                        Log.d(TAG, "Received null API response");
                    }
                } else {
                    Log.d(TAG, "Response code: " + response.code());
                    //Toast.makeText(mContext, "Error fetching data: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<Category>> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "Error fetching data: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void refreshMovement() {
        this.movementService.getMovementList().enqueue(new Callback<APIResponse<Movement>>() {
            @Override
            public void onResponse(Call<APIResponse<Movement>> call, Response<APIResponse<Movement>> response) {
                if (response.isSuccessful()) {
                    APIResponse<Movement> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Movement> movementList = apiResponse.getData();
                        if (movementList != null) {
                            Log.d(TAG, "Received " + movementList.size() + " Categories");
                            executor.execute(() -> movementDAO.createOrUpdateMovements(movementList));
                        } else {
                            Log.d(TAG, "Received null Categories list");
                        }
                    } else {
                        Log.d(TAG, "Received null API response");
                    }
                } else {
                    Log.d(TAG, "Response code: " + response.code());
                    //Toast.makeText(mContext, "Error fetching data: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<Movement>> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "Error fetching data: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

    public void createCategoryAPI(@NonNull Category category){
        Category categoryOut = new Category(category.getIdCategory(), category.getCategoryName()
                , category.getLimit(), category.getIdUser());

        // insert the new category using the API
        this.categoryService.createCategory(categoryOut).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    refreshCategory();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
    public void updateCategoryAPI(Category category) {
        this.categoryService.updateCategory(category.getIdCategory(), category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> categoryDAO.updateCategory(category));
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
    public void deleteCategory(long categoryId) {
        this.categoryService.deleteCategory(categoryId).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    Category category = categoryDAO.getById(categoryId);
                    executor.execute(() -> categoryDAO.delete(category));
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void createMovementAPI(Movement movement){

        Movement movementOut = new Movement(movement.getIdMovement(),String.valueOf(movement.getIdUser()),String.valueOf(movement.getIdCategory()),String.valueOf(movement.getValue()),movement.getDescription(),movement.getMovementsDate());
        this.movementService.createMovement(movementOut).enqueue(new Callback<Movement>() {
            @Override
            public void onResponse(Call<Movement> call, Response<Movement> response) {
                if (response.isSuccessful()) {
                    refreshMovement();
                }
            }

            @Override
            public void onFailure(Call<Movement> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
    public void updateExpenseAPI(Movement movement) {
        this.movementService.updateMovement(movement, movement.getIdMovement()).enqueue(new Callback<Movement>() {
            @Override
            public void onResponse(Call<Movement> call, Response<Movement> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> movementDAO.update(movement));
                }
            }

            @Override
            public void onFailure(Call<Movement> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
    public void updateIncomeAPI(Movement movement) {
        this.movementService.updateMovement(movement, movement.getIdMovement()).enqueue(new Callback<Movement>() {
            @Override
            public void onResponse(Call<Movement> call, Response<Movement> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> movementDAO.update(movement));
                }
            }

            @Override
            public void onFailure(Call<Movement> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
    public void deleteMovement(long movementid) {
        this.movementService.deleteMovements(movementid).enqueue(new Callback<Movement>() {
            @Override
            public void onResponse(Call<Movement> call, Response<Movement> response) {
                if (response.isSuccessful()) {
                    Movement movement = movementDAO.getById(movementid);
                    executor.execute(() -> movementDAO.delete(movement));
                }
            }

            @Override
            public void onFailure(Call<Movement> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void updateLimitAPI(Category category) {
        this.categoryService.updateLimit(category.getIdCategory(), category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> categoryDAO.updateCategory(category));
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }
}
