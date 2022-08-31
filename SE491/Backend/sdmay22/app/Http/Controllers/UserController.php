<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\User;

class UserController extends Controller
{
    public function all(){
        return User::all();
    }

    public function user(){
        return User::where('id', '=', $_GET['id'])->first();
    }

    public function new(){
        $user = new User;
        $user->email = $_GET['email'];
        $user->name = $_GET['name'];
        $user->id = $_GET['id'];
        $user->password - $_GET['pass'];
        $user->save();
        return $user;
    }

    public function update(){
        $user = User::where('id', '=', $_GET['id'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'email')){
            $user->email = $results['email'];
        }
        if(strpos($url, 'name')){
            $user->name = $results['name'];
        }
        if(strpos($url, 'pass')){
            $user->pass = $results['pass'];
        }
        if(strpos($url, 'id')){
            $user->id = $results['id'];
        }
        $user->save();
        return $user;
    }

    public function delete(){
        return User::where('id', '=', $_GET['id'])->delete();
    }

}
