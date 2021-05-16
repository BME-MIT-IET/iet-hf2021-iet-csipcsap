# Nem-funkcionális jellemzők vizsgálata

## Feladat
A Nem-funkcionális jellemzőket a teljesítmény (load), a stressz (stress) és egy picit a biztonsági (security) tesztekkel vizsgáltam. A feladat elvégzése során a saját gépemet használtam szerverként, mivel a projekt REST API-ja nem elérhető egy remote server-en sem.

### Előkészületek
Ahhoz, hogy az alább sorolt teszteket végre tudjam hajtani, több alkalmazásra is szükségem volt. Ezek az alábbiak:

- IntelliJ IDEA
- Postman
- Apache JMeter
- Java SDK 11

Mindenek előtt el kell indítani a REST API-t a saját gépünkön. Ehhez nem kell mást tenni, mint a github repository-t megnyitni az IntelliJ-ben, majd szimplán futtatni kell az alkalmazát. Ezután már elérhető lesz a localhost:8080-on az API.

Ahhoz, hogy a teszteknek legyen értelmük, egy kevés adatot is tettem a rendszerbe. Így ténylgesen ellenőrizhető, hogy a végpontok jól működnek. Ehhez a Postman-t használtam. Létrehoztam egy workspace-t a projektnek, és meginvitáltam bele néhány csapattársamat (amennyit az ingyenes verzió engedett). Itt létrehoztam collection-öket, hogy így tudjam őket külön-külön futtatni, de mégsem egyesével. A kezdeti adatokat a "Freelancer carrier 0.0" collection-ben hoztam létre. Regisztráltam 3 felhasználót (1-et be is léptettem), 3 járművet, 2 várost, és 5 csomagot. A tranzakciókból az előre megadottakat használtam. Természetesen ezek teljesítményét is ellenőriztem, de a Postman csak szekvenciálisan tud request-eket futtatni, ezért ezek nem annyira relevánsak a tesztek szempontjából. Egyedül az első regisztrálás csúszott ki az általam megszabott max. 500ms-os válaszidőből, minden request 200 OK-val tért vissza.

### Előzetes tesztelés
Még maradtam a Postman-ben és mivel ez a környezet kicsit ismerősebb volt, mint a JMeter (amit most fedeztem fel), ezért ebben kísérleteztem a teszt kialakításával. Végül több iterációnyi gondolkodás és próbálkozás után úgy döntöttem, hogy mivel az én gépem nem egy workstation, ezért sokkal lényegretörőbb, ha szimpla lekérdezésekkel próbálkozom leterhelni a szervert. Ezek úgyis sokszor jelennek meg működés közben is.


### Load / stress test (Teljesítmény / stressz teszt)
Ezen teszt arra szolgál, hogy az alkalmazást a várható forgalommal teszteljük, és megnézzük, hogy ez mennyire befolyásolja a használhatóságot. Mivel ez egy viszonylag kis projekt (egyenlőre), ezért nem számoltam nagyon sok felhasználóval. Végül amellett döntöttem, hogy egyszerre olyan 1000 felhasználót szimulálok. Ezt a szimulációt beletettem egy ciklusba, ami végrehajtotta egymás után 15-ször. Ezt a JMeter-rel csináltam, mert az tud párhuzamosan több szálon is lekérdezni egyszerre.

Letölteni JMetert (.zip -> kicsomagol -> kész) 
GUI mód (config) : bin -> jmeter.bat -> File -> Open -> .jmx file
# GUI módban ne futtassatok semmit össztörik alatta!!! Itt csak configolni szabad!!!
Itt lehet a Thread groupban ellenőrizni, hogy hány thread van:
- Load -> 1000 legyen
- Stress -> 10000 legyen

Itt vannak különböző nézetek is:
- Table view
- Summary view
Ha beírjátok valamelyik .jtl-t a File to read-be, akkor megjelennek az eredmények.

Futtatás:
cmd -> benavigálni a bin-be -> ott parancs :
jmeter -n -t path/to/file.jmx -o path/to/savingplace/otherfile.jtl
(a .jtl-nek nem kell léteznie, azt majd kigenerálja oda a jmeter)

Html generálás:
cmd -> benavigálni a bin-be -> ott parancs :
jmeter -g path/to/file.jtl -o path/to/output/folder

### SQL Injection
Szerintem a rövid verzióban ez eléggé egyértelmű.
