<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\User;
use App\Models\NonIsuUser;
use Validator;
use Hash;

class AuthController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth:api', ['except' => ['login', 'register']]);
    }

    public function login(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|email',
            'password' => 'required|string|min:8',
        ]);

        if ($validator->fails())
        {
            return response()->json($validator->errors(), 422);
        }

        if (!$token = auth()->attempt($validator->validated()))
        {
            auth()->shouldUse('non-isu-api');
            if (!$token = auth()->attempt($validator->validated()))
            {
                return response()->json(['error' => 'Unauthorized'], 401);
            }
        }

        return $this->createNewToken($token);
    }

    public function register(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'name' => 'required|string',
            'email' => 'required|email|unique:users',
            'password' => 'required|string|min:8',
        ]);

        if ($validator->fails())
        {
            return response()->json($validator->errors(), 400);
        }

        $user = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password),
            'objectguid' => '0',
            
        ]);

        return response()->json([
            'message' => 'User created successfully',
            'user' => $user,
        ], 201);
    }

    public function logout()
    {
        auth()->logout();
        return response()->json(['message' => 'User signed out']);
    }

    public function user()
    {
         return response()->json(auth()->user());
    }

    protected function createNewToken($token)
    {
        return response()->json([
        'access_token' => $token,
        'token_type' => 'bearer',
        'expires_in' => auth()->factory()->getTTL() * 60,
        'user' => auth()->user()
        ]);
    }
}
