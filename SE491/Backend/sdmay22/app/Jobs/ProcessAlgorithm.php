<?php

namespace App\Jobs;

use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldBeUnique;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Queue\SerializesModels;
use Illuminate\Support\Facades\Storage;
use App\Models\Client;
use App\Models\Faculty;
use App\Models\Preference;
use App\Models\Project;
use App\Models\Requirements;
use App\Models\Skill;
use App\Models\Student;
use App\Models\Team;
use App\Models\User;
use App\Models\File;


$ProjectBonus = array(5, 4.5, 4, 4, 4);
$SATURATE = 0.6;
$SkillBonus = 2;
$TeammateBonus = 7;
$RECURSSION_CAP = 5;

class ProcessAlgorithm implements ShouldQueue
{
    use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;

    protected $params;

    /**
     * Create a new job instance.
     *
     * @return void
     */
    public function __construct(array $params)
    {
        $this->params = $params;
    }

    /**
     * Execute the job.
     *
     * @return void
     */
    public function handle()
    {
        // Do the "main" of the algorithm script here
        // Project Model should have all the fields as the Project class created in Script.php.
        // - Can be accessed by $proj = Project::where('pid', '=', $_GET['pid'])->first();
        // Student model should have all the fields as the Student class created in Script.php
        // - Can be accessed by $s = Student::where('net_id', '=', $_GET['netid'])->first();
        // See https://laravel.com/docs/9.x/eloquent#collections for more info on getting data from models
        // The models are linked to the DB, so you are able to get data from there instead of hardcoding in script
		global $params;

		$sList = new StudentList();
		foreach($params[1] as $stu)
			$sList->push($stu);
			
		$this->ProjectMatch($params[0], $sList, $contents);

        $sortClass = new AlgorithmSort(); // Call $sortClass.FUNCTION() to use sort functions

        // Write the contents of the algorithm to a file. The file can be accessed by Storage::get()
        $fileName = 'AlgoOutput_' . strval(Time());
        $contents = "{'key': 'value'}"; // TODO: Fill out contents to be result from algorithm
        Storage::put($fileName, $contents);
    }

    private function Swap()
    {
        // TODO: Fill out code and function params
    }

    private function Matching()
    {
        // TODO: Fill out code and function params
    }

    private function ProjectMatch($pList, $sList, &$team)
    {
        global $ProjectBonus, $SATURATE, $TeammateBonus, $SkillBonus;
		for($i = 0; $i < count($pList); $i++)
			$team[$i] = array_fill(0, $pList[$i]->size, -1); //Initialize team based on student size for a project
		$stu = $sList->getStudents();

		$current = array_fill(0, count($pList), 0); //Intialize the array of current, each index represent project, the value represents the students in the project at this time
		$stu_scores = array_fill(0, count($sList->getStudents()), array());
		$stu_projects = array_fill(0, count($sList->getStudents()), null);
		$sp_pos = array_fill(0, count($stu), array());
		
		for($i = 0; $i < count($stu); $i++) {
			$score = array_fill(0, count($pList), 0);
			for($j = 0; $j < count($pList); $j++) {
				$p_multiplier = 1;
				$s_multiplier = 1;
				$C_PID = $pList[$j]->pid;
				if($stu[$i]->PreferredProject($C_PID) != -1) {
					//echo "Preferred Project: + {$s_multiplier} * {$ProjectBonus[$stu[$i]->PreferredProject($C_PID)]}\r\n";
					$score[$j] += $s_multiplier * $ProjectBonus[$stu[$i]->PreferredProject[$C_PID]];
					$s_multiplier *= $SATURATE;
				}
				$skill = $stu[$i]->getSkill();
				foreach($skill as $value) {
					if($pList[$j]->RequiredSkill($value)) {
						//echo "Matched Skills + {$p_multiplier} * {$SkillBonus}\r\n";
						$score[$j] += $p_multiplier * $SkillBonus;
						$p_multiplier *= $SATURATE;
					}
				}
				foreach($team[$j] as $value) {
					if($value == -1)
						break;
					if($stu[$i]->PreferredTeammate[$value]) {
						//echo "Preferred teammate: + {$s_multiplier} * {$TeammateBonus}\r\n";
						$score[$j] += $s_multiplier * $TeammateBonus;
						$s_multiplier *= $SATURATE;
					}
				}
			}
			$list = $pList;
            $sort = new AlgorithmSort;
			$sort->MSort($list, $score, $sp_pos[$i], 0, count($list) - 1, 0);

			$stu_projects[$i] = $list;
			$stu_scores[$i] = $score;

			foreach($list as $project) {
				if($this->Optimize($stu[$i], $project, $stu_scores, $stu_projects, $sp_pos, $current, $team, $sList, 0))
					break;
			}
		}

        // TODO: Fill out code and function params
    }

