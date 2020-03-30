create table weather
(
	id uuid
		constraint weather_pk
			primary key,
	timestamp timestamptz default now() not null,
	temp int not null,
	humidity int not null,
	location text not null,
	response json not null
);

