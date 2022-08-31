<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateStudentsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('skills');
	Schema::dropIfExists('students');
        Schema::create('students', function (Blueprint $table) {
        $table->timestamps();
        $table->string('email');
        $table->string('name');
        $table->string('major');
        $table->unsignedBigInteger('project');
        $table->unsignedBigInteger('pref1');
	$table->unsignedBigInteger('pref2');
	$table->unsignedBigInteger('pref3');
	$table->unsignedBigInteger('pref4');
	$table->unsignedBigInteger('pref5');
	$table->unsignedBigInteger('gid');
	$table->string('net_id')->unique();
		

            //Foreign Keys
		$table->foreign('gid')->references('gid')->on('teams');
		$table->foreign('project')->references('pid')->on('projects');
           	$table->foreign('pref1')->references('pid')->on('projects');
		$table->foreign('pref2')->references('pid')->on('projects');
		$table->foreign('pref3')->references('pid')->on('projects');
		$table->foreign('pref4')->references('pid')->on('projects');
		$table->foreign('pref5')->references('pid')->on('projects');


        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
	Schema::dropIfExists('skills');
        Schema::dropIfExists('students');
    }
}
