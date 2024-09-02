# Flickr Photo Viewer App

## Overview

This Android app utilises the Flickr API to display a list of photos (with the search term "Yorkshire" by default), as well as providing the option to view additional information about Users and Photos, and search by text, userId, or tags.

### Key Features
- **Photo Search View:** Displays a list of photos with associated tags, user ID, and user icon. Can search by text, userId, or tags.
- **Photo View:** A separate page with more detail about the selected photo.
- **User View:** Tapping on a user allows users to see more photos by that user, as well as any additional information about them. NB: To view all user information on the user view page, please click the dropdown button on the top right.
- **Default Search:** Preloaded with "Yorkshire" on startup. `safe_search` is set to "safe" for all requests.

## Architecture and Libraries

### Compose
Jetpack Compose is used as the UI toolkit to reduce the amount of boilerplate code, making the UI code more readable and maintainable compared to MVVM approaches.

### Paging3
The app uses the Paging 3 library to handle Flickr API photo responses efficiently. The API can return vast numbers of photos, and handling them all at once would lead to performance issues. Paging allows the app to load data incrementally, reducing memory consumption and providing a smooth scrolling experience.

### Retrofit
Retrofit is used for network communication. It offers built-in support for JSON parsing using converters, making it straightforward to map the Flickr API responses to Kotlin data classes.

## Testing
The app includes a selection of instrumented tests to verify the core features of the app. In the future, these tests could be expanded to include unit tests.