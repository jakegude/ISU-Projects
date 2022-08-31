<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\FacultyPanel;

class FacultyPanelController extends Controller
{
    public function all(){
        return FacultyPanel::all();
    }

    public function facultypanel(){
        return FacultyPanel::where('id', '=', $_GET['id'])->first();
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function student(){
        $fp = FacultyPanel::where('id', '=', $_GET['id'])->first();
        return $fp->students;
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function team(){
        $fp = FacultyPanel::where('id', '=', $_GET['id'])->first();
        return $fp->team;
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function panel(){
        $fp = FacultyPanel::where('id', '=', $_GET['id'])->first();
        return $fp->panel;
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function faculty(){
        $fp = FacultyPanel::where('id', '=', $_GET['id'])->first();
        return $fp->faculty;
    }

    public function new(){
        $fp = new FacultyPanel;
        $fp->id = $_GET['id'];
        //Need these in DB before un-commenting them
        //$fp->team = $_GET['team'];
        //$fp->student = $_GET['student'];
        //$fp->faculty = $_GET['faculty'];
        //$fp->panel = $_GET['panel'];
        $fp->save();
        return $fp ;
    }

    public function update(){
        $fp = FacultyPanel::where('id', '=', $_GET['id'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'email')){
            $fp->email = $results['email'];
        }
        //Need these in the DB before un-commenting them
        /*
        if(strpos($url, 'team')){
            $fp->team = $results['team'];
        }
        if(strpos($url, 'student')){
            $fp->student = $results['student'];
        }
        if(strpos($url, 'faculty')){
            $fp->faculty = $results['faculty'];
        }
        if(strpos($url, 'panel')){
            $fp->panel = $results['panel'];
        }
        */
        $fp->save();
        return $fp;
    }

    public function delete(){
        return FacultyPanel::where('id', '=', $_GET['id'])->delete();
    }
}
