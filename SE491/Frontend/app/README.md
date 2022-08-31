# Installation

For frontend development you will need to download and install Node.js - https://nodejs.org/en/download/

Once you have done that, you will need to run the command "npm install" in a terminal in the sdmay22-44/Frontend/app directory.
This contains a package.json and package-lock.json that contain all the dependencies that npm will install. Make sure to 
double check all changes to these files as many packages depend on each other and dependency issues can be a bear to debug.

Once you have run npm install, you should be able to build and run the app. If you want the frontend to communicate with your local backend,
make sure to update the global domain strings that I have not added yet, but will and that you have started the local backend instance. 

If you want a prod build that will not have auto updates and watch, you can run "npm run build:prod". This will generate and replace the 
source files in the backend with the generated javascript from angular and will emulate how the prod setup wil work. If you want to see how the
command does this you can peep the package.json "scripts" section. Then simply start the backend and go to the port that is open on and you 
will see the frontend.

In the environments folder, in the environments.ts you must change the apiUrl to the url of your backend to allow communication there, same with the environments.prod.ts if you want your prod build to use your backend and not the current build on the server (make sure to change this one back after you build).


# App

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 12.2.10.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
