<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\StudentController;
use App\Http\Controllers\ClientController;
use App\Http\Controllers\FacultyController;
use App\Http\Controllers\FacultyPanelController;
use App\Http\Controllers\FrontendController;
use App\Http\Controllers\IndustryPanelController;
use App\Http\Controllers\ProjectController;
use App\Http\Controllers\TeamController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\SkillController;
use App\Http\Controllers\AlgorithmController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// Test Routes
Route::get('/testing', function () {
    return ['message' => 'Hello World!'];
});

Route::get('/ldap', function (Request $request) {

    $users = Adldap::search()->where('mail', '=', 'sethg18@iastate.edu')->get();
    foreach ($users as $user){
        echo $user;
    }
});

// Algorithm Endpoint
Route::get('/algorithm', [AlgorithmController::class, 'execute']);

// Isu Auth Routes
Route::get('/login', [AuthController::class, 'login']);
Route::get('/register', [AuthController::class, 'register']);
Route::get('/logout', [AuthController::class, 'logout']);
Route::get('/user', [AuthController::class, 'user']);

//Student Routes
Route::get('student/all', [StudentController::class, 'all']);
Route::get('student/student', [StudentController::class, 'student']);
Route::get('student/team', [StudentController::class, 'team']);
Route::get('student/project', [StudentController::class, 'project']);
Route::get('student/preferences', [StudentController::class, 'preferences']);
Route::post('student/new', [StudentController::class, 'new']);
Route::put('student/update', [StudentController::class, 'update']);
Route::delete('student/delete', [StudentController::class, 'delete']);

//Team Routes
Route::get('team/all', [TeamController::class, 'all']);
Route::get('team/team', [TeamController::class, 'team']);
Route::get('team/faculty', [TeamController::class, 'faculty']); //Finds all team the specified faculty is a part of
Route::get('team/project', [TeamController::class, 'project']);
Route::post('team/new', [TeamController::class, 'new']);
Route::put('team/update', [TeamController::class, 'update']);
Route::delete('team/delete', [TeamController::class, 'delete']);

//Project Routes
Route::get('project/all', [ProjectController::class, 'all']);
Route::get('project/project', [ProjectController::class, 'project']);
Route::get('project/approved', [ProjectController::class, 'approved']);
Route::get('project/client', [ProjectController::class, 'client']);
Route::get('project/team', [ProjectController::class, 'team']);
Route::get('project/sizePref', [ProjectController::class, 'sizePref']);
Route::get('project/semax', [ProjectController::class, 'semax']);
Route::get('project/csemax', [ProjectController::class, 'csemax']);
Route::get('project/cemax', [ProjectController::class, 'cemax']);
Route::get('project/eemax', [ProjectController::class, 'eemax']);
Route::get('project/faculty', [ProjectController::class, 'faculty']);
Route::get('project/file', [ProjectController::class, 'file']);
Route::post('project/new', [ProjectController::class, 'new']);
Route::put('project/update', [ProjectController::class, 'update']);
Route::delete('project/delete', [ProjectController::class, 'delete']);

//Faculty Routes
Route::get('faculty/all', [FacultyController::class, 'all']);
Route::get('faculty/faculty', [FacultyController::class, 'faculty']);
Route::get('faculty/panel', [FacultyController::class, 'panel']);
Route::get('faculty/panelbyname', [FacultyController::class, 'panelbyname']);
Route::post('faculty/new', [FacultyController::class, 'new']);
Route::put('faculty/update', [FacultyController::class, 'update']);
Route::delete('faculty/delete', [FacultyController::class, 'delete']);

//FacultyPanel Routes
Route::get('facultyPanel/all', [FacultyPanelController::class, 'all']);
Route::get('facultyPanel/faculty', [FacultyPanelController::class, 'faculty']);
Route::get('facultyPanel/panel', [FacultyPanelController::class, 'panel']);
Route::get('facultyPanel/team', [FacultyPanelController::class, 'team']);
Route::post('facultyPanel/new', [FacultyPanelController::class, 'new']);
Route::put('facultyPanel/update', [FacultyPanelController::class, 'update']);
Route::delete('facultyPanel/delete', [FacultyPanelController::class, 'delete']);

//Client Routes
Route::get('client/all', [ClientController::class, 'all']);
Route::get('client/client', [ClientController::class, 'client']);
Route::post('client/new', [ClientController::class, 'new']);
Route::put('client/update', [ClientController::class, 'update']);
Route::delete('client/delete', [ClientController::class, 'delete']);

//IndustryPanel Routes
Route::get('industryPanel/all', [IndustryPanelController::class, 'all']);
Route::get('industryPanel/faculty', [IndustryPanelController::class, 'faculty']);
Route::get('industryPanel/panel', [IndustryPanelController::class, 'panel']);
Route::get('industryPanel/team', [IndustryPanelController::class, 'team']);
Route::post('industryPanel/new', [IndustryPanelController::class, 'new']);
Route::put('industryPanel/update', [IndustryPanelController::class, 'update']);
Route::delete('industryPanel/delete', [IndustryPanelController::class, 'delete']);

//User Routes
Route::get('user/all', [UserController::class, 'all']);
Route::get('user/user', [UserController::class, 'user']);
Route::post('user/new', [UserController::class, 'new']);
Route::put('user/update', [UserController::class, 'update']);
Route::delete('user/delete', [UserController::class, 'delete']);

//Skill Routes
Route::get('skill/all', [SkillController::class, 'all']);
Route::get('skill/skill', [SkillController::class, 'skill']);
Route::post('skill/new', [SkillController::class, 'new']);
Route::put('skill/update', [SkillController::class, 'update']);
Route::delete('skill/delete', [SkillController::class, 'delete']);

//Requirements Routes
Route::get('requirements/all', [RequirementsController::class, 'all']);
Route::get('requirements/requirements', [RequirementsController::class, 'requirements']);
Route::post('requirements/new', [RequirementsController::class, 'new']);
Route::put('requirements/update', [RequirementsController::class, 'update']);
Route::delete('requirements/delete', [RequirementsController::class, 'delete']);

//Preference Routes
Route::get('preference/all', [PreferenceController::class, 'all']);
Route::get('preference/preference', [PreferenceController::class, 'preference']);
Route::post('preference/new', [PreferenceController::class, 'new']);
Route::put('preference/update', [PreferenceController::class, 'update']);
Route::delete('preference/delete', [PreferenceController::class, 'delete']);
