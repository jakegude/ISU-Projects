<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\IndustryPanel;

class IndustryPanelController extends Controller
{
    public function all(){
        return IndustryPanel::all();
    }

    public function industrypanel(){
        return IndustryPanel::where('iid', '=', $_GET['iid'])->first();
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function panel(){
        $ip = IndustryPanel::where('iid', '=', $_GET['iid'])->first();
        return $ip->panel;
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function team(){
        $ip = IndustryPanel::where('iid', '=', $_GET['iid'])->first();
        return $ip->team;
    }

    //Currently doesn't do anything; DB needs this column of data first
    public function faculty(){
        $ip = IndustryPanel::where('iid', '=', $_GET['iid'])->first();
        return $ip->team;
    }

    /*public function schedule(int $id, String $type){
        ???
    }*/

    public function new(){
        $ip = new IndustryPanel;
        $ip->email = $_GET['email'];
        $ip->name = $_GET['name'];
        $ip->iid = $_GET['iid'];
        //Need these in DB before un-commenting them
        //$ip->team = $_GET['team'];
        //$ip->panel = $_GET['panel'];
        //$ip->faculty = $_GET['faculty'];
        $ip->save();
        return $ip;
    }

    public function update(){
        $ip = IndustryPanel::where('net_id', '=', $_GET['netid'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'email')){
            $ip->email = $results['email'];
        }
        if(strpos($url, 'name')){
            $ip->name = $results['name'];
        }
        if(strpos($url, 'iid')){
            $ip->iid = $results['iid'];
        }
        //Need these in the DB before un-commenting them
        /*
        if(strpos($url, 'team')){
            $ip->team = $results['team'];
        }
        if(strpos($url, 'panel')){
            $ip->panel = $results['panel'];
        }
        if(strpos($url, 'faculty')){
            $ip->faculty = $results['faculty'];
        }
        */
        $ip->save();
        return $ip;
    }

    public function delete(){
        return IndustryPanel::where('iid', '=', $_GET['iid'])->delete();
    }
}
