INSERT INTO election_type(code, name)
VALUES
    ('PRH', 'Predsjednički izbori'),
    ('LOKGN', 'Lokalni izbori - Gradonačelnik');

INSERT INTO election(type, year, event_date, deadline, status)
SELECT id, 2019, '2019-12-06', '2019-11-20', 'Završen'
FROM election_type
WHERE code = 'PRH';

INSERT INTO election(type, year, event_date, deadline, status)
SELECT id, 2021, '2021-05-16', '2021-04-30', 'Završen'
FROM election_type
WHERE code = 'LOKGN';

INSERT INTO election(type, year, event_date, deadline, status)
SELECT id, 2024, '2024-07-06', '2024-06-19', 'U tijeku'
FROM election_type
WHERE code = 'PRH';

INSERT INTO election(type, year, event_date, deadline, status)
SELECT id, 2024, '2024-06-14', '2024-05-28', 'U tijeku'
FROM election_type
WHERE code = 'LOKGN';

INSERT INTO person(first_name, last_name, dob, address, pid, nationality, sex)
VALUES
    ('Tomislav', 'Tomašević', '1982-01-13', 'Ilica 1, 10000 Zagreb', '10000000000', 'Hrvat', 'M'),
    ('Miroslav', 'Škoro', '1962-07-29', 'Trg bana Josipa Jelačića 3, 10000 Zagreb', '20000000000', 'Hrvat', 'M'),
    ('Jelena', 'Pavičić Vukičević', '1975-09-02', 'Maksimirska cesta 34, 10000 Zagreb', '30000000000', 'Hrvatica', 'Ž'),
    ('Davor', 'Filipović', '1984-06-10', 'Savska cesta 66, 10000 Zagreb', '40000000000', 'Hrvat', 'M'),
    ('Joško', 'Klisović', '1968-08-26', 'Jurišićeva ulica 19, 10000 Zagreb', '50000000000', 'Hrvat', 'M'),
    ('Vesna', 'Škare Ožbolt', '1961-06-20', 'Palmotićeva ulica 22, 10000 Zagreb', '60000000000', 'Hrvatica', 'Ž'),
    ('Zvonimir', 'Troskot', '1984-10-05', 'Radnička cesta 80, 10000 Zagreb', '70000000000', 'Hrvat', 'M'),
    ('Davor', 'Nađi', '1989-10-30', 'Nova cesta 7, 10000 Zagreb', '80000000000', 'Hrvat', 'M'),
    ('Anka', 'Mrak-Taritaš', '1959-11-24', 'Petrinjska ulica 31, 10000 Zagreb', '90000000000', 'Hrvatica', 'Ž'),
    ('Željko', 'Tokić', '1970-01-01', 'Vukovarska ulica 64, 10000 Zagreb', '11111111111', 'Hrvat', 'M'),
    ('Zoran', 'Milanović', '1966-10-30', 'Ulica grada Vukovara 269a, 10000 Zagreb', '22222222222', 'Hrvat', 'M'),
    ('Kolinda', 'Grabar-Kitarović', '1968-04-29', 'Korzo 8, 51000 Rijeka', '33333333333', 'Hrvatica', 'Ž'),
    ('Mislav', 'Kolakušić', '1969-09-15', 'Splitska ulica 22, 21000 Split', '44444444444', 'Hrvat', 'M'),
    ('Dario', 'Juričan', '1976-12-16', 'Ulica kralja Zvonimira 12, 23000 Zadar', '55555555555', 'Hrvat', 'M'),
    ('Dalija', 'Orešković', '1977-06-18', 'Ulica Ivana Gundulića 4, 31000 Osijek', '66666666666', 'Hrvatica', 'Ž'),
    ('Ivan', 'Pernar', '1985-10-14', 'Obala Kneza Domagoja 1, 23000 Zadar', '77777777777', 'Hrvat', 'M'),
    ('Katarina', 'Peović', '1974-11-16', 'Ulica Ante Starčevića 9, 21000 Split', '88888888888', 'Hrvatica', 'Ž'),
    ('Dejan', 'Kovač', '1984-01-01', 'Franjevački trg 1, 42000 Varaždin', '99999999999', 'Hrvat', 'M'),
    ('Anto', 'Đapić', '1958-08-22', 'Ulica Matice hrvatske 15, 31000 Osijek', '11111111110', 'Hrvat', 'M'),
    ('Nedjeljko', 'Babić', '1968-01-01', 'Ulica Nikole Tesle 8, 52100 Pula', '22222222220', 'Hrvat', 'M'),
    ('Ivan', 'Horvat', '1980-01-01', 'Šetalište Ivana Meštrovića 5, 21000 Split', '12345678900', 'Hrvat', 'M');

INSERT INTO position(code, name)
VALUES
    ('PRH', 'Predsjednik Republike Hrvatske'),
    ('LOKGN', 'Gradonačelnik');

