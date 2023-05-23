package com.flowyh.letmecook.controllers.di

import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import com.flowyh.letmecook.controllers.repositories.FirestoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirestoreModule {

  @Binds
  @Singleton
  abstract fun bindFirestoreRepository(
    firestoreRepositoryImpl: FirestoreRepositoryImpl
  ): FirestoreRepository
}