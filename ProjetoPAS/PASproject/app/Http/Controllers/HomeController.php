<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;
use App\Models\Category;
use App\Models\Movements;
use Illuminate\Http\Request;
use App\Charts\MovementsChart;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Api\MovementsController;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Contracts\Support\Renderable
     */
    public function index()
    {
        $idUser = Auth::user()->id;

        $movementspositive = Movements::where('idUser', $idUser)
        ->where('value', '>', 0)
        ->get();

        $sumpositive = $movementspositive->sum('value');



        $movementsNegative = Movements::where('idUser', $idUser)
        ->where('value', '<', 0)
        ->get();

        $movementsNegative->map(function ($movement) {
            $movement->value = abs($movement->value);
            return $movement;
        });
        $sumnegative = $movementsNegative->sum('value');

        $data['pieChart'] = DB::table('movements')
        ->join('categories', 'movements.idCategory', '=', 'categories.id')
        ->select('categories.categoryName', DB::raw('SUM(value) as totalvalue'))
        ->where('movements.idUser', $idUser)
        ->where('value', '<', 0)
        ->groupBy('categories.categoryName')
        ->get()
        ->toArray();
        return view('auth.dashboard', [
            'sumnegative' => $sumnegative,
            'sumpositive' => $sumpositive,
            'pieChart' => $data['pieChart']
    ]);
    }

    public function categories()
    {
        $userId = Auth::user()->id;
        $category = Category::where('idUser', (string) $userId )->get();
        return view('auth.categories', compact('category'));
    }
    public function expenses()
    {
        $movementsController = new MovementsController();
        $userId = Auth::user()->id;
        $expense = $movementsController->shownegative($userId);
        return view('auth.expenses', compact('expense'));
    }
    public function income()
    {
        $movementsController = new MovementsController();
        $userId = Auth::user()->id;
        $income = $movementsController->showpositive($userId);
        return view('auth.income', compact('income'));
    }
}
