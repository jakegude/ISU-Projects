<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTeamsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('teams');
        Schema::create('teams', function (Blueprint $table) {
            $table->unsignedBigInteger('gid');
            $table->timestamps();
            $table->string('groupName')->default('null');
            $table->string('teamEmail')->default('null');
            $table->integer('size')->default('0');
	    $table->string('fid');
		$table->unsignedBigInteger('project')->default('0');
	    $table->primary('gid');

		$table->foreign('fid')->references('fid')->on('faculties');
		$table->foreign('project')->references('pid')->on('projects');


        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('teams');
    }
}
