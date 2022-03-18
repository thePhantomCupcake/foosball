package com.epiuse.labs.epifoos.player

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Players : IdTable<String>("player") {
    override val id: Column<EntityID<String>> = Players.varchar("email", 255)
        .entityId()
        .uniqueIndex("player_email_uniq_idx")
    var username = varchar("username", 255)
    var firstName = varchar("first_name", 255)
    var lastName = varchar("last_name", 255)
}

class Player(id: EntityID<String>) : Entity<String>(id) {

    companion object : EntityClass<String, Player>(Players)

    var username by Players.username
    var firstName by Players.firstName
    var lastName by Players.lastName
}