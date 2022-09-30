use group31;

SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

-- SELECT @@innodb_buffer_pool_size;
-- Set back to default
-- SET GLOBAL innodb_buffer_pool_size=134217728;
-- Set to larger value
-- SET GLOBAL innodb_buffer_pool_size=268435456;

update user
set ofstate = 'AL'
where ofstate like '%Alabama%';

update user
set ofstate = 'AK'
where ofstate like '%Alaska%';

update user
set ofstate = 'AZ'
where ofstate like '%Arizona%';

update user
set ofstate = 'AR'
where ofstate like '%Arkansas%';

update user
set ofstate = 'CA'
where ofstate like '%California%';

update user
set ofstate = 'CO'
where ofstate like '%Colorado%';

update user
set ofstate = 'CT'
where ofstate like '%Connecticut%';

update user
set ofstate = 'DE'
where ofstate like '%Delaware%';

update user
set ofstate = 'FL'
where ofstate like '%Florida%';

update user
set ofstate = 'GA'
where ofstate like '%Georgia%';

update user
set ofstate = 'HI'
where ofstate like '%Hawaii%';

update user
set ofstate = 'IL'
where ofstate like '%Illinois%';

update user
set ofstate = 'ID'
where ofstate like '%Idaho%';

update user
set ofstate = 'IN'
where ofstate like '%Indiana%';

update user
set ofstate = 'IA'
where ofstate like '%Iowa%';

update user
set ofstate = 'KS'
where ofstate like '%Kansas%';

update user
set ofstate = 'KY'
where ofstate like '%Kentucky%';

update user
set ofstate = 'LA'
where ofstate like '%Louisiana%';

update user
set ofstate = 'ME'
where ofstate like '%Maine%';

update user
set ofstate = 'MD'
where ofstate like '%Maryland%';

update user
set ofstate = 'MA'
where ofstate like '%Massachusetts%';

update user
set ofstate = 'MI'
where ofstate like '%Michigan%';

update user
set ofstate = 'MN'
where ofstate like '%Minnesota%';

update user
set ofstate = 'MS'
where ofstate like '%Mississippi%';

update user
set ofstate = 'MO'
where ofstate like '%Missouri%';

update user
set ofstate = 'MT'
where ofstate like '%Montana%';

update user
set ofstate = 'NE'
where ofstate like '%Nebraska%';

update user
set ofstate = 'NV'
where ofstate like '%Nevada%';

update user
set ofstate = 'NH'
where ofstate like '%New Hampshire%';

update user
set ofstate = 'NJ'
where ofstate like '%New Jersey%';

update user
set ofstate = 'NM'
where ofstate like '%New Mexico%';

update user
set ofstate = 'NY'
where ofstate like '%New York%';

update user
set ofstate = 'NC'
where ofstate like '%North Carolina%';

update user
set ofstate = 'ND'
where ofstate like '%North Dakota%';

update user
set ofstate = 'OH'
where ofstate like '%Ohio%';

update user
set ofstate = 'OK'
where ofstate like '%Oklahoma%';

update user
set ofstate = 'OR'
where ofstate like '%Oregon%';

update user
set ofstate = 'PA'
where ofstate like '%Pennsylvania%';

update user
set ofstate = 'RI'
where ofstate like '%Rhode Island%';

update user
set ofstate = 'SC'
where ofstate like '%South Carolina%';

update user
set ofstate = 'SD'
where ofstate like '%South Dakota%';

update user
set ofstate = 'TN'
where ofstate like '%Tennessee%';

update user
set ofstate = 'TX'
where ofstate like '%Texas%';

update user
set ofstate = 'UT'
where ofstate like '%Utah%';

update user
set ofstate = 'VT'
where ofstate like '%Vermont%';

update user
set ofstate = 'VA'
where ofstate like '%Virginia%';

update user
set ofstate = 'WV'
where ofstate like '%West Virginia%';

update user
set ofstate = 'WA'
where ofstate like '%Washington%';

update user
set ofstate = 'WI'
where ofstate like '%Wisconsin%';

update user
set ofstate = 'WY'
where ofstate like '%Wyoming%';