# covid-tracker-cljs

This application visualises COVID-related data in Finland.

Admin-ui -stylish graphics have been implemented, the data is fetched from project's backend.

This application is written with ClojureScript and its libraries Quil and Re-Frame.

# Installation instructions

Have lein and npm installed. Then run `lein install` and `npm install` to install packages.

Start the application with `lein watch`. The application will start at http://localhost:8280/.

For tests install karma-cli globally with `npm install -g karma-cli`. After that run the tests with `lein ci`. You should be able to see the results in the terminal. Alternatively run `karma start` when the client is running.

# Links

Project's backend: https://github.com/KeremAtak/covid-tracker-clj
