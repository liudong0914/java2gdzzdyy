ALTER TABLE edu_course_user ADD COLUMN validitytime VARCHAR(100);
update  edu_course_user SET validitytime =730 where vip =1;
ALTER TABLE sys_user_info ADD COLUMN vipmark VARCHAR(100);