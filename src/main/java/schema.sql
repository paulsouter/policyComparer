drop table policytag;
drop table tags;
drop table policy;
drop table party;


create table tags(
    tagsId number primary key auto_increment(0),
    tagName varchar
);
create table party(
    partyid number primary key auto_increment(0),
    partyName varchar not null,
    website varchar,
);
create table policy(
    policyId number primary key auto_increment(0),
    policyName varchar,
    policyText varchar not null,
    partyId number,
    dateupload timestamp not null,
    constraint fks foreign key (partyId) REFERENCES party(partyId),
);
create table policyTag(
    policynum number,
    tagsnum number,
    constraint fkp foreign key (policynum) references policy(policyId),
    constraint fkr foreign key (tagsnum) REFERENCES tags(tagsId),
    constraint pk primary key (policynum, tagsnum),
);

