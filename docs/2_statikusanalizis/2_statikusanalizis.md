# Statikus analízis eszköz futtatása

## Feladat
A SonarQube szoftver telepítése és a szerver indítása után a már meglévő Maven builddel futtattam az analízist.
Az első futtatások után az analízis build failure miatt nem sikerült.
Az adatbáziselérés módosítása után az analízis lefutott a következő eredményekkel:

![](p04_results.png)

2 *major* szintű bug

![](p09_bug01.png)
![](p10_bug02.png)

2 *critical* szintű sebezhetőség

![](p11_vuln01.png)
![](p12_vuln02.png)

52 code smell

![](p07_smell.png)

Ezek közül a súlyosabbak:

![](p08_smell_blocker.png)
1 darab *blocker* szintű

![](p16_smell_critical.png)
5 darab *critical* szintű

A Gradle bevezetése és a kapott hibák egy részének kijavítása után a következő eredmények születtek:

![](p17.png)

## Eredmények, tanulságok

A kódminőségen sikerült javítanom. Azt a következtetést vontam le, hogy bármely projekt esetén érdemes néhány alkalommal statikus analízist futtatnunk. Ezen a példán is kiderült, hogy a program olyan potenciális hibákat is felderíthet, amelyek nem szembeötlőek, de egy éles rendszernél komoly problémákat okozhatnak.