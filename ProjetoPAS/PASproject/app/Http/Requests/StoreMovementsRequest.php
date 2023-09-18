<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreMovementsRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return false;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'idUser'=>['required'],
            'idCategory'=>['required'],
            'value' => ['required', 'max:30'],
            'description'  => ['required'],
            'movementsDate' => ['required', 'max:100']

    
        ];
       
    }
}