INSERT INTO party(code, name)
VALUES
    ('HSSČKŠ', 'Hrvatska stranka svih čakavaca kajkavaca i štokavaca'),
    ('DESNO', 'Demokratski savez nacionalne obnove'),
    ('HDZ', 'Hrvatska demokratska zajednica'),
    ('HSLS', 'Hrvatska socijalno-liberalna stranka'),
    ('SDP', 'Socijaldemokratska partija Hrvatske'),
    ('RF', 'Radnička fronta'),
    ('SIP', 'Stranka Ivana Pernara'),
    ('GLAS', 'Građansko-liberalni savez'),
    ('FOKUS', 'Fokus'),
    ('365', 'Bandić Milan 365 - Stranka rada i solidarnosti'),
    ('DP', 'Domovinski pokret'),
    ('VŠONL', 'Vesna Škare Ožbolt - Nezavisna lista'),
    ('MOŽEMO', 'Možemo!'),
    ('MOST', 'Most');

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2019-11-18'
FROM election e, person pe, position po, party pa
WHERE e.year = 2019 AND pe.pid = '22222222220' AND po.code = 'PRH' AND pa.code = 'HSSČKŠ';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2019-11-13'
FROM election e, person pe, position po, party pa
WHERE e.year = 2019 AND pe.pid = '11111111110' AND po.code = 'PRH' AND pa.code = 'DESNO';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2019-11-14'
FROM election e, person pe, position po, party pa
WHERE e.year = 2019 AND pe.pid = '33333333333' AND po.code = 'PRH' AND pa.code = '365';

INSERT INTO candidate (election, person, position, applied)
SELECT e.id, pe.id, po.id, '2019-11-16'
FROM election e, person pe, position po
WHERE e.year = 2019 AND pe.pid = '55555555555' AND po.code = 'PRH';

INSERT INTO candidate (election, person, position, applied)
SELECT e.id, pe.id, po.id, '2019-11-17'
FROM election e, person pe, position po
WHERE e.year = 2019 AND pe.pid = '44444444444' AND po.code = 'PRH';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2019-11-18'
FROM election e, person pe, position po, party pa
WHERE e.year = 2019 AND pe.pid = '99999999999' AND po.code = 'PRH' AND pa.code = 'HSLS';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2019-11-12'
FROM election e, person pe, position po, party pa
WHERE e.year = 2019 AND pe.pid = '22222222222' AND po.code = 'PRH' AND pa.code = 'SDP';

INSERT INTO candidate (election, person, position, applied)
SELECT e.id, pe.id, po.id, '2019-11-10'
FROM election e, person pe, position po
WHERE e.year = 2019 AND pe.pid = '66666666666' AND po.code = 'PRH';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2019-11-18'
FROM election e, person pe, position po, party pa
WHERE e.year = 2019 AND pe.pid = '88888888888' AND po.code = 'PRH' AND pa.code = 'RF';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2019-11-10'
FROM election e, person pe, position po, party pa
WHERE e.year = 2019 AND pe.pid = '77777777777' AND po.code = 'PRH' AND pa.code = 'SIP';

INSERT INTO candidate (election, person, position, applied)
SELECT e.id, pe.id, po.id, '2019-11-06'
FROM election e, person pe, position po
WHERE e.year = 2019 AND pe.pid = '20000000000' AND po.code = 'PRH';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-30'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '40000000000' AND po.code = 'LOKGN' AND pa.code = 'HDZ';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-22'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '50000000000' AND po.code = 'LOKGN' AND pa.code = 'SDP';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-30'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '90000000000' AND po.code = 'LOKGN' AND pa.code = 'GLAS';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-16'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '80000000000' AND po.code = 'LOKGN' AND pa.code = 'FOKUS';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-21'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '30000000000' AND po.code = 'LOKGN' AND pa.code = '365';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-30'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '60000000000' AND po.code = 'LOKGN' AND pa.code = 'VŠONL';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-16'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '20000000000' AND po.code = 'LOKGN' AND pa.code = 'DP';

INSERT INTO candidate (election, person, position, applied)
SELECT e.id, pe.id, po.id, '2021-04-26'
FROM election e, person pe, position po
WHERE e.year = 2021 AND pe.pid = '11111111111' AND po.code = 'LOKGN';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-23'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '10000000000' AND po.code = 'LOKGN' AND pa.code = 'MOŽEMO';

INSERT INTO candidate (election, person, position, party, applied)
SELECT e.id, pe.id, po.id, pa.id, '2021-04-18'
FROM election e, person pe, position po, party pa
WHERE e.year = 2021 AND pe.pid = '70000000000' AND po.code = 'LOKGN' AND pa.code = 'MOST';

INSERT INTO candidate (election, person, position, applied)
SELECT e.id, pe.id, po.id, '2021-05-02'
FROM election e, person pe, position po
WHERE e.year = 2021 AND pe.pid = '12345678900' AND po.code = 'LOKGN';