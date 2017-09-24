package com.github.mgrzeszczak.jsondsl

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.*


class Json private constructor() {

    companion object {

        fun obj(content: Builder.() -> Unit): JsonObject {
            val builder = Builder()
            builder.content()
            return builder.obj(content)
        }

        fun array(vararg args: Any): JsonArray {
            val array = JsonArray()
            args.forEach {
                when (it) {
                    is Char -> array.add(it)
                    is Number -> array.add(it)
                    is String -> array.add(it)
                    is Boolean -> array.add(it)
                    is JsonElement -> array.add(it)
                    else -> array.add(it.toString())
                }
            }
            return array
        }

        class Builder internal constructor() {
            private val objects = Stack<JsonObject>()
            private val current: JsonObject
                get() = objects.peek()

            fun array(vararg args: Any): JsonArray {
                return Json.array(*args)
            }

            fun obj(content: Builder.() -> Unit): JsonObject {
                val obj = JsonObject()
                objects.push(obj)
                this.content()
                objects.pop()
                return obj
            }

            infix fun String.to(value: Any) {
                when (value) {
                    is Char -> current.addProperty(this, value)
                    is Number -> current.addProperty(this, value)
                    is String -> current.addProperty(this, value)
                    is Boolean -> current.addProperty(this, value)
                    is JsonElement -> current.add(this, value)
                    else -> current.addProperty(this, value.toString())
                }
            }

            init {
                objects.add(JsonObject())
            }
        }
    }

}
