<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateSkillsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('skills');
        Schema::create('skills', function (Blueprint $table) {
            $table->string('net_id');
		$table->integer('Java');
		$table->integer('Laravel');
		$table->integer('SQL');
		$table->integer('PHP');
		$table->integer('Algorithm');
		$table->timestamps();
		$table->primary('net_id');
		$table->foreign('net_id')->references('net_id')->on('students');
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
    }
}
