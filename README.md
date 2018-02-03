# OOP-Muzicka-Prodavnica
**Muzicka prodavnica za predmet OOP, VISER, 2017. godine**

Muzički servis je aplikacija sa bazom koja ima izvođače, albume i pesme, kao i korisnike koji koriste aplikaciju i korisnike koji ažuriraju muzički servis. Za potrebe projekta, potrebno je kreirati „helper“ paket sa klasom „Trajanje“. Klasa „Trajanje“ ima privatne atribute za sate, minute i sekunde i služi da predstavi trajanje pesama, odnosno albuma. Potrebno je implementirati metode koje aplikaciji omogućavaju da izračunaju i formatiraju trajanje, i za listu objekata trajanja vrate ukupno trajanje.

# Početak rada aplikacije

Kada se aplikacija pokrene, pojavljuje se poruka koja od korisnika traži da unese svoje
jedinstveno korisničko ime i lozinku. Korisnička imena korisnika imaju karakteristiku da korisnici koji su kupci u aplikaciji imaju korisnička imena koja počinju sa slovom „k“ dok administratorima počinju sa slovom „a“.

Svaki korisnik ima tri šanse za prijavu na aplikaciju. Kada korisnik ne unese odgovarajuće podatke, treba da se baci izuzetak sa porukom da podaci koje unose nisu validni i da mu ponudi da se ponovo prijavi ili da ugasi aplikaciju. Ukoliko sva tri puta unese pogrešne podatke, aplikacija prestaje sa radom. Ukoliko unese tačne podatke, u zavisnosti od korinika otvara se odgovarajući meni.

# Korisnik

Ukoliko se korisnik prijavi na aplikaciju, otvara mu se sledeći meni:

**1. Prikaz biblioteke kupljenih albuma i pesama**

Izlistava korisniku sve pesme i albume koje je stavio u biblioteku. Individualne pesme potrebno je prikazati prve nakon čega je potrebno prikazati albume ali u prikazu albuma treba prikazati samo ime izvođača, naziv albuma i ukupno trajanje.

**2. Pretraga izvođača u muzičkoj aplikaciji**

Korisnik unosi izvođača kog želi da pronađe u aplikaciji. Ukoliko izvođač ne postoji potrebno je baciti izuzetak da traženi izvođač ne postoji i korisnika vratiti na meni.
Ukoliko izvođač postoji potrebno je prikazati informacije o izvođaču i u nastavku sledeći meni:

	a) Prikaz pesama i albuma
	b) Dodavanje pesme u biblioteku
	c) Dodavanje albuma u biblioteku
	d) Nazad

**3. Dodavanje pesme u biblioteku**

Omogućava korisniku da na osnovu unetog ID-a pesme, smesti pesmu u svoju biblioteku. Ukoliko pesma već postoji u korisnikovoj biblioteci potrebno je baciti izuzetak sa odgovarajućom porukom.

**4. Dodavanje albuma u biblioteku**

Omogućava korisniku da na osnovu unetog ID-a albuma, smesti album u svoju biblioteku. Ukoliko album već postoji u korisnikovoj biblioteci potrebno je baciti izuzetak sa odgovarajućom porukom.

**5. Odjava**

Korisnik se odjavljuje iz aplikacije i preusmerava se na meni za prijavu.

# Administrator

Ukoliko se administrator prijavi na aplikaciju otvara mu se sledeći meni:
1. Unos pesme, izvođača, ili albuma
2. Ažuriranje pesme, izvođača, ili albuma
3. Brisanje pesme, izvođača, ili albuma
4. Odjava

Sve akcije (korisničko ime administratora i sav tekstualni unos) se snimaju sa datumom i vremenom u datoteku „aktivnosti.log“.
