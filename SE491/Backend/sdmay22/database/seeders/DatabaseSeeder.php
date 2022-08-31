<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        DB::table('clients')->insert(['cid' => 0, 'email' => 'null', 'name' => 'null']);
	DB::table('faculties')->insert(['fid' => 'null', 'email' => 'null', 'name' => 'null']);
	DB::table('projects')->insert(['pid' => 0, 'fid' => 'null']);
	DB::table('teams')->insert(['gid' => 0, 'fid' => 'null']);
    }
}
