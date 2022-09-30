input_lines = LOAD 'network_trace' USING PigStorage('\n') AS (time:chararray, ip:chararray, ipaddr1:chararray, gt:chararray, ipaddr2:chararray, protocol:chararray, num:int);

--filter by aland
--outputlines = FOREACH input_lines GENERATE (protocol);
--output_lines = FILTER outputlines by protocol == 'tcp';

--order the records by count
ordered_outputlines = ORDER input_lines BY protocol DESC;
--ordered_outputlines = ORDER output_lines BY protocol DESC;

--limit output to 10 records
final_outputlines = limit ordered_outputlines 10;

--output
STORE final_outputlines INTO 'exp2/output/';
DUMP outputlines;
