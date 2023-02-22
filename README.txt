/* Ionescu Andrei Ionut - 321CB */

				Tema 1

	Note generale:

	Am scris clasele cerute de problema, la care am adaugat 2 clase ajutatoare:
Initialization si Administration. In clasa Initialization am scris metode statice
care citesc din fisierele de intrare si creaza obiectele necesare rezolvarii
problemei. Metodele au fost facute statice pentru a nu avea nevoie de un obiect
de tip Initialization pentru a initializa datele de intrare. Astfel initializarea
se poate face mai usor. In clasa Administration am scris metodele care au fost
cerute de problema. Clasa are un atribut de tip vector de PublishingRetailer
care este initializat la constructie. Astfel, toate metodele din clasa au acces
la vectorul de retaileri care trebuie astfel initializat o singura data. Acesta
este un vector deoarece am scris metoda de initializare a retailer-ilor sa
intoarca un vector. Testele pentru apelurile functiilor implementate sunt citite
din fisierul teste.txt.

	Implementarea metodelor:

	Pentru metoda getBooksForPublishingRetailerID parcurgem vectorul de retaileri
o singura data, pana gasim retailer-ul cu ID-ul cautat. Dupa aceea, luam cartile
sale si le adaugam intr-o tabela hash. Apoi parcurgem grupurile sale editoriale
si brand-urile de publicatie si adaugam si cartile lor in tabela hash. Astfel,
daca o carte apare de 2 ori, in tabela va fi tinuta o singura data. La final
parcurgem tabela si adaugam toate elementele intr-o lista pe care o intoarcem.
Acest algoritm are o complexitate temporala de O(n).

	Pentru metoda getLanguagesForPublishingRetailerID aplicam o strategie
asemanatoare ca cea pentru metoda anterioara, doar ca mai intai gasim cartile
retailer-ului, iar apoi limbile acestor carti sunt adaugate in tabela hash.

	Pentru metoda getCountriesForBookID parcurgem toti retailerii si toate cartile
fiecaruia. Daca gasim cartea cautata la un retailer, atunci adaugam intr-o lista
toate tarile in care acesta publica. La final, dupa ce am gasit toti retailerii
care publica o anumita carte, sortam lista de tari, iar apoi eliminam duplicatele
printr-o singura parcurgere a listei.

	Pentru metoda getCommonBooksForRetailerIDs, gasim mai intai listele cu carti
ale celor 2 retaileri folosindu-ne de metoda getBooksForPublishingRetailerID. Apoi,
adaugam in tabela hash lista mai scurta, iar apoi cautam prin tabela fiecare element
din a 2-a lista si daca se afla, atunci este adaugata cartea in lista intersectie.
Cum cautarea printr-o tabela hash are in general complexitatea O(1), si cum cautam
toate elementele din a 2-a lista in tabela hash, complexitatea finala a acestei
metode este de O(n+m), asa cum s-a cerut.

	Pentru metoda getAllBooksForRetailerIDs, gasim listele cu carti pentru cei 2
retaileri, iar apoi adaugam toate elementele celor 2 liste intr-o tabela hash.
Daca un element se afla si intr-o lista si in alta, in tabela hash va fi trecut
doar o singura data. La final, trecem prin toate elementele tabelei hash si le
adaugam intr-o lista. Ca si la metoda precedenta, complexitatea folosind tabele
hash a acestui algoritm este de O(n+m).