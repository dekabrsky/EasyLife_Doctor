package ru.dekabrsky.italks.profile.di

import ru.dekabrsky.italks.profile.data.api.ProfileApi
import toothpick.config.Module

class ProfileFeatureModule : Module() {
    init {
        bind(ProfileApi::class.java).toProvider(PatientApiProvider::class.java)
    }
}