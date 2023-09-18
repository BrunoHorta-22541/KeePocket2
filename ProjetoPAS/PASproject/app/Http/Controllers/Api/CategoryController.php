<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\StoreCategoryRequest;
use App\Http\Resources\CategoryResource;
use App\Models\Category;
use Illuminate\Http\Request;

class CategoryController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $category = Category::all();
        return CategoryResource::collection($category);
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
        
        $category = Category::create($request->all());
        return new CategoryResource($category);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($idUser)
    {
        $category = Category::where('idUser', (string) $idUser)->get();
        return CategoryResource::collection($category);
    
    }
/*
    public function showCategories($idUser) {
        
        $categories = Category::where('idUser', $idUser)->get();
        return view('table', compact('categories'));
      }
*/
    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $category = Category::findOrFail($id);
        return view('edit', compact('category'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Models\Category  $category
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {   
        $category = Category::find($id);
        $category->update([
            'categoryName' => $request->input('categoryName'),
            'limit' => $request->input('limit'),
        ]);
        
        return response()->json(['success' => true, 'message' => 'Category updated successfully'], 200);
    }
    public function updatelimit(Request $request, $id)
    {   
        $category = Category::find($id);
        $category->limit = $request->limit;
        $category->update();
        
        return response()->json(['success' => true, 'message' => 'Limite updated successfully'], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Models\Category  $category
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $category = Category::find($id);
        $category->delete();

        return response()->json(['success' => true, 'message' => 'Category deleted successfully'], 200);
    }
}
