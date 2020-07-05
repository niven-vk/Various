# -*- coding: utf-8 -*-

dane_e = [["indeksy", 1, 2, 3],
          [1, "Abonament za Internet", 40.00, 1],
          [2, "Bezpieczny Internet", 9.90, 3],
          [3, "Telewizja Pakiet pełny", 80.00, 2],
          [4, "GigaNagrywarka", 15.00, 2],
          [5, "Rabat za Internet", -20.00, 1]]

podsumowanie_e = [["indeksy", 1, 2, 3],
                  [1, "Internet", 1, 1],
                  [2, "Usługi Dodatkowe", 3, 2],
                  [3, "Telewizja", 2, 3]]
array_length=len

# w treści zadania jest założenie że mam dostęp do sort_by więc dla wygody skorzystam z gotowej funkcji sorted

def sort_by(tablica_glowna, X):
    return(sorted(tablica_glowna, key=lambda k: k[X]))

# idea jest taka:
# tworzę słownik gdzie kluczami jest identyfikator i obliczam jego kwotę
# następnie sortuję kategorie podsumowania/nazwy pozycji podsumowania według zadanej kolejności
# na końcu przypisuję kategoriom ich kwoty według pasujących identyfikatorów

def main(dane, podsumowanie):

    # tu można użyć defaultdict i operatora += żeby było ładniej
    amounts = {}
    for index in range(1, array_length(dane)):
        #get zwraca wartość dla klucza równego pierwszemu argumentowi,
        #jeśli nie znajdzie wartości to zwraca drugi argument
        amounts[dane[index][3]] = amounts.get(
            dane[index][3], 0) + dane[index][2]

    sorted_pods = sort_by(podsumowanie[1:], 3)

    print("{: ^30} {: ^30}".format("Kategoria podsumowania", "Kwota"))
    for i in range(array_length(sorted_pods)):
        print("{: <30} {:.2f}".format(sorted_pods[i][1], amounts.get(sorted_pods[i][2],0)))

main(dane_e, podsumowanie_e)
