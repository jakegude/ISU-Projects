<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFacultyPanelsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('faculty_panels');
        Schema::create('faculty_panels', function (Blueprint $table) {
            $table->id();
            $table->timestamps();
		$table->string('fid');
		$table->unsignedBigInteger('project');
		$table->string('time');
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
        Schema::dropIfExists('faculty_panels');
    }
}
