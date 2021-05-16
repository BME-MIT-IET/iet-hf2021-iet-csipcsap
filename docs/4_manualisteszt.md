# Manuális tesztek végrehajtása

## Feladat

A feladat során "exploratory testing"-et végeztem a kóddal, és a hozzá tartozó dokumentációval. Rendelkezésre állt egy Swagger dokumentáció az API-hoz, amelyet tüzetesen átolvasva néhány hibát vettem észre. Ezeket az Eredmények részben részletezem, valamint az Exploratory Testing című Issue-ban.

Az API továbbá a tesztelés során a csomagról történő információ lekérés közben hibát dobott. Ennek okát a kódban megtaláltam, és a fent említett Issue-ban dokumentált pull requestben javítottam.

## Eredmények, tanulságok

A feladat során a dokumentációban a következő hibákat vettem észre:
- **Helyesírási hiba a Model-eknél:** "owner" helyett "ownder" szerepel a model-eknél.
- **Inkonzisztencia a válaszok terén:** Vannak olyan hívások, amiknél az Unauthorized válasz dokumentálva van, míg másoknál nincs, pedig autentikációhoz vannak kötve.
- **Félrevezető változó nevek:** A Vehicle osztály raktere x, y, z dimenziókkal van jellemezve, amelyek utalhatnak a jármű, és a raktér dimenzióira is egyaránt.

Tanulságként azt vonnám le, hogy ez az API külön egy alkalmazáshoz készült, annak a szolgáltatásait elégíti ki, azonban néhány hard-code-olt konstans miatt, és a fent említett félrevezető változó nevek miatt nehézségeket okoz más hasonló kontextusú alkalmazásba beimplementálni.
