create table IF NOT EXISTS LASTPUSH (
                          PUSH_TYPE varchar2 ,
                          TIMESTAMP timestamp,
                          PRIMARY KEY (PUSH_TYPE)
);
create table IF NOT EXISTS ALARM (
                       ID TINYINT not null DEFAULT 1,
                       MODE varchar2,
                       constraint Alarm_PK PRIMARY KEY (ID),
                       CONSTRAINT Alarm_OnlyOneRow CHECK (ID = 1)
);