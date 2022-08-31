<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Skill;

class SkillController extends Controller
{
    public function all(){
        return Skill::all();
    }

    public function new(){
        $skill = new Skill;
        $skill->net_id = $_GET['netid'];
        $skill->Java = $_GET['java'];
        $skill->Laravel = $_GET['laravel'];
        $skill->SQL = $_GET['sql'];
        $skill->PHP = $_GET['php'];
        $skill->Algorithm = $_GET['algorithm'];
        $skill->save();
        return $skill;
    }

    //Returns the skills of the specified student
    public function skill(){
        return Skill::where('net_id', '=', $_GET['netid'])->first();
    }

    public function update(){
        $skill = Skill::where('id', '=', $_GET['id'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'netid')){
            $skill->net_id = $results['netid'];
        }
        if(strpos($url, 'java')){
            $skill->Java = $results['java'];
        }
        if(strpos($url, 'laravel')){
            $skill->Laravel = $results['laravel'];
        }
        if(strpos($url, 'sql')){
            $skill->SQL = $results['sql'];
        }
        if(strpos($url, 'php')){
            $skill->PHP = $results['php'];
        }
        if(strpos($url, 'algorithm')){
            $skill->Algorithm = $results['algorithm'];
        }
        $skill->save();
        return $skill;
    }

    public function delete(){
        return Skill::where('net_id', '=', $_GET['netid'])->delete();
    }

}
