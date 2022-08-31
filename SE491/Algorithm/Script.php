<?php
	require "./Sort.php";

	$ProjectBonus = array(5, 4.5, 4, 4, 4);
	$SATURATE = 0.8;
	$SkillBonus = 3;
	$TeammateBonus = 8;
	$RECURSSION_CAP = 5;

	$conn = null;
	$server = "server";
	$username = "username";
	$password = "password";
	$dbname = "db";

	class Project {
		private $pid;
		private $name;
		private $r_skill;
		private $size;

		//pid is project id in DB
		//name is project name
		//skill is an array of the prefered skill of the project
		//dict is a SkillDict value
		//size is the team size
		function __construct($pid, $name, $skill, $dict, $size) {
			$this->pid = $pid;
			$this->name = $name;
			$this->r_skill = array();
			$this->r_skill = array_fill(0, $dict->Count(), 0);
			foreach($skill as $value){
				$this->r_skill[$dict->get($value)] = 1;
			}
			$this->size = $size;
		}

		function getPID() {
			return $this->pid;
		}

		function getName() {
			return $this->name;
		}

		function RequiredSkill($skill) {
			return $this->r_skill[$skill];
		}

		function getSkills() {
			return $this->r_skill;
		}

		function getSize() {
			return $this->size;
		}

		function toString() {
			$result = "{$this->pid} {$this->name} { ";
			foreach($this->r_skill as $value) {
				$result = "{$result}{$value} ";
			}
			$result = "{$result}}";
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

	class Student {
		private $sid;
		private $name;
		private $skills;
		private $preferred_project;
		private $preferred_teammate;

		//sid is student id
		//name is student name
		//skills is an array of skills that student have
		//dict is a SkillDict value
		//project is an array of preferred project id of the student
		//total project number means how many project are there in this semester
		//teammate is an array of preferred teammate id of the student
		//total stu number means how many students are there in this semester
		function __construct($sid, $name, $skills, $dict, $project, $total_project_num, $teammate, $total_stu_num) {
			$this->name = $name;
			$this->sid = $sid;
			$this->skills = array();
			$i = 0;
			foreach($skills as $value) {
				$this->skills[$i] = $dict->get($value);
				$i++;
			}

			$this->preferred_project = array_fill(0, $total_project_num, -1);
			$position = 0;
			foreach($project as $value) {
				$this->preferred_project[$value] = $position >= 5 ? $position : $position++;
			}

			$this->preferred_teammate = array_fill(0, $total_stu_num, 0);
			foreach($teammate as $value) {
				$this->preferred_teammate[$value] = 1;
			}
		}

		function getSID() {
			return $this->sid;
		}

		function PreferredProject($pid) {
			return $this->preferred_project[$pid];
		}

		function PreferredTeammate($sid) {
			return $this->preferred_teammate[$sid];
		}

		function getName() {
			return $this->name;
		}

		function getSkill() {
			return $this->skills;
		}

		function getPreferredProject() {
			return $this->preferred_project;
		}

		function getPreferredTeammate() {
			return $this->preferred_teammate;
		}

		function toString() {
			$result = "{$this->sid} {$this->name} { ";
			for($i = 0; $i < count($this->skills); $i++) {
				$result = "{$result}{$this->skills[$i]} ";
			}
			$result = "{$result}} { ";
			foreach($this->preferred_project as $value) {
				$result = "{$result}{$value} ";
			}
			$result = "{$result}} { ";
			foreach($this->preferred_teammate as $value) {
				$result = "{$result}{$value} ";
			}
			$result = "{$result}}";
			return "{$result}\r\n";
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
				$result = "{$result}{$this->students[$i]->getSID()} {$this->available[$i]}\r\n";
			return $result;
		}
	}

	function swap(&$arr, $i, $j) {
		$temp = $arr[$i];
		$arr[$i] = $arr[$j];
		$arr[$j] = $temp;
	}

	//put initialization here
	function Matching(){
		
		$dict = new SkillDict();
		//push skills to skill Dict. Detail is at line 64

		$project_list = array();
		//add project objects to project list. Detail is at line 16

		$student_list = new StudentList();
		//push student objcts to student list. Detail is at line 104

		Project_Match($project_list, $student_list, $team);

		//export team to database
	}

	
	function Project_Match ($pList, $sList, &$team) {
		global $ProjectBonus, $SATURATE, $TeammateBonus, $SkillBonus;
		for($i = 0; $i < count($pList); $i++)
			$team[$i] = array_fill(0, $pList[$i]->getSize(), -1); //Initialize team based on student size for a project
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
				$C_PID = $pList[$j]->getPID();
				if($stu[$i]->PreferredProject($C_PID) != -1) {
					//echo "Preferred Project: + {$s_multiplier} * {$ProjectBonus[$stu[$i]->PreferredProject($C_PID)]}\r\n";
					$score[$j] += $s_multiplier * $ProjectBonus[$stu[$i]->PreferredProject($C_PID)];
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
					if($stu[$i]->PreferredTeammate($value)) {
						//echo "Preferred teammate: + {$s_multiplier} * {$TeammateBonus}\r\n";
						$score[$j] += $s_multiplier * $TeammateBonus;
						$s_multiplier *= $SATURATE;
					}
				}
			}
			$list = $pList;
			MSort($list, $score, $sp_pos[$i], 0, count($list) - 1, 0);

			$stu_projects[$i] = $list;
			$stu_scores[$i] = $score;

			foreach($list as $project) {
				if(Optimize($stu[$i], $project, $stu_scores, $stu_projects, $sp_pos, $current, $team, $sList, 0))
					break;
				echo "{$project->getPID()} failed\r\n";
			}
			echo "{$i} out of 300\r\n";
		}
		

	}


	#current_s means the current student to do matching
	#current_p means the current project to seek replacement
	#sScore means the sorted satisfaction score for each project recorded in each student.
	#sProject means the sorted project list based on the satisfaction score by each student
	#position means the index of each project in each student after sorting the project by the satisfaction score, 
	#current means the current number of people in each project team
	#team means the team member of all project, each value is a SID
	#sList means the student list
	#level means the current time of recurssion. function return false if level go over a certain amount
	function Optimize($current_s, $current_p, $sScore, $sProject, $position, &$current, &$team, &$sList, $level) {
		global $RECURSSION_CAP;
		if($level > $RECURSSION_CAP)
			return 0;
		//echo "current level: {$level}\r\n";
		$sid = $current_s->getSID();
		$pid = $current_p->getPID();

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
		MSortIndex($list, $score, $cPosition, 0, count($list) - 1, 0);

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
			if(Optimize($sList->getStudents()[$team[$pid][$j]], $sProject[$team[$pid][$j]][$position[$team[$pid][$j]][$pid]], $sScore, $sProject, $position, $current, $temp_team, $sList, $level + 1)) {
				$team = $temp_team;
				return 1;
			}
		}
		return 0;
	}

	$dict = new SkillDict();
	/*$dict->push("JavaScript");
	$dict->push("SQL");
	$dict->push("Laravel");
	$dict->push("PHP");
	$dict->push("Algorithm");*/

	for($i = 0; $i < 26; $i++)
		$dict->push(chr(97 + $i));

	$num = 0;
	
	$list = new StudentList();
	for($i = 0; $i < 300; $i++) {
		$skill = array();
		$loop = rand(3, 5);
		for($j = 0; $j < $loop; $j++)
			$skill[$j] = chr(rand(0, 25) + 97);
		$PP = array();
		$loop = rand(2, 5);
		for($j = 0; $j < $loop; $j++)
			$PP[$j] = rand(0, 59);
		$PT = array();
		$loop = rand(0, 2);
		for($j = 0; $j < $loop; $j++)
			$PT[$j] = rand(0, 299);
		$s = new Student($i, (string)$i, $skill, $dict, $PP, 60, $PT, 300);
		$list->push($s);
	}

	$project = array();

	for($i = 0; $i < 60; $i++) {
		$loop = rand(5, 10);
		$skill = array();
		for($j = 0; $j < $loop; $j++)
			$skill[$j] = chr(rand(0, 25) + 97);
		$p = new Project($i, (string)$i, $skill, $dict, 6);
		array_push($project, $p);
	}

	Project_Match($project, $list, $team);
	
	$people = 0;

	for($i = 0; $i < count($team); $i++)
	{
		$str = "";
		$num = 0;
		foreach($team[$i] as $v)
			if($v != -1)
				$num++;
		$MatchNum = array_fill(0, 3, 0);
		foreach($team[$i] as $value)
		{
			if($value == -1)
				continue;
			$people++;
			$str .= "    ";
			$str .= $list->get($value)->getName();
			$str .= "   skills: ";
			$current = $list->get($value);

			$toAdd = "";
			$assigned = false;
			foreach($current->getSkill() as $skill) {
				$toAdd .= $dict->getSkillByIndex($skill);
				$toAdd .= ", ";
				if($project[$i]->RequiredSkill($skill) && !$assigned)
				{
					$MatchNum[0]++;
					$assigned = true;
				}
			}
			$toAdd = substr($toAdd, 0, strlen($toAdd) - 2);
			$str .= $toAdd;

			
			$str .= "    preferred projects: ";
			$toAdd = "";
			$assigned = false;
			for($j = 0; $j < count($current->getPreferredProject()); $j++) {
				if($current->getPreferredProject()[$j] == -1)
					continue;
				$toAdd .= $j;
				$toAdd .= ", ";
			}
			if($list->get($value)->PreferredProject($i))
				$MatchNum[1]++;
			$toAdd = substr($toAdd, 0, strlen($toAdd) - 2);
			$str .= $toAdd;

			
			$str .= "    preferred teammate: ";
			$toAdd = "";
			for($j = 0; $j < count($current->getPreferredTeammate()); $j++) {
				if($current->getPreferredTeammate()[$j]) {
					$toAdd .= $j;
					$toAdd .= ", ";
				}
			}
			foreach($team[$i] as $v)
			{
				if($list->get($value)->PreferredTeammate($v))
				{
					$MatchNum[2]++;
					break;
				}
			}
			$toAdd = substr($toAdd, 0, strlen($toAdd) - 2);
			$str .= $toAdd;
			$str .= "\r\n";
		}
		if($str == "")
			continue;
		$p_s = "    skills: ";
		for($j = 0; $j < count($project[$i]->getSkills()); $j++) {
			$value = $project[$i]->getSkills()[$j];
			if(!$value)
				continue;
			$p_s .= $dict->getSkillByIndex($j);
			$p_s .= ", ";
		}
		$p_s = substr($p_s, 0, strlen($p_s) - 2);
		echo $project[$i]->getName();
		echo $p_s;
		echo "\r\n";
		echo $str;
		echo "Number of people have at least 1 matched skills: {$MatchNum[0]} out of {$num}\r\n";
		echo "Number of people in their preferred project: {$MatchNum[1]} out of {$num}\r\n";
		echo "Number of people working with their preferred teammate {$MatchNum[2]} out of {$num}\r\n\r\n";

	}

	echo "\r\n{$people} assigned\r\n";
?>