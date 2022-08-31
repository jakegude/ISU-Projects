<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Project;

class ProjectController extends Controller
{
    public function all(){
        return Project::all();
    }

    public function team(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->team;
    }

    public function accepted(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->accepted;
    }

    public function project(){
        return Project::where('pid', '=', $_GET['pid'])->first();
    }

    public function sizePref(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->sizePref;
    }

    public function client(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->client;
    }

    public function faculty(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->faculty;
    }

    public function semax(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->SEmax;
    }

    public function csemax(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->CSEmax;
    }

    public function cemax(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->CEmax;
    }

    public function eemax(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->EEmax;
    }

    public function file(){
        $proj = Project::where('pid', '=', $_GET['pid'])->first();
        return $proj->projectFile;
    }

    public function new(){
        $proj = new Project;
        $proj->pid = $_GET['pid'];
        $proj->projectName = $_GET['name'];
        $proj->projectFile = $_GET['file'];
        $proj->cid = $_GET['cid'];
        $proj->fid = $_GET['fid'];
        $proj->teamSize = $_GET['teamSize'];
        $proj->CSEmax = $_GET['csemax'];
        $proj->CEmax = $_GET['cemax'];
        $proj->SEmax = $_GET['semax'];
        $proj->EEmax = $_GET['eemax'];
        $proj->accepted = false;
        $proj->save();
        return $proj;
    }

    public function update(){
        $proj = Student::where('net_id', '=', $_GET['netid'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'pid')){
            $proj->pid = $results['pid'];
        }
        if(strpos($url, 'name')){
            $proj->projectName = $results['name'];
        }
        if(strpos($url, 'file')){
            $proj->projectFile = $results['file'];
        }
        if(strpos($url, 'skills')){
            $proj->skills = $results['skills'];
        }
        if(strpos($url, 'sizePref')){
            $proj->sizePref = $results['sizePref'];
        }
        if(strpos($url, 'team')){
            $proj->preference = $results['team'];
        }
        if(strpos($url, 'client')){
            $proj->client = $results['client'];
        }
        if(strpos($url, 'faculty')){
            $proj->faculty = $results['faculty'];
        }
        if(strpos($url, 'semax')){
            $proj->SEmax = $results['semax'];
        }
        if(strpos($url, 'csemax')){
            $proj->CSEmax = $results['csemax'];
        }
        if(strpos($url, 'cemax')){
            $proj->CEmax = $results['cemax'];
        }
        if(strpos($url, 'eemax')){
            $proj->EEmax = $results['eemax'];
        }
        if(strpos($url, 'accepted')){
            $proj->accepted = $results['accepted'];
        }
        $proj->save();
        return $proj;
    }

    public function delete(){
        return Project::where('pid', '=', $_GET['pid'])->delete();
    }
}
