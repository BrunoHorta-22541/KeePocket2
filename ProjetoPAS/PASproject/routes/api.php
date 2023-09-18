<?php
use App\Http\Models\Category;
use App\Http\Models\Movements;
use App\Http\Controllers\Api\CategoryController;
use App\Http\Controllers\Api\MovementsController;
use App\Http\Controllers\Api\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
Route::apiResource('categories',CategoryController::class);
Route::apiResource('users',UserController::class);
Route::apiResource('movements',MovementsController::class);
Route::post('categoryupdate/{id}', [CategoryController::class, 'update'])->name('update');
Route::delete('categorydelete/{id}', [CategoryController::class, 'destroy'])->name('destroy');
Route::post('limitupdate/{id}', [CategoryController::class, 'updatelimit'])->name('updatelimit');
Route::post('movementupdate/{id}', [MovementsController::class, 'update'])->name('update');
Route::delete('movementdelete/{id}', [MovementsController::class, 'destroy'])->name('destroy');
//Route::apiResource('categories/{idUser}',CategoryController::class);
Route::get('/movements/positive/{id}', [MovementsController::class, 'showpositive'])->name('movements.positive');
Route::get('/movements/negative/{id}', [MovementsController::class, 'shownegative'])->name('movements.negative');
Route::get('/movements/positive-sum/{id}', [MovementsController::class, 'getPositiveSum'])->name('movements.negative');
