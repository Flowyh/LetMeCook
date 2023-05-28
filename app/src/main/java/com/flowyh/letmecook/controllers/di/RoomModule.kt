package com.flowyh.letmecook.controllers.di

import android.content.Context
import androidx.room.Room
import com.flowyh.letmecook.controllers.interfaces.RoomRepositoryDao
import com.flowyh.letmecook.controllers.repositories.IngredientsListConverter
import com.flowyh.letmecook.controllers.repositories.RecipeDetailsConverter
import com.flowyh.letmecook.controllers.repositories.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

  @Provides
  @Singleton
  fun provide(@ApplicationContext context: Context): RoomRepository =
    Room.databaseBuilder(
      context.applicationContext,
      RoomRepository::class.java, "local_database")
      .addTypeConverter(RecipeDetailsConverter())
      .addTypeConverter(IngredientsListConverter())
      .allowMainThreadQueries()
      .fallbackToDestructiveMigration()
      .build()

  @Provides
  @Singleton
  fun provideDao(roomRepository: RoomRepository): RoomRepositoryDao =
    roomRepository.repositoryDao()

}
