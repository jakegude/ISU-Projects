<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Validator;
use Illuminate\Routing\Controller as BaseController;
use Illuminate\Support\Facades\Storage;
use App\Jobs\ProcessAlgorithm;

class AlgorithmController extends Controller
{
   public function execute(Request $request) {
        // TODO: Determine params that need to be sent to the algorithm
        $params = array();
        $algorithmJob = new ProcessAlgorithm($params);
        $this->dispatch($algorithmJob);

        return response()->json([
            'message' => 'Algorithm Triggered Successfully',
        ], 202);
   }

   public function get(Request $request) {
        // TODO: Determine UUID to retreive files. 
        //Storage::get()
   }

   public function delete(Request $request) {
        // TODO: Determine UUID to remove files.
        //Storage::delete()
   }
}


