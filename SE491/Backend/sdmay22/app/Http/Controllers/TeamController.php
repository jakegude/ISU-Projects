<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Team;

class TeamController extends Controller
{
    public function all(){
        return Team::all();
    }

    public function team(){
        return Team::where('gid', '=', $_GET['gid'])->first();
    }

    //Finds all teams the specified faculty is a part of
    public function faculty(){
        return Team::where('fid', '=', $_GET['fid'])->get();
    }

    public function project(){
        $team = Team::where('gid', '=', $_GET['gid'])->first();
        return $team->project;
    }

    public function new(){
        $team = new Team;
        $team->teamEmail = $_GET['email'];
        $team->groupName = $_GET['name'];
        $team->size = $_GET['size'];
        $team->gid = $_GET['gid'];
        $team->fid = $_GET['fid'];
        $team->project = $_GET['project'];
        $team->save();
        return $team;
    }

    public function update(){
        $team = Team::where('gid', '=', $_GET['gid'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'email')){
            $team->teamEmail = $results['email'];
        }
        if(strpos($url, 'name')){
            $team->groupName = $results['name'];
        }
        if(strpos($url, 'size')){
            $team->size = $results['size'];
        }
        if(strpos($url, 'gid')){
            $team->gid = $results['gid'];
        }
        if(strpos($url, 'project')){
            $team->project = $results['project'];
        }
        $team->save();
        return $team;
    }

    public function delete(){
        return Team::where('gid', '=', $_GET['gid'])->delete();
    }

}