    private function Optimize($current_s, $current_p, $sScore, $sProject, $position, &$current, &$team, &$sList, $level)
    {
        global $RECURSSION_CAP;
		if($level > $RECURSSION_CAP)
			return 0;
		//echo "current level: {$level}\r\n";
		$sid = $current_s->sid;
		$pid = $current_p->pid;

		if($current[$pid] < $current_p->getSize()) {
			$sList->SetInTeam($sid);
			$team[$pid][$current[$pid]] = $sid;
			$current[$pid]++;
			return 1;
		}

		$score = array_fill(0, $current_p->getSize(), 0);
		$all_last = 1;
		$list = $team[$pid];

		$project_satisfaction = 0;
		foreach($team[$pid] as $stu) {
			if($stu == -1)
				continue;

			$project_satisfaction += $sScore[$stu][$pid];
			if($position[$stu][$pid] == count($position[$stu]) - 1) {
				$score[$stu] = -1;
				continue;
			}
			$all_last = 0;
			$score[$stu] = $sScore[$stu][$position[$stu][$pid]] - $sScore[$stu][$position[$stu][$pid] + 1];
		}

		if($all_last)
			return 0;

        $cPosition = array();
        $sort = new AlgorithmSort;
		$sort->MSortIndex($list, $score, $cPosition, 0, count($list) - 1, 0);

		for($i = (count($list) - 1); $i >= 0; $i--) {
			$c_sid = $list[$i];
			if($score[$c_sid] == -1)
				continue;
			if($project_satisfaction > $project_satisfaction - $sScore[$c_sid][$position[$c_sid][$pid]] + $sScore[$sid][$position[$sid][$pid]])
				return 0;
			$temp_team = $team;
			for($j = 0; $j < count($temp_team[$pid]) - 1; $j++) {
				if($temp_team[$pid][$j] == $list[$i]) {
					break;
				}
			}
			$temp_team[$pid][$j] = $sid;
			if($this->Optimize($sList->getStudents()[$team[$pid][$j]], $sProject[$team[$pid][$j]][$position[$team[$pid][$j]][$pid]], $sScore, $sProject, $position, $current, $temp_team, $sList, $level + 1)) {
				$team = $temp_team;
				return 1;
			}
		}
		return 0;
        // TODO: Fill out code and function params
    }
  
}

class StudentList {
    private $students;
    private $available;
    private $size;

    function __construct()
    {
        $this->students = array();
        $this->available = array();
        $this->size = 0;
    }

    //parameter will be a student object
    function push($student) {
        $this->students[$this->size] = $student;
        $this->available[$this->size] = 1;
        $this->size++;
    }

    function Count() {
        return $this->size;
    }

    function get($i) {
        return $this->students[$i];
    }

    function SetInTeam($i) {
        $this->available[$i] = 0;
    }

    function getStudents() {
        return $this->students;
    }

    function toString() {
        $result = "";
        for($i = 0; $i < count($this->students); $i++) 
            $result = "{$result}{$this->students[$i]->sid()} {$this->available[$i]}\r\n";
        return $result;
    }
}

class SkillDict {
    private $dict;
    private $reverse;
    private $size;

    function __construct() {
        $this->size = 0;
        $this->dict = array();
        $this->reverse = array();
    }

    //push the skill in plain text
    //push function can check duplicate and return 0 if it find duplicate skill
    function push($skill) {
        //echo "{$this->size}\r\n";
        if($this->dict[$skill] != null)
            return 0;
        $this->dict[$skill] = $this->size;
        $this->reverse[$this->size] = $skill;
        $this->size++;
        return 1;
    }

    function get($i){
        return $this->dict[$i];
    }

    function Count() {
        return $this->size;
    }

    function getSkillByIndex($i) {
        return $this->reverse[$i];
    }

    function toString() {
        $result = "";
        foreach($this->dict as $key => $value) {
            $result = "{$result}{$key}: {$value}\r\n";
        }
        return $result;
    }
}

class AlgorithmSort {

    public function __construct()
    {
        // Can be left empty
    }

    public function MergeSort(&$arr, &$score, $l, $r)
    {
        if($l < $r)
		{
			$m = $l + (int)(($r - $l) / 2);
			$this->MergeSort($arr, $score, $l, $m);
			$this->Mergesort($arr, $score, $m + 1, $r);
			$this->Merge($arr, $score, $l, $m, $r);
		}
        // TODO: Fill out code and function params
    }

    public function Merge(&$arr, &$score, $l, $m, $r)
    {
        $length1 = $m - $l + 1;
		$length2 = $r - $m;

		$score_l = array_fill(0, $length1, 0);
		$score_r = array_fill(0, $length2, 0);

		$L = array_fill(0, $length1, null);
		$R = array_fill(0, $length2, null);

		for($i = 0; $i < $length1; $i++) {
			$L[$i] = $arr[$l + $i];
			$score_l[$i] = $score[$l + $i];
		}

		for($j = 0; $j < $length2; $j++) {
			$R[$j] = $arr[$m + 1 + $j];
			$score_r[$j] = $score[$m + 1 + $j];
		}

		$i = 0;
		$j = 0;
		$k = $l;

		while($i < $length1 && $j < $length2) {
			if($score_l[$i] <= $score_r[$j]) {
				$arr[$k] = $R[$j];
				$score[$k] = $score_r[$j];
				$j++;
			} else {
				$arr[$k] = $L[$i];
				$score[$k] = $score_l[$i];
				$i++;
			}
			$k++;
		}

		while($i < $length1) {
			$arr[$k] = $L[$i];
			$score[$k] = $score_l[$i];
			$i++;
			$k++;
		}

		while($j < $length2) {
			$arr[$k] = $R[$j];
			$score[$k] = $score_r[$j];
			$j++;
			$k++;
		}
        // TODO: Fill out code and function params
    }

