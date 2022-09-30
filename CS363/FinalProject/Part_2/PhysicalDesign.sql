-- Jake Gudenkauf

use group31;

-- did not use index for Q3, increased buffer pool size instead

-- create index for Q7
create index q7_index on tweets (tid);

-- create index for Q9
create index q9_index on user (numFollowers);

-- did not use index for Q16, increased buffer pool size instead

-- create index for Q18
create index q18_index on mentioned (tid);

-- create index for Q23
create index q23_index on tagged (tid);
