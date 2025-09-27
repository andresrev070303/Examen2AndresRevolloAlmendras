package com.calyrsoft.ucbp1.di


import com.calyrsoft.ucbp1.features.dollar.data.database.AppRoomDatabase
import com.calyrsoft.ucbp1.features.github.data.api.GithubService
import com.calyrsoft.ucbp1.features.post.data.api.PostsService
import com.calyrsoft.ucbp1.features.movie.data.api.TmdbService
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.github.data.datasource.GithubRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.data.datasource.MoviesRemoteDataSource
import com.calyrsoft.ucbp1.features.post.data.datasource.PostsRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.DollarRepository
import com.calyrsoft.ucbp1.features.exchange.data.repository.ExchangeRateRepository
import com.calyrsoft.ucbp1.features.github.data.repository.GithubRepository
import com.calyrsoft.ucbp1.features.login.data.repository.LoginRepository
import com.calyrsoft.ucbp1.features.movie.data.repository.MoviesRepository
import com.calyrsoft.ucbp1.features.post.data.repository.PostsRepository
import com.calyrsoft.ucbp1.features.whatsapp.data.repository.WhatsappRepository
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import com.calyrsoft.ucbp1.features.exchange.domain.repository.IExchangeRateRepository
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository
import com.calyrsoft.ucbp1.features.login.domain.repository.ILoginRepository
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository
import com.calyrsoft.ucbp1.features.post.domain.repository.IPostsRepository
import com.calyrsoft.ucbp1.features.whatsapp.domain.repository.IWhatsappRepository
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import com.calyrsoft.ucbp1.features.login.domain.usecase.FindByNameAndPasswordUseCase
import com.calyrsoft.ucbp1.features.login.domain.usecase.FindByNameUseCase
import com.calyrsoft.ucbp1.features.github.domain.usecase.FindByNickNameUseCase
import com.calyrsoft.ucbp1.features.post.domain.usecase.GetAllPostsUseCase
import com.calyrsoft.ucbp1.features.post.domain.usecase.GetCommentsByPostIdUseCase
import com.calyrsoft.ucbp1.features.exchange.domain.usecase.GetExchangeRateUseCase
import com.calyrsoft.ucbp1.features.whatsapp.domain.usecase.GetFirstWhatsappNumberUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usecase.GetPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.profile.domain.usecase.UpdateUserProfileUseCase
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarViewModel
import com.calyrsoft.ucbp1.features.exchange.presentation.ExchangeRateViewModel
import com.calyrsoft.ucbp1.features.profile.presentation.ForgotPasswordViewModel
import com.calyrsoft.ucbp1.features.github.presentation.GithubViewModel
import com.calyrsoft.ucbp1.features.login.presentation.LoginViewModel
import com.calyrsoft.ucbp1.features.movie.presentation.MoviesViewModel
import com.calyrsoft.ucbp1.features.post.presentation.PostsViewModel
import com.calyrsoft.ucbp1.features.profile.presentation.ProfileViewModel
import com.calyrsoft.ucbp1.features.whatsapp.presentation.WhatsappViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {





    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    // Retrofits
    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single(named("TMDB")) {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single(named("Posts")) {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // GithubService
    single<GithubService> {
        get<Retrofit>().create(GithubService::class.java)
    }

    // Servicio de Peliculas
    single<TmdbService> {
        get<Retrofit>(named("TMDB")).create(TmdbService::class.java)
    }

    // Servicio de Posts
    single<PostsService> {
        get<Retrofit>(named("Posts")).create(PostsService::class.java)
    }

    // DataSource
    single{ GithubRemoteDataSource(get()) }
    single { MoviesRemoteDataSource(get()) }
    single { PostsRemoteDataSource(get()) }
    single { RealTimeRemoteDataSource() }

    single { DollarLocalDataSource(get()) }

    // Repositorios
    single<IGithubRepository>{ GithubRepository(get()) }
    single<ILoginRepository> { LoginRepository() }
    single<IExchangeRateRepository> { ExchangeRateRepository() }
    single<IWhatsappRepository> { WhatsappRepository() }
    single<IMoviesRepository> { MoviesRepository(get()) }
    single<IPostsRepository> { PostsRepository(get()) }
    single<IDollarRepository> { DollarRepository(get(), get()) }


    single { AppRoomDatabase.getDatabase(get()) }
    single { get<AppRoomDatabase>().dollarDao() }


    //Use Cases
    factory { FindByNickNameUseCase(get()) }
    factory { FindByNameAndPasswordUseCase(get()) }
    factory { FindByNameUseCase(get()) }
    factory { UpdateUserProfileUseCase(get()) }
    factory { GetExchangeRateUseCase(get()) }
    factory { GetFirstWhatsappNumberUseCase(get()) }
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetAllPostsUseCase(get()) }
    factory { GetCommentsByPostIdUseCase(get()) }
    factory { FetchDollarUseCase(get()) }




    //view models
    viewModel { GithubViewModel(get(), androidContext()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { ExchangeRateViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get(), get()) }
    viewModel { WhatsappViewModel(get ()) }
    viewModel { MoviesViewModel(get()) }
    viewModel { PostsViewModel(get(), get()) }
    viewModel{ DollarViewModel(get()) }


}