    public function MSort(&$arr, &$score, &$position, $l, $r, $level)
    {
        if($l < $r)
		{
			$m = $l + (int)(($r - $l) / 2);
			$this->MSort($arr, $score, $position, $l, $m, $level + 1);
			$this->Msort($arr, $score, $position, $m + 1, $r, $level + 1);
			$this->MergeAndLocate($arr, $score, $position, $l, $m, $r, $level);
		}
        // TODO: Fill out code and function params
    }

    public function MergeAndLocate(&$arr, &$score, &$position, $l, $m, $r, $level)
    {
        $length1 = $m - $l + 1;
		$length2 = $r - $m;

		$score_l = array_fill(0, $length1, 0);
		$score_r = array_fill(0, $length2, 0);

		$L = array_fill(0, $length1, null);
		$R = array_fill(0, $length2, null);

		if($level == 0) {
			$position = array_fill(0, $r + 1, 0);
		}

		for($i = 0; $i < $length1; $i++) {
			$L[$i] = $arr[$l + $i];
			$score_l[$i] = $score[$l + $i];
		}

		for($j = 0; $j < $length2; $j++) {
			$R[$j] = $arr[$m + 1 + $j];
			$score_r[$j] = $score[$m + 1 + $j];
		}

		$i = 0;
		$j = 0;
		$k = $l;

		while($i < $length1 && $j < $length2) {
			if($score_l[$i] <= $score_r[$j]) {
				$arr[$k] = $R[$j];
				$score[$k] = $score_r[$j];
				if($level == 0) {
					$position[$R[$j]->getPID()] = $k;
				}
				$j++;
			} else {
				$arr[$k] = $L[$i];
				$score[$k] = $score_l[$i];
				if($level == 0) {
					$position[$L[$i]->getPID()] = $k;
				}
				$i++;
			}
			$k++;
		}

		while($i < $length1) {
			$arr[$k] = $L[$i];
			$score[$k] = $score_l[$i];
			if($level == 0) {
				$position[$L[$i]->getPID()] = $k;
			}
			$i++;
			$k++;
		}

		while($j < $length2) {
			$arr[$k] = $R[$j];
			$score[$k] = $score_r[$j];
			if($level == 0) {
				$position[$R[$j]->getPID()] = $k;
			}
			$j++;
			$k++;
		}
        // TODO: Fill out code and function params
    }

    public function MSortIndex(&$arr, &$score, &$position, $l, $r, $level)
    {
        if($l < $r)
		{
			$m = $l + (int)(($r - $l) / 2);
			$this->MSortIndex($arr, $score, $position, $l, $m, $level + 1);
			$this->MsortIndex($arr, $score, $position, $m + 1, $r, $level + 1);
			$this->MergeAndLocateIndex($arr, $score, $position, $l, $m, $r, $level);
		}
        // TODO: Fill out code and function params
    }

    public function MergeAndLocateIndex(&$arr, &$score, &$position, $l, $m, $r, $level)
    {
        $length1 = $m - $l + 1;
		$length2 = $r - $m;

		$score_l = array_fill(0, $length1, 0);
		$score_r = array_fill(0, $length2, 0);

		$L = array_fill(0, $length1, null);
		$R = array_fill(0, $length2, null);

		if($level == 0) {
			$position = array_fill(0, $r + 1, 0);
		}

		for($i = 0; $i < $length1; $i++) {
			$L[$i] = $arr[$l + $i];
			$score_l[$i] = $score[$l + $i];
		}

		for($j = 0; $j < $length2; $j++) {
			$R[$j] = $arr[$m + 1 + $j];
			$score_r[$j] = $score[$m + 1 + $j];
		}

		$i = 0;
		$j = 0;
		$k = $l;

		while($i < $length1 && $j < $length2) {
			if($score_l[$i] <= $score_r[$j]) {
				$arr[$k] = $R[$j];
				$score[$k] = $score_r[$j];
				if($level == 0) {
					$position[$R[$j]] = $k;
				}
				$j++;
			} else {
				$arr[$k] = $L[$i];
				$score[$k] = $score_l[$i];
				if($level == 0) {
					$position[$L[$i]] = $k;
				}
				$i++;
			}
			$k++;
		}

		while($i < $length1) {
			$arr[$k] = $L[$i];
			$score[$k] = $score_l[$i];
			if($level == 0) {
				$position[$L[$i]] = $k;
			}
			$i++;
			$k++;
		}

		while($j < $length2) {
			$arr[$k] = $R[$j];
			$score[$k] = $score_r[$j];
			if($level == 0) {
				$position[$R[$j]] = $k;
			}
			$j++;
			$k++;
		}
        // TODO: Fill out code and function params
    }
}
