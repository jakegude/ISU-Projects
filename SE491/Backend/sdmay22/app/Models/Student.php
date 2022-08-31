<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Student extends Model
{
    use HasFactory;

    // All fields added to the migration, should be added in this "fillable" array, to allow for the create() and update() functions in cthe controllers
    protected $fillable = ['name', 'email', 'major'];

    public function team()
    {
        return $this->belongsTo('App\Team');
    }

    protected $casts = [
        'preference' => 'array'
    ];
}
