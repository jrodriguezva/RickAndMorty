
# Rick And Morty Android application:

Application to test the Rick&Morty API, and test different libraries like the Jetpack Component suite.

## Screens 📱
The app currently consists of 3 screens:
- A list of characters screen, in which once the user enters de app, they can visualize a list of Rick&Morty characters taken from the API, and select one.
- A character detail screen, in which after selecting one character from the list, the user can visualize detailed information from said character, like their name, description(if there is), comics or series.

## Libraries 🛠️:
 - MVVM
 - Flow
 - Dagger Hilt
 - Room
 - Coil
 - Android Jetpack
 -- Navigator
 -- Livedata
 -- Viewmodel
 - Moshi
 - Retrofit

## Structure 🎨
- __App__:
  - __DI__: the dependency injector package, where the modules and components are created.
  - __UI__: with an MVVM pattern, everything is separated as features, the screens and logic behind them are found here.
  - __Utils__: A variety of classes, extensions, and helpers to help and use across the application, that not necessarily have anything to do with the logic of the same.   
  - __Framework__: Contains the datasource implementations with your mappers.   
- __Data__: Contains the repositories Implementations and one or multiple Data Sources.
  - __Datasource__: In which we have the source of the data we are going to work with, let it be the API abstraction, and/or the database.
   - __Repositories__: Repositories are responsible to coordinate data from the different Data Sources. A sort of abstraction for the data sources in order to avoid working directly with them. We make calls to them and we can ignore whether the data comes from the network or a local database.
- __Domain__: Collection of entity objects and related business logic that is designed to represent the enterprise business model.
  - __Models__: an abstraction of the objects that represent the logic of the project.


## Testing 🧰 
#### (There should totally be more tests, :construction:)
- JUnit
- [Mockk](https://mockk.io/)
- [Kluent](https://markusamshove.github.io/Kluent/)