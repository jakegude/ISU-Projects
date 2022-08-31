<?php
    function MergeSort(&$arr, &$score, $l, $r) {
		if($l < $r)
		{
			$m = $l + (int)(($r - $l) / 2);
			MergeSort($arr, $score, $l, $m);
			Mergesort($arr, $score, $m + 1, $r);
			Merge($arr, $score, $l, $m, $r);
		}
	}

	function Merge(&$arr, &$score, $l, $m, $r) {
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
	}

	function MSort(&$arr, &$score, &$position, $l, $r, $level) {
		if($l < $r)
		{
			$m = $l + (int)(($r - $l) / 2);
			MSort($arr, $score, $position, $l, $m, $level + 1);
			Msort($arr, $score, $position, $m + 1, $r, $level + 1);
			MergeAndLocate($arr, $score, $position, $l, $m, $r, $level);
		}
	}

	function MergeAndLocate (&$arr, &$score, &$position, $l, $m, $r, $level) {
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
	}

    function MSortIndex(&$arr, &$score, &$position, $l, $r, $level) {
		if($l < $r)
		{
			$m = $l + (int)(($r - $l) / 2);
			MSortIndex($arr, $score, $position, $l, $m, $level + 1);
			MsortIndex($arr, $score, $position, $m + 1, $r, $level + 1);
			MergeAndLocateIndex($arr, $score, $position, $l, $m, $r, $level);
		}
	}

	function MergeAndLocateIndex (&$arr, &$score, &$position, $l, $m, $r, $level) {
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
	}

?>