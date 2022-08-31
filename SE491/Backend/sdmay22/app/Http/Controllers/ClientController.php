<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Routing\Controller as BaseController;
use App\Models\Client;

class ClientController extends Controller
{
    public function all(){
        return Client::all();
    }

    public function client(){
        return Client::where('cid', '=', $_GET['cid'])->first();
    }

    public function new(){
        $cli = new Client;
        $cli->email = $_GET['email'];
        $cli->name = $_GET['name'];
        $cli->cid = $_GET['cid'];
        $cli->save();
        return $cli;
    }

    public function update(){
        $cli = Client::where('cid', '=', $_GET['cid'])->first();
        $url = $_SERVER['REQUEST_URI'];
        $components = parse_url($url);
        parse_str($components['query'], $results);
        if(strpos($url, 'email')){
            $cli->email = $results['email'];
        }
        if(strpos($url, 'name')){
            $cli->name = $results['name'];
        }
        if(strpos($url, 'cid')){
            $cli->net_id = $results['cid'];
        }
        $cli->save();
        return $cli;
    }

    public function delete(){
        return Client::where('cid', '=', $_GET['cid'])->delete();
    }
}
