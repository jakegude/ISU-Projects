input_lines = LOAD 'gaz_tracts_national.txt' USING PigStorage('\n') AS (usps: chararray, geoid: double, pop10: double, hu10: double, aland_la: double, awater: double, aland_sqmi:double, awater_sqmi: double, intptlat: double, intptlong: double);

--filter by aland
--outputlines = FILTER input_lines By aland_la is not null;

--order the records by count
--ordered_outputlines = ORDER outputlines BY aland_la DESC;
ordered_outputlines = ORDER input_lines BY aland_la DESC;

--limit output to 10 records
final_outputlines = limit ordered_outputlines 10;

--output
STORE final_outputlines INTO 'exp1/output/';
DUMP ordered_outputlines;
