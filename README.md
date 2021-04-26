
# Rick And Morty Android application

Application to test the Rick&Morty API, and test different libraries like the Jetpack Component suite.

## Screens 📱
The app currently consists of 3 screens:
- A list of characters screen, in which once the user enters de app, they can visualize a list of Rick&Morty characters taken from the API, and select one.
- A character detail screen, in which after selecting one character from the list, the user can visualize detailed information from said character, like their name, description(if there is), comics or series.

## Libraries 🛠️
- [Flow](https://developer.android.com/kotlin/flow)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Room](https://developer.android.com/training/data-storage/room)
- [Coil](https://coil-kt.github.io/coil/)
- [Android Jetpack](https://developer.android.com/jetpack)
  - [Navigator](https://developer.android.com/guide/navigation/navigation-getting-started)
  - [Livedata](https://developer.android.com/topic/libraries/architecture/livedata)
  - [View Binding](https://developer.android.com/topic/libraries/view-binding)
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
- [Moshi](https://github.com/square/moshi)
- [Retrofit](https://square.github.io/retrofit/)

## Structure 🎨
- __App__:
  - __DI__: the dependency injector package, where the modules and components are created.
  - __UI__: with an MVVM pattern, everything is separated as features, the screens and logic behind them are found here.
  - __Utils__: A variety of classes, extensions, and helpers to help and use across the application, that not necessarily have anything to do with the logic of the same.   
  - __Framework__: Contains the dataSource implementations with your mappers.
- __Data__: Contains the repositories Implementations and one or multiple Data Sources.
  - __DataSource__: In which we have the source of the data we are going to work with, let it be the API abstraction, and/or the database.
   - __Repositories__: Repositories are responsible to coordinate data from the different Data Sources. A sort of abstraction for the data sources in order to avoid working directly with them. We make calls to them and we can ignore whether the data comes from the network or a local database.
- __Domain__: Collection of entity objects and related business logic that is designed to represent the enterprise business model.
  - __Models__: an abstraction of the objects that represent the logic of the project.


## Testing 🧰 
#### (There should totally be more tests, Work in project :construction:)
- JUnit
- [Mockk](https://mockk.io/)
- [Kluent](https://markusamshove.github.io/Kluent/)