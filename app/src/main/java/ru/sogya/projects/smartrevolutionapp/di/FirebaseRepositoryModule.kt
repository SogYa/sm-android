package ru.sogya.projects.smartrevolutionapp.di

import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.usecases.firebase.SendEmailVerivicationUseCase
import com.sogya.domain.usecases.firebase.ticket.*
import com.sogya.domain.usecases.firebase.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class FirebaseRepositoryModule {
    @Provides
    fun providesCreateTicketUseCase(firebaseRepository: FirebaseRepository) =
        CreateTicketUseCase(firebaseRepository)

    @Provides
    fun providesDeleteTicketUseCase(firebaseRepository: FirebaseRepository) =
        DeleteTicketUseCase(firebaseRepository)

    @Provides
    fun providesReadAllTicketUseCase(firebaseRepository: FirebaseRepository) =
        ReadAllTicketUseCase(firebaseRepository)

    @Provides
    fun providesReadTicketByIdUseCase(firebaseRepository: FirebaseRepository) =
        ReadTicketByIdUseCase(firebaseRepository)

    @Provides
    fun providesCreateUserUseCase(firebaseRepository: FirebaseRepository) =
        CreateUserUseCase(firebaseRepository)

    @Provides
    fun providesLogInUserUseCase(firebaseRepository: FirebaseRepository) =
        LogInUserUseCase(firebaseRepository)

    @Provides
    fun providesLogOutUseCase(firebaseRepository: FirebaseRepository) =
        LogOutUseCase(firebaseRepository)

    @Provides
    fun providesReadUserUseCase(firebaseRepository: FirebaseRepository) =
        ReadUserUseCase(firebaseRepository)

    @Provides
    fun providesWriteServersUseCase(firebaseRepository: FirebaseRepository) =
        WriteServersUseCase(firebaseRepository)

    @Provides
    fun providesWriteUserUseCase(firebaseRepository: FirebaseRepository) =
        WriteUserUseCase(firebaseRepository)

    @Provides
    fun providesSendEmailVerivicationUseCase(firebaseRepository: FirebaseRepository) =
        SendEmailVerivicationUseCase(firebaseRepository)
}