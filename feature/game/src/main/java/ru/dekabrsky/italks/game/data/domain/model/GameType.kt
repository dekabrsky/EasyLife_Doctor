package ru.dekabrsky.italks.game.data.domain.model

enum class GameType {
    Flappy,
    Leaves,
    TicTacToe,
    Barbecue,
    None;
    companion object{
        fun getByName(name: String) = values().find { it.name == name } ?: None
    }
}