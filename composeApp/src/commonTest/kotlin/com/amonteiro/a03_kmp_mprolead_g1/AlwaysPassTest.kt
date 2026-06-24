package com.amonteiro.a03_kmp_mprolead_g1

import com.amonteiro.a03_kmp_mprolead_g1.data.remote.PhotographerDTO
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests unitaires "code commun" : compilés et exécutés sur TOUS les targets
 * (JVM/Desktop, Android host, iOS simulateur).
 *
 * Ces tests ne font AUCUN appel réseau : ils servent à vérifier que la
 * chaîne de CI exécute bien les tests et qu'on obtient un job vert.
 */
class AlwaysPassTest {

    @Test
    fun le_test_reussit_toujours() {
        // Un test volontairement trivial pour valider que la CI lance bien les tests.
        assertEquals(4, 2 + 2)
        assertTrue(true)
    }

    @Test
    fun photographerDTO_se_construit_correctement() {
        // Petit test de logique commune (sans réseau) sur le modèle de données.
        val photographe = PhotographerDTO(
            id = 1,
            stageName = "Bob la Menace",
            photoUrl = "https://example.com/bob.jpg",
            story = "Une histoire",
            portfolio = listOf("https://picsum.photos/1", "https://picsum.photos/2")
        )
        assertEquals(1, photographe.id)
        assertEquals("Bob la Menace", photographe.stageName)
        assertEquals(2, photographe.portfolio.size)
    }
}
