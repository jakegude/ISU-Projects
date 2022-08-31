<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Student;

class StudentController extends Controller
{
    public function all(){
        return Student::all();
    }

    public function student(){
        return Student::where('net_id', '=', $_GET['netid'])->first();
    }

    public function team(){
        $stu = Student::where('net_id', '=', $_GET['netid'])->first();
        return $stu->gid;
    }

    public function project(){
        $stu = Student::where('net_id', '=', $_GET['netid'])->first();
        return $stu->project;
    }

    //Returns a comma-separated list of preferences in preferential order
    public function preferences(){
        $stu = Student::where('net_id', '=', $_GET['netid'])->first();
        $preferences = strval($stu->pref1) . ", " . strval($stu->pref2) . ", " . strval($stu->pref3)
        . ", " . strval($stu->pref4) . ", " . strval($stu->pref5); 
        return $preferences;
    }

    public function skills(){
        $stu = Student::where('net_id', '=', $_GET['netid'])->first();
        return $stu->skills;
    }

    public function new(){
        $stu = new Student;
        $stu->email = $_GET['email'];
        $stu->name = $_GET['name'];
        $stu->major = $_GET['major'];
        $stu->net_id = $_GET['netid'];
        $stu->pref1 = $_GET['pref1'];
        $stu->pref2 = $_GET['pref2'];
        $stu->pref3 = $_GET['pref3'];
        $stu->pref4 = $_GET['pref4'];
        $stu->pref5 = $_GET['pref5'];
        $stu->gid = $_GET['gid'];
        $stu->project = $_GET['project'];
        $stu->skills = $_GET['skills'];
        $stu->save();
        return $stu;
    }

    public function update(){
        $stu = Student::where('net_id', '=', $_GET['netid'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'email')){
            $stu->teamEmail = $results['email'];
        }
        if(strpos($url, 'name')){
            $stu->groupName = $results['name'];
        }
        if(strpos($url, 'major')){
            $stu->major = $results['major'];
        }
        if(strpos($url, 'netid')){
            $stu->net_id = $results['netid'];
        }
        if(strpos($url, 'gid')){
            $stu->gid = $results['gid'];
        }
        if(strpos($url, 'pref1')){
            $stu->pref1 = $results['pref1'];
        }
        if(strpos($url, 'pref2')){
            $stu->pref2 = $results['pref2'];
        }
        if(strpos($url, 'pref3')){
            $stu->pref3 = $results['pref3'];
        }
        if(strpos($url, 'pref4')){
            $stu->pref4 = $results['pref4'];
        }
        if(strpos($url, 'pref5')){
            $stu->pref5 = $results['pref5'];
        }
        if(strpos($url, 'project')){
            $stu->project = $results['project'];
        }
        if(strpos($url, 'skills')){
            $stu->skills = $results['skills'];
        }
        $stu->save();
        return $stu;
    }

    public function delete(){
        return Student::where('net_id', '=', $_GET['netid'])->delete();
    }

}
