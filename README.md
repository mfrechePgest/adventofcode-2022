# adventofcode-2022

Mes solutions pour le [Advent of Code 2022](https://adventofcode.com/)

Ca va du très sale au moins sale, selon l'humeur du jour, la qualité de mon réveil et le temps disponible...

Usage : 

## Init d'un nouveau jour

`./gradlew createDay -Pday=09` pour générer le folder du jour 09, par exemple.

Si un fichier token.txt est présent à la racine et contient le token adventofcode, alors le folder contiendra directement le fichier `input.txt` contenant l'input du jour

On peut récup le token dans les cookies du browser quand on charge l'input via ce dernier. [Voir des instructions ici](https://github.com/wimglenn/advent-of-code-wim/issues/1)

## Run

`./gradlew run` pour tout lancer

`./gradlew :day05:run` pour lancer le jour 5, par exemple

## Tests

`./gradlew test` pour lancer tous les tests

`./gradlew :day05:test` pour lancer les tests du jour 5, par exemple