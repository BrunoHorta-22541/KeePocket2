<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\StoreMovementsRequest;
use App\Http\Resources\MovementsResource;
use App\Models\Movements;
use Illuminate\Http\Request;

class MovementsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $movements = Movements::all();
        return MovementsResource::collection($movements);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $movements = Movements::create($request->all());
        
        return new MovementsResource($movements);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Models\Movements  $movements
     * @return \Illuminate\Http\Response
     */
    public function show($idUser)
    {
        $movements = Movements::where('idUser', (string) $idUser)->get();
        return MovementsResource::collection($movements);
    }
    public function showpositive($idUserP)
    {
        $movements = Movements::join('categories', 'movements.idCategory', '=', 'categories.id')
            ->where('movements.idUser', $idUserP)
            ->where('movements.value', '>', 0)
            ->select('movements.*', 'categories.categoryName')
            ->get();
            
        return MovementsResource::collection($movements);
    }
    public function shownegative($idUser){
        $movements = Movements::join('categories', 'movements.idCategory', '=', 'categories.id')
            ->where('movements.idUser', $idUser)
            ->where('movements.value', '<', 0)
            ->select('movements.*', 'categories.categoryName')
            ->get();
            
        return MovementsResource::collection($movements);
    }
    public function sumpositive($idUser)
    {
        $movements = Movements::where('idUser', $idUser)
            ->where('value', '>', 0)
            ->get();

        $sum = $movements->sum('value');

        $data = [
            'movements' => $movements,
            'sum' => $sum,
        ];

        return new MovementsResource($data);
    }
    public function getPositiveSum($userId)
{
    $positiveSum = Movements::where('idUser', $userId)
                            ->where('value', '>', 0)
                            ->sum('value');

    return response()->json(['positive_sum' => $positiveSum]);
}
    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Models\Movements  $movements
     * @return \Illuminate\Http\Response
     */
    public function edit(Movements $movements)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Models\Movements  $movements
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {   
        $movement = Movements::find($id);
     
        $movement->update([
            'idUser' => $request->input('idUser'),
            'idCategory' => $request->input('idCategory'),
            'value' => $request->input('value'),
            'description' => $request->input('description'),
        ]);
        
        return response()->json(['success' => true, 'message' => 'Movement updated successfully'], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Models\Movements  $movements
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $movement = Movements::find($id);
        $movement->delete();

        return response()->json(['success' => true, 'message' => 'Movement deleted successfully'], 200);
    }
}
