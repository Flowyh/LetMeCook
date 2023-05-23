package com.flowyh.letmecook.controllers.repositories

import android.app.Application
import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
  // TODO: Should we pass Firestore instance here, or is it a global singleton?
  private val appCtx: Application
) : FirestoreRepository {
  // TODO: fill this
  init {
    // TODO: init code here
  }


}
