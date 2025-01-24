# SE8-Group5-API

Develop Branch

Version 5.1.0
- Added .circleci folder and config.yml

Version 5.0.0
- Renamed app.api.key to APP_API_KEY because circleCi do not allow ".", only "_" for env variable name
- Renamed UserAccessControllerTest.java to a .txt file

Version 4.0.0
- Removed bus stop @DecimalMax @DecimalMin for Latitude and Longitude causes error during the pull of BusStops from LTA Datamall because there's bus stops in Malaysia but my Latitude Longitude min and max range settings to only Singapore land block those bus stops from getting pulled from LTA Datamall. This was causing issue when trying to build a Docker image.


## Keeping Datamall API secret

### Step 1:

In application.properties, put this in:
  `spring.profiles.active=dev`

Comment out the previous line by Jimmy :

`#APP_API_KEY=${MY_API_KEY}`

### Step 2:

Create a an application-dev.properties file in the same folder as the application.properties.

In the file, put this line:

`APP_API_KEY=<YOUR API KEY>`

### No action required

There are `main - resources - META-INF` and `.vscode` folder. Leave it as it is. No need to change anything.

### Git ignore

Put this line in the `.gitignore`, so that the API key will not be uploaded to github.

`/src/main/resources/application-dev.properties`

Do a `git status`. You should **not** see any git tracking of the `application-dev.properties`.


## Endpoints

Endpoints in **controllerNote.md**.

Note that there are two types of API calls here.

- One is our calls to Datamall.
- Another is our own endpoints for the front-end app.


## Load data

The Postgres database can persist now. To load the data just one time use the following GET /datamall endpoints.

To load the data, use the Datamall endpoints. Call these endpoints one by one.

The **bus routes** data is very large. Be patient and wait. It can take more than 15 minutes depending on network traffic.

```
- Bus services
  - /datamall/services
- Bus stops
  - /datamall/stops
- Bus routes
  - /datamall/routes

```

All Controller files to be in this folder

# Endpoints

### Supply to front-end

Supply to front-end, the route is prefixed by **/app**. This will return the first id of each table. This serves as a test to make sure the endpoint is working. Returning the whole data is too large.

- Bus services
  - /app/services
- To find one bus service
  - /app/services/{serviceNo}
  - localhost:8080/app/services/58
- Bus stops
  - /app/stops
- To find one bus stop
  - /app/stops/{busStopCode}
  - localhost:8080/app/stops/53009
- Bus routes
  - /app/routes
- To find bus route by service number and direction
  - /app/routes/service/{serviceNo}/direction/{direction}
  - localhost:8080/app/routes/service/80/direction/1
- Bus arrival - Notes that this will return the Datamall string as it is.
  - /app/arrival


## From datamall

This is for initial testing to get the data one by one. 

Getting from Datamall, the route is prefixed by **/datamall**.

Not using DataLoader because the data is too large.

- Bus services
  - /datamall/services
- Bus stops
  - /datamall/stops
- Bus routes
  - /datamall/routes


## Guide for Git Team collaboration

Youtube guide: https://www.youtube.com/watch?v=S7XpTAnSDL4
- skip to branching part


### Step-by-step walkthrough

1. Fork your own copy of the group Repo
2. Clone the forked repository `git clone https://github.com/username/repo.git`

Change to the repository directory, `cd repo`


3. Create a new branch:
`git checkout -b feature-branch` (this is a shortcut. the branch name is feature-branch here)

  Check you are on the new Branch using `git branch`.

  **General rule of thumb:** Always use `git status` or `git branch` to check you're on the new branch

4. Do your staging and commit
  - `git add -A` (to stage all new files, deleted files, and modified files. Better than git add . as that does not stage deleted files)
  - `git commit -m "your message"`


5. Publish local branch to remote and set up tracking relationship (have to do this the first time you git push on new branch):
eg. `git push --set-upstream origin features/add_MapMain` 
(above is a chained git command of `git push` and `git --set-upstream`, meaning git push is also done in this line of code)

  - Subsequently, any further git push you want to make, simply use `git push`
    - Optional:  `git push origin your-branch-name` also works.  (see below for explanation of difference)


What is upstream branch?
An upstream branch is a remote branch that your local branch tracks. upstream command pushes a local branch to the origin remote repo (eg. your forked repo), linking them, and sets local branch to track remote branch (eg. your forked repo).


6. Create a pull request on GitHub
  - Go to your forked repository on GitHub 
  - Click on "Compare & pull request"
  - Select "main" as the base branch and "feature-branch" as the compare branch
  - Click on "Create pull request"
  - Team reviews, comments, and approves
  - Once approved, you merge the pull request 


### What is difference between" git push and  git push origin your-branch-name"?

`git push` :
- If a tracking relationship has already been set up (as it is with `--set-upstream`), git push automatically pushes the current branch (eg. Joshua-Branch, the name of my branch) to its corresponding remote branch (origin/Joshua-Branch).
- This is convenient and commonly used after setting the upstream branch.

