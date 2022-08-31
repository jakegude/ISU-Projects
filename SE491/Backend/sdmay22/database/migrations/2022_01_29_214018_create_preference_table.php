<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePreferenceTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
	Schema::dropIfExists('preference');
        Schema::create('preference', function (Blueprint $table) {
             $table->string('net_id');
		$table->string('friend1');
		$table->string('friend2');
		$table->string('friend3');
		$table->string('friend4');
		$table->string('friend5');
		$table->timestamps();
		$table->primary('net_id');
		$table->foreign('net_id')->references('net_id')->on('students');
		$table->foreign('friend1')->references('net_id')->on('students');
		$table->foreign('friend2')->references('net_id')->on('students');
		$table->foreign('friend3')->references('net_id')->on('students');
		$table->foreign('friend4')->references('net_id')->on('students');
		$table->foreign('friend5')->references('net_id')->on('students');

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('preference');
    }
}
