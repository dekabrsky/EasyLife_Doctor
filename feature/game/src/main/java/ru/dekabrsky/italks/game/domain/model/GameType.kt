package ru.dekabrsky.italks.game.domain.model

enum class GameType {
    Flappy,
    Leaves,
    TicTacToe,
    Barbecue,
    None;

    companion object{
        fun getByName(name: String) = GameType.values().find { it.name == name } ?: None
    }
}