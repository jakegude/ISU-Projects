<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Faculty;

class FacultyController extends Controller
{
    public function all(){
        return Faculty::all();
    }

    public function faculty(){
        return Faculty::where('fid', '=', $_GET['fid'])->first();
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function panel(){
        $fac = Faculty::where('fid', '=', $_GET['fid'])->first();
        return $fac->panel;
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function panelbyname(){
        $fac = Faculty::where('name', '=', $_GET['name'])->first();
        return $fac->panel;
    }

    public function new(){
        $fac = new Faculty;
        $fac->fid = $_GET['fid'];
        $fac->name = $_GET['name'];
        //Need these in DB before un-commenting them
        //$fac->panel = $_GET['panel'];
        $fac->save();
        return $fac;
    }

    public function update(){
        $fac = Faculty::where('fid', '=', $_GET['fid'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'fid')){
            $fac->email = $results['fid'];
        }
        if(strpos($url, 'name')){
            $fac->name = $results['name'];
        }
        //Need these in the DB before un-commenting them
        /*
        if(strpos($url, 'panel')){
            $fac->panel = $results['panel'];
        }
        */
        $fac->save();
        return $fac;
    }

    public function delete(){
        return Faculty::where('fid', '=', $_GET['fid'])->delete();
    }
}
