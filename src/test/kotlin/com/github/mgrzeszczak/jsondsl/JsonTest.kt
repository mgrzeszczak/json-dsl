package com.github.mgrzeszczak.jsondsl

import com.google.gson.JsonObject
import org.junit.Test

class JsonTest {

    @Test
    fun example() {
        val obj : JsonObject = Json.obj {
            "name" to "Jon"
            "surname" to "Snow"
            "allies" to array(
                    obj {
                        "name" to "Ghost"
                        "race" to "Direwolf"
                    },
                    obj {
                        "name" to "Sansa"
                        "surname" to "Stark"
                    }
            )
        }
        println(obj)
    }

}