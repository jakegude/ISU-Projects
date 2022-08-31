<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRequirementsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('requirements');
        Schema::create('requirements', function (Blueprint $table) {
            $table->unsignedBigInteger('pid');
		$table->integer('Java');
		$table->integer('Laravel');
		$table->integer('SQL');
		$table->integer('PHP');
		$table->integer('Algorithm');
		$table->timestamps();
		$table->foreign('pid')->references('pid')->on('projects');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('requirements');
    }
}
