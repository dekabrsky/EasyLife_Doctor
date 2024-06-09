package ru.dekabrsky.easylife.profile.di

import ru.dekabrsky.easylife.profile.data.api.ProfileApi
import toothpick.config.Module

class ProfileFeatureModule : Module() {
    init {
        bind(ProfileApi::class.java).toProvider(PatientApiProvider::class.java)
    }
}