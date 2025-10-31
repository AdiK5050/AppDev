package org.example.project.viewmodels

data class User(val name: String, val password: String)

class Database {
    val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun checkPassword(providedName: String, providedPassword: String) : Boolean {
        return users.any { (name, password) -> name == providedName && password == providedPassword }
    }
}