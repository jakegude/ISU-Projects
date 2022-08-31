<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateProjectsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('requirements');
	Schema::dropIfExists('skills');
	Schema::dropIfExists('students');
	Schema::dropIfExists('projects');
        Schema::create('projects', function (Blueprint $table) {
		$table->unsignedBigInteger('pid')->default('0');
		$table->unsignedBigInteger('cid')->default('0');
		$table->string('clientName')->default('null');
		$table->string('sumbitterName')->default('null');
		$table->string('pointOfConName')->default('null');
		$table->string('abstract')->default('null');
		$table->string('deliverables')->default('null');
		$table->string('specResources')->default('null');
		$table->string('financialResources')->default('null');
		$table->string('clientInteractionTimes')->default('null');
		$table->string('clientInteractionType')->default('null');
		$table->integer('abet1')->default('0');
		$table->integer('abet2')->default('0');
		$table->integer('abet3')->default('0');
		$table->integer('abet4')->default('0');
		$table->integer('abet5')->default('0');
		$table->unsignedBigInteger('SEmax')->default('0');
		$table->unsignedBigInteger('CSEmax')->default('0');
		$table->unsignedBigInteger('CEmax')->default('0');
		$table->unsignedBigInteger('EEmax')->default('0');
		$table->boolean('accepted')->default(false);
		$table->unsignedBigInteger('teamSize')->default('0');
		$table->string('fid')->default('0');
		$table->timestamps();
		$table->string('projectName')->default('null');
		$table->primary('pid')->default('0');
		$table->foreign('cid')->references('cid')->on('clients');
           	$table->foreign('fid')->references('fid')->on('faculties');
		

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('projects');
    }
}