`git push origin Joshua-Branch` :
- Explicitly specifies both the remote (`origin`) and the branch (`Joshua-Branch`) to push.
- This is useful when:
- You want to push to a different branch or remote.
- No tracking relationship has been set up. 

# SE8-Group5-API

Develop branch

Version 4.0.0
- Removed bus stop @DecimalMax @DecimalMin for Latitude and Longitude causes error during the pull of BusStops from LTA Datamall because there's bus stops in Malaysia but my Latitude Longitude min and max range settings to only Singapore land block those bus stops from getting pulled from LTA Datamall. This was causing issue when trying to build a Docker image.

## Keeping Datamall API secret

### Step 1:

In application.properties, put this in:
  `spring.profiles.active=dev`

Comment out the previous line by Jimmy :

`#app.api.key=${MY_API_KEY}`

### Step 2:

Create a an application-dev.properties file in the same folder as the application.properties.

In the file, put this line:

`app.api.key=<YOUR API KEY>`

### No action required

There are `main - resources - META-INF` and `.vscode` folder. Leave it as it is. No need to change anything.

### Git ignore

Put this line in the `.gitignore`, so that the API key will not be uploaded to github.

`/src/main/resources/application-dev.properties`

Do a `git status`. You should **not** see any git tracking of the `application-dev.properties`.


## Endpoints

Endpoints in **controllerNote.md**.

Note that there are two types of API calls here.

- One is our calls to Datamall.
- Another is our own endpoints for the front-end app.


## Load data

The Postgres database can persist now. To load the data just one time use the following GET /datamall endpoints.

To load the data, use the Datamall endpoints. Call these endpoints one by one.

The **bus routes** data is very large. Be patient and wait. It can take more than 15 minutes depending on network traffic.

```
- Bus services
  - /datamall/services
- Bus stops
  - /datamall/stops
- Bus routes
  - /datamall/routes

```

All Controller files to be in this folder

# Endpoints

### Supply to front-end

Supply to front-end, the route is prefixed by **/app**. This will return the first id of each table. This serves as a test to make sure the endpoint is working. Returning the whole data is too large.

- Bus services
  - /app/services
- To find one bus service
  - /app/services/{serviceNo}
  - localhost:8080/app/services/58
- Bus stops
  - /app/stops
- To find one bus stop
  - /app/stops/{busStopCode}
  - localhost:8080/app/stops/53009
- Bus routes
  - /app/routes
- To find bus route by service number and direction
  - /app/routes/service/{serviceNo}/direction/{direction}
  - localhost:8080/app/routes/service/80/direction/1
- Bus arrival - Notes that this will return the Datamall string as it is.
  - /app/arrival


## From datamall

This is for initial testing to get the data one by one. 

Getting from Datamall, the route is prefixed by **/datamall**.

Not using DataLoader because the data is too large.

- Bus services
  - /datamall/services
- Bus stops
  - /datamall/stops
- Bus routes
  - /datamall/routes


## Guide for Git Team collaboration

Youtube guide: https://www.youtube.com/watch?v=S7XpTAnSDL4
- skip to branching part


### Step-by-step walkthrough

1. Fork your own copy of the group Repo
2. Clone the forked repository `git clone https://github.com/username/repo.git`

Change to the repository directory, `cd repo`


3. Create a new branch:
`git checkout -b feature-branch` (this is a shortcut. the branch name is feature-branch here)

  Check you are on the new Branch using `git branch`.

  **General rule of thumb:** Always use `git status` or `git branch` to check you're on the new branch

4. Do your staging and commit
  - `git add -A` (to stage all new files, deleted files, and modified files. Better than git add . as that does not stage deleted files)
  - `git commit -m "your message"`


5. Publish local branch to remote and set up tracking relationship (have to do this the first time you git push on new branch):
eg. `git push --set-upstream origin features/add_MapMain` 
(above is a chained git command of `git push` and `git --set-upstream`, meaning git push is also done in this line of code)

  - Subsequently, any further git push you want to make, simply use `git push`
    - Optional:  `git push origin your-branch-name` also works.  (see below for explanation of difference)


What is upstream branch?
An upstream branch is a remote branch that your local branch tracks. upstream command pushes a local branch to the origin remote repo (eg. your forked repo), linking them, and sets local branch to track remote branch (eg. your forked repo).


6. Create a pull request on GitHub
  - Go to your forked repository on GitHub 
  - Click on "Compare & pull request"
  - Select "main" as the base branch and "feature-branch" as the compare branch
  - Click on "Create pull request"
  - Team reviews, comments, and approves
  - Once approved, you merge the pull request 


### What is difference between" git push and  git push origin your-branch-name"?

`git push` :
- If a tracking relationship has already been set up (as it is with `--set-upstream`), git push automatically pushes the current branch (eg. Joshua-Branch, the name of my branch) to its corresponding remote branch (origin/Joshua-Branch).
- This is convenient and commonly used after setting the upstream branch.

`git push origin Joshua-Branch` :
- Explicitly specifies both the remote (`origin`) and the branch (`Joshua-Branch`) to push.
- This is useful when:
- You want to push to a different branch or remote.
- No tracking relationship has been set up. 
