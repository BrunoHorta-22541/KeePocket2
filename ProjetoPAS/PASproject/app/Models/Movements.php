<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Movements extends Model
{
    use HasFactory;

    public $timestamps = false;

    protected $fillable = ['idUser', 'idCategory', 'value', 'description', 'movementsdate'];

    protected $dateFormat = 'Y-m-d H:i:s';

    protected $casts = [
        'movementsdate' => 'datetime:Y-m-d H:i:s',
    ];
}