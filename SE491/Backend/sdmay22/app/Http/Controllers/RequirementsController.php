<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Requirements;

class RequirementsController extends Controller
{
    public function all(){
        return Requirements::all();
    }

    public function new(){
        $reqs = new Requirements;
        $reqs->pid = $_GET['pid'];
        $reqs->Java = $_GET['java'];
        $reqs->Laravel = $_GET['laravel'];
        $reqs->SQL = $_GET['sql'];
        $reqs->PHP = $_GET['php'];
        $reqs->Algorithm = $_GET['algorithm'];
        $reqs->save();
        return $reqs;
    }

    //Returns the requirements of the specified project
    public function requirements(){
        return Requirements::where('pid', '=', $_GET['pid'])->first();
    }

    public function update(){
        $reqs = Requirements::where('pid', '=', $_GET['pid'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'netid')){
            $reqs->net_id = $results['netid'];
        }
        if(strpos($url, 'java')){
            $reqs->Java = $results['java'];
        }
        if(strpos($url, 'laravel')){
            $reqs->Laravel = $results['laravel'];
        }
        if(strpos($url, 'sql')){
            $reqs->SQL = $results['sql'];
        }
        if(strpos($url, 'php')){
            $reqs->PHP = $results['php'];
        }
        if(strpos($url, 'algorithm')){
            $reqs->Algorithm = $results['algorithm'];
        }
        $reqs->save();
        return $reqs;
    }

    public function delete(){
        return Requirements::where('pid', '=', $_GET['pid'])->delete();
    }

}
