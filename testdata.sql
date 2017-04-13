insert into competitions (id, ref_id, name) values (1, 'test', 'Testing Competition');

insert into judges (id, name, login_secret, competition_id)
  values (1, 'Judge Judy', 'pass', 1);
insert into judges (id, name, login_secret, competition_id)
  values (2, 'Judge Ingyu', 'pass', 1);
insert into judges (id, name, login_secret, competition_id)
  values (3, 'Judge Q', 'pass', 1);
insert into judges (id, name, login_secret, competition_id)
  values (4, 'Judge Nelsen', 'pass', 1);

insert into teams (id, team_name, login_secret, member_names, competition_id)
  values (1, 'PaddysPub', 'pass', 'Dennis, Mac, Charlie, Frank, Dee', 1);
insert into teams (id, team_name, login_secret, member_names, competition_id)
  values (2, 'DelrayMisfits', 'pass', 'Brad, Lenny, Jason', 1);

insert into problems (id, ref_id, order_index, title, description, language, code, answer, competition_id)
  values (1, '2017helloworld', 0, 'Hello World!', 'Print "Hello World" to the screen.', 'Java', '"Hello World"', 'println("Hello World");', 1);

insert into problems (id, ref_id, order_index, title, language, description, code, answer, competition_id)
  values (2, '2017iseven', 1, 'Is Even', 'Java', 'Return 1 if even, 0 if odd.', 'n % 2', '(n-1)%2', 1);

INSERT INTO problems (id, ref_id, order_index, title, language, description, precode, code, postcode, answer, competition_id)
VALUES (3, 'shahdude', 2, 'Shah Dude', 'Java', 'shah, dude!', 'shah', 'adjective', 'dude', 'freaking', 1);

insert into problems (id, ref_id, order_index, title, language, description, code, precode, postcode, answer, competition_id)
  values (4, '2017xss', 3,
          '<script>console.log("xss title");</script>',
          'HTML',
          '<script>console.log("xss desc");</script>',
          '<script>console.log("xss code");</script>',
          '<script>console.log("xss pre");</script>',
          '<script>console.log("xss post");</script>',
          '<script>console.log("xss answer");</script>', 1);