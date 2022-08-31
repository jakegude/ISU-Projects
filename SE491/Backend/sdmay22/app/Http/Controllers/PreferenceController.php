<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Preference;

class PreferenceController extends Controller
{
    public function all(){
        return Preference::all();
    }

    public function new(){
        $pref = new Preference;
        $pref->net_id = $_GET['netid'];
        $pref->friend1 = _GET['friend1'];
        $pref->friend2 = $_GET['friend2'];
        $pref->friend3 = $_GET['friend3'];
        $pref->friend4 = $_GET['friend4'];
        $pref->friend5 = $_GET['friend5'];
        $pref->save();
        return $pref;
    }

    public function preference(){
        return Preference::where('net_id', '=', $_GET['netid'])->first();
    }

    public function update(){
        $pref = Preference::where('id', '=', $_GET['id'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'netid')){
            $pref->net_id = $results['netid'];
        }
        if(strpos($url, 'friend1')){
            $pref->friend1 = $results['friend1'];
        }
        if(strpos($url, 'friend2')){
            $pref->friend2 = $results['friend2'];
        }
        if(strpos($url, 'friend3')){
            $pref->friend3 = $results['friend3'];
        }
        if(strpos($url, 'friend4')){
            $pref->friend4 = $results['friend4'];
        }
        if(strpos($url, 'friend5')){
            $pref->friend5 = $results['friend5'];
        }
        $pref->save();
        return $pref;
    }

    public function delete(){
        return Preference::where('net_id', '=', $_GET['netid'])->delete();
    }

}
