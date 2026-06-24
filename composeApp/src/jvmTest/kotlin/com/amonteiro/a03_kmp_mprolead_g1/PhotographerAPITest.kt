package com.amonteiro.a03_kmp_mprolead_g1

import com.amonteiro.a03_kmp_mprolead_g1.data.remote.PhotographerAPI
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * 2e test demandé par le prof : faire un appel à PhotographerAPI
 * "juste pour vérifier que la clé d'API a bien été récupérée dans les secrets GitHub".
 *
 * IMPORTANT : le but n'est PAS que l'API renvoie de vraies données, mais de prouver
 * que le SECRET GitHub a bien été injecté dans le build (clé non vide).
 *  - La valeur du secret peut être n'importe quoi (même fictive).
 *  - AVANT d'avoir créé le secret : la clé est vide -> le test ÉCHOUE.
 *  - APRÈS avoir créé le secret PHOTOGRAPHER_API_KEY : la clé est présente -> le test PASSE.
 *
 * Tourne dans le job CI via la tâche :composeApp:jvmTest (moteur OkHttp côté JVM).
 */
class PhotographerAPITest {

    @Test
    fun la_cle_api_est_bien_recuperee_du_secret() {
        // 1) Vérifie que la clé du secret GitHub a bien été injectée (non vide).
        val cle = BuildConfig.PHOTOGRAPHER_API_KEY
        assertTrue(
            cle.isNotBlank(),
            "Clé vide : le secret PHOTOGRAPHER_API_KEY n'a pas été récupéré sur GitHub."
        )

        // 2) Fait réellement un appel à PhotographerAPI (la clé part dans l'URL).
        //    On n'échoue PAS si le serveur refuse la clé : seule compte la présence de la clé.
        runBlocking {
            val client = HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
                }
                install(HttpTimeout) { requestTimeoutMillis = 10_000 }
            }
            val api = PhotographerAPI(client)
            try {
                val photographers = api.loadPhotographers()
                println("Appel API OK, photographes reçus : ${photographers.size}")
            } catch (e: Exception) {
                println("Appel API effectué (réponse serveur : ${e.message})")
            } finally {
                api.close()
            }
        }
    }
}
