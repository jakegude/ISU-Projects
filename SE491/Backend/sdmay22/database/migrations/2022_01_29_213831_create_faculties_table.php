<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFacultiesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('teams');
	Schema::dropIfExists('faculties');
        Schema::create('faculties', function (Blueprint $table) {
            $table->string('fid');
	    $table->string('email');
	    $table->string('name');
            $table->timestamps();
	    $table->primary('fid');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('faculties');
    }
}
