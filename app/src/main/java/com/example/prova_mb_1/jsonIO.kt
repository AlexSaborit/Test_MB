package com.example.prova_mb_1

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime

object JsonIO {
    /*
    fun llegirLlistat(context: Context, reserves: Boolean){
        val jsonFilePath = context.filesDir.toString() + "/json/esdeveniments.json"
        var esdeveniments: MutableList<Esdeveniment> = mutableListOf()
        try {
            val jsonFile = FileReader(jsonFilePath)
            val jsonArray = JSONArray(jsonFile.readText()) // Llegir com un JSONArray

            // Recórrer cada esdeveniment de la llista JSON
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                // Llegir les entrades per a cada esdeveniment
                val entradesJsonArray = jsonObject.getJSONArray("entrades")
                val entrades: MutableList<Entrada> = mutableListOf()
                for (j in 0 until entradesJsonArray.length()) {
                    val entradaJsonObject = entradesJsonArray.getJSONObject(j)
                    val entrada = Entrada(
                        id = entradaJsonObject.getInt("id"),
                        nom_reserva = entradaJsonObject.getString("nom_reserva")
                    )
                    entrades.add(entrada)
                }

                // Crear l'objecte Esdeveniment i afegir-lo a la llista
                val esdeveniment = Esdeveniment(
                    id = jsonObject.getInt("id"),
                    nom = jsonObject.getString("nom"),
                    //imatge = jsonObject.getString("imatge"),
                    descripcio = jsonObject.getString("descripcio"),
                    data = LocalDateTime.parse(jsonObject.getString("data")),
                    idioma = jsonObject.getString("idioma"),
                    preu = jsonObject.getDouble("preu"),
                    numerat = jsonObject.getBoolean("numerat"),
                    tipus = jsonObject.getString("tipus"),
                    entrades = entrades,
                    especific1 = jsonObject.getString("especific1"),
                    especific2 = jsonObject.getString("especific2"),
                    especific3 = jsonObject.getString("especific3"),
                    especific4 = mutableListOf<String>().apply {
                        val especific4Array = jsonObject.getJSONArray("especific4")
                        for (j in 0 until especific4Array.length()) {
                            add(especific4Array.getString(j))
                        }
                    }
                )
                if (reserves){
                    if(esdeveniment.entrades.count() > 0) {
                        //afegirEsdeveniment(context,esdeveniment)
                        esdeveniments.add(esdeveniment)
                    }
                } else {
                    //afegirEsdeveniment(context,esdeveniment)
                    esdeveniments.add(esdeveniment)
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Error en llegir el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
            esdeveniments.clear()
        }
        Esdeveniment_Manager.esdeveniments = esdeveniments
    }
    fun escriureLlistat(context: Context) {
        val jsonFilePath = context.filesDir.toString() + "/json/esdeveniments.json"
        try {
            val jsonArrayEsdeveniments = JSONArray()

            // Recórrer la llista d'esdeveniments i afegir-los a l'array JSON
            for (esdeveniment in Esdeveniment_Manager.esdeveniments) {
                val jsonObjectEsdeveniment = JSONObject().apply {
                    put("id", esdeveniment.id)
                    put("nom", esdeveniment.nom)
                    put("descripcio", esdeveniment.descripcio)
                    put("data", esdeveniment.data.toString())
                    put("idioma", esdeveniment.idioma.toString())
                    put("preu", esdeveniment.preu.toDouble())
                    put("numerat", esdeveniment.numerat)
                    put("tipus", esdeveniment.tipus)
                    put("especific1", esdeveniment.especific1)
                    put("especific2", esdeveniment.especific2)
                    put("especific3", esdeveniment.especific3)
                    put("especific4", JSONArray(esdeveniment.especific4))

                    val JsonArrayentrades = JSONArray()
                    for (entrada in esdeveniment.entrades) {
                        if (entrada.id > 0 && entrada.id <= Esdeveniment_Manager.aforament){
                            val JsonObjectentrada = JSONObject().apply {
                                put("id", entrada.id)
                                put("nom_reserva", entrada.nom_reserva)
                            }
                            JsonArrayentrades.put(JsonObjectentrada)
                        }
                    }
                    put("entrades", JsonArrayentrades)
                }
                jsonArrayEsdeveniments.put(jsonObjectEsdeveniment)
            }

            // Escriure l'array JSON a l'arxiu
            val fileWriter = FileWriter(jsonFilePath)
            fileWriter.use { it.write(jsonArrayEsdeveniments.toString()) }
            //Toast.makeText(context, "Llistat d'esdeveniments actualitzat", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Error en escriure el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    fun idPerNouEsdeveniment():Int{
        var idLliure = -1
        var anterior = 0
        var maxim = -1
        var llistat = mutableListOf<Int>()

        Esdeveniment_Manager.esdeveniments.forEachIndexed{index, value ->
            llistat.add(value.id)
        }
        for (num in llistat){
            if (num > maxim){maxim = num}
        }
        for (id in 1..maxim){
            if (!llistat.contains(id) && idLliure < 0){
                idLliure = id//aquest id no s'usa
            }
        }
        if (idLliure < 0){
            idLliure = maxim + 1
        }
        return idLliure
    }
    fun cercarEsdeveniment(esdevenimentCercat: Esdeveniment): Int{
        var index = -1
        var esdeveniments = Esdeveniment_Manager.esdeveniments
        for (i in 0 until esdeveniments.count()){
            if (esdevenimentCercat.id == esdeveniments[i].id){
                index = i
            }
        }
        return index
    }
    //afegir un esdeveniment al llistat
    fun afegirEsdeveniment(context: Context, esdevenimentNou: Esdeveniment){
        Esdeveniment_Manager.esdeveniments.add(esdevenimentNou)
        escriureLlistat(context)
        llegirLlistat(context, false)
    }
    //modificar un esdeveniment
    fun modificarEsdeveniment(context: Context, esdevenimentModificat: Esdeveniment, index: Int): Boolean {
        var afegit: Boolean
        try {
            esdevenimentModificat.entrades = GestorEntrades.ordenarEntrades(esdevenimentModificat.entrades)
            Esdeveniment_Manager.esdeveniments[index] = esdevenimentModificat
            escriureLlistat(context)
            afegit = true
        } catch (e: Exception) {
            afegit = false
        }
        return afegit
    }
    //eliminar un esdeveniment
    fun eliminarEsdeveniment(context: Context, esdevenimentPerEliminar: Esdeveniment): Boolean {
        var eliminat: Boolean
        var total = Esdeveniment_Manager.esdeveniments.count()
        val indexPerEliminar = cercarEsdeveniment(esdevenimentPerEliminar)
        Esdeveniment_Manager.esdeveniments.removeAt(indexPerEliminar)
        escriureLlistat(context)
        if (total > Esdeveniment_Manager.esdeveniments.count()){
            eliminat = true
        } else {
            eliminat = false
        }
        GestorImatge.eliminarImatges(esdevenimentPerEliminar.id.toString(), context)
        return eliminat
    }
    fun ordenarMutableListInt(llista: MutableList<Int>): MutableList<Int>{
        var llistaOrdenada = mutableListOf<Int>()
        llistaOrdenada = llista.sorted().toMutableList()

        return llistaOrdenada
    }

     */
}