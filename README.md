# covid-tracker-cljs

This application will eventually visualize covid-related data. Currently it doesn't do much and the data is hardcoded, but it eventually should look impressive and present real data from THL.

Some rudimentary Three.js graphics have been implemented.

This application is written with ClojureScript and it's library re-frame.

# Installation instructions

Have lein and npm installed. Then run `lein install` and `npm install` to install packages.

Start the application with `lein watch`. The application will start at http://localhost:8280/.

For tests install karma-cli globally with `npm install -g karma-cli`. After that run the tests with `lein ci`. You should be able to see the results in the terminal.

# Links

Project's backend (currently not used): https://github.com/KeremAtak/covid-tracker-clj
